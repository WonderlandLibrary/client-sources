package Focus.Beta.UTILS.world;

public class Timer {
	
	public long MS = System.currentTimeMillis();
	
	public void reset() {
		MS = System.currentTimeMillis();
	}
	
	public boolean hasElapsed(long time, boolean reset) {
		if(System.currentTimeMillis()-MS > time) {
			if(reset)
				reset();
			
			return true;
		}
		return false;
	}
	

    public boolean hasReached(double milliseconds) {
        if ((double) (this.getCurrentTime() - this.MS) >= milliseconds) {
            return true;
        }
        return false;
    }
	public boolean reach(final long milliseconds) {
		return System.currentTimeMillis() - this.MS >= milliseconds;
	}

	public boolean reach(final double milliseconds) {
		return System.currentTimeMillis() - this.MS >= milliseconds;
	}

	public boolean hasTimePassed(final long ms) {
		return System.currentTimeMillis() >= MS + ms;
	}

	public long hasTimeLeft(final long ms) {
		return (ms + MS) - System.currentTimeMillis();
	}

	public boolean check(float milliseconds) {
		return getTime() >= milliseconds;
	}

	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public long getTime() {
		return getCurrentTime() - MS;
	}

}
