/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.timer;

public class TimerUtil {
    private long prevMS = 0L;
    private long currentMs = 0L;
    private long lastMs = -1L;
    private long previousTime;

    public boolean hasDelay(double d) {
        return (double)(this.getTime() - this.prevMS) >= 1000.0 / d;
    }

    public void reset() {
        this.prevMS = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean a(long milliseconds) {
        return this.getCurrentMS() - this.prevMS >= milliseconds;
    }

    public void updateMs() {
        this.currentMs = System.currentTimeMillis();
    }

    public void setLastMs() {
        this.lastMs = System.currentTimeMillis();
    }

    public boolean hasPassed(long Ms) {
        return this.currentMs > this.lastMs + Ms;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS() {
        return this.lastMs;
    }

    public static long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasTimePassedM(long MS) {
        return this.currentMs >= this.lastMs + MS;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMs >= milliseconds;
    }

    public boolean check(float milliseconds) {
        return (float)(TimerUtil.getCurrentTime() - this.previousTime) >= milliseconds;
    }

    public boolean hasPassed(double milli) {
        return (double)(this.getTime() - this.previousTime) >= milli;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastMs >= delay;
    }

    public int convertToMS(int perSecond) {
        return 1000 / perSecond;
    }
}

