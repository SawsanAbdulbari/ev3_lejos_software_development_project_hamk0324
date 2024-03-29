package main.java.colorSensor;

import lejos.hardware.Button;

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

        Thread colorSensorThread = new Thread(colorSensor);

        colorSensorThread.start();

        while (true) 
        {
            if (!Button.ESCAPE.isUp()) 
            {
                System.exit(0);
            }
        }
    }
}