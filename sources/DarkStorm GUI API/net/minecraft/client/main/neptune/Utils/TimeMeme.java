package net.minecraft.client.main.neptune.Utils;

public class TimeMeme {
	private long prevTime;

	public TimeMeme() {
		this.reset();
	}

	public boolean hasPassed(double milli) {
		return this.getTime() - this.prevTime >= milli;
	}

	public long getTime() {
		return System.nanoTime() / 1000000L;
	}

	public void reset() {
		this.prevTime = this.getTime();
	}
}
