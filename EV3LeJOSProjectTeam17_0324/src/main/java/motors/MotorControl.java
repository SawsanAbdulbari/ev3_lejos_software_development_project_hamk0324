package main.java.motors;


import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class MotorControl implements Runnable
{

	private static EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
	private static EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
	
	private static int baseSpeed = 100;

    
    @Override
	public void run() 
	{
		while(true)
		{
			forward();
			turnLeft();
			turnRight();
		}
	}

	
    public static void forward() 
    {
        leftMotor.setSpeed(baseSpeed);
        rightMotor.setSpeed(baseSpeed);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(2000);
    }
    public static void turnLeft() 
    {	
        double leftSpeed = baseSpeed * 0.8;
        double rightSpeed = baseSpeed * 1.2;
        leftMotor.setSpeed((int) leftSpeed);
        rightMotor.setSpeed((int) rightSpeed);
        Delay.msDelay(2000);
    }
    public static void turnRight() 
    {
        double leftSpeed = baseSpeed * 1.5;
        double rightSpeed = baseSpeed * 0.8;
        leftMotor.setSpeed((int) leftSpeed);
        rightMotor.setSpeed((int) rightSpeed);
        Delay.msDelay(2000);
    }
    public static void stop() 
    {
        leftMotor.stop();
        rightMotor.stop();
    }
    public static void closeMotors() 
    {
        leftMotor.close();
        rightMotor.close();
    }
}
