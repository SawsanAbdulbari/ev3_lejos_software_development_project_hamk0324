package main.java.colorSensor;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Date: March 2024
 * This is a simple function for color sensor to read the line and the floor
 * @author Sawsan
 * @version 1.0
 */
public class ColorSensor implements Runnable 
{

	/**
	 * 
	 */
	private static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
	private static SampleProvider sampleProv = colorSensor.getRedMode();
	private static float lineValue, floorValue, threshold, sensorValue;

    private static float[] sample = new float[sampleProv.sampleSize()];
    
 
	/**
	 *
	 */
	@Override
	public void run() 
	{
	    System.out.println("Place on line, then press any button.");
	    Button.waitForAnyPress();
	    sampleProv.fetchSample(sample, 0);
	    lineValue = (float) sample[0];
	    System.out.println(lineValue);
	    	    
	    System.out.println("Place on floor, then press any button.");
	    Button.waitForAnyPress();
	    sampleProv.fetchSample(sample, 0);
	    floorValue = (float) sample[0];
	    System.out.println(floorValue);   


	    threshold = (lineValue + floorValue) / 2;
	    System.out.println("Threshold set at: " + threshold);

	    
	    Delay.msDelay(2000);
	    
        while (true) 
        {
            sampleProv.fetchSample(sample, 0);
            sensorValue = (float) sample[0];
            if (sensorValue < threshold)
            {
                System.out.println("Robot is on the line.");
            }
            else if (sensorValue > threshold)
            {
                System.out.println("Robot is on the floor.");
            }
            else 
            {
                System.out.println("Robot is on the track.");
            }

            Delay.msDelay(1000);
        }
	}
}