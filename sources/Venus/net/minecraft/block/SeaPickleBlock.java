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
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
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

public class SeaPickleBlock
extends BushBlock
implements IGrowable,
IWaterLoggable {
    public static final IntegerProperty PICKLES = BlockStateProperties.PICKLES_1_4;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape ONE_SHAPE = Block.makeCuboidShape(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
    protected static final VoxelShape TWO_SHAPE = Block.makeCuboidShape(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
    protected static final VoxelShape THREE_SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
    protected static final VoxelShape FOUR_SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

    protected SeaPickleBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(PICKLES, 1)).with(WATERLOGGED, true));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockItemUseContext.getPos());
        if (blockState.isIn(this)) {
            return (BlockState)blockState.with(PICKLES, Math.min(4, blockState.get(PICKLES) + 1));
        }
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return (BlockState)super.getStateForPlacement(blockItemUseContext).with(WATERLOGGED, bl);
    }

    public static boolean isInBadEnvironment(BlockState blockState) {
        return blockState.get(WATERLOGGED) == false;
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return !blockState.getCollisionShape(iBlockReader, blockPos).project(Direction.UP).isEmpty() || blockState.isSolidSide(iBlockReader, blockPos, Direction.UP);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return this.isValidGround(iWorldReader.getBlockState(blockPos2), iWorldReader, blockPos2);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        return blockItemUseContext.getItem().getItem() == this.asItem() && blockState.get(PICKLES) < 4 ? true : super.isReplaceable(blockState, blockItemUseContext);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        switch (blockState.get(PICKLES)) {
            default: {
                return ONE_SHAPE;
            }
            case 2: {
                return TWO_SHAPE;
            }
            case 3: {
                return THREE_SHAPE;
            }
            case 4: 
        }
        return FOUR_SHAPE;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PICKLES, WATERLOGGED);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        if (!SeaPickleBlock.isInBadEnvironment(blockState) && serverWorld.getBlockState(blockPos.down()).isIn(BlockTags.CORAL_BLOCKS)) {
            int n = 5;
            int n2 = 1;
            int n3 = 2;
            int n4 = 0;
            int n5 = blockPos.getX() - 2;
            int n6 = 0;
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < n2; ++j) {
                    int n7 = 2 + blockPos.getY() - 1;
                    for (int k = n7 - 2; k < n7; ++k) {
                        BlockState blockState2;
                        BlockPos blockPos2 = new BlockPos(n5 + i, k, blockPos.getZ() - n6 + j);
                        if (blockPos2 == blockPos || random2.nextInt(6) != 0 || !serverWorld.getBlockState(blockPos2).isIn(Blocks.WATER) || !(blockState2 = serverWorld.getBlockState(blockPos2.down())).isIn(BlockTags.CORAL_BLOCKS)) continue;
                        serverWorld.setBlockState(blockPos2, (BlockState)Blocks.SEA_PICKLE.getDefaultState().with(PICKLES, random2.nextInt(4) + 1), 0);
                    }
                }
                if (n4 < 2) {
                    n2 += 2;
                    ++n6;
                } else {
                    n2 -= 2;
                    --n6;
                }
                ++n4;
            }
            serverWorld.setBlockState(blockPos, (BlockState)blockState.with(PICKLES, 4), 1);
        }
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

