/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.SelectStrategyFactory;
import io.netty.channel.nio.NioEventLoop;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NioEventLoopGroup
extends MultithreadEventLoopGroup {
    public NioEventLoopGroup() {
        this(0);
    }

    public NioEventLoopGroup(int n) {
        this(n, (Executor)null);
    }

    public NioEventLoopGroup(int n, ThreadFactory threadFactory) {
        this(n, threadFactory, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int n, Executor executor) {
        this(n, executor, SelectorProvider.provider());
    }

    public NioEventLoopGroup(int n, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
        this(n, threadFactory, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int n, ThreadFactory threadFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(n, threadFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int n, Executor executor, SelectorProvider selectorProvider) {
        this(n, executor, selectorProvider, DefaultSelectStrategyFactory.INSTANCE);
    }

    public NioEventLoopGroup(int n, Executor executor, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(n, executor, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory) {
        super(n, executor, eventExecutorChooserFactory, selectorProvider, selectStrategyFactory, RejectedExecutionHandlers.reject());
    }

    public NioEventLoopGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, SelectorProvider selectorProvider, SelectStrategyFactory selectStrategyFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, executor, eventExecutorChooserFactory, selectorProvider, selectStrategyFactory, rejectedExecutionHandler);
    }

    public void setIoRatio(int n) {
        for (EventExecutor eventExecutor : this) {
            ((NioEventLoop)eventExecutor).setIoRatio(n);
        }
    }

    public void rebuildSelectors() {
        for (EventExecutor eventExecutor : this) {
            ((NioEventLoop)eventExecutor).rebuildSelector();
        }
    }

    @Override
    protected EventLoop newChild(Executor executor, Object ... objectArray) throws Exception {
        return new NioEventLoop(this, executor, (SelectorProvider)objectArray[0], ((SelectStrategyFactory)objectArray[5]).newSelectStrategy(), (RejectedExecutionHandler)objectArray[5]);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object[] objectArray) throws Exception {
        return this.newChild(executor, objectArray);
    }
}

