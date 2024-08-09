/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.HpackEncoder;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.util.internal.ObjectUtil;

public class DefaultHttp2HeadersEncoder
implements Http2HeadersEncoder,
Http2HeadersEncoder.Configuration {
    private final HpackEncoder hpackEncoder;
    private final Http2HeadersEncoder.SensitivityDetector sensitivityDetector;
    private final ByteBuf tableSizeChangeOutput = Unpooled.buffer();

    public DefaultHttp2HeadersEncoder() {
        this(NEVER_SENSITIVE);
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this(sensitivityDetector, new HpackEncoder());
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean bl) {
        this(sensitivityDetector, new HpackEncoder(bl));
    }

    public DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean bl, int n) {
        this(sensitivityDetector, new HpackEncoder(bl, n));
    }

    DefaultHttp2HeadersEncoder(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, HpackEncoder hpackEncoder) {
        this.sensitivityDetector = ObjectUtil.checkNotNull(sensitivityDetector, "sensitiveDetector");
        this.hpackEncoder = ObjectUtil.checkNotNull(hpackEncoder, "hpackEncoder");
    }

    @Override
    public void encodeHeaders(int n, Http2Headers http2Headers, ByteBuf byteBuf) throws Http2Exception {
        try {
            if (this.tableSizeChangeOutput.isReadable()) {
                byteBuf.writeBytes(this.tableSizeChangeOutput);
                this.tableSizeChangeOutput.clear();
            }
            this.hpackEncoder.encodeHeaders(n, byteBuf, http2Headers, this.sensitivityDetector);
        } catch (Http2Exception http2Exception) {
            throw http2Exception;
        } catch (Throwable throwable) {
            throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, throwable, "Failed encoding headers block: %s", throwable.getMessage());
        }
    }

    @Override
    public void maxHeaderTableSize(long l) throws Http2Exception {
        this.hpackEncoder.setMaxHeaderTableSize(this.tableSizeChangeOutput, l);
    }

    @Override
    public long maxHeaderTableSize() {
        return this.hpackEncoder.getMaxHeaderTableSize();
    }

    @Override
    public void maxHeaderListSize(long l) throws Http2Exception {
        this.hpackEncoder.setMaxHeaderListSize(l);
    }

    @Override
    public long maxHeaderListSize() {
        return this.hpackEncoder.getMaxHeaderListSize();
    }

    @Override
    public Http2HeadersEncoder.Configuration configuration() {
        return this;
    }
}

