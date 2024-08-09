/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SmallFireballEntity
extends AbstractFireballEntity {
    public SmallFireballEntity(EntityType<? extends SmallFireballEntity> entityType, World world) {
        super((EntityType<? extends AbstractFireballEntity>)entityType, world);
    }

    public SmallFireballEntity(World world, LivingEntity livingEntity, double d, double d2, double d3) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.SMALL_FIREBALL, livingEntity, d, d2, d3, world);
    }

    public SmallFireballEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        super((EntityType<? extends AbstractFireballEntity>)EntityType.SMALL_FIREBALL, d, d2, d3, d4, d5, d6, world);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        Entity entity2;
        super.onEntityHit(entityRayTraceResult);
        if (!this.world.isRemote && !(entity2 = entityRayTraceResult.getEntity()).isImmuneToFire()) {
            Entity entity3 = this.func_234616_v_();
            int n = entity2.getFireTimer();
            entity2.setFire(5);
            boolean bl = entity2.attackEntityFrom(DamageSource.func_233547_a_(this, entity3), 5.0f);
            if (!bl) {
                entity2.forceFireTicks(n);
            } else if (entity3 instanceof LivingEntity) {
                this.applyEnchantments((LivingEntity)entity3, entity2);
            }
        }
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        BlockPos blockPos;
        Entity entity2;
        super.func_230299_a_(blockRayTraceResult);
        if (!this.world.isRemote && ((entity2 = this.func_234616_v_()) == null || !(entity2 instanceof MobEntity) || this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && this.world.isAirBlock(blockPos = blockRayTraceResult.getPos().offset(blockRayTraceResult.getFace()))) {
            this.world.setBlockState(blockPos, AbstractFireBlock.getFireForPlacement(this.world, blockPos));
        }
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
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
}

