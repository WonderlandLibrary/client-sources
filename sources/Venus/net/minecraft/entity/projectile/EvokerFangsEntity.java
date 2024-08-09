/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EvokerFangsEntity
extends Entity {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;
    private boolean clientSideAttackStarted;
    private LivingEntity caster;
    private UUID casterUuid;

    public EvokerFangsEntity(EntityType<? extends EvokerFangsEntity> entityType, World world) {
        super(entityType, world);
    }

    public EvokerFangsEntity(World world, double d, double d2, double d3, float f, int n, LivingEntity livingEntity) {
        this((EntityType<? extends EvokerFangsEntity>)EntityType.EVOKER_FANGS, world);
        this.warmupDelayTicks = n;
        this.setCaster(livingEntity);
        this.rotationYaw = f * 57.295776f;
        this.setPosition(d, d2, d3);
    }

    @Override
    protected void registerData() {
    }

    public void setCaster(@Nullable LivingEntity livingEntity) {
        this.caster = livingEntity;
        this.casterUuid = livingEntity == null ? null : livingEntity.getUniqueID();
    }

    @Nullable
    public LivingEntity getCaster() {
        Entity entity2;
        if (this.caster == null && this.casterUuid != null && this.world instanceof ServerWorld && (entity2 = ((ServerWorld)this.world).getEntityByUuid(this.casterUuid)) instanceof LivingEntity) {
            this.caster = (LivingEntity)entity2;
        }
        return this.caster;
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        this.warmupDelayTicks = compoundNBT.getInt("Warmup");
        if (compoundNBT.hasUniqueId("Owner")) {
            this.casterUuid = compoundNBT.getUniqueId("Owner");
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Warmup", this.warmupDelayTicks);
        if (this.casterUuid != null) {
            compoundNBT.putUniqueId("Owner", this.casterUuid);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isRemote) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks == 14) {
                    for (int i = 0; i < 12; ++i) {
                        double d = this.getPosX() + (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double d2 = this.getPosY() + 0.05 + this.rand.nextDouble();
                        double d3 = this.getPosZ() + (this.rand.nextDouble() * 2.0 - 1.0) * (double)this.getWidth() * 0.5;
                        double d4 = (this.rand.nextDouble() * 2.0 - 1.0) * 0.3;
                        double d5 = 0.3 + this.rand.nextDouble() * 0.3;
                        double d6 = (this.rand.nextDouble() * 2.0 - 1.0) * 0.3;
                        this.world.addParticle(ParticleTypes.CRIT, d, d2 + 1.0, d3, d4, d5, d6);
                    }
                }
            }
        } else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -8) {
                for (LivingEntity livingEntity : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(0.2, 0.0, 0.2))) {
                    this.damage(livingEntity);
                }
            }
            if (!this.sentSpikeEvent) {
                this.world.setEntityState(this, (byte)4);
                this.sentSpikeEvent = true;
            }
            if (--this.lifeTicks < 0) {
                this.remove();
            }
        }
    }

    private void damage(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getCaster();
        if (livingEntity.isAlive() && !livingEntity.isInvulnerable() && livingEntity != livingEntity2) {
            if (livingEntity2 == null) {
                livingEntity.attackEntityFrom(DamageSource.MAGIC, 6.0f);
            } else {
                if (livingEntity2.isOnSameTeam(livingEntity)) {
                    return;
                }
                livingEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, livingEntity2), 6.0f);
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte by) {
        super.handleStatusUpdate(by);
        if (by == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_EVOKER_FANGS_ATTACK, this.getSoundCategory(), 1.0f, this.rand.nextFloat() * 0.2f + 0.85f, true);
            }
        }
    }

    public float getAnimationProgress(float f) {
        if (!this.clientSideAttackStarted) {
            return 0.0f;
        }
        int n = this.lifeTicks - 2;
        return n <= 0 ? 1.0f : 1.0f - ((float)n - f) / 20.0f;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

