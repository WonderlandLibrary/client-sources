/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorChooserFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class MultithreadEventLoopGroup
extends MultithreadEventExecutorGroup
implements EventLoopGroup {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
    private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));

    protected MultithreadEventLoopGroup(int n, Executor executor, Object ... objectArray) {
        super(n == 0 ? DEFAULT_EVENT_LOOP_THREADS : n, executor, objectArray);
    }

    protected MultithreadEventLoopGroup(int n, ThreadFactory threadFactory, Object ... objectArray) {
        super(n == 0 ? DEFAULT_EVENT_LOOP_THREADS : n, threadFactory, objectArray);
    }

    protected MultithreadEventLoopGroup(int n, Executor executor, EventExecutorChooserFactory eventExecutorChooserFactory, Object ... objectArray) {
        super(n == 0 ? DEFAULT_EVENT_LOOP_THREADS : n, executor, eventExecutorChooserFactory, objectArray);
    }

    @Override
    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(this.getClass(), 10);
    }

    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }

    @Override
    protected abstract EventLoop newChild(Executor var1, Object ... var2) throws Exception;

    @Override
    public ChannelFuture register(Channel channel) {
        return this.next().register(channel);
    }

    @Override
    public ChannelFuture register(ChannelPromise channelPromise) {
        return this.next().register(channelPromise);
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return this.next().register(channel, channelPromise);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object[] objectArray) throws Exception {
        return this.newChild(executor, objectArray);
    }

    @Override
    public EventExecutor next() {
        return this.next();
    }

    static {
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.eventLoopThreads: {}", (Object)DEFAULT_EVENT_LOOP_THREADS);
        }
    }
}

