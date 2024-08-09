/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledHeapByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.buffer.UnsafeHeapSwappedByteBuf;
import io.netty.util.Recycler;
import io.netty.util.internal.PlatformDependent;

final class PooledUnsafeHeapByteBuf
extends PooledHeapByteBuf {
    private static final Recycler<PooledUnsafeHeapByteBuf> RECYCLER = new Recycler<PooledUnsafeHeapByteBuf>(){

        @Override
        protected PooledUnsafeHeapByteBuf newObject(Recycler.Handle<PooledUnsafeHeapByteBuf> handle) {
            return new PooledUnsafeHeapByteBuf(handle, 0, null);
        }

        @Override
        protected Object newObject(Recycler.Handle handle) {
            return this.newObject(handle);
        }
    };

    static PooledUnsafeHeapByteBuf newUnsafeInstance(int n) {
        PooledUnsafeHeapByteBuf pooledUnsafeHeapByteBuf = RECYCLER.get();
        pooledUnsafeHeapByteBuf.reuse(n);
        return pooledUnsafeHeapByteBuf;
    }

    private PooledUnsafeHeapByteBuf(Recycler.Handle<PooledUnsafeHeapByteBuf> handle, int n) {
        super((Recycler.Handle<? extends PooledHeapByteBuf>)handle, n);
    }

    @Override
    protected byte _getByte(int n) {
        return UnsafeByteBufUtil.getByte((byte[])this.memory, this.idx(n));
    }

    @Override
    protected short _getShort(int n) {
        return UnsafeByteBufUtil.getShort((byte[])this.memory, this.idx(n));
    }

    @Override
    protected short _getShortLE(int n) {
        return UnsafeByteBufUtil.getShortLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return UnsafeByteBufUtil.getUnsignedMedium((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return UnsafeByteBufUtil.getUnsignedMediumLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getInt(int n) {
        return UnsafeByteBufUtil.getInt((byte[])this.memory, this.idx(n));
    }

    @Override
    protected int _getIntLE(int n) {
        return UnsafeByteBufUtil.getIntLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected long _getLong(int n) {
        return UnsafeByteBufUtil.getLong((byte[])this.memory, this.idx(n));
    }

    @Override
    protected long _getLongLE(int n) {
        return UnsafeByteBufUtil.getLongLE((byte[])this.memory, this.idx(n));
    }

    @Override
    protected void _setByte(int n, int n2) {
        UnsafeByteBufUtil.setByte((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        UnsafeByteBufUtil.setShort((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        UnsafeByteBufUtil.setShortLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        UnsafeByteBufUtil.setMedium((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        UnsafeByteBufUtil.setMediumLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        UnsafeByteBufUtil.setInt((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        UnsafeByteBufUtil.setIntLE((byte[])this.memory, this.idx(n), n2);
    }

    @Override
    protected void _setLong(int n, long l) {
        UnsafeByteBufUtil.setLong((byte[])this.memory, this.idx(n), l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        UnsafeByteBufUtil.setLongLE((byte[])this.memory, this.idx(n), l);
    }

    @Override
    public ByteBuf setZero(int n, int n2) {
        if (PlatformDependent.javaVersion() >= 7) {
            this.checkIndex(n, n2);
            UnsafeByteBufUtil.setZero((byte[])this.memory, this.idx(n), n2);
            return this;
        }
        return super.setZero(n, n2);
    }

    @Override
    public ByteBuf writeZero(int n) {
        if (PlatformDependent.javaVersion() >= 7) {
            this.ensureWritable(n);
            int n2 = this.writerIndex;
            UnsafeByteBufUtil.setZero((byte[])this.memory, this.idx(n2), n);
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

    PooledUnsafeHeapByteBuf(Recycler.Handle handle, int n, 1 var3_3) {
        this(handle, n);
    }
}

