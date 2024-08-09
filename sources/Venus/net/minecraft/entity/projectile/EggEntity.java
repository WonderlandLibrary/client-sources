/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EggEntity
extends ProjectileItemEntity {
    public EggEntity(EntityType<? extends EggEntity> entityType, World world) {
        super((EntityType<? extends ProjectileItemEntity>)entityType, world);
    }

    public EggEntity(World world, LivingEntity livingEntity) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.EGG, livingEntity, world);
    }

    public EggEntity(World world, double d, double d2, double d3) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.EGG, d, d2, d3, world);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 3) {
            double d = 0.08;
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5) * 0.08, ((double)this.rand.nextFloat() - 0.5) * 0.08, ((double)this.rand.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        entityRayTraceResult.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0f);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            if (this.rand.nextInt(8) == 0) {
                int n = 1;
                if (this.rand.nextInt(32) == 0) {
                    n = 4;
                }
                for (int i = 0; i < n; ++i) {
                    ChickenEntity chickenEntity = EntityType.CHICKEN.create(this.world);
                    chickenEntity.setGrowingAge(-24000);
                    chickenEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0f);
                    this.world.addEntity(chickenEntity);
                }
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }
}

