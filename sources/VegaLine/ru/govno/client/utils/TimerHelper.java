/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils;

public class TimerHelper {
    private long previousTime = -1L;
    private long ms = this.getCurrentMS();

    public boolean check(float milliseconds) {
        return (float)(this.getCurrentTime() - this.previousTime) >= milliseconds;
    }

    public short convert(float perSecond) {
        return (short)(1000.0f / perSecond);
    }

    public long get() {
        return this.previousTime;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    private long getCurrentMS() {
        return System.currentTimeMillis();
    }

    public boolean hasReached(float milliseconds) {
        return (float)(this.getCurrentMS() - this.ms) > milliseconds;
    }

    public void reset() {
        this.ms = this.getCurrentMS();
    }

    public long getTime() {
        return this.getCurrentMS() - this.ms;
    }
}

