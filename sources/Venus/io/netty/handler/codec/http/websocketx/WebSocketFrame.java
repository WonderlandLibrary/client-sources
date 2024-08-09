/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class WebSocketFrame
extends DefaultByteBufHolder {
    private final boolean finalFragment;
    private final int rsv;

    protected WebSocketFrame(ByteBuf byteBuf) {
        this(true, 0, byteBuf);
    }

    protected WebSocketFrame(boolean bl, int n, ByteBuf byteBuf) {
        super(byteBuf);
        this.finalFragment = bl;
        this.rsv = n;
    }

    public boolean isFinalFragment() {
        return this.finalFragment;
    }

    public int rsv() {
        return this.rsv;
    }

    @Override
    public WebSocketFrame copy() {
        return (WebSocketFrame)super.copy();
    }

    @Override
    public WebSocketFrame duplicate() {
        return (WebSocketFrame)super.duplicate();
    }

    @Override
    public WebSocketFrame retainedDuplicate() {
        return (WebSocketFrame)super.retainedDuplicate();
    }

    @Override
    public abstract WebSocketFrame replace(ByteBuf var1);

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + this.contentToString() + ')';
    }

    @Override
    public WebSocketFrame retain() {
        super.retain();
        return this;
    }

    @Override
    public WebSocketFrame retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public WebSocketFrame touch() {
        super.touch();
        return this;
    }

    @Override
    public WebSocketFrame touch(Object object) {
        super.touch(object);
        return this;
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

