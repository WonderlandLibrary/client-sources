/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.InterruptibleTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import javax.annotation.Nullable;

@GwtCompatible
class TrustedListenableFutureTask<V>
extends AbstractFuture.TrustedFuture<V>
implements RunnableFuture<V> {
    private TrustedFutureInterruptibleTask task;

    static <V> TrustedListenableFutureTask<V> create(Callable<V> callable) {
        return new TrustedListenableFutureTask<V>(callable);
    }

    static <V> TrustedListenableFutureTask<V> create(Runnable runnable, @Nullable V v) {
        return new TrustedListenableFutureTask<V>(Executors.callable(runnable, v));
    }

    TrustedListenableFutureTask(Callable<V> callable) {
        this.task = new TrustedFutureInterruptibleTask(this, callable);
    }

    @Override
    public void run() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.task;
        if (trustedFutureInterruptibleTask != null) {
            trustedFutureInterruptibleTask.run();
        }
    }

    @Override
    protected void afterDone() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask;
        super.afterDone();
        if (this.wasInterrupted() && (trustedFutureInterruptibleTask = this.task) != null) {
            trustedFutureInterruptibleTask.interruptTask();
        }
        this.task = null;
    }

    public String toString() {
        return super.toString() + " (delegate = " + this.task + ")";
    }

    private final class TrustedFutureInterruptibleTask
    extends InterruptibleTask {
        private final Callable<V> callable;
        final TrustedListenableFutureTask this$0;

        TrustedFutureInterruptibleTask(TrustedListenableFutureTask trustedListenableFutureTask, Callable<V> callable) {
            this.this$0 = trustedListenableFutureTask;
            this.callable = Preconditions.checkNotNull(callable);
        }

        @Override
        void runInterruptibly() {
            if (!this.this$0.isDone()) {
                try {
                    this.this$0.set(this.callable.call());
                } catch (Throwable throwable) {
                    this.this$0.setException(throwable);
                }
            }
        }

        @Override
        boolean wasInterrupted() {
            return this.this$0.wasInterrupted();
        }

        public String toString() {
            return this.callable.toString();
        }
    }
}

