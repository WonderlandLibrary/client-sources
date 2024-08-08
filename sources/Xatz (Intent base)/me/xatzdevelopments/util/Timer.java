package me.xatzdevelopments.util;

public class Timer {

	public long lastMS = System.currentTimeMillis();
	
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	
	public boolean isDelayComplete(double delay) {
        return System.currentTimeMillis() - delay >= lastMS;
    }
	
	 public long getTime() {
	      return System.nanoTime() / 1000000L;
	   }
	
	 public boolean delay(float milliSec) {
	      return (float)MathUtils.getIncremental((double)(this.getTime() - this.lastMS), 50.0D) >= milliSec;
	   }
	
	public boolean hasTimeElapsed(long time, boolean reset) {
		if(System.currentTimeMillis() - lastMS > time) {
			if(reset)
				reset();
			
			
			
			return true;
		}
		
		return false;
	}
	
}
