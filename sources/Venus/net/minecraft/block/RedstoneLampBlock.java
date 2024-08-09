/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RedstoneLampBlock
extends Block {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public RedstoneLampBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)this.getDefaultState().with(LIT, false));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return (BlockState)this.getDefaultState().with(LIT, blockItemUseContext.getWorld().isBlockPowered(blockItemUseContext.getPos()));
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2;
        if (!world.isRemote && (bl2 = blockState.get(LIT).booleanValue()) != world.isBlockPowered(blockPos)) {
            if (bl2) {
                world.getPendingBlockTicks().scheduleTick(blockPos, this, 4);
            } else {
                world.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(LIT), 1);
            }
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(LIT).booleanValue() && !serverWorld.isBlockPowered(blockPos)) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(LIT), 1);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}

