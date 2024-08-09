/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.memcache.DefaultMemcacheContent;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultLastMemcacheContent
extends DefaultMemcacheContent
implements LastMemcacheContent {
    public DefaultLastMemcacheContent() {
        super(Unpooled.buffer());
    }

    public DefaultLastMemcacheContent(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public LastMemcacheContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastMemcacheContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public LastMemcacheContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastMemcacheContent touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public LastMemcacheContent copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public LastMemcacheContent duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public LastMemcacheContent retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public LastMemcacheContent replace(ByteBuf byteBuf) {
        return new DefaultLastMemcacheContent(byteBuf);
    }

    @Override
    public MemcacheContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public MemcacheContent touch() {
        return this.touch();
    }

    @Override
    public MemcacheContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public MemcacheContent retain() {
        return this.retain();
    }

    @Override
    public MemcacheContent replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public MemcacheContent retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public MemcacheContent duplicate() {
        return this.duplicate();
    }

    @Override
    public MemcacheContent copy() {
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

