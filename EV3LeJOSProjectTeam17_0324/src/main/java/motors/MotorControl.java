package main.java.motors;

import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import colorSensor.ColorSensor;
import ultrasonicSensor.UltrasonicSensor;

public class MotorControl implements Runnable
{

	
	
    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;
	
	
    public static float threshold = ColorSensor.calibrate();
    public static float lineValue = ColorSensor.lineValue;
    public static float floorValue = ColorSensor.floorValue;
    public static float lineThreshold = ColorSensor.getLineThreshold();
    public static float onLineThreshold = ColorSensor.getOnLineThreshold();
    public static float floorThreshold = ColorSensor.getFloorThreshold();
    public static float onFloorThreshold = ColorSensor.getOnFloorThreshold();
   
	
	// TESTING
    // PID control parameters
    private final float Kp = 20.0f;
    private final float Ki = 0.9f;
    private final float Kd = 0.05f;

    private float lastError = 0f;
    private float integral = 0f;
    private float error;
    private float derivative;
    private float PIDsum;
    
    private float leftSpeed;
    private float rightSpeed;
    private float sensorValue;
    private float errorThreshold = 0.05f;

    private float baseSpeed = 300;
    
    
    public MotorControl() {
        this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
    }
    
    
    @Override
	public void run() 
	{
        while (!ColorSensor.calibrationDone) 
        {
            try 
            {
                Thread.sleep(100); // Sleep for a short interval before checking again
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
        

        
        while(true)
		{
	        turn();
		}
	}
    
    public boolean objectDetected()
    {
        boolean objectDetected = UltrasonicSensor.objectDetected;
        return objectDetected;
    }

    
    public void turn() {
    	    
    	// Check if object has been detected
        if (objectDetected() == true)
        {
        	avoidObject();
        }
        
        
        sensorValue = ColorSensor.getSensorValue();        
        
        if (sensorValue >= lineThreshold && sensorValue <= floorThreshold)
        {
        	integral = 0;
        }
        
        error = sensorValue - threshold;
        integral += error;
        derivative = error - lastError;
        lastError = error;
        PIDsum = error * Kp + integral * Ki + derivative * Kd;
        
        
        System.out.println("");
        
        System.out.println("sensorValue: " + sensorValue + " | error: " + error + " | sum: " + PIDsum);
        
        
        if (Math.abs(error) < errorThreshold) {
            // If error is within a certain range, go straight
            goForward();
            System.out.println(" GOING FORWARD ");
            return;
        }
        
        // Set motor speeds
        if (error<0)
        {
            leftSpeed = (baseSpeed - Math.abs(PIDsum)); 
            rightSpeed = (baseSpeed + Math.abs(PIDsum));
            System.out.println(" TURNING LEFT ");
        } 
        else 
        { 
            leftSpeed = (baseSpeed + Math.abs(PIDsum));
            rightSpeed = (baseSpeed - Math.abs(PIDsum));
            System.out.println(" TURNING RIGHT ");
        }

       
        
        if (sensorValue >= floorValue)
        {
       	    leftSpeed *= 0.9f;
            rightSpeed *= 0.5f;
            System.out.println(" (sensorValue >= floorValue) ");
        }
        
        if (sensorValue >= onFloorThreshold && sensorValue < floorValue)
        {
       	    leftSpeed *= 0.8f;
            rightSpeed *= 0.6f;
            System.out.println(" (sensorValue >= onFloorThreshold) ");
        }        

        if (sensorValue > floorThreshold && sensorValue < onFloorThreshold) 
        {
            leftSpeed *= 0.8f;
            rightSpeed *= 0.78f;
            
            System.out.println("(sensorValue > floorThreshold)");
        }
             
        
        if (sensorValue <= lineValue) 
        {
       	    leftSpeed *= 0.2f;
            rightSpeed *= 0.8f;
            setMotorsSpeedReverse(leftSpeed, rightSpeed);
            System.out.println(" (sensorValue <= lineValue) ");
        }
        
        if (sensorValue <= onLineThreshold && sensorValue > lineValue)
        {
       	    leftSpeed *= 0.15f;
            rightSpeed *= 0.6f;
            setMotorsSpeedReverse(leftSpeed, rightSpeed);
            System.out.println(" (sensorValue <= onLineThreshold) ");
        }
        
        if (sensorValue < lineThreshold && sensorValue > onLineThreshold) 
        {
       	    leftSpeed *= 0.78f;
            rightSpeed *= 0.8f;
            
            System.out.println(" (sensorValue < lineThreshold) ");
        }       

        setMotorsSpeed(leftSpeed, rightSpeed);
        Delay.msDelay(50);
    }
    
    private void avoidObject() {
    	stop();
    	leftMotor.setSpeed(0);
        rightMotor.setSpeed(50);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(500);
    	leftMotor.setSpeed(100);
        rightMotor.setSpeed(50);
        Delay.msDelay(1000);
    	leftMotor.setSpeed(100);
        rightMotor.setSpeed(100);
        Delay.msDelay(1000);
        return;
	}


	private void setMotorsSpeed(float leftSpeed, float rightSpeed) 
    {
    	leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);
        leftMotor.forward();
        rightMotor.forward();
    }
	
    private void setMotorsSpeedReverse(float leftSpeed, float rightSpeed) 
    {   	
    	leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);
        leftMotor.backward();
        rightMotor.forward();
    }   
    
    public void goForward() 
    {
        leftMotor.setSpeed(baseSpeed);
        rightMotor.setSpeed(baseSpeed);
        leftMotor.forward();
        rightMotor.forward();
    }
	  
    public void stop() 
    {
        leftMotor.stop();
        rightMotor.stop();
    }
    
    public void closeMotors() 
    {
        leftMotor.close();
        rightMotor.close();
    }
}
