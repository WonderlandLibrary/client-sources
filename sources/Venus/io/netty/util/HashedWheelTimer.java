/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

public class HashedWheelTimer
implements Timer {
    static final InternalLogger logger;
    private static final AtomicInteger INSTANCE_COUNTER;
    private static final AtomicBoolean WARNED_TOO_MANY_INSTANCES;
    private static final int INSTANCE_COUNT_LIMIT = 64;
    private static final ResourceLeakDetector<HashedWheelTimer> leakDetector;
    private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER;
    private final ResourceLeakTracker<HashedWheelTimer> leak;
    private final Worker worker = new Worker(this, null);
    private final Thread workerThread;
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_STARTED = 1;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    private volatile int workerState;
    private final long tickDuration;
    private final HashedWheelBucket[] wheel;
    private final int mask;
    private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
    private final Queue<HashedWheelTimeout> timeouts = PlatformDependent.newMpscQueue();
    private final Queue<HashedWheelTimeout> cancelledTimeouts = PlatformDependent.newMpscQueue();
    private final AtomicLong pendingTimeouts = new AtomicLong(0L);
    private final long maxPendingTimeouts;
    private volatile long startTime;
    static final boolean $assertionsDisabled;

    public HashedWheelTimer() {
        this(Executors.defaultThreadFactory());
    }

    public HashedWheelTimer(long l, TimeUnit timeUnit) {
        this(Executors.defaultThreadFactory(), l, timeUnit);
    }

    public HashedWheelTimer(long l, TimeUnit timeUnit, int n) {
        this(Executors.defaultThreadFactory(), l, timeUnit, n);
    }

    public HashedWheelTimer(ThreadFactory threadFactory) {
        this(threadFactory, 100L, TimeUnit.MILLISECONDS);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long l, TimeUnit timeUnit) {
        this(threadFactory, l, timeUnit, 512);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long l, TimeUnit timeUnit, int n) {
        this(threadFactory, l, timeUnit, n, true);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long l, TimeUnit timeUnit, int n, boolean bl) {
        this(threadFactory, l, timeUnit, n, bl, -1L);
    }

    public HashedWheelTimer(ThreadFactory threadFactory, long l, TimeUnit timeUnit, int n, boolean bl, long l2) {
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (l <= 0L) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + l);
        }
        if (n <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + n);
        }
        this.wheel = HashedWheelTimer.createWheel(n);
        this.mask = this.wheel.length - 1;
        this.tickDuration = timeUnit.toNanos(l);
        if (this.tickDuration >= Long.MAX_VALUE / (long)this.wheel.length) {
            throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", l, Long.MAX_VALUE / (long)this.wheel.length));
        }
        this.workerThread = threadFactory.newThread(this.worker);
        this.leak = bl || !this.workerThread.isDaemon() ? leakDetector.track(this) : null;
        this.maxPendingTimeouts = l2;
        if (INSTANCE_COUNTER.incrementAndGet() > 64 && WARNED_TOO_MANY_INSTANCES.compareAndSet(false, false)) {
            HashedWheelTimer.reportTooManyInstances();
        }
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } finally {
            if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                INSTANCE_COUNTER.decrementAndGet();
            }
        }
    }

    private static HashedWheelBucket[] createWheel(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + n);
        }
        if (n > 0x40000000) {
            throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + n);
        }
        n = HashedWheelTimer.normalizeTicksPerWheel(n);
        HashedWheelBucket[] hashedWheelBucketArray = new HashedWheelBucket[n];
        for (int i = 0; i < hashedWheelBucketArray.length; ++i) {
            hashedWheelBucketArray[i] = new HashedWheelBucket(null);
        }
        return hashedWheelBucketArray;
    }

    private static int normalizeTicksPerWheel(int n) {
        int n2;
        for (n2 = 1; n2 < n; n2 <<= 1) {
        }
        return n2;
    }

    public void start() {
        switch (WORKER_STATE_UPDATER.get(this)) {
            case 0: {
                if (!WORKER_STATE_UPDATER.compareAndSet(this, 0, 0)) break;
                this.workerThread.start();
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                throw new IllegalStateException("cannot be started once stopped");
            }
            default: {
                throw new Error("Invalid WorkerState");
            }
        }
        while (this.startTime == 0L) {
            try {
                this.startTimeInitialized.await();
            } catch (InterruptedException interruptedException) {}
        }
    }

    @Override
    public Set<Timeout> stop() {
        boolean bl;
        if (Thread.currentThread() == this.workerThread) {
            throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
        }
        if (!WORKER_STATE_UPDATER.compareAndSet(this, 1, 1)) {
            if (WORKER_STATE_UPDATER.getAndSet(this, 2) != 2) {
                INSTANCE_COUNTER.decrementAndGet();
                if (this.leak != null) {
                    boolean bl2 = this.leak.close(this);
                    if (!$assertionsDisabled && !bl2) {
                        throw new AssertionError();
                    }
                }
            }
            return Collections.emptySet();
        }
        try {
            bl = false;
            while (this.workerThread.isAlive()) {
                this.workerThread.interrupt();
                try {
                    this.workerThread.join(100L);
                } catch (InterruptedException interruptedException) {
                    bl = true;
                }
            }
            if (bl) {
                Thread.currentThread().interrupt();
            }
        } finally {
            INSTANCE_COUNTER.decrementAndGet();
            if (this.leak != null) {
                bl = this.leak.close(this);
                if (!$assertionsDisabled && !bl) {
                    throw new AssertionError();
                }
            }
        }
        return this.worker.unprocessedTimeouts();
    }

    @Override
    public Timeout newTimeout(TimerTask timerTask2, long l, TimeUnit timeUnit) {
        if (timerTask2 == null) {
            throw new NullPointerException("task");
        }
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        long l2 = this.pendingTimeouts.incrementAndGet();
        if (this.maxPendingTimeouts > 0L && l2 > this.maxPendingTimeouts) {
            this.pendingTimeouts.decrementAndGet();
            throw new RejectedExecutionException("Number of pending timeouts (" + l2 + ") is greater than or equal to maximum allowed pending timeouts (" + this.maxPendingTimeouts + ")");
        }
        this.start();
        long l3 = System.nanoTime() + timeUnit.toNanos(l) - this.startTime;
        if (l > 0L && l3 < 0L) {
            l3 = Long.MAX_VALUE;
        }
        HashedWheelTimeout hashedWheelTimeout = new HashedWheelTimeout(this, timerTask2, l3);
        this.timeouts.add(hashedWheelTimeout);
        return hashedWheelTimeout;
    }

    public long pendingTimeouts() {
        return this.pendingTimeouts.get();
    }

    private static void reportTooManyInstances() {
        String string = StringUtil.simpleClassName(HashedWheelTimer.class);
        logger.error("You are creating too many " + string + " instances. " + string + " is a shared resource that must be reused across the JVM,so that only a few instances are created.");
    }

    static long access$202(HashedWheelTimer hashedWheelTimer, long l) {
        hashedWheelTimer.startTime = l;
        return hashedWheelTimer.startTime;
    }

    static long access$200(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.startTime;
    }

    static CountDownLatch access$300(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.startTimeInitialized;
    }

    static int access$400(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.mask;
    }

    static HashedWheelBucket[] access$500(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.wheel;
    }

    static AtomicIntegerFieldUpdater access$600() {
        return WORKER_STATE_UPDATER;
    }

    static Queue access$700(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.timeouts;
    }

    static long access$900(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.tickDuration;
    }

    static Queue access$1000(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.cancelledTimeouts;
    }

    static AtomicLong access$1100(HashedWheelTimer hashedWheelTimer) {
        return hashedWheelTimer.pendingTimeouts;
    }

    static {
        $assertionsDisabled = !HashedWheelTimer.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
        INSTANCE_COUNTER = new AtomicInteger();
        WARNED_TOO_MANY_INSTANCES = new AtomicBoolean();
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(HashedWheelTimer.class, 1);
        WORKER_STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
    }

    private static final class HashedWheelBucket {
        private HashedWheelTimeout head;
        private HashedWheelTimeout tail;
        static final boolean $assertionsDisabled = !HashedWheelTimer.class.desiredAssertionStatus();

        private HashedWheelBucket() {
        }

        public void addTimeout(HashedWheelTimeout hashedWheelTimeout) {
            if (!$assertionsDisabled && hashedWheelTimeout.bucket != null) {
                throw new AssertionError();
            }
            hashedWheelTimeout.bucket = this;
            if (this.head == null) {
                this.head = this.tail = hashedWheelTimeout;
            } else {
                this.tail.next = hashedWheelTimeout;
                hashedWheelTimeout.prev = this.tail;
                this.tail = hashedWheelTimeout;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void expireTimeouts(long l) {
            HashedWheelTimeout hashedWheelTimeout = this.head;
            while (hashedWheelTimeout != null) {
                HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
                if (hashedWheelTimeout.remainingRounds <= 0L) {
                    hashedWheelTimeout2 = this.remove(hashedWheelTimeout);
                    if (HashedWheelTimeout.access$800(hashedWheelTimeout) > l) throw new IllegalStateException(String.format("timeout.deadline (%d) > deadline (%d)", HashedWheelTimeout.access$800(hashedWheelTimeout), l));
                    hashedWheelTimeout.expire();
                } else if (hashedWheelTimeout.isCancelled()) {
                    hashedWheelTimeout2 = this.remove(hashedWheelTimeout);
                } else {
                    --hashedWheelTimeout.remainingRounds;
                }
                hashedWheelTimeout = hashedWheelTimeout2;
            }
        }

        public HashedWheelTimeout remove(HashedWheelTimeout hashedWheelTimeout) {
            HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
            if (hashedWheelTimeout.prev != null) {
                hashedWheelTimeout.prev.next = hashedWheelTimeout2;
            }
            if (hashedWheelTimeout.next != null) {
                hashedWheelTimeout.next.prev = hashedWheelTimeout.prev;
            }
            if (hashedWheelTimeout == this.head) {
                if (hashedWheelTimeout == this.tail) {
                    this.tail = null;
                    this.head = null;
                } else {
                    this.head = hashedWheelTimeout2;
                }
            } else if (hashedWheelTimeout == this.tail) {
                this.tail = hashedWheelTimeout.prev;
            }
            hashedWheelTimeout.prev = null;
            hashedWheelTimeout.next = null;
            hashedWheelTimeout.bucket = null;
            HashedWheelTimer.access$1100(HashedWheelTimeout.access$1200(hashedWheelTimeout)).decrementAndGet();
            return hashedWheelTimeout2;
        }

        public void clearTimeouts(Set<Timeout> set) {
            HashedWheelTimeout hashedWheelTimeout;
            while ((hashedWheelTimeout = this.pollTimeout()) != null) {
                if (hashedWheelTimeout.isExpired() || hashedWheelTimeout.isCancelled()) continue;
                set.add(hashedWheelTimeout);
            }
            return;
        }

        private HashedWheelTimeout pollTimeout() {
            HashedWheelTimeout hashedWheelTimeout = this.head;
            if (hashedWheelTimeout == null) {
                return null;
            }
            HashedWheelTimeout hashedWheelTimeout2 = hashedWheelTimeout.next;
            if (hashedWheelTimeout2 == null) {
                this.head = null;
                this.tail = null;
            } else {
                this.head = hashedWheelTimeout2;
                hashedWheelTimeout2.prev = null;
            }
            hashedWheelTimeout.next = null;
            hashedWheelTimeout.prev = null;
            hashedWheelTimeout.bucket = null;
            return hashedWheelTimeout;
        }

        HashedWheelBucket(1 var1_1) {
            this();
        }
    }

    private static final class HashedWheelTimeout
    implements Timeout {
        private static final int ST_INIT = 0;
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
        private final HashedWheelTimer timer;
        private final TimerTask task;
        private final long deadline;
        private volatile int state = 0;
        long remainingRounds;
        HashedWheelTimeout next;
        HashedWheelTimeout prev;
        HashedWheelBucket bucket;

        HashedWheelTimeout(HashedWheelTimer hashedWheelTimer, TimerTask timerTask2, long l) {
            this.timer = hashedWheelTimer;
            this.task = timerTask2;
            this.deadline = l;
        }

        @Override
        public Timer timer() {
            return this.timer;
        }

        @Override
        public TimerTask task() {
            return this.task;
        }

        @Override
        public boolean cancel() {
            if (!this.compareAndSetState(0, 0)) {
                return true;
            }
            HashedWheelTimer.access$1000(this.timer).add(this);
            return false;
        }

        void remove() {
            HashedWheelBucket hashedWheelBucket = this.bucket;
            if (hashedWheelBucket != null) {
                hashedWheelBucket.remove(this);
            } else {
                HashedWheelTimer.access$1100(this.timer).decrementAndGet();
            }
        }

        public boolean compareAndSetState(int n, int n2) {
            return STATE_UPDATER.compareAndSet(this, n, n2);
        }

        public int state() {
            return this.state;
        }

        @Override
        public boolean isCancelled() {
            return this.state() == 1;
        }

        @Override
        public boolean isExpired() {
            return this.state() == 2;
        }

        public void expire() {
            block3: {
                if (!this.compareAndSetState(0, 1)) {
                    return;
                }
                try {
                    this.task.run(this);
                } catch (Throwable throwable) {
                    if (!logger.isWarnEnabled()) break block3;
                    logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', throwable);
                }
            }
        }

        public String toString() {
            long l = System.nanoTime();
            long l2 = this.deadline - l + HashedWheelTimer.access$200(this.timer);
            StringBuilder stringBuilder = new StringBuilder(192).append(StringUtil.simpleClassName(this)).append('(').append("deadline: ");
            if (l2 > 0L) {
                stringBuilder.append(l2).append(" ns later");
            } else if (l2 < 0L) {
                stringBuilder.append(-l2).append(" ns ago");
            } else {
                stringBuilder.append("now");
            }
            if (this.isCancelled()) {
                stringBuilder.append(", cancelled");
            }
            return stringBuilder.append(", task: ").append(this.task()).append(')').toString();
        }

        static long access$800(HashedWheelTimeout hashedWheelTimeout) {
            return hashedWheelTimeout.deadline;
        }

        static HashedWheelTimer access$1200(HashedWheelTimeout hashedWheelTimeout) {
            return hashedWheelTimeout.timer;
        }
    }

    private final class Worker
    implements Runnable {
        private final Set<Timeout> unprocessedTimeouts;
        private long tick;
        final HashedWheelTimer this$0;

        private Worker(HashedWheelTimer hashedWheelTimer) {
            this.this$0 = hashedWheelTimer;
            this.unprocessedTimeouts = new HashSet<Timeout>();
        }

        @Override
        public void run() {
            HashedWheelBucket hashedWheelBucket;
            int n;
            HashedWheelTimer.access$202(this.this$0, System.nanoTime());
            if (HashedWheelTimer.access$200(this.this$0) == 0L) {
                HashedWheelTimer.access$202(this.this$0, 1L);
            }
            HashedWheelTimer.access$300(this.this$0).countDown();
            do {
                long l;
                if ((l = this.waitForNextTick()) <= 0L) continue;
                n = (int)(this.tick & (long)HashedWheelTimer.access$400(this.this$0));
                this.processCancelledTasks();
                hashedWheelBucket = HashedWheelTimer.access$500(this.this$0)[n];
                this.transferTimeoutsToBuckets();
                hashedWheelBucket.expireTimeouts(l);
                ++this.tick;
            } while (HashedWheelTimer.access$600().get(this.this$0) == 1);
            Object object = HashedWheelTimer.access$500(this.this$0);
            int n2 = ((HashedWheelBucket[])object).length;
            for (n = 0; n < n2; ++n) {
                hashedWheelBucket = object[n];
                hashedWheelBucket.clearTimeouts(this.unprocessedTimeouts);
            }
            while ((object = (HashedWheelTimeout)HashedWheelTimer.access$700(this.this$0).poll()) != null) {
                if (((HashedWheelTimeout)object).isCancelled()) continue;
                this.unprocessedTimeouts.add((Timeout)object);
            }
            this.processCancelledTasks();
        }

        private void transferTimeoutsToBuckets() {
            HashedWheelTimeout hashedWheelTimeout;
            for (int i = 0; i < 100000 && (hashedWheelTimeout = (HashedWheelTimeout)HashedWheelTimer.access$700(this.this$0).poll()) != null; ++i) {
                if (hashedWheelTimeout.state() == 1) continue;
                long l = HashedWheelTimeout.access$800(hashedWheelTimeout) / HashedWheelTimer.access$900(this.this$0);
                hashedWheelTimeout.remainingRounds = (l - this.tick) / (long)HashedWheelTimer.access$500(this.this$0).length;
                long l2 = Math.max(l, this.tick);
                int n = (int)(l2 & (long)HashedWheelTimer.access$400(this.this$0));
                HashedWheelBucket hashedWheelBucket = HashedWheelTimer.access$500(this.this$0)[n];
                hashedWheelBucket.addTimeout(hashedWheelTimeout);
            }
        }

        private void processCancelledTasks() {
            HashedWheelTimeout hashedWheelTimeout;
            while ((hashedWheelTimeout = (HashedWheelTimeout)HashedWheelTimer.access$1000(this.this$0).poll()) != null) {
                try {
                    hashedWheelTimeout.remove();
                } catch (Throwable throwable) {
                    if (!logger.isWarnEnabled()) continue;
                    logger.warn("An exception was thrown while process a cancellation task", throwable);
                }
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private long waitForNextTick() {
            long l = HashedWheelTimer.access$900(this.this$0) * (this.tick + 1L);
            while (true) {
                long l2;
                long l3;
                if ((l3 = (l - (l2 = System.nanoTime() - HashedWheelTimer.access$200(this.this$0)) + 999999L) / 1000000L) <= 0L) {
                    if (l2 != Long.MIN_VALUE) return l2;
                    return -9223372036854775807L;
                }
                if (PlatformDependent.isWindows()) {
                    l3 = l3 / 10L * 10L;
                }
                try {
                    Thread.sleep(l3);
                    continue;
                } catch (InterruptedException interruptedException) {
                    if (HashedWheelTimer.access$600().get(this.this$0) == 2) return Long.MIN_VALUE;
                    continue;
                }
                break;
            }
        }

        public Set<Timeout> unprocessedTimeouts() {
            return Collections.unmodifiableSet(this.unprocessedTimeouts);
        }

        Worker(HashedWheelTimer hashedWheelTimer, 1 var2_2) {
            this(hashedWheelTimer);
        }
    }
}

