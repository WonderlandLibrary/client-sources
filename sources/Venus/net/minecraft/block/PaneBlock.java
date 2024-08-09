/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FourWayBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class PaneBlock
extends FourWayBlock {
    protected PaneBlock(AbstractBlock.Properties properties) {
        super(1.0f, 1.0f, 16.0f, 16.0f, 16.0f, properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.west();
        BlockPos blockPos5 = blockPos.east();
        BlockState blockState = world.getBlockState(blockPos2);
        BlockState blockState2 = world.getBlockState(blockPos3);
        BlockState blockState3 = world.getBlockState(blockPos4);
        BlockState blockState4 = world.getBlockState(blockPos5);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(NORTH, this.canAttachTo(blockState, blockState.isSolidSide(world, blockPos2, Direction.SOUTH)))).with(SOUTH, this.canAttachTo(blockState2, blockState2.isSolidSide(world, blockPos3, Direction.NORTH)))).with(WEST, this.canAttachTo(blockState3, blockState3.isSolidSide(world, blockPos4, Direction.EAST)))).with(EAST, this.canAttachTo(blockState4, blockState4.isSolidSide(world, blockPos5, Direction.WEST)))).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return direction.getAxis().isHorizontal() ? (BlockState)blockState.with((Property)FACING_TO_PROPERTY_MAP.get(direction), this.canAttachTo(blockState2, blockState2.isSolidSide(iWorld, blockPos2, direction.getOpposite()))) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        if (blockState2.isIn(this)) {
            if (!direction.getAxis().isHorizontal()) {
                return false;
            }
            if (((Boolean)blockState.get((Property)FACING_TO_PROPERTY_MAP.get(direction))).booleanValue() && ((Boolean)blockState2.get((Property)FACING_TO_PROPERTY_MAP.get(direction.getOpposite()))).booleanValue()) {
                return false;
            }
        }
        return super.isSideInvisible(blockState, blockState2, direction);
    }

    public final boolean canAttachTo(BlockState blockState, boolean bl) {
        Block block = blockState.getBlock();
        return !PaneBlock.cannotAttach(block) && bl || block instanceof PaneBlock || block.isIn(BlockTags.WALLS);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}

