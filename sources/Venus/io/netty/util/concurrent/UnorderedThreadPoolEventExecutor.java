/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultProgressivePromise;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FailedFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ProgressivePromise;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseTask;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.concurrent.SucceededFuture;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class UnorderedThreadPoolEventExecutor
extends ScheduledThreadPoolExecutor
implements EventExecutor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(UnorderedThreadPoolEventExecutor.class);
    private final Promise<?> terminationFuture = GlobalEventExecutor.INSTANCE.newPromise();
    private final Set<EventExecutor> executorSet = Collections.singleton(this);

    public UnorderedThreadPoolEventExecutor(int n) {
        this(n, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class));
    }

    public UnorderedThreadPoolEventExecutor(int n, ThreadFactory threadFactory) {
        super(n, threadFactory);
    }

    public UnorderedThreadPoolEventExecutor(int n, RejectedExecutionHandler rejectedExecutionHandler) {
        this(n, new DefaultThreadFactory(UnorderedThreadPoolEventExecutor.class), rejectedExecutionHandler);
    }

    public UnorderedThreadPoolEventExecutor(int n, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, threadFactory, rejectedExecutionHandler);
    }

    @Override
    public EventExecutor next() {
        return this;
    }

    @Override
    public EventExecutorGroup parent() {
        return this;
    }

    @Override
    public boolean inEventLoop() {
        return true;
    }

    @Override
    public boolean inEventLoop(Thread thread2) {
        return true;
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
    public boolean isShuttingDown() {
        return this.isShutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        List<Runnable> list = super.shutdownNow();
        this.terminationFuture.trySuccess(null);
        return list;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        this.terminationFuture.trySuccess(null);
    }

    @Override
    public Future<?> shutdownGracefully() {
        return this.shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        this.shutdown();
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return this.executorSet.iterator();
    }

    @Override
    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> runnableScheduledFutureTask) {
        return runnable instanceof NonNotifyRunnable ? runnableScheduledFutureTask : new RunnableScheduledFutureTask((EventExecutor)this, runnable, runnableScheduledFutureTask);
    }

    @Override
    protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> runnableScheduledFuture) {
        return new RunnableScheduledFutureTask<V>((EventExecutor)this, callable, runnableScheduledFuture);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return (ScheduledFuture)super.schedule(runnable, l, timeUnit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return (ScheduledFuture)super.schedule(callable, l, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return (ScheduledFuture)super.scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return (ScheduledFuture)super.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
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
    public void execute(Runnable runnable) {
        super.schedule(new NonNotifyRunnable(runnable), 0L, TimeUnit.NANOSECONDS);
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

    static InternalLogger access$000() {
        return logger;
    }

    private static final class NonNotifyRunnable
    implements Runnable {
        private final Runnable task;

        NonNotifyRunnable(Runnable runnable) {
            this.task = runnable;
        }

        @Override
        public void run() {
            this.task.run();
        }
    }

    private static final class RunnableScheduledFutureTask<V>
    extends PromiseTask<V>
    implements RunnableScheduledFuture<V>,
    ScheduledFuture<V> {
        private final RunnableScheduledFuture<V> future;

        RunnableScheduledFutureTask(EventExecutor eventExecutor, Runnable runnable, RunnableScheduledFuture<V> runnableScheduledFuture) {
            super(eventExecutor, runnable, null);
            this.future = runnableScheduledFuture;
        }

        RunnableScheduledFutureTask(EventExecutor eventExecutor, Callable<V> callable, RunnableScheduledFuture<V> runnableScheduledFuture) {
            super(eventExecutor, callable);
            this.future = runnableScheduledFuture;
        }

        @Override
        public void run() {
            block5: {
                if (!this.isPeriodic()) {
                    super.run();
                } else if (!this.isDone()) {
                    try {
                        this.task.call();
                    } catch (Throwable throwable) {
                        if (this.tryFailureInternal(throwable)) break block5;
                        UnorderedThreadPoolEventExecutor.access$000().warn("Failure during execution of task", throwable);
                    }
                }
            }
        }

        @Override
        public boolean isPeriodic() {
            return this.future.isPeriodic();
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return this.future.getDelay(timeUnit);
        }

        @Override
        public int compareTo(Delayed delayed) {
            return this.future.compareTo(delayed);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Delayed)object);
        }
    }
}

