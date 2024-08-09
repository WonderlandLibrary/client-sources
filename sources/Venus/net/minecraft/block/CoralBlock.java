/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

public class CoralBlock
extends Block {
    private final Block deadBlock;

    public CoralBlock(Block block, AbstractBlock.Properties properties) {
        super(properties);
        this.deadBlock = block;
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!this.canLive(serverWorld, blockPos)) {
            serverWorld.setBlockState(blockPos, this.deadBlock.getDefaultState(), 1);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!this.canLive(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 60 + iWorld.getRandom().nextInt(40));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    protected boolean canLive(IBlockReader iBlockReader, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            FluidState fluidState = iBlockReader.getFluidState(blockPos.offset(direction));
            if (!fluidState.isTagged(FluidTags.WATER)) continue;
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        if (!this.canLive(blockItemUseContext.getWorld(), blockItemUseContext.getPos())) {
            blockItemUseContext.getWorld().getPendingBlockTicks().scheduleTick(blockItemUseContext.getPos(), this, 60 + blockItemUseContext.getWorld().getRandom().nextInt(40));
        }
        return this.getDefaultState();
    }
}

