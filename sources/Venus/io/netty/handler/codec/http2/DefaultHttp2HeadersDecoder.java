/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.netty.handler.codec.http2.HpackDecoder;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersDecoder;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2HeadersDecoder
implements Http2HeadersDecoder,
Http2HeadersDecoder.Configuration {
    private static final float HEADERS_COUNT_WEIGHT_NEW = 0.2f;
    private static final float HEADERS_COUNT_WEIGHT_HISTORICAL = 0.8f;
    private final HpackDecoder hpackDecoder;
    private final boolean validateHeaders;
    private float headerArraySizeAccumulator = 8.0f;

    public DefaultHttp2HeadersDecoder() {
        this(true);
    }

    public DefaultHttp2HeadersDecoder(boolean bl) {
        this(bl, 8192L);
    }

    public DefaultHttp2HeadersDecoder(boolean bl, long l) {
        this(bl, l, 32);
    }

    public DefaultHttp2HeadersDecoder(boolean bl, long l, int n) {
        this(bl, new HpackDecoder(l, n));
    }

    DefaultHttp2HeadersDecoder(boolean bl, HpackDecoder hpackDecoder) {
        this.hpackDecoder = ObjectUtil.checkNotNull(hpackDecoder, "hpackDecoder");
        this.validateHeaders = bl;
    }

    @Override
    public void maxHeaderTableSize(long l) throws Http2Exception {
        this.hpackDecoder.setMaxHeaderTableSize(l);
    }

    @Override
    public long maxHeaderTableSize() {
        return this.hpackDecoder.getMaxHeaderTableSize();
    }

    @Override
    public void maxHeaderListSize(long l, long l2) throws Http2Exception {
        this.hpackDecoder.setMaxHeaderListSize(l, l2);
    }

    @Override
    public long maxHeaderListSize() {
        return this.hpackDecoder.getMaxHeaderListSize();
    }

    @Override
    public long maxHeaderListSizeGoAway() {
        return this.hpackDecoder.getMaxHeaderListSizeGoAway();
    }

    @Override
    public Http2HeadersDecoder.Configuration configuration() {
        return this;
    }

    @Override
    public Http2Headers decodeHeaders(int n, ByteBuf byteBuf) throws Http2Exception {
        try {
            Http2Headers http2Headers = this.newHeaders();
            this.hpackDecoder.decode(n, byteBuf, http2Headers, this.validateHeaders);
            this.headerArraySizeAccumulator = 0.2f * (float)http2Headers.size() + 0.8f * this.headerArraySizeAccumulator;
            return http2Headers;
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, throwable, throwable.getMessage(), new Object[0]);
        }
    }

    protected final int numberOfHeadersGuess() {
        return (int)this.headerArraySizeAccumulator;
    }

    protected final boolean validateHeaders() {
        return this.validateHeaders;
    }

    protected Http2Headers newHeaders() {
        return new DefaultHttp2Headers(this.validateHeaders, (int)this.headerArraySizeAccumulator);
    }
}

