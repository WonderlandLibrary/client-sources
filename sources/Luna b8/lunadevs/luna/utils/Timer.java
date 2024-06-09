package lunadevs.luna.utils;

public class Timer {
	private long previousTime;
	private long prevMS;

	public Timer() {
		this.previousTime = -1L;
		this.prevMS = 0L;
	}

	public boolean delay(float milliSec) {
		return (float) (getTime() - this.prevMS) >= milliSec;
	}

	public boolean check(float milliseconds) {
		return (float) getTime() >= milliseconds;
	}

	public long getTime() {
		return getCurrentTime() - this.previousTime;
	}

	public void reset() {
		this.previousTime = getCurrentTime();
	}

	public long getCurrentTime() {
		return System.currentTimeMillis();
	}
}
