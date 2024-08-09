/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;

public class GlazedTerracottaBlock
extends HorizontalBlock {
    public GlazedTerracottaBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.PUSH_ONLY;
    }
}

