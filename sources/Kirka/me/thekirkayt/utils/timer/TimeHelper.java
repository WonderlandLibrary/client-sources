/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.timer;

public class TimeHelper {
    private long lastMS;
    private long prevTime;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS() {
        return this.lastMS;
    }

    public boolean hasPassed(double milli) {
        return (double)(this.getTime() - this.prevTime) >= milli;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public long getTime() {
        return this.getCurrentMS() - this.lastMS;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public void setLastMS(long currentMS) {
        this.lastMS = currentMS;
    }
}

