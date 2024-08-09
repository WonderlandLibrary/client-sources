/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class SingleThreadEventLoop
extends SingleThreadEventExecutor
implements EventLoop {
    protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));
    private final Queue<Runnable> tailTasks;

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean bl) {
        this(eventLoopGroup, threadFactory, bl, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean bl) {
        this(eventLoopGroup, executor, bl, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean bl, int n, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventExecutorGroup)eventLoopGroup, threadFactory, bl, n, rejectedExecutionHandler);
        this.tailTasks = this.newTaskQueue(n);
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean bl, int n, RejectedExecutionHandler rejectedExecutionHandler) {
        super((EventExecutorGroup)eventLoopGroup, executor, bl, n, rejectedExecutionHandler);
        this.tailTasks = this.newTaskQueue(n);
    }

    @Override
    public EventLoopGroup parent() {
        return (EventLoopGroup)super.parent();
    }

    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }

    @Override
    public ChannelFuture register(Channel channel) {
        return this.register(new DefaultChannelPromise(channel, this));
    }

    @Override
    public ChannelFuture register(ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        channelPromise.channel().unsafe().register(this, channelPromise);
        return channelPromise;
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
    }

    public final void executeAfterEventLoopIteration(Runnable runnable) {
        ObjectUtil.checkNotNull(runnable, "task");
        if (this.isShutdown()) {
            SingleThreadEventLoop.reject();
        }
        if (!this.tailTasks.offer(runnable)) {
            this.reject(runnable);
        }
        if (this.wakesUpForTask(runnable)) {
            this.wakeup(this.inEventLoop());
        }
    }

    final boolean removeAfterEventLoopIterationTask(Runnable runnable) {
        return this.tailTasks.remove(ObjectUtil.checkNotNull(runnable, "task"));
    }

    @Override
    protected boolean wakesUpForTask(Runnable runnable) {
        return !(runnable instanceof NonWakeupRunnable);
    }

    @Override
    protected void afterRunningAllTasks() {
        this.runAllTasksFrom(this.tailTasks);
    }

    @Override
    protected boolean hasTasks() {
        return super.hasTasks() || !this.tailTasks.isEmpty();
    }

    @Override
    public int pendingTasks() {
        return super.pendingTasks() + this.tailTasks.size();
    }

    @Override
    public EventExecutorGroup parent() {
        return this.parent();
    }

    @Override
    public EventExecutor next() {
        return this.next();
    }

    static interface NonWakeupRunnable
    extends Runnable {
    }
}

