/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http2.AbstractHttp2StreamChannel;
import io.netty.handler.codec.http2.DefaultHttp2ResetFrame;
import io.netty.handler.codec.http2.DefaultHttp2WindowUpdateFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Frame;
import io.netty.handler.codec.http2.Http2GoAwayFrame;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2StreamActiveEvent;
import io.netty.handler.codec.http2.Http2StreamChannelBootstrap;
import io.netty.handler.codec.http2.Http2StreamClosedEvent;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Http2MultiplexCodec
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2MultiplexCodec.class);
    private final Http2StreamChannelBootstrap bootstrap;
    private final List<Http2StreamChannel> channelsToFireChildReadComplete = new ArrayList<Http2StreamChannel>();
    private final boolean server;
    private ChannelHandlerContext ctx;
    private volatile Runnable flushTask;
    private final IntObjectMap<Http2StreamChannel> childChannels = new IntObjectHashMap<Http2StreamChannel>();

    public Http2MultiplexCodec(boolean server, Http2StreamChannelBootstrap bootstrap) {
        if (bootstrap.parentChannel() != null) {
            throw new IllegalStateException("The parent channel must not be set on the bootstrap.");
        }
        this.server = server;
        this.bootstrap = new Http2StreamChannelBootstrap(bootstrap);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        this.bootstrap.parentChannel(ctx.channel());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (!(cause instanceof Http2Exception.StreamException)) {
            ctx.fireExceptionCaught(cause);
            return;
        }
        Http2Exception.StreamException streamEx = (Http2Exception.StreamException)cause;
        try {
            Http2StreamChannel childChannel = this.childChannels.get(streamEx.streamId());
            if (childChannel != null) {
                childChannel.pipeline().fireExceptionCaught(streamEx);
            } else {
                logger.warn(String.format("Exception caught for unknown HTTP/2 stream '%d'", streamEx.streamId()), streamEx);
            }
        } finally {
            this.onStreamClosed(streamEx.streamId());
        }
    }

    @Override
    public void flush(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof Http2Frame)) {
            ctx.fireChannelRead(msg);
            return;
        }
        if (msg instanceof Http2StreamFrame) {
            Http2StreamFrame frame = (Http2StreamFrame)msg;
            int streamId = frame.streamId();
            Http2StreamChannel childChannel = this.childChannels.get(streamId);
            if (childChannel == null) {
                ReferenceCountUtil.release(msg);
                throw new Http2Exception.StreamException(streamId, Http2Error.STREAM_CLOSED, String.format("Received %s frame for an unknown stream %d", frame.name(), streamId));
            }
            this.fireChildReadAndRegister(childChannel, frame);
        } else if (msg instanceof Http2GoAwayFrame) {
            Http2GoAwayFrame goAwayFrame = (Http2GoAwayFrame)msg;
            for (IntObjectMap.PrimitiveEntry<Http2StreamChannel> entry : this.childChannels.entries()) {
                Http2StreamChannel childChannel = entry.value();
                int streamId = entry.key();
                if (streamId <= goAwayFrame.lastStreamId() || !Http2CodecUtil.isOutboundStream(this.server, streamId)) continue;
                childChannel.pipeline().fireUserEventTriggered(goAwayFrame.retainedDuplicate());
            }
            goAwayFrame.release();
        } else {
            ReferenceCountUtil.release(msg);
            throw new UnsupportedMessageTypeException(msg, new Class[0]);
        }
    }

    private void fireChildReadAndRegister(Http2StreamChannel childChannel, Http2StreamFrame frame) {
        childChannel.fireChildRead(frame);
        if (!childChannel.inStreamsToFireChildReadComplete) {
            this.channelsToFireChildReadComplete.add(childChannel);
            childChannel.inStreamsToFireChildReadComplete = true;
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof Http2StreamActiveEvent) {
            Http2StreamActiveEvent activeEvent = (Http2StreamActiveEvent)evt;
            this.onStreamActive(activeEvent.streamId(), activeEvent.headers());
        } else if (evt instanceof Http2StreamClosedEvent) {
            this.onStreamClosed(((Http2StreamClosedEvent)evt).streamId());
        } else {
            ctx.fireUserEventTriggered(evt);
        }
    }

    private void onStreamActive(int streamId, Http2HeadersFrame headersFrame) {
        Http2StreamChannel childChannel;
        if (Http2CodecUtil.isOutboundStream(this.server, streamId)) {
            if (!(headersFrame instanceof ChannelCarryingHeadersFrame)) {
                throw new IllegalArgumentException("needs to be wrapped");
            }
            childChannel = ((ChannelCarryingHeadersFrame)headersFrame).channel();
            childChannel.streamId(streamId);
        } else {
            ChannelFuture future = this.bootstrap.connect(streamId);
            childChannel = (Http2StreamChannel)future.channel();
        }
        Http2StreamChannel existing = this.childChannels.put(streamId, childChannel);
        assert (existing == null);
    }

    private void onStreamClosed(int streamId) {
        final Http2StreamChannel childChannel = this.childChannels.remove(streamId);
        if (childChannel != null) {
            EventLoop eventLoop = childChannel.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.onStreamClosed0(childChannel);
            } else {
                eventLoop.execute(new Runnable(){

                    @Override
                    public void run() {
                        Http2MultiplexCodec.this.onStreamClosed0(childChannel);
                    }
                });
            }
        }
    }

    private void onStreamClosed0(Http2StreamChannel childChannel) {
        assert (childChannel.eventLoop().inEventLoop());
        childChannel.onStreamClosedFired = true;
        childChannel.fireChildRead(AbstractHttp2StreamChannel.CLOSE_MESSAGE);
    }

    void flushFromStreamChannel() {
        EventExecutor executor = this.ctx.executor();
        if (executor.inEventLoop()) {
            this.flush(this.ctx);
        } else {
            Runnable task = this.flushTask;
            if (task == null) {
                task = this.flushTask = new Runnable(){

                    @Override
                    public void run() {
                        Http2MultiplexCodec.this.flush(Http2MultiplexCodec.this.ctx);
                    }
                };
            }
            executor.execute(task);
        }
    }

    void writeFromStreamChannel(Object msg, boolean flush) {
        this.writeFromStreamChannel(msg, this.ctx.newPromise(), flush);
    }

    void writeFromStreamChannel(final Object msg, final ChannelPromise promise, final boolean flush) {
        EventExecutor executor = this.ctx.executor();
        if (executor.inEventLoop()) {
            this.writeFromStreamChannel0(msg, flush, promise);
        } else {
            try {
                executor.execute(new Runnable(){

                    @Override
                    public void run() {
                        Http2MultiplexCodec.this.writeFromStreamChannel0(msg, flush, promise);
                    }
                });
            } catch (Throwable cause) {
                promise.setFailure(cause);
            }
        }
    }

    private void writeFromStreamChannel0(Object msg, boolean flush, ChannelPromise promise) {
        try {
            this.write(this.ctx, msg, promise);
        } catch (Throwable cause) {
            promise.tryFailure(cause);
        }
        if (flush) {
            this.flush(this.ctx);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        for (int i = 0; i < this.channelsToFireChildReadComplete.size(); ++i) {
            Http2StreamChannel childChannel = this.channelsToFireChildReadComplete.get(i);
            childChannel.inStreamsToFireChildReadComplete = false;
            childChannel.fireChildReadComplete();
        }
        this.channelsToFireChildReadComplete.clear();
    }

    ChannelFuture createStreamChannel(Channel parentChannel, EventLoopGroup group, ChannelHandler handler, Map<ChannelOption<?>, Object> options, Map<AttributeKey<?>, Object> attrs, int streamId) {
        Http2StreamChannel channel = new Http2StreamChannel(parentChannel);
        if (Http2CodecUtil.isStreamIdValid(streamId)) {
            assert (!Http2CodecUtil.isOutboundStream(this.server, streamId));
            assert (this.ctx.channel().eventLoop().inEventLoop());
            channel.streamId(streamId);
        }
        channel.pipeline().addLast(handler);
        Http2MultiplexCodec.initOpts(channel, options);
        Http2MultiplexCodec.initAttrs(channel, attrs);
        ChannelFuture future = group.register(channel);
        if (future.cause() != null) {
            if (channel.isRegistered()) {
                channel.close();
            } else {
                channel.unsafe().closeForcibly();
            }
        }
        return future;
    }

    private static void initOpts(Channel channel, Map<ChannelOption<?>, Object> opts) {
        if (opts != null) {
            for (Map.Entry<ChannelOption<?>, Object> e : opts.entrySet()) {
                try {
                    if (channel.config().setOption(e.getKey(), e.getValue())) continue;
                    logger.warn("Unknown channel option: " + e);
                } catch (Throwable t) {
                    logger.warn("Failed to set a channel option: " + channel, t);
                }
            }
        }
    }

    private static void initAttrs(Channel channel, Map<AttributeKey<?>, Object> attrs) {
        if (attrs != null) {
            for (Map.Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
                channel.attr(e.getKey()).set(e.getValue());
            }
        }
    }

    private static final class ChannelCarryingHeadersFrame
    implements Http2HeadersFrame {
        private final Http2HeadersFrame frame;
        private final Http2StreamChannel childChannel;

        ChannelCarryingHeadersFrame(Http2HeadersFrame frame, Http2StreamChannel childChannel) {
            this.frame = frame;
            this.childChannel = childChannel;
        }

        @Override
        public Http2Headers headers() {
            return this.frame.headers();
        }

        @Override
        public boolean isEndStream() {
            return this.frame.isEndStream();
        }

        @Override
        public int padding() {
            return this.frame.padding();
        }

        @Override
        public Http2StreamFrame streamId(int streamId) {
            return this.frame.streamId(streamId);
        }

        @Override
        public int streamId() {
            return this.frame.streamId();
        }

        @Override
        public String name() {
            return this.frame.name();
        }

        Http2StreamChannel channel() {
            return this.childChannel;
        }
    }

    final class Http2StreamChannel
    extends AbstractHttp2StreamChannel
    implements ChannelFutureListener {
        boolean onStreamClosedFired;
        boolean inStreamsToFireChildReadComplete;

        Http2StreamChannel(Channel parentChannel) {
            super(parentChannel);
        }

        @Override
        protected void doClose() throws Exception {
            if (!this.onStreamClosedFired && Http2CodecUtil.isStreamIdValid(this.streamId())) {
                DefaultHttp2ResetFrame resetFrame = new DefaultHttp2ResetFrame(Http2Error.CANCEL).streamId(this.streamId());
                Http2MultiplexCodec.this.writeFromStreamChannel(resetFrame, true);
            }
            super.doClose();
        }

        @Override
        protected void doWrite(Object msg) {
            if (msg instanceof Http2StreamFrame) {
                Http2StreamFrame frame = (Http2StreamFrame)msg;
                ChannelPromise promise = Http2MultiplexCodec.this.ctx.newPromise();
                if (Http2CodecUtil.isStreamIdValid(frame.streamId())) {
                    ReferenceCountUtil.release(frame);
                    throw new IllegalArgumentException("Stream id must not be set on the frame. Was: " + frame.streamId());
                }
                if (!Http2CodecUtil.isStreamIdValid(this.streamId())) {
                    if (!(frame instanceof Http2HeadersFrame)) {
                        ReferenceCountUtil.release(frame);
                        throw new IllegalArgumentException("The first frame must be a headers frame. Was: " + frame.name());
                    }
                    frame = new ChannelCarryingHeadersFrame((Http2HeadersFrame)frame, this);
                    promise.addListener(this);
                } else {
                    frame.streamId(this.streamId());
                }
                Http2MultiplexCodec.this.writeFromStreamChannel(frame, promise, false);
            } else if (msg instanceof Http2GoAwayFrame) {
                ChannelPromise promise = Http2MultiplexCodec.this.ctx.newPromise();
                promise.addListener(this);
                Http2MultiplexCodec.this.writeFromStreamChannel(msg, promise, false);
            } else {
                ReferenceCountUtil.release(msg);
                throw new IllegalArgumentException("Message must be an Http2GoAwayFrame or Http2StreamFrame: " + msg);
            }
        }

        @Override
        protected void doWriteComplete() {
            Http2MultiplexCodec.this.flushFromStreamChannel();
        }

        @Override
        protected EventExecutor preferredEventExecutor() {
            return Http2MultiplexCodec.this.ctx.executor();
        }

        @Override
        protected void bytesConsumed(int bytes) {
            Http2MultiplexCodec.this.ctx.write(new DefaultHttp2WindowUpdateFrame(bytes).streamId(this.streamId()));
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            Throwable cause = future.cause();
            if (cause != null) {
                this.pipeline().fireExceptionCaught(cause);
                this.close();
            }
        }
    }
}

