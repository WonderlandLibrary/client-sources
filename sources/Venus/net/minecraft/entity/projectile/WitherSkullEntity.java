/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WitherSkullEntity
extends DamagingProjectileEntity {
    private static final DataParameter<Boolean> INVULNERABLE = EntityDataManager.createKey(WitherSkullEntity.class, DataSerializers.BOOLEAN);

    public WitherSkullEntity(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super((EntityType<? extends DamagingProjectileEntity>)entityType, world);
    }

    public WitherSkullEntity(World world, LivingEntity livingEntity, double d, double d2, double d3) {
        super(EntityType.WITHER_SKULL, livingEntity, d, d2, d3, world);
    }

    public WitherSkullEntity(World world, double d, double d2, double d3, double d4, double d5, double d6) {
        super(EntityType.WITHER_SKULL, d, d2, d3, d4, d5, d6, world);
    }

    @Override
    protected float getMotionFactor() {
        return this.isSkullInvulnerable() ? 0.73f : super.getMotionFactor();
    }

    @Override
    public boolean isBurning() {
        return true;
    }

    @Override
    public float getExplosionResistance(Explosion explosion, IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, FluidState fluidState, float f) {
        return this.isSkullInvulnerable() && WitherEntity.canDestroyBlock(blockState) ? Math.min(0.8f, f) : f;
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult entityRayTraceResult) {
        super.onEntityHit(entityRayTraceResult);
        if (!this.world.isRemote) {
            boolean bl;
            Entity entity2 = entityRayTraceResult.getEntity();
            Entity entity3 = this.func_234616_v_();
            if (entity3 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity3;
                bl = entity2.attackEntityFrom(DamageSource.func_233549_a_(this, livingEntity), 8.0f);
                if (bl) {
                    if (entity2.isAlive()) {
                        this.applyEnchantments(livingEntity, entity2);
                    } else {
                        livingEntity.heal(5.0f);
                    }
                }
            } else {
                bl = entity2.attackEntityFrom(DamageSource.MAGIC, 5.0f);
            }
            if (bl && entity2 instanceof LivingEntity) {
                int n = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    n = 10;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    n = 40;
                }
                if (n > 0) {
                    ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.WITHER, 20 * n, 1));
                }
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            Explosion.Mode mode = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0f, false, mode);
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
    protected void registerData() {
        this.dataManager.register(INVULNERABLE, false);
    }

    public boolean isSkullInvulnerable() {
        return this.dataManager.get(INVULNERABLE);
    }

    public void setSkullInvulnerable(boolean bl) {
        this.dataManager.set(INVULNERABLE, bl);
    }

    @Override
    protected boolean isFireballFiery() {
        return true;
    }
}

