/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.util.concurrent.locks.LockSupport;
import net.minecraft.util.concurrent.ThreadTaskExecutor;

public class SoundEngineExecutor
extends ThreadTaskExecutor<Runnable> {
    private Thread executionThread = this.createExecutionThread();
    private volatile boolean stopped;

    public SoundEngineExecutor() {
        super("Sound executor");
    }

    private Thread createExecutionThread() {
        Thread thread2 = new Thread(this::run);
        thread2.setDaemon(false);
        thread2.setName("Sound engine");
        thread2.start();
        return thread2;
    }

    @Override
    protected Runnable wrapTask(Runnable runnable) {
        return runnable;
    }

    @Override
    protected boolean canRun(Runnable runnable) {
        return !this.stopped;
    }

    @Override
    protected Thread getExecutionThread() {
        return this.executionThread;
    }

    private void run() {
        while (!this.stopped) {
            this.driveUntil(this::lambda$run$0);
        }
    }

    @Override
    protected void threadYieldPark() {
        LockSupport.park("waiting for tasks");
    }

    public void restart() {
        this.stopped = true;
        this.executionThread.interrupt();
        try {
            this.executionThread.join();
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
        this.dropTasks();
        this.stopped = false;
        this.executionThread = this.createExecutionThread();
    }

    private boolean lambda$run$0() {
        return this.stopped;
    }
}

