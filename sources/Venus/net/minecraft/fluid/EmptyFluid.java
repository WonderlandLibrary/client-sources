/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class EmptyFluid
extends Fluid {
    @Override
    public Item getFilledBucket() {
        return Items.AIR;
    }

    @Override
    public boolean canDisplace(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    public Vector3d getFlow(IBlockReader iBlockReader, BlockPos blockPos, FluidState fluidState) {
        return Vector3d.ZERO;
    }

    @Override
    public int getTickRate(IWorldReader iWorldReader) {
        return 1;
    }

    @Override
    protected boolean isEmpty() {
        return false;
    }

    @Override
    protected float getExplosionResistance() {
        return 0.0f;
    }

    @Override
    public float getActualHeight(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return 0.0f;
    }

    @Override
    public float getHeight(FluidState fluidState) {
        return 0.0f;
    }

    @Override
    protected BlockState getBlockState(FluidState fluidState) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return true;
    }

    @Override
    public int getLevel(FluidState fluidState) {
        return 1;
    }

    @Override
    public VoxelShape func_215664_b(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return VoxelShapes.empty();
    }
}

