package net.optifine;

import net.acE;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.optifine.CustomColors;
import net.optifine.CustomColors$IColorizer;
import net.optifine.MatchBlock;

final class CustomColors$3 implements CustomColors$IColorizer {
   public int getColor(IBlockAccess var1, BlockPos var2) {
      acE[] var3 = MatchBlock.b();
      return CustomColors.access$200() != null?CustomColors.access$200().getColor(var1, var2):ColorizerFoliage.getFoliageColorPine();
   }

   public boolean isColorConstant() {
      return CustomColors.access$200() == null;
   }
}
