/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;

public class Http2FrameAdapter
implements Http2FrameListener {
    @Override
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
        return byteBuf.readableBytes() + n2;
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
    }

    @Override
    public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) throws Http2Exception {
    }

    @Override
    public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
    }

    @Override
    public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
    }

    @Override
    public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
    }

    @Override
    public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
    }

    @Override
    public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
    }

    @Override
    public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
    }

    @Override
    public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
    }

    @Override
    public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) throws Http2Exception {
    }

    @Override
    public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) {
    }
}

