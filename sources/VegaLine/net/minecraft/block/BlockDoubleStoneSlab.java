/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockDoubleStoneSlab
extends BlockStoneSlab {
    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.SOLID;
    }
}

