/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class CaveSpiderEntity
extends SpiderEntity {
    public CaveSpiderEntity(EntityType<? extends CaveSpiderEntity> entityType, World world) {
        super((EntityType<? extends SpiderEntity>)entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return SpiderEntity.func_234305_eI_().createMutableAttribute(Attributes.MAX_HEALTH, 12.0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        if (super.attackEntityAsMob(entity2)) {
            if (entity2 instanceof LivingEntity) {
                int n = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    n = 7;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    n = 15;
                }
                if (n > 0) {
                    ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.POISON, n * 20, 0));
                }
            }
            return false;
        }
        return true;
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        return iLivingEntityData;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.45f;
    }
}

