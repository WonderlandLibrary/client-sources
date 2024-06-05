package optifine;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

static final class CustomColors$4 implements IColorizer {
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
        return (CustomColors.access$300() != null && biome == BiomeGenBase.swampland) ? CustomColors.access$300().getColor(biome, blockPos) : biome.func_180625_c(blockPos);
    }
    
    @Override
    public boolean isColorConstant() {
        return false;
    }
}