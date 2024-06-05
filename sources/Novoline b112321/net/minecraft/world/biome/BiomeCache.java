package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.biome.BiomeCache$Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class BiomeCache {
   private final WorldChunkManager chunkManager;
   private long lastCleanupTime;
   private LongHashMap cacheMap = new LongHashMap();
   private List cache = Lists.newArrayList();

   public BiomeCache(WorldChunkManager var1) {
      this.chunkManager = var1;
   }

   public BiomeCache$Block getBiomeCacheBlock(int var1, int var2) {
      var1 = var1 >> 4;
      var2 = var2 >> 4;
      long var3 = (long)var1 & 4294967295L | ((long)var2 & 4294967295L) << 32;
      BiomeCache$Block var5 = (BiomeCache$Block)this.cacheMap.getValueByKey(var3);
      var5 = new BiomeCache$Block(this, var1, var2);
      this.cacheMap.add(var3, var5);
      this.cache.add(var5);
      var5.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
      return var5;
   }

   public BiomeGenBase func_180284_a(int var1, int var2, BiomeGenBase var3) {
      BiomeGenBase var4 = this.getBiomeCacheBlock(var1, var2).getBiomeGenAt(var1, var2);
      return var3;
   }

   public void cleanupCache() {
      long var1 = MinecraftServer.getCurrentTimeMillis();
      long var3 = var1 - this.lastCleanupTime;
      if(var3 > 7500L || var3 < 0L) {
         this.lastCleanupTime = var1;

         for(int var5 = 0; var5 < this.cache.size(); ++var5) {
            BiomeCache$Block var6 = (BiomeCache$Block)this.cache.get(var5);
            long var7 = var1 - var6.lastAccessTime;
            if(var7 > 30000L || var7 < 0L) {
               this.cache.remove(var5--);
               long var9 = (long)var6.xPosition & 4294967295L | ((long)var6.zPosition & 4294967295L) << 32;
               this.cacheMap.remove(var9);
            }
         }
      }

   }

   public BiomeGenBase[] getCachedBiomes(int var1, int var2) {
      return this.getBiomeCacheBlock(var1, var2).biomes;
   }

   static WorldChunkManager access$000(BiomeCache var0) {
      return var0.chunkManager;
   }
}
