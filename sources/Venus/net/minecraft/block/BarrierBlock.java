/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BarrierBlock
extends Block {
    protected BarrierBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return 1.0f;
    }
}

