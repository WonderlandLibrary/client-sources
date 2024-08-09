/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.util.concurrent.AggregateFuture;
import com.google.common.util.concurrent.AsyncCallable;
import com.google.common.util.concurrent.InterruptibleTask;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;

@GwtCompatible
final class CombinedFuture<V>
extends AggregateFuture<Object, V> {
    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean bl, Executor executor, AsyncCallable<V> asyncCallable) {
        this.init(new CombinedFutureRunningState(this, immutableCollection, bl, new AsyncCallableInterruptibleTask(this, asyncCallable, executor)));
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> immutableCollection, boolean bl, Executor executor, Callable<V> callable) {
        this.init(new CombinedFutureRunningState(this, immutableCollection, bl, new CallableInterruptibleTask(this, callable, executor)));
    }

    private final class CallableInterruptibleTask
    extends CombinedFutureInterruptibleTask {
        private final Callable<V> callable;
        final CombinedFuture this$0;

        public CallableInterruptibleTask(CombinedFuture combinedFuture, Callable<V> callable, Executor executor) {
            this.this$0 = combinedFuture;
            super(combinedFuture, executor);
            this.callable = Preconditions.checkNotNull(callable);
        }

        @Override
        void setValue() throws Exception {
            this.this$0.set(this.callable.call());
        }
    }

    private final class AsyncCallableInterruptibleTask
    extends CombinedFutureInterruptibleTask {
        private final AsyncCallable<V> callable;
        final CombinedFuture this$0;

        public AsyncCallableInterruptibleTask(CombinedFuture combinedFuture, AsyncCallable<V> asyncCallable, Executor executor) {
            this.this$0 = combinedFuture;
            super(combinedFuture, executor);
            this.callable = Preconditions.checkNotNull(asyncCallable);
        }

        @Override
        void setValue() throws Exception {
            this.this$0.setFuture(this.callable.call());
        }
    }

    private abstract class CombinedFutureInterruptibleTask
    extends InterruptibleTask {
        private final Executor listenerExecutor;
        volatile boolean thrownByExecute;
        final CombinedFuture this$0;

        public CombinedFutureInterruptibleTask(CombinedFuture combinedFuture, Executor executor) {
            this.this$0 = combinedFuture;
            this.thrownByExecute = true;
            this.listenerExecutor = Preconditions.checkNotNull(executor);
        }

        @Override
        final void runInterruptibly() {
            this.thrownByExecute = false;
            if (!this.this$0.isDone()) {
                try {
                    this.setValue();
                } catch (ExecutionException executionException) {
                    this.this$0.setException(executionException.getCause());
                } catch (CancellationException cancellationException) {
                    this.this$0.cancel(true);
                } catch (Throwable throwable) {
                    this.this$0.setException(throwable);
                }
            }
        }

        @Override
        final boolean wasInterrupted() {
            return this.this$0.wasInterrupted();
        }

        final void execute() {
            block2: {
                try {
                    this.listenerExecutor.execute(this);
                } catch (RejectedExecutionException rejectedExecutionException) {
                    if (!this.thrownByExecute) break block2;
                    this.this$0.setException(rejectedExecutionException);
                }
            }
        }

        abstract void setValue() throws Exception;
    }

    private final class CombinedFutureRunningState
    extends AggregateFuture.RunningState {
        private CombinedFutureInterruptibleTask task;
        final CombinedFuture this$0;

        CombinedFutureRunningState(CombinedFuture combinedFuture, ImmutableCollection<? extends ListenableFuture<? extends Object>> immutableCollection, boolean bl, CombinedFutureInterruptibleTask combinedFutureInterruptibleTask) {
            this.this$0 = combinedFuture;
            super(combinedFuture, immutableCollection, bl, false);
            this.task = combinedFutureInterruptibleTask;
        }

        void collectOneValue(boolean bl, int n, @Nullable Object object) {
        }

        @Override
        void handleAllCompleted() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.execute();
            } else {
                Preconditions.checkState(this.this$0.isDone());
            }
        }

        @Override
        void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.task = null;
        }

        @Override
        void interruptTask() {
            CombinedFutureInterruptibleTask combinedFutureInterruptibleTask = this.task;
            if (combinedFutureInterruptibleTask != null) {
                combinedFutureInterruptibleTask.interruptTask();
            }
        }
    }
}

