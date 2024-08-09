/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.pool.ChannelHealthChecker;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import java.util.Deque;

public class SimpleChannelPool
implements ChannelPool {
    private static final AttributeKey<SimpleChannelPool> POOL_KEY;
    private static final IllegalStateException FULL_EXCEPTION;
    private final Deque<Channel> deque = PlatformDependent.newConcurrentDeque();
    private final ChannelPoolHandler handler;
    private final ChannelHealthChecker healthCheck;
    private final Bootstrap bootstrap;
    private final boolean releaseHealthCheck;
    private final boolean lastRecentUsed;
    static final boolean $assertionsDisabled;

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler) {
        this(bootstrap, channelPoolHandler, ChannelHealthChecker.ACTIVE);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, boolean bl) {
        this(bootstrap, channelPoolHandler, channelHealthChecker, bl, true);
    }

    public SimpleChannelPool(Bootstrap bootstrap, ChannelPoolHandler channelPoolHandler, ChannelHealthChecker channelHealthChecker, boolean bl, boolean bl2) {
        this.handler = ObjectUtil.checkNotNull(channelPoolHandler, "handler");
        this.healthCheck = ObjectUtil.checkNotNull(channelHealthChecker, "healthCheck");
        this.releaseHealthCheck = bl;
        this.bootstrap = ObjectUtil.checkNotNull(bootstrap, "bootstrap").clone();
        this.bootstrap.handler(new ChannelInitializer<Channel>(this, channelPoolHandler){
            static final boolean $assertionsDisabled = !SimpleChannelPool.class.desiredAssertionStatus();
            final ChannelPoolHandler val$handler;
            final SimpleChannelPool this$0;
            {
                this.this$0 = simpleChannelPool;
                this.val$handler = channelPoolHandler;
            }

            @Override
            protected void initChannel(Channel channel) throws Exception {
                if (!$assertionsDisabled && !channel.eventLoop().inEventLoop()) {
                    throw new AssertionError();
                }
                this.val$handler.channelCreated(channel);
            }
        });
        this.lastRecentUsed = bl2;
    }

    protected Bootstrap bootstrap() {
        return this.bootstrap;
    }

    protected ChannelPoolHandler handler() {
        return this.handler;
    }

    protected ChannelHealthChecker healthChecker() {
        return this.healthCheck;
    }

    protected boolean releaseHealthCheck() {
        return this.releaseHealthCheck;
    }

    @Override
    public final Future<Channel> acquire() {
        return this.acquire(this.bootstrap.config().group().next().newPromise());
    }

    @Override
    public Future<Channel> acquire(Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        return this.acquireHealthyFromPoolOrNew(promise);
    }

    private Future<Channel> acquireHealthyFromPoolOrNew(Promise<Channel> promise) {
        try {
            Channel channel = this.pollChannel();
            if (channel == null) {
                Bootstrap bootstrap = this.bootstrap.clone();
                bootstrap.attr(POOL_KEY, this);
                ChannelFuture channelFuture = this.connectChannel(bootstrap);
                if (channelFuture.isDone()) {
                    this.notifyConnect(channelFuture, promise);
                } else {
                    channelFuture.addListener(new ChannelFutureListener(this, promise){
                        final Promise val$promise;
                        final SimpleChannelPool this$0;
                        {
                            this.this$0 = simpleChannelPool;
                            this.val$promise = promise;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            SimpleChannelPool.access$000(this.this$0, channelFuture, this.val$promise);
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    });
                }
                return promise;
            }
            EventLoop eventLoop = channel.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.doHealthCheck(channel, promise);
            } else {
                eventLoop.execute(new Runnable(this, channel, promise){
                    final Channel val$ch;
                    final Promise val$promise;
                    final SimpleChannelPool this$0;
                    {
                        this.this$0 = simpleChannelPool;
                        this.val$ch = channel;
                        this.val$promise = promise;
                    }

                    @Override
                    public void run() {
                        SimpleChannelPool.access$100(this.this$0, this.val$ch, this.val$promise);
                    }
                });
            }
        } catch (Throwable throwable) {
            promise.tryFailure(throwable);
        }
        return promise;
    }

    private void notifyConnect(ChannelFuture channelFuture, Promise<Channel> promise) {
        if (channelFuture.isSuccess()) {
            Channel channel = channelFuture.channel();
            if (!promise.trySuccess(channel)) {
                this.release(channel);
            }
        } else {
            promise.tryFailure(channelFuture.cause());
        }
    }

    private void doHealthCheck(Channel channel, Promise<Channel> promise) {
        if (!$assertionsDisabled && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        }
        Future<Boolean> future = this.healthCheck.isHealthy(channel);
        if (future.isDone()) {
            this.notifyHealthCheck(future, channel, promise);
        } else {
            future.addListener((GenericFutureListener<Future<Boolean>>)new FutureListener<Boolean>(this, channel, promise){
                final Channel val$ch;
                final Promise val$promise;
                final SimpleChannelPool this$0;
                {
                    this.this$0 = simpleChannelPool;
                    this.val$ch = channel;
                    this.val$promise = promise;
                }

                @Override
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.access$200(this.this$0, future, this.val$ch, this.val$promise);
                }
            });
        }
    }

    private void notifyHealthCheck(Future<Boolean> future, Channel channel, Promise<Channel> promise) {
        if (!$assertionsDisabled && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        }
        if (future.isSuccess()) {
            if (future.getNow().booleanValue()) {
                try {
                    channel.attr(POOL_KEY).set(this);
                    this.handler.channelAcquired(channel);
                    promise.setSuccess(channel);
                } catch (Throwable throwable) {
                    SimpleChannelPool.closeAndFail(channel, throwable, promise);
                }
            } else {
                SimpleChannelPool.closeChannel(channel);
                this.acquireHealthyFromPoolOrNew(promise);
            }
        } else {
            SimpleChannelPool.closeChannel(channel);
            this.acquireHealthyFromPoolOrNew(promise);
        }
    }

    protected ChannelFuture connectChannel(Bootstrap bootstrap) {
        return bootstrap.connect();
    }

    @Override
    public final Future<Void> release(Channel channel) {
        return this.release(channel, channel.eventLoop().newPromise());
    }

    @Override
    public Future<Void> release(Channel channel, Promise<Void> promise) {
        ObjectUtil.checkNotNull(channel, "channel");
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            EventLoop eventLoop = channel.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.doReleaseChannel(channel, promise);
            } else {
                eventLoop.execute(new Runnable(this, channel, promise){
                    final Channel val$channel;
                    final Promise val$promise;
                    final SimpleChannelPool this$0;
                    {
                        this.this$0 = simpleChannelPool;
                        this.val$channel = channel;
                        this.val$promise = promise;
                    }

                    @Override
                    public void run() {
                        SimpleChannelPool.access$300(this.this$0, this.val$channel, this.val$promise);
                    }
                });
            }
        } catch (Throwable throwable) {
            SimpleChannelPool.closeAndFail(channel, throwable, promise);
        }
        return promise;
    }

    private void doReleaseChannel(Channel channel, Promise<Void> promise) {
        if (!$assertionsDisabled && !channel.eventLoop().inEventLoop()) {
            throw new AssertionError();
        }
        if (channel.attr(POOL_KEY).getAndSet(null) != this) {
            SimpleChannelPool.closeAndFail(channel, new IllegalArgumentException("Channel " + channel + " was not acquired from this ChannelPool"), promise);
        } else {
            try {
                if (this.releaseHealthCheck) {
                    this.doHealthCheckOnRelease(channel, promise);
                } else {
                    this.releaseAndOffer(channel, promise);
                }
            } catch (Throwable throwable) {
                SimpleChannelPool.closeAndFail(channel, throwable, promise);
            }
        }
    }

    private void doHealthCheckOnRelease(Channel channel, Promise<Void> promise) throws Exception {
        Future<Boolean> future = this.healthCheck.isHealthy(channel);
        if (future.isDone()) {
            this.releaseAndOfferIfHealthy(channel, promise, future);
        } else {
            future.addListener((GenericFutureListener<Future<Boolean>>)new FutureListener<Boolean>(this, channel, promise, future){
                final Channel val$channel;
                final Promise val$promise;
                final Future val$f;
                final SimpleChannelPool this$0;
                {
                    this.this$0 = simpleChannelPool;
                    this.val$channel = channel;
                    this.val$promise = promise;
                    this.val$f = future;
                }

                @Override
                public void operationComplete(Future<Boolean> future) throws Exception {
                    SimpleChannelPool.access$400(this.this$0, this.val$channel, this.val$promise, this.val$f);
                }
            });
        }
    }

    private void releaseAndOfferIfHealthy(Channel channel, Promise<Void> promise, Future<Boolean> future) throws Exception {
        if (future.getNow().booleanValue()) {
            this.releaseAndOffer(channel, promise);
        } else {
            this.handler.channelReleased(channel);
            promise.setSuccess(null);
        }
    }

    private void releaseAndOffer(Channel channel, Promise<Void> promise) throws Exception {
        if (this.offerChannel(channel)) {
            this.handler.channelReleased(channel);
            promise.setSuccess(null);
        } else {
            SimpleChannelPool.closeAndFail(channel, FULL_EXCEPTION, promise);
        }
    }

    private static void closeChannel(Channel channel) {
        channel.attr(POOL_KEY).getAndSet(null);
        channel.close();
    }

    private static void closeAndFail(Channel channel, Throwable throwable, Promise<?> promise) {
        SimpleChannelPool.closeChannel(channel);
        promise.tryFailure(throwable);
    }

    protected Channel pollChannel() {
        return this.lastRecentUsed ? this.deque.pollLast() : this.deque.pollFirst();
    }

    protected boolean offerChannel(Channel channel) {
        return this.deque.offer(channel);
    }

    @Override
    public void close() {
        Channel channel;
        while ((channel = this.pollChannel()) != null) {
            channel.close();
        }
    }

    static void access$000(SimpleChannelPool simpleChannelPool, ChannelFuture channelFuture, Promise promise) {
        simpleChannelPool.notifyConnect(channelFuture, promise);
    }

    static void access$100(SimpleChannelPool simpleChannelPool, Channel channel, Promise promise) {
        simpleChannelPool.doHealthCheck(channel, promise);
    }

    static void access$200(SimpleChannelPool simpleChannelPool, Future future, Channel channel, Promise promise) {
        simpleChannelPool.notifyHealthCheck(future, channel, promise);
    }

    static void access$300(SimpleChannelPool simpleChannelPool, Channel channel, Promise promise) {
        simpleChannelPool.doReleaseChannel(channel, promise);
    }

    static void access$400(SimpleChannelPool simpleChannelPool, Channel channel, Promise promise, Future future) throws Exception {
        simpleChannelPool.releaseAndOfferIfHealthy(channel, promise, future);
    }

    static {
        $assertionsDisabled = !SimpleChannelPool.class.desiredAssertionStatus();
        POOL_KEY = AttributeKey.newInstance("channelPool");
        FULL_EXCEPTION = ThrowableUtil.unknownStackTrace(new IllegalStateException("ChannelPool full"), SimpleChannelPool.class, "releaseAndOffer(...)");
    }
}

