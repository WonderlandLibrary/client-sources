/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.logging.LogLevel;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class Http2FrameLogger
extends ChannelHandlerAdapter {
    private static final int BUFFER_LENGTH_THRESHOLD = 64;
    private final InternalLogger logger;
    private final InternalLogLevel level;

    public Http2FrameLogger(LogLevel logLevel) {
        this(logLevel.toInternalLevel(), InternalLoggerFactory.getInstance(Http2FrameLogger.class));
    }

    public Http2FrameLogger(LogLevel logLevel, String string) {
        this(logLevel.toInternalLevel(), InternalLoggerFactory.getInstance(string));
    }

    public Http2FrameLogger(LogLevel logLevel, Class<?> clazz) {
        this(logLevel.toInternalLevel(), InternalLoggerFactory.getInstance(clazz));
    }

    private Http2FrameLogger(InternalLogLevel internalLogLevel, InternalLogger internalLogger) {
        this.level = ObjectUtil.checkNotNull(internalLogLevel, "level");
        this.logger = ObjectUtil.checkNotNull(internalLogger, "logger");
    }

    public void logData(Direction direction, ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) {
        this.logger.log(this.level, "{} {} DATA: streamId={} padding={} endStream={} length={} bytes={}", channelHandlerContext.channel(), direction.name(), n, n2, bl, byteBuf.readableBytes(), this.toString(byteBuf));
    }

    public void logHeaders(Direction direction, ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) {
        this.logger.log(this.level, "{} {} HEADERS: streamId={} headers={} padding={} endStream={}", channelHandlerContext.channel(), direction.name(), n, http2Headers, n2, bl);
    }

    public void logHeaders(Direction direction, ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) {
        this.logger.log(this.level, "{} {} HEADERS: streamId={} headers={} streamDependency={} weight={} exclusive={} padding={} endStream={}", channelHandlerContext.channel(), direction.name(), n, http2Headers, n2, s, bl, n3, bl2);
    }

    public void logPriority(Direction direction, ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) {
        this.logger.log(this.level, "{} {} PRIORITY: streamId={} streamDependency={} weight={} exclusive={}", channelHandlerContext.channel(), direction.name(), n, n2, s, bl);
    }

    public void logRstStream(Direction direction, ChannelHandlerContext channelHandlerContext, int n, long l) {
        this.logger.log(this.level, "{} {} RST_STREAM: streamId={} errorCode={}", channelHandlerContext.channel(), direction.name(), n, l);
    }

    public void logSettingsAck(Direction direction, ChannelHandlerContext channelHandlerContext) {
        this.logger.log(this.level, "{} {} SETTINGS: ack=true", (Object)channelHandlerContext.channel(), (Object)direction.name());
    }

    public void logSettings(Direction direction, ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) {
        this.logger.log(this.level, "{} {} SETTINGS: ack=false settings={}", channelHandlerContext.channel(), direction.name(), http2Settings);
    }

    public void logPing(Direction direction, ChannelHandlerContext channelHandlerContext, long l) {
        this.logger.log(this.level, "{} {} PING: ack=false bytes={}", channelHandlerContext.channel(), direction.name(), l);
    }

    public void logPingAck(Direction direction, ChannelHandlerContext channelHandlerContext, long l) {
        this.logger.log(this.level, "{} {} PING: ack=true bytes={}", channelHandlerContext.channel(), direction.name(), l);
    }

    public void logPushPromise(Direction direction, ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) {
        this.logger.log(this.level, "{} {} PUSH_PROMISE: streamId={} promisedStreamId={} headers={} padding={}", channelHandlerContext.channel(), direction.name(), n, n2, http2Headers, n3);
    }

    public void logGoAway(Direction direction, ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) {
        this.logger.log(this.level, "{} {} GO_AWAY: lastStreamId={} errorCode={} length={} bytes={}", channelHandlerContext.channel(), direction.name(), n, l, byteBuf.readableBytes(), this.toString(byteBuf));
    }

    public void logWindowsUpdate(Direction direction, ChannelHandlerContext channelHandlerContext, int n, int n2) {
        this.logger.log(this.level, "{} {} WINDOW_UPDATE: streamId={} windowSizeIncrement={}", channelHandlerContext.channel(), direction.name(), n, n2);
    }

    public void logUnknownFrame(Direction direction, ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) {
        this.logger.log(this.level, "{} {} UNKNOWN: frameType={} streamId={} flags={} length={} bytes={}", channelHandlerContext.channel(), direction.name(), by & 0xFF, n, http2Flags.value(), byteBuf.readableBytes(), this.toString(byteBuf));
    }

    private String toString(ByteBuf byteBuf) {
        if (!this.logger.isEnabled(this.level)) {
            return "";
        }
        if (this.level == InternalLogLevel.TRACE || byteBuf.readableBytes() <= 64) {
            return ByteBufUtil.hexDump(byteBuf);
        }
        int n = Math.min(byteBuf.readableBytes(), 64);
        return ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), n) + "...";
    }

    public static enum Direction {
        INBOUND,
        OUTBOUND;

    }
}

