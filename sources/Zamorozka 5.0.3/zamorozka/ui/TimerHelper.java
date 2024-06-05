package zamorozka.ui;

public class TimerHelper {
	private static long lastMS;

	public static long getCurrentMS() {
		return System.nanoTime() / 1000000L;
	}

	public static boolean hasReached(double d) {
		return getCurrentMS() - lastMS >= d;
	}

	public long getTime() {
		return getCurrentMS() - this.lastMS;
	}

	public static void reset() {
		lastMS = getCurrentMS();
	}

	public boolean isDelayComplete(long delay) {
		return System.currentTimeMillis() - this.lastMS >= delay;
	}

	public boolean isDelayComplete(float delay) {
		return System.currentTimeMillis() - this.lastMS >= delay;
	}

	public boolean isDelayComplete(int delay) {
		return System.currentTimeMillis() - this.lastMS >= delay;
	}

	public boolean isDelayComplete(double delay) {
		return System.currentTimeMillis() - this.lastMS >= delay;
	}

	public long getLastMs() {
		return System.currentTimeMillis() - this.lastMS;
	}

	public void setLastMS() {
		this.lastMS = System.currentTimeMillis();
	}

}