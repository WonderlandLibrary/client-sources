/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MagmaBlock
extends Block {
    public MagmaBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
        if (!entity2.isImmuneToFire() && entity2 instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity2)) {
            entity2.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0f);
        }
        super.onEntityWalk(world, blockPos, entity2);
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        BubbleColumnBlock.placeBubbleColumn(serverWorld, blockPos.up(), true);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (direction == Direction.UP && blockState2.isIn(Blocks.WATER)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 20);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        BlockPos blockPos2 = blockPos.up();
        if (serverWorld.getFluidState(blockPos).isTagged(FluidTags.WATER)) {
            serverWorld.playSound(null, blockPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 2.6f + (serverWorld.rand.nextFloat() - serverWorld.rand.nextFloat()) * 0.8f);
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, (double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.25, (double)blockPos2.getZ() + 0.5, 8, 0.5, 0.25, 0.5, 0.0);
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        world.getPendingBlockTicks().scheduleTick(blockPos, this, 20);
    }
}

