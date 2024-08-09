/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.PromiseTask;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.PriorityQueueNode;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

final class ScheduledFutureTask<V>
extends PromiseTask<V>
implements ScheduledFuture<V>,
PriorityQueueNode {
    private static final AtomicLong nextTaskId;
    private static final long START_TIME;
    private final long id = nextTaskId.getAndIncrement();
    private long deadlineNanos;
    private final long periodNanos;
    private int queueIndex = -1;
    static final boolean $assertionsDisabled;

    static long nanoTime() {
        return System.nanoTime() - START_TIME;
    }

    static long deadlineNanos(long l) {
        return ScheduledFutureTask.nanoTime() + l;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Runnable runnable, V v, long l) {
        this(abstractScheduledEventExecutor, ScheduledFutureTask.toCallable(runnable, v), l);
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long l, long l2) {
        super(abstractScheduledEventExecutor, callable);
        if (l2 == 0L) {
            throw new IllegalArgumentException("period: 0 (expected: != 0)");
        }
        this.deadlineNanos = l;
        this.periodNanos = l2;
    }

    ScheduledFutureTask(AbstractScheduledEventExecutor abstractScheduledEventExecutor, Callable<V> callable, long l) {
        super(abstractScheduledEventExecutor, callable);
        this.deadlineNanos = l;
        this.periodNanos = 0L;
    }

    @Override
    protected EventExecutor executor() {
        return super.executor();
    }

    public long deadlineNanos() {
        return this.deadlineNanos;
    }

    public long delayNanos() {
        return Math.max(0L, this.deadlineNanos() - ScheduledFutureTask.nanoTime());
    }

    public long delayNanos(long l) {
        return Math.max(0L, this.deadlineNanos() - (l - START_TIME));
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.delayNanos(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        if (this == delayed) {
            return 1;
        }
        ScheduledFutureTask scheduledFutureTask = (ScheduledFutureTask)delayed;
        long l = this.deadlineNanos() - scheduledFutureTask.deadlineNanos();
        if (l < 0L) {
            return 1;
        }
        if (l > 0L) {
            return 0;
        }
        if (this.id < scheduledFutureTask.id) {
            return 1;
        }
        if (this.id == scheduledFutureTask.id) {
            throw new Error();
        }
        return 0;
    }

    @Override
    public void run() {
        if (!$assertionsDisabled && !this.executor().inEventLoop()) {
            throw new AssertionError();
        }
        try {
            if (this.periodNanos == 0L) {
                if (this.setUncancellableInternal()) {
                    Object v = this.task.call();
                    this.setSuccessInternal(v);
                }
            } else if (!this.isCancelled()) {
                this.task.call();
                if (!this.executor().isShutdown()) {
                    long l = this.periodNanos;
                    this.deadlineNanos = l > 0L ? (this.deadlineNanos += l) : ScheduledFutureTask.nanoTime() - l;
                    if (!this.isCancelled()) {
                        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = ((AbstractScheduledEventExecutor)this.executor()).scheduledTaskQueue;
                        if (!$assertionsDisabled && priorityQueue == null) {
                            throw new AssertionError();
                        }
                        priorityQueue.add(this);
                    }
                }
            }
        } catch (Throwable throwable) {
            this.setFailureInternal(throwable);
        }
    }

    @Override
    public boolean cancel(boolean bl) {
        boolean bl2 = super.cancel(bl);
        if (bl2) {
            ((AbstractScheduledEventExecutor)this.executor()).removeScheduled(this);
        }
        return bl2;
    }

    boolean cancelWithoutRemove(boolean bl) {
        return super.cancel(bl);
    }

    @Override
    protected StringBuilder toStringBuilder() {
        StringBuilder stringBuilder = super.toStringBuilder();
        stringBuilder.setCharAt(stringBuilder.length() - 1, ',');
        return stringBuilder.append(" id: ").append(this.id).append(", deadline: ").append(this.deadlineNanos).append(", period: ").append(this.periodNanos).append(')');
    }

    @Override
    public int priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue) {
        return this.queueIndex;
    }

    @Override
    public void priorityQueueIndex(DefaultPriorityQueue<?> defaultPriorityQueue, int n) {
        this.queueIndex = n;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Delayed)object);
    }

    static {
        $assertionsDisabled = !ScheduledFutureTask.class.desiredAssertionStatus();
        nextTaskId = new AtomicLong();
        START_TIME = System.nanoTime();
    }
}

