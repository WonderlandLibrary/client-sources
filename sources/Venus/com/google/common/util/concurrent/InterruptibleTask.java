/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible(emulated=true)
abstract class InterruptibleTask
implements Runnable {
    private volatile Thread runner;
    private volatile boolean doneInterrupting;
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log;

    InterruptibleTask() {
    }

    @Override
    public final void run() {
        if (!ATOMIC_HELPER.compareAndSetRunner(this, null, Thread.currentThread())) {
            return;
        }
        try {
            this.runInterruptibly();
        } finally {
            if (this.wasInterrupted()) {
                while (!this.doneInterrupting) {
                    Thread.yield();
                }
            }
        }
    }

    abstract void runInterruptibly();

    abstract boolean wasInterrupted();

    final void interruptTask() {
        Thread thread2 = this.runner;
        if (thread2 != null) {
            thread2.interrupt();
        }
        this.doneInterrupting = true;
    }

    static Thread access$200(InterruptibleTask interruptibleTask) {
        return interruptibleTask.runner;
    }

    static Thread access$202(InterruptibleTask interruptibleTask, Thread thread2) {
        interruptibleTask.runner = thread2;
        return interruptibleTask.runner;
    }

    static {
        AtomicHelper atomicHelper;
        log = Logger.getLogger(InterruptibleTask.class.getName());
        try {
            atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(InterruptibleTask.class, Thread.class, "runner"));
        } catch (Throwable throwable) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", throwable);
            atomicHelper = new SynchronizedAtomicHelper(null);
        }
        ATOMIC_HELPER = atomicHelper;
    }

    private static final class SynchronizedAtomicHelper
    extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super(null);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        boolean compareAndSetRunner(InterruptibleTask interruptibleTask, Thread thread2, Thread thread3) {
            InterruptibleTask interruptibleTask2 = interruptibleTask;
            synchronized (interruptibleTask2) {
                if (InterruptibleTask.access$200(interruptibleTask) == thread2) {
                    InterruptibleTask.access$202(interruptibleTask, thread3);
                }
            }
            return false;
        }

        SynchronizedAtomicHelper(1 var1_1) {
            this();
        }
    }

    private static final class SafeAtomicHelper
    extends AtomicHelper {
        final AtomicReferenceFieldUpdater<InterruptibleTask, Thread> runnerUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater) {
            super(null);
            this.runnerUpdater = atomicReferenceFieldUpdater;
        }

        @Override
        boolean compareAndSetRunner(InterruptibleTask interruptibleTask, Thread thread2, Thread thread3) {
            return this.runnerUpdater.compareAndSet(interruptibleTask, thread2, thread3);
        }
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract boolean compareAndSetRunner(InterruptibleTask var1, Thread var2, Thread var3);

        AtomicHelper(1 var1_1) {
            this();
        }
    }
}

