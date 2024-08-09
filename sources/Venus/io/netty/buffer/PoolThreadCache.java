/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.PoolArena;
import io.netty.buffer.PoolChunk;
import io.netty.buffer.PooledByteBuf;
import io.netty.util.Recycler;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.Queue;

final class PoolThreadCache {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PoolThreadCache.class);
    final PoolArena<byte[]> heapArena;
    final PoolArena<ByteBuffer> directArena;
    private final MemoryRegionCache<byte[]>[] tinySubPageHeapCaches;
    private final MemoryRegionCache<byte[]>[] smallSubPageHeapCaches;
    private final MemoryRegionCache<ByteBuffer>[] tinySubPageDirectCaches;
    private final MemoryRegionCache<ByteBuffer>[] smallSubPageDirectCaches;
    private final MemoryRegionCache<byte[]>[] normalHeapCaches;
    private final MemoryRegionCache<ByteBuffer>[] normalDirectCaches;
    private final int numShiftsNormalDirect;
    private final int numShiftsNormalHeap;
    private final int freeSweepAllocationThreshold;
    private int allocations;

    PoolThreadCache(PoolArena<byte[]> poolArena, PoolArena<ByteBuffer> poolArena2, int n, int n2, int n3, int n4, int n5) {
        if (n4 < 0) {
            throw new IllegalArgumentException("maxCachedBufferCapacity: " + n4 + " (expected: >= 0)");
        }
        this.freeSweepAllocationThreshold = n5;
        this.heapArena = poolArena;
        this.directArena = poolArena2;
        if (poolArena2 != null) {
            this.tinySubPageDirectCaches = PoolThreadCache.createSubPageCaches(n, 32, PoolArena.SizeClass.Tiny);
            this.smallSubPageDirectCaches = PoolThreadCache.createSubPageCaches(n2, poolArena2.numSmallSubpagePools, PoolArena.SizeClass.Small);
            this.numShiftsNormalDirect = PoolThreadCache.log2(poolArena2.pageSize);
            this.normalDirectCaches = PoolThreadCache.createNormalCaches(n3, n4, poolArena2);
            poolArena2.numThreadCaches.getAndIncrement();
        } else {
            this.tinySubPageDirectCaches = null;
            this.smallSubPageDirectCaches = null;
            this.normalDirectCaches = null;
            this.numShiftsNormalDirect = -1;
        }
        if (poolArena != null) {
            this.tinySubPageHeapCaches = PoolThreadCache.createSubPageCaches(n, 32, PoolArena.SizeClass.Tiny);
            this.smallSubPageHeapCaches = PoolThreadCache.createSubPageCaches(n2, poolArena.numSmallSubpagePools, PoolArena.SizeClass.Small);
            this.numShiftsNormalHeap = PoolThreadCache.log2(poolArena.pageSize);
            this.normalHeapCaches = PoolThreadCache.createNormalCaches(n3, n4, poolArena);
            poolArena.numThreadCaches.getAndIncrement();
        } else {
            this.tinySubPageHeapCaches = null;
            this.smallSubPageHeapCaches = null;
            this.normalHeapCaches = null;
            this.numShiftsNormalHeap = -1;
        }
        if ((this.tinySubPageDirectCaches != null || this.smallSubPageDirectCaches != null || this.normalDirectCaches != null || this.tinySubPageHeapCaches != null || this.smallSubPageHeapCaches != null || this.normalHeapCaches != null) && n5 < 1) {
            throw new IllegalArgumentException("freeSweepAllocationThreshold: " + n5 + " (expected: > 0)");
        }
    }

    private static <T> MemoryRegionCache<T>[] createSubPageCaches(int n, int n2, PoolArena.SizeClass sizeClass) {
        if (n > 0 && n2 > 0) {
            MemoryRegionCache[] memoryRegionCacheArray = new MemoryRegionCache[n2];
            for (int i = 0; i < memoryRegionCacheArray.length; ++i) {
                memoryRegionCacheArray[i] = new SubPageMemoryRegionCache(n, sizeClass);
            }
            return memoryRegionCacheArray;
        }
        return null;
    }

    private static <T> MemoryRegionCache<T>[] createNormalCaches(int n, int n2, PoolArena<T> poolArena) {
        if (n > 0 && n2 > 0) {
            int n3 = Math.min(poolArena.chunkSize, n2);
            int n4 = Math.max(1, PoolThreadCache.log2(n3 / poolArena.pageSize) + 1);
            MemoryRegionCache[] memoryRegionCacheArray = new MemoryRegionCache[n4];
            for (int i = 0; i < memoryRegionCacheArray.length; ++i) {
                memoryRegionCacheArray[i] = new NormalMemoryRegionCache(n);
            }
            return memoryRegionCacheArray;
        }
        return null;
    }

    private static int log2(int n) {
        int n2 = 0;
        while (n > 1) {
            n >>= 1;
            ++n2;
        }
        return n2;
    }

    boolean allocateTiny(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int n, int n2) {
        return this.allocate(this.cacheForTiny(poolArena, n2), pooledByteBuf, n);
    }

    boolean allocateSmall(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int n, int n2) {
        return this.allocate(this.cacheForSmall(poolArena, n2), pooledByteBuf, n);
    }

    boolean allocateNormal(PoolArena<?> poolArena, PooledByteBuf<?> pooledByteBuf, int n, int n2) {
        return this.allocate(this.cacheForNormal(poolArena, n2), pooledByteBuf, n);
    }

    private boolean allocate(MemoryRegionCache<?> memoryRegionCache, PooledByteBuf pooledByteBuf, int n) {
        if (memoryRegionCache == null) {
            return true;
        }
        boolean bl = memoryRegionCache.allocate(pooledByteBuf, n);
        if (++this.allocations >= this.freeSweepAllocationThreshold) {
            this.allocations = 0;
            this.trim();
        }
        return bl;
    }

    boolean add(PoolArena<?> poolArena, PoolChunk poolChunk, long l, int n, PoolArena.SizeClass sizeClass) {
        MemoryRegionCache<?> memoryRegionCache = this.cache(poolArena, n, sizeClass);
        if (memoryRegionCache == null) {
            return true;
        }
        return memoryRegionCache.add(poolChunk, l);
    }

    private MemoryRegionCache<?> cache(PoolArena<?> poolArena, int n, PoolArena.SizeClass sizeClass) {
        switch (1.$SwitchMap$io$netty$buffer$PoolArena$SizeClass[sizeClass.ordinal()]) {
            case 1: {
                return this.cacheForNormal(poolArena, n);
            }
            case 2: {
                return this.cacheForSmall(poolArena, n);
            }
            case 3: {
                return this.cacheForTiny(poolArena, n);
            }
        }
        throw new Error();
    }

    void free() {
        int n = PoolThreadCache.free(this.tinySubPageDirectCaches) + PoolThreadCache.free(this.smallSubPageDirectCaches) + PoolThreadCache.free(this.normalDirectCaches) + PoolThreadCache.free(this.tinySubPageHeapCaches) + PoolThreadCache.free(this.smallSubPageHeapCaches) + PoolThreadCache.free(this.normalHeapCaches);
        if (n > 0 && logger.isDebugEnabled()) {
            logger.debug("Freed {} thread-local buffer(s) from thread: {}", (Object)n, (Object)Thread.currentThread().getName());
        }
        if (this.directArena != null) {
            this.directArena.numThreadCaches.getAndDecrement();
        }
        if (this.heapArena != null) {
            this.heapArena.numThreadCaches.getAndDecrement();
        }
    }

    private static int free(MemoryRegionCache<?>[] memoryRegionCacheArray) {
        if (memoryRegionCacheArray == null) {
            return 1;
        }
        int n = 0;
        for (MemoryRegionCache<?> memoryRegionCache : memoryRegionCacheArray) {
            n += PoolThreadCache.free(memoryRegionCache);
        }
        return n;
    }

    private static int free(MemoryRegionCache<?> memoryRegionCache) {
        if (memoryRegionCache == null) {
            return 1;
        }
        return memoryRegionCache.free();
    }

    void trim() {
        PoolThreadCache.trim(this.tinySubPageDirectCaches);
        PoolThreadCache.trim(this.smallSubPageDirectCaches);
        PoolThreadCache.trim(this.normalDirectCaches);
        PoolThreadCache.trim(this.tinySubPageHeapCaches);
        PoolThreadCache.trim(this.smallSubPageHeapCaches);
        PoolThreadCache.trim(this.normalHeapCaches);
    }

    private static void trim(MemoryRegionCache<?>[] memoryRegionCacheArray) {
        if (memoryRegionCacheArray == null) {
            return;
        }
        for (MemoryRegionCache<?> memoryRegionCache : memoryRegionCacheArray) {
            PoolThreadCache.trim(memoryRegionCache);
        }
    }

    private static void trim(MemoryRegionCache<?> memoryRegionCache) {
        if (memoryRegionCache == null) {
            return;
        }
        memoryRegionCache.trim();
    }

    private MemoryRegionCache<?> cacheForTiny(PoolArena<?> poolArena, int n) {
        int n2 = PoolArena.tinyIdx(n);
        if (poolArena.isDirect()) {
            return PoolThreadCache.cache(this.tinySubPageDirectCaches, n2);
        }
        return PoolThreadCache.cache(this.tinySubPageHeapCaches, n2);
    }

    private MemoryRegionCache<?> cacheForSmall(PoolArena<?> poolArena, int n) {
        int n2 = PoolArena.smallIdx(n);
        if (poolArena.isDirect()) {
            return PoolThreadCache.cache(this.smallSubPageDirectCaches, n2);
        }
        return PoolThreadCache.cache(this.smallSubPageHeapCaches, n2);
    }

    private MemoryRegionCache<?> cacheForNormal(PoolArena<?> poolArena, int n) {
        if (poolArena.isDirect()) {
            int n2 = PoolThreadCache.log2(n >> this.numShiftsNormalDirect);
            return PoolThreadCache.cache(this.normalDirectCaches, n2);
        }
        int n3 = PoolThreadCache.log2(n >> this.numShiftsNormalHeap);
        return PoolThreadCache.cache(this.normalHeapCaches, n3);
    }

    private static <T> MemoryRegionCache<T> cache(MemoryRegionCache<T>[] memoryRegionCacheArray, int n) {
        if (memoryRegionCacheArray == null || n > memoryRegionCacheArray.length - 1) {
            return null;
        }
        return memoryRegionCacheArray[n];
    }

    private static abstract class MemoryRegionCache<T> {
        private final int size;
        private final Queue<Entry<T>> queue;
        private final PoolArena.SizeClass sizeClass;
        private int allocations;
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>(){

            @Override
            protected Entry newObject(Recycler.Handle<Entry> handle) {
                return new Entry(handle);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };

        MemoryRegionCache(int n, PoolArena.SizeClass sizeClass) {
            this.size = MathUtil.safeFindNextPositivePowerOfTwo(n);
            this.queue = PlatformDependent.newFixedMpscQueue(this.size);
            this.sizeClass = sizeClass;
        }

        protected abstract void initBuf(PoolChunk<T> var1, long var2, PooledByteBuf<T> var4, int var5);

        public final boolean add(PoolChunk<T> poolChunk, long l) {
            Entry entry = MemoryRegionCache.newEntry(poolChunk, l);
            boolean bl = this.queue.offer(entry);
            if (!bl) {
                entry.recycle();
            }
            return bl;
        }

        public final boolean allocate(PooledByteBuf<T> pooledByteBuf, int n) {
            Entry<T> entry = this.queue.poll();
            if (entry == null) {
                return true;
            }
            this.initBuf(entry.chunk, entry.handle, pooledByteBuf, n);
            entry.recycle();
            ++this.allocations;
            return false;
        }

        public final int free() {
            return this.free(Integer.MAX_VALUE);
        }

        private int free(int n) {
            int n2;
            for (n2 = 0; n2 < n; ++n2) {
                Entry<T> entry = this.queue.poll();
                if (entry == null) {
                    return n2;
                }
                this.freeEntry(entry);
            }
            return n2;
        }

        public final void trim() {
            int n = this.size - this.allocations;
            this.allocations = 0;
            if (n > 0) {
                this.free(n);
            }
        }

        private void freeEntry(Entry entry) {
            PoolChunk poolChunk = entry.chunk;
            long l = entry.handle;
            entry.recycle();
            poolChunk.arena.freeChunk(poolChunk, l, this.sizeClass);
        }

        private static Entry newEntry(PoolChunk<?> poolChunk, long l) {
            Entry entry = RECYCLER.get();
            entry.chunk = poolChunk;
            entry.handle = l;
            return entry;
        }

        static final class Entry<T> {
            final Recycler.Handle<Entry<?>> recyclerHandle;
            PoolChunk<T> chunk;
            long handle = -1L;

            Entry(Recycler.Handle<Entry<?>> handle) {
                this.recyclerHandle = handle;
            }

            void recycle() {
                this.chunk = null;
                this.handle = -1L;
                this.recyclerHandle.recycle(this);
            }
        }
    }

    private static final class NormalMemoryRegionCache<T>
    extends MemoryRegionCache<T> {
        NormalMemoryRegionCache(int n) {
            super(n, PoolArena.SizeClass.Normal);
        }

        @Override
        protected void initBuf(PoolChunk<T> poolChunk, long l, PooledByteBuf<T> pooledByteBuf, int n) {
            poolChunk.initBuf(pooledByteBuf, l, n);
        }
    }

    private static final class SubPageMemoryRegionCache<T>
    extends MemoryRegionCache<T> {
        SubPageMemoryRegionCache(int n, PoolArena.SizeClass sizeClass) {
            super(n, sizeClass);
        }

        @Override
        protected void initBuf(PoolChunk<T> poolChunk, long l, PooledByteBuf<T> pooledByteBuf, int n) {
            poolChunk.initBufWithSubpage(pooledByteBuf, l, n);
        }
    }
}

