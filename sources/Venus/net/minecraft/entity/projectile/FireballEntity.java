/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class FireballEntity
extends AbstractFireballEntity {
    public int explosionPower = 1;

    public FireballEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super((EntityType<? extends AbstractFireballEntity>)entityType, world);
    }

    public FireballEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.FIREBALL, d, d2, d3, d4, d5, d6, world);
    }

    public FireballEntity(World world, LivingEntity livingEntity, double d, double d2, double d3) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.FIREBALL, livingEntity, d, d2, d3, world);
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
            this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), this.explosionPower, bl, bl ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        if (!this.world.isRemote) {
            Entity entity2 = entityRayTraceResult.getEntity();
            Entity entity3 = this.func_234616_v_();
            entity2.attackEntityFrom(DamageSource.func_233547_a_(this, entity3), 6.0f);
            if (entity3 instanceof LivingEntity) {
                this.applyEnchantments((LivingEntity)entity3, entity2);
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("ExplosionPower", this.explosionPower);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("ExplosionPower", 0)) {
            this.explosionPower = compoundNBT.getInt("ExplosionPower");
        }
    }
}

