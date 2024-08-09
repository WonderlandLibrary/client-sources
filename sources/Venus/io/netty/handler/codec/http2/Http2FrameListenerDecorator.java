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
import io.netty.util.internal.ObjectUtil;

public class Http2FrameListenerDecorator
implements Http2FrameListener {
    protected final Http2FrameListener listener;

    public Http2FrameListenerDecorator(Http2FrameListener http2FrameListener) {
        this.listener = ObjectUtil.checkNotNull(http2FrameListener, "listener");
    }

    @Override
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
        return this.listener.onDataRead(channelHandlerContext, n, byteBuf, n2, bl);
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
        this.listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, bl);
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
        this.listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
    }

    @Override
    public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) throws Http2Exception {
        this.listener.onPriorityRead(channelHandlerContext, n, n2, s, bl);
    }

    @Override
    public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
        this.listener.onRstStreamRead(channelHandlerContext, n, l);
    }

    @Override
    public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
        this.listener.onSettingsAckRead(channelHandlerContext);
    }

    @Override
    public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
        this.listener.onSettingsRead(channelHandlerContext, http2Settings);
    }

    @Override
    public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
        this.listener.onPingRead(channelHandlerContext, l);
    }

    @Override
    public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
        this.listener.onPingAckRead(channelHandlerContext, l);
    }

    @Override
    public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
        this.listener.onPushPromiseRead(channelHandlerContext, n, n2, http2Headers, n3);
    }

    @Override
    public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onGoAwayRead(channelHandlerContext, n, l, byteBuf);
    }

    @Override
    public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) throws Http2Exception {
        this.listener.onWindowUpdateRead(channelHandlerContext, n, n2);
    }

    @Override
    public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onUnknownFrame(channelHandlerContext, by, n, http2Flags, byteBuf);
    }
}

