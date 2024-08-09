/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyStreamFrame;

public abstract class DefaultSpdyStreamFrame
implements SpdyStreamFrame {
    private int streamId;
    private boolean last;

    protected DefaultSpdyStreamFrame(int n) {
        this.setStreamId(n);
    }

    @Override
    public int streamId() {
        return this.streamId;
    }

    @Override
    public SpdyStreamFrame setStreamId(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Stream-ID must be positive: " + n);
        }
        this.streamId = n;
        return this;
    }

    @Override
    public boolean isLast() {
        return this.last;
    }

    @Override
    public SpdyStreamFrame setLast(boolean bl) {
        this.last = bl;
        return this;
    }
}

