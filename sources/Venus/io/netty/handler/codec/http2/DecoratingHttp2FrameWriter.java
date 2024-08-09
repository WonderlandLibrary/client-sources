/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.ObjectUtil;

public class DecoratingHttp2FrameWriter
implements Http2FrameWriter {
    private final Http2FrameWriter delegate;

    public DecoratingHttp2FrameWriter(Http2FrameWriter http2FrameWriter) {
        this.delegate = ObjectUtil.checkNotNull(http2FrameWriter, "delegate");
    }

    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        return this.delegate.writeData(channelHandlerContext, n, byteBuf, n2, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        return this.delegate.writeHeaders(channelHandlerContext, n, http2Headers, n2, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        return this.delegate.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise);
    }

    @Override
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl, ChannelPromise channelPromise) {
        return this.delegate.writePriority(channelHandlerContext, n, n2, s, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        return this.delegate.writeRstStream(channelHandlerContext, n, l, channelPromise);
    }

    @Override
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        return this.delegate.writeSettings(channelHandlerContext, http2Settings, channelPromise);
    }

    @Override
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return this.delegate.writeSettingsAck(channelHandlerContext, channelPromise);
    }

    @Override
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean bl, long l, ChannelPromise channelPromise) {
        return this.delegate.writePing(channelHandlerContext, bl, l, channelPromise);
    }

    @Override
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3, ChannelPromise channelPromise) {
        return this.delegate.writePushPromise(channelHandlerContext, n, n2, http2Headers, n3, channelPromise);
    }

    @Override
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.delegate.writeGoAway(channelHandlerContext, n, l, byteBuf, channelPromise);
    }

    @Override
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int n, int n2, ChannelPromise channelPromise) {
        return this.delegate.writeWindowUpdate(channelHandlerContext, n, n2, channelPromise);
    }

    @Override
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.delegate.writeFrame(channelHandlerContext, by, n, http2Flags, byteBuf, channelPromise);
    }

    @Override
    public Http2FrameWriter.Configuration configuration() {
        return this.delegate.configuration();
    }

    @Override
    public void close() {
        this.delegate.close();
    }
}

