package optifine;

import net.minecraft.util.*;
import net.minecraft.world.*;

static final class CustomColors$3 implements IColorizer {
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (CustomColors.access$200() != null) ? CustomColors.access$200().getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorBirch();
    }
    
    @Override
    public boolean isColorConstant() {
        return CustomColors.access$200() == null;
    }
}