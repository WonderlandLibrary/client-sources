/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2UnknownFrame;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultHttp2UnknownFrame
extends DefaultByteBufHolder
implements Http2UnknownFrame {
    private final byte frameType;
    private final Http2Flags flags;
    private Http2FrameStream stream;

    public DefaultHttp2UnknownFrame(byte by, Http2Flags http2Flags) {
        this(by, http2Flags, Unpooled.EMPTY_BUFFER);
    }

    public DefaultHttp2UnknownFrame(byte by, Http2Flags http2Flags, ByteBuf byteBuf) {
        super(byteBuf);
        this.frameType = by;
        this.flags = http2Flags;
    }

    @Override
    public Http2FrameStream stream() {
        return this.stream;
    }

    @Override
    public DefaultHttp2UnknownFrame stream(Http2FrameStream http2FrameStream) {
        this.stream = http2FrameStream;
        return this;
    }

    @Override
    public byte frameType() {
        return this.frameType;
    }

    @Override
    public Http2Flags flags() {
        return this.flags;
    }

    @Override
    public String name() {
        return "UNKNOWN";
    }

    @Override
    public DefaultHttp2UnknownFrame copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public DefaultHttp2UnknownFrame duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public DefaultHttp2UnknownFrame retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public DefaultHttp2UnknownFrame replace(ByteBuf byteBuf) {
        return new DefaultHttp2UnknownFrame(this.frameType, this.flags, byteBuf).stream(this.stream());
    }

    @Override
    public DefaultHttp2UnknownFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public DefaultHttp2UnknownFrame retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(frameType=" + this.frameType() + ", stream=" + this.stream() + ", flags=" + this.flags() + ", content=" + this.contentToString() + ')';
    }

    @Override
    public DefaultHttp2UnknownFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public DefaultHttp2UnknownFrame touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttp2UnknownFrame)) {
            return true;
        }
        DefaultHttp2UnknownFrame defaultHttp2UnknownFrame = (DefaultHttp2UnknownFrame)object;
        return super.equals(defaultHttp2UnknownFrame) && this.flags().equals(defaultHttp2UnknownFrame.flags()) && this.frameType() == defaultHttp2UnknownFrame.frameType() && this.stream() == null && defaultHttp2UnknownFrame.stream() == null || this.stream().equals(defaultHttp2UnknownFrame.stream());
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + this.frameType();
        n = n * 31 + this.flags().hashCode();
        if (this.stream() != null) {
            n = n * 31 + this.stream().hashCode();
        }
        return n;
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

    @Override
    public Http2UnknownFrame touch(Object object) {
        return this.touch(object);
    }

    @Override
    public Http2UnknownFrame touch() {
        return this.touch();
    }

    @Override
    public Http2UnknownFrame retain(int n) {
        return this.retain(n);
    }

    @Override
    public Http2UnknownFrame retain() {
        return this.retain();
    }

    @Override
    public Http2UnknownFrame replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public Http2UnknownFrame retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public Http2UnknownFrame duplicate() {
        return this.duplicate();
    }

    @Override
    public Http2UnknownFrame copy() {
        return this.copy();
    }

    @Override
    public Http2UnknownFrame stream(Http2FrameStream http2FrameStream) {
        return this.stream(http2FrameStream);
    }
}

