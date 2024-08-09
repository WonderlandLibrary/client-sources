/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.OrderedEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public final class NonStickyEventExecutorGroup
implements EventExecutorGroup {
    private final EventExecutorGroup group;
    private final int maxTaskExecutePerRun;

    public NonStickyEventExecutorGroup(EventExecutorGroup eventExecutorGroup) {
        this(eventExecutorGroup, 1024);
    }

    public NonStickyEventExecutorGroup(EventExecutorGroup eventExecutorGroup, int n) {
        this.group = NonStickyEventExecutorGroup.verify(eventExecutorGroup);
        this.maxTaskExecutePerRun = ObjectUtil.checkPositive(n, "maxTaskExecutePerRun");
    }

    private static EventExecutorGroup verify(EventExecutorGroup eventExecutorGroup) {
        for (EventExecutor eventExecutor : ObjectUtil.checkNotNull(eventExecutorGroup, "group")) {
            if (!(eventExecutor instanceof OrderedEventExecutor)) continue;
            throw new IllegalArgumentException("EventExecutorGroup " + eventExecutorGroup + " contains OrderedEventExecutors: " + eventExecutor);
        }
        return eventExecutorGroup;
    }

    private NonStickyOrderedEventExecutor newExecutor(EventExecutor eventExecutor) {
        return new NonStickyOrderedEventExecutor(eventExecutor, this.maxTaskExecutePerRun);
    }

    @Override
    public boolean isShuttingDown() {
        return this.group.isShuttingDown();
    }

    @Override
    public Future<?> shutdownGracefully() {
        return this.group.shutdownGracefully();
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        return this.group.shutdownGracefully(l, l2, timeUnit);
    }

    @Override
    public Future<?> terminationFuture() {
        return this.group.terminationFuture();
    }

    @Override
    public void shutdown() {
        this.group.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.group.shutdownNow();
    }

    @Override
    public EventExecutor next() {
        return this.newExecutor(this.group.next());
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        Iterator<EventExecutor> iterator2 = this.group.iterator();
        return new Iterator<EventExecutor>(this, iterator2){
            final Iterator val$itr;
            final NonStickyEventExecutorGroup this$0;
            {
                this.this$0 = nonStickyEventExecutorGroup;
                this.val$itr = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$itr.hasNext();
            }

            @Override
            public EventExecutor next() {
                return NonStickyEventExecutorGroup.access$000(this.this$0, (EventExecutor)this.val$itr.next());
            }

            @Override
            public void remove() {
                this.val$itr.remove();
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return this.group.submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T t) {
        return this.group.submit(runnable, t);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.group.submit(callable);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.group.schedule(runnable, l, timeUnit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return this.group.schedule(callable, l, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.group.scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.group.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
    }

    @Override
    public boolean isShutdown() {
        return this.group.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.group.isTerminated();
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.group.awaitTermination(l, timeUnit);
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        return this.group.invokeAll(collection);
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.group.invokeAll(collection, l, timeUnit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        return this.group.invokeAny(collection);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.group.invokeAny(collection, l, timeUnit);
    }

    @Override
    public void execute(Runnable runnable) {
        this.group.execute(runnable);
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

    static NonStickyOrderedEventExecutor access$000(NonStickyEventExecutorGroup nonStickyEventExecutorGroup, EventExecutor eventExecutor) {
        return nonStickyEventExecutorGroup.newExecutor(eventExecutor);
    }

    private static final class NonStickyOrderedEventExecutor
    extends AbstractEventExecutor
    implements Runnable,
    OrderedEventExecutor {
        private final EventExecutor executor;
        private final Queue<Runnable> tasks = PlatformDependent.newMpscQueue();
        private static final int NONE = 0;
        private static final int SUBMITTED = 1;
        private static final int RUNNING = 2;
        private final AtomicInteger state = new AtomicInteger();
        private final int maxTaskExecutePerRun;

        NonStickyOrderedEventExecutor(EventExecutor eventExecutor, int n) {
            super(eventExecutor);
            this.executor = eventExecutor;
            this.maxTaskExecutePerRun = n;
        }

        @Override
        public void run() {
            if (!this.state.compareAndSet(1, 1)) {
                return;
            }
            block7: while (true) {
                int n = 0;
                try {
                    while (true) {
                        Runnable runnable;
                        if (n >= this.maxTaskExecutePerRun || (runnable = this.tasks.poll()) == null) continue block7;
                        NonStickyOrderedEventExecutor.safeExecute(runnable);
                        ++n;
                    }
                } finally {
                    if (n == this.maxTaskExecutePerRun) {
                        try {
                            this.state.set(1);
                            this.executor.execute(this);
                            return;
                        } catch (Throwable throwable) {
                            this.state.set(2);
                        }
                    } else {
                        this.state.set(0);
                        return;
                    }
                    continue;
                }
                break;
            }
        }

        @Override
        public boolean inEventLoop(Thread thread2) {
            return true;
        }

        @Override
        public boolean inEventLoop() {
            return true;
        }

        @Override
        public boolean isShuttingDown() {
            return this.executor.isShutdown();
        }

        @Override
        public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
            return this.executor.shutdownGracefully(l, l2, timeUnit);
        }

        @Override
        public Future<?> terminationFuture() {
            return this.executor.terminationFuture();
        }

        @Override
        public void shutdown() {
            this.executor.shutdown();
        }

        @Override
        public boolean isShutdown() {
            return this.executor.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return this.executor.isTerminated();
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.executor.awaitTermination(l, timeUnit);
        }

        @Override
        public void execute(Runnable runnable) {
            if (!this.tasks.offer(runnable)) {
                throw new RejectedExecutionException();
            }
            if (this.state.compareAndSet(0, 0)) {
                try {
                    this.executor.execute(this);
                } catch (Throwable throwable) {
                    this.tasks.remove(runnable);
                    PlatformDependent.throwException(throwable);
                }
            }
        }
    }
}

