/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FailedChannelFuture;
import io.netty.channel.ThreadPerChannelEventLoop;
import io.netty.util.concurrent.AbstractEventExecutorGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import io.netty.util.internal.ThrowableUtil;
import java.util.Collections;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ThreadPerChannelEventLoopGroup
extends AbstractEventExecutorGroup
implements EventLoopGroup {
    private final Object[] childArgs;
    private final int maxChannels;
    final Executor executor;
    final Set<EventLoop> activeChildren = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
    final Queue<EventLoop> idleChildren = new ConcurrentLinkedQueue<EventLoop>();
    private final ChannelException tooManyChannels;
    private volatile boolean shuttingDown;
    private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
    private final FutureListener<Object> childTerminationListener = new FutureListener<Object>(this){
        final ThreadPerChannelEventLoopGroup this$0;
        {
            this.this$0 = threadPerChannelEventLoopGroup;
        }

        @Override
        public void operationComplete(Future<Object> future) throws Exception {
            if (this.this$0.isTerminated()) {
                ThreadPerChannelEventLoopGroup.access$000(this.this$0).trySuccess(null);
            }
        }
    };

    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }

    protected ThreadPerChannelEventLoopGroup(int n) {
        this(n, Executors.defaultThreadFactory(), new Object[0]);
    }

    protected ThreadPerChannelEventLoopGroup(int n, ThreadFactory threadFactory, Object ... objectArray) {
        this(n, new ThreadPerTaskExecutor(threadFactory), objectArray);
    }

    protected ThreadPerChannelEventLoopGroup(int n, Executor executor, Object ... objectArray) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", n));
        }
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        this.childArgs = objectArray == null ? EmptyArrays.EMPTY_OBJECTS : (Object[])objectArray.clone();
        this.maxChannels = n;
        this.executor = executor;
        this.tooManyChannels = ThrowableUtil.unknownStackTrace(new ChannelException("too many channels (max: " + n + ')'), ThreadPerChannelEventLoopGroup.class, "nextChild()");
    }

    protected EventLoop newChild(Object ... objectArray) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }

    @Override
    public Iterator<EventExecutor> iterator() {
        return new ReadOnlyIterator<EventExecutor>(this.activeChildren.iterator());
    }

    @Override
    public EventLoop next() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Future<?> shutdownGracefully(long l, long l2, TimeUnit timeUnit) {
        this.shuttingDown = true;
        for (EventLoop eventLoop : this.activeChildren) {
            eventLoop.shutdownGracefully(l, l2, timeUnit);
        }
        for (EventLoop eventLoop : this.idleChildren) {
            eventLoop.shutdownGracefully(l, l2, timeUnit);
        }
        if (this.isTerminated()) {
            this.terminationFuture.trySuccess(null);
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
        this.shuttingDown = true;
        for (EventLoop eventLoop : this.activeChildren) {
            eventLoop.shutdown();
        }
        for (EventLoop eventLoop : this.idleChildren) {
            eventLoop.shutdown();
        }
        if (this.isTerminated()) {
            this.terminationFuture.trySuccess(null);
        }
    }

    @Override
    public boolean isShuttingDown() {
        for (EventLoop eventLoop : this.activeChildren) {
            if (eventLoop.isShuttingDown()) continue;
            return true;
        }
        for (EventLoop eventLoop : this.idleChildren) {
            if (eventLoop.isShuttingDown()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isShutdown() {
        for (EventLoop eventLoop : this.activeChildren) {
            if (eventLoop.isShutdown()) continue;
            return true;
        }
        for (EventLoop eventLoop : this.idleChildren) {
            if (eventLoop.isShutdown()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isTerminated() {
        for (EventLoop eventLoop : this.activeChildren) {
            if (eventLoop.isTerminated()) continue;
            return true;
        }
        for (EventLoop eventLoop : this.idleChildren) {
            if (eventLoop.isTerminated()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        long l2;
        long l3 = System.nanoTime() + timeUnit.toNanos(l);
        for (EventLoop eventLoop : this.activeChildren) {
            do {
                if ((l2 = l3 - System.nanoTime()) > 0L) continue;
                return this.isTerminated();
            } while (!eventLoop.awaitTermination(l2, TimeUnit.NANOSECONDS));
        }
        for (EventLoop eventLoop : this.idleChildren) {
            do {
                if ((l2 = l3 - System.nanoTime()) > 0L) continue;
                return this.isTerminated();
            } while (!eventLoop.awaitTermination(l2, TimeUnit.NANOSECONDS));
        }
        return this.isTerminated();
    }

    @Override
    public ChannelFuture register(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            EventLoop eventLoop = this.nextChild();
            return eventLoop.register(new DefaultChannelPromise(channel, eventLoop));
        } catch (Throwable throwable) {
            return new FailedChannelFuture(channel, GlobalEventExecutor.INSTANCE, throwable);
        }
    }

    @Override
    public ChannelFuture register(ChannelPromise channelPromise) {
        try {
            return this.nextChild().register(channelPromise);
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
            return channelPromise;
        }
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            return this.nextChild().register(channel, channelPromise);
        } catch (Throwable throwable) {
            channelPromise.setFailure(throwable);
            return channelPromise;
        }
    }

    private EventLoop nextChild() throws Exception {
        if (this.shuttingDown) {
            throw new RejectedExecutionException("shutting down");
        }
        EventLoop eventLoop = this.idleChildren.poll();
        if (eventLoop == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
                throw this.tooManyChannels;
            }
            eventLoop = this.newChild(this.childArgs);
            eventLoop.terminationFuture().addListener(this.childTerminationListener);
        }
        this.activeChildren.add(eventLoop);
        return eventLoop;
    }

    @Override
    public EventExecutor next() {
        return this.next();
    }

    static Promise access$000(ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        return threadPerChannelEventLoopGroup.terminationFuture;
    }
}

