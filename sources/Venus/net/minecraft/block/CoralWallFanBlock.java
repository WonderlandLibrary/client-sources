/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DeadCoralWallFanBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CoralWallFanBlock
extends DeadCoralWallFanBlock {
    private final Block deadBlock;

    protected CoralWallFanBlock(Block block, AbstractBlock.Properties properties) {
        super(properties);
        this.deadBlock = block;
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        this.updateIfDry(blockState, world, blockPos);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!CoralWallFanBlock.isInWater(blockState, serverWorld, blockPos)) {
            serverWorld.setBlockState(blockPos, (BlockState)((BlockState)this.deadBlock.getDefaultState().with(WATERLOGGED, false)).with(FACING, blockState.get(FACING)), 1);
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction.getOpposite() == blockState.get(FACING) && !blockState.isValidPosition(iWorld, blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        this.updateIfDry(blockState, iWorld, blockPos);
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }
}

