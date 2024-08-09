/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2ResetFrame;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultHttp2ResetFrame
extends AbstractHttp2StreamFrame
implements Http2ResetFrame {
    private final long errorCode;

    public DefaultHttp2ResetFrame(Http2Error http2Error) {
        this.errorCode = ObjectUtil.checkNotNull(http2Error, "error").code();
    }

    public DefaultHttp2ResetFrame(long l) {
        this.errorCode = l;
    }

    @Override
    public DefaultHttp2ResetFrame stream(Http2FrameStream http2FrameStream) {
        super.stream(http2FrameStream);
        return this;
    }

    @Override
    public String name() {
        return "RST_STREAM";
    }

    @Override
    public long errorCode() {
        return this.errorCode;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(stream=" + this.stream() + ", errorCode=" + this.errorCode + ')';
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttp2ResetFrame)) {
            return true;
        }
        DefaultHttp2ResetFrame defaultHttp2ResetFrame = (DefaultHttp2ResetFrame)object;
        return super.equals(object) && this.errorCode == defaultHttp2ResetFrame.errorCode;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + (int)(this.errorCode ^ this.errorCode >>> 32);
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

