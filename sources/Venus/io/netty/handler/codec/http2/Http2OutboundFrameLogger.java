/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.ObjectUtil;

public class Http2OutboundFrameLogger
implements Http2FrameWriter {
    private final Http2FrameWriter writer;
    private final Http2FrameLogger logger;

    public Http2OutboundFrameLogger(Http2FrameWriter http2FrameWriter, Http2FrameLogger http2FrameLogger) {
        this.writer = ObjectUtil.checkNotNull(http2FrameWriter, "writer");
        this.logger = ObjectUtil.checkNotNull(http2FrameLogger, "logger");
    }

    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        this.logger.logData(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, byteBuf, n2, bl);
        return this.writer.writeData(channelHandlerContext, n, byteBuf, n2, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        this.logger.logHeaders(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, http2Headers, n2, bl);
        return this.writer.writeHeaders(channelHandlerContext, n, http2Headers, n2, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        this.logger.logHeaders(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
        return this.writer.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise);
    }

    @Override
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl, ChannelPromise channelPromise) {
        this.logger.logPriority(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, n2, s, bl);
        return this.writer.writePriority(channelHandlerContext, n, n2, s, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        this.logger.logRstStream(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, l);
        return this.writer.writeRstStream(channelHandlerContext, n, l, channelPromise);
    }

    @Override
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        this.logger.logSettings(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, http2Settings);
        return this.writer.writeSettings(channelHandlerContext, http2Settings, channelPromise);
    }

    @Override
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        this.logger.logSettingsAck(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext);
        return this.writer.writeSettingsAck(channelHandlerContext, channelPromise);
    }

    @Override
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean bl, long l, ChannelPromise channelPromise) {
        if (bl) {
            this.logger.logPingAck(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, l);
        } else {
            this.logger.logPing(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, l);
        }
        return this.writer.writePing(channelHandlerContext, bl, l, channelPromise);
    }

    @Override
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3, ChannelPromise channelPromise) {
        this.logger.logPushPromise(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, n2, http2Headers, n3);
        return this.writer.writePushPromise(channelHandlerContext, n, n2, http2Headers, n3, channelPromise);
    }

    @Override
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelPromise channelPromise) {
        this.logger.logGoAway(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, l, byteBuf);
        return this.writer.writeGoAway(channelHandlerContext, n, l, byteBuf, channelPromise);
    }

    @Override
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int n, int n2, ChannelPromise channelPromise) {
        this.logger.logWindowsUpdate(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, n, n2);
        return this.writer.writeWindowUpdate(channelHandlerContext, n, n2, channelPromise);
    }

    @Override
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        this.logger.logUnknownFrame(Http2FrameLogger.Direction.OUTBOUND, channelHandlerContext, by, n, http2Flags, byteBuf);
        return this.writer.writeFrame(channelHandlerContext, by, n, http2Flags, byteBuf, channelPromise);
    }

    @Override
    public void close() {
        this.writer.close();
    }

    @Override
    public Http2FrameWriter.Configuration configuration() {
        return this.writer.configuration();
    }
}

