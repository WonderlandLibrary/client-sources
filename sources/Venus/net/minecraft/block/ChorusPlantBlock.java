/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class ChorusPlantBlock
extends SixWayBlock {
    protected ChorusPlantBlock(AbstractBlock.Properties properties) {
        super(0.3125f, properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(UP, false)).with(DOWN, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.makeConnections(blockItemUseContext.getWorld(), blockItemUseContext.getPos());
    }

    public BlockState makeConnections(IBlockReader iBlockReader, BlockPos blockPos) {
        Block block = iBlockReader.getBlockState(blockPos.down()).getBlock();
        Block block2 = iBlockReader.getBlockState(blockPos.up()).getBlock();
        Block block3 = iBlockReader.getBlockState(blockPos.north()).getBlock();
        Block block4 = iBlockReader.getBlockState(blockPos.east()).getBlock();
        Block block5 = iBlockReader.getBlockState(blockPos.south()).getBlock();
        Block block6 = iBlockReader.getBlockState(blockPos.west()).getBlock();
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(DOWN, block == this || block == Blocks.CHORUS_FLOWER || block == Blocks.END_STONE)).with(UP, block2 == this || block2 == Blocks.CHORUS_FLOWER)).with(NORTH, block3 == this || block3 == Blocks.CHORUS_FLOWER)).with(EAST, block4 == this || block4 == Blocks.CHORUS_FLOWER)).with(SOUTH, block5 == this || block5 == Blocks.CHORUS_FLOWER)).with(WEST, block6 == this || block6 == Blocks.CHORUS_FLOWER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
            return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
        }
        boolean bl = blockState2.getBlock() == this || blockState2.isIn(Blocks.CHORUS_FLOWER) || direction == Direction.DOWN && blockState2.isIn(Blocks.END_STONE);
        return (BlockState)blockState.with((Property)FACING_TO_PROPERTY_MAP.get(direction), bl);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        boolean bl = !iWorldReader.getBlockState(blockPos.up()).isAir() && !blockState2.isAir();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            Block block = iWorldReader.getBlockState(blockPos2).getBlock();
            if (block != this) continue;
            if (bl) {
                return true;
            }
            Block block2 = iWorldReader.getBlockState(blockPos2.down()).getBlock();
            if (block2 != this && block2 != Blocks.END_STONE) continue;
            return false;
        }
        Block block = blockState2.getBlock();
        return block == this || block == Blocks.END_STONE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

