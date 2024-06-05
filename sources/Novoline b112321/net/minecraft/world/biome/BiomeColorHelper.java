package net.minecraft.world.biome;

import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper$1;
import net.minecraft.world.biome.BiomeColorHelper$2;
import net.minecraft.world.biome.BiomeColorHelper$3;
import net.minecraft.world.biome.BiomeColorHelper$ColorResolver;

public class BiomeColorHelper {
   private static final BiomeColorHelper$ColorResolver field_180291_a = new BiomeColorHelper$1();
   private static final BiomeColorHelper$ColorResolver field_180289_b = new BiomeColorHelper$2();
   private static final BiomeColorHelper$ColorResolver field_180290_c = new BiomeColorHelper$3();

   private static int func_180285_a(IBlockAccess var0, BlockPos var1, BiomeColorHelper$ColorResolver var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      for(BlockPos$MutableBlockPos var7 : BlockPos.getAllInBoxMutable(var1.a(-1, 0, -1), var1.a(1, 0, 1))) {
         int var8 = var2.getColorAtPos(var0.getBiomeGenForCoords(var7), var7);
         var3 += (var8 & 16711680) >> 16;
         var4 += (var8 & '\uff00') >> 8;
         var5 += var8 & 255;
      }

      return (var3 / 9 & 255) << 16 | (var4 / 9 & 255) << 8 | var5 / 9 & 255;
   }

   public static int getGrassColorAtPos(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180291_a);
   }

   public static int getFoliageColorAtPos(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180289_b);
   }

   public static int getWaterColorAtPos(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180290_c);
   }
}
