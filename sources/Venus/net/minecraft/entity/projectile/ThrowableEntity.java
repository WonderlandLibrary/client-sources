/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class ThrowableEntity
extends ProjectileEntity {
    protected ThrowableEntity(EntityType<? extends ThrowableEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    protected ThrowableEntity(EntityType<? extends ThrowableEntity> entityType, double d, double d2, double d3, World world) {
        this(entityType, world);
        this.setPosition(d, d2, d3);
    }

    protected ThrowableEntity(EntityType<? extends ThrowableEntity> entityType, LivingEntity livingEntity, World world) {
        this(entityType, livingEntity.getPosX(), livingEntity.getPosYEye() - (double)0.1f, livingEntity.getPosZ(), world);
        this.setShooter(livingEntity);
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
        float f;
        Object object;
        super.tick();
        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        boolean bl = false;
        if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
            object = ((BlockRayTraceResult)rayTraceResult).getPos();
            BlockState blockState = this.world.getBlockState((BlockPos)object);
            if (blockState.isIn(Blocks.NETHER_PORTAL)) {
                this.setPortal((BlockPos)object);
                bl = true;
            } else if (blockState.isIn(Blocks.END_GATEWAY)) {
                TileEntity tileEntity = this.world.getTileEntity((BlockPos)object);
                if (tileEntity instanceof EndGatewayTileEntity && EndGatewayTileEntity.func_242690_a(this)) {
                    ((EndGatewayTileEntity)tileEntity).teleportEntity(this);
                }
                bl = true;
            }
        }
        if (rayTraceResult.getType() != RayTraceResult.Type.MISS && !bl) {
            this.onImpact(rayTraceResult);
        }
        this.doBlockCollisions();
        object = this.getMotion();
        double d = this.getPosX() + ((Vector3d)object).x;
        double d2 = this.getPosY() + ((Vector3d)object).y;
        double d3 = this.getPosZ() + ((Vector3d)object).z;
        this.func_234617_x_();
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float f2 = 0.25f;
                this.world.addParticle(ParticleTypes.BUBBLE, d - ((Vector3d)object).x * 0.25, d2 - ((Vector3d)object).y * 0.25, d3 - ((Vector3d)object).z * 0.25, ((Vector3d)object).x, ((Vector3d)object).y, ((Vector3d)object).z);
            }
            f = 0.8f;
        } else {
            f = 0.99f;
        }
        this.setMotion(((Vector3d)object).scale(f));
        if (!this.hasNoGravity()) {
            Vector3d vector3d = this.getMotion();
            this.setMotion(vector3d.x, vector3d.y - (double)this.getGravityVelocity(), vector3d.z);
        }
        this.setPosition(d, d2, d3);
    }

    public float getGravityVelocity() {
        return 0.03f;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

