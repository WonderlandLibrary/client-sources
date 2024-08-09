/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

public class TallSeaGrassBlock
extends DoublePlantBlock
implements ILiquidContainer {
    public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public TallSeaGrassBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return blockState.isSolidSide(iBlockReader, blockPos, Direction.UP) && !blockState.isIn(Blocks.MAGMA_BLOCK);
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(Blocks.SEAGRASS);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        FluidState fluidState;
        BlockState blockState = super.getStateForPlacement(blockItemUseContext);
        if (blockState != null && (fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos().up())).isTagged(FluidTags.WATER) && fluidState.getLevel() == 8) {
            return blockState;
        }
        return null;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        if (blockState.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
            return blockState2.isIn(this) && blockState2.get(HALF) == DoubleBlockHalf.LOWER;
        }
        FluidState fluidState = iWorldReader.getFluidState(blockPos);
        return super.isValidPosition(blockState, iWorldReader, blockPos) && fluidState.isTagged(FluidTags.WATER) && fluidState.getLevel() == 8;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return Fluids.WATER.getStillFluidState(true);
    }

    @Override
    public boolean canContainFluid(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return true;
    }

    @Override
    public boolean receiveFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return true;
    }
}

