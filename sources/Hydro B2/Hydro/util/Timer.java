package Hydro.util;

public class Timer {
	
	public long lastMS;
    private long ms;
    
    public Timer() {
    	ms = getCurrentMS();
    }

    private long getCurrentMS() {
        return System.currentTimeMillis();
    }
	
	public void reset() {
		lastMS = System.currentTimeMillis();
	}
	
	public boolean hasTimeElapsed(long time, boolean reset) {
		if(System.currentTimeMillis() - lastMS > time) {
			if(reset)
				reset();
			return true;
		}
		
		return false;
	}
	
	public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public void setTime(long time) {
        lastMS = time;
    }
	
	public final boolean elapsed(final long milliseconds) {
        return (getCurrentMS() - ms) > milliseconds;
    }
	
	public boolean hasReached(final double milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public boolean delay(final float milliSec) {
        return this.getTime() - this.lastMS >= milliSec;
    }

}
