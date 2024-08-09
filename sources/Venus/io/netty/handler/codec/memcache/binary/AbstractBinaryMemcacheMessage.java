/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache.binary;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.memcache.AbstractMemcacheObject;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheMessage;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractBinaryMemcacheMessage
extends AbstractMemcacheObject
implements BinaryMemcacheMessage {
    private ByteBuf key;
    private ByteBuf extras;
    private byte magic;
    private byte opcode;
    private short keyLength;
    private byte extrasLength;
    private byte dataType;
    private int totalBodyLength;
    private int opaque;
    private long cas;

    protected AbstractBinaryMemcacheMessage(ByteBuf byteBuf, ByteBuf byteBuf2) {
        this.key = byteBuf;
        this.keyLength = byteBuf == null ? (short)0 : (short)byteBuf.readableBytes();
        this.extras = byteBuf2;
        this.extrasLength = byteBuf2 == null ? (byte)0 : (byte)byteBuf2.readableBytes();
        this.totalBodyLength = this.keyLength + this.extrasLength;
    }

    @Override
    public ByteBuf key() {
        return this.key;
    }

    @Override
    public ByteBuf extras() {
        return this.extras;
    }

    @Override
    public BinaryMemcacheMessage setKey(ByteBuf byteBuf) {
        if (this.key != null) {
            this.key.release();
        }
        this.key = byteBuf;
        short s = this.keyLength;
        this.keyLength = byteBuf == null ? (short)0 : (short)byteBuf.readableBytes();
        this.totalBodyLength = this.totalBodyLength + this.keyLength - s;
        return this;
    }

    @Override
    public BinaryMemcacheMessage setExtras(ByteBuf byteBuf) {
        if (this.extras != null) {
            this.extras.release();
        }
        this.extras = byteBuf;
        short s = this.extrasLength;
        this.extrasLength = byteBuf == null ? (byte)0 : (byte)byteBuf.readableBytes();
        this.totalBodyLength = this.totalBodyLength + this.extrasLength - s;
        return this;
    }

    @Override
    public byte magic() {
        return this.magic;
    }

    @Override
    public BinaryMemcacheMessage setMagic(byte by) {
        this.magic = by;
        return this;
    }

    @Override
    public long cas() {
        return this.cas;
    }

    @Override
    public BinaryMemcacheMessage setCas(long l) {
        this.cas = l;
        return this;
    }

    @Override
    public int opaque() {
        return this.opaque;
    }

    @Override
    public BinaryMemcacheMessage setOpaque(int n) {
        this.opaque = n;
        return this;
    }

    @Override
    public int totalBodyLength() {
        return this.totalBodyLength;
    }

    @Override
    public BinaryMemcacheMessage setTotalBodyLength(int n) {
        this.totalBodyLength = n;
        return this;
    }

    @Override
    public byte dataType() {
        return this.dataType;
    }

    @Override
    public BinaryMemcacheMessage setDataType(byte by) {
        this.dataType = by;
        return this;
    }

    @Override
    public byte extrasLength() {
        return this.extrasLength;
    }

    BinaryMemcacheMessage setExtrasLength(byte by) {
        this.extrasLength = by;
        return this;
    }

    @Override
    public short keyLength() {
        return this.keyLength;
    }

    BinaryMemcacheMessage setKeyLength(short s) {
        this.keyLength = s;
        return this;
    }

    @Override
    public byte opcode() {
        return this.opcode;
    }

    @Override
    public BinaryMemcacheMessage setOpcode(byte by) {
        this.opcode = by;
        return this;
    }

    @Override
    public BinaryMemcacheMessage retain() {
        super.retain();
        return this;
    }

    @Override
    public BinaryMemcacheMessage retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    protected void deallocate() {
        if (this.key != null) {
            this.key.release();
        }
        if (this.extras != null) {
            this.extras.release();
        }
    }

    @Override
    public BinaryMemcacheMessage touch() {
        super.touch();
        return this;
    }

    @Override
    public BinaryMemcacheMessage touch(Object object) {
        if (this.key != null) {
            this.key.touch(object);
        }
        if (this.extras != null) {
            this.extras.touch(object);
        }
        return this;
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
}

