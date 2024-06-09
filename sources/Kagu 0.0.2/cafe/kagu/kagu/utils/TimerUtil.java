/**
 * 
 */
package cafe.kagu.kagu.utils;

/**
 * @author lavaflowglow
 *
 */
public class TimerUtil {
	
	private long lastMillis = System.currentTimeMillis();
	
	/**
	 * Check the the time has elapsed
	 * @param time The time to check that has passed
	 * @return true if the time has elapsed, otherwise false
	 */
	public boolean hasTimeElapsed(long time) {
		return hasTimeElapsed(time, false);
	}
	
	/**
	 * Check the the time has elapsed
	 * @param time The time to check that has passed
	 * @param reset Whether or not the timer should reset if the time has elapsed
	 * @return true if the time has elapsed, otherwise false
	 */
	public boolean hasTimeElapsed(long time, boolean reset) {
		// If the user sets their clock back then the time millis may be greater than the current time, in this event we should reset the time
		if (lastMillis >= System.currentTimeMillis())
			reset();
		
		// Check if the time has elapsed and return
		if (lastMillis + time <= System.currentTimeMillis()) {
			if (reset)
				reset();
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the timer
	 */
	public void reset() {
		lastMillis = System.currentTimeMillis();
	}
	
}
