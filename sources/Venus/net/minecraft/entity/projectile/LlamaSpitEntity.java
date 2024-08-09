/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LlamaSpitEntity
extends ProjectileEntity {
    public LlamaSpitEntity(EntityType<? extends LlamaSpitEntity> entityType, World world) {
        super((EntityType<? extends ProjectileEntity>)entityType, world);
    }

    public LlamaSpitEntity(World world, LlamaEntity llamaEntity) {
        this((EntityType<? extends LlamaSpitEntity>)EntityType.LLAMA_SPIT, world);
        super.setShooter(llamaEntity);
        this.setPosition(llamaEntity.getPosX() - (double)(llamaEntity.getWidth() + 1.0f) * 0.5 * (double)MathHelper.sin(llamaEntity.renderYawOffset * ((float)Math.PI / 180)), llamaEntity.getPosYEye() - (double)0.1f, llamaEntity.getPosZ() + (double)(llamaEntity.getWidth() + 1.0f) * 0.5 * (double)MathHelper.cos(llamaEntity.renderYawOffset * ((float)Math.PI / 180)));
    }

    public LlamaSpitEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        this((EntityType<? extends LlamaSpitEntity>)EntityType.LLAMA_SPIT, world);
        this.setPosition(d, d2, d3);
        for (int i = 0; i < 7; ++i) {
            double d7 = 0.4 + 0.1 * (double)i;
            world.addParticle(ParticleTypes.SPIT, d, d2, d3, d4 * d7, d5, d6 * d7);
        }
        this.setMotion(d4, d5, d6);
    }

    @Override
    public void tick() {
        super.tick();
        Vector3d vector3d = this.getMotion();
        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        if (rayTraceResult != null) {
            this.onImpact(rayTraceResult);
        }
        double d = this.getPosX() + vector3d.x;
        double d2 = this.getPosY() + vector3d.y;
        double d3 = this.getPosZ() + vector3d.z;
        this.func_234617_x_();
        float f = 0.99f;
        float f2 = 0.06f;
        if (this.world.func_234853_a_(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
            this.remove();
        } else if (this.isInWaterOrBubbleColumn()) {
            this.remove();
        } else {
            this.setMotion(vector3d.scale(0.99f));
            if (!this.hasNoGravity()) {
                this.setMotion(this.getMotion().add(0.0, -0.06f, 0.0));
            }
            this.setPosition(d, d2, d3);
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        Entity entity2 = this.func_234616_v_();
        if (entity2 instanceof LivingEntity) {
            entityRayTraceResult.getEntity().attackEntityFrom(DamageSource.causeIndirectDamage(this, (LivingEntity)entity2).setProjectile(), 1.0f);
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        super.func_230299_a_(blockRayTraceResult);
        if (!this.world.isRemote) {
            this.remove();
        }
    }

    @Override
    protected void registerData() {
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

