/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class DragonFireballEntity
extends DamagingProjectileEntity {
    public DragonFireballEntity(EntityType<? extends DragonFireballEntity> entityType, World world) {
        super((EntityType<? extends DamagingProjectileEntity>)entityType, world);
    }

    public DragonFireballEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        super(EntityType.DRAGON_FIREBALL, d, d2, d3, d4, d5, d6, world);
    }

    public DragonFireballEntity(World world, LivingEntity livingEntity, double d, double d2, double d3) {
        super(EntityType.DRAGON_FIREBALL, livingEntity, d, d2, d3, world);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        Entity entity2 = this.func_234616_v_();
        if (!(rayTraceResult.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult)rayTraceResult).getEntity().isEntityEqual(entity2) || this.world.isRemote)) {
            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0, 2.0, 4.0));
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
            if (entity2 instanceof LivingEntity) {
                areaEffectCloudEntity.setOwner((LivingEntity)entity2);
            }
            areaEffectCloudEntity.setParticleData(ParticleTypes.DRAGON_BREATH);
            areaEffectCloudEntity.setRadius(3.0f);
            areaEffectCloudEntity.setDuration(600);
            areaEffectCloudEntity.setRadiusPerTick((7.0f - areaEffectCloudEntity.getRadius()) / (float)areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.getDistanceSq(livingEntity);
                    if (!(d < 16.0)) continue;
                    areaEffectCloudEntity.setPosition(livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ());
                    break;
                }
            }
            this.world.playEvent(2006, this.getPosition(), this.isSilent() ? -1 : 1);
            this.world.addEntity(areaEffectCloudEntity);
            this.remove();
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return true;
    }

    @Override
    protected IParticleData getParticle() {
        return ParticleTypes.DRAGON_BREATH;
    }

    @Override
    protected boolean isFireballFiery() {
        return true;
    }
}

