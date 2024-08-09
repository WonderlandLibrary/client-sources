/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SnowballEntity
extends ProjectileItemEntity {
    public SnowballEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super((EntityType<? extends ProjectileItemEntity>)entityType, world);
    }

    public SnowballEntity(World world, LivingEntity livingEntity) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.SNOWBALL, livingEntity, world);
    }

    public SnowballEntity(World world, double d, double d2, double d3) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.SNOWBALL, d, d2, d3, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    private IParticleData makeParticle() {
        ItemStack itemStack = this.func_213882_k();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemStack);
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 3) {
            IParticleData iParticleData = this.makeParticle();
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(iParticleData, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        Entity entity2 = entityRayTraceResult.getEntity();
        int n = entity2 instanceof BlazeEntity ? 3 : 0;
        entity2.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), n);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }
}

