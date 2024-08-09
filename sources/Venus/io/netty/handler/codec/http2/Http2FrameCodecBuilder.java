/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.StreamBufferingEncoder;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Http2FrameCodecBuilder
extends AbstractHttp2ConnectionHandlerBuilder<Http2FrameCodec, Http2FrameCodecBuilder> {
    private Http2FrameWriter frameWriter;

    Http2FrameCodecBuilder(boolean bl) {
        this.server(bl);
    }

    public static Http2FrameCodecBuilder forClient() {
        return new Http2FrameCodecBuilder(false);
    }

    public static Http2FrameCodecBuilder forServer() {
        return new Http2FrameCodecBuilder(true);
    }

    Http2FrameCodecBuilder frameWriter(Http2FrameWriter http2FrameWriter) {
        this.frameWriter = ObjectUtil.checkNotNull(http2FrameWriter, "frameWriter");
        return this;
    }

    @Override
    public Http2Settings initialSettings() {
        return super.initialSettings();
    }

    @Override
    public Http2FrameCodecBuilder initialSettings(Http2Settings http2Settings) {
        return (Http2FrameCodecBuilder)super.initialSettings(http2Settings);
    }

    @Override
    public long gracefulShutdownTimeoutMillis() {
        return super.gracefulShutdownTimeoutMillis();
    }

    @Override
    public Http2FrameCodecBuilder gracefulShutdownTimeoutMillis(long l) {
        return (Http2FrameCodecBuilder)super.gracefulShutdownTimeoutMillis(l);
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
    public Http2FrameCodecBuilder maxReservedStreams(int n) {
        return (Http2FrameCodecBuilder)super.maxReservedStreams(n);
    }

    @Override
    public boolean isValidateHeaders() {
        return super.isValidateHeaders();
    }

    @Override
    public Http2FrameCodecBuilder validateHeaders(boolean bl) {
        return (Http2FrameCodecBuilder)super.validateHeaders(bl);
    }

    @Override
    public Http2FrameLogger frameLogger() {
        return super.frameLogger();
    }

    @Override
    public Http2FrameCodecBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return (Http2FrameCodecBuilder)super.frameLogger(http2FrameLogger);
    }

    @Override
    public boolean encoderEnforceMaxConcurrentStreams() {
        return super.encoderEnforceMaxConcurrentStreams();
    }

    @Override
    public Http2FrameCodecBuilder encoderEnforceMaxConcurrentStreams(boolean bl) {
        return (Http2FrameCodecBuilder)super.encoderEnforceMaxConcurrentStreams(bl);
    }

    @Override
    public Http2HeadersEncoder.SensitivityDetector headerSensitivityDetector() {
        return super.headerSensitivityDetector();
    }

    @Override
    public Http2FrameCodecBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return (Http2FrameCodecBuilder)super.headerSensitivityDetector(sensitivityDetector);
    }

    @Override
    public Http2FrameCodecBuilder encoderIgnoreMaxHeaderListSize(boolean bl) {
        return (Http2FrameCodecBuilder)super.encoderIgnoreMaxHeaderListSize(bl);
    }

    @Override
    public Http2FrameCodecBuilder initialHuffmanDecodeCapacity(int n) {
        return (Http2FrameCodecBuilder)super.initialHuffmanDecodeCapacity(n);
    }

    @Override
    public Http2FrameCodec build() {
        Http2FrameWriter http2FrameWriter = this.frameWriter;
        if (http2FrameWriter != null) {
            DefaultHttp2Connection defaultHttp2Connection = new DefaultHttp2Connection(this.isServer(), this.maxReservedStreams());
            Long l = this.initialSettings().maxHeaderListSize();
            Http2FrameReader http2FrameReader = new DefaultHttp2FrameReader(l == null ? new DefaultHttp2HeadersDecoder(true) : new DefaultHttp2HeadersDecoder(true, l));
            if (this.frameLogger() != null) {
                http2FrameWriter = new Http2OutboundFrameLogger(http2FrameWriter, this.frameLogger());
                http2FrameReader = new Http2InboundFrameLogger(http2FrameReader, this.frameLogger());
            }
            Http2ConnectionEncoder http2ConnectionEncoder = new DefaultHttp2ConnectionEncoder(defaultHttp2Connection, http2FrameWriter);
            if (this.encoderEnforceMaxConcurrentStreams()) {
                http2ConnectionEncoder = new StreamBufferingEncoder(http2ConnectionEncoder);
            }
            DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder = new DefaultHttp2ConnectionDecoder(defaultHttp2Connection, http2ConnectionEncoder, http2FrameReader);
            return this.build(defaultHttp2ConnectionDecoder, http2ConnectionEncoder, this.initialSettings());
        }
        return (Http2FrameCodec)super.build();
    }

    @Override
    protected Http2FrameCodec build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        return new Http2FrameCodec(http2ConnectionEncoder, http2ConnectionDecoder, http2Settings);
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

