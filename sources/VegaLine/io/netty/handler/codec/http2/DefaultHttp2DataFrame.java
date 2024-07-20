/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.AbstractHttp2StreamFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.ObjectUtil;

public final class DefaultHttp2DataFrame
extends AbstractHttp2StreamFrame
implements Http2DataFrame {
    private final ByteBuf content;
    private final boolean endStream;
    private final int padding;

    public DefaultHttp2DataFrame(ByteBuf content) {
        this(content, false);
    }

    public DefaultHttp2DataFrame(boolean endStream) {
        this(Unpooled.EMPTY_BUFFER, endStream);
    }

    public DefaultHttp2DataFrame(ByteBuf content, boolean endStream) {
        this(content, endStream, 0);
    }

    public DefaultHttp2DataFrame(ByteBuf content, boolean endStream, int padding) {
        this.content = ObjectUtil.checkNotNull(content, "content");
        this.endStream = endStream;
        Http2CodecUtil.verifyPadding(padding);
        this.padding = padding;
    }

    @Override
    public DefaultHttp2DataFrame streamId(int streamId) {
        super.streamId(streamId);
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
    public DefaultHttp2DataFrame replace(ByteBuf content) {
        return new DefaultHttp2DataFrame(content, this.endStream, this.padding);
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
    public boolean release(int decrement) {
        return this.content.release(decrement);
    }

    @Override
    public DefaultHttp2DataFrame retain() {
        this.content.retain();
        return this;
    }

    @Override
    public DefaultHttp2DataFrame retain(int increment) {
        this.content.retain(increment);
        return this;
    }

    public String toString() {
        return "DefaultHttp2DataFrame(streamId=" + this.streamId() + ", content=" + this.content + ", endStream=" + this.endStream + ", padding=" + this.padding + ")";
    }

    @Override
    public DefaultHttp2DataFrame touch() {
        this.content.touch();
        return this;
    }

    @Override
    public DefaultHttp2DataFrame touch(Object hint) {
        this.content.touch(hint);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultHttp2DataFrame)) {
            return false;
        }
        DefaultHttp2DataFrame other = (DefaultHttp2DataFrame)o;
        return super.equals(other) && this.content.equals(other.content()) && this.endStream == other.endStream && this.padding == other.padding;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 31 + this.content.hashCode();
        hash = hash * 31 + (this.endStream ? 0 : 1);
        hash = hash * 31 + this.padding;
        return hash;
    }
}

