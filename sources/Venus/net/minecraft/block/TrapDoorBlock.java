/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class TrapDoorBlock
extends HorizontalBlock
implements IWaterLoggable {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape EAST_OPEN_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
    protected static final VoxelShape WEST_OPEN_AABB = Block.makeCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_OPEN_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape NORTH_OPEN_AABB = Block.makeCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape BOTTOM_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape TOP_AABB = Block.makeCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

    protected TrapDoorBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(OPEN, false)).with(HALF, Half.BOTTOM)).with(POWERED, false)).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        if (!blockState.get(OPEN).booleanValue()) {
            return blockState.get(HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
        }
        switch (blockState.get(HORIZONTAL_FACING)) {
            default: {
                return NORTH_OPEN_AABB;
            }
            case SOUTH: {
                return SOUTH_OPEN_AABB;
            }
            case WEST: {
                return WEST_OPEN_AABB;
            }
            case EAST: 
        }
        return EAST_OPEN_AABB;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (pathType) {
            case LAND: {
                return blockState.get(OPEN);
            }
            case WATER: {
                return blockState.get(WATERLOGGED);
            }
            case AIR: {
                return blockState.get(OPEN);
            }
        }
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (this.material == Material.IRON) {
            return ActionResultType.PASS;
        }
        blockState = (BlockState)blockState.func_235896_a_(OPEN);
        world.setBlockState(blockPos, blockState, 1);
        if (blockState.get(WATERLOGGED).booleanValue()) {
            world.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        this.playSound(playerEntity, world, blockPos, blockState.get(OPEN));
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    protected void playSound(@Nullable PlayerEntity playerEntity, World world, BlockPos blockPos, boolean bl) {
        if (bl) {
            int n = this.material == Material.IRON ? 1037 : 1007;
            world.playEvent(playerEntity, n, blockPos, 0);
        } else {
            int n = this.material == Material.IRON ? 1036 : 1013;
            world.playEvent(playerEntity, n, blockPos, 0);
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        boolean bl2;
        if (!world.isRemote && (bl2 = world.isBlockPowered(blockPos)) != blockState.get(POWERED)) {
            if (blockState.get(OPEN) != bl2) {
                blockState = (BlockState)blockState.with(OPEN, bl2);
                this.playSound(null, world, blockPos, bl2);
            }
            world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl2), 1);
            if (blockState.get(WATERLOGGED).booleanValue()) {
                world.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        Direction direction = blockItemUseContext.getFace();
        blockState = !blockItemUseContext.replacingClickedOnBlock() && direction.getAxis().isHorizontal() ? (BlockState)((BlockState)blockState.with(HORIZONTAL_FACING, direction)).with(HALF, blockItemUseContext.getHitVec().y - (double)blockItemUseContext.getPos().getY() > 0.5 ? Half.TOP : Half.BOTTOM) : (BlockState)((BlockState)blockState.with(HORIZONTAL_FACING, blockItemUseContext.getPlacementHorizontalFacing().getOpposite())).with(HALF, direction == Direction.UP ? Half.BOTTOM : Half.TOP);
        if (blockItemUseContext.getWorld().isBlockPowered(blockItemUseContext.getPos())) {
            blockState = (BlockState)((BlockState)blockState.with(OPEN, true)).with(POWERED, true);
        }
        return (BlockState)blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN, HALF, POWERED, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }
}

