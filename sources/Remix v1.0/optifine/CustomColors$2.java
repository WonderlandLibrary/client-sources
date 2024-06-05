package optifine;

import net.minecraft.util.*;
import net.minecraft.world.*;

static final class CustomColors$2 implements IColorizer {
    @Override
    public int getColor(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return (CustomColors.access$100() != null) ? CustomColors.access$100().getColor(blockAccess, blockPos) : ColorizerFoliage.getFoliageColorPine();
    }
    
    @Override
    public boolean isColorConstant() {
        return CustomColors.access$100() == null;
    }
}