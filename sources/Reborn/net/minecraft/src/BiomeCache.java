package net.minecraft.src;

import java.util.*;

public class BiomeCache
{
    private final WorldChunkManager chunkManager;
    private long lastCleanupTime;
    private LongHashMap cacheMap;
    private List cache;
    
    public BiomeCache(final WorldChunkManager par1WorldChunkManager) {
        this.lastCleanupTime = 0L;
        this.cacheMap = new LongHashMap();
        this.cache = new ArrayList();
        this.chunkManager = par1WorldChunkManager;
    }
    
    public BiomeCacheBlock getBiomeCacheBlock(int par1, int par2) {
        par1 >>= 4;
        par2 >>= 4;
        final long var3 = (par1 & 0xFFFFFFFFL) | (par2 & 0xFFFFFFFFL) << 32;
        BiomeCacheBlock var4 = (BiomeCacheBlock)this.cacheMap.getValueByKey(var3);
        if (var4 == null) {
            var4 = new BiomeCacheBlock(this, par1, par2);
            this.cacheMap.add(var3, var4);
            this.cache.add(var4);
        }
        var4.lastAccessTime = System.currentTimeMillis();
        return var4;
    }
    
    public BiomeGenBase getBiomeGenAt(final int par1, final int par2) {
        return this.getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
    }
    
    public void cleanupCache() {
        final long var1 = System.currentTimeMillis();
        final long var2 = var1 - this.lastCleanupTime;
        if (var2 > 7500L || var2 < 0L) {
            this.lastCleanupTime = var1;
            for (int var3 = 0; var3 < this.cache.size(); ++var3) {
                final BiomeCacheBlock var4 = this.cache.get(var3);
                final long var5 = var1 - var4.lastAccessTime;
                if (var5 > 30000L || var5 < 0L) {
                    this.cache.remove(var3--);
                    final long var6 = (var4.xPosition & 0xFFFFFFFFL) | (var4.zPosition & 0xFFFFFFFFL) << 32;
                    this.cacheMap.remove(var6);
                }
            }
        }
    }
    
    public BiomeGenBase[] getCachedBiomes(final int par1, final int par2) {
        return this.getBiomeCacheBlock(par1, par2).biomes;
    }
    
    static WorldChunkManager getChunkManager(final BiomeCache par0BiomeCache) {
        return par0BiomeCache.chunkManager;
    }
}
