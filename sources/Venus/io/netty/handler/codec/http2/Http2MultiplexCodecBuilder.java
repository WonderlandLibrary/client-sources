/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2MultiplexCodec;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Http2MultiplexCodecBuilder
extends AbstractHttp2ConnectionHandlerBuilder<Http2MultiplexCodec, Http2MultiplexCodecBuilder> {
    final ChannelHandler childHandler;

    Http2MultiplexCodecBuilder(boolean bl, ChannelHandler channelHandler) {
        this.server(bl);
        this.childHandler = Http2MultiplexCodecBuilder.checkSharable(ObjectUtil.checkNotNull(channelHandler, "childHandler"));
    }

    private static ChannelHandler checkSharable(ChannelHandler channelHandler) {
        if (channelHandler instanceof ChannelHandlerAdapter && !((ChannelHandlerAdapter)channelHandler).isSharable() && !channelHandler.getClass().isAnnotationPresent(ChannelHandler.Sharable.class)) {
            throw new IllegalArgumentException("The handler must be Sharable");
        }
        return channelHandler;
    }

    public static Http2MultiplexCodecBuilder forClient(ChannelHandler channelHandler) {
        return new Http2MultiplexCodecBuilder(false, channelHandler);
    }

    public static Http2MultiplexCodecBuilder forServer(ChannelHandler channelHandler) {
        return new Http2MultiplexCodecBuilder(true, channelHandler);
    }

    @Override
    public Http2Settings initialSettings() {
        return super.initialSettings();
    }

    @Override
    public Http2MultiplexCodecBuilder initialSettings(Http2Settings http2Settings) {
        return (Http2MultiplexCodecBuilder)super.initialSettings(http2Settings);
    }

    @Override
    public long gracefulShutdownTimeoutMillis() {
        return super.gracefulShutdownTimeoutMillis();
    }

    @Override
    public Http2MultiplexCodecBuilder gracefulShutdownTimeoutMillis(long l) {
        return (Http2MultiplexCodecBuilder)super.gracefulShutdownTimeoutMillis(l);
    }

    @Override
    public boolean isServer() {
        return super.isServer();
    }

    @Override
    public int maxReservedStreams() {
        return super.maxReservedStreams();
    }

    @Override
    public Http2MultiplexCodecBuilder maxReservedStreams(int n) {
        return (Http2MultiplexCodecBuilder)super.maxReservedStreams(n);
    }

    @Override
    public boolean isValidateHeaders() {
        return super.isValidateHeaders();
    }

    @Override
    public Http2MultiplexCodecBuilder validateHeaders(boolean bl) {
        return (Http2MultiplexCodecBuilder)super.validateHeaders(bl);
    }

    @Override
    public Http2FrameLogger frameLogger() {
        return super.frameLogger();
    }

    @Override
    public Http2MultiplexCodecBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return (Http2MultiplexCodecBuilder)super.frameLogger(http2FrameLogger);
    }

    @Override
    public boolean encoderEnforceMaxConcurrentStreams() {
        return super.encoderEnforceMaxConcurrentStreams();
    }

    @Override
    public Http2MultiplexCodecBuilder encoderEnforceMaxConcurrentStreams(boolean bl) {
        return (Http2MultiplexCodecBuilder)super.encoderEnforceMaxConcurrentStreams(bl);
    }

    @Override
    public Http2HeadersEncoder.SensitivityDetector headerSensitivityDetector() {
        return super.headerSensitivityDetector();
    }

    @Override
    public Http2MultiplexCodecBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return (Http2MultiplexCodecBuilder)super.headerSensitivityDetector(sensitivityDetector);
    }

    @Override
    public Http2MultiplexCodecBuilder encoderIgnoreMaxHeaderListSize(boolean bl) {
        return (Http2MultiplexCodecBuilder)super.encoderIgnoreMaxHeaderListSize(bl);
    }

    @Override
    public Http2MultiplexCodecBuilder initialHuffmanDecodeCapacity(int n) {
        return (Http2MultiplexCodecBuilder)super.initialHuffmanDecodeCapacity(n);
    }

    @Override
    public Http2MultiplexCodec build() {
        return (Http2MultiplexCodec)super.build();
    }

    @Override
    protected Http2MultiplexCodec build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        return new Http2MultiplexCodec(http2ConnectionEncoder, http2ConnectionDecoder, http2Settings, this.childHandler);
    }

    @Override
    protected Http2ConnectionHandler build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) throws Exception {
        return this.build(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
    }

    @Override
    public Http2ConnectionHandler build() {
        return this.build();
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder initialHuffmanDecodeCapacity(int n) {
        return this.initialHuffmanDecodeCapacity(n);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder encoderIgnoreMaxHeaderListSize(boolean bl) {
        return this.encoderIgnoreMaxHeaderListSize(bl);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return this.headerSensitivityDetector(sensitivityDetector);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder encoderEnforceMaxConcurrentStreams(boolean bl) {
        return this.encoderEnforceMaxConcurrentStreams(bl);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return this.frameLogger(http2FrameLogger);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder validateHeaders(boolean bl) {
        return this.validateHeaders(bl);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder maxReservedStreams(int n) {
        return this.maxReservedStreams(n);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long l) {
        return this.gracefulShutdownTimeoutMillis(l);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder initialSettings(Http2Settings http2Settings) {
        return this.initialSettings(http2Settings);
    }
}

