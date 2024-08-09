/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class HuskEntity
extends ZombieEntity {
    public HuskEntity(EntityType<? extends HuskEntity> entityType, World world) {
        super((EntityType<? extends ZombieEntity>)entityType, world);
    }

    public static boolean func_223334_b(EntityType<HuskEntity> entityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return HuskEntity.canMonsterSpawnInLight(entityType, iServerWorld, spawnReason, blockPos, random2) && (spawnReason == SpawnReason.SPAWNER || iServerWorld.canSeeSky(blockPos));
    }

    @Override
    protected boolean shouldBurnInDay() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HUSK_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_HUSK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HUSK_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_HUSK_STEP;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        boolean bl = super.attackEntityAsMob(entity2);
        if (bl && this.getHeldItemMainhand().isEmpty() && entity2 instanceof LivingEntity) {
            float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();
            ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.HUNGER, 140 * (int)f));
        }
        return bl;
    }

    @Override
    protected boolean shouldDrown() {
        return false;
    }

    @Override
    protected void onDrowned() {
        this.func_234341_c_(EntityType.ZOMBIE);
        if (!this.isSilent()) {
            this.world.playEvent(null, 1041, this.getPosition(), 0);
        }
    }

    @Override
    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }
}

