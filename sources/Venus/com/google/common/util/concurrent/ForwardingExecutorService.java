/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@CanIgnoreReturnValue
@GwtIncompatible
public abstract class ForwardingExecutorService
extends ForwardingObject
implements ExecutorService {
    protected ForwardingExecutorService() {
    }

    @Override
    protected abstract ExecutorService delegate();

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().awaitTermination(l, timeUnit);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.delegate().invokeAll(collection);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.delegate().invokeAll(collection, l, timeUnit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.delegate().invokeAny(collection);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().invokeAny(collection, l, timeUnit);
    }

    @Override
    public boolean isShutdown() {
        return this.delegate().isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.delegate().isTerminated();
    }

    @Override
    public void shutdown() {
        this.delegate().shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate().shutdownNow();
    }

    @Override
    public void execute(Runnable runnable) {
        this.delegate().execute(runnable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.delegate().submit(callable);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return this.delegate().submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T t) {
        return this.delegate().submit(runnable, t);
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

