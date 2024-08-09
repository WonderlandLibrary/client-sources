/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.color;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class ColorCache {
    private final ThreadLocal<Entry> threadCacheEntry = ThreadLocal.withInitial(ColorCache::lambda$new$0);
    private final Long2ObjectLinkedOpenHashMap<int[]> cache = new Long2ObjectLinkedOpenHashMap(256, 0.25f);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int getColor(BlockPos blockPos, IntSupplier intSupplier) {
        int n;
        int n2 = blockPos.getX() >> 4;
        int n3 = blockPos.getZ() >> 4;
        Entry entry = this.threadCacheEntry.get();
        if (entry.chunkX != n2 || entry.chunkZ != n3) {
            entry.chunkX = n2;
            entry.chunkZ = n3;
            entry.colorCache = this.getChunkCache(n2, n3);
        }
        int n4 = blockPos.getX() & 0xF;
        int n5 = blockPos.getZ() & 0xF;
        int n6 = n5 << 4 | n4;
        int n7 = entry.colorCache[n6];
        if (n7 != -1) {
            return n7;
        }
        entry.colorCache[n6] = n = intSupplier.getAsInt();
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void invalidateChunk(int n, int n2) {
        try {
            this.lock.writeLock().lock();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    long l = ChunkPos.asLong(n + i, n2 + j);
                    this.cache.remove(l);
                }
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void invalidateAll() {
        try {
            this.lock.writeLock().lock();
            this.cache.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int[] getChunkCache(int n, int n2) {
        int[] nArray;
        long l = ChunkPos.asLong(n, n2);
        this.lock.readLock().lock();
        try {
            nArray = this.cache.get(l);
        } finally {
            this.lock.readLock().unlock();
        }
        if (nArray != null) {
            return nArray;
        }
        int[] nArray2 = new int[256];
        Arrays.fill(nArray2, -1);
        try {
            this.lock.writeLock().lock();
            if (this.cache.size() >= 256) {
                this.cache.removeFirst();
            }
            this.cache.put(l, nArray2);
        } finally {
            this.lock.writeLock().unlock();
        }
        return nArray2;
    }

    private static Entry lambda$new$0() {
        return new Entry();
    }

    static class Entry {
        public int chunkX = Integer.MIN_VALUE;
        public int chunkZ = Integer.MIN_VALUE;
        public int[] colorCache;

        private Entry() {
        }
    }
}

