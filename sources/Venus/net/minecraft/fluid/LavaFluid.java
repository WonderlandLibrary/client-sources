/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFireBlock;
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
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class LavaFluid
extends FlowingFluid {
    @Override
    public Fluid getFlowingFluid() {
        return Fluids.FLOWING_LAVA;
    }

    @Override
    public Fluid getStillFluid() {
        return Fluids.LAVA;
    }

    @Override
    public Item getFilledBucket() {
        return Items.LAVA_BUCKET;
    }

    @Override
    public void animateTick(World world, BlockPos blockPos, FluidState fluidState, Random random2) {
        BlockPos blockPos2 = blockPos.up();
        if (world.getBlockState(blockPos2).isAir() && !world.getBlockState(blockPos2).isOpaqueCube(world, blockPos2)) {
            if (random2.nextInt(100) == 0) {
                double d = (double)blockPos.getX() + random2.nextDouble();
                double d2 = (double)blockPos.getY() + 1.0;
                double d3 = (double)blockPos.getZ() + random2.nextDouble();
                world.addParticle(ParticleTypes.LAVA, d, d2, d3, 0.0, 0.0, 0.0);
                world.playSound(d, d2, d3, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2f + random2.nextFloat() * 0.2f, 0.9f + random2.nextFloat() * 0.15f, true);
            }
            if (random2.nextInt(200) == 0) {
                world.playSound(blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2f + random2.nextFloat() * 0.2f, 0.9f + random2.nextFloat() * 0.15f, true);
            }
        }
    }

    @Override
    public void randomTick(World world, BlockPos blockPos, FluidState fluidState, Random random2) {
        block7: {
            if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) break block7;
            int n = random2.nextInt(3);
            if (n > 0) {
                BlockPos blockPos2 = blockPos;
                for (int i = 0; i < n; ++i) {
                    if (!world.isBlockPresent(blockPos2 = blockPos2.add(random2.nextInt(3) - 1, 1, random2.nextInt(3) - 1))) {
                        return;
                    }
                    BlockState blockState = world.getBlockState(blockPos2);
                    if (blockState.isAir()) {
                        if (!this.isSurroundingBlockFlammable(world, blockPos2)) continue;
                        world.setBlockState(blockPos2, AbstractFireBlock.getFireForPlacement(world, blockPos2));
                        return;
                    }
                    if (!blockState.getMaterial().blocksMovement()) continue;
                    return;
                }
            } else {
                for (int i = 0; i < 3; ++i) {
                    BlockPos blockPos3 = blockPos.add(random2.nextInt(3) - 1, 0, random2.nextInt(3) - 1);
                    if (!world.isBlockPresent(blockPos3)) {
                        return;
                    }
                    if (!world.isAirBlock(blockPos3.up()) || !this.getCanBlockBurn(world, blockPos3)) continue;
                    world.setBlockState(blockPos3.up(), AbstractFireBlock.getFireForPlacement(world, blockPos3));
                }
            }
        }
    }

    private boolean isSurroundingBlockFlammable(IWorldReader iWorldReader, BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (!this.getCanBlockBurn(iWorldReader, blockPos.offset(direction))) continue;
            return false;
        }
        return true;
    }

    private boolean getCanBlockBurn(IWorldReader iWorldReader, BlockPos blockPos) {
        return blockPos.getY() >= 0 && blockPos.getY() < 256 && !iWorldReader.isBlockLoaded(blockPos) ? false : iWorldReader.getBlockState(blockPos).getMaterial().isFlammable();
    }

    @Override
    @Nullable
    public IParticleData getDripParticleData() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    @Override
    protected void beforeReplacingBlock(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        this.triggerEffects(iWorld, blockPos);
    }

    @Override
    public int getSlopeFindDistance(IWorldReader iWorldReader) {
        return iWorldReader.getDimensionType().isUltrawarm() ? 4 : 2;
    }

    @Override
    public BlockState getBlockState(FluidState fluidState) {
        return (BlockState)Blocks.LAVA.getDefaultState().with(FlowingFluidBlock.LEVEL, LavaFluid.getLevelFromState(fluidState));
    }

    @Override
    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA;
    }

    @Override
    public int getLevelDecreasePerBlock(IWorldReader iWorldReader) {
        return iWorldReader.getDimensionType().isUltrawarm() ? 1 : 2;
    }

    @Override
    public boolean canDisplace(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos, Fluid fluid, Direction direction) {
        return fluidState.getActualHeight(iBlockReader, blockPos) >= 0.44444445f && fluid.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(IWorldReader iWorldReader) {
        return iWorldReader.getDimensionType().isUltrawarm() ? 10 : 30;
    }

    @Override
    public int func_215667_a(World world, BlockPos blockPos, FluidState fluidState, FluidState fluidState2) {
        int n = this.getTickRate(world);
        if (!(fluidState.isEmpty() || fluidState2.isEmpty() || fluidState.get(FALLING).booleanValue() || fluidState2.get(FALLING).booleanValue() || !(fluidState2.getActualHeight(world, blockPos) > fluidState.getActualHeight(world, blockPos)) || world.getRandom().nextInt(4) == 0)) {
            n *= 4;
        }
        return n;
    }

    private void triggerEffects(IWorld iWorld, BlockPos blockPos) {
        iWorld.playEvent(1501, blockPos, 0);
    }

    @Override
    protected boolean canSourcesMultiply() {
        return true;
    }

    @Override
    protected void flowInto(IWorld iWorld, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
        if (direction == Direction.DOWN) {
            FluidState fluidState2 = iWorld.getFluidState(blockPos);
            if (this.isIn(FluidTags.LAVA) && fluidState2.isTagged(FluidTags.WATER)) {
                if (blockState.getBlock() instanceof FlowingFluidBlock) {
                    iWorld.setBlockState(blockPos, Blocks.STONE.getDefaultState(), 3);
                }
                this.triggerEffects(iWorld, blockPos);
                return;
            }
        }
        super.flowInto(iWorld, blockPos, blockState, direction, fluidState);
    }

    @Override
    protected boolean ticksRandomly() {
        return false;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0f;
    }

    public static class Source
    extends LavaFluid {
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
    extends LavaFluid {
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

