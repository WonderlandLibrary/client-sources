package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;

enum BlockSilverfish$EnumType$1
{
    @Override
    public IBlockState func_176883_d() {
        return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE);
    }
}