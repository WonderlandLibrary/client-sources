/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public class Timer {
    public float renderPartialTicks;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private final float tickLength;
    public float timerSpeed = 1.0f;

    public Timer(float f, long l) {
        this.tickLength = 1000.0f / f;
        this.lastSyncSysClock = l;
    }

    public int getPartialTicks(long l) {
        this.elapsedPartialTicks = (float)(l - this.lastSyncSysClock) / this.tickLength * this.timerSpeed;
        this.lastSyncSysClock = l;
        this.renderPartialTicks += this.elapsedPartialTicks;
        int n = (int)this.renderPartialTicks;
        this.renderPartialTicks -= (float)n;
        return n;
    }
}

