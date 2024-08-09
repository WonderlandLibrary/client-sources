/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.FailedFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFutureTask;
import io.netty.util.internal.PriorityQueue;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GlobalEventExecutor
extends AbstractScheduledEventExecutor {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
    private static final long SCHEDULE_QUIET_PERIOD_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
    public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
    final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    final ScheduledFutureTask<Void> quietPeriodTask = new ScheduledFutureTask<Object>((AbstractScheduledEventExecutor)this, Executors.callable(new Runnable(this){
        final GlobalEventExecutor this$0;
        {
            this.this$0 = globalEventExecutor;
        }

        @Override
        public void run() {
        }
    }, null), ScheduledFutureTask.deadlineNanos(SCHEDULE_QUIET_PERIOD_INTERVAL), -SCHEDULE_QUIET_PERIOD_INTERVAL);
    final ThreadFactory threadFactory = new DefaultThreadFactory(DefaultThreadFactory.toPoolName(this.getClass()), false, 5, null);
    private final TaskRunner taskRunner = new TaskRunner(this);
    private final AtomicBoolean started = new AtomicBoolean();
    volatile Thread thread;
    private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());

    private GlobalEventExecutor() {
        this.scheduledTaskQueue().add(this.quietPeriodTask);
    }

    Runnable takeTask() {
        Runnable runnable;
        BlockingQueue<Runnable> blockingQueue = this.taskQueue;
        do {
            ScheduledFutureTask<?> scheduledFutureTask;
            if ((scheduledFutureTask = this.peekScheduledTask()) == null) {
                Runnable runnable2 = null;
                try {
                    runnable2 = blockingQueue.take();
                } catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return runnable2;
            }
            long l = scheduledFutureTask.delayNanos();
            if (l > 0L) {
                try {
                    runnable = blockingQueue.poll(l, TimeUnit.NANOSECONDS);
                } catch (InterruptedException interruptedException) {
                    return null;
                }
            } else {
                runnable = (Runnable)blockingQueue.poll();
            }
            if (runnable != null) continue;
            this.fetchFromScheduledTaskQueue();
            runnable = (Runnable)blockingQueue.poll();
        } while (runnable == null);
        return runnable;
    }

    private void fetchFromScheduledTaskQueue() {
        long l = AbstractScheduledEventExecutor.nanoTime();
        Runnable runnable = this.pollScheduledTask(l);
        while (runnable != null) {
            this.taskQueue.add(runnable);
            runnable = this.pollScheduledTask(l);
        }
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    private void addTask(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        this.taskQueue.add(runnable);
    }

    @Override
    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        return this.terminationFuture();
    }

    @Override
    public Future<?> terminationFuture() {
        return this.terminationFuture;
    }

    @Override
    @Deprecated
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isShuttingDown() {
        return true;
    }

    @Override
    public boolean isShutdown() {
        return true;
    }

    @Override
    public boolean isTerminated() {
        return true;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) {
        return true;
    }

    public boolean awaitInactivity(long l, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        Thread thread2 = this.thread;
        if (thread2 == null) {
            throw new IllegalStateException("thread was not started");
        }
        thread2.join(timeUnit.toMillis(l));
        return !thread2.isAlive();
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        this.addTask(runnable);
        if (!this.inEventLoop()) {
            this.startThread();
        }
    }

    private void startThread() {
        if (this.started.compareAndSet(false, false)) {
            Thread thread2 = this.threadFactory.newThread(this.taskRunner);
            AccessController.doPrivileged(new PrivilegedAction<Void>(this, thread2){
                final Thread val$t;
                final GlobalEventExecutor this$0;
                {
                    this.this$0 = globalEventExecutor;
                    this.val$t = thread2;
                }

                @Override
                public Void run() {
                    this.val$t.setContextClassLoader(null);
                    return null;
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
            this.thread = thread2;
            thread2.start();
        }
    }

    static InternalLogger access$000() {
        return logger;
    }

    static AtomicBoolean access$100(GlobalEventExecutor globalEventExecutor) {
        return globalEventExecutor.started;
    }

    final class TaskRunner
    implements Runnable {
        static final boolean $assertionsDisabled = !GlobalEventExecutor.class.desiredAssertionStatus();
        final GlobalEventExecutor this$0;

        TaskRunner(GlobalEventExecutor globalEventExecutor) {
            this.this$0 = globalEventExecutor;
        }

        @Override
        public void run() {
            while (true) {
                Runnable runnable;
                if ((runnable = this.this$0.takeTask()) != null) {
                    try {
                        runnable.run();
                    } catch (Throwable throwable) {
                        GlobalEventExecutor.access$000().warn("Unexpected exception from the global event executor: ", throwable);
                    }
                    if (runnable != this.this$0.quietPeriodTask) continue;
                }
                PriorityQueue priorityQueue = this.this$0.scheduledTaskQueue;
                if (!this.this$0.taskQueue.isEmpty() || priorityQueue != null && priorityQueue.size() != 1) continue;
                boolean bl = GlobalEventExecutor.access$100(this.this$0).compareAndSet(true, true);
                if (!$assertionsDisabled && !bl) {
                    throw new AssertionError();
                }
                if (this.this$0.taskQueue.isEmpty() && (priorityQueue == null || priorityQueue.size() == 1) || !GlobalEventExecutor.access$100(this.this$0).compareAndSet(false, false)) break;
            }
        }
    }
}

