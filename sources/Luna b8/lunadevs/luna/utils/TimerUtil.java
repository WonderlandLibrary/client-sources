package lunadevs.luna.utils;

public class TimerUtil {
	private long prevMS;
	private long previousTime;
	private long lastMS;

	public TimerUtil() {
		this.prevMS = 0L;
	}

	public boolean delay(final double d) {
		if (this.getTime() - this.getPrevMS() >= d) {
			this.reset();
			return true;
		}
		return false;
	}

	public long getTime() {
		return System.nanoTime() / 1000000L;
	}

	public long getPrevMS() {
		return this.prevMS;
	}

	public void Timer() {
		this.previousTime = -1L;
	}

	public boolean hasReach(final float milliseconds) {
		return getCurrentTime() - this.previousTime >= milliseconds;
	}

	public void reset() {
		this.previousTime = getCurrentTime();
	}

	public long get() {
		return this.previousTime;
	}

	public static long getCurrentTime() {
		return System.nanoTime() / 1000000L;
	}

	public boolean check(final float milliseconds) {
		return getCurrentTime() - this.previousTime >= milliseconds;
	}

	public static short convert(final float perSecond) {
		return (short) (1000.0f / perSecond);
	}

	public long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public long getLastMS() {
		return this.lastMS;
	}

	public boolean hasReached(final long milliseconds) {
		return this.getCurrentMS() - this.lastMS >= milliseconds;
	}

	public void reset1() {
		this.lastMS = this.getCurrentMS();
	}

	public void setLastMS(final long currentMS) {
		this.lastMS = currentMS;
	}

	public boolean isDelayComplete(final long delay) {
		return System.currentTimeMillis() - this.lastMS >= delay;
	}

	public boolean hasDelay(double d) {
		return getTime() - this.prevMS >= 1000.0D / d;
	}
}