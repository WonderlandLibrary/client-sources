package optifine;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

static final class CustomColors$5 implements IColorizer {
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final BiomeGenBase biome = CustomColors.getColorBiome(blockAccess, blockPos);
        return (CustomColors.access$400() != null && biome == BiomeGenBase.swampland) ? CustomColors.access$400().getColor(biome, blockPos) : biome.func_180627_b(blockPos);
    }
    
    @Override
    public boolean isColorConstant() {
        return false;
    }
}