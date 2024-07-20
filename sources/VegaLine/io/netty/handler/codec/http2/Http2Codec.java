/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.DefaultHttp2FrameWriter;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2MultiplexCodec;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2StreamChannelBootstrap;
import io.netty.handler.logging.LogLevel;

public final class Http2Codec
extends ChannelDuplexHandler {
    private static final Http2FrameLogger HTTP2_FRAME_LOGGER = new Http2FrameLogger(LogLevel.INFO, Http2Codec.class);
    private final Http2FrameCodec frameCodec;
    private final Http2MultiplexCodec multiplexCodec;

    public Http2Codec(boolean server, ChannelHandler streamHandler) {
        this(server, new Http2StreamChannelBootstrap().handler(streamHandler), HTTP2_FRAME_LOGGER);
    }

    public Http2Codec(boolean server, ChannelHandler streamHandler, Http2Settings initialSettings) {
        this(server, new Http2StreamChannelBootstrap().handler(streamHandler), HTTP2_FRAME_LOGGER, initialSettings);
    }

    public Http2Codec(boolean server, Http2StreamChannelBootstrap bootstrap, Http2FrameLogger frameLogger) {
        this(server, bootstrap, new DefaultHttp2FrameWriter(), frameLogger, new Http2Settings());
    }

    public Http2Codec(boolean server, Http2StreamChannelBootstrap bootstrap, Http2FrameLogger frameLogger, Http2Settings initialSettings) {
        this(server, bootstrap, new DefaultHttp2FrameWriter(), frameLogger, initialSettings);
    }

    Http2Codec(boolean server, Http2StreamChannelBootstrap bootstrap, Http2FrameWriter frameWriter, Http2FrameLogger frameLogger, Http2Settings initialSettings) {
        this.frameCodec = new Http2FrameCodec(server, frameWriter, frameLogger, initialSettings);
        this.multiplexCodec = new Http2MultiplexCodec(server, bootstrap);
    }

    Http2FrameCodec frameCodec() {
        return this.frameCodec;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.frameCodec);
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.multiplexCodec);
        ctx.pipeline().remove(this);
    }
}

