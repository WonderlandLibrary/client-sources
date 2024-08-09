/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ExperienceBottleEntity
extends ProjectileItemEntity {
    public ExperienceBottleEntity(EntityType<? extends ExperienceBottleEntity> entityType, World world) {
        super((EntityType<? extends ProjectileItemEntity>)entityType, world);
    }

    public ExperienceBottleEntity(World world, LivingEntity livingEntity) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.EXPERIENCE_BOTTLE, livingEntity, world);
    }

    public ExperienceBottleEntity(World world, double d, double d2, double d3) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.EXPERIENCE_BOTTLE, d, d2, d3, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EXPERIENCE_BOTTLE;
    }

    @Override
    public float getGravityVelocity() {
        return 0.07f;
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            int n;
            this.world.playEvent(2002, this.getPosition(), PotionUtils.getPotionColor(Potions.WATER));
            for (int i = 3 + this.world.rand.nextInt(5) + this.world.rand.nextInt(5); i > 0; i -= n) {
                n = ExperienceOrbEntity.getXPSplit(i);
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), n));
            }
            this.remove();
        }
    }
}

