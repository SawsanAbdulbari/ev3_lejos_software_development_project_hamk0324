package data;

import java.util.concurrent.CountDownLatch;

/**
 * Config class serves as the centralized storage for the application's configuration and state.
 * It holds various parameters for line following and obstacle avoidance behaviors, including sensor thresholds,
 * PID control parameters, and flags for application state. Methods are synchronized to ensure thread safety.
 * @date April 2024
 * @authors Sawsan and Linda
 * @version 2.0
 */
public class Config {
	
	/**
	 * Sensor value read from the color sensor
	 */
	public float SENSOR_VALUE;
	
	/**
	 * Threshold value to determine line presence
	 */
	public float LINE_VALUE;
	
	/**
	 * Threshold value to determine floor presence
	 */
	public float FLOOR_VALUE;
	
	/**
	 * Threshold to determine the ideal driving point
	 */
	public float THRESHOLD;
	
	/**
	 * // Additional threshold parameters for more refined control
	 */
	public float lineThreshold;
	public float onLineThreshold;
	public float floorThreshold;
	public float onFloorThreshold;
	
	
	/**
	 * Application running state
	 */
	public boolean RUNNING = true;
	
	/**
	 * Flag to indicate if the goal has been reached, 
	 * object has been detected twice
	 */
	public boolean hasReachedGoal = false;
	
	/**
	 * Flag to indicate if an object has been detected by the ultrasonic sensor
	 */
	public boolean objectDetected = false;
	
	/**
	 * PID control parameters for line following. The values have been fine-tuned
	 * to accurately adjust speed and wheel rotation to follow a line.
	 */
	public final float PROPORTIONAL_GAIN = 30.0f;
	public final float INTEGRAL_GAIN = 0.99f;
	public final float DERIVATIVE_GAIN = 0.05f;
	
	/**
	 * Accepted variation of error to drive straight 
	 */
	public final float ERROR_THRESHOLD = 0.07f;
	
	/**
	 * Base speed for motor operation
	 */
	public final float BASE_SPEED = 190;
	
	
	/**
	 * Latch for synchronizing start-up sequence
	 */
	public CountDownLatch latch = new CountDownLatch(1);
	
	/**
	 * Checks if an object is detected.
	 * @return True if an object is detected, false otherwise.
	 */
	public synchronized boolean isObjectDetected() {
		return objectDetected;
	}
	
	/**
	 * Sets the object detected state.
	 * @param objectDetected New state to set.
	 */
	public synchronized void setObjectDetected(boolean objectDetected) {
		this.objectDetected = objectDetected;
	}
	
	/**
	 * Checks if the goal has been reached.
	 * @return True if the goal has been reached, false otherwise.
	 */
	public synchronized boolean isHasReachedGoal() {
		return hasReachedGoal;
	}
	
	/**
	 * Sets the goal reached state.
	 * @param hasReachedGoal New state to set.
	 */
	public synchronized void setHasReachedGoal(boolean hasReachedGoal) {
		this.hasReachedGoal = hasReachedGoal;
	}
		
	/**
	 * Sets the sensor value.
	 * @param value New sensor value to set.
	 */
	public synchronized void setSensorValue(float value) {
	    this.SENSOR_VALUE = value;
	}
	
	/**
	 * Gets the current sensor value.
	 * @return Current sensor value.
	 */
	public synchronized float getSensorValue() {
	    return this.SENSOR_VALUE;
	}	
	
	/**
	 * Sets the threshold, middle point of the line.
	 * @param threshold New threshold to set.
	 */
	public synchronized float getThreshold() {
		return THRESHOLD;
	}
	
	/**
	 * Gets the threshold, middle point of the line.
	 * @return Current threshold.
	 */
	public synchronized void setThreshold(float threshold) {
		this.THRESHOLD = threshold;
	}
	
	/**
	 * Sets the line value.
	 * @param value New line value to set.
	 */
	public synchronized void setLineValue(float value) {
	    this.LINE_VALUE = value;
	}
	
	/**
	 * Gets the current line value.
	 * @return Current line value.
	 */
	public synchronized float getLineValue() {
		return LINE_VALUE;
	}
	
	/**
	 * Sets the floor value.
	 * @param value New floor value to set.
	 */
	public synchronized void setFloorValue(float value) {
	    this.FLOOR_VALUE = value;
	}
	
	/**
	 * Gets the current floor value.
	 * @return Current floor value.
	 */
	public synchronized float getFloorValue() {
		return FLOOR_VALUE;
	}
	
	/**
	 * Sets the line threshold value.
	 * @param value New line threshold value to set.
	 */
	public synchronized void setLineThreshold(float lineThreshold) {
		this.lineThreshold = lineThreshold;
	}
	
	/**
	 * Gets the current line threshold value.
	 * @return Current line threshold value.
	 */
	public synchronized float getLineThreshold() {
		return lineThreshold;
	}
	
	/**
	 * Sets the on line threshold value.
	 * @param value New on line threshold value to set.
	 */
	public synchronized void setOnLineThreshold(float onLineThreshold) {
		this.onLineThreshold = onLineThreshold;
	}
	
	/**
	 * Gets the current on line threshold value.
	 * @return Current on line threshold value.
	 */
	public synchronized float getOnLineThreshold() {
		return onLineThreshold;
	}
	
	/**
	 * Sets the floor threshold value.
	 * @param value New floor threshold value to set.
	 */
	public synchronized void setFloorThreshold(float floorThreshold) {
		this.floorThreshold = floorThreshold;
	}
	
	/**
	 * Gets the current floor threshold value.
	 * @return Current floor threshold value.
	 */
	public synchronized float getFloorThreshold() {
		return floorThreshold;
	}
	
	/**
	 * Sets the on floor threshold value.
	 * @param value New on floor threshold value to set.
	 */
	public synchronized void setOnFloorThreshold(float onFloorThreshold) {
		this.onFloorThreshold = onFloorThreshold;
	}
	
	/**
	 * Gets the current on floor threshold value.
	 * @return Current on floor threshold value.
	 */
	public synchronized float getOnFloorThreshold() {
		return onFloorThreshold;
	}
	
	/**
	 * Sets the running state of the application.
	 * @param running New running state to set.
	 */
	public synchronized void setRunning(boolean running) {
		this.RUNNING = running;
	}
	
	/**
	 * Checks if the application is currently running.
	 * @return True if the application is running, false otherwise.
	 */
	public synchronized boolean isRunning() {
		return RUNNING;
	}
}