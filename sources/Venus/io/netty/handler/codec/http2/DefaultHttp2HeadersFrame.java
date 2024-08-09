/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultHttp2HeadersFrame
extends AbstractHttp2StreamFrame
implements Http2HeadersFrame {
    private final Http2Headers headers;
    private final boolean endStream;
    private final int padding;

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers) {
        this(http2Headers, false);
    }

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers, boolean bl) {
        this(http2Headers, bl, 0);
    }

    public DefaultHttp2HeadersFrame(Http2Headers http2Headers, boolean bl, int n) {
        this.headers = ObjectUtil.checkNotNull(http2Headers, "headers");
        this.endStream = bl;
        Http2CodecUtil.verifyPadding(n);
        this.padding = n;
    }

    @Override
    public DefaultHttp2HeadersFrame stream(Http2FrameStream http2FrameStream) {
        super.stream(http2FrameStream);
        return this;
    }

    @Override
    public String name() {
        return "HEADERS";
    }

    @Override
    public Http2Headers headers() {
        return this.headers;
    }

    @Override
    public boolean isEndStream() {
        return this.endStream;
    }

    @Override
    public int padding() {
        return this.padding;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(stream=" + this.stream() + ", headers=" + this.headers + ", endStream=" + this.endStream + ", padding=" + this.padding + ')';
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttp2HeadersFrame)) {
            return true;
        }
        DefaultHttp2HeadersFrame defaultHttp2HeadersFrame = (DefaultHttp2HeadersFrame)object;
        return super.equals(defaultHttp2HeadersFrame) && this.headers.equals(defaultHttp2HeadersFrame.headers) && this.endStream == defaultHttp2HeadersFrame.endStream && this.padding == defaultHttp2HeadersFrame.padding;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + this.headers.hashCode();
        n = n * 31 + (this.endStream ? 0 : 1);
        n = n * 31 + this.padding;
        return n;
    }

    @Override
    public AbstractHttp2StreamFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }

    @Override
    public Http2StreamFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }
}

