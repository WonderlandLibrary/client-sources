/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.netty.handler.codec.http2.DefaultHttp2FrameWriter;
import io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.StreamBufferingEncoder;
import io.netty.util.internal.ObjectUtil;

public abstract class AbstractHttp2ConnectionHandlerBuilder<T extends Http2ConnectionHandler, B extends AbstractHttp2ConnectionHandlerBuilder<T, B>> {
    private static final Http2HeadersEncoder.SensitivityDetector DEFAULT_HEADER_SENSITIVITY_DETECTOR;
    private Http2Settings initialSettings = Http2Settings.defaultSettings();
    private Http2FrameListener frameListener;
    private long gracefulShutdownTimeoutMillis = Http2CodecUtil.DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT_MILLIS;
    private Boolean isServer;
    private Integer maxReservedStreams;
    private Http2Connection connection;
    private Http2ConnectionDecoder decoder;
    private Http2ConnectionEncoder encoder;
    private Boolean validateHeaders;
    private Http2FrameLogger frameLogger;
    private Http2HeadersEncoder.SensitivityDetector headerSensitivityDetector;
    private Boolean encoderEnforceMaxConcurrentStreams;
    private Boolean encoderIgnoreMaxHeaderListSize;
    private int initialHuffmanDecodeCapacity = 32;
    static final boolean $assertionsDisabled;

    protected Http2Settings initialSettings() {
        return this.initialSettings;
    }

    protected B initialSettings(Http2Settings http2Settings) {
        this.initialSettings = ObjectUtil.checkNotNull(http2Settings, "settings");
        return this.self();
    }

    protected Http2FrameListener frameListener() {
        return this.frameListener;
    }

    protected B frameListener(Http2FrameListener http2FrameListener) {
        this.frameListener = ObjectUtil.checkNotNull(http2FrameListener, "frameListener");
        return this.self();
    }

    protected long gracefulShutdownTimeoutMillis() {
        return this.gracefulShutdownTimeoutMillis;
    }

    protected B gracefulShutdownTimeoutMillis(long l) {
        if (l < -1L) {
            throw new IllegalArgumentException("gracefulShutdownTimeoutMillis: " + l + " (expected: -1 for indefinite or >= 0)");
        }
        this.gracefulShutdownTimeoutMillis = l;
        return this.self();
    }

    protected boolean isServer() {
        return this.isServer != null ? this.isServer : true;
    }

    protected B server(boolean bl) {
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "connection", this.connection);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "codec", this.decoder);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "codec", this.encoder);
        this.isServer = bl;
        return this.self();
    }

    protected int maxReservedStreams() {
        return this.maxReservedStreams != null ? this.maxReservedStreams : 100;
    }

    protected B maxReservedStreams(int n) {
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "connection", this.connection);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "codec", this.decoder);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("server", "codec", this.encoder);
        this.maxReservedStreams = ObjectUtil.checkPositiveOrZero(n, "maxReservedStreams");
        return this.self();
    }

    protected Http2Connection connection() {
        return this.connection;
    }

    protected B connection(Http2Connection http2Connection) {
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("connection", "maxReservedStreams", this.maxReservedStreams);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("connection", "server", this.isServer);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("connection", "codec", this.decoder);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("connection", "codec", this.encoder);
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
        return this.self();
    }

    protected Http2ConnectionDecoder decoder() {
        return this.decoder;
    }

    protected Http2ConnectionEncoder encoder() {
        return this.encoder;
    }

    protected B codec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "server", this.isServer);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "maxReservedStreams", this.maxReservedStreams);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "connection", this.connection);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "frameLogger", this.frameLogger);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "validateHeaders", this.validateHeaders);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "headerSensitivityDetector", this.headerSensitivityDetector);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint("codec", "encoderEnforceMaxConcurrentStreams", this.encoderEnforceMaxConcurrentStreams);
        ObjectUtil.checkNotNull(http2ConnectionDecoder, "decoder");
        ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        if (http2ConnectionDecoder.connection() != http2ConnectionEncoder.connection()) {
            throw new IllegalArgumentException("The specified encoder and decoder have different connections.");
        }
        this.decoder = http2ConnectionDecoder;
        this.encoder = http2ConnectionEncoder;
        return this.self();
    }

    protected boolean isValidateHeaders() {
        return this.validateHeaders != null ? this.validateHeaders : true;
    }

    protected B validateHeaders(boolean bl) {
        this.enforceNonCodecConstraints("validateHeaders");
        this.validateHeaders = bl;
        return this.self();
    }

    protected Http2FrameLogger frameLogger() {
        return this.frameLogger;
    }

    protected B frameLogger(Http2FrameLogger http2FrameLogger) {
        this.enforceNonCodecConstraints("frameLogger");
        this.frameLogger = ObjectUtil.checkNotNull(http2FrameLogger, "frameLogger");
        return this.self();
    }

    protected boolean encoderEnforceMaxConcurrentStreams() {
        return this.encoderEnforceMaxConcurrentStreams != null ? this.encoderEnforceMaxConcurrentStreams : false;
    }

    protected B encoderEnforceMaxConcurrentStreams(boolean bl) {
        this.enforceNonCodecConstraints("encoderEnforceMaxConcurrentStreams");
        this.encoderEnforceMaxConcurrentStreams = bl;
        return this.self();
    }

    protected Http2HeadersEncoder.SensitivityDetector headerSensitivityDetector() {
        return this.headerSensitivityDetector != null ? this.headerSensitivityDetector : DEFAULT_HEADER_SENSITIVITY_DETECTOR;
    }

    protected B headerSensitivityDetector(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this.enforceNonCodecConstraints("headerSensitivityDetector");
        this.headerSensitivityDetector = ObjectUtil.checkNotNull(sensitivityDetector, "headerSensitivityDetector");
        return this.self();
    }

    protected B encoderIgnoreMaxHeaderListSize(boolean bl) {
        this.enforceNonCodecConstraints("encoderIgnoreMaxHeaderListSize");
        this.encoderIgnoreMaxHeaderListSize = bl;
        return this.self();
    }

    protected B initialHuffmanDecodeCapacity(int n) {
        this.enforceNonCodecConstraints("initialHuffmanDecodeCapacity");
        this.initialHuffmanDecodeCapacity = ObjectUtil.checkPositive(n, "initialHuffmanDecodeCapacity");
        return this.self();
    }

    protected T build() {
        if (this.encoder != null) {
            if (!$assertionsDisabled && this.decoder == null) {
                throw new AssertionError();
            }
            return this.buildFromCodec(this.decoder, this.encoder);
        }
        Http2Connection http2Connection = this.connection;
        if (http2Connection == null) {
            http2Connection = new DefaultHttp2Connection(this.isServer(), this.maxReservedStreams());
        }
        return this.buildFromConnection(http2Connection);
    }

    private T buildFromConnection(Http2Connection http2Connection) {
        Http2FrameWriter http2FrameWriter;
        Long l = this.initialSettings.maxHeaderListSize();
        Http2FrameReader http2FrameReader = new DefaultHttp2FrameReader(new DefaultHttp2HeadersDecoder(this.isValidateHeaders(), l == null ? 8192L : l, this.initialHuffmanDecodeCapacity));
        DefaultHttp2FrameWriter defaultHttp2FrameWriter = http2FrameWriter = this.encoderIgnoreMaxHeaderListSize == null ? new DefaultHttp2FrameWriter(this.headerSensitivityDetector()) : new DefaultHttp2FrameWriter(this.headerSensitivityDetector(), this.encoderIgnoreMaxHeaderListSize);
        if (this.frameLogger != null) {
            http2FrameReader = new Http2InboundFrameLogger(http2FrameReader, this.frameLogger);
            http2FrameWriter = new Http2OutboundFrameLogger(http2FrameWriter, this.frameLogger);
        }
        Http2ConnectionEncoder http2ConnectionEncoder = new DefaultHttp2ConnectionEncoder(http2Connection, http2FrameWriter);
        boolean bl = this.encoderEnforceMaxConcurrentStreams();
        if (bl) {
            if (http2Connection.isServer()) {
                http2ConnectionEncoder.close();
                http2FrameReader.close();
                throw new IllegalArgumentException("encoderEnforceMaxConcurrentStreams: " + bl + " not supported for server");
            }
            http2ConnectionEncoder = new StreamBufferingEncoder(http2ConnectionEncoder);
        }
        DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder = new DefaultHttp2ConnectionDecoder(http2Connection, http2ConnectionEncoder, http2FrameReader);
        return this.buildFromCodec(defaultHttp2ConnectionDecoder, http2ConnectionEncoder);
    }

    private T buildFromCodec(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder) {
        T t;
        try {
            t = this.build(http2ConnectionDecoder, http2ConnectionEncoder, this.initialSettings);
        } catch (Throwable throwable) {
            http2ConnectionEncoder.close();
            http2ConnectionDecoder.close();
            throw new IllegalStateException("failed to build a Http2ConnectionHandler", throwable);
        }
        ((Http2ConnectionHandler)t).gracefulShutdownTimeoutMillis(this.gracefulShutdownTimeoutMillis);
        if (((Http2ConnectionHandler)t).decoder().frameListener() == null) {
            ((Http2ConnectionHandler)t).decoder().frameListener(this.frameListener);
        }
        return t;
    }

    protected abstract T build(Http2ConnectionDecoder var1, Http2ConnectionEncoder var2, Http2Settings var3) throws Exception;

    protected final B self() {
        return (B)this;
    }

    private void enforceNonCodecConstraints(String string) {
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint(string, "server/connection", this.decoder);
        AbstractHttp2ConnectionHandlerBuilder.enforceConstraint(string, "server/connection", this.encoder);
    }

    private static void enforceConstraint(String string, String string2, Object object) {
        if (object != null) {
            throw new IllegalStateException(string + "() cannot be called because " + string2 + "() has been called already.");
        }
    }

    static {
        $assertionsDisabled = !AbstractHttp2ConnectionHandlerBuilder.class.desiredAssertionStatus();
        DEFAULT_HEADER_SENSITIVITY_DETECTOR = Http2HeadersEncoder.NEVER_SENSITIVE;
    }
}

