/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractEventExecutorGroup
implements EventExecutorGroup {
    @Override
    public Future<?> submit(Runnable runnable) {
        return this.next().submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T t) {
        return this.next().submit(runnable, t);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.next().submit(callable);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.next().schedule(runnable, l, timeUnit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return this.next().schedule(callable, l, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.next().scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.next().scheduleWithFixedDelay(runnable, l, l2, timeUnit);
    }

    @Override
    public Future<?> shutdownGracefully() {
        return this.shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
    }

    @Override
    @Deprecated
    public abstract void shutdown();

    @Override
    @Deprecated
    public List<Runnable> shutdownNow() {
        this.shutdown();
        return Collections.emptyList();
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.next().invokeAll(collection);
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.next().invokeAll(collection, l, timeUnit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.next().invokeAny(collection);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.next().invokeAny(collection, l, timeUnit);
    }

    @Override
    public void execute(Runnable runnable) {
        this.next().execute(runnable);
    }

    public java.util.concurrent.ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
    }

    public java.util.concurrent.ScheduledFuture scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    public java.util.concurrent.ScheduledFuture schedule(Callable callable, long l, TimeUnit timeUnit) {
        return this.schedule(callable, l, timeUnit);
    }

    public java.util.concurrent.ScheduledFuture schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.schedule(runnable, l, timeUnit);
    }

    public java.util.concurrent.Future submit(Runnable runnable) {
        return this.submit(runnable);
    }

    public java.util.concurrent.Future submit(Runnable runnable, Object object) {
        return this.submit(runnable, object);
    }

    public java.util.concurrent.Future submit(Callable callable) {
        return this.submit(callable);
    }
}

