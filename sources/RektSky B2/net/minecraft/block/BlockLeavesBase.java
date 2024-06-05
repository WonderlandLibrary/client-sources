package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockLeavesBase extends Block
{
    protected boolean fancyGraphics;
    
    protected BlockLeavesBase(final Material materialIn, final boolean fancyGraphics) {
        super(materialIn);
        this.fancyGraphics = fancyGraphics;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return (this.fancyGraphics || worldIn.getBlockState(pos).getBlock() != this) && super.shouldSideBeRendered(worldIn, pos, side);
    }
}
