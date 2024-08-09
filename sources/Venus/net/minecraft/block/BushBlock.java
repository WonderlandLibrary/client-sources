/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class BushBlock
extends Block {
    protected BushBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isIn(Blocks.GRASS_BLOCK) || blockState.isIn(Blocks.DIRT) || blockState.isIn(Blocks.COARSE_DIRT) || blockState.isIn(Blocks.PODZOL) || blockState.isIn(Blocks.FARMLAND);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return this.isValidGround(iWorldReader.getBlockState(blockPos2), iWorldReader, blockPos2);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.getFluidState().isEmpty();
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return pathType == PathType.AIR && !this.canCollide ? true : super.allowsMovement(blockState, iBlockReader, blockPos, pathType);
    }
}

