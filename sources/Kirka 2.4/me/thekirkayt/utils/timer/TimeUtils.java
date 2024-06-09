/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils.timer;

public class TimeUtils {
    private long prevMS = 0L;

    public boolean delay(float milliSec) {
        return (float)(this.getTime() - this.prevMS) >= milliSec;
    }

    public void reset() {
        this.prevMS = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getDifference() {
        return this.getTime() - this.prevMS;
    }
}

