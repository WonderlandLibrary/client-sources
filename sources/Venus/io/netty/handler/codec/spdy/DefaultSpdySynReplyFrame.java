/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.spdy.DefaultSpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyHeadersFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdySynReplyFrame;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSpdySynReplyFrame
extends DefaultSpdyHeadersFrame
implements SpdySynReplyFrame {
    public DefaultSpdySynReplyFrame(int n) {
        super(n);
    }

    public DefaultSpdySynReplyFrame(int n, boolean bl) {
        super(n, bl);
    }

    @Override
    public SpdySynReplyFrame setStreamId(int n) {
        super.setStreamId(n);
        return this;
    }

    @Override
    public SpdySynReplyFrame setLast(boolean bl) {
        super.setLast(bl);
        return this;
    }

    @Override
    public SpdySynReplyFrame setInvalid() {
        super.setInvalid();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(this.isLast()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(this.streamId()).append(StringUtil.NEWLINE).append("--> Headers:").append(StringUtil.NEWLINE);
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

