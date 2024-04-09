package main.java.ultraSonicSensor;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class TestUltrasonicSensor implements Runnable {
	
	
	 	SampleProvider distance;
	 	float[] sample;
	 	boolean running;
		Port port = LocalEV3.get().getPort("S4");
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S4);
	
	public TestUltrasonicSensor() {
	
		distance= us.getDistanceMode();
		sample = new float[distance.sampleSize()];
		running = true;
	}
	
	 @Override
	    public void run() {
	
		while(running) {
			distance.fetchSample(sample, 0);
			System.out.println("Distance: " + sample[0]);		
			Delay.msDelay(500);
		}
		
		us.close();
	}
	
	public void stopSensor() {
		running = false;
	}
	
	
}
