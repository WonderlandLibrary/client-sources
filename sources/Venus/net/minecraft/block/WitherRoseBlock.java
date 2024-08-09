/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WitherRoseBlock
extends FlowerBlock {
    public WitherRoseBlock(Effect effect, AbstractBlock.Properties properties) {
        super(effect, 8, properties);
    }

    @Override
    protected boolean isValidGround(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return super.isValidGround(blockState, iBlockReader, blockPos) || blockState.isIn(Blocks.NETHERRACK) || blockState.isIn(Blocks.SOUL_SAND) || blockState.isIn(Blocks.SOUL_SOIL);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        VoxelShape voxelShape = this.getShape(blockState, world, blockPos, ISelectionContext.dummy());
        Vector3d vector3d = voxelShape.getBoundingBox().getCenter();
        double d = (double)blockPos.getX() + vector3d.x;
        double d2 = (double)blockPos.getZ() + vector3d.z;
        for (int i = 0; i < 3; ++i) {
            if (!random2.nextBoolean()) continue;
            world.addParticle(ParticleTypes.SMOKE, d + random2.nextDouble() / 5.0, (double)blockPos.getY() + (0.5 - random2.nextDouble()), d2 + random2.nextDouble() / 5.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        LivingEntity livingEntity;
        if (!world.isRemote && world.getDifficulty() != Difficulty.PEACEFUL && entity2 instanceof LivingEntity && !(livingEntity = (LivingEntity)entity2).isInvulnerableTo(DamageSource.WITHER)) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.WITHER, 40));
        }
    }
}

