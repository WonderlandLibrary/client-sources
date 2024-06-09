/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.timer;

import java.util.Random;

public class TimerUtils {
    private static long prevMS;
    private long currentMs = 0L;
    private long lastMs = -1L;

    public TimerUtils() {
        prevMS = 0L;
    }

    public static boolean hD(double d) {
        return (double)(TimerUtils.getTime() - prevMS) >= 1000.0 / d;
    }

    public static boolean hasRandomDelay(double d, Random rand) {
        return (double)(TimerUtils.getTime() - prevMS) >= 1000.0 / d;
    }

    public static void rt() {
        prevMS = TimerUtils.getTime();
    }

    public static long getTime() {
        return System.nanoTime() / 1000000L;
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

    public static boolean hasReached(long milliseconds) {
        return (float)(TimerUtils.getTime() - prevMS) >= 1000.0f / (float)milliseconds;
    }
}

