/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public final class FluidState
extends StateHolder<Fluid, FluidState> {
    public static final Codec<FluidState> field_237213_a_ = FluidState.func_235897_a_(Registry.FLUID, Fluid::getDefaultState).stable();

    public FluidState(Fluid fluid, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<FluidState> mapCodec) {
        super(fluid, immutableMap, mapCodec);
    }

    public Fluid getFluid() {
        return (Fluid)this.instance;
    }

    public boolean isSource() {
        return this.getFluid().isSource(this);
    }

    public boolean isEmpty() {
        return this.getFluid().isEmpty();
    }

    public float getActualHeight(IBlockReader iBlockReader, BlockPos blockPos) {
        return this.getFluid().getActualHeight(this, iBlockReader, blockPos);
    }

    public float getHeight() {
        return this.getFluid().getHeight(this);
    }

    public int getLevel() {
        return this.getFluid().getLevel(this);
    }

    public boolean shouldRenderSides(IBlockReader iBlockReader, BlockPos blockPos) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                BlockPos blockPos2 = blockPos.add(i, 0, j);
                FluidState fluidState = iBlockReader.getFluidState(blockPos2);
                if (fluidState.getFluid().isEquivalentTo(this.getFluid()) || iBlockReader.getBlockState(blockPos2).isOpaqueCube(iBlockReader, blockPos2)) continue;
                return false;
            }
        }
        return true;
    }

    public void tick(World world, BlockPos blockPos) {
        this.getFluid().tick(world, blockPos, this);
    }

    public void animateTick(World world, BlockPos blockPos, Random random2) {
        this.getFluid().animateTick(world, blockPos, this, random2);
    }

    public boolean ticksRandomly() {
        return this.getFluid().ticksRandomly();
    }

    public void randomTick(World world, BlockPos blockPos, Random random2) {
        this.getFluid().randomTick(world, blockPos, this, random2);
    }

    public Vector3d getFlow(IBlockReader iBlockReader, BlockPos blockPos) {
        return this.getFluid().getFlow(iBlockReader, blockPos, this);
    }

    public BlockState getBlockState() {
        return this.getFluid().getBlockState(this);
    }

    @Nullable
    public IParticleData getDripParticleData() {
        return this.getFluid().getDripParticleData();
    }

    public boolean isTagged(ITag<Fluid> iTag) {
        return this.getFluid().isIn(iTag);
    }

    public float getExplosionResistance() {
        return this.getFluid().getExplosionResistance();
    }

    public boolean canDisplace(IBlockReader iBlockReader, BlockPos blockPos, Fluid fluid, Direction direction) {
        return this.getFluid().canDisplace(this, iBlockReader, blockPos, fluid, direction);
    }

    public VoxelShape getShape(IBlockReader iBlockReader, BlockPos blockPos) {
        return this.getFluid().func_215664_b(this, iBlockReader, blockPos);
    }
}

