package main.java.motors;

import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

import data.Config;
/**
 * The MotorControl class is responsible for the direct control of the motors attached to an EV3 robot, 
 * facilitating both basic and complex movement patterns. It implements a PID controller to adjust the 
 * robot's speed and direction dynamically based on sensor feedback, aiming to achieve precise navigation. 
 * This class also includes methods for obstacle avoidance, making use of sensor data to alter the robot's path 
 * when necessary. It is designed to run on a separate thread to continuously monitor and adjust the robot's movement 
 * as needed based on the current environment and objectives.
 * @date April 2024
 * @authors Linda and Sawsan
 * @version 2.0
 */
public class MotorControl implements Runnable
{
	/**
	 * Shared configuration object with settings and sensor values
	 */
	private Config config;
	
	/**
	 * Motor controller for the left wheel
	 */
    private EV3LargeRegulatedMotor leftMotor;
    
    /**
     * Motor controller for the right wheel
     */
    private EV3LargeRegulatedMotor rightMotor;
    
    // PID controller variables
    /**
     * The previous error value
     */
    private float lastError = 0f;
    
    /**
     * Accumulated error over time
     */
    private float integral = 0f;
    
    /**
     * Current error
     */
    private float error;
    
    /**
     * Rate of change of error
     */
    private float derivative;
    
    /**
     * Output of PID calculation
     */
    private float PIDsum;
    
    /**
     * Speed adjustment for left motor
     */
    private float leftSpeed;
    
    /**
     * Speed adjustment for right motor
     */
    private float rightSpeed;
    
    /**
     * A counter for obstacle handling
     */
    public static int i;

    /**
     * Initializes a new instance of the MotorControl class, setting up the motors and storing the configuration.
     * 
     * @param config The configuration object containing operational parameters and sensor thresholds.
     */
    public MotorControl(Config config) {
        this.config = config;
        this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    }
    
    /**
     * The entry point for the MotorControl thread. Waits for initial calibration to complete, then enters
     * a loop where it continuously checks sensor values and adjusts motor speeds accordingly to navigate the robot.
     */
    @Override
    public void run() {
        /** 
         * Wait for calibration to complete before starting the navigation loop. 
         */
        try {
            /*
             * Blocking call to ensure calibration is complete
             */
            config.latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle thread interruption
            return; // Early exit if the thread is interrupted
        }
                
        /** 
         * Main loop for continuous navigation adjustments based on sensor input 
         */
        while(true) {
            config.SENSOR_VALUE = config.getSensorValue(); // Update the sensor value from configuration
            turn(); // Adjust the robot's movement based on the current sensor values
        }
    }
    
    /**
     * Implements a PID control strategy to adjust the robot's movement based on sensor feedback.
     * This method calculates the necessary adjustments to the motor speeds for turning and straight movement,
     * aiming to maintain the desired trajectory.
     */
    public void turn() {

        if (config.objectDetected == true)
        {
        	i += 1;
        	if (i==2)
        	{
        		closeMotors();
        		config.setHasReachedGoal(true);
        	}
        	else
        	{        	
        		handleObjectAvoidance();
        		config.objectDetected = false;
        		return;
        	}

        }
        
        if (config.SENSOR_VALUE >= config.lineThreshold && config.SENSOR_VALUE <= config.floorThreshold)
        {
        	integral = 0;
        }
        
        /* 
         * The turn method utilizes a PID controller to dynamically adjust the robot's heading based on the difference 
         * (error) between the desired trajectory and the current sensor reading. This method adapts the motor speeds 
         * to correct for deviations, such as veering off a path or approaching an obstacle, ensuring smooth and 
         * effective navigation.
         */
        error = config.SENSOR_VALUE - config.THRESHOLD;
        integral += error;
        derivative = error - lastError;
        lastError = error;
        PIDsum = error * config.PROPORTIONAL_GAIN + integral * config.INTEGRAL_GAIN + derivative * config.DERIVATIVE_GAIN;                
        
        if (Math.abs(error) < config.ERROR_THRESHOLD) {
            // If error is within a certain range, GOING FORWARD
       	    leftSpeed = config.BASE_SPEED;
            rightSpeed = config.BASE_SPEED;
            adjustMotorsSpeed(leftSpeed, rightSpeed, false);
            return;
        }
        
        // Set motor speeds
        if (error<0)
        {
        	// TURNING LEFT
            leftSpeed = (config.BASE_SPEED - Math.abs(PIDsum)); 
            rightSpeed = (config.BASE_SPEED + Math.abs(PIDsum));
        } 
        else 
        { 
        	// TURNING RIGHT 
            leftSpeed = (config.BASE_SPEED + Math.abs(PIDsum));
            rightSpeed = (config.BASE_SPEED - Math.abs(PIDsum));
        }

       
        
        if (config.SENSOR_VALUE >= config.FLOOR_VALUE)
        {
       	    leftSpeed *= 0.85f;
            rightSpeed *= 0.55f;
        }
        
        if (config.SENSOR_VALUE >= config.onFloorThreshold && config.SENSOR_VALUE < config.FLOOR_VALUE)
        {
       	    leftSpeed *= 0.8f;
            rightSpeed *= 0.6f;
        }        

        if (config.SENSOR_VALUE > config.floorThreshold && config.SENSOR_VALUE < config.onFloorThreshold) 
        {
            leftSpeed *= 0.8f;
            rightSpeed *= 0.78f;            
        }
             
        
        if (config.SENSOR_VALUE <= config.LINE_VALUE) 
        { 
       	    leftSpeed *= 0.2f;
            rightSpeed *= 0.8f;
            adjustMotorsSpeed(leftSpeed, rightSpeed, true);
        }
        
        if (config.SENSOR_VALUE <= config.onLineThreshold && config.SENSOR_VALUE > config.LINE_VALUE)
        {
       	    leftSpeed *= 0.15f;
            rightSpeed *= 0.6f;
            adjustMotorsSpeed(leftSpeed, rightSpeed, true);
        }
        
        if (config.SENSOR_VALUE < config.lineThreshold && config.SENSOR_VALUE > config.onLineThreshold) 
        {
       	    leftSpeed *= 0.78f;
            rightSpeed *= 0.8f;
        }       

        adjustMotorsSpeed(leftSpeed, rightSpeed, false);
        Delay.msDelay(50);
    }
    
    /* 
     * The handleObjectAvoidance method is called when an obstacle is detected in the robot's path. 
     * It temporarily overrides the normal navigation to execute a maneuver designed to avoid the obstacle, 
     * involving stopping, reversing, turning, and then proceeding forward. 
     * After object has been passed the robot automatically starts looking for the line again.
     */
    private void handleObjectAvoidance() {

    	 /**
         * Stop to prevent collision
         */
        stop();
        Delay.msDelay(300);

        /**
         * Turn to avoid the object, by stopping the left motor and setting the right motor to move forward
         */
        adjustMotorsSpeed(30, 30, true);
        Delay.msDelay(1000); 

        /**
         * Move forward for a set amount of time before resuming normal operation
         */
        adjustMotorsSpeed(40, 40, false); // 
        Delay.msDelay(600);

        /**
         * Move forward and set motor speeds to the base speed
         */
        adjustMotorsSpeed(config.BASE_SPEED, config.BASE_SPEED, false);
        Delay.msDelay(1000);
    }
    
    /**
     * Adjusts the speed and direction of the motors based on the given parameters. This method can be used to
     * set the motors for forward movement, reverse, or turning, depending on the speeds and the reverse flag.
     * 
     * @param leftSpeed The speed for the left motor.
     * @param rightSpeed The speed for the right motor.
     * @param reverse A flag indicating whether the motors should be set to reverse.
     */
    private void adjustMotorsSpeed(float leftSpeed, float rightSpeed, boolean reverse) {
        leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);
        if (reverse) {
            leftMotor.backward();
            rightMotor.forward();
        } else {
            leftMotor.forward();
            rightMotor.forward();
        }
    }
	  
    /**
     * Stops both motors immediately. This is used for emergency stopping or as part of obstacle avoidance.
     */
    public void stop() 
    {
        leftMotor.stop();
        rightMotor.stop();
    }
    
    /**
     * Closes the motor ports, effectively disabling the motors. This method is called before shutting down
     * the program to ensure a graceful termination of motor control.
     */
    public void closeMotors() 
    {
        leftMotor.close();
        rightMotor.close();
    }
}
