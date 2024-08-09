/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

import net.minecraft.util.concurrent.ThreadTaskExecutor;

public abstract class RecursiveEventLoop<R extends Runnable>
extends ThreadTaskExecutor<R> {
    private int running;

    public RecursiveEventLoop(String string) {
        super(string);
    }

    @Override
    protected boolean shouldDeferTasks() {
        return this.isTaskRunning() || super.shouldDeferTasks();
    }

    protected boolean isTaskRunning() {
        return this.running != 0;
    }

    @Override
    protected void run(R r) {
        ++this.running;
        try {
            super.run(r);
        } finally {
            --this.running;
        }
    }
}

