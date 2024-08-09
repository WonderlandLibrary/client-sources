/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class GolemEntity
extends CreatureEntity {
    protected GolemEntity(EntityType<? extends GolemEntity> entityType, World world) {
        super((EntityType<? extends CreatureEntity>)entityType, world);
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        return true;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return null;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public int getTalkInterval() {
        return 1;
    }

    @Override
    public boolean canDespawn(double d) {
        return true;
    }
}

