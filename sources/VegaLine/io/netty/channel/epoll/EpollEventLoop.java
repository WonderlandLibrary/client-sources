/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.epoll.AbstractEpollChannel;
import io.netty.channel.epoll.EpollEventArray;
import io.netty.channel.epoll.IovArray;
import io.netty.channel.epoll.Native;
import io.netty.channel.unix.FileDescriptor;
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
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

final class EpollEventLoop
extends SingleThreadEventLoop {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(EpollEventLoop.class);
    private static final AtomicIntegerFieldUpdater<EpollEventLoop> WAKEN_UP_UPDATER = AtomicIntegerFieldUpdater.newUpdater(EpollEventLoop.class, "wakenUp");
    private final FileDescriptor epollFd;
    private final FileDescriptor eventFd;
    private final IntObjectMap<AbstractEpollChannel> channels = new IntObjectHashMap<AbstractEpollChannel>(4096);
    private final boolean allowGrowing;
    private final EpollEventArray events;
    private final IovArray iovArray = new IovArray();
    private final SelectStrategy selectStrategy;
    private final IntSupplier selectNowSupplier = new IntSupplier(){

        @Override
        public int get() throws Exception {
            return Native.epollWait(EpollEventLoop.this.epollFd.intValue(), EpollEventLoop.this.events, 0);
        }
    };
    private final Callable<Integer> pendingTasksCallable = new Callable<Integer>(){

        @Override
        public Integer call() throws Exception {
            return EpollEventLoop.super.pendingTasks();
        }
    };
    private volatile int wakenUp;
    private volatile int ioRatio = 50;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    EpollEventLoop(EventLoopGroup parent, Executor executor, int maxEvents, SelectStrategy strategy, RejectedExecutionHandler rejectedExecutionHandler) {
        super(parent, executor, false, DEFAULT_MAX_PENDING_TASKS, rejectedExecutionHandler);
        this.selectStrategy = ObjectUtil.checkNotNull(strategy, "strategy");
        if (maxEvents == 0) {
            this.allowGrowing = true;
            this.events = new EpollEventArray(4096);
        } else {
            this.allowGrowing = false;
            this.events = new EpollEventArray(maxEvents);
        }
        boolean success = false;
        FileDescriptor epollFd = null;
        FileDescriptor eventFd = null;
        try {
            this.epollFd = epollFd = Native.newEpollCreate();
            this.eventFd = eventFd = Native.newEventFd();
            try {
                Native.epollCtlAdd(epollFd.intValue(), eventFd.intValue(), Native.EPOLLIN);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to add eventFd filedescriptor to epoll", e);
            }
            success = true;
        } finally {
            if (!success) {
                if (epollFd != null) {
                    try {
                        epollFd.close();
                    } catch (Exception exception) {}
                }
                if (eventFd != null) {
                    try {
                        eventFd.close();
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
    protected void wakeup(boolean inEventLoop) {
        if (!inEventLoop && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
            Native.eventFdWrite(this.eventFd.intValue(), 1L);
        }
    }

    void add(AbstractEpollChannel ch) throws IOException {
        assert (this.inEventLoop());
        int fd = ch.fd().intValue();
        Native.epollCtlAdd(this.epollFd.intValue(), fd, ch.flags);
        this.channels.put(fd, ch);
    }

    void modify(AbstractEpollChannel ch) throws IOException {
        assert (this.inEventLoop());
        Native.epollCtlMod(this.epollFd.intValue(), ch.fd().intValue(), ch.flags);
    }

    void remove(AbstractEpollChannel ch) throws IOException {
        int fd;
        assert (this.inEventLoop());
        if (ch.isOpen() && this.channels.remove(fd = ch.fd().intValue()) != null) {
            Native.epollCtlDel(this.epollFd.intValue(), ch.fd().intValue());
        }
    }

    @Override
    protected Queue<Runnable> newTaskQueue(int maxPendingTasks) {
        return PlatformDependent.newMpscQueue(maxPendingTasks);
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

    public void setIoRatio(int ioRatio) {
        if (ioRatio <= 0 || ioRatio > 100) {
            throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = ioRatio;
    }

    private int epollWait(boolean oldWakenUp) throws IOException {
        int selectCnt = 0;
        long currentTimeNanos = System.nanoTime();
        long selectDeadLineNanos = currentTimeNanos + this.delayNanos(currentTimeNanos);
        while (true) {
            long timeoutMillis;
            if ((timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L) <= 0L) {
                int ready;
                if (selectCnt != 0 || (ready = Native.epollWait(this.epollFd.intValue(), this.events, 0)) <= 0) break;
                return ready;
            }
            if (this.hasTasks() && WAKEN_UP_UPDATER.compareAndSet(this, 0, 1)) {
                return Native.epollWait(this.epollFd.intValue(), this.events, 0);
            }
            int selectedKeys = Native.epollWait(this.epollFd.intValue(), this.events, (int)timeoutMillis);
            ++selectCnt;
            if (selectedKeys != 0 || oldWakenUp || this.wakenUp == 1 || this.hasTasks() || this.hasScheduledTasks()) {
                return selectedKeys;
            }
            currentTimeNanos = System.nanoTime();
        }
        return 0;
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
                    strategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, this.hasTasks());
                    switch (strategy) {
                        case -2: {
                            continue block15;
                        }
                        case -1: {
                            strategy = this.epollWait(EpollEventLoop.WAKEN_UP_UPDATER.getAndSet(this, 0) == 1);
                            if (this.wakenUp != 1) break block15;
                            Native.eventFdWrite(this.eventFd.intValue(), 1L);
                        }
                    }
                    break;
                }
                ioRatio = this.ioRatio;
                if (ioRatio == 100) {
                    try {
                        if (strategy <= 0) ** GOTO lbl32
                        this.processReady(this.events, strategy);
                    } finally {
                        this.runAllTasks();
                    }
                } else {
                    ioStartTime = System.nanoTime();
                    try {
                        if (strategy > 0) {
                            this.processReady(this.events, strategy);
                        }
                    } finally {
                        ioTime = System.nanoTime() - ioStartTime;
                        this.runAllTasks(ioTime * (long)(100 - ioRatio) / (long)ioRatio);
                    }
                }
                if (this.allowGrowing && strategy == this.events.length()) {
                    this.events.increase();
                }
            } catch (Throwable t) {
                EpollEventLoop.handleLoopException(t);
            }
            try {
                if (!this.isShuttingDown()) continue;
                this.closeAll();
                if (!this.confirmShutdown()) continue;
            } catch (Throwable t) {
                EpollEventLoop.handleLoopException(t);
                continue;
            }
            break;
        }
    }

    private static void handleLoopException(Throwable t) {
        logger.warn("Unexpected exception in the selector loop.", t);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void closeAll() {
        try {
            Native.epollWait(this.epollFd.intValue(), this.events, 0);
        } catch (IOException iOException) {
            // empty catch block
        }
        ArrayList<AbstractEpollChannel> array = new ArrayList<AbstractEpollChannel>(this.channels.size());
        for (AbstractEpollChannel channel : this.channels.values()) {
            array.add(channel);
        }
        for (AbstractEpollChannel ch : array) {
            ch.unsafe().close(ch.unsafe().voidPromise());
        }
    }

    private void processReady(EpollEventArray events, int ready) {
        for (int i = 0; i < ready; ++i) {
            int fd = events.fd(i);
            if (fd == this.eventFd.intValue()) {
                Native.eventFdRead(this.eventFd.intValue());
                continue;
            }
            long ev = events.events(i);
            AbstractEpollChannel ch = this.channels.get(fd);
            if (ch != null) {
                AbstractEpollChannel.AbstractEpollUnsafe unsafe = (AbstractEpollChannel.AbstractEpollUnsafe)ch.unsafe();
                if ((ev & (long)(Native.EPOLLERR | Native.EPOLLOUT)) != 0L) {
                    unsafe.epollOutReady();
                }
                if ((ev & (long)(Native.EPOLLERR | Native.EPOLLIN)) != 0L) {
                    unsafe.epollInReady();
                }
                if ((ev & (long)Native.EPOLLRDHUP) == 0L) continue;
                unsafe.epollRdHupReady();
                continue;
            }
            try {
                Native.epollCtlDel(this.epollFd.intValue(), fd);
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
            } catch (IOException e) {
                logger.warn("Failed to close the epoll fd.", e);
            }
            try {
                this.eventFd.close();
            } catch (IOException e) {
                logger.warn("Failed to close the event fd.", e);
            }
        } finally {
            this.iovArray.release();
            this.events.free();
        }
    }
}

