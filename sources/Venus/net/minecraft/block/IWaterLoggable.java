/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public interface IWaterLoggable
extends IBucketPickupHandler,
ILiquidContainer {
    @Override
    default public boolean canContainFluid(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return blockState.get(BlockStateProperties.WATERLOGGED) == false && fluid == Fluids.WATER;
    }

    @Override
    default public boolean receiveFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
        if (!blockState.get(BlockStateProperties.WATERLOGGED).booleanValue() && fluidState.getFluid() == Fluids.WATER) {
            if (!iWorld.isRemote()) {
                iWorld.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.WATERLOGGED, true), 3);
                iWorld.getPendingFluidTicks().scheduleTick(blockPos, fluidState.getFluid(), fluidState.getFluid().getTickRate(iWorld));
            }
            return false;
        }
        return true;
    }

    @Override
    default public Fluid pickupFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        if (blockState.get(BlockStateProperties.WATERLOGGED).booleanValue()) {
            iWorld.setBlockState(blockPos, (BlockState)blockState.with(BlockStateProperties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        }
        return Fluids.EMPTY;
    }
}

