package us.loki.legit.utils;

public class TimeHelper {
	private static long LastMS = 0;
	private long lastMS = -1;
	long sysMs;

	public boolean isDelayComplete(float f) {
		if ((float) (System.currentTimeMillis() - LastMS) >= f) {
			return true;
		}
		return false;
	}

	public static long getCurrentMS() {
		return System.nanoTime() / 1000000;
	}

	public void setLastMS(long LastMS) {
		TimeHelper.LastMS = System.currentTimeMillis();
	}

	public int convertToMS(int perSecond) {
		return 1000 / perSecond;
	}

	public static boolean hasReached(long millisecond) {
		if (TimeHelper.getCurrentMS() - LastMS >= millisecond) {
			return true;
		}
		return false;
	}

	public static void reset() {
		LastMS = TimeHelper.getCurrentMS();
	}

	public void setLastMS() {
		LastMS = System.currentTimeMillis();
	}

	public boolean hasReached1(double lol) {
		return getCurrentMS() - this.LastMS >= lol;
	}

	public boolean delay(float milliSec) {
		return (float) (getCurrentMS() - this.LastMS) >= milliSec;
	}

	public boolean isDelayComplete(long delay) {
		if (System.currentTimeMillis() - this.LastMS >= delay) {
			return true;
		}
		return false;
	}

	private long previousTime;

	public boolean check(float milliseconds) {
		return (float) (getCurrentMS() - this.previousTime) >= milliseconds;
	}

	private long resetMS = 0;

	public void resetAndAdd(long reset) {
		this.resetMS = TimeHelper.getCurrentMS() + reset;
	}

	public boolean hasDelayRun(double d2) {
		if ((double) TimeHelper.getCurrentMS() >= (double) this.resetMS + d2) {
			return true;
		}
		return false;
	}

	public long getTimeSinceReset() {
		return getCurrentMS() - this.LastMS;
	}

	public boolean hasMSPassed(final long toPass) {
		return this.getMSPassed() >= toPass;
	}

	long startTime;

	public long getMSPassed() {
		return System.currentTimeMillis() - this.startTime;
	}

	public boolean isTime(float time) {
		return currentTime() >= time * 1000.0F;
	}

	public float currentTime() {
		return (float) (systemTime() - this.previousTime);
	}

	public long systemTime() {
		return System.currentTimeMillis();
	}

	public boolean hasReached(float milliseconds) {
		return this.getCurrentMS() - this.LastMS >= milliseconds;
	}

	public void updateLastMS() {
		this.lastMS = System.currentTimeMillis();
	}

	public void updateLastMS(long plusMS) {
		this.lastMS += plusMS;
	}

	public boolean hasTimePassedM(long MS) {
		if (System.currentTimeMillis() >= this.lastMS + MS) {
			return true;
		}
		return false;
	}

	public boolean hasTimePassedS(float speed) {
		if (System.currentTimeMillis() >= this.lastMS + (long) (1000.0f / speed)) {
			return true;
		}
		return false;
	}

	public boolean over(final long ms) {
		return System.currentTimeMillis() - this.sysMs > ms;
	}

	public boolean isDelayComplete(double delay) {
		if (System.currentTimeMillis() - this.lastMS < delay) {
			return false;
		}
		return true;
	}

}
