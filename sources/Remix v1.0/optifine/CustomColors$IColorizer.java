package optifine;

import net.minecraft.world.*;
import net.minecraft.util.*;

public interface IColorizer
{
    int getColor(final IBlockAccess p0, final BlockPos p1);
    
    boolean isColorConstant();
}
