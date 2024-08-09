/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultEventExecutorChooserFactory;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup
extends AbstractEventExecutorGroup {
    private final EventExecutor[] children;
    private final Set<EventExecutor> readonlyChildren;
    private final AtomicInteger terminatedChildren = new AtomicInteger();
    private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
    private final EventExecutorChooserFactory.EventExecutorChooser chooser;

    protected MultithreadEventExecutorGroup(int n, ThreadFactory threadFactory, Object ... objectArray) {
        this(n, (Executor)(threadFactory == null ? null : new ThreadPerTaskExecutor(threadFactory)), objectArray);
    }

    protected MultithreadEventExecutorGroup(int n, Executor executor, Object ... objectArray) {
        this(n, executor, DefaultEventExecutorChooserFactory.INSTANCE, objectArray);
    }

    protected MultithreadEventExecutorGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, Object ... objectArray) {
        if (n <= 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", n));
        }
        if (executor == null) {
            executor = new ThreadPerTaskExecutor(this.newDefaultThreadFactory());
        }
        this.children = new EventExecutor[n];
        for (int i = 0; i < n; ++i) {
            boolean bl = false;
            try {
                this.children[i] = this.newChild(executor, objectArray);
                bl = true;
                continue;
            } catch (Exception exception) {
                throw new IllegalStateException("failed to create a child event loop", exception);
            } finally {
                if (!bl) {
                    int n2;
                    for (n2 = 0; n2 < i; ++n2) {
                        this.children[n2].shutdownGracefully();
                    }
                    for (n2 = 0; n2 < i; ++n2) {
                        EventExecutor eventExecutor = this.children[n2];
                        try {
                            while (!eventExecutor.isTerminated()) {
                                eventExecutor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                            }
                            continue;
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            }
        }
        this.chooser = eventExecutorChooserFactory.newChooser(this.children);
        FutureListener<Object> futureListener = new FutureListener<Object>(this){
            final MultithreadEventExecutorGroup this$0;
            {
                this.this$0 = multithreadEventExecutorGroup;
            }

            @Override
            public void operationComplete(Future<Object> future) throws Exception {
                if (MultithreadEventExecutorGroup.access$000(this.this$0).incrementAndGet() == MultithreadEventExecutorGroup.access$100(this.this$0).length) {
                    MultithreadEventExecutorGroup.access$200(this.this$0).setSuccess(null);
                }
            }
        };
        for (EventExecutor eventExecutor : this.children) {
            eventExecutor.terminationFuture().addListener(futureListener);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(this.children.length);
        Collections.addAll(linkedHashSet, this.children);
        this.readonlyChildren = Collections.unmodifiableSet(linkedHashSet);
    }

    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(this.getClass());
    }

    @Override
    public EventExecutor next() {
        return this.chooser.next();
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return this.readonlyChildren.iterator();
    }

    public final int executorCount() {
        return this.children.length;
    }

    protected abstract EventExecutor newChild(Executor var1, Object ... var2) throws Exception;

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        for (EventExecutor eventExecutor : this.children) {
            eventExecutor.shutdownGracefully(l, l2, timeUnit);
        }
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    @Deprecated
    public void shutdown() {
        for (EventExecutor eventExecutor : this.children) {
            eventExecutor.shutdown();
        }
    }

    @Override
    public boolean isShuttingDown() {
        for (EventExecutor eventExecutor : this.children) {
            if (eventExecutor.isShuttingDown()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isShutdown() {
        for (EventExecutor eventExecutor : this.children) {
            if (eventExecutor.isShutdown()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isTerminated() {
        for (EventExecutor eventExecutor : this.children) {
            if (eventExecutor.isTerminated()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        long l2 = System.nanoTime() + timeUnit.toNanos(l);
        block0: for (EventExecutor eventExecutor : this.children) {
            long l3;
            while ((l3 = l2 - System.nanoTime()) > 0L) {
                if (!eventExecutor.awaitTermination(l3, TimeUnit.NANOSECONDS)) continue;
                continue block0;
            }
            break block0;
        }
        return this.isTerminated();
    }

    static AtomicInteger access$000(MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.terminatedChildren;
    }

    static EventExecutor[] access$100(MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.children;
    }

    static Promise access$200(MultithreadEventExecutorGroup multithreadEventExecutorGroup) {
        return multithreadEventExecutorGroup.terminationFuture;
    }
}

