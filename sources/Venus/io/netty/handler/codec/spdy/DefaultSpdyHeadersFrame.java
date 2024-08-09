/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.DefaultSpdyHeaders;
import io.netty.handler.codec.spdy.DefaultSpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdyHeaders;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.util.internal.StringUtil;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSpdyHeadersFrame
extends DefaultSpdyStreamFrame
implements SpdyHeadersFrame {
    private boolean invalid;
    private boolean truncated;
    private final SpdyHeaders headers;

    public DefaultSpdyHeadersFrame(int n) {
        this(n, true);
    }

    public DefaultSpdyHeadersFrame(int n, boolean bl) {
        super(n);
        this.headers = new DefaultSpdyHeaders(bl);
    }

    @Override
    public SpdyHeadersFrame setStreamId(int n) {
        super.setStreamId(n);
        return this;
    }

    @Override
    public SpdyHeadersFrame setLast(boolean bl) {
        super.setLast(bl);
        return this;
    }

    @Override
    public boolean isInvalid() {
        return this.invalid;
    }

    @Override
    public SpdyHeadersFrame setInvalid() {
        this.invalid = true;
        return this;
    }

    @Override
    public boolean isTruncated() {
        return this.truncated;
    }

    @Override
    public SpdyHeadersFrame setTruncated() {
        this.truncated = true;
        return this;
    }

    @Override
    public SpdyHeaders headers() {
        return this.headers;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(this.isLast()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(this.streamId()).append(StringUtil.NEWLINE).append("--> Headers:").append(StringUtil.NEWLINE);
        this.appendHeaders(stringBuilder);
        stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
        return stringBuilder.toString();
    }

    protected void appendHeaders(StringBuilder stringBuilder) {
        for (Map.Entry entry : this.headers()) {
            stringBuilder.append("    ");
            stringBuilder.append((CharSequence)entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append((CharSequence)entry.getValue());
            stringBuilder.append(StringUtil.NEWLINE);
        }
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

