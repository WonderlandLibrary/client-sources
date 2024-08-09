/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;

public class BreakableBlock
extends Block {
    protected BreakableBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        return blockState2.isIn(this) ? true : super.isSideInvisible(blockState, blockState2, direction);
    }
}

