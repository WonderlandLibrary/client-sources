/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class HoneyBlock
extends BreakableBlock {
    protected static final VoxelShape SHAPES = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

    public HoneyBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    private static boolean hasSlideEffects(Entity entity2) {
        return entity2 instanceof LivingEntity || entity2 instanceof AbstractMinecartEntity || entity2 instanceof TNTEntity || entity2 instanceof BoatEntity;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return SHAPES;
    }

    @Override
    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        entity2.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0f, 1.0f);
        if (!world.isRemote) {
            world.setEntityState(entity2, (byte)54);
        }
        if (entity2.onLivingFall(f, 0.2f)) {
            entity2.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5f, this.soundType.getPitch() * 0.75f);
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (this.isSliding(blockPos, entity2)) {
            this.triggerSlideDownBlock(entity2, blockPos);
            this.setSlideVelocity(entity2);
            this.slideEffects(world, entity2);
        }
        super.onEntityCollision(blockState, world, blockPos, entity2);
    }

    private boolean isSliding(BlockPos blockPos, Entity entity2) {
        if (entity2.isOnGround()) {
            return true;
        }
        if (entity2.getPosY() > (double)blockPos.getY() + 0.9375 - 1.0E-7) {
            return true;
        }
        if (entity2.getMotion().y >= -0.08) {
            return true;
        }
        double d = Math.abs((double)blockPos.getX() + 0.5 - entity2.getPosX());
        double d2 = Math.abs((double)blockPos.getZ() + 0.5 - entity2.getPosZ());
        double d3 = 0.4375 + (double)(entity2.getWidth() / 2.0f);
        return d + 1.0E-7 > d3 || d2 + 1.0E-7 > d3;
    }

    private void triggerSlideDownBlock(Entity entity2, BlockPos blockPos) {
        if (entity2 instanceof ServerPlayerEntity && entity2.world.getGameTime() % 20L == 0L) {
            CriteriaTriggers.SLIDE_DOWN_BLOCK.test((ServerPlayerEntity)entity2, entity2.world.getBlockState(blockPos));
        }
    }

    private void setSlideVelocity(Entity entity2) {
        Vector3d vector3d = entity2.getMotion();
        if (vector3d.y < -0.13) {
            double d = -0.05 / vector3d.y;
            entity2.setMotion(new Vector3d(vector3d.x * d, -0.05, vector3d.z * d));
        } else {
            entity2.setMotion(new Vector3d(vector3d.x, -0.05, vector3d.z));
        }
        entity2.fallDistance = 0.0f;
    }

    private void slideEffects(World world, Entity entity2) {
        if (HoneyBlock.hasSlideEffects(entity2)) {
            if (world.rand.nextInt(5) == 0) {
                entity2.playSound(SoundEvents.BLOCK_HONEY_BLOCK_SLIDE, 1.0f, 1.0f);
            }
            if (!world.isRemote && world.rand.nextInt(5) == 0) {
                world.setEntityState(entity2, (byte)53);
            }
        }
    }

    public static void entitySlideParticles(Entity entity2) {
        HoneyBlock.slideParticles(entity2, 5);
    }

    public static void livingSlideParticles(Entity entity2) {
        HoneyBlock.slideParticles(entity2, 10);
    }

    private static void slideParticles(Entity entity2, int n) {
        if (entity2.world.isRemote) {
            BlockState blockState = Blocks.HONEY_BLOCK.getDefaultState();
            for (int i = 0; i < n; ++i) {
                entity2.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockState), entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), 0.0, 0.0, 0.0);
            }
        }
    }
}

