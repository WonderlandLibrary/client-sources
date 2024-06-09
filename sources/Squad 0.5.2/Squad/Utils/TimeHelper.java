package Squad.Utils;

public final class TimeHelper {
	private long lastMS;

	public long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public long getLastMS() {
		return this.lastMS;
	}

	public boolean hasReached(float f) {
		return (float) (getCurrentMS() - this.lastMS) >= f;
	}

	public void reset() {
		this.lastMS = getCurrentMS();
	}

	public void setLastMS(long currentMS) {
		this.lastMS = currentMS;
	}


	public boolean isDelayComplete(float f) {
		if (System.currentTimeMillis() - this.lastMS >= f) {
			return true;
		}
		return false;
	}

	
	public boolean delay(float milliSec) {
        return this.getCurrentMS() - this.lastMS >= milliSec;
    }

	public void setLastMS() {
		this.lastMS = System.currentTimeMillis();
	}

	public int convertToMS(int perSecond) {
		return 1000 / perSecond;
	}
	
	
	    
	    public long getMSPassed() {
	        return System.currentTimeMillis() - this.lastMS;
	    }
	    
	    public void add(final int amount) {
	        this.lastMS -= amount;
	    }
	    
	    public void subtract(final int amount) {
	        this.lastMS += amount;
	    }
	    
	    public boolean hasMSPassed(final long toPass) {
	        return this.getMSPassed() >= toPass;
	    }
}