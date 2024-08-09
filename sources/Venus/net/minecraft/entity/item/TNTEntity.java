/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TNTEntity
extends Entity {
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(TNTEntity.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 80;

    public TNTEntity(EntityType<? extends TNTEntity> entityType, World world) {
        super(entityType, world);
        this.preventEntitySpawning = true;
    }

    public TNTEntity(World world, double d, double d2, double d3, @Nullable LivingEntity livingEntity) {
        this((EntityType<? extends TNTEntity>)EntityType.TNT, world);
        this.setPosition(d, d2, d3);
        double d4 = world.rand.nextDouble() * 6.2831854820251465;
        this.setMotion(-Math.sin(d4) * 0.02, 0.2f, -Math.cos(d4) * 0.02);
        this.setFuse(80);
        this.prevPosX = d;
        this.prevPosY = d2;
        this.prevPosZ = d3;
        this.tntPlacedBy = livingEntity;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FUSE, 80);
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0, -0.04, 0.0));
        }
        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7, -0.5, 0.7));
        }
        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5, this.getPosZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void explode() {
        float f = 4.0f;
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625), this.getPosZ(), 4.0f, Explosion.Mode.BREAK);
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putShort("Fuse", (short)this.getFuse());
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        this.setFuse(compoundNBT.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.15f;
    }

    public void setFuse(int n) {
        this.dataManager.set(FUSE, n);
        this.fuse = n;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        if (FUSE.equals(dataParameter)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

