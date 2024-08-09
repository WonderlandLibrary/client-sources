/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class TameableEntity
extends AnimalEntity {
    protected static final DataParameter<Byte> TAMED = EntityDataManager.createKey(TameableEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(TameableEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private boolean field_233683_bw_;

    protected TameableEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.setupTamedAI();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TAMED, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        if (this.getOwnerId() != null) {
            compoundNBT.putUniqueId("Owner", this.getOwnerId());
        }
        compoundNBT.putBoolean("Sitting", this.field_233683_bw_);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        UUID uUID;
        super.readAdditional(compoundNBT);
        if (compoundNBT.hasUniqueId("Owner")) {
            uUID = compoundNBT.getUniqueId("Owner");
        } else {
            String string = compoundNBT.getString("Owner");
            uUID = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setOwnerId(uUID);
                this.setTamed(false);
            } catch (Throwable throwable) {
                this.setTamed(true);
            }
        }
        this.field_233683_bw_ = compoundNBT.getBoolean("Sitting");
        this.setSleeping(this.field_233683_bw_);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity playerEntity) {
        return !this.getLeashed();
    }

    protected void playTameEffect(boolean bl) {
        BasicParticleType basicParticleType = ParticleTypes.HEART;
        if (!bl) {
            basicParticleType = ParticleTypes.SMOKE;
        }
        for (int i = 0; i < 7; ++i) {
            double d = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            double d3 = this.rand.nextGaussian() * 0.02;
            this.world.addParticle(basicParticleType, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), d, d2, d3);
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 7) {
            this.playTameEffect(false);
        } else if (by == 6) {
            this.playTameEffect(true);
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public boolean isTamed() {
        return (this.dataManager.get(TAMED) & 4) != 0;
    }

    public void setTamed(boolean bl) {
        byte by = this.dataManager.get(TAMED);
        if (bl) {
            this.dataManager.set(TAMED, (byte)(by | 4));
        } else {
            this.dataManager.set(TAMED, (byte)(by & 0xFFFFFFFB));
        }
        this.setupTamedAI();
    }

    protected void setupTamedAI() {
    }

    @Override
    public boolean isSleeping() {
        return (this.dataManager.get(TAMED) & 1) != 0;
    }

    public void setSleeping(boolean bl) {
        byte by = this.dataManager.get(TAMED);
        if (bl) {
            this.dataManager.set(TAMED, (byte)(by | 1));
        } else {
            this.dataManager.set(TAMED, (byte)(by & 0xFFFFFFFE));
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orElse(null);
    }

    public void setOwnerId(@Nullable UUID uUID) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uUID));
    }

    public void setTamedBy(PlayerEntity playerEntity) {
        this.setTamed(false);
        this.setOwnerId(playerEntity.getUniqueID());
        if (playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayerEntity)playerEntity, this);
        }
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.getOwnerId();
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        return this.isOwner(livingEntity) ? false : super.canAttack(livingEntity);
    }

    public boolean isOwner(LivingEntity livingEntity) {
        return livingEntity == this.getOwner();
    }

    public boolean shouldAttackEntity(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return false;
    }

    @Override
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.isTamed() && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }

    @Override
    public boolean isOnSameTeam(Entity entity2) {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (entity2 == livingEntity) {
                return false;
            }
            if (livingEntity != null) {
                return livingEntity.isOnSameTeam(entity2);
            }
        }
        return super.isOnSameTeam(entity2);
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (!this.world.isRemote && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage(), Util.DUMMY_UUID);
        }
        super.onDeath(damageSource);
    }

    public boolean isSitting() {
        return this.field_233683_bw_;
    }

    public void func_233687_w_(boolean bl) {
        this.field_233683_bw_ = bl;
    }
}

