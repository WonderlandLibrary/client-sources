package net.minecraft.world.biome;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.util.BlockPos;

public class WorldChunkManagerHell extends WorldChunkManager {
   private BiomeGenBase biomeGenerator;
   private static final String __OBFID = "CL_00000169";
   private float rainfall;

   public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.biomeGenerator);
      return var1;
   }

   public float[] getRainfall(float[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new float[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.rainfall);
      return var1;
   }

   public WorldChunkManagerHell(BiomeGenBase var1, float var2) {
      this.biomeGenerator = var1;
      this.rainfall = var2;
   }

   public boolean areBiomesViable(int var1, int var2, int var3, List var4) {
      return var4.contains(this.biomeGenerator);
   }

   public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] var1, int var2, int var3, int var4, int var5) {
      if (var1 == null || var1.length < var4 * var5) {
         var1 = new BiomeGenBase[var4 * var5];
      }

      Arrays.fill(var1, 0, var4 * var5, this.biomeGenerator);
      return var1;
   }

   public BiomeGenBase func_180631_a(BlockPos var1) {
      return this.biomeGenerator;
   }

   public BlockPos findBiomePosition(int var1, int var2, int var3, List var4, Random var5) {
      return var4.contains(this.biomeGenerator) ? new BlockPos(var1 - var3 + var5.nextInt(var3 * 2 + 1), 0, var2 - var3 + var5.nextInt(var3 * 2 + 1)) : null;
   }

   public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] var1, int var2, int var3, int var4, int var5, boolean var6) {
      return this.loadBlockGeneratorData(var1, var2, var3, var4, var5);
   }
}
