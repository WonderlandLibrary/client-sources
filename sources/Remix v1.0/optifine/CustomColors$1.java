package optifine;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

static final class CustomColors$1 implements IColorizer {
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
        return (CustomColors.access$000() != null) ? CustomColors.access$000().getColor(biome, blockPos) : (Reflector.ForgeBiomeGenBase_getWaterColorMultiplier.exists() ? Reflector.callInt(biome, Reflector.ForgeBiomeGenBase_getWaterColorMultiplier, new Object[0]) : biome.waterColorMultiplier);
    }
    
    @Override
    public boolean isColorConstant() {
        return false;
    }
}