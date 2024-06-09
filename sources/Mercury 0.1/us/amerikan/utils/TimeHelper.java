/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

public class TimeHelper {
    private static long LastMS = 0L;
    private long previousTime;
    private long resetMS = 0L;
    long startTime;

    public boolean isDelayComplete(float f2) {
        return (float)(System.currentTimeMillis() - LastMS) >= f2;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long LastMS) {
        TimeHelper.LastMS = System.currentTimeMillis();
    }

    public int convertToMS(int perSecond) {
        return 1000 / perSecond;
    }

    public static boolean hasReached(long millisecond) {
        return TimeHelper.getCurrentMS() - LastMS >= millisecond;
    }

    public static void reset() {
        LastMS = TimeHelper.getCurrentMS();
    }

    public void setLastMS() {
        LastMS = System.currentTimeMillis();
    }

    public boolean hasReached1(double lol) {
        return (double)(TimeHelper.getCurrentMS() - LastMS) >= lol;
    }

    public boolean delay(float milliSec) {
        return (float)(TimeHelper.getCurrentMS() - LastMS) >= milliSec;
    }

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - LastMS >= delay;
    }

    public boolean check(float milliseconds) {
        return (float)(TimeHelper.getCurrentMS() - this.previousTime) >= milliseconds;
    }

    public void resetAndAdd(long reset) {
        this.resetMS = TimeHelper.getCurrentMS() + reset;
    }

    public boolean hasDelayRun(double d2) {
        return (double)TimeHelper.getCurrentMS() >= (double)this.resetMS + d2;
    }

    public long getTimeSinceReset() {
        return TimeHelper.getCurrentMS() - LastMS;
    }

    public boolean hasMSPassed(long toPass) {
        return this.getMSPassed() >= toPass;
    }

    public long getMSPassed() {
        return System.currentTimeMillis() - this.startTime;
    }

    public boolean isTime(float time) {
        return this.currentTime() >= time * 1000.0f;
    }

    public float currentTime() {
        return this.systemTime() - this.previousTime;
    }

    public long systemTime() {
        return System.currentTimeMillis();
    }

    public boolean hasReached(float milliseconds) {
        return (float)(TimeHelper.getCurrentMS() - LastMS) >= milliseconds;
    }
}

