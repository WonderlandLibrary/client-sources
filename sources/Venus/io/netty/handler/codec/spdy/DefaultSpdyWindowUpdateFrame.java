/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyWindowUpdateFrame;
import io.netty.util.internal.StringUtil;

public class DefaultSpdyWindowUpdateFrame
implements SpdyWindowUpdateFrame {
    private int streamId;
    private int deltaWindowSize;

    public DefaultSpdyWindowUpdateFrame(int n, int n2) {
        this.setStreamId(n);
        this.setDeltaWindowSize(n2);
    }

    @Override
    public int streamId() {
        return this.streamId;
    }

    @Override
    public SpdyWindowUpdateFrame setStreamId(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Stream-ID cannot be negative: " + n);
        }
        this.streamId = n;
        return this;
    }

    @Override
    public int deltaWindowSize() {
        return this.deltaWindowSize;
    }

    @Override
    public SpdyWindowUpdateFrame setDeltaWindowSize(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Delta-Window-Size must be positive: " + n);
        }
        this.deltaWindowSize = n;
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Stream-ID = " + this.streamId() + StringUtil.NEWLINE + "--> Delta-Window-Size = " + this.deltaWindowSize();
    }
}

