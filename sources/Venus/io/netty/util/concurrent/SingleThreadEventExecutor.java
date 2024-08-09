/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.AbstractScheduledEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.OrderedEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.ScheduledFutureTask;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.concurrent.ThreadProperties;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public abstract class SingleThreadEventExecutor
extends AbstractScheduledEventExecutor
implements OrderedEventExecutor {
    static final int DEFAULT_MAX_PENDING_EXECUTOR_TASKS;
    private static final InternalLogger logger;
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_TERMINATED = 5;
    private static final Runnable WAKEUP_TASK;
    private static final Runnable NOOP_TASK;
    private static final AtomicIntegerFieldUpdater<SingleThreadEventExecutor> STATE_UPDATER;
    private static final AtomicReferenceFieldUpdater<SingleThreadEventExecutor, ThreadProperties> PROPERTIES_UPDATER;
    private final Queue<Runnable> taskQueue;
    private volatile Thread thread;
    private volatile ThreadProperties threadProperties;
    private final Executor executor;
    private volatile boolean interrupted;
    private final Semaphore threadLock = new Semaphore(0);
    private final Set<Runnable> shutdownHooks = new LinkedHashSet<Runnable>();
    private final boolean addTaskWakesUp;
    private final int maxPendingTasks;
    private final RejectedExecutionHandler rejectedExecutionHandler;
    private long lastExecutionTime;
    private volatile int state = 1;
    private volatile long gracefulShutdownQuietPeriod;
    private volatile long gracefulShutdownTimeout;
    private long gracefulShutdownStartTime;
    private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
    private static final long SCHEDULE_PURGE_INTERVAL;
    static final boolean $assertionsDisabled;

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, ThreadFactory threadFactory, boolean bl) {
        this(eventExecutorGroup, new ThreadPerTaskExecutor(threadFactory), bl);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, ThreadFactory threadFactory, boolean bl, int n, RejectedExecutionHandler rejectedExecutionHandler) {
        this(eventExecutorGroup, new ThreadPerTaskExecutor(threadFactory), bl, n, rejectedExecutionHandler);
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, Executor executor, boolean bl) {
        this(eventExecutorGroup, executor, bl, DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventExecutor(EventExecutorGroup eventExecutorGroup, Executor executor, boolean bl, int n, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventExecutorGroup);
        this.addTaskWakesUp = bl;
        this.maxPendingTasks = Math.max(16, n);
        this.executor = ObjectUtil.checkNotNull(executor, "executor");
        this.taskQueue = this.newTaskQueue(this.maxPendingTasks);
        this.rejectedExecutionHandler = ObjectUtil.checkNotNull(rejectedExecutionHandler, "rejectedHandler");
    }

    @Deprecated
    protected Queue<Runnable> newTaskQueue() {
        return this.newTaskQueue(this.maxPendingTasks);
    }

    protected Queue<Runnable> newTaskQueue(int n) {
        return new LinkedBlockingQueue<Runnable>(n);
    }

    protected void interruptThread() {
        Thread thread2 = this.thread;
        if (thread2 == null) {
            this.interrupted = true;
        } else {
            thread2.interrupt();
        }
    }

    protected Runnable pollTask() {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        return SingleThreadEventExecutor.pollTaskFrom(this.taskQueue);
    }

    protected static Runnable pollTaskFrom(Queue<Runnable> queue) {
        Runnable runnable;
        while ((runnable = queue.poll()) == WAKEUP_TASK) {
        }
        return runnable;
    }

    protected Runnable takeTask() {
        Runnable runnable;
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        if (!(this.taskQueue instanceof BlockingQueue)) {
            throw new UnsupportedOperationException();
        }
        BlockingQueue blockingQueue = (BlockingQueue)this.taskQueue;
        do {
            ScheduledFutureTask<?> scheduledFutureTask;
            if ((scheduledFutureTask = this.peekScheduledTask()) == null) {
                Runnable runnable2 = null;
                try {
                    runnable2 = (Runnable)blockingQueue.take();
                    if (runnable2 == WAKEUP_TASK) {
                        runnable2 = null;
                    }
                } catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                return runnable2;
            }
            long l = scheduledFutureTask.delayNanos();
            runnable = null;
            if (l > 0L) {
                try {
                    runnable = (Runnable)blockingQueue.poll(l, TimeUnit.NANOSECONDS);
                } catch (InterruptedException interruptedException) {
                    return null;
                }
            }
            if (runnable != null) continue;
            this.fetchFromScheduledTaskQueue();
            runnable = (Runnable)blockingQueue.poll();
        } while (runnable == null);
        return runnable;
    }

    private boolean fetchFromScheduledTaskQueue() {
        long l = AbstractScheduledEventExecutor.nanoTime();
        Runnable runnable = this.pollScheduledTask(l);
        while (runnable != null) {
            if (!this.taskQueue.offer(runnable)) {
                this.scheduledTaskQueue().add((ScheduledFutureTask)runnable);
                return true;
            }
            runnable = this.pollScheduledTask(l);
        }
        return false;
    }

    protected Runnable peekTask() {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        return this.taskQueue.peek();
    }

    protected boolean hasTasks() {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        return !this.taskQueue.isEmpty();
    }

    public int pendingTasks() {
        return this.taskQueue.size();
    }

    protected void addTask(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        if (!this.offerTask(runnable)) {
            this.reject(runnable);
        }
    }

    final boolean offerTask(Runnable runnable) {
        if (this.isShutdown()) {
            SingleThreadEventExecutor.reject();
        }
        return this.taskQueue.offer(runnable);
    }

    protected boolean removeTask(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        return this.taskQueue.remove(runnable);
    }

    protected boolean runAllTasks() {
        boolean bl;
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        boolean bl2 = false;
        do {
            bl = this.fetchFromScheduledTaskQueue();
            if (!this.runAllTasksFrom(this.taskQueue)) continue;
            bl2 = true;
        } while (!bl);
        if (bl2) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        this.afterRunningAllTasks();
        return bl2;
    }

    protected final boolean runAllTasksFrom(Queue<Runnable> queue) {
        Runnable runnable = SingleThreadEventExecutor.pollTaskFrom(queue);
        if (runnable == null) {
            return true;
        }
        do {
            SingleThreadEventExecutor.safeExecute(runnable);
        } while ((runnable = SingleThreadEventExecutor.pollTaskFrom(queue)) != null);
        return false;
    }

    protected boolean runAllTasks(long l) {
        long l2;
        block2: {
            this.fetchFromScheduledTaskQueue();
            Runnable runnable = this.pollTask();
            if (runnable == null) {
                this.afterRunningAllTasks();
                return true;
            }
            long l3 = ScheduledFutureTask.nanoTime() + l;
            long l4 = 0L;
            do {
                SingleThreadEventExecutor.safeExecute(runnable);
                if ((++l4 & 0x3FL) == 0L && (l2 = ScheduledFutureTask.nanoTime()) >= l3) break block2;
            } while ((runnable = this.pollTask()) != null);
            l2 = ScheduledFutureTask.nanoTime();
        }
        this.afterRunningAllTasks();
        this.lastExecutionTime = l2;
        return false;
    }

    protected void afterRunningAllTasks() {
    }

    protected long delayNanos(long l) {
        ScheduledFutureTask<?> scheduledFutureTask = this.peekScheduledTask();
        if (scheduledFutureTask == null) {
            return SCHEDULE_PURGE_INTERVAL;
        }
        return scheduledFutureTask.delayNanos(l);
    }

    protected void updateLastExecutionTime() {
        this.lastExecutionTime = ScheduledFutureTask.nanoTime();
    }

    protected abstract void run();

    protected void cleanup() {
    }

    protected void wakeup(boolean bl) {
        if (!bl || this.state == 3) {
            this.taskQueue.offer(WAKEUP_TASK);
        }
    }

    @Override
    public boolean inEventLoop(Thread thread2) {
        return thread2 == this.thread;
    }

    public void addShutdownHook(Runnable runnable) {
        if (this.inEventLoop()) {
            this.shutdownHooks.add(runnable);
        } else {
            this.execute(new Runnable(this, runnable){
                final Runnable val$task;
                final SingleThreadEventExecutor this$0;
                {
                    this.this$0 = singleThreadEventExecutor;
                    this.val$task = runnable;
                }

                @Override
                public void run() {
                    SingleThreadEventExecutor.access$000(this.this$0).add(this.val$task);
                }
            });
        }
    }

    public void removeShutdownHook(Runnable runnable) {
        if (this.inEventLoop()) {
            this.shutdownHooks.remove(runnable);
        } else {
            this.execute(new Runnable(this, runnable){
                final Runnable val$task;
                final SingleThreadEventExecutor this$0;
                {
                    this.this$0 = singleThreadEventExecutor;
                    this.val$task = runnable;
                }

                @Override
                public void run() {
                    SingleThreadEventExecutor.access$000(this.this$0).remove(this.val$task);
                }
            });
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean runShutdownHooks() {
        boolean bl = false;
        while (!this.shutdownHooks.isEmpty()) {
            ArrayList<Runnable> arrayList = new ArrayList<Runnable>(this.shutdownHooks);
            this.shutdownHooks.clear();
            for (Runnable runnable : arrayList) {
                try {
                    runnable.run();
                } catch (Throwable throwable) {
                    logger.warn("Shutdown hook raised an exception.", throwable);
                } finally {
                    bl = true;
                }
            }
        }
        if (bl) {
            this.lastExecutionTime = ScheduledFutureTask.nanoTime();
        }
        return bl;
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        boolean bl;
        int n;
        int n2;
        if (l < 0L) {
            throw new IllegalArgumentException("quietPeriod: " + l + " (expected >= 0)");
        }
        if (l2 < l) {
            throw new IllegalArgumentException("timeout: " + l2 + " (expected >= quietPeriod (" + l + "))");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (this.isShuttingDown()) {
            return this.terminationFuture();
        }
        boolean bl2 = this.inEventLoop();
        do {
            if (this.isShuttingDown()) {
                return this.terminationFuture();
            }
            bl = true;
            n2 = this.state;
            if (bl2) {
                n = 3;
                continue;
            }
            switch (n2) {
                case 1: 
                case 2: {
                    n = 3;
                    break;
                }
                default: {
                    n = n2;
                    bl = false;
                }
            }
        } while (!STATE_UPDATER.compareAndSet(this, n2, n));
        this.gracefulShutdownQuietPeriod = timeUnit.toNanos(l);
        this.gracefulShutdownTimeout = timeUnit.toNanos(l2);
        if (n2 == 1) {
            try {
                this.doStartThread();
            } catch (Throwable throwable) {
                STATE_UPDATER.set(this, 5);
                this.terminationFuture.tryFailure(throwable);
                if (!(throwable instanceof Exception)) {
                    PlatformDependent.throwException(throwable);
                }
                return this.terminationFuture;
            }
        }
        if (bl) {
            this.wakeup(bl2);
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
        boolean bl;
        int n;
        int n2;
        if (this.isShutdown()) {
            return;
        }
        boolean bl2 = this.inEventLoop();
        do {
            if (this.isShuttingDown()) {
                return;
            }
            bl = true;
            n2 = this.state;
            if (bl2) {
                n = 4;
                continue;
            }
            switch (n2) {
                case 1: 
                case 2: 
                case 3: {
                    n = 4;
                    break;
                }
                default: {
                    n = n2;
                    bl = false;
                }
            }
        } while (!STATE_UPDATER.compareAndSet(this, n2, n));
        if (n2 == 1) {
            try {
                this.doStartThread();
            } catch (Throwable throwable) {
                STATE_UPDATER.set(this, 5);
                this.terminationFuture.tryFailure(throwable);
                if (!(throwable instanceof Exception)) {
                    PlatformDependent.throwException(throwable);
                }
                return;
            }
        }
        if (bl) {
            this.wakeup(bl2);
        }
    }

    @Override
    public boolean isShuttingDown() {
        return this.state >= 3;
    }

    @Override
    public boolean isShutdown() {
        return this.state >= 4;
    }

    @Override
    public boolean isTerminated() {
        return this.state == 5;
    }

    protected boolean confirmShutdown() {
        if (!this.isShuttingDown()) {
            return true;
        }
        if (!this.inEventLoop()) {
            throw new IllegalStateException("must be invoked from an event loop");
        }
        this.cancelScheduledTasks();
        if (this.gracefulShutdownStartTime == 0L) {
            this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
        }
        if (this.runAllTasks() || this.runShutdownHooks()) {
            if (this.isShutdown()) {
                return false;
            }
            if (this.gracefulShutdownQuietPeriod == 0L) {
                return false;
            }
            this.wakeup(false);
            return true;
        }
        long l = ScheduledFutureTask.nanoTime();
        if (this.isShutdown() || l - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
            return false;
        }
        if (l - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
            this.wakeup(false);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException interruptedException) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (this.inEventLoop()) {
            throw new IllegalStateException("cannot await termination of the current thread");
        }
        if (this.threadLock.tryAcquire(l, timeUnit)) {
            this.threadLock.release();
        }
        return this.isTerminated();
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        boolean bl = this.inEventLoop();
        this.addTask(runnable);
        if (!bl) {
            this.startThread();
            if (this.isShutdown() && this.removeTask(runnable)) {
                SingleThreadEventExecutor.reject();
            }
        }
        if (!this.addTaskWakesUp && this.wakesUpForTask(runnable)) {
            this.wakeup(bl);
        }
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
        this.throwIfInEventLoop("invokeAny");
        return super.invokeAny(collection);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        this.throwIfInEventLoop("invokeAny");
        return super.invokeAny(collection, l, timeUnit);
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
        this.throwIfInEventLoop("invokeAll");
        return super.invokeAll(collection);
    }

    @Override
    public <T> List<java.util.concurrent.Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
        this.throwIfInEventLoop("invokeAll");
        return super.invokeAll(collection, l, timeUnit);
    }

    private void throwIfInEventLoop(String string) {
        if (this.inEventLoop()) {
            throw new RejectedExecutionException("Calling " + string + " from within the EventLoop is not allowed");
        }
    }

    public final ThreadProperties threadProperties() {
        ThreadProperties threadProperties = this.threadProperties;
        if (threadProperties == null) {
            Thread thread2 = this.thread;
            if (thread2 == null) {
                if (!$assertionsDisabled && this.inEventLoop()) {
                    throw new AssertionError();
                }
                this.submit(NOOP_TASK).syncUninterruptibly();
                thread2 = this.thread;
                if (!$assertionsDisabled && thread2 == null) {
                    throw new AssertionError();
                }
            }
            if (!PROPERTIES_UPDATER.compareAndSet(this, null, threadProperties = new DefaultThreadProperties(thread2))) {
                threadProperties = this.threadProperties;
            }
        }
        return threadProperties;
    }

    protected boolean wakesUpForTask(Runnable runnable) {
        return false;
    }

    protected static void reject() {
        throw new RejectedExecutionException("event executor terminated");
    }

    protected final void reject(Runnable runnable) {
        this.rejectedExecutionHandler.rejected(runnable, this);
    }

    private void startThread() {
        if (this.state == 1 && STATE_UPDATER.compareAndSet(this, 1, 1)) {
            try {
                this.doStartThread();
            } catch (Throwable throwable) {
                STATE_UPDATER.set(this, 1);
                PlatformDependent.throwException(throwable);
            }
        }
    }

    private void doStartThread() {
        if (!$assertionsDisabled && this.thread != null) {
            throw new AssertionError();
        }
        this.executor.execute(new Runnable(this){
            final SingleThreadEventExecutor this$0;
            {
                this.this$0 = singleThreadEventExecutor;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                SingleThreadEventExecutor.access$102(this.this$0, Thread.currentThread());
                if (SingleThreadEventExecutor.access$200(this.this$0)) {
                    SingleThreadEventExecutor.access$100(this.this$0).interrupt();
                }
                boolean bl = false;
                this.this$0.updateLastExecutionTime();
                try {
                    this.this$0.run();
                    bl = true;
                } catch (Throwable throwable) {
                    SingleThreadEventExecutor.access$300().warn("Unexpected exception from an event executor: ", throwable);
                } finally {
                    int n;
                    while ((n = SingleThreadEventExecutor.access$400(this.this$0)) < 3 && !SingleThreadEventExecutor.access$500().compareAndSet(this.this$0, n, 0)) {
                    }
                    if (bl && SingleThreadEventExecutor.access$600(this.this$0) == 0L) {
                        SingleThreadEventExecutor.access$300().error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called before run() implementation terminates.");
                    }
                    while (!this.this$0.confirmShutdown()) {
                    }
                }
                try {
                    this.this$0.cleanup();
                    return;
                } finally {
                    SingleThreadEventExecutor.access$500().set(this.this$0, 5);
                    SingleThreadEventExecutor.access$700(this.this$0).release();
                    if (!SingleThreadEventExecutor.access$800(this.this$0).isEmpty()) {
                        SingleThreadEventExecutor.access$300().warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.access$800(this.this$0).size() + ')');
                    }
                    SingleThreadEventExecutor.access$900(this.this$0).setSuccess(null);
                }
            }
        });
    }

    static Set access$000(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.shutdownHooks;
    }

    static Thread access$102(SingleThreadEventExecutor singleThreadEventExecutor, Thread thread2) {
        singleThreadEventExecutor.thread = thread2;
        return singleThreadEventExecutor.thread;
    }

    static boolean access$200(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.interrupted;
    }

    static Thread access$100(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.thread;
    }

    static InternalLogger access$300() {
        return logger;
    }

    static int access$400(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.state;
    }

    static AtomicIntegerFieldUpdater access$500() {
        return STATE_UPDATER;
    }

    static long access$600(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.gracefulShutdownStartTime;
    }

    static Semaphore access$700(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.threadLock;
    }

    static Queue access$800(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.taskQueue;
    }

    static Promise access$900(SingleThreadEventExecutor singleThreadEventExecutor) {
        return singleThreadEventExecutor.terminationFuture;
    }

    static {
        $assertionsDisabled = !SingleThreadEventExecutor.class.desiredAssertionStatus();
        DEFAULT_MAX_PENDING_EXECUTOR_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventexecutor.maxPendingTasks", Integer.MAX_VALUE));
        logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
        WAKEUP_TASK = new Runnable(){

            @Override
            public void run() {
            }
        };
        NOOP_TASK = new Runnable(){

            @Override
            public void run() {
            }
        };
        STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(SingleThreadEventExecutor.class, "state");
        PROPERTIES_UPDATER = AtomicReferenceFieldUpdater.newUpdater(SingleThreadEventExecutor.class, ThreadProperties.class, "threadProperties");
        SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
    }

    private static final class DefaultThreadProperties
    implements ThreadProperties {
        private final Thread t;

        DefaultThreadProperties(Thread thread2) {
            this.t = thread2;
        }

        @Override
        public Thread.State state() {
            return this.t.getState();
        }

        @Override
        public int priority() {
            return this.t.getPriority();
        }

        @Override
        public boolean isInterrupted() {
            return this.t.isInterrupted();
        }

        @Override
        public boolean isDaemon() {
            return this.t.isDaemon();
        }

        @Override
        public String name() {
            return this.t.getName();
        }

        @Override
        public long id() {
            return this.t.getId();
        }

        @Override
        public StackTraceElement[] stackTrace() {
            return this.t.getStackTrace();
        }

        @Override
        public boolean isAlive() {
            return this.t.isAlive();
        }
    }
}

