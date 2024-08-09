/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdySynStreamFrame;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSpdySynStreamFrame
extends DefaultSpdyHeadersFrame
implements SpdySynStreamFrame {
    private int associatedStreamId;
    private byte priority;
    private boolean unidirectional;

    public DefaultSpdySynStreamFrame(int n, int n2, byte by) {
        this(n, n2, by, true);
    }

    public DefaultSpdySynStreamFrame(int n, int n2, byte by, boolean bl) {
        super(n, bl);
        this.setAssociatedStreamId(n2);
        this.setPriority(by);
    }

    @Override
    public SpdySynStreamFrame setStreamId(int n) {
        super.setStreamId(n);
        return this;
    }

    @Override
    public SpdySynStreamFrame setLast(boolean bl) {
        super.setLast(bl);
        return this;
    }

    @Override
    public SpdySynStreamFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    public int associatedStreamId() {
        return this.associatedStreamId;
    }

    @Override
    public SpdySynStreamFrame setAssociatedStreamId(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + n);
        }
        this.associatedStreamId = n;
        return this;
    }

    @Override
    public byte priority() {
        return this.priority;
    }

    @Override
    public SpdySynStreamFrame setPriority(byte by) {
        if (by < 0 || by > 7) {
            throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + by);
        }
        this.priority = by;
        return this;
    }

    @Override
    public boolean isUnidirectional() {
        return this.unidirectional;
    }

    @Override
    public SpdySynStreamFrame setUnidirectional(boolean bl) {
        this.unidirectional = bl;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(this.isLast()).append("; unidirectional: ").append(this.isUnidirectional()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(this.streamId()).append(StringUtil.NEWLINE);
        if (this.associatedStreamId != 0) {
            stringBuilder.append("--> Associated-To-Stream-ID = ").append(this.associatedStreamId()).append(StringUtil.NEWLINE);
        }
        stringBuilder.append("--> Priority = ").append(this.priority()).append(StringUtil.NEWLINE).append("--> Headers:").append(StringUtil.NEWLINE);
        this.appendHeaders(stringBuilder);
        stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
        return stringBuilder.toString();
    }

    @Override
    public SpdyHeadersFrame setInvalid() {
        return this.setInvalid();
    }

    @Override
    public SpdyHeadersFrame setLast(boolean bl) {
        return this.setLast(bl);
    }

    @Override
    public SpdyHeadersFrame setStreamId(int n) {
        return this.setStreamId(n);
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

