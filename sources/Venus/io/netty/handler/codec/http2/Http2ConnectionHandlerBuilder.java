/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2ConnectionHandlerBuilder;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2Settings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Http2ConnectionHandlerBuilder
extends AbstractHttp2ConnectionHandlerBuilder<Http2ConnectionHandler, Http2ConnectionHandlerBuilder> {
    @Override
    public Http2ConnectionHandlerBuilder validateHeaders(boolean bl) {
        return (Http2ConnectionHandlerBuilder)super.validateHeaders(bl);
    }

    @Override
    public Http2ConnectionHandlerBuilder initialSettings(Http2Settings http2Settings) {
        return (Http2ConnectionHandlerBuilder)super.initialSettings(http2Settings);
    }

    @Override
    public Http2ConnectionHandlerBuilder frameListener(Http2FrameListener http2FrameListener) {
        return (Http2ConnectionHandlerBuilder)super.frameListener(http2FrameListener);
    }

    @Override
    public Http2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long l) {
        return (Http2ConnectionHandlerBuilder)super.gracefulShutdownTimeoutMillis(l);
    }

    @Override
    public Http2ConnectionHandlerBuilder server(boolean bl) {
        return (Http2ConnectionHandlerBuilder)super.server(bl);
    }

    @Override
    public Http2ConnectionHandlerBuilder connection(Http2Connection http2Connection) {
        return (Http2ConnectionHandlerBuilder)super.connection(http2Connection);
    }

    @Override
    public Http2ConnectionHandlerBuilder maxReservedStreams(int n) {
        return (Http2ConnectionHandlerBuilder)super.maxReservedStreams(n);
    }

    @Override
    public Http2ConnectionHandlerBuilder codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        return (Http2ConnectionHandlerBuilder)super.codec(http2ConnectionDecoder, http2ConnectionEncoder);
    }

    @Override
    public Http2ConnectionHandlerBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return (Http2ConnectionHandlerBuilder)super.frameLogger(http2FrameLogger);
    }

    @Override
    public Http2ConnectionHandlerBuilder encoderEnforceMaxConcurrentStreams(boolean bl) {
        return (Http2ConnectionHandlerBuilder)super.encoderEnforceMaxConcurrentStreams(bl);
    }

    @Override
    public Http2ConnectionHandlerBuilder encoderIgnoreMaxHeaderListSize(boolean bl) {
        return (Http2ConnectionHandlerBuilder)super.encoderIgnoreMaxHeaderListSize(bl);
    }

    @Override
    public Http2ConnectionHandlerBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return (Http2ConnectionHandlerBuilder)super.headerSensitivityDetector(sensitivityDetector);
    }

    @Override
    public Http2ConnectionHandlerBuilder initialHuffmanDecodeCapacity(int n) {
        return (Http2ConnectionHandlerBuilder)super.initialHuffmanDecodeCapacity(n);
    }

    @Override
    public Http2ConnectionHandler build() {
        return super.build();
    }

    @Override
    protected Http2ConnectionHandler build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        return new Http2ConnectionHandler(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
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
    public AbstractHttp2ConnectionHandlerBuilder codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        return this.codec(http2ConnectionDecoder, http2ConnectionEncoder);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder connection(Http2Connection http2Connection) {
        return this.connection(http2Connection);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder maxReservedStreams(int n) {
        return this.maxReservedStreams(n);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder server(boolean bl) {
        return this.server(bl);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long l) {
        return this.gracefulShutdownTimeoutMillis(l);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder frameListener(Http2FrameListener http2FrameListener) {
        return this.frameListener(http2FrameListener);
    }

    @Override
    public AbstractHttp2ConnectionHandlerBuilder initialSettings(Http2Settings http2Settings) {
        return this.initialSettings(http2Settings);
    }
}

