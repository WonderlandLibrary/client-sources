package net.minecraft.world.biome;

import net.z0;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeCache$Block {
   public float[] rainfallValues;
   public BiomeGenBase[] biomes;
   public int xPosition;
   public int zPosition;
   public long lastAccessTime;
   final BiomeCache this$0;

   public BiomeCache$Block(BiomeCache var1, int var2, int var3) {
      this.this$0 = var1;
      this.rainfallValues = new float[256];
      this.biomes = new BiomeGenBase[256];
      this.xPosition = var2;
      this.zPosition = var3;
      BiomeCache.access$000(var1).getRainfall(this.rainfallValues, var2 << 4, var3 << 4, 16, 16);
      z0.a(BiomeCache.access$000(var1), this.biomes, var2 << 4, var3 << 4, 16, 16, false);
   }

   public BiomeGenBase getBiomeGenAt(int var1, int var2) {
      return this.biomes[var1 & 15 | (var2 & 15) << 4];
   }
}
