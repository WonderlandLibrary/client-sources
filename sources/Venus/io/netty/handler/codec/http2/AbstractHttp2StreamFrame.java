/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2StreamFrame;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractHttp2StreamFrame
implements Http2StreamFrame {
    private Http2FrameStream stream;

    @Override
    public AbstractHttp2StreamFrame stream(Http2FrameStream http2FrameStream) {
        this.stream = http2FrameStream;
        return this;
    }

    @Override
    public Http2FrameStream stream() {
        return this.stream;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Http2StreamFrame)) {
            return true;
        }
        Http2StreamFrame http2StreamFrame = (Http2StreamFrame)object;
        return this.stream == http2StreamFrame.stream() || this.stream != null && this.stream.equals(http2StreamFrame.stream());
    }

    public int hashCode() {
        Http2FrameStream http2FrameStream = this.stream;
        if (http2FrameStream == null) {
            return super.hashCode();
        }
        return http2FrameStream.hashCode();
    }

    @Override
    public Http2StreamFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }
}

