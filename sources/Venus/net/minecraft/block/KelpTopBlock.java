/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class KelpTopBlock
extends AbstractTopPlantBlock
implements ILiquidContainer {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    protected KelpTopBlock(AbstractBlock.Properties properties) {
        super(properties, Direction.UP, SHAPE, true, 0.14);
    }

    @Override
    protected boolean canGrowIn(BlockState blockState) {
        return blockState.isIn(Blocks.WATER);
    }

    @Override
    protected Block getBodyPlantBlock() {
        return Blocks.KELP_PLANT;
    }

    @Override
    protected boolean canGrowOn(Block block) {
        return block != Blocks.MAGMA_BLOCK;
    }

    @Override
    public boolean canContainFluid(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return true;
    }

    @Override
    public boolean receiveFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        return true;
    }

    @Override
    protected int getGrowthAmount(Random random2) {
        return 0;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        return fluidState.isTagged(FluidTags.WATER) && fluidState.getLevel() == 8 ? super.getStateForPlacement(blockItemUseContext) : null;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return Fluids.WATER.getStillFluidState(true);
    }
}

