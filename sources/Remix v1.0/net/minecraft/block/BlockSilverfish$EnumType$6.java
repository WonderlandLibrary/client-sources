package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;

enum BlockSilverfish$EnumType$6
{
    @Override
    public IBlockState func_176883_d() {
        return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CHISELED);
    }
}