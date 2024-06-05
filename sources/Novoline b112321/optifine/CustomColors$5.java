package optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.CustomColors;
import optifine.CustomColors$IColorizer;
import optifine.MatchBlock;
import optifine.Reflector;

final class CustomColors$5 implements CustomColors$IColorizer {
   public int getColor(IBlockAccess var1, BlockPos var2) {
      MatchBlock.b();
      BiomeGenBase var4 = CustomColors.getColorBiome(var1, var2);
      return CustomColors.access$400() != null?CustomColors.access$400().getColor(var4, var2):(Reflector.cY.d()?Reflector.e(var4, Reflector.cY, new Object[0]):var4.waterColorMultiplier);
   }

   public boolean isColorConstant() {
      return false;
   }
}
