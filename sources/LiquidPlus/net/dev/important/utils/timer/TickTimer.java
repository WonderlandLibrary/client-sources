/*
 * Decompiled with CFR 0.152.
 */
package net.dev.important.utils.timer;

public final class TickTimer {
    public int tick;

    public void update() {
        ++this.tick;
    }

    public void reset() {
        this.tick = 0;
    }

    public boolean hasTimePassed(int ticks) {
        return this.tick >= ticks;
    }
}

