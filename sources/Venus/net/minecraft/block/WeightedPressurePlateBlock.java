/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class WeightedPressurePlateBlock
extends AbstractPressurePlateBlock {
    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;
    private final int maxWeight;

    protected WeightedPressurePlateBlock(int n, AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWER, 0));
        this.maxWeight = n;
    }

    @Override
    protected int computeRedstoneStrength(World world, BlockPos blockPos) {
        int n = Math.min(world.getEntitiesWithinAABB(Entity.class, PRESSURE_AABB.offset(blockPos)).size(), this.maxWeight);
        if (n > 0) {
            float f = (float)Math.min(this.maxWeight, n) / (float)this.maxWeight;
            return MathHelper.ceil(f * 15.0f);
        }
        return 1;
    }

    @Override
    protected void playClickOnSound(IWorld iWorld, BlockPos blockPos) {
        iWorld.playSound(null, blockPos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3f, 0.90000004f);
    }

    @Override
    protected void playClickOffSound(IWorld iWorld, BlockPos blockPos) {
        iWorld.playSound(null, blockPos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3f, 0.75f);
    }

    @Override
    protected int getRedstoneStrength(BlockState blockState) {
        return blockState.get(POWER);
    }

    @Override
    protected BlockState setRedstoneStrength(BlockState blockState, int n) {
        return (BlockState)blockState.with(POWER, n);
    }

    @Override
    protected int getPoweredDuration() {
        return 1;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }
}

