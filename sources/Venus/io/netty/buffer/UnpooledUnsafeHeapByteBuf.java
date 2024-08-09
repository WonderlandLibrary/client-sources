/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.buffer.UnsafeHeapSwappedByteBuf;
import io.netty.util.internal.PlatformDependent;

class UnpooledUnsafeHeapByteBuf
extends UnpooledHeapByteBuf {
    UnpooledUnsafeHeapByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        super(byteBufAllocator, n, n2);
    }

    @Override
    byte[] allocateArray(int n) {
        return PlatformDependent.allocateUninitializedArray(n);
    }

    @Override
    public byte getByte(int n) {
        this.checkIndex(n);
        return this._getByte(n);
    }

    @Override
    protected byte _getByte(int n) {
        return UnsafeByteBufUtil.getByte(this.array, n);
    }

    @Override
    public short getShort(int n) {
        this.checkIndex(n, 2);
        return this._getShort(n);
    }

    @Override
    protected short _getShort(int n) {
        return UnsafeByteBufUtil.getShort(this.array, n);
    }

    @Override
    public short getShortLE(int n) {
        this.checkIndex(n, 2);
        return this._getShortLE(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return UnsafeByteBufUtil.getShortLE(this.array, n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        this.checkIndex(n, 3);
        return this._getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return UnsafeByteBufUtil.getUnsignedMedium(this.array, n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        this.checkIndex(n, 3);
        return this._getUnsignedMediumLE(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return UnsafeByteBufUtil.getUnsignedMediumLE(this.array, n);
    }

    @Override
    public int getInt(int n) {
        this.checkIndex(n, 4);
        return this._getInt(n);
    }

    @Override
    protected int _getInt(int n) {
        return UnsafeByteBufUtil.getInt(this.array, n);
    }

    @Override
    public int getIntLE(int n) {
        this.checkIndex(n, 4);
        return this._getIntLE(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return UnsafeByteBufUtil.getIntLE(this.array, n);
    }

    @Override
    public long getLong(int n) {
        this.checkIndex(n, 8);
        return this._getLong(n);
    }

    @Override
    protected long _getLong(int n) {
        return UnsafeByteBufUtil.getLong(this.array, n);
    }

    @Override
    public long getLongLE(int n) {
        this.checkIndex(n, 8);
        return this._getLongLE(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return UnsafeByteBufUtil.getLongLE(this.array, n);
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        this.checkIndex(n);
        this._setByte(n, n2);
        return this;
    }

    @Override
    protected void _setByte(int n, int n2) {
        UnsafeByteBufUtil.setByte(this.array, n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        this.checkIndex(n, 2);
        this._setShort(n, n2);
        return this;
    }

    @Override
    protected void _setShort(int n, int n2) {
        UnsafeByteBufUtil.setShort(this.array, n, n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        this.checkIndex(n, 2);
        this._setShortLE(n, n2);
        return this;
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        UnsafeByteBufUtil.setShortLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        this.checkIndex(n, 3);
        this._setMedium(n, n2);
        return this;
    }

    @Override
    protected void _setMedium(int n, int n2) {
        UnsafeByteBufUtil.setMedium(this.array, n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        this.checkIndex(n, 3);
        this._setMediumLE(n, n2);
        return this;
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        UnsafeByteBufUtil.setMediumLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        this.checkIndex(n, 4);
        this._setInt(n, n2);
        return this;
    }

    @Override
    protected void _setInt(int n, int n2) {
        UnsafeByteBufUtil.setInt(this.array, n, n2);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        this.checkIndex(n, 4);
        this._setIntLE(n, n2);
        return this;
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        UnsafeByteBufUtil.setIntLE(this.array, n, n2);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        this.checkIndex(n, 8);
        this._setLong(n, l);
        return this;
    }

    @Override
    protected void _setLong(int n, long l) {
        UnsafeByteBufUtil.setLong(this.array, n, l);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        this.checkIndex(n, 8);
        this._setLongLE(n, l);
        return this;
    }

    @Override
    protected void _setLongLE(int n, long l) {
        UnsafeByteBufUtil.setLongLE(this.array, n, l);
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        if (PlatformDependent.javaVersion() >= 7) {
            this.checkIndex(n, n2);
            UnsafeByteBufUtil.setZero(this.array, n, n2);
            return this;
        }
        return super.setZero(n, n2);
    }

    @Override
    public ByteBuf writeZero(int n) {
        if (PlatformDependent.javaVersion() >= 7) {
            this.ensureWritable(n);
            int n2 = this.writerIndex;
            UnsafeByteBufUtil.setZero(this.array, n2, n);
            this.writerIndex = n2 + n;
            return this;
        }
        return super.writeZero(n);
    }

    @Override
    @Deprecated
    protected SwappedByteBuf newSwappedByteBuf() {
        if (PlatformDependent.isUnaligned()) {
            return new UnsafeHeapSwappedByteBuf(this);
        }
        return super.newSwappedByteBuf();
    }
}

