package main.java.ultraSonicSensor;


import data.Config;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

/**
 * Date: April 2024
 * This is Ultrasonic sensor
 * @auhor Sonja
 * @version 2.0
 */

public class UltrasonicSensor implements Runnable {
	/**
	 * Config object for configuration settings.
	 */
    private Config config;
    /**
     * Sampleprovider for reading distance.
     */
	SampleProvider distance;
	/**
	 *  Array to store sensor samples.
	 */
	float[] sample;
	/**
	 * Line retrieves port"s1" from localEV3 brick by using getPort()method.
	 */
	Port port = LocalEV3.get().getPort("S1");
	/**
	 * Ultrasonic sensor object.
	 */
	EV3UltrasonicSensor us;
	

    /**
     * In this constructor we initialize object config, ultrasonic sensor and sample.
     * Also retrieves port"s1" from localEV3 brick by using getPort()method.
     * creates new instance of UltrasonicSensor and initializes it with port(s1) 
     * retrieves distanceMode from us(Ultrasonic Sensor) and assigns it to distance
     * creates new float array which stores samples from distanceMode
     * @param config
     * 
     */
    public UltrasonicSensor(Config config) {
        this.config = config;
        Port port = LocalEV3.get().getPort("S1");
    	this.us = new EV3UltrasonicSensor(port);
        this.distance = us.getDistanceMode();
        this.sample = new float[distance.sampleSize()];
    }
	/**
	 *Wait until the latch in the config object is released.
	 *calibrate and then proceed with the rest of the thread's logic.
	 *preserve interrupt status and exit method.
	 *
	 *Loop where sample is fetched from the sensor and
	 *sample is converted to centimeters
	 *
	 *delay before taking next sample
	 *
	 *check if object is whitin certain range
	 *
	 *if true it will avoid detecting for 10000ms
	 */
		public void run() {
	        try {
	            config.latch.await();
	            // Proceed with the rest of the thread's logic after calibration is done
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt(); // Preserve interrupt status
	            return; // Exit the method
	        }
			while(true) {
				  distance.fetchSample(sample, 0);
				  float distanceValue = sample[0] * 100;
								  
				  Delay.msDelay(100);
	
					  	if (distanceValue <= 10 && distanceValue >=0) {
							//LISÄTTY
							config.setObjectDetected(true);
							Delay.msDelay(10000);
						}	
			}
		}
		
	}
