/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public class FrameTimer {
    private final long[] frames = new long[240];
    private int lastIndex;
    private int counter;
    private int index;

    public void addFrame(long l) {
        this.frames[this.index] = l;
        ++this.index;
        if (this.index == 240) {
            this.index = 0;
        }
        if (this.counter < 240) {
            this.lastIndex = 0;
            ++this.counter;
        } else {
            this.lastIndex = this.parseIndex(this.index + 1);
        }
    }

    public int getLineHeight(long l, int n, int n2) {
        double d = (double)l / (double)(1000000000L / (long)n2);
        return (int)(d * (double)n);
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    public int getIndex() {
        return this.index;
    }

    public int parseIndex(int n) {
        return n % 240;
    }

    public long[] getFrames() {
        return this.frames;
    }
}

