package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockBreakable extends Block
{
    private boolean ignoreSimilarity;
    
    protected BlockBreakable(final Material p_i45712_1_, final boolean p_i45712_2_) {
        super(p_i45712_1_);
        this.ignoreSimilarity = p_i45712_2_;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        final IBlockState var4 = worldIn.getBlockState(pos);
        final Block var5 = var4.getBlock();
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (worldIn.getBlockState(pos.offset(side.getOpposite())) != var4) {
                return true;
            }
            if (var5 == this) {
                return false;
            }
        }
        return (this.ignoreSimilarity || var5 != this) && super.shouldSideBeRendered(worldIn, pos, side);
    }
}
