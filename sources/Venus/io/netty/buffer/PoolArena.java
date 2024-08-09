/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.PoolArenaMetric;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PoolChunkList;
import io.netty.buffer.PoolChunkListMetric;
import io.netty.buffer.PoolChunkMetric;
import io.netty.buffer.PoolSubpage;
import io.netty.buffer.PoolSubpageMetric;
import io.netty.buffer.PoolThreadCache;
import io.netty.buffer.PooledByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.PooledDirectByteBuf;
import io.netty.buffer.PooledHeapByteBuf;
import io.netty.buffer.PooledUnsafeDirectByteBuf;
import io.netty.buffer.PooledUnsafeHeapByteBuf;
import io.netty.util.internal.LongCounter;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PoolArena<T>
implements PoolArenaMetric {
    static final boolean HAS_UNSAFE;
    static final int numTinySubpagePools = 32;
    final PooledByteBufAllocator parent;
    private final int maxOrder;
    final int pageSize;
    final int pageShifts;
    final int chunkSize;
    final int subpageOverflowMask;
    final int numSmallSubpagePools;
    final int directMemoryCacheAlignment;
    final int directMemoryCacheAlignmentMask;
    private final PoolSubpage<T>[] tinySubpagePools;
    private final PoolSubpage<T>[] smallSubpagePools;
    private final PoolChunkList<T> q050;
    private final PoolChunkList<T> q025;
    private final PoolChunkList<T> q000;
    private final PoolChunkList<T> qInit;
    private final PoolChunkList<T> q075;
    private final PoolChunkList<T> q100;
    private final List<PoolChunkListMetric> chunkListMetrics;
    private long allocationsNormal;
    private final LongCounter allocationsTiny = PlatformDependent.newLongCounter();
    private final LongCounter allocationsSmall = PlatformDependent.newLongCounter();
    private final LongCounter allocationsHuge = PlatformDependent.newLongCounter();
    private final LongCounter activeBytesHuge = PlatformDependent.newLongCounter();
    private long deallocationsTiny;
    private long deallocationsSmall;
    private long deallocationsNormal;
    private final LongCounter deallocationsHuge = PlatformDependent.newLongCounter();
    final AtomicInteger numThreadCaches = new AtomicInteger();
    static final boolean $assertionsDisabled;

    protected PoolArena(PooledByteBufAllocator pooledByteBufAllocator, int n, int n2, int n3, int n4, int n5) {
        int n6;
        this.parent = pooledByteBufAllocator;
        this.pageSize = n;
        this.maxOrder = n2;
        this.pageShifts = n3;
        this.chunkSize = n4;
        this.directMemoryCacheAlignment = n5;
        this.directMemoryCacheAlignmentMask = n5 - 1;
        this.subpageOverflowMask = ~(n - 1);
        this.tinySubpagePools = this.newSubpagePoolArray(32);
        for (n6 = 0; n6 < this.tinySubpagePools.length; ++n6) {
            this.tinySubpagePools[n6] = this.newSubpagePoolHead(n);
        }
        this.numSmallSubpagePools = n3 - 9;
        this.smallSubpagePools = this.newSubpagePoolArray(this.numSmallSubpagePools);
        for (n6 = 0; n6 < this.smallSubpagePools.length; ++n6) {
            this.smallSubpagePools[n6] = this.newSubpagePoolHead(n);
        }
        this.q100 = new PoolChunkList(this, null, 100, Integer.MAX_VALUE, n4);
        this.q075 = new PoolChunkList<T>(this, this.q100, 75, 100, n4);
        this.q050 = new PoolChunkList<T>(this, this.q075, 50, 100, n4);
        this.q025 = new PoolChunkList<T>(this, this.q050, 25, 75, n4);
        this.q000 = new PoolChunkList<T>(this, this.q025, 1, 50, n4);
        this.qInit = new PoolChunkList<T>(this, this.q000, Integer.MIN_VALUE, 25, n4);
        this.q100.prevList(this.q075);
        this.q075.prevList(this.q050);
        this.q050.prevList(this.q025);
        this.q025.prevList(this.q000);
        this.q000.prevList(null);
        this.qInit.prevList(this.qInit);
        ArrayList<PoolChunkList<T>> arrayList = new ArrayList<PoolChunkList<T>>(6);
        arrayList.add(this.qInit);
        arrayList.add(this.q000);
        arrayList.add(this.q025);
        arrayList.add(this.q050);
        arrayList.add(this.q075);
        arrayList.add(this.q100);
        this.chunkListMetrics = Collections.unmodifiableList(arrayList);
    }

    private PoolSubpage<T> newSubpagePoolHead(int n) {
        PoolSubpage poolSubpage = new PoolSubpage(n);
        poolSubpage.prev = poolSubpage;
        poolSubpage.next = poolSubpage;
        return poolSubpage;
    }

    private PoolSubpage<T>[] newSubpagePoolArray(int n) {
        return new PoolSubpage[n];
    }

    abstract boolean isDirect();

    PooledByteBuf<T> allocate(PoolThreadCache poolThreadCache, int n, int n2) {
        PooledByteBuf<T> pooledByteBuf = this.newByteBuf(n2);
        this.allocate(poolThreadCache, pooledByteBuf, n);
        return pooledByteBuf;
    }

    static int tinyIdx(int n) {
        return n >>> 4;
    }

    static int smallIdx(int n) {
        int n2 = 0;
        int n3 = n >>> 10;
        while (n3 != 0) {
            n3 >>>= 1;
            ++n2;
        }
        return n2;
    }

    boolean isTinyOrSmall(int n) {
        return (n & this.subpageOverflowMask) == 0;
    }

    static boolean isTiny(int n) {
        return (n & 0xFFFFFE00) == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void allocate(PoolThreadCache poolThreadCache, PooledByteBuf<T> pooledByteBuf, int n) {
        int n2 = this.normalizeCapacity(n);
        if (this.isTinyOrSmall(n2)) {
            PoolSubpage<T>[] poolSubpageArray;
            int n3;
            boolean bl = PoolArena.isTiny(n2);
            if (bl) {
                if (poolThreadCache.allocateTiny(this, pooledByteBuf, n, n2)) {
                    return;
                }
                n3 = PoolArena.tinyIdx(n2);
                poolSubpageArray = this.tinySubpagePools;
            } else {
                if (poolThreadCache.allocateSmall(this, pooledByteBuf, n, n2)) {
                    return;
                }
                n3 = PoolArena.smallIdx(n2);
                poolSubpageArray = this.smallSubpagePools;
            }
            PoolSubpage<T> poolSubpage = poolSubpageArray[n3];
            Object object = poolSubpage;
            synchronized (object) {
                PoolSubpage poolSubpage2 = poolSubpage.next;
                if (poolSubpage2 != poolSubpage) {
                    if (!($assertionsDisabled || poolSubpage2.doNotDestroy && poolSubpage2.elemSize == n2)) {
                        throw new AssertionError();
                    }
                    long l = poolSubpage2.allocate();
                    if (!$assertionsDisabled && l < 0L) {
                        throw new AssertionError();
                    }
                    poolSubpage2.chunk.initBufWithSubpage(pooledByteBuf, l, n);
                    this.incTinySmallAllocation(bl);
                    return;
                }
            }
            object = this;
            synchronized (object) {
                this.allocateNormal(pooledByteBuf, n, n2);
            }
            this.incTinySmallAllocation(bl);
            return;
        }
        if (n2 <= this.chunkSize) {
            if (poolThreadCache.allocateNormal(this, pooledByteBuf, n, n2)) {
                return;
            }
            PoolArena poolArena = this;
            synchronized (poolArena) {
                this.allocateNormal(pooledByteBuf, n, n2);
                ++this.allocationsNormal;
            }
        } else {
            this.allocateHuge(pooledByteBuf, n);
        }
    }

    private void allocateNormal(PooledByteBuf<T> pooledByteBuf, int n, int n2) {
        if (this.q050.allocate(pooledByteBuf, n, n2) || this.q025.allocate(pooledByteBuf, n, n2) || this.q000.allocate(pooledByteBuf, n, n2) || this.qInit.allocate(pooledByteBuf, n, n2) || this.q075.allocate(pooledByteBuf, n, n2)) {
            return;
        }
        PoolChunk<T> poolChunk = this.newChunk(this.pageSize, this.maxOrder, this.pageShifts, this.chunkSize);
        long l = poolChunk.allocate(n2);
        if (!$assertionsDisabled && l <= 0L) {
            throw new AssertionError();
        }
        poolChunk.initBuf(pooledByteBuf, l, n);
        this.qInit.add(poolChunk);
    }

    private void incTinySmallAllocation(boolean bl) {
        if (bl) {
            this.allocationsTiny.increment();
        } else {
            this.allocationsSmall.increment();
        }
    }

    private void allocateHuge(PooledByteBuf<T> pooledByteBuf, int n) {
        PoolChunk<T> poolChunk = this.newUnpooledChunk(n);
        this.activeBytesHuge.add(poolChunk.chunkSize());
        pooledByteBuf.initUnpooled(poolChunk, n);
        this.allocationsHuge.increment();
    }

    void free(PoolChunk<T> poolChunk, long l, int n, PoolThreadCache poolThreadCache) {
        if (poolChunk.unpooled) {
            int n2 = poolChunk.chunkSize();
            this.destroyChunk(poolChunk);
            this.activeBytesHuge.add(-n2);
            this.deallocationsHuge.increment();
        } else {
            SizeClass sizeClass = this.sizeClass(n);
            if (poolThreadCache != null && poolThreadCache.add(this, poolChunk, l, n, sizeClass)) {
                return;
            }
            this.freeChunk(poolChunk, l, sizeClass);
        }
    }

    private SizeClass sizeClass(int n) {
        if (!this.isTinyOrSmall(n)) {
            return SizeClass.Normal;
        }
        return PoolArena.isTiny(n) ? SizeClass.Tiny : SizeClass.Small;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void freeChunk(PoolChunk<T> poolChunk, long l, SizeClass sizeClass) {
        boolean bl;
        PoolArena poolArena = this;
        synchronized (poolArena) {
            switch (1.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()]) {
                case 1: {
                    ++this.deallocationsNormal;
                    break;
                }
                case 2: {
                    ++this.deallocationsSmall;
                    break;
                }
                case 3: {
                    ++this.deallocationsTiny;
                    break;
                }
                default: {
                    throw new Error();
                }
            }
            bl = !poolChunk.parent.free(poolChunk, l);
        }
        if (bl) {
            this.destroyChunk(poolChunk);
        }
    }

    PoolSubpage<T> findSubpagePoolHead(int n) {
        PoolSubpage<T>[] poolSubpageArray;
        int n2;
        if (PoolArena.isTiny(n)) {
            n2 = n >>> 4;
            poolSubpageArray = this.tinySubpagePools;
        } else {
            n2 = 0;
            n >>>= 10;
            while (n != 0) {
                n >>>= 1;
                ++n2;
            }
            poolSubpageArray = this.smallSubpagePools;
        }
        return poolSubpageArray[n2];
    }

    int normalizeCapacity(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("capacity: " + n + " (expected: 0+)");
        }
        if (n >= this.chunkSize) {
            return this.directMemoryCacheAlignment == 0 ? n : this.alignCapacity(n);
        }
        if (!PoolArena.isTiny(n)) {
            int n2 = n;
            --n2;
            n2 |= n2 >>> 1;
            n2 |= n2 >>> 2;
            n2 |= n2 >>> 4;
            n2 |= n2 >>> 8;
            n2 |= n2 >>> 16;
            if (++n2 < 0) {
                n2 >>>= 1;
            }
            if (!$assertionsDisabled && this.directMemoryCacheAlignment != 0 && (n2 & this.directMemoryCacheAlignmentMask) != 0) {
                throw new AssertionError();
            }
            return n2;
        }
        if (this.directMemoryCacheAlignment > 0) {
            return this.alignCapacity(n);
        }
        if ((n & 0xF) == 0) {
            return n;
        }
        return (n & 0xFFFFFFF0) + 16;
    }

    int alignCapacity(int n) {
        int n2 = n & this.directMemoryCacheAlignmentMask;
        return n2 == 0 ? n : n + this.directMemoryCacheAlignment - n2;
    }

    void reallocate(PooledByteBuf<T> pooledByteBuf, int n, boolean bl) {
        if (n < 0 || n > pooledByteBuf.maxCapacity()) {
            throw new IllegalArgumentException("newCapacity: " + n);
        }
        int n2 = pooledByteBuf.length;
        if (n2 == n) {
            return;
        }
        PoolChunk poolChunk = pooledByteBuf.chunk;
        long l = pooledByteBuf.handle;
        Object t = pooledByteBuf.memory;
        int n3 = pooledByteBuf.offset;
        int n4 = pooledByteBuf.maxLength;
        int n5 = pooledByteBuf.readerIndex();
        int n6 = pooledByteBuf.writerIndex();
        this.allocate(this.parent.threadCache(), pooledByteBuf, n);
        if (n > n2) {
            this.memoryCopy(t, n3, pooledByteBuf.memory, pooledByteBuf.offset, n2);
        } else if (n < n2) {
            if (n5 < n) {
                if (n6 > n) {
                    n6 = n;
                }
                this.memoryCopy(t, n3 + n5, pooledByteBuf.memory, pooledByteBuf.offset + n5, n6 - n5);
            } else {
                n5 = n6 = n;
            }
        }
        pooledByteBuf.setIndex(n5, n6);
        if (bl) {
            this.free(poolChunk, l, n4, pooledByteBuf.cache);
        }
    }

    @Override
    public int numThreadCaches() {
        return this.numThreadCaches.get();
    }

    @Override
    public int numTinySubpages() {
        return this.tinySubpagePools.length;
    }

    @Override
    public int numSmallSubpages() {
        return this.smallSubpagePools.length;
    }

    @Override
    public int numChunkLists() {
        return this.chunkListMetrics.size();
    }

    @Override
    public List<PoolSubpageMetric> tinySubpages() {
        return PoolArena.subPageMetricList(this.tinySubpagePools);
    }

    @Override
    public List<PoolSubpageMetric> smallSubpages() {
        return PoolArena.subPageMetricList(this.smallSubpagePools);
    }

    @Override
    public List<PoolChunkListMetric> chunkLists() {
        return this.chunkListMetrics;
    }

    private static List<PoolSubpageMetric> subPageMetricList(PoolSubpage<?>[] poolSubpageArray) {
        ArrayList<PoolSubpageMetric> arrayList = new ArrayList<PoolSubpageMetric>();
        for (PoolSubpage<?> poolSubpage : poolSubpageArray) {
            if (poolSubpage.next == poolSubpage) continue;
            PoolSubpage poolSubpage2 = poolSubpage.next;
            do {
                arrayList.add(poolSubpage2);
            } while ((poolSubpage2 = poolSubpage2.next) != poolSubpage);
        }
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long numAllocations() {
        long l;
        PoolArena poolArena = this;
        synchronized (poolArena) {
            l = this.allocationsNormal;
        }
        return this.allocationsTiny.value() + this.allocationsSmall.value() + l + this.allocationsHuge.value();
    }

    @Override
    public long numTinyAllocations() {
        return this.allocationsTiny.value();
    }

    @Override
    public long numSmallAllocations() {
        return this.allocationsSmall.value();
    }

    @Override
    public synchronized long numNormalAllocations() {
        return this.allocationsNormal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long numDeallocations() {
        long l;
        PoolArena poolArena = this;
        synchronized (poolArena) {
            l = this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal;
        }
        return l + this.deallocationsHuge.value();
    }

    @Override
    public synchronized long numTinyDeallocations() {
        return this.deallocationsTiny;
    }

    @Override
    public synchronized long numSmallDeallocations() {
        return this.deallocationsSmall;
    }

    @Override
    public synchronized long numNormalDeallocations() {
        return this.deallocationsNormal;
    }

    @Override
    public long numHugeAllocations() {
        return this.allocationsHuge.value();
    }

    @Override
    public long numHugeDeallocations() {
        return this.deallocationsHuge.value();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long numActiveAllocations() {
        long l = this.allocationsTiny.value() + this.allocationsSmall.value() + this.allocationsHuge.value() - this.deallocationsHuge.value();
        PoolArena poolArena = this;
        synchronized (poolArena) {
        }
        return Math.max(l += this.allocationsNormal - (this.deallocationsTiny + this.deallocationsSmall + this.deallocationsNormal), 0L);
    }

    @Override
    public long numActiveTinyAllocations() {
        return Math.max(this.numTinyAllocations() - this.numTinyDeallocations(), 0L);
    }

    @Override
    public long numActiveSmallAllocations() {
        return Math.max(this.numSmallAllocations() - this.numSmallDeallocations(), 0L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long numActiveNormalAllocations() {
        long l;
        PoolArena poolArena = this;
        synchronized (poolArena) {
            l = this.allocationsNormal - this.deallocationsNormal;
        }
        return Math.max(l, 0L);
    }

    @Override
    public long numActiveHugeAllocations() {
        return Math.max(this.numHugeAllocations() - this.numHugeDeallocations(), 0L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long numActiveBytes() {
        long l = this.activeBytesHuge.value();
        PoolArena poolArena = this;
        synchronized (poolArena) {
            for (int i = 0; i < this.chunkListMetrics.size(); ++i) {
                for (PoolChunkMetric poolChunkMetric : this.chunkListMetrics.get(i)) {
                    l += (long)poolChunkMetric.chunkSize();
                }
            }
        }
        return Math.max(0L, l);
    }

    protected abstract PoolChunk<T> newChunk(int var1, int var2, int var3, int var4);

    protected abstract PoolChunk<T> newUnpooledChunk(int var1);

    protected abstract PooledByteBuf<T> newByteBuf(int var1);

    protected abstract void memoryCopy(T var1, int var2, T var3, int var4, int var5);

    protected abstract void destroyChunk(PoolChunk<T> var1);

    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("Chunk(s) at 0~25%:").append(StringUtil.NEWLINE).append(this.qInit).append(StringUtil.NEWLINE).append("Chunk(s) at 0~50%:").append(StringUtil.NEWLINE).append(this.q000).append(StringUtil.NEWLINE).append("Chunk(s) at 25~75%:").append(StringUtil.NEWLINE).append(this.q025).append(StringUtil.NEWLINE).append("Chunk(s) at 50~100%:").append(StringUtil.NEWLINE).append(this.q050).append(StringUtil.NEWLINE).append("Chunk(s) at 75~100%:").append(StringUtil.NEWLINE).append(this.q075).append(StringUtil.NEWLINE).append("Chunk(s) at 100%:").append(StringUtil.NEWLINE).append(this.q100).append(StringUtil.NEWLINE).append("tiny subpages:");
        PoolArena.appendPoolSubPages(stringBuilder, this.tinySubpagePools);
        stringBuilder.append(StringUtil.NEWLINE).append("small subpages:");
        PoolArena.appendPoolSubPages(stringBuilder, this.smallSubpagePools);
        stringBuilder.append(StringUtil.NEWLINE);
        return stringBuilder.toString();
    }

    private static void appendPoolSubPages(StringBuilder stringBuilder, PoolSubpage<?>[] poolSubpageArray) {
        for (int i = 0; i < poolSubpageArray.length; ++i) {
            PoolSubpage<?> poolSubpage = poolSubpageArray[i];
            if (poolSubpage.next == poolSubpage) continue;
            stringBuilder.append(StringUtil.NEWLINE).append(i).append(": ");
            PoolSubpage poolSubpage2 = poolSubpage.next;
            do {
                stringBuilder.append(poolSubpage2);
            } while ((poolSubpage2 = poolSubpage2.next) != poolSubpage);
        }
    }

    protected final void finalize() throws Throwable {
        try {
            super.finalize();
        } catch (Throwable throwable) {
            PoolArena.destroyPoolSubPages(this.smallSubpagePools);
            PoolArena.destroyPoolSubPages(this.tinySubpagePools);
            this.destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
            throw throwable;
        }
        PoolArena.destroyPoolSubPages(this.smallSubpagePools);
        PoolArena.destroyPoolSubPages(this.tinySubpagePools);
        this.destroyPoolChunkLists(this.qInit, this.q000, this.q025, this.q050, this.q075, this.q100);
    }

    private static void destroyPoolSubPages(PoolSubpage<?>[] poolSubpageArray) {
        for (PoolSubpage<?> poolSubpage : poolSubpageArray) {
            poolSubpage.destroy();
        }
    }

    private void destroyPoolChunkLists(PoolChunkList<T> ... poolChunkListArray) {
        for (PoolChunkList<T> poolChunkList : poolChunkListArray) {
            poolChunkList.destroy(this);
        }
    }

    static {
        $assertionsDisabled = !PoolArena.class.desiredAssertionStatus();
        HAS_UNSAFE = PlatformDependent.hasUnsafe();
    }

    static final class DirectArena
    extends PoolArena<ByteBuffer> {
        DirectArena(PooledByteBufAllocator pooledByteBufAllocator, int n, int n2, int n3, int n4, int n5) {
            super(pooledByteBufAllocator, n, n2, n3, n4, n5);
        }

        @Override
        boolean isDirect() {
            return false;
        }

        private int offsetCacheLine(ByteBuffer byteBuffer) {
            return HAS_UNSAFE ? (int)(PlatformDependent.directBufferAddress(byteBuffer) & (long)this.directMemoryCacheAlignmentMask) : 0;
        }

        @Override
        protected PoolChunk<ByteBuffer> newChunk(int n, int n2, int n3, int n4) {
            if (this.directMemoryCacheAlignment == 0) {
                return new PoolChunk<ByteBuffer>(this, DirectArena.allocateDirect(n4), n, n2, n3, n4, 0);
            }
            ByteBuffer byteBuffer = DirectArena.allocateDirect(n4 + this.directMemoryCacheAlignment);
            return new PoolChunk<ByteBuffer>(this, byteBuffer, n, n2, n3, n4, this.offsetCacheLine(byteBuffer));
        }

        @Override
        protected PoolChunk<ByteBuffer> newUnpooledChunk(int n) {
            if (this.directMemoryCacheAlignment == 0) {
                return new PoolChunk<ByteBuffer>(this, DirectArena.allocateDirect(n), n, 0);
            }
            ByteBuffer byteBuffer = DirectArena.allocateDirect(n + this.directMemoryCacheAlignment);
            return new PoolChunk<ByteBuffer>(this, byteBuffer, n, this.offsetCacheLine(byteBuffer));
        }

        private static ByteBuffer allocateDirect(int n) {
            return PlatformDependent.useDirectBufferNoCleaner() ? PlatformDependent.allocateDirectNoCleaner(n) : ByteBuffer.allocateDirect(n);
        }

        @Override
        protected void destroyChunk(PoolChunk<ByteBuffer> poolChunk) {
            if (PlatformDependent.useDirectBufferNoCleaner()) {
                PlatformDependent.freeDirectNoCleaner((ByteBuffer)poolChunk.memory);
            } else {
                PlatformDependent.freeDirectBuffer((ByteBuffer)poolChunk.memory);
            }
        }

        @Override
        protected PooledByteBuf<ByteBuffer> newByteBuf(int n) {
            if (HAS_UNSAFE) {
                return PooledUnsafeDirectByteBuf.newInstance(n);
            }
            return PooledDirectByteBuf.newInstance(n);
        }

        @Override
        protected void memoryCopy(ByteBuffer byteBuffer, int n, ByteBuffer byteBuffer2, int n2, int n3) {
            if (n3 == 0) {
                return;
            }
            if (HAS_UNSAFE) {
                PlatformDependent.copyMemory(PlatformDependent.directBufferAddress(byteBuffer) + (long)n, PlatformDependent.directBufferAddress(byteBuffer2) + (long)n2, n3);
            } else {
                byteBuffer = byteBuffer.duplicate();
                byteBuffer2 = byteBuffer2.duplicate();
                byteBuffer.position(n).limit(n + n3);
                byteBuffer2.position(n2);
                byteBuffer2.put(byteBuffer);
            }
        }

        @Override
        protected void memoryCopy(Object object, int n, Object object2, int n2, int n3) {
            this.memoryCopy((ByteBuffer)object, n, (ByteBuffer)object2, n2, n3);
        }
    }

    static final class HeapArena
    extends PoolArena<byte[]> {
        HeapArena(PooledByteBufAllocator pooledByteBufAllocator, int n, int n2, int n3, int n4, int n5) {
            super(pooledByteBufAllocator, n, n2, n3, n4, n5);
        }

        private static byte[] newByteArray(int n) {
            return PlatformDependent.allocateUninitializedArray(n);
        }

        @Override
        boolean isDirect() {
            return true;
        }

        @Override
        protected PoolChunk<byte[]> newChunk(int n, int n2, int n3, int n4) {
            return new PoolChunk<byte[]>(this, HeapArena.newByteArray(n4), n, n2, n3, n4, 0);
        }

        @Override
        protected PoolChunk<byte[]> newUnpooledChunk(int n) {
            return new PoolChunk<byte[]>(this, HeapArena.newByteArray(n), n, 0);
        }

        @Override
        protected void destroyChunk(PoolChunk<byte[]> poolChunk) {
        }

        @Override
        protected PooledByteBuf<byte[]> newByteBuf(int n) {
            return HAS_UNSAFE ? PooledUnsafeHeapByteBuf.newUnsafeInstance(n) : PooledHeapByteBuf.newInstance(n);
        }

        @Override
        protected void memoryCopy(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
            if (n3 == 0) {
                return;
            }
            System.arraycopy(byArray, n, byArray2, n2, n3);
        }

        @Override
        protected void memoryCopy(Object object, int n, Object object2, int n2, int n3) {
            this.memoryCopy((byte[])object, n, (byte[])object2, n2, n3);
        }
    }

    static enum SizeClass {
        Tiny,
        Small,
        Normal;

    }
}

