package net.minecraft.block;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public interface IGrowable
{
    boolean canUseBonemeal(final World p0, final Random p1, final BlockPos p2, final IBlockState p3);
    
    boolean canGrow(final World p0, final BlockPos p1, final IBlockState p2, final boolean p3);
    
    void grow(final World p0, final Random p1, final BlockPos p2, final IBlockState p3);
}
