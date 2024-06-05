package net;

import java.util.List;
import java.util.Random;
import net.bh;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class z0 {
   public static boolean a(WorldChunkManager var0, int var1, int var2, int var3, List var4) {
      return var0.areBiomesViable(var1, var2, var3, var4);
   }

   public static BiomeGenBase a(WorldChunkManager var0, BlockPos var1, BiomeGenBase var2) {
      return var0.getBiomeGenerator(var1, var2);
   }

   public static float[] a(WorldChunkManager var0, float[] var1, int var2, int var3, int var4, int var5) {
      return var0.getRainfall(var1, var2, var3, var4, var5);
   }

   public static BiomeGenBase[] a(WorldChunkManager var0, BiomeGenBase[] var1, int var2, int var3, int var4, int var5, boolean var6) {
      int[] var7 = bh.b();
      return var0.getBiomeGenAt(var1, var2, var3, var4, var5, var6);
   }

   public static BiomeGenBase[] b(WorldChunkManager var0, BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      return var0.loadBlockGeneratorData(var1, var2, var3, var4, var5);
   }

   public static float a(WorldChunkManager var0, float var1, int var2) {
      return var0.getTemperatureAtHeight(var1, var2);
   }

   public static void a(WorldChunkManager var0) {
      var0.cleanupCache();
   }

   public static List b(WorldChunkManager var0) {
      return var0.getBiomesToSpawnIn();
   }

   public static BlockPos a(WorldChunkManager var0, int var1, int var2, int var3, List var4, Random var5) {
      return var0.findBiomePosition(var1, var2, var3, var4, var5);
   }

   public static BiomeGenBase[] a(WorldChunkManager var0, BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      return var0.getBiomesForGeneration(var1, var2, var3, var4, var5);
   }

   public static BiomeGenBase a(WorldChunkManager var0, BlockPos var1) {
      return var0.getBiomeGenerator(var1);
   }
}
