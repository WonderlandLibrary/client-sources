/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.kqueue;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.kqueue.AbstractKQueueChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventArray;
import io.netty.channel.kqueue.Native;
import io.netty.channel.kqueue.NativeLongArray;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class KQueueEventLoop
extends SingleThreadEventLoop {
    private static final InternalLogger logger;
    private static final AtomicIntegerFieldUpdater<KQueueEventLoop> WAKEN_UP_UPDATER;
    private static final int KQUEUE_WAKE_UP_IDENT = 0;
    private final NativeLongArray jniChannelPointers;
    private final boolean allowGrowing;
    private final FileDescriptor kqueueFd;
    private final KQueueEventArray changeList;
    private final KQueueEventArray eventList;
    private final SelectStrategy selectStrategy;
    private final IovArray iovArray = new IovArray();
    private final IntSupplier selectNowSupplier = new IntSupplier(this){
        final KQueueEventLoop this$0;
        {
            this.this$0 = kQueueEventLoop;
        }

        @Override
        public int get() throws Exception {
            return KQueueEventLoop.access$000(this.this$0);
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>(this){
        final KQueueEventLoop this$0;
        {
            this.this$0 = kQueueEventLoop;
        }

        @Override
        public Integer call() throws Exception {
            return KQueueEventLoop.access$101(this.this$0);
        }

        @Override
        public Object call() throws Exception {
            return this.call();
        }
    };
    private volatile int wakenUp;
    private volatile int ioRatio = 50;
    static final long MAX_SCHEDULED_DAYS = 1095L;
    static final boolean $assertionsDisabled;

    KQueueEventLoop(EventLoopGroup eventLoopGroup, Executor executor, int n, SelectStrategy selectStrategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventLoopGroup, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        this.selectStrategy = ObjectUtil.checkNotNull(selectStrategy, "strategy");
        this.kqueueFd = Native.newKQueue();
        if (n == 0) {
            this.allowGrowing = true;
            n = 4096;
        } else {
            this.allowGrowing = false;
        }
        this.changeList = new KQueueEventArray(n);
        this.eventList = new KQueueEventArray(n);
        this.jniChannelPointers = new NativeLongArray(4096);
        int n2 = Native.keventAddUserEvent(this.kqueueFd.intValue(), 0);
        if (n2 < 0) {
            this.cleanup();
            throw new IllegalStateException("kevent failed to add user event with errno: " + -n2);
        }
    }

    void evSet(AbstractKQueueChannel abstractKQueueChannel, short s, short s2, int n) {
        this.changeList.evSet(abstractKQueueChannel, s, s2, n);
    }

    void remove(AbstractKQueueChannel abstractKQueueChannel) throws IOException {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        if (abstractKQueueChannel.jniSelfPtr == 0L) {
            return;
        }
        this.jniChannelPointers.add(abstractKQueueChannel.jniSelfPtr);
        abstractKQueueChannel.jniSelfPtr = 0L;
    }

    IovArray cleanArray() {
        this.iovArray.clear();
        return this.iovArray;
    }

    @Override
    protected void wakeup(boolean bl) {
        if (!bl && WAKEN_UP_UPDATER.compareAndSet(this, 0, 0)) {
            this.wakeup();
        }
    }

    private void wakeup() {
        Native.keventTriggerUserEvent(this.kqueueFd.intValue(), 0);
    }

    private int kqueueWait(boolean bl) throws IOException {
        if (bl && this.hasTasks()) {
            return this.kqueueWaitNow();
        }
        long l = this.delayNanos(System.nanoTime());
        int n = (int)Math.min(l / 1000000000L, Integer.MAX_VALUE);
        return this.kqueueWait(n, (int)Math.min(l - (long)n * 1000000000L, Integer.MAX_VALUE));
    }

    private int kqueueWaitNow() throws IOException {
        return this.kqueueWait(0, 0);
    }

    private int kqueueWait(int n, int n2) throws IOException {
        this.deleteJniChannelPointers();
        int n3 = Native.keventWait(this.kqueueFd.intValue(), this.changeList, this.eventList, n, n2);
        this.changeList.clear();
        return n3;
    }

    private void deleteJniChannelPointers() {
        if (!this.jniChannelPointers.isEmpty()) {
            KQueueEventArray.deleteGlobalRefs(this.jniChannelPointers.memoryAddress(), this.jniChannelPointers.memoryAddressEnd());
            this.jniChannelPointers.clear();
        }
    }

    private void processReady(int n) {
        for (int i = 0; i < n; ++i) {
            short s = this.eventList.filter(i);
            short s2 = this.eventList.flags(i);
            if (s == Native.EVFILT_USER || (s2 & Native.EV_ERROR) != 0) {
                if (!($assertionsDisabled || s != Native.EVFILT_USER || s == Native.EVFILT_USER && this.eventList.fd(i) == 0)) {
                    throw new AssertionError();
                }
                continue;
            }
            AbstractKQueueChannel abstractKQueueChannel = this.eventList.channel(i);
            if (abstractKQueueChannel == null) {
                logger.warn("events[{}]=[{}, {}] had no channel!", i, this.eventList.fd(i), s);
                continue;
            }
            AbstractKQueueChannel.AbstractKQueueUnsafe abstractKQueueUnsafe = (AbstractKQueueChannel.AbstractKQueueUnsafe)abstractKQueueChannel.unsafe();
            if (s == Native.EVFILT_WRITE) {
                abstractKQueueUnsafe.writeReady();
            } else if (s == Native.EVFILT_READ) {
                abstractKQueueUnsafe.readReady(this.eventList.data(i));
            } else if (s == Native.EVFILT_SOCK && (this.eventList.fflags(i) & Native.NOTE_RDHUP) != 0) {
                abstractKQueueUnsafe.readEOF();
            }
            if ((s2 & Native.EV_EOF) == 0) continue;
            abstractKQueueUnsafe.readEOF();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    protected void run() {
        while (true) {
            try {
                block15: while (true) {
                    var1_1 = this.selectStrategy.calculateStrategy(this.selectNowSupplier, this.hasTasks());
                    switch (var1_1) {
                        case -2: {
                            continue block15;
                        }
                        case -1: {
                            var1_1 = this.kqueueWait(KQueueEventLoop.WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
                            if (this.wakenUp != 1) break block15;
                            this.wakeup();
                        }
                    }
                    break;
                }
                var2_4 = this.ioRatio;
                if (var2_4 == 100) {
                    try {
                        if (var1_1 <= 0) ** GOTO lbl32
                        this.processReady(var1_1);
                    } finally {
                        this.runAllTasks();
                    }
                } else {
                    var3_5 = System.nanoTime();
                    try {
                        if (var1_1 > 0) {
                            this.processReady(var1_1);
                        }
                    } finally {
                        var5_7 = System.nanoTime() - var3_5;
                        this.runAllTasks(var5_7 * (long)(100 - var2_4) / (long)var2_4);
                    }
                }
                if (this.allowGrowing && var1_1 == this.eventList.capacity()) {
                    this.eventList.realloc(true);
                }
            } catch (Throwable var1_2) {
                KQueueEventLoop.handleLoopException(var1_2);
            }
            try {
                if (!this.isShuttingDown()) continue;
                this.closeAll();
                if (!this.confirmShutdown()) continue;
            } catch (Throwable var1_3) {
                KQueueEventLoop.handleLoopException(var1_3);
                continue;
            }
            break;
        }
    }

    @Override
    protected Queue<Runnable> newTaskQueue(int n) {
        return n == Integer.MAX_VALUE ? PlatformDependent.newMpscQueue() : PlatformDependent.newMpscQueue(n);
    }

    @Override
    public int pendingTasks() {
        return this.inEventLoop() ? super.pendingTasks() : ((Integer)this.submit(this.pendingTasksCallable).syncUninterruptibly().getNow()).intValue();
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int n) {
        if (n <= 0 || n > 100) {
            throw new IllegalArgumentException("ioRatio: " + n + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = n;
    }

    @Override
    protected void cleanup() {
        try {
            try {
                this.kqueueFd.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close the kqueue fd.", iOException);
            }
        } finally {
            this.deleteJniChannelPointers();
            this.jniChannelPointers.free();
            this.changeList.free();
            this.eventList.free();
        }
    }

    private void closeAll() {
        try {
            this.kqueueWaitNow();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    private static void handleLoopException(Throwable throwable) {
        logger.warn("Unexpected exception in the selector loop.", throwable);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    @Override
    protected void validateScheduled(long l, TimeUnit timeUnit) {
        long l2 = timeUnit.toDays(l);
        if (l2 > 1095L) {
            throw new IllegalArgumentException("days: " + l2 + " (expected: < " + 1095L + ')');
        }
    }

    static int access$000(KQueueEventLoop kQueueEventLoop) throws IOException {
        return kQueueEventLoop.kqueueWaitNow();
    }

    static int access$101(KQueueEventLoop kQueueEventLoop) {
        return super.pendingTasks();
    }

    static {
        $assertionsDisabled = !KQueueEventLoop.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(KQueueEventLoop.class);
        WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(KQueueEventLoop.class, "wakenUp");
        KQueue.ensureAvailability();
    }
}

