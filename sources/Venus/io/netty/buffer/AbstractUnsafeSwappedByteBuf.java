/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;

abstract class AbstractUnsafeSwappedByteBuf
extends SwappedByteBuf {
    private final boolean nativeByteOrder;
    private final AbstractByteBuf wrapped;
    static final boolean $assertionsDisabled = !AbstractUnsafeSwappedByteBuf.class.desiredAssertionStatus();

    AbstractUnsafeSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
        if (!$assertionsDisabled && !PlatformDependent.isUnaligned()) {
            throw new AssertionError();
        }
        this.wrapped = abstractByteBuf;
        this.nativeByteOrder = PlatformDependent.BIG_ENDIAN_NATIVE_ORDER == (this.order() == ByteOrder.BIG_ENDIAN);
    }

    @Override
    public final long getLong(int n) {
        this.wrapped.checkIndex(n, 8);
        long l = this._getLong(this.wrapped, n);
        return this.nativeByteOrder ? l : Long.reverseBytes(l);
    }

    @Override
    public final float getFloat(int n) {
        return Float.intBitsToFloat(this.getInt(n));
    }

    @Override
    public final double getDouble(int n) {
        return Double.longBitsToDouble(this.getLong(n));
    }

    @Override
    public final char getChar(int n) {
        return (char)this.getShort(n);
    }

    @Override
    public final long getUnsignedInt(int n) {
        return (long)this.getInt(n) & 0xFFFFFFFFL;
    }

    @Override
    public final int getInt(int n) {
        this.wrapped.checkIndex(n, 4);
        int n2 = this._getInt(this.wrapped, n);
        return this.nativeByteOrder ? n2 : Integer.reverseBytes(n2);
    }

    @Override
    public final int getUnsignedShort(int n) {
        return this.getShort(n) & 0xFFFF;
    }

    @Override
    public final short getShort(int n) {
        this.wrapped.checkIndex(n, 2);
        short s = this._getShort(this.wrapped, n);
        return this.nativeByteOrder ? s : Short.reverseBytes(s);
    }

    @Override
    public final ByteBuf setShort(int n, int n2) {
        this.wrapped.checkIndex(n, 2);
        this._setShort(this.wrapped, n, this.nativeByteOrder ? (short)n2 : Short.reverseBytes((short)n2));
        return this;
    }

    @Override
    public final ByteBuf setInt(int n, int n2) {
        this.wrapped.checkIndex(n, 4);
        this._setInt(this.wrapped, n, this.nativeByteOrder ? n2 : Integer.reverseBytes(n2));
        return this;
    }

    @Override
    public final ByteBuf setLong(int n, long l) {
        this.wrapped.checkIndex(n, 8);
        this._setLong(this.wrapped, n, this.nativeByteOrder ? l : Long.reverseBytes(l));
        return this;
    }

    @Override
    public final ByteBuf setChar(int n, int n2) {
        this.setShort(n, n2);
        return this;
    }

    @Override
    public final ByteBuf setFloat(int n, float f) {
        this.setInt(n, Float.floatToRawIntBits(f));
        return this;
    }

    @Override
    public final ByteBuf setDouble(int n, double d) {
        this.setLong(n, Double.doubleToRawLongBits(d));
        return this;
    }

    @Override
    public final ByteBuf writeShort(int n) {
        this.wrapped.ensureWritable0(2);
        this._setShort(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? (short)n : Short.reverseBytes((short)n));
        this.wrapped.writerIndex += 2;
        return this;
    }

    @Override
    public final ByteBuf writeInt(int n) {
        this.wrapped.ensureWritable0(4);
        this._setInt(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? n : Integer.reverseBytes(n));
        this.wrapped.writerIndex += 4;
        return this;
    }

    @Override
    public final ByteBuf writeLong(long l) {
        this.wrapped.ensureWritable0(8);
        this._setLong(this.wrapped, this.wrapped.writerIndex, this.nativeByteOrder ? l : Long.reverseBytes(l));
        this.wrapped.writerIndex += 8;
        return this;
    }

    @Override
    public final ByteBuf writeChar(int n) {
        this.writeShort(n);
        return this;
    }

    @Override
    public final ByteBuf writeFloat(float f) {
        this.writeInt(Float.floatToRawIntBits(f));
        return this;
    }

    @Override
    public final ByteBuf writeDouble(double d) {
        this.writeLong(Double.doubleToRawLongBits(d));
        return this;
    }

    protected abstract short _getShort(AbstractByteBuf var1, int var2);

    protected abstract int _getInt(AbstractByteBuf var1, int var2);

    protected abstract long _getLong(AbstractByteBuf var1, int var2);

    protected abstract void _setShort(AbstractByteBuf var1, int var2, short var3);

    protected abstract void _setInt(AbstractByteBuf var1, int var2, int var3);

    protected abstract void _setLong(AbstractByteBuf var1, int var2, long var3);
}

