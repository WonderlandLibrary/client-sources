/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class ProjectileEntity
extends Entity {
    private UUID field_234609_b_;
    public int field_234610_c_;
    private boolean field_234611_d_;

    ProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setShooter(@Nullable Entity entity2) {
        if (entity2 != null) {
            this.field_234609_b_ = entity2.getUniqueID();
            this.field_234610_c_ = entity2.getEntityId();
        }
    }

    @Nullable
    public Entity func_234616_v_() {
        if (this.field_234609_b_ != null && this.world instanceof ServerWorld) {
            return ((ServerWorld)this.world).getEntityByUuid(this.field_234609_b_);
        }
        return this.field_234610_c_ != 0 ? this.world.getEntityByID(this.field_234610_c_) : null;
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        if (this.field_234609_b_ != null) {
            compoundNBT.putUniqueId("Owner", this.field_234609_b_);
        }
        if (this.field_234611_d_) {
            compoundNBT.putBoolean("LeftOwner", false);
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.hasUniqueId("Owner")) {
            this.field_234609_b_ = compoundNBT.getUniqueId("Owner");
        }
        this.field_234611_d_ = compoundNBT.getBoolean("LeftOwner");
    }

    @Override
    public void tick() {
        if (!this.field_234611_d_) {
            this.field_234611_d_ = this.func_234615_h_();
        }
        super.tick();
    }

    private boolean func_234615_h_() {
        Entity entity2 = this.func_234616_v_();
        if (entity2 != null) {
            for (Entity entity3 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0), ProjectileEntity::lambda$func_234615_h_$0)) {
                if (entity3.getLowestRidingEntity() != entity2.getLowestRidingEntity()) continue;
                return true;
            }
        }
        return false;
    }

    public void shoot(double d, double d2, double d3, float f, float f2) {
        Vector3d vector3d = new Vector3d(d, d2, d3).normalize().add(this.rand.nextGaussian() * (double)0.0075f * (double)f2, this.rand.nextGaussian() * (double)0.0075f * (double)f2, this.rand.nextGaussian() * (double)0.0075f * (double)f2).scale(f);
        this.setMotion(vector3d);
        float f3 = MathHelper.sqrt(ProjectileEntity.horizontalMag(vector3d));
        this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875);
        this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, f3) * 57.2957763671875);
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    public void func_234612_a_(Entity entity2, float f, float f2, float f3, float f4, float f5) {
        float f6 = -MathHelper.sin(f2 * ((float)Math.PI / 180)) * MathHelper.cos(f * ((float)Math.PI / 180));
        float f7 = -MathHelper.sin((f + f3) * ((float)Math.PI / 180));
        float f8 = MathHelper.cos(f2 * ((float)Math.PI / 180)) * MathHelper.cos(f * ((float)Math.PI / 180));
        this.shoot(f6, f7, f8, f4, f5);
        Vector3d vector3d = entity2.getMotion();
        this.setMotion(this.getMotion().add(vector3d.x, entity2.isOnGround() ? 0.0 : vector3d.y, vector3d.z));
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        RayTraceResult.Type type = rayTraceResult.getType();
        if (type == RayTraceResult.Type.ENTITY) {
            this.onEntityHit((EntityRayTraceResult)rayTraceResult);
        } else if (type == RayTraceResult.Type.BLOCK) {
            this.func_230299_a_((BlockRayTraceResult)rayTraceResult);
        }
    }

    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
    }

    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        BlockState blockState = this.world.getBlockState(blockRayTraceResult.getPos());
        blockState.onProjectileCollision(this.world, blockState, blockRayTraceResult, this);
    }

    @Override
    public void setVelocity(double d, double d2, double d3) {
        this.setMotion(d, d2, d3);
        if (this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt(d * d + d3 * d3);
            this.rotationPitch = (float)(MathHelper.atan2(d2, f) * 57.2957763671875);
            this.rotationYaw = (float)(MathHelper.atan2(d, d3) * 57.2957763671875);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
        }
    }

    protected boolean func_230298_a_(Entity entity2) {
        if (!entity2.isSpectator() && entity2.isAlive() && entity2.canBeCollidedWith()) {
            Entity entity3 = this.func_234616_v_();
            return entity3 == null || this.field_234611_d_ || !entity3.isRidingSameEntity(entity2);
        }
        return true;
    }

    protected void func_234617_x_() {
        Vector3d vector3d = this.getMotion();
        float f = MathHelper.sqrt(ProjectileEntity.horizontalMag(vector3d));
        this.rotationPitch = ProjectileEntity.func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(vector3d.y, f) * 57.2957763671875));
        this.rotationYaw = ProjectileEntity.func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875));
    }

    protected static float func_234614_e_(float f, float f2) {
        while (f2 - f < -180.0f) {
            f -= 360.0f;
        }
        while (f2 - f >= 180.0f) {
            f += 360.0f;
        }
        return MathHelper.lerp(0.2f, f, f2);
    }

    private static boolean lambda$func_234615_h_$0(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }
}

