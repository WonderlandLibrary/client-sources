/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PoolThreadCache;
import io.netty.buffer.PooledDuplicatedByteBuf;
import io.netty.buffer.PooledSlicedByteBuf;
import io.netty.util.Recycler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

abstract class PooledByteBuf<T>
extends AbstractReferenceCountedByteBuf {
    private final Recycler.Handle<PooledByteBuf<T>> recyclerHandle;
    protected PoolChunk<T> chunk;
    protected long handle;
    protected T memory;
    protected int offset;
    protected int length;
    int maxLength;
    PoolThreadCache cache;
    private ByteBuffer tmpNioBuf;
    private ByteBufAllocator allocator;
    static final boolean $assertionsDisabled = !PooledByteBuf.class.desiredAssertionStatus();

    protected PooledByteBuf(Recycler.Handle<? extends PooledByteBuf<T>> handle, int n) {
        super(n);
        this.recyclerHandle = handle;
    }

    void init(PoolChunk<T> poolChunk, long l, int n, int n2, int n3, PoolThreadCache poolThreadCache) {
        this.init0(poolChunk, l, n, n2, n3, poolThreadCache);
    }

    void initUnpooled(PoolChunk<T> poolChunk, int n) {
        this.init0(poolChunk, 0L, poolChunk.offset, n, n, null);
    }

    private void init0(PoolChunk<T> poolChunk, long l, int n, int n2, int n3, PoolThreadCache poolThreadCache) {
        if (!$assertionsDisabled && l < 0L) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && poolChunk == null) {
            throw new AssertionError();
        }
        this.chunk = poolChunk;
        this.memory = poolChunk.memory;
        this.allocator = poolChunk.arena.parent;
        this.cache = poolThreadCache;
        this.handle = l;
        this.offset = n;
        this.length = n2;
        this.maxLength = n3;
        this.tmpNioBuf = null;
    }

    final void reuse(int n) {
        this.maxCapacity(n);
        this.setRefCnt(1);
        this.setIndex0(0, 0);
        this.discardMarks();
    }

    @Override
    public final int capacity() {
        return this.length;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public final ByteBuf capacity(int n) {
        this.checkNewCapacity(n);
        if (this.chunk.unpooled) {
            if (n == this.length) {
                return this;
            }
        } else if (n > this.length) {
            if (n <= this.maxLength) {
                this.length = n;
                return this;
            }
        } else {
            if (n >= this.length) return this;
            if (n > this.maxLength >>> 1) {
                if (this.maxLength <= 512) {
                    if (n > this.maxLength - 16) {
                        this.length = n;
                        this.setIndex(Math.min(this.readerIndex(), n), Math.min(this.writerIndex(), n));
                        return this;
                    }
                } else {
                    this.length = n;
                    this.setIndex(Math.min(this.readerIndex(), n), Math.min(this.writerIndex(), n));
                    return this;
                }
            }
        }
        this.chunk.arena.reallocate(this, n, false);
        return this;
    }

    @Override
    public final ByteBufAllocator alloc() {
        return this.allocator;
    }

    @Override
    public final ByteOrder order() {
        return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public final ByteBuf unwrap() {
        return null;
    }

    @Override
    public final ByteBuf retainedDuplicate() {
        return PooledDuplicatedByteBuf.newInstance(this, this, this.readerIndex(), this.writerIndex());
    }

    @Override
    public final ByteBuf retainedSlice() {
        int n = this.readerIndex();
        return this.retainedSlice(n, this.writerIndex() - n);
    }

    @Override
    public final ByteBuf retainedSlice(int n, int n2) {
        return PooledSlicedByteBuf.newInstance(this, this, n, n2);
    }

    protected final ByteBuffer internalNioBuffer() {
        ByteBuffer byteBuffer = this.tmpNioBuf;
        if (byteBuffer == null) {
            this.tmpNioBuf = byteBuffer = this.newInternalNioBuffer(this.memory);
        }
        return byteBuffer;
    }

    protected abstract ByteBuffer newInternalNioBuffer(T var1);

    @Override
    protected final void deallocate() {
        if (this.handle >= 0L) {
            long l = this.handle;
            this.handle = -1L;
            this.memory = null;
            this.tmpNioBuf = null;
            this.chunk.arena.free(this.chunk, l, this.maxLength, this.cache);
            this.chunk = null;
            this.recycle();
        }
    }

    private void recycle() {
        this.recyclerHandle.recycle(this);
    }

    protected final int idx(int n) {
        return this.offset + n;
    }
}

