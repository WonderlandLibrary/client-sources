/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2FrameStreamVisitor;
import io.netty.util.internal.StringUtil;

public abstract class Http2ChannelDuplexHandler
extends ChannelDuplexHandler {
    private volatile Http2FrameCodec frameCodec;

    @Override
    public final void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.frameCodec = Http2ChannelDuplexHandler.requireHttp2FrameCodec(channelHandlerContext);
        this.handlerAdded0(channelHandlerContext);
    }

    protected void handlerAdded0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    @Override
    public final void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.handlerRemoved0(channelHandlerContext);
        } finally {
            this.frameCodec = null;
        }
    }

    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public final Http2FrameStream newStream() {
        Http2FrameCodec http2FrameCodec = this.frameCodec;
        if (http2FrameCodec == null) {
            throw new IllegalStateException(StringUtil.simpleClassName(Http2FrameCodec.class) + " not found. Has the handler been added to a pipeline?");
        }
        return http2FrameCodec.newStream();
    }

    protected final void forEachActiveStream(Http2FrameStreamVisitor http2FrameStreamVisitor) throws Http2Exception {
        this.frameCodec.forEachActiveStream(http2FrameStreamVisitor);
    }

    private static Http2FrameCodec requireHttp2FrameCodec(ChannelHandlerContext channelHandlerContext) {
        ChannelHandlerContext channelHandlerContext2 = channelHandlerContext.pipeline().context(Http2FrameCodec.class);
        if (channelHandlerContext2 == null) {
            throw new IllegalArgumentException(Http2FrameCodec.class.getSimpleName() + " was not found in the channel pipeline.");
        }
        return (Http2FrameCodec)channelHandlerContext2.handler();
    }
}

