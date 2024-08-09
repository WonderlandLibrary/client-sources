/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class GravelBlock
extends FallingBlock {
    public GravelBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public int getDustColor(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return 1;
    }
}

