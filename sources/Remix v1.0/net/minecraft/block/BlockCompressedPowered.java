package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockCompressedPowered extends BlockCompressed
{
    public BlockCompressedPowered(final MapColor p_i45416_1_) {
        super(p_i45416_1_);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return 15;
    }
}
