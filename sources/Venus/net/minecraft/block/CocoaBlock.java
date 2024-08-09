/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CocoaBlock
extends HorizontalBlock
implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_2;
    protected static final VoxelShape[] COCOA_EAST_AABB = new VoxelShape[]{Block.makeCuboidShape(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), Block.makeCuboidShape(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), Block.makeCuboidShape(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)};
    protected static final VoxelShape[] COCOA_WEST_AABB = new VoxelShape[]{Block.makeCuboidShape(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), Block.makeCuboidShape(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), Block.makeCuboidShape(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)};
    protected static final VoxelShape[] COCOA_NORTH_AABB = new VoxelShape[]{Block.makeCuboidShape(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), Block.makeCuboidShape(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), Block.makeCuboidShape(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)};
    protected static final VoxelShape[] COCOA_SOUTH_AABB = new VoxelShape[]{Block.makeCuboidShape(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), Block.makeCuboidShape(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), Block.makeCuboidShape(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)};

    public CocoaBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(AGE, 0));
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        int n;
        if (serverWorld.rand.nextInt(5) == 0 && (n = blockState.get(AGE).intValue()) < 2) {
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, n + 1), 1);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        Block block = iWorldReader.getBlockState(blockPos.offset(blockState.get(HORIZONTAL_FACING))).getBlock();
        return block.isIn(BlockTags.JUNGLE_LOGS);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        int n = blockState.get(AGE);
        switch (1.$SwitchMap$net$minecraft$util$Direction[blockState.get(HORIZONTAL_FACING).ordinal()]) {
            case 1: {
                return COCOA_SOUTH_AABB[n];
            }
            default: {
                return COCOA_NORTH_AABB[n];
            }
            case 3: {
                return COCOA_WEST_AABB[n];
            }
            case 4: 
        }
        return COCOA_EAST_AABB[n];
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = this.getDefaultState();
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        for (Direction direction : blockItemUseContext.getNearestLookingDirections()) {
            if (!direction.getAxis().isHorizontal() || !(blockState = (BlockState)blockState.with(HORIZONTAL_FACING, direction)).isValidPosition(world, blockPos)) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return direction == blockState.get(HORIZONTAL_FACING) && !blockState.isValidPosition(iWorld, blockPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return blockState.get(AGE) < 2;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, blockState.get(AGE) + 1), 1);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, AGE);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

