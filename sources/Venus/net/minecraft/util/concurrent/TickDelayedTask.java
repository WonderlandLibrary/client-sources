/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

public class TickDelayedTask
implements Runnable {
    private final int field_218824_a;
    private final Runnable field_218825_b;

    public TickDelayedTask(int n, Runnable runnable) {
        this.field_218824_a = n;
        this.field_218825_b = runnable;
    }

    public int getScheduledTime() {
        return this.field_218824_a;
    }

    @Override
    public void run() {
        this.field_218825_b.run();
    }
}

