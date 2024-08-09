/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class DamagingProjectileEntity
extends ProjectileEntity {
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    protected DamagingProjectileEntity(EntityType<? extends DamagingProjectileEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    public DamagingProjectileEntity(EntityType<? extends DamagingProjectileEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, World world) {
        this(entityType, world);
        this.setLocationAndAngles(d, d2, d3, this.rotationYaw, this.rotationPitch);
        this.recenterBoundingBox();
        double d7 = MathHelper.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if (d7 != 0.0) {
            this.accelerationX = d4 / d7 * 0.1;
            this.accelerationY = d5 / d7 * 0.1;
            this.accelerationZ = d6 / d7 * 0.1;
        }
    }

    public DamagingProjectileEntity(EntityType<? extends DamagingProjectileEntity> entityType, LivingEntity livingEntity, double d, double d2, double d3, World world) {
        this(entityType, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), d, d2, d3, world);
        this.setShooter(livingEntity);
        this.setRotation(livingEntity.rotationYaw, livingEntity.rotationPitch);
    }

    @Override
    protected void registerData() {
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        double d2 = this.getBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(d2)) {
            d2 = 4.0;
        }
        return d < (d2 *= 64.0) * d2;
    }

    @Override
    public void tick() {
        Entity entity2 = this.func_234616_v_();
        if (this.world.isRemote || (entity2 == null || !entity2.removed) && this.world.isBlockLoaded(this.getPosition())) {
            RayTraceResult rayTraceResult;
            super.tick();
            if (this.isFireballFiery()) {
                this.setFire(1);
            }
            if ((rayTraceResult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_)).getType() != RayTraceResult.Type.MISS) {
                this.onImpact(rayTraceResult);
            }
            this.doBlockCollisions();
            Vector3d vector3d = this.getMotion();
            double d = this.getPosX() + vector3d.x;
            double d2 = this.getPosY() + vector3d.y;
            double d3 = this.getPosZ() + vector3d.z;
            ProjectileHelper.rotateTowardsMovement(this, 0.2f);
            float f = this.getMotionFactor();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    float f2 = 0.25f;
                    this.world.addParticle(ParticleTypes.BUBBLE, d - vector3d.x * 0.25, d2 - vector3d.y * 0.25, d3 - vector3d.z * 0.25, vector3d.x, vector3d.y, vector3d.z);
                }
                f = 0.8f;
            }
            this.setMotion(vector3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.world.addParticle(this.getParticle(), d, d2 + 0.5, d3, 0.0, 0.0, 0.0);
            this.setPosition(d, d2, d3);
        } else {
            this.remove();
        }
    }

    @Override
    protected boolean func_230298_a_(Entity entity2) {
        return super.func_230298_a_(entity2) && !entity2.noClip;
    }

    protected boolean isFireballFiery() {
        return false;
    }

    protected IParticleData getParticle() {
        return ParticleTypes.SMOKE;
    }

    protected float getMotionFactor() {
        return 0.95f;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.put("power", this.newDoubleNBTList(this.accelerationX, this.accelerationY, this.accelerationZ));
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        ListNBT listNBT;
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("power", 0) && (listNBT = compoundNBT.getList("power", 6)).size() == 3) {
            this.accelerationX = listNBT.getDouble(0);
            this.accelerationY = listNBT.getDouble(1);
            this.accelerationZ = listNBT.getDouble(2);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public float getCollisionBorderSize() {
        return 1.0f;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        this.markVelocityChanged();
        Entity entity2 = damageSource.getTrueSource();
        if (entity2 != null) {
            Vector3d vector3d = entity2.getLookVec();
            this.setMotion(vector3d);
            this.accelerationX = vector3d.x * 0.1;
            this.accelerationY = vector3d.y * 0.1;
            this.accelerationZ = vector3d.z * 0.1;
            this.setShooter(entity2);
            return false;
        }
        return true;
    }

    @Override
    public float getBrightness() {
        return 1.0f;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        Entity entity2 = this.func_234616_v_();
        int n = entity2 == null ? 0 : entity2.getEntityId();
        return new SSpawnObjectPacket(this.getEntityId(), this.getUniqueID(), this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationPitch, this.rotationYaw, this.getType(), n, new Vector3d(this.accelerationX, this.accelerationY, this.accelerationZ));
    }
}

