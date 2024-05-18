/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

public class Timer {
    private static long prevMS;
    private long lastMS;

    public Timer() {
        prevMS = 0L;
    }

    public static boolean delay(float milliSec) {
        return (float)(Timer.getTime() - prevMS) >= milliSec;
    }

    public void reset() {
        prevMS = Timer.getTime();
    }

    public long getLastMS() {
        return prevMS;
    }

    public static long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getDifference() {
        return Timer.getTime() - prevMS;
    }

    public void setDifference(long difference) {
        prevMS = Timer.getTime() - difference;
    }

    public void setLastMS() {
        prevMS = System.currentTimeMillis();
    }

    public static boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - prevMS >= delay;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public boolean hasReached(float milliseconds) {
        return (float)(this.getCurrentMS() - this.lastMS) >= milliseconds;
    }

    public boolean hasReached(Float milliseconds) {
        return (float)(this.getCurrentMS() - this.lastMS) >= milliseconds.floatValue();
    }

    public void setLastMS(long currentMS) {
        this.lastMS = currentMS;
    }
}

