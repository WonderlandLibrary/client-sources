/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2ResetFrame;
import io.netty.util.internal.ObjectUtil;

public final class DefaultHttp2ResetFrame
extends AbstractHttp2StreamFrame
implements Http2ResetFrame {
    private final long errorCode;

    public DefaultHttp2ResetFrame(Http2Error error) {
        this.errorCode = ObjectUtil.checkNotNull(error, "error").code();
    }

    public DefaultHttp2ResetFrame(long errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public DefaultHttp2ResetFrame streamId(int streamId) {
        super.streamId(streamId);
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
        return "DefaultHttp2ResetFrame(stream=" + this.streamId() + "errorCode=" + this.errorCode + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttp2ResetFrame)) {
            return false;
        }
        DefaultHttp2ResetFrame other = (DefaultHttp2ResetFrame)o;
        return super.equals(o) && this.errorCode == other.errorCode;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 31 + (int)(this.errorCode ^ this.errorCode >>> 32);
        return hash;
    }
}

