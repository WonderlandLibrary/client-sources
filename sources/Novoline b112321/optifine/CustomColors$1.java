package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.CustomColors;
import optifine.CustomColors$IColorizer;
import optifine.MatchBlock;

final class CustomColors$1 implements CustomColors$IColorizer {
   public int getColor(IBlockAccess var1, BlockPos var2) {
      MatchBlock.b();
      BiomeGenBase var4 = CustomColors.getColorBiome(var1, var2);
      return CustomColors.access$000() != null && var4 == BiomeGenBase.swampland?CustomColors.access$000().getColor(var4, var2):var4.getGrassColorAtPos(var2);
   }

   public boolean isColorConstant() {
      return false;
   }
}
