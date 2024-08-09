/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.memcache.AbstractMemcacheObject;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultMemcacheContent
extends AbstractMemcacheObject
implements MemcacheContent {
    private final ByteBuf content;

    public DefaultMemcacheContent(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("Content cannot be null.");
        }
        this.content = byteBuf;
    }

    @Override
    public ByteBuf content() {
        return this.content;
    }

    @Override
    public MemcacheContent copy() {
        return this.replace(this.content.copy());
    }

    @Override
    public MemcacheContent duplicate() {
        return this.replace(this.content.duplicate());
    }

    @Override
    public MemcacheContent retainedDuplicate() {
        return this.replace(this.content.retainedDuplicate());
    }

    @Override
    public MemcacheContent replace(ByteBuf byteBuf) {
        return new DefaultMemcacheContent(byteBuf);
    }

    @Override
    public MemcacheContent retain() {
        super.retain();
        return this;
    }

    @Override
    public MemcacheContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public MemcacheContent touch() {
        super.touch();
        return this;
    }

    @Override
    public MemcacheContent touch(Object object) {
        this.content.touch(object);
        return this;
    }

    @Override
    protected void deallocate() {
        this.content.release();
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(data: " + this.content() + ", decoderResult: " + this.decoderResult() + ')';
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
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
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
}

