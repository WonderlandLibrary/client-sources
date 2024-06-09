
package me.gishreload.yukon.utils;

public final class Timer {
	long startTime;

	public Timer() {
	this.reset();
	}

	public void reset() {
	this.startTime = System.currentTimeMillis();
	}

	public long getMSPassed() {
	return System.currentTimeMillis() - this.startTime;
	}

	public void add(final int amount) {
	this.startTime -= amount;
	}

	public void subtract(final int amount) {
	this.startTime += amount;
	}

	public boolean hasMSPassed(final long toPass) {
	return this.getMSPassed() >= toPass;
	}
	}

