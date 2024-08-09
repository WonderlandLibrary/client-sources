/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponse;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultBinaryMemcacheResponse
extends AbstractBinaryMemcacheMessage
implements BinaryMemcacheResponse {
    public static final byte RESPONSE_MAGIC_BYTE = -127;
    private short status;

    public DefaultBinaryMemcacheResponse() {
        this(null, null);
    }

    public DefaultBinaryMemcacheResponse(ByteBuf byteBuf) {
        this(byteBuf, null);
    }

    public DefaultBinaryMemcacheResponse(ByteBuf byteBuf, ByteBuf byteBuf2) {
        super(byteBuf, byteBuf2);
        this.setMagic((byte)-127);
    }

    @Override
    public short status() {
        return this.status;
    }

    @Override
    public BinaryMemcacheResponse setStatus(short s) {
        this.status = s;
        return this;
    }

    @Override
    public BinaryMemcacheResponse retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheResponse retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public BinaryMemcacheResponse touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryMemcacheResponse touch(Object object) {
        super.touch(object);
        return this;
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
}

