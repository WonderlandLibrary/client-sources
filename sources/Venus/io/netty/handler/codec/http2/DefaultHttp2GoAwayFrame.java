/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2GoAwayFrame;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultHttp2GoAwayFrame
extends DefaultByteBufHolder
implements Http2GoAwayFrame {
    private final long errorCode;
    private final int lastStreamId;
    private int extraStreamIds;

    public DefaultHttp2GoAwayFrame(Http2Error http2Error) {
        this(http2Error.code());
    }

    public DefaultHttp2GoAwayFrame(long l) {
        this(l, Unpooled.EMPTY_BUFFER);
    }

    public DefaultHttp2GoAwayFrame(Http2Error http2Error, ByteBuf byteBuf) {
        this(http2Error.code(), byteBuf);
    }

    public DefaultHttp2GoAwayFrame(long l, ByteBuf byteBuf) {
        this(-1, l, byteBuf);
    }

    DefaultHttp2GoAwayFrame(int n, long l, ByteBuf byteBuf) {
        super(byteBuf);
        this.errorCode = l;
        this.lastStreamId = n;
    }

    @Override
    public String name() {
        return "GOAWAY";
    }

    @Override
    public long errorCode() {
        return this.errorCode;
    }

    @Override
    public int extraStreamIds() {
        return this.extraStreamIds;
    }

    @Override
    public Http2GoAwayFrame setExtraStreamIds(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("extraStreamIds must be non-negative");
        }
        this.extraStreamIds = n;
        return this;
    }

    @Override
    public int lastStreamId() {
        return this.lastStreamId;
    }

    @Override
    public Http2GoAwayFrame copy() {
        return new DefaultHttp2GoAwayFrame(this.lastStreamId, this.errorCode, this.content().copy());
    }

    @Override
    public Http2GoAwayFrame duplicate() {
        return (Http2GoAwayFrame)super.duplicate();
    }

    @Override
    public Http2GoAwayFrame retainedDuplicate() {
        return (Http2GoAwayFrame)super.retainedDuplicate();
    }

    @Override
    public Http2GoAwayFrame replace(ByteBuf byteBuf) {
        return new DefaultHttp2GoAwayFrame(this.errorCode, byteBuf).setExtraStreamIds(this.extraStreamIds);
    }

    @Override
    public Http2GoAwayFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public Http2GoAwayFrame retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public Http2GoAwayFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public Http2GoAwayFrame touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DefaultHttp2GoAwayFrame)) {
            return true;
        }
        DefaultHttp2GoAwayFrame defaultHttp2GoAwayFrame = (DefaultHttp2GoAwayFrame)object;
        return this.errorCode == defaultHttp2GoAwayFrame.errorCode && this.extraStreamIds == defaultHttp2GoAwayFrame.extraStreamIds && super.equals(defaultHttp2GoAwayFrame);
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = n * 31 + (int)(this.errorCode ^ this.errorCode >>> 32);
        n = n * 31 + this.extraStreamIds;
        return n;
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(errorCode=" + this.errorCode + ", content=" + this.content() + ", extraStreamIds=" + this.extraStreamIds + ", lastStreamId=" + this.lastStreamId + ')';
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

