/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class WaterFluid
extends FlowingFluid {
    @Override
    public Fluid getFlowingFluid() {
        return Fluids.FLOWING_WATER;
    }

    @Override
    public Fluid getStillFluid() {
        return Fluids.WATER;
    }

    @Override
    public Item getFilledBucket() {
        return Items.WATER_BUCKET;
    }

    @Override
    public void animateTick(World world, BlockPos blockPos, FluidState fluidState, Random random2) {
        if (!fluidState.isSource() && !fluidState.get(FALLING).booleanValue()) {
            if (random2.nextInt(64) == 0) {
                world.playSound((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random2.nextFloat() * 0.25f + 0.75f, random2.nextFloat() + 0.5f, true);
            }
        } else if (random2.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.UNDERWATER, (double)blockPos.getX() + random2.nextDouble(), (double)blockPos.getY() + random2.nextDouble(), (double)blockPos.getZ() + random2.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    @Nullable
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean canSourcesMultiply() {
        return false;
    }

    @Override
    protected void beforeReplacingBlock(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        TileEntity tileEntity = blockState.getBlock().isTileEntityProvider() ? iWorld.getTileEntity(blockPos) : null;
        Block.spawnDrops(blockState, iWorld, blockPos, tileEntity);
    }

    @Override
    public int getSlopeFindDistance(IWorldReader iWorldReader) {
        return 1;
    }

    @Override
    public BlockState getBlockState(FluidState fluidState) {
        return (BlockState)Blocks.WATER.getDefaultState().with(FlowingFluidBlock.LEVEL, WaterFluid.getLevelFromState(fluidState));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER;
    }

    @Override
    public int getLevelDecreasePerBlock(IWorldReader iWorldReader) {
        return 0;
    }

    @Override
    public int getTickRate(IWorldReader iWorldReader) {
        return 0;
    }

    @Override
    public boolean canDisplace(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0f;
    }

    public static class Source
    extends WaterFluid {
        @Override
        public int getLevel(FluidState fluidState) {
            return 1;
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }
    }

    public static class Flowing
    extends WaterFluid {
        @Override
        protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL_1_8);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }
    }
}

