package net;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.CustomColormap;
import net.optifine.MatchBlock;

public class ad6 {
   public static int a(CustomColormap var0, BiomeGenBase var1, BlockPos var2) {
      return var0.getColor(var1, var2);
   }

   public static int f(CustomColormap var0) {
      return var0.getWidth();
   }

   public static void a(CustomColormap var0, MatchBlock var1) {
      var0.addMatchBlock(var1);
   }

   public static boolean a(CustomColormap var0, String var1) {
      return var0.isValid(var1);
   }

   public static boolean b(CustomColormap var0, String var1) {
      return var0.isValidMatchBlocks(var1);
   }

   public static int[] b(CustomColormap var0) {
      return var0.getMatchBlockIds();
   }

   public static boolean d(CustomColormap var0) {
      return var0.isColorConstant();
   }

   public static int a(CustomColormap var0, IBlockAccess var1, BlockPos var2) {
      return var0.getColor(var1, var2);
   }

   public static boolean a(CustomColormap var0, BlockStateBase var1) {
      return var0.matchesBlock(var1);
   }

   public static int c(CustomColormap var0) {
      return var0.getColorRandom();
   }

   public static int a(CustomColormap var0, int var1) {
      return var0.getColor(var1);
   }

   public static int a(CustomColormap var0) {
      return var0.e();
   }

   public static int a(CustomColormap var0, IBlockAccess var1, double var2, double var4, double var6, int var8) {
      return var0.getColorSmooth(var1, var2, var4, var6, var8);
   }

   public static int g(CustomColormap var0) {
      return var0.getHeight();
   }

   public static float[][] e(CustomColormap var0) {
      return var0.getColorsRgb();
   }
}
