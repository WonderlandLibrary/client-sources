/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class SlabBlock
extends Block
implements IWaterLoggable {
    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    protected static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

    public SlabBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, false));
    }

    @Override
    public boolean isTransparent(BlockState blockState) {
        return blockState.get(TYPE) != SlabType.DOUBLE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        SlabType slabType = blockState.get(TYPE);
        switch (slabType) {
            case DOUBLE: {
                return VoxelShapes.fullCube();
            }
            case TOP: {
                return TOP_SHAPE;
            }
        }
        return BOTTOM_SHAPE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockPos blockPos = blockItemUseContext.getPos();
        BlockState blockState = blockItemUseContext.getWorld().getBlockState(blockPos);
        if (blockState.isIn(this)) {
            return (BlockState)((BlockState)blockState.with(TYPE, SlabType.DOUBLE)).with(WATERLOGGED, false);
        }
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockPos);
        BlockState blockState2 = (BlockState)((BlockState)this.getDefaultState().with(TYPE, SlabType.BOTTOM)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        Direction direction = blockItemUseContext.getFace();
        return !(direction == Direction.DOWN || direction != Direction.UP && blockItemUseContext.getHitVec().y - (double)blockPos.getY() > 0.5) ? blockState2 : (BlockState)blockState2.with(TYPE, SlabType.TOP);
    }

    @Override
    public boolean isReplaceable(BlockState blockState, BlockItemUseContext blockItemUseContext) {
        ItemStack itemStack = blockItemUseContext.getItem();
        SlabType slabType = blockState.get(TYPE);
        if (slabType != SlabType.DOUBLE && itemStack.getItem() == this.asItem()) {
            if (blockItemUseContext.replacingClickedOnBlock()) {
                boolean bl = blockItemUseContext.getHitVec().y - (double)blockItemUseContext.getPos().getY() > 0.5;
                Direction direction = blockItemUseContext.getFace();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                }
                return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
            }
            return false;
        }
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.get(WATERLOGGED) != false ? Fluids.WATER.getStillFluidState(true) : super.getFluidState(blockState);
    }

    @Override
    public boolean receiveFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return blockState.get(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.receiveFluid(iWorld, blockPos, blockState, fluidState) : false;
    }

    @Override
    public boolean canContainFluid(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return blockState.get(TYPE) != SlabType.DOUBLE ? IWaterLoggable.super.canContainFluid(iBlockReader, blockPos, blockState, fluid) : false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        switch (pathType) {
            case LAND: {
                return true;
            }
            case WATER: {
                return iBlockReader.getFluidState(blockPos).isTagged(FluidTags.WATER);
            }
            case AIR: {
                return true;
            }
        }
        return true;
    }
}

