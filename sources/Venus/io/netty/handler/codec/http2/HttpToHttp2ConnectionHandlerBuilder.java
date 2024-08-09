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
import io.netty.handler.codec.http2.HttpToHttp2ConnectionHandler;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class HttpToHttp2ConnectionHandlerBuilder
extends AbstractHttp2ConnectionHandlerBuilder<HttpToHttp2ConnectionHandler, HttpToHttp2ConnectionHandlerBuilder> {
    @Override
    public HttpToHttp2ConnectionHandlerBuilder validateHeaders(boolean bl) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.validateHeaders(bl);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder initialSettings(Http2Settings http2Settings) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.initialSettings(http2Settings);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder frameListener(Http2FrameListener http2FrameListener) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.frameListener(http2FrameListener);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder gracefulShutdownTimeoutMillis(long l) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.gracefulShutdownTimeoutMillis(l);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder server(boolean bl) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.server(bl);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder connection(Http2Connection http2Connection) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.connection(http2Connection);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.codec(http2ConnectionDecoder, http2ConnectionEncoder);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder frameLogger(Http2FrameLogger http2FrameLogger) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.frameLogger(http2FrameLogger);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder encoderEnforceMaxConcurrentStreams(boolean bl) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.encoderEnforceMaxConcurrentStreams(bl);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.headerSensitivityDetector(sensitivityDetector);
    }

    @Override
    public HttpToHttp2ConnectionHandlerBuilder initialHuffmanDecodeCapacity(int n) {
        return (HttpToHttp2ConnectionHandlerBuilder)super.initialHuffmanDecodeCapacity(n);
    }

    @Override
    public HttpToHttp2ConnectionHandler build() {
        return (HttpToHttp2ConnectionHandler)super.build();
    }

    @Override
    protected HttpToHttp2ConnectionHandler build(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        return new HttpToHttp2ConnectionHandler(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings, this.isValidateHeaders());
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

