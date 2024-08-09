/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface IAngerable {
    public int getAngerTime();

    public void setAngerTime(int var1);

    @Nullable
    public UUID getAngerTarget();

    public void setAngerTarget(@Nullable UUID var1);

    public void func_230258_H__();

    default public void writeAngerNBT(CompoundNBT compoundNBT) {
        compoundNBT.putInt("AngerTime", this.getAngerTime());
        if (this.getAngerTarget() != null) {
            compoundNBT.putUniqueId("AngryAt", this.getAngerTarget());
        }
    }

    default public void readAngerNBT(ServerWorld serverWorld, CompoundNBT compoundNBT) {
        this.setAngerTime(compoundNBT.getInt("AngerTime"));
        if (!compoundNBT.hasUniqueId("AngryAt")) {
            this.setAngerTarget(null);
        } else {
            UUID uUID = compoundNBT.getUniqueId("AngryAt");
            this.setAngerTarget(uUID);
            Entity entity2 = serverWorld.getEntityByUuid(uUID);
            if (entity2 != null) {
                if (entity2 instanceof MobEntity) {
                    this.setRevengeTarget((MobEntity)entity2);
                }
                if (entity2.getType() == EntityType.PLAYER) {
                    this.func_230246_e_((PlayerEntity)entity2);
                }
            }
        }
    }

    default public void func_241359_a_(ServerWorld serverWorld, boolean bl) {
        LivingEntity livingEntity = this.getAttackTarget();
        UUID uUID = this.getAngerTarget();
        if ((livingEntity == null || livingEntity.getShouldBeDead()) && uUID != null && serverWorld.getEntityByUuid(uUID) instanceof MobEntity) {
            this.func_241356_K__();
        } else {
            if (livingEntity != null && !Objects.equals(uUID, livingEntity.getUniqueID())) {
                this.setAngerTarget(livingEntity.getUniqueID());
                this.func_230258_H__();
            }
            if (!(this.getAngerTime() <= 0 || livingEntity != null && livingEntity.getType() == EntityType.PLAYER && bl)) {
                this.setAngerTime(this.getAngerTime() - 1);
                if (this.getAngerTime() == 0) {
                    this.func_241356_K__();
                }
            }
        }
    }

    default public boolean func_233680_b_(LivingEntity livingEntity) {
        if (!EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity)) {
            return true;
        }
        return livingEntity.getType() == EntityType.PLAYER && this.func_241357_a_(livingEntity.world) ? true : livingEntity.getUniqueID().equals(this.getAngerTarget());
    }

    default public boolean func_241357_a_(World world) {
        return world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER) && this.func_233678_J__() && this.getAngerTarget() == null;
    }

    default public boolean func_233678_J__() {
        return this.getAngerTime() > 0;
    }

    default public void func_233681_b_(PlayerEntity playerEntity) {
        if (playerEntity.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS) && playerEntity.getUniqueID().equals(this.getAngerTarget())) {
            this.func_241356_K__();
        }
    }

    default public void func_241355_J__() {
        this.func_241356_K__();
        this.func_230258_H__();
    }

    default public void func_241356_K__() {
        this.setRevengeTarget(null);
        this.setAngerTarget(null);
        this.setAttackTarget(null);
        this.setAngerTime(0);
    }

    public void setRevengeTarget(@Nullable LivingEntity var1);

    public void func_230246_e_(@Nullable PlayerEntity var1);

    public void setAttackTarget(@Nullable LivingEntity var1);

    @Nullable
    public LivingEntity getAttackTarget();
}

