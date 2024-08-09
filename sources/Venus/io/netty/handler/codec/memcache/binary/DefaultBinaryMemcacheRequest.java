/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.AbstractBinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheRequest;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultBinaryMemcacheRequest
extends AbstractBinaryMemcacheMessage
implements BinaryMemcacheRequest {
    public static final byte REQUEST_MAGIC_BYTE = -128;
    private short reserved;

    public DefaultBinaryMemcacheRequest() {
        this(null, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf byteBuf) {
        this(byteBuf, null);
    }

    public DefaultBinaryMemcacheRequest(ByteBuf byteBuf, ByteBuf byteBuf2) {
        super(byteBuf, byteBuf2);
        this.setMagic((byte)-128);
    }

    @Override
    public short reserved() {
        return this.reserved;
    }

    @Override
    public BinaryMemcacheRequest setReserved(short s) {
        this.reserved = s;
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheRequest retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public BinaryMemcacheRequest touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryMemcacheRequest touch(Object object) {
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

