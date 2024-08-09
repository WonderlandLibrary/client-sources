/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EpollEventLoopGroup
extends MultithreadEventLoopGroup {
    public EpollEventLoopGroup() {
        this(0);
    }

    public EpollEventLoopGroup(int n) {
        this(n, (ThreadFactory)null);
    }

    public EpollEventLoopGroup(int n, SelectStrategyFactory selectStrategyFactory) {
        this(n, (ThreadFactory)null, selectStrategyFactory);
    }

    public EpollEventLoopGroup(int n, ThreadFactory threadFactory) {
        this(n, threadFactory, 0);
    }

    public EpollEventLoopGroup(int n, Executor executor) {
        this(n, executor, DefaultSelectStrategyFactory.INSTANCE);
    }

    public EpollEventLoopGroup(int n, ThreadFactory threadFactory, SelectStrategyFactory selectStrategyFactory) {
        this(n, threadFactory, 0, selectStrategyFactory);
    }

    @Deprecated
    public EpollEventLoopGroup(int n, ThreadFactory threadFactory, int n2) {
        this(n, threadFactory, n2, DefaultSelectStrategyFactory.INSTANCE);
    }

    @Deprecated
    public EpollEventLoopGroup(int n, ThreadFactory threadFactory, int n2, SelectStrategyFactory selectStrategyFactory) {
        super(n, threadFactory, n2, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int n, Executor executor, SelectStrategyFactory selectStrategyFactory) {
        super(n, executor, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectStrategyFactory selectStrategyFactory) {
        super(n, executor, eventExecutorChooserFactory, 0, selectStrategyFactory, RejectedExecutionHandlers.reject());
        Epoll.ensureAvailability();
    }

    public EpollEventLoopGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, executor, eventExecutorChooserFactory, 0, selectStrategyFactory, rejectedExecutionHandler);
        Epoll.ensureAvailability();
    }

    public void setIoRatio(int n) {
        for (EventExecutor eventExecutor : this) {
            ((EpollEventLoop)eventExecutor).setIoRatio(n);
        }
    }

    @Override
    protected EventLoop newChild(Executor executor, Object ... objectArray) throws Exception {
        return new EpollEventLoop((EventLoopGroup)this, executor, (Integer)objectArray[0], ((SelectStrategyFactory)objectArray[5]).newSelectStrategy(), (RejectedExecutionHandler)objectArray[5]);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object[] objectArray) throws Exception {
        return this.newChild(executor, objectArray);
    }
}

