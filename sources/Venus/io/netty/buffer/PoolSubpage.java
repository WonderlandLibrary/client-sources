/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.PoolArena;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PoolSubpageMetric;

final class PoolSubpage<T>
implements PoolSubpageMetric {
    final PoolChunk<T> chunk;
    private final int memoryMapIdx;
    private final int runOffset;
    private final int pageSize;
    private final long[] bitmap;
    PoolSubpage<T> prev;
    PoolSubpage<T> next;
    boolean doNotDestroy;
    int elemSize;
    private int maxNumElems;
    private int bitmapLength;
    private int nextAvail;
    private int numAvail;
    static final boolean $assertionsDisabled = !PoolSubpage.class.desiredAssertionStatus();

    PoolSubpage(int n) {
        this.chunk = null;
        this.memoryMapIdx = -1;
        this.runOffset = -1;
        this.elemSize = -1;
        this.pageSize = n;
        this.bitmap = null;
    }

    PoolSubpage(PoolSubpage<T> poolSubpage, PoolChunk<T> poolChunk, int n, int n2, int n3, int n4) {
        this.chunk = poolChunk;
        this.memoryMapIdx = n;
        this.runOffset = n2;
        this.pageSize = n3;
        this.bitmap = new long[n3 >>> 10];
        this.init(poolSubpage, n4);
    }

    void init(PoolSubpage<T> poolSubpage, int n) {
        this.doNotDestroy = true;
        this.elemSize = n;
        if (n != 0) {
            this.maxNumElems = this.numAvail = this.pageSize / n;
            this.nextAvail = 0;
            this.bitmapLength = this.maxNumElems >>> 6;
            if ((this.maxNumElems & 0x3F) != 0) {
                ++this.bitmapLength;
            }
            for (int i = 0; i < this.bitmapLength; ++i) {
                this.bitmap[i] = 0L;
            }
        }
        this.addToPool(poolSubpage);
    }

    long allocate() {
        if (this.elemSize == 0) {
            return this.toHandle(0);
        }
        if (this.numAvail == 0 || !this.doNotDestroy) {
            return -1L;
        }
        int n = this.getNextAvail();
        int n2 = n >>> 6;
        int n3 = n & 0x3F;
        if (!$assertionsDisabled && (this.bitmap[n2] >>> n3 & 1L) != 0L) {
            throw new AssertionError();
        }
        int n4 = n2;
        this.bitmap[n4] = this.bitmap[n4] | 1L << n3;
        if (--this.numAvail == 0) {
            this.removeFromPool();
        }
        return this.toHandle(n);
    }

    boolean free(PoolSubpage<T> poolSubpage, int n) {
        if (this.elemSize == 0) {
            return false;
        }
        int n2 = n >>> 6;
        int n3 = n & 0x3F;
        if (!$assertionsDisabled && (this.bitmap[n2] >>> n3 & 1L) == 0L) {
            throw new AssertionError();
        }
        int n4 = n2;
        this.bitmap[n4] = this.bitmap[n4] ^ 1L << n3;
        this.setNextAvail(n);
        if (this.numAvail++ == 0) {
            this.addToPool(poolSubpage);
            return false;
        }
        if (this.numAvail != this.maxNumElems) {
            return false;
        }
        if (this.prev == this.next) {
            return false;
        }
        this.doNotDestroy = false;
        this.removeFromPool();
        return true;
    }

    private void addToPool(PoolSubpage<T> poolSubpage) {
        if (!($assertionsDisabled || this.prev == null && this.next == null)) {
            throw new AssertionError();
        }
        this.prev = poolSubpage;
        this.next = poolSubpage.next;
        this.next.prev = this;
        poolSubpage.next = this;
    }

    private void removeFromPool() {
        if (!($assertionsDisabled || this.prev != null && this.next != null)) {
            throw new AssertionError();
        }
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
    }

    private void setNextAvail(int n) {
        this.nextAvail = n;
    }

    private int getNextAvail() {
        int n = this.nextAvail;
        if (n >= 0) {
            this.nextAvail = -1;
            return n;
        }
        return this.findNextAvail();
    }

    private int findNextAvail() {
        long[] lArray = this.bitmap;
        int n = this.bitmapLength;
        for (int i = 0; i < n; ++i) {
            long l = lArray[i];
            if ((l ^ 0xFFFFFFFFFFFFFFFFL) == 0L) continue;
            return this.findNextAvail0(i, l);
        }
        return 1;
    }

    private int findNextAvail0(int n, long l) {
        int n2 = this.maxNumElems;
        int n3 = n << 6;
        for (int i = 0; i < 64; ++i) {
            if ((l & 1L) == 0L) {
                int n4 = n3 | i;
                if (n4 >= n2) break;
                return n4;
            }
            l >>>= 1;
        }
        return 1;
    }

    private long toHandle(int n) {
        return 0x4000000000000000L | (long)n << 32 | (long)this.memoryMapIdx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        int n;
        int n2;
        int n3;
        boolean bl;
        PoolArena poolArena = this.chunk.arena;
        synchronized (poolArena) {
            if (!this.doNotDestroy) {
                bl = false;
                n3 = -1;
                n2 = -1;
                n = -1;
            } else {
                bl = true;
                n = this.maxNumElems;
                n2 = this.numAvail;
                n3 = this.elemSize;
            }
        }
        if (!bl) {
            return "(" + this.memoryMapIdx + ": not in use)";
        }
        return "(" + this.memoryMapIdx + ": " + (n - n2) + '/' + n + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + n3 + ')';
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int maxNumElements() {
        PoolArena poolArena = this.chunk.arena;
        synchronized (poolArena) {
            return this.maxNumElems;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int numAvailable() {
        PoolArena poolArena = this.chunk.arena;
        synchronized (poolArena) {
            return this.numAvail;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int elementSize() {
        PoolArena poolArena = this.chunk.arena;
        synchronized (poolArena) {
            return this.elemSize;
        }
    }

    @Override
    public int pageSize() {
        return this.pageSize;
    }

    void destroy() {
        if (this.chunk != null) {
            this.chunk.destroy();
        }
    }
}

