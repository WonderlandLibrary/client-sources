/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FailedFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseTask;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.concurrent.SucceededFuture;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public abstract class AbstractEventExecutor
extends AbstractExecutorService
implements EventExecutor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractEventExecutor.class);
    static final long DEFAULT_SHUTDOWN_QUIET_PERIOD = 2L;
    static final long DEFAULT_SHUTDOWN_TIMEOUT = 15L;
    private final EventExecutorGroup parent;
    private final Collection<EventExecutor> selfCollection = Collections.singleton(this);

    protected AbstractEventExecutor() {
        this(null);
    }

    protected AbstractEventExecutor(EventExecutorGroup eventExecutorGroup) {
        this.parent = eventExecutorGroup;
    }

    @Override
    public EventExecutorGroup parent() {
        return this.parent;
    }

    @Override
    public EventExecutor next() {
        return this;
    }

    @Override
    public boolean inEventLoop() {
        return this.inEventLoop(Thread.currentThread());
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return this.selfCollection.iterator();
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
    public <V> Promise<V> newPromise() {
        return new DefaultPromise(this);
    }

    @Override
    public <V> ProgressivePromise<V> newProgressivePromise() {
        return new DefaultProgressivePromise(this);
    }

    @Override
    public <V> Future<V> newSucceededFuture(V v) {
        return new SucceededFuture<V>(this, v);
    }

    @Override
    public <V> Future<V> newFailedFuture(Throwable throwable) {
        return new FailedFuture(this, throwable);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return (Future)super.submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T t) {
        return (Future)super.submit(runnable, t);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return (Future)super.submit(callable);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new PromiseTask<T>(this, runnable, t);
    }

    @Override
    protected final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PromiseTask<T>(this, callable);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    protected static void safeExecute(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            logger.warn("A task raised an exception. Task: {}", (Object)runnable, (Object)throwable);
        }
    }

    public java.util.concurrent.Future submit(Callable callable) {
        return this.submit(callable);
    }

    public java.util.concurrent.Future submit(Runnable runnable, Object object) {
        return this.submit(runnable, object);
    }

    public java.util.concurrent.Future submit(Runnable runnable) {
        return this.submit(runnable);
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
}

