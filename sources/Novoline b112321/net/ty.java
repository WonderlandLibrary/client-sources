package net;

import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;

public class ty {
   public static BiomeGenBase a(BiomeCache var0, int var1, int var2, BiomeGenBase var3) {
      return var0.func_180284_a(var1, var2, var3);
   }

   public static BiomeGenBase[] a(BiomeCache var0, int var1, int var2) {
      return var0.getCachedBiomes(var1, var2);
   }
}
