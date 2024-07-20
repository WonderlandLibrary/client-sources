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

    public Http2FrameLogger(LogLevel level) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance(Http2FrameLogger.class));
    }

    public Http2FrameLogger(LogLevel level, String name) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance(name));
    }

    public Http2FrameLogger(LogLevel level, Class<?> clazz) {
        this(level.toInternalLevel(), InternalLoggerFactory.getInstance(clazz));
    }

    private Http2FrameLogger(InternalLogLevel level, InternalLogger logger) {
        this.level = ObjectUtil.checkNotNull(level, "level");
        this.logger = ObjectUtil.checkNotNull(logger, "logger");
    }

    public void logData(Direction direction, ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endStream) {
        if (this.enabled()) {
            this.log(direction, "%s DATA: streamId=%d, padding=%d, endStream=%b, length=%d, bytes=%s", ctx.channel(), streamId, padding, endStream, data.readableBytes(), this.toString(data));
        }
    }

    public void logHeaders(Direction direction, ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endStream) {
        if (this.enabled()) {
            this.log(direction, "%s HEADERS: streamId=%d, headers=%s, padding=%d, endStream=%b", ctx.channel(), streamId, headers, padding, endStream);
        }
    }

    public void logHeaders(Direction direction, ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) {
        if (this.enabled()) {
            this.log(direction, "%s HEADERS: streamId=%d, headers=%s, streamDependency=%d, weight=%d, exclusive=%b, padding=%d, endStream=%b", ctx.channel(), streamId, headers, streamDependency, weight, exclusive, padding, endStream);
        }
    }

    public void logPriority(Direction direction, ChannelHandlerContext ctx, int streamId, int streamDependency, short weight, boolean exclusive) {
        if (this.enabled()) {
            this.log(direction, "%s PRIORITY: streamId=%d, streamDependency=%d, weight=%d, exclusive=%b", ctx.channel(), streamId, streamDependency, weight, exclusive);
        }
    }

    public void logRstStream(Direction direction, ChannelHandlerContext ctx, int streamId, long errorCode) {
        if (this.enabled()) {
            this.log(direction, "%s RST_STREAM: streamId=%d, errorCode=%d", ctx.channel(), streamId, errorCode);
        }
    }

    public void logSettingsAck(Direction direction, ChannelHandlerContext ctx) {
        if (this.enabled()) {
            this.log(direction, "%s SETTINGS: ack=true", ctx.channel());
        }
    }

    public void logSettings(Direction direction, ChannelHandlerContext ctx, Http2Settings settings) {
        if (this.enabled()) {
            this.log(direction, "%s SETTINGS: ack=false, settings=%s", ctx.channel(), settings);
        }
    }

    public void logPing(Direction direction, ChannelHandlerContext ctx, ByteBuf data) {
        if (this.enabled()) {
            this.log(direction, "%s PING: ack=false, length=%d, bytes=%s", ctx.channel(), data.readableBytes(), this.toString(data));
        }
    }

    public void logPingAck(Direction direction, ChannelHandlerContext ctx, ByteBuf data) {
        if (this.enabled()) {
            this.log(direction, "%s PING: ack=true, length=%d, bytes=%s", ctx.channel(), data.readableBytes(), this.toString(data));
        }
    }

    public void logPushPromise(Direction direction, ChannelHandlerContext ctx, int streamId, int promisedStreamId, Http2Headers headers, int padding) {
        if (this.enabled()) {
            this.log(direction, "%s PUSH_PROMISE: streamId=%d, promisedStreamId=%d, headers=%s, padding=%d", ctx.channel(), streamId, promisedStreamId, headers, padding);
        }
    }

    public void logGoAway(Direction direction, ChannelHandlerContext ctx, int lastStreamId, long errorCode, ByteBuf debugData) {
        if (this.enabled()) {
            this.log(direction, "%s GO_AWAY: lastStreamId=%d, errorCode=%d, length=%d, bytes=%s", ctx.channel(), lastStreamId, errorCode, debugData.readableBytes(), this.toString(debugData));
        }
    }

    public void logWindowsUpdate(Direction direction, ChannelHandlerContext ctx, int streamId, int windowSizeIncrement) {
        if (this.enabled()) {
            this.log(direction, "%s WINDOW_UPDATE: streamId=%d, windowSizeIncrement=%d", ctx.channel(), streamId, windowSizeIncrement);
        }
    }

    public void logUnknownFrame(Direction direction, ChannelHandlerContext ctx, byte frameType, int streamId, Http2Flags flags, ByteBuf data) {
        if (this.enabled()) {
            this.log(direction, "%s UNKNOWN: frameType=%d, streamId=%d, flags=%d, length=%d, bytes=%s", ctx.channel(), frameType & 0xFF, streamId, flags.value(), data.readableBytes(), this.toString(data));
        }
    }

    private boolean enabled() {
        return this.logger.isEnabled(this.level);
    }

    private String toString(ByteBuf buf) {
        if (this.level == InternalLogLevel.TRACE || buf.readableBytes() <= 64) {
            return ByteBufUtil.hexDump(buf);
        }
        int length = Math.min(buf.readableBytes(), 64);
        return ByteBufUtil.hexDump(buf, buf.readerIndex(), length) + "...";
    }

    private void log(Direction direction, String format, Object ... args) {
        StringBuilder b = new StringBuilder(200);
        b.append("\n----------------").append(direction.name()).append("--------------------\n").append(String.format(format, args)).append("\n------------------------------------");
        this.logger.log(this.level, b.toString());
    }

    public static enum Direction {
        INBOUND,
        OUTBOUND;

    }
}

