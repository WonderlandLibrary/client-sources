/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.PoolArena;
import io.netty.buffer.PoolChunkList;
import io.netty.buffer.PoolChunkMetric;
import io.netty.buffer.PoolSubpage;
import io.netty.buffer.PooledByteBuf;

final class PoolChunk<T>
implements PoolChunkMetric {
    private static final int INTEGER_SIZE_MINUS_ONE = 31;
    final PoolArena<T> arena;
    final T memory;
    final boolean unpooled;
    final int offset;
    private final byte[] memoryMap;
    private final byte[] depthMap;
    private final PoolSubpage<T>[] subpages;
    private final int subpageOverflowMask;
    private final int pageSize;
    private final int pageShifts;
    private final int maxOrder;
    private final int chunkSize;
    private final int log2ChunkSize;
    private final int maxSubpageAllocs;
    private final byte unusable;
    private int freeBytes;
    PoolChunkList<T> parent;
    PoolChunk<T> prev;
    PoolChunk<T> next;
    static final boolean $assertionsDisabled = !PoolChunk.class.desiredAssertionStatus();

    PoolChunk(PoolArena<T> poolArena, T t, int n, int n2, int n3, int n4, int n5) {
        this.unpooled = false;
        this.arena = poolArena;
        this.memory = t;
        this.pageSize = n;
        this.pageShifts = n3;
        this.maxOrder = n2;
        this.chunkSize = n4;
        this.offset = n5;
        this.unusable = (byte)(n2 + 1);
        this.log2ChunkSize = PoolChunk.log2(n4);
        this.subpageOverflowMask = ~(n - 1);
        this.freeBytes = n4;
        if (!$assertionsDisabled && n2 >= 30) {
            throw new AssertionError((Object)("maxOrder should be < 30, but is: " + n2));
        }
        this.maxSubpageAllocs = 1 << n2;
        this.memoryMap = new byte[this.maxSubpageAllocs << 1];
        this.depthMap = new byte[this.memoryMap.length];
        int n6 = 1;
        for (int i = 0; i <= n2; ++i) {
            int n7 = 1 << i;
            for (int j = 0; j < n7; ++j) {
                this.memoryMap[n6] = (byte)i;
                this.depthMap[n6] = (byte)i;
                ++n6;
            }
        }
        this.subpages = this.newSubpageArray(this.maxSubpageAllocs);
    }

    PoolChunk(PoolArena<T> poolArena, T t, int n, int n2) {
        this.unpooled = true;
        this.arena = poolArena;
        this.memory = t;
        this.offset = n2;
        this.memoryMap = null;
        this.depthMap = null;
        this.subpages = null;
        this.subpageOverflowMask = 0;
        this.pageSize = 0;
        this.pageShifts = 0;
        this.maxOrder = 0;
        this.unusable = (byte)(this.maxOrder + 1);
        this.chunkSize = n;
        this.log2ChunkSize = PoolChunk.log2(this.chunkSize);
        this.maxSubpageAllocs = 0;
    }

    private PoolSubpage<T>[] newSubpageArray(int n) {
        return new PoolSubpage[n];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int usage() {
        int n;
        PoolArena<T> poolArena = this.arena;
        synchronized (poolArena) {
            n = this.freeBytes;
        }
        return this.usage(n);
    }

    private int usage(int n) {
        if (n == 0) {
            return 1;
        }
        int n2 = (int)((long)n * 100L / (long)this.chunkSize);
        if (n2 == 0) {
            return 0;
        }
        return 100 - n2;
    }

    long allocate(int n) {
        if ((n & this.subpageOverflowMask) != 0) {
            return this.allocateRun(n);
        }
        return this.allocateSubpage(n);
    }

    private void updateParentsAlloc(int n) {
        while (n > 1) {
            byte by;
            int n2 = n >>> 1;
            byte by2 = this.value(n);
            byte by3 = by2 < (by = this.value(n ^ 1)) ? by2 : by;
            this.setValue(n2, by3);
            n = n2;
        }
    }

    private void updateParentsFree(int n) {
        int n2 = this.depth(n) + 1;
        while (n > 1) {
            int n3 = n >>> 1;
            byte by = this.value(n);
            byte by2 = this.value(n ^ 1);
            if (by == --n2 && by2 == n2) {
                this.setValue(n3, (byte)(n2 - 1));
            } else {
                byte by3 = by < by2 ? by : by2;
                this.setValue(n3, by3);
            }
            n = n3;
        }
    }

    private int allocateNode(int n) {
        int n2 = 1;
        int n3 = -(1 << n);
        byte by = this.value(n2);
        if (by > n) {
            return 1;
        }
        while (by < n || (n2 & n3) == 0) {
            by = this.value(n2 <<= 1);
            if (by <= n) continue;
            by = this.value(n2 ^= 1);
        }
        byte by2 = this.value(n2);
        if (!($assertionsDisabled || by2 == n && (n2 & n3) == 1 << n)) {
            throw new AssertionError((Object)String.format("val = %d, id & initial = %d, d = %d", by2, n2 & n3, n));
        }
        this.setValue(n2, this.unusable);
        this.updateParentsAlloc(n2);
        return n2;
    }

    private long allocateRun(int n) {
        int n2 = this.maxOrder - (PoolChunk.log2(n) - this.pageShifts);
        int n3 = this.allocateNode(n2);
        if (n3 < 0) {
            return n3;
        }
        this.freeBytes -= this.runLength(n3);
        return n3;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long allocateSubpage(int n) {
        PoolSubpage<T> poolSubpage;
        PoolSubpage<T> poolSubpage2 = poolSubpage = this.arena.findSubpagePoolHead(n);
        synchronized (poolSubpage2) {
            int n2 = this.maxOrder;
            int n3 = this.allocateNode(n2);
            if (n3 < 0) {
                return n3;
            }
            PoolSubpage<T>[] poolSubpageArray = this.subpages;
            int n4 = this.pageSize;
            this.freeBytes -= n4;
            int n5 = this.subpageIdx(n3);
            PoolSubpage<T> poolSubpage3 = poolSubpageArray[n5];
            if (poolSubpage3 == null) {
                poolSubpage3 = new PoolSubpage<T>(poolSubpage, this, n3, this.runOffset(n3), n4, n);
                poolSubpageArray[n5] = poolSubpage3;
            } else {
                poolSubpage3.init(poolSubpage, n);
            }
            return poolSubpage3.allocate();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void free(long l) {
        int n = PoolChunk.memoryMapIdx(l);
        int n2 = PoolChunk.bitmapIdx(l);
        if (n2 != 0) {
            PoolSubpage<T> poolSubpage;
            PoolSubpage<T> poolSubpage2 = this.subpages[this.subpageIdx(n)];
            if (!($assertionsDisabled || poolSubpage2 != null && poolSubpage2.doNotDestroy)) {
                throw new AssertionError();
            }
            PoolSubpage<T> poolSubpage3 = poolSubpage = this.arena.findSubpagePoolHead(poolSubpage2.elemSize);
            synchronized (poolSubpage3) {
                if (poolSubpage2.free(poolSubpage, n2 & 0x3FFFFFFF)) {
                    return;
                }
            }
        }
        this.freeBytes += this.runLength(n);
        this.setValue(n, this.depth(n));
        this.updateParentsFree(n);
    }

    void initBuf(PooledByteBuf<T> pooledByteBuf, long l, int n) {
        int n2 = PoolChunk.memoryMapIdx(l);
        int n3 = PoolChunk.bitmapIdx(l);
        if (n3 == 0) {
            byte by = this.value(n2);
            if (!$assertionsDisabled && by != this.unusable) {
                throw new AssertionError((Object)String.valueOf(by));
            }
            pooledByteBuf.init(this, l, this.runOffset(n2) + this.offset, n, this.runLength(n2), this.arena.parent.threadCache());
        } else {
            this.initBufWithSubpage(pooledByteBuf, l, n3, n);
        }
    }

    void initBufWithSubpage(PooledByteBuf<T> pooledByteBuf, long l, int n) {
        this.initBufWithSubpage(pooledByteBuf, l, PoolChunk.bitmapIdx(l), n);
    }

    private void initBufWithSubpage(PooledByteBuf<T> pooledByteBuf, long l, int n, int n2) {
        if (!$assertionsDisabled && n == 0) {
            throw new AssertionError();
        }
        int n3 = PoolChunk.memoryMapIdx(l);
        PoolSubpage<T> poolSubpage = this.subpages[this.subpageIdx(n3)];
        if (!$assertionsDisabled && !poolSubpage.doNotDestroy) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n2 > poolSubpage.elemSize) {
            throw new AssertionError();
        }
        pooledByteBuf.init(this, l, this.runOffset(n3) + (n & 0x3FFFFFFF) * poolSubpage.elemSize + this.offset, n2, poolSubpage.elemSize, this.arena.parent.threadCache());
    }

    private byte value(int n) {
        return this.memoryMap[n];
    }

    private void setValue(int n, byte by) {
        this.memoryMap[n] = by;
    }

    private byte depth(int n) {
        return this.depthMap[n];
    }

    private static int log2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    private int runLength(int n) {
        return 1 << this.log2ChunkSize - this.depth(n);
    }

    private int runOffset(int n) {
        int n2 = n ^ 1 << this.depth(n);
        return n2 * this.runLength(n);
    }

    private int subpageIdx(int n) {
        return n ^ this.maxSubpageAllocs;
    }

    private static int memoryMapIdx(long l) {
        return (int)l;
    }

    private static int bitmapIdx(long l) {
        return (int)(l >>> 32);
    }

    @Override
    public int chunkSize() {
        return this.chunkSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int freeBytes() {
        PoolArena<T> poolArena = this.arena;
        synchronized (poolArena) {
            return this.freeBytes;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        int n;
        PoolArena<T> poolArena = this.arena;
        synchronized (poolArena) {
            n = this.freeBytes;
        }
        return "Chunk(" + Integer.toHexString(System.identityHashCode(this)) + ": " + this.usage(n) + "%, " + (this.chunkSize - n) + '/' + this.chunkSize + ')';
    }

    void destroy() {
        this.arena.destroyChunk(this);
    }
}

