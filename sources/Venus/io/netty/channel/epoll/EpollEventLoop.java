/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventArray;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.IovArray;
import io.netty.util.IntSupplier;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class EpollEventLoop
extends SingleThreadEventLoop {
    private static final InternalLogger logger;
    private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER;
    private final FileDescriptor epollFd;
    private final FileDescriptor eventFd;
    private final FileDescriptor timerFd;
    private final IntObjectMap<AbstractEpollChannel> channels = new IntObjectHashMap<AbstractEpollChannel>(4096);
    private final boolean allowGrowing;
    private final EpollEventArray events;
    private final IovArray iovArray = new IovArray();
    private final SelectStrategy selectStrategy;
    private final IntSupplier selectNowSupplier = new IntSupplier(this){
        final EpollEventLoop this$0;
        {
            this.this$0 = epollEventLoop;
        }

        @Override
        public int get() throws Exception {
            return EpollEventLoop.access$000(this.this$0);
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>(this){
        final EpollEventLoop this$0;
        {
            this.this$0 = epollEventLoop;
        }

        @Override
        public Integer call() throws Exception {
            return EpollEventLoop.access$101(this.this$0);
        }

        @Override
        public Object call() throws Exception {
            return this.call();
        }
    };
    private volatile int wakenUp;
    private volatile int ioRatio = 50;
    static final long MAX_SCHEDULED_DAYS;
    static final boolean $assertionsDisabled;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    EpollEventLoop(EventLoopGroup eventLoopGroup, Executor executor, int n, SelectStrategy selectStrategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventLoopGroup, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        this.selectStrategy = ObjectUtil.checkNotNull(selectStrategy, "strategy");
        if (n == 0) {
            this.allowGrowing = true;
            this.events = new EpollEventArray(4096);
        } else {
            this.allowGrowing = false;
            this.events = new EpollEventArray(n);
        }
        boolean bl = false;
        FileDescriptor fileDescriptor = null;
        FileDescriptor fileDescriptor2 = null;
        FileDescriptor fileDescriptor3 = null;
        try {
            this.epollFd = fileDescriptor = Native.newEpollCreate();
            this.eventFd = fileDescriptor2 = Native.newEventFd();
            try {
                Native.epollCtlAdd(fileDescriptor.intValue(), fileDescriptor2.intValue(), Native.EPOLLIN);
            } catch (IOException iOException) {
                throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", iOException);
            }
            this.timerFd = fileDescriptor3 = Native.newTimerFd();
            try {
                Native.epollCtlAdd(fileDescriptor.intValue(), fileDescriptor3.intValue(), Native.EPOLLIN | Native.EPOLLET);
            } catch (IOException iOException) {
                throw new IllegalStateException("Unable to add timerFd filedescriptor to epoll", iOException);
            }
            bl = true;
        } finally {
            if (!bl) {
                if (fileDescriptor != null) {
                    try {
                        fileDescriptor.close();
                    } catch (Exception exception) {}
                }
                if (fileDescriptor2 != null) {
                    try {
                        fileDescriptor2.close();
                    } catch (Exception exception) {}
                }
                if (fileDescriptor3 != null) {
                    try {
                        fileDescriptor3.close();
                    } catch (Exception exception) {}
                }
            }
        }
    }

    IovArray cleanArray() {
        this.iovArray.clear();
        return this.iovArray;
    }

    @Override
    protected void wakeup(boolean bl) {
        if (!bl && WAKEN_UP_UPDATER.compareAndSet(this, 0, 0)) {
            Native.eventFdWrite(this.eventFd.intValue(), 1L);
        }
    }

    void add(AbstractEpollChannel abstractEpollChannel) throws IOException {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        int n = abstractEpollChannel.socket.intValue();
        Native.epollCtlAdd(this.epollFd.intValue(), n, abstractEpollChannel.flags);
        this.channels.put(n, abstractEpollChannel);
    }

    void modify(AbstractEpollChannel abstractEpollChannel) throws IOException {
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        Native.epollCtlMod(this.epollFd.intValue(), abstractEpollChannel.socket.intValue(), abstractEpollChannel.flags);
    }

    void remove(AbstractEpollChannel abstractEpollChannel) throws IOException {
        int n;
        if (!$assertionsDisabled && !this.inEventLoop()) {
            throw new AssertionError();
        }
        if (abstractEpollChannel.isOpen() && this.channels.remove(n = abstractEpollChannel.socket.intValue()) != null) {
            Native.epollCtlDel(this.epollFd.intValue(), abstractEpollChannel.fd().intValue());
        }
    }

    @Override
    protected Queue<Runnable> newTaskQueue(int n) {
        return n == Integer.MAX_VALUE ? PlatformDependent.newMpscQueue() : PlatformDependent.newMpscQueue(n);
    }

    @Override
    public int pendingTasks() {
        if (this.inEventLoop()) {
            return super.pendingTasks();
        }
        return (Integer)this.submit(this.pendingTasksCallable).syncUninterruptibly().getNow();
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

    private int epollWait(boolean bl) throws IOException {
        if (bl && this.hasTasks()) {
            return this.epollWaitNow();
        }
        long l = this.delayNanos(System.nanoTime());
        int n = (int)Math.min(l / 1000000000L, Integer.MAX_VALUE);
        return Native.epollWait(this.epollFd, this.events, this.timerFd, n, (int)Math.min(l - (long)n * 1000000000L, Integer.MAX_VALUE));
    }

    private int epollWaitNow() throws IOException {
        return Native.epollWait(this.epollFd, this.events, this.timerFd, 0, 0);
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
                            var1_1 = this.epollWait(EpollEventLoop.WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
                            if (this.wakenUp != 1) break block15;
                            Native.eventFdWrite(this.eventFd.intValue(), 1L);
                        }
                    }
                    break;
                }
                var2_4 = this.ioRatio;
                if (var2_4 == 100) {
                    try {
                        if (var1_1 <= 0) ** GOTO lbl32
                        this.processReady(this.events, var1_1);
                    } finally {
                        this.runAllTasks();
                    }
                } else {
                    var3_5 = System.nanoTime();
                    try {
                        if (var1_1 > 0) {
                            this.processReady(this.events, var1_1);
                        }
                    } finally {
                        var5_7 = System.nanoTime() - var3_5;
                        this.runAllTasks(var5_7 * (long)(100 - var2_4) / (long)var2_4);
                    }
                }
                if (this.allowGrowing && var1_1 == this.events.length()) {
                    this.events.increase();
                }
            } catch (Throwable var1_2) {
                EpollEventLoop.handleLoopException(var1_2);
            }
            try {
                if (!this.isShuttingDown()) continue;
                this.closeAll();
                if (!this.confirmShutdown()) continue;
            } catch (Throwable var1_3) {
                EpollEventLoop.handleLoopException(var1_3);
                continue;
            }
            break;
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

    private void closeAll() {
        try {
            this.epollWaitNow();
        } catch (IOException iOException) {
            // empty catch block
        }
        ArrayList<AbstractEpollChannel> arrayList = new ArrayList<AbstractEpollChannel>(this.channels.size());
        for (AbstractEpollChannel abstractEpollChannel : this.channels.values()) {
            arrayList.add(abstractEpollChannel);
        }
        for (AbstractEpollChannel abstractEpollChannel : arrayList) {
            abstractEpollChannel.unsafe().close(abstractEpollChannel.unsafe().voidPromise());
        }
    }

    private void processReady(EpollEventArray epollEventArray, int n) {
        for (int i = 0; i < n; ++i) {
            int n2 = epollEventArray.fd(i);
            if (n2 == this.eventFd.intValue()) {
                Native.eventFdRead(n2);
                continue;
            }
            if (n2 == this.timerFd.intValue()) {
                Native.timerFdRead(n2);
                continue;
            }
            long l = epollEventArray.events(i);
            AbstractEpollChannel abstractEpollChannel = this.channels.get(n2);
            if (abstractEpollChannel != null) {
                AbstractEpollChannel.AbstractEpollUnsafe abstractEpollUnsafe = (AbstractEpollChannel.AbstractEpollUnsafe)abstractEpollChannel.unsafe();
                if ((l & (long)(Native.EPOLLERR | Native.EPOLLOUT)) != 0L) {
                    abstractEpollUnsafe.epollOutReady();
                }
                if ((l & (long)(Native.EPOLLERR | Native.EPOLLIN)) != 0L) {
                    abstractEpollUnsafe.epollInReady();
                }
                if ((l & (long)Native.EPOLLRDHUP) == 0L) continue;
                abstractEpollUnsafe.epollRdHupReady();
                continue;
            }
            try {
                Native.epollCtlDel(this.epollFd.intValue(), n2);
                continue;
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    @Override
    protected void cleanup() {
        try {
            try {
                this.epollFd.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close the epoll fd.", iOException);
            }
            try {
                this.eventFd.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close the event fd.", iOException);
            }
            try {
                this.timerFd.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close the timer fd.", iOException);
            }
        } finally {
            this.iovArray.release();
            this.events.free();
        }
    }

    @Override
    protected void validateScheduled(long l, TimeUnit timeUnit) {
        long l2 = timeUnit.toDays(l);
        if (l2 > MAX_SCHEDULED_DAYS) {
            throw new IllegalArgumentException("days: " + l2 + " (expected: < " + MAX_SCHEDULED_DAYS + ')');
        }
    }

    static int access$000(EpollEventLoop epollEventLoop) throws IOException {
        return epollEventLoop.epollWaitNow();
    }

    static int access$101(EpollEventLoop epollEventLoop) {
        return super.pendingTasks();
    }

    static {
        $assertionsDisabled = !EpollEventLoop.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
        WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
        Epoll.ensureAvailability();
        MAX_SCHEDULED_DAYS = TimeUnit.SECONDS.toDays(999999999L);
    }
}

