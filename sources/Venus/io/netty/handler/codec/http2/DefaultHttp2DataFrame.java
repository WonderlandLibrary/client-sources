/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultHttp2DataFrame
extends AbstractHttp2StreamFrame
implements Http2DataFrame {
    private final ByteBuf content;
    private final boolean endStream;
    private final int padding;
    private final int initialFlowControlledBytes;

    public DefaultHttp2DataFrame(ByteBuf byteBuf) {
        this(byteBuf, false);
    }

    public DefaultHttp2DataFrame(boolean bl) {
        this(Unpooled.EMPTY_BUFFER, bl);
    }

    public DefaultHttp2DataFrame(ByteBuf byteBuf, boolean bl) {
        this(byteBuf, bl, 0);
    }

    public DefaultHttp2DataFrame(ByteBuf byteBuf, boolean bl, int n) {
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.endStream = bl;
        Http2CodecUtil.verifyPadding(n);
        this.padding = n;
        if ((long)this.content().readableBytes() + (long)n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("content + padding must be <= Integer.MAX_VALUE");
        }
        this.initialFlowControlledBytes = this.content().readableBytes() + n;
    }

    @Override
    public DefaultHttp2DataFrame stream(Http2FrameStream http2FrameStream) {
        super.stream(http2FrameStream);
        return this;
    }

    @Override
    public String name() {
        return "DATA";
    }

    @Override
    public boolean isEndStream() {
        return this.endStream;
    }

    @Override
    public int padding() {
        return this.padding;
    }

    @Override
    public ByteBuf content() {
        if (this.content.refCnt() <= 0) {
            throw new IllegalReferenceCountException(this.content.refCnt());
        }
        return this.content;
    }

    @Override
    public int initialFlowControlledBytes() {
        return this.initialFlowControlledBytes;
    }

    @Override
    public DefaultHttp2DataFrame copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public DefaultHttp2DataFrame duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public DefaultHttp2DataFrame retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public DefaultHttp2DataFrame replace(ByteBuf byteBuf) {
        return new DefaultHttp2DataFrame(byteBuf, this.endStream, this.padding);
    }

    @Override
    public int refCnt() {
        return this.content.refCnt();
    }

    @Override
    public boolean release() {
        return this.content.release();
    }

    @Override
    public boolean release(int n) {
        return this.content.release(n);
    }

    @Override
    public DefaultHttp2DataFrame retain() {
        this.content.retain();
        return this;
    }

    @Override
    public DefaultHttp2DataFrame retain(int n) {
        this.content.retain(n);
        return this;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(stream=" + this.stream() + ", content=" + this.content + ", endStream=" + this.endStream + ", padding=" + this.padding + ')';
    }

    @Override
    public DefaultHttp2DataFrame touch() {
        this.content.touch();
        return this;
    }

    @Override
    public DefaultHttp2DataFrame touch(Object object) {
        this.content.touch(object);
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttp2DataFrame)) {
            return true;
        }
        DefaultHttp2DataFrame defaultHttp2DataFrame = (DefaultHttp2DataFrame)object;
        return super.equals(defaultHttp2DataFrame) && this.content.equals(defaultHttp2DataFrame.content()) && this.endStream == defaultHttp2DataFrame.endStream && this.padding == defaultHttp2DataFrame.padding;
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + this.content.hashCode();
        n = n * 31 + (this.endStream ? 0 : 1);
        n = n * 31 + this.padding;
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

    @Override
    public Http2DataFrame touch(Object object) {
        return this.touch(object);
    }

    @Override
    public Http2DataFrame touch() {
        return this.touch();
    }

    @Override
    public Http2DataFrame retain(int n) {
        return this.retain(n);
    }

    @Override
    public Http2DataFrame retain() {
        return this.retain();
    }

    @Override
    public Http2DataFrame replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public Http2DataFrame retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public Http2DataFrame duplicate() {
        return this.duplicate();
    }

    @Override
    public Http2DataFrame copy() {
        return this.copy();
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

