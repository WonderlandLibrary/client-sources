/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http2.Http2MultiplexCodec;
import io.netty.handler.codec.http2.Http2StreamChannel;
import io.netty.util.AbstractConstant;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.ClosedChannelException;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Http2StreamChannelBootstrap {
    private static final InternalLogger logger;
    private final Map<ChannelOption<?>, Object> options = new LinkedHashMap();
    private final Map<AttributeKey<?>, Object> attrs = new LinkedHashMap();
    private final Channel channel;
    private volatile ChannelHandler handler;
    static final boolean $assertionsDisabled;

    public Http2StreamChannelBootstrap(Channel channel) {
        this.channel = ObjectUtil.checkNotNull(channel, "channel");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> Http2StreamChannelBootstrap option(ChannelOption<T> channelOption, T t) {
        if (channelOption == null) {
            throw new NullPointerException("option");
        }
        if (t == null) {
            Map<ChannelOption<?>, Object> map = this.options;
            synchronized (map) {
                this.options.remove(channelOption);
            }
        }
        Map<ChannelOption<?>, Object> map = this.options;
        synchronized (map) {
            this.options.put(channelOption, t);
        }
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> Http2StreamChannelBootstrap attr(AttributeKey<T> attributeKey, T t) {
        if (attributeKey == null) {
            throw new NullPointerException("key");
        }
        if (t == null) {
            Map<AttributeKey<?>, Object> map = this.attrs;
            synchronized (map) {
                this.attrs.remove(attributeKey);
            }
        }
        Map<AttributeKey<?>, Object> map = this.attrs;
        synchronized (map) {
            this.attrs.put(attributeKey, t);
        }
        return this;
    }

    public Http2StreamChannelBootstrap handler(ChannelHandler channelHandler) {
        this.handler = ObjectUtil.checkNotNull(channelHandler, "handler");
        return this;
    }

    public Future<Http2StreamChannel> open() {
        return this.open(this.channel.eventLoop().newPromise());
    }

    public Future<Http2StreamChannel> open(Promise<Http2StreamChannel> promise) {
        ChannelHandlerContext channelHandlerContext = this.channel.pipeline().context(Http2MultiplexCodec.class);
        if (channelHandlerContext == null) {
            if (this.channel.isActive()) {
                promise.setFailure(new IllegalStateException(StringUtil.simpleClassName(Http2MultiplexCodec.class) + " must be in the ChannelPipeline of Channel " + this.channel));
            } else {
                promise.setFailure(new ClosedChannelException());
            }
        } else {
            EventExecutor eventExecutor = channelHandlerContext.executor();
            if (eventExecutor.inEventLoop()) {
                this.open0(channelHandlerContext, promise);
            } else {
                eventExecutor.execute(new Runnable(this, channelHandlerContext, promise){
                    final ChannelHandlerContext val$ctx;
                    final Promise val$promise;
                    final Http2StreamChannelBootstrap this$0;
                    {
                        this.this$0 = http2StreamChannelBootstrap;
                        this.val$ctx = channelHandlerContext;
                        this.val$promise = promise;
                    }

                    @Override
                    public void run() {
                        this.this$0.open0(this.val$ctx, this.val$promise);
                    }
                });
            }
        }
        return promise;
    }

    public void open0(ChannelHandlerContext channelHandlerContext, Promise<Http2StreamChannel> promise) {
        if (!$assertionsDisabled && !channelHandlerContext.executor().inEventLoop()) {
            throw new AssertionError();
        }
        Http2StreamChannel http2StreamChannel = ((Http2MultiplexCodec)channelHandlerContext.handler()).newOutboundStream();
        try {
            this.init(http2StreamChannel);
        } catch (Exception exception) {
            http2StreamChannel.unsafe().closeForcibly();
            promise.setFailure(exception);
            return;
        }
        ChannelFuture channelFuture = channelHandlerContext.channel().eventLoop().register(http2StreamChannel);
        channelFuture.addListener(new ChannelFutureListener(this, promise, http2StreamChannel){
            final Promise val$promise;
            final Http2StreamChannel val$streamChannel;
            final Http2StreamChannelBootstrap this$0;
            {
                this.this$0 = http2StreamChannelBootstrap;
                this.val$promise = promise;
                this.val$streamChannel = http2StreamChannel;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    this.val$promise.setSuccess(this.val$streamChannel);
                } else if (channelFuture.isCancelled()) {
                    this.val$promise.cancel(false);
                } else {
                    if (this.val$streamChannel.isRegistered()) {
                        this.val$streamChannel.close();
                    } else {
                        this.val$streamChannel.unsafe().closeForcibly();
                    }
                    this.val$promise.setFailure(channelFuture.cause());
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void init(Channel channel) throws Exception {
        ChannelPipeline channelPipeline = channel.pipeline();
        ChannelHandler channelHandler = this.handler;
        if (channelHandler != null) {
            channelPipeline.addLast(channelHandler);
        }
        Map<AbstractConstant, Object> map = this.options;
        synchronized (map) {
            Http2StreamChannelBootstrap.setChannelOptions(channel, this.options, logger);
        }
        map = this.attrs;
        synchronized (map) {
            for (Map.Entry<AttributeKey<?>, Object> entry : this.attrs.entrySet()) {
                channel.attr(entry.getKey()).set(entry.getValue());
            }
        }
    }

    private static void setChannelOptions(Channel channel, Map<ChannelOption<?>, Object> map, InternalLogger internalLogger) {
        for (Map.Entry<ChannelOption<?>, Object> entry : map.entrySet()) {
            Http2StreamChannelBootstrap.setChannelOption(channel, entry.getKey(), entry.getValue(), internalLogger);
        }
    }

    private static void setChannelOption(Channel channel, ChannelOption<?> channelOption, Object object, InternalLogger internalLogger) {
        try {
            if (!channel.config().setOption(channelOption, object)) {
                internalLogger.warn("Unknown channel option '{}' for channel '{}'", (Object)channelOption, (Object)channel);
            }
        } catch (Throwable throwable) {
            internalLogger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", channelOption, object, channel, throwable);
        }
    }

    static {
        $assertionsDisabled = !Http2StreamChannelBootstrap.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(Http2StreamChannelBootstrap.class);
    }
}

