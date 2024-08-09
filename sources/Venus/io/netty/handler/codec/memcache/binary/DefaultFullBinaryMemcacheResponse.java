/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.DefaultBinaryMemcacheResponse;
import io.netty.handler.codec.memcache.binary.FullBinaryMemcacheResponse;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultFullBinaryMemcacheResponse
extends DefaultBinaryMemcacheResponse
implements FullBinaryMemcacheResponse {
    private final ByteBuf content;

    public DefaultFullBinaryMemcacheResponse(ByteBuf byteBuf, ByteBuf byteBuf2) {
        this(byteBuf, byteBuf2, Unpooled.buffer(0));
    }

    public DefaultFullBinaryMemcacheResponse(ByteBuf byteBuf, ByteBuf byteBuf2, ByteBuf byteBuf3) {
        super(byteBuf, byteBuf2);
        if (byteBuf3 == null) {
            throw new NullPointerException("Supplied content is null.");
        }
        this.content = byteBuf3;
        this.setTotalBodyLength(this.keyLength() + this.extrasLength() + byteBuf3.readableBytes());
    }

    @Override
    public ByteBuf content() {
        return this.content;
    }

    @Override
    public FullBinaryMemcacheResponse retain() {
        super.retain();
        return this;
    }

    @Override
    public FullBinaryMemcacheResponse retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public FullBinaryMemcacheResponse touch() {
        super.touch();
        return this;
    }

    @Override
    public FullBinaryMemcacheResponse touch(Object object) {
        super.touch(object);
        this.content.touch(object);
        return this;
    }

    @Override
    protected void deallocate() {
        super.deallocate();
        this.content.release();
    }

    @Override
    public FullBinaryMemcacheResponse copy() {
        ByteBuf byteBuf;
        ByteBuf byteBuf2 = this.key();
        if (byteBuf2 != null) {
            byteBuf2 = byteBuf2.copy();
        }
        if ((byteBuf = this.extras()) != null) {
            byteBuf = byteBuf.copy();
        }
        return new DefaultFullBinaryMemcacheResponse(byteBuf2, byteBuf, this.content().copy());
    }

    @Override
    public FullBinaryMemcacheResponse duplicate() {
        ByteBuf byteBuf;
        ByteBuf byteBuf2 = this.key();
        if (byteBuf2 != null) {
            byteBuf2 = byteBuf2.duplicate();
        }
        if ((byteBuf = this.extras()) != null) {
            byteBuf = byteBuf.duplicate();
        }
        return new DefaultFullBinaryMemcacheResponse(byteBuf2, byteBuf, this.content().duplicate());
    }

    @Override
    public FullBinaryMemcacheResponse retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public FullBinaryMemcacheResponse replace(ByteBuf byteBuf) {
        ByteBuf byteBuf2;
        ByteBuf byteBuf3 = this.key();
        if (byteBuf3 != null) {
            byteBuf3 = byteBuf3.retainedDuplicate();
        }
        if ((byteBuf2 = this.extras()) != null) {
            byteBuf2 = byteBuf2.retainedDuplicate();
        }
        return new DefaultFullBinaryMemcacheResponse(byteBuf3, byteBuf2, byteBuf);
    }

    @Override
    public BinaryMemcacheResponse touch(Object object) {
        return this.touch(object);
    }

    @Override
    public BinaryMemcacheResponse touch() {
        return this.touch();
    }

    @Override
    public BinaryMemcacheResponse retain(int n) {
        return this.retain(n);
    }

    @Override
    public BinaryMemcacheResponse retain() {
        return this.retain();
    }

    @Override
    public BinaryMemcacheMessage touch(Object object) {
        return this.touch(object);
    }

    @Override
    public BinaryMemcacheMessage touch() {
        return this.touch();
    }

    @Override
    public BinaryMemcacheMessage retain(int n) {
        return this.retain(n);
    }

    @Override
    public BinaryMemcacheMessage retain() {
        return this.retain();
    }

    @Override
    public MemcacheMessage touch(Object object) {
        return this.touch(object);
    }

    @Override
    public MemcacheMessage touch() {
        return this.touch();
    }

    @Override
    public MemcacheMessage retain(int n) {
        return this.retain(n);
    }

    @Override
    public MemcacheMessage retain() {
        return this.retain();
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
    public FullMemcacheMessage touch(Object object) {
        return this.touch(object);
    }

    @Override
    public FullMemcacheMessage touch() {
        return this.touch();
    }

    @Override
    public FullMemcacheMessage retain() {
        return this.retain();
    }

    @Override
    public FullMemcacheMessage retain(int n) {
        return this.retain(n);
    }

    @Override
    public FullMemcacheMessage replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public FullMemcacheMessage retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public FullMemcacheMessage duplicate() {
        return this.duplicate();
    }

    @Override
    public FullMemcacheMessage copy() {
        return this.copy();
    }

    @Override
    public LastMemcacheContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public LastMemcacheContent touch() {
        return this.touch();
    }

    @Override
    public LastMemcacheContent retain() {
        return this.retain();
    }

    @Override
    public LastMemcacheContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public LastMemcacheContent replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public LastMemcacheContent retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public LastMemcacheContent duplicate() {
        return this.duplicate();
    }

    @Override
    public LastMemcacheContent copy() {
        return this.copy();
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
}

