/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DonkeyEntity
extends AbstractChestedHorseEntity {
    public DonkeyEntity(EntityType<? extends DonkeyEntity> entityType, World world) {
        super((EntityType<? extends AbstractChestedHorseEntity>)entityType, world);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_DONKEY_AMBIENT;
    }

    @Override
    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.ENTITY_DONKEY_ANGRY;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_DONKEY_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent func_230274_fe_() {
        return SoundEvents.ENTITY_DONKEY_EAT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        super.getHurtSound(damageSource);
        return SoundEvents.ENTITY_DONKEY_HURT;
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return true;
        }
        if (!(animalEntity instanceof DonkeyEntity) && !(animalEntity instanceof HorseEntity)) {
            return true;
        }
        return this.canMate() && ((AbstractHorseEntity)animalEntity).canMate();
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        EntityType<AbstractChestedHorseEntity> entityType = ageableEntity instanceof HorseEntity ? EntityType.MULE : EntityType.DONKEY;
        AbstractHorseEntity abstractHorseEntity = entityType.create(serverWorld);
        this.setOffspringAttributes(ageableEntity, abstractHorseEntity);
        return abstractHorseEntity;
    }
}

