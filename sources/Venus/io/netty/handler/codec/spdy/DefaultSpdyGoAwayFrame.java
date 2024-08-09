/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.SpdyGoAwayFrame;
import io.netty.handler.codec.spdy.SpdySessionStatus;
import io.netty.util.internal.StringUtil;

public class DefaultSpdyGoAwayFrame
implements SpdyGoAwayFrame {
    private int lastGoodStreamId;
    private SpdySessionStatus status;

    public DefaultSpdyGoAwayFrame(int n) {
        this(n, 0);
    }

    public DefaultSpdyGoAwayFrame(int n, int n2) {
        this(n, SpdySessionStatus.valueOf(n2));
    }

    public DefaultSpdyGoAwayFrame(int n, SpdySessionStatus spdySessionStatus) {
        this.setLastGoodStreamId(n);
        this.setStatus(spdySessionStatus);
    }

    @Override
    public int lastGoodStreamId() {
        return this.lastGoodStreamId;
    }

    @Override
    public SpdyGoAwayFrame setLastGoodStreamId(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Last-good-stream-ID cannot be negative: " + n);
        }
        this.lastGoodStreamId = n;
        return this;
    }

    @Override
    public SpdySessionStatus status() {
        return this.status;
    }

    @Override
    public SpdyGoAwayFrame setStatus(SpdySessionStatus spdySessionStatus) {
        this.status = spdySessionStatus;
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Last-good-stream-ID = " + this.lastGoodStreamId() + StringUtil.NEWLINE + "--> Status: " + this.status();
    }
}

