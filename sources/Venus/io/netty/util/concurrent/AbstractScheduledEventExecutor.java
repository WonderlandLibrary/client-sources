/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.concurrent.ScheduledFutureTask;
import io.netty.util.internal.DefaultPriorityQueue;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractScheduledEventExecutor
extends AbstractEventExecutor {
    private static final Comparator<ScheduledFutureTask<?>> SCHEDULED_FUTURE_TASK_COMPARATOR;
    PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue;
    static final boolean $assertionsDisabled;

    protected AbstractScheduledEventExecutor() {
    }

    protected AbstractScheduledEventExecutor(EventExecutorGroup eventExecutorGroup) {
        super(eventExecutorGroup);
    }

    protected static long nanoTime() {
        return ScheduledFutureTask.nanoTime();
    }

    PriorityQueue<ScheduledFutureTask<?>> scheduledTaskQueue() {
        if (this.scheduledTaskQueue == null) {
            this.scheduledTaskQueue = new DefaultPriorityQueue(SCHEDULED_FUTURE_TASK_COMPARATOR, 11);
        }
        return this.scheduledTaskQueue;
    }

    private static boolean isNullOrEmpty(Queue<ScheduledFutureTask<?>> queue) {
        return queue == null || queue.isEmpty();
    }

    protected void cancelScheduledTasks() {
        ScheduledFutureTask[] scheduledFutureTaskArray;
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
        if (AbstractScheduledEventExecutor.isNullOrEmpty(priorityQueue)) {
            return;
        }
        for (ScheduledFutureTask scheduledFutureTask : scheduledFutureTaskArray = priorityQueue.toArray(new ScheduledFutureTask[priorityQueue.size()])) {
            scheduledFutureTask.cancelWithoutRemove(true);
        }
        priorityQueue.clearIgnoringIndexes();
    }

    protected final Runnable pollScheduledTask() {
        return this.pollScheduledTask(AbstractScheduledEventExecutor.nanoTime());
    }

    protected final Runnable pollScheduledTask(long l) {
        ScheduledFutureTask scheduledFutureTask;
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
        ScheduledFutureTask scheduledFutureTask2 = scheduledFutureTask = priorityQueue == null ? null : (ScheduledFutureTask)priorityQueue.peek();
        if (scheduledFutureTask == null) {
            return null;
        }
        if (scheduledFutureTask.deadlineNanos() <= l) {
            priorityQueue.remove();
            return scheduledFutureTask;
        }
        return null;
    }

    protected final long nextScheduledTaskNano() {
        ScheduledFutureTask scheduledFutureTask;
        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
        ScheduledFutureTask scheduledFutureTask2 = scheduledFutureTask = priorityQueue == null ? null : (ScheduledFutureTask)priorityQueue.peek();
        if (scheduledFutureTask == null) {
            return -1L;
        }
        return Math.max(0L, scheduledFutureTask.deadlineNanos() - AbstractScheduledEventExecutor.nanoTime());
    }

    final ScheduledFutureTask<?> peekScheduledTask() {
        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
        if (priorityQueue == null) {
            return null;
        }
        return (ScheduledFutureTask)priorityQueue.peek();
    }

    protected final boolean hasScheduledTasks() {
        PriorityQueue<ScheduledFutureTask<?>> priorityQueue = this.scheduledTaskQueue;
        ScheduledFutureTask scheduledFutureTask = priorityQueue == null ? null : (ScheduledFutureTask)priorityQueue.peek();
        return scheduledFutureTask != null && scheduledFutureTask.deadlineNanos() <= AbstractScheduledEventExecutor.nanoTime();
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (l < 0L) {
            l = 0L;
        }
        this.validateScheduled(l, timeUnit);
        return this.schedule(new ScheduledFutureTask<Object>(this, runnable, null, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(l))));
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(callable, "callable");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (l < 0L) {
            l = 0L;
        }
        this.validateScheduled(l, timeUnit);
        return this.schedule(new ScheduledFutureTask<V>(this, callable, ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(l))));
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (l < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", l));
        }
        if (l2 <= 0L) {
            throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", l2));
        }
        this.validateScheduled(l, timeUnit);
        this.validateScheduled(l2, timeUnit);
        return this.schedule(new ScheduledFutureTask<Object>(this, Executors.callable(runnable, null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(l)), timeUnit.toNanos(l2)));
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(runnable, "command");
        ObjectUtil.checkNotNull(timeUnit, "unit");
        if (l < 0L) {
            throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", l));
        }
        if (l2 <= 0L) {
            throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", l2));
        }
        this.validateScheduled(l, timeUnit);
        this.validateScheduled(l2, timeUnit);
        return this.schedule(new ScheduledFutureTask<Object>(this, Executors.callable(runnable, null), ScheduledFutureTask.deadlineNanos(timeUnit.toNanos(l)), -timeUnit.toNanos(l2)));
    }

    protected void validateScheduled(long l, TimeUnit timeUnit) {
    }

    <V> ScheduledFuture<V> schedule(ScheduledFutureTask<V> scheduledFutureTask) {
        if (this.inEventLoop()) {
            this.scheduledTaskQueue().add(scheduledFutureTask);
        } else {
            this.execute(new Runnable(this, scheduledFutureTask){
                final ScheduledFutureTask val$task;
                final AbstractScheduledEventExecutor this$0;
                {
                    this.this$0 = abstractScheduledEventExecutor;
                    this.val$task = scheduledFutureTask;
                }

                @Override
                public void run() {
                    this.this$0.scheduledTaskQueue().add(this.val$task);
                }
            });
        }
        return scheduledFutureTask;
    }

    final void removeScheduled(ScheduledFutureTask<?> scheduledFutureTask) {
        if (this.inEventLoop()) {
            this.scheduledTaskQueue().removeTyped(scheduledFutureTask);
        } else {
            this.execute(new Runnable(this, scheduledFutureTask){
                final ScheduledFutureTask val$task;
                final AbstractScheduledEventExecutor this$0;
                {
                    this.this$0 = abstractScheduledEventExecutor;
                    this.val$task = scheduledFutureTask;
                }

                @Override
                public void run() {
                    this.this$0.removeScheduled(this.val$task);
                }
            });
        }
    }

    @Override
    public java.util.concurrent.ScheduledFuture scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
    }

    @Override
    public java.util.concurrent.ScheduledFuture scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    @Override
    public java.util.concurrent.ScheduledFuture schedule(Callable callable, long l, TimeUnit timeUnit) {
        return this.schedule(callable, l, timeUnit);
    }

    @Override
    public java.util.concurrent.ScheduledFuture schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.schedule(runnable, l, timeUnit);
    }

    static {
        $assertionsDisabled = !AbstractScheduledEventExecutor.class.desiredAssertionStatus();
        SCHEDULED_FUTURE_TASK_COMPARATOR = new Comparator<ScheduledFutureTask<?>>(){

            @Override
            public int compare(ScheduledFutureTask<?> scheduledFutureTask, ScheduledFutureTask<?> scheduledFutureTask2) {
                return scheduledFutureTask.compareTo(scheduledFutureTask2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((ScheduledFutureTask)object, (ScheduledFutureTask)object2);
            }
        };
    }
}

