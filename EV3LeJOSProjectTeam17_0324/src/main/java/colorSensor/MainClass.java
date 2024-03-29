package main.java.colorSensor;

import lejos.hardware.Button;
import main.java.motors.MotorControl;

/**
 * 
 */
public class MainClass 
{
    /**
     * @param args
     */
    public static void main(String[] args) 
    {
        ColorSensor colorSensor = new ColorSensor();
        MotorControl motorControl = new MotorControl();

        Thread colorSensorThread = new Thread(colorSensor);
        Thread motorControlThread = new Thread(motorControl);

        colorSensorThread.start();
        motorControlThread.start();


        while (true) 
        {
            if (!Button.ESCAPE.isUp()) 
            {
                System.exit(0);
            }
        }
    }
}