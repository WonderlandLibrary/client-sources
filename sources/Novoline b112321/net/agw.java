package net;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.optifine.CustomColors$IColorizer;

public class agw {
   public static boolean a(CustomColors$IColorizer var0) {
      return var0.isColorConstant();
   }

   public static int a(CustomColors$IColorizer var0, IBlockAccess var1, BlockPos var2) {
      return var0.getColor(var1, var2);
   }
}
