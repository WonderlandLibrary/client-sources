/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@CanIgnoreReturnValue
@GwtIncompatible
abstract class WrappingExecutorService
implements ExecutorService {
    private final ExecutorService delegate;

    protected WrappingExecutorService(ExecutorService executorService) {
        this.delegate = Preconditions.checkNotNull(executorService);
    }

    protected abstract <T> Callable<T> wrapTask(Callable<T> var1);

    protected Runnable wrapTask(Runnable runnable) {
        Callable<Object> callable = this.wrapTask(Executors.callable(runnable, null));
        return new Runnable(this, callable){
            final Callable val$wrapped;
            final WrappingExecutorService this$0;
            {
                this.this$0 = wrappingExecutorService;
                this.val$wrapped = callable;
            }

            @Override
            public void run() {
                try {
                    this.val$wrapped.call();
                } catch (Exception exception) {
                    Throwables.throwIfUnchecked(exception);
                    throw new RuntimeException(exception);
                }
            }
        };
    }

    private final <T> ImmutableList<Callable<T>> wrapTasks(Collection<? extends Callable<T>> collection) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Callable<T> callable : collection) {
            builder.add(this.wrapTask(callable));
        }
        return builder.build();
    }

    @Override
    public final void execute(Runnable runnable) {
        this.delegate.execute(this.wrapTask(runnable));
    }

    @Override
    public final <T> Future<T> submit(Callable<T> callable) {
        return this.delegate.submit(this.wrapTask(Preconditions.checkNotNull(callable)));
    }

    @Override
    public final Future<?> submit(Runnable runnable) {
        return this.delegate.submit(this.wrapTask(runnable));
    }

    @Override
    public final <T> Future<T> submit(Runnable runnable, T t) {
        return this.delegate.submit(this.wrapTask(runnable), t);
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.delegate.invokeAll(this.wrapTasks(collection));
    }

    @Override
    public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.invokeAll(this.wrapTasks(collection), l, timeUnit);
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(this.wrapTasks(collection));
    }

    @Override
    public final <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(this.wrapTasks(collection), l, timeUnit);
    }

    @Override
    public final void shutdown() {
        this.delegate.shutdown();
    }

    @Override
    public final List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public final boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public final boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate.awaitTermination(l, timeUnit);
    }
}

