/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class StrayEntity
extends AbstractSkeletonEntity {
    public StrayEntity(EntityType<? extends StrayEntity> entityType, World world) {
        super((EntityType<? extends AbstractSkeletonEntity>)entityType, world);
    }

    public static boolean func_223327_b(EntityType<StrayEntity> entityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return StrayEntity.canMonsterSpawnInLight(entityType, iServerWorld, spawnReason, blockPos, random2) && (spawnReason == SpawnReason.SPAWNER || iServerWorld.canSeeSky(blockPos));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_STRAY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_STRAY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRAY_DEATH;
    }

    @Override
    SoundEvent getStepSound() {
        return SoundEvents.ENTITY_STRAY_STEP;
    }

    @Override
    protected AbstractArrowEntity fireArrow(ItemStack itemStack, float f) {
        AbstractArrowEntity abstractArrowEntity = super.fireArrow(itemStack, f);
        if (abstractArrowEntity instanceof ArrowEntity) {
            ((ArrowEntity)abstractArrowEntity).addEffect(new EffectInstance(Effects.SLOWNESS, 600));
        }
        return abstractArrowEntity;
    }
}

