// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

public final class Timer {
    private long time;

    public Timer() {
        this.reset();
    }

    public long getNanoTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasElapsed(final double milliseconds) {
        return this.getNanoTime() - this.time >= milliseconds;
    }

    public void reset() {
        this.time = this.getNanoTime();
    }

    public boolean hasPassedDelay(final float milliSec) {
        return this.toMilliseconds() - this.time >= milliSec;
    }

    public long toMilliseconds() {
        return System.nanoTime() / 1000000L;
    }
}