/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.spdy.DefaultSpdyStreamFrame;
import io.netty.handler.codec.spdy.SpdyDataFrame;
import io.netty.handler.codec.spdy.SpdyStreamFrame;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultSpdyDataFrame
extends DefaultSpdyStreamFrame
implements SpdyDataFrame {
    private final ByteBuf data;

    public DefaultSpdyDataFrame(int n) {
        this(n, Unpooled.buffer(0));
    }

    public DefaultSpdyDataFrame(int n, ByteBuf byteBuf) {
        super(n);
        if (byteBuf == null) {
            throw new NullPointerException("data");
        }
        this.data = DefaultSpdyDataFrame.validate(byteBuf);
    }

    private static ByteBuf validate(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() > 0xFFFFFF) {
            throw new IllegalArgumentException("data payload cannot exceed 16777215 bytes");
        }
        return byteBuf;
    }

    @Override
    public SpdyDataFrame setStreamId(int n) {
        super.setStreamId(n);
        return this;
    }

    @Override
    public SpdyDataFrame setLast(boolean bl) {
        super.setLast(bl);
        return this;
    }

    @Override
    public ByteBuf content() {
        if (this.data.refCnt() <= 0) {
            throw new IllegalReferenceCountException(this.data.refCnt());
        }
        return this.data;
    }

    @Override
    public SpdyDataFrame copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public SpdyDataFrame duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public SpdyDataFrame retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public SpdyDataFrame replace(ByteBuf byteBuf) {
        DefaultSpdyDataFrame defaultSpdyDataFrame = new DefaultSpdyDataFrame(this.streamId(), byteBuf);
        defaultSpdyDataFrame.setLast(this.isLast());
        return defaultSpdyDataFrame;
    }

    @Override
    public int refCnt() {
        return this.data.refCnt();
    }

    @Override
    public SpdyDataFrame retain() {
        this.data.retain();
        return this;
    }

    @Override
    public SpdyDataFrame retain(int n) {
        this.data.retain(n);
        return this;
    }

    @Override
    public SpdyDataFrame touch() {
        this.data.touch();
        return this;
    }

    @Override
    public SpdyDataFrame touch(Object object) {
        this.data.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.data.release();
    }

    @Override
    public boolean release(int n) {
        return this.data.release(n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(StringUtil.simpleClassName(this)).append("(last: ").append(this.isLast()).append(')').append(StringUtil.NEWLINE).append("--> Stream-ID = ").append(this.streamId()).append(StringUtil.NEWLINE).append("--> Size = ");
        if (this.refCnt() == 0) {
            stringBuilder.append("(freed)");
        } else {
            stringBuilder.append(this.content().readableBytes());
        }
        return stringBuilder.toString();
    }

    @Override
    public SpdyStreamFrame setLast(boolean bl) {
        return this.setLast(bl);
    }

    @Override
    public SpdyStreamFrame setStreamId(int n) {
        return this.setStreamId(n);
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

