package net.minecraft.world.biome;

import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BiomeColorHelper {
   private static final BiomeColorHelper.ColorResolver field_180291_a = new BiomeColorHelper.ColorResolver() {
      private static final String __OBFID = "CL_00002148";

      public int func_180283_a(BiomeGenBase var1, BlockPos var2) {
         return var1.func_180627_b(var2);
      }
   };
   private static final String __OBFID = "CL_00002149";
   private static final BiomeColorHelper.ColorResolver field_180289_b = new BiomeColorHelper.ColorResolver() {
      private static final String __OBFID = "CL_00002147";

      public int func_180283_a(BiomeGenBase var1, BlockPos var2) {
         return var1.func_180625_c(var2);
      }
   };
   private static final BiomeColorHelper.ColorResolver field_180290_c = new BiomeColorHelper.ColorResolver() {
      private static final String __OBFID = "CL_00002146";

      public int func_180283_a(BiomeGenBase var1, BlockPos var2) {
         return var1.waterColorMultiplier;
      }
   };

   private static int func_180285_a(IBlockAccess var0, BlockPos var1, BiomeColorHelper.ColorResolver var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      int var6;
      for(Iterator var7 = BlockPos.getAllInBoxMutable(var1.add(-1, 0, -1), var1.add(1, 0, 1)).iterator(); var7.hasNext(); var5 += var6 & 255) {
         BlockPos.MutableBlockPos var8 = (BlockPos.MutableBlockPos)var7.next();
         var6 = var2.func_180283_a(var0.getBiomeGenForCoords(var8), var8);
         var3 += (var6 & 16711680) >> 16;
         var4 += (var6 & '\uff00') >> 8;
      }

      return (var3 / 9 & 255) << 16 | (var4 / 9 & 255) << 8 | var5 / 9 & 255;
   }

   public static int func_180287_b(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180289_b);
   }

   public static int func_180288_c(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180290_c);
   }

   public static int func_180286_a(IBlockAccess var0, BlockPos var1) {
      return func_180285_a(var0, var1, field_180291_a);
   }

   interface ColorResolver {
      int func_180283_a(BiomeGenBase var1, BlockPos var2);
   }
}
