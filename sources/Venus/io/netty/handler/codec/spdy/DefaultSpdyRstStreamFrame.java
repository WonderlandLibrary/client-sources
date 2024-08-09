/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.DefaultSpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdyRstStreamFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdyStreamStatus;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSpdyRstStreamFrame
extends DefaultSpdyStreamFrame
implements SpdyRstStreamFrame {
    private SpdyStreamStatus status;

    public DefaultSpdyRstStreamFrame(int n, int n2) {
        this(n, SpdyStreamStatus.valueOf(n2));
    }

    public DefaultSpdyRstStreamFrame(int n, SpdyStreamStatus spdyStreamStatus) {
        super(n);
        this.setStatus(spdyStreamStatus);
    }

    @Override
    public SpdyRstStreamFrame setStreamId(int n) {
        super.setStreamId(n);
        return this;
    }

    @Override
    public SpdyRstStreamFrame setLast(boolean bl) {
        super.setLast(bl);
        return this;
    }

    @Override
    public SpdyStreamStatus status() {
        return this.status;
    }

    @Override
    public SpdyRstStreamFrame setStatus(SpdyStreamStatus spdyStreamStatus) {
        this.status = spdyStreamStatus;
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + StringUtil.NEWLINE + "--> Stream-ID = " + this.streamId() + StringUtil.NEWLINE + "--> Status: " + this.status();
    }

    @Override
    public SpdyStreamFrame setLast(boolean bl) {
        return this.setLast(bl);
    }

    @Override
    public SpdyStreamFrame setStreamId(int n) {
        return this.setStreamId(n);
    }
}

