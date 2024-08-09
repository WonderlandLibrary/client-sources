/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class PotionEntity
extends ProjectileItemEntity
implements IRendersAsItem {
    public static final Predicate<LivingEntity> WATER_SENSITIVE = LivingEntity::isWaterSensitive;

    public PotionEntity(EntityType<? extends PotionEntity> entityType, World world) {
        super((EntityType<? extends ProjectileItemEntity>)entityType, world);
    }

    public PotionEntity(World world, LivingEntity livingEntity) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.POTION, livingEntity, world);
    }

    public PotionEntity(World world, double d, double d2, double d3) {
        super((EntityType<? extends ProjectileItemEntity>)EntityType.POTION, d, d2, d3, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SPLASH_POTION;
    }

    @Override
    public float getGravityVelocity() {
        return 0.05f;
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult blockRayTraceResult) {
        super.func_230299_a_(blockRayTraceResult);
        if (!this.world.isRemote) {
            ItemStack itemStack = this.getItem();
            Potion potion = PotionUtils.getPotionFromItem(itemStack);
            List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemStack);
            boolean bl = potion == Potions.WATER && list.isEmpty();
            Direction direction = blockRayTraceResult.getFace();
            BlockPos blockPos = blockRayTraceResult.getPos();
            BlockPos blockPos2 = blockPos.offset(direction);
            if (bl) {
                this.extinguishFires(blockPos2, direction);
                this.extinguishFires(blockPos2.offset(direction.getOpposite()), direction);
                for (Direction direction2 : Direction.Plane.HORIZONTAL) {
                    this.extinguishFires(blockPos2.offset(direction2), direction2);
                }
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult rayTraceResult) {
        super.onImpact(rayTraceResult);
        if (!this.world.isRemote) {
            boolean bl;
            ItemStack itemStack = this.getItem();
            Potion potion = PotionUtils.getPotionFromItem(itemStack);
            List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemStack);
            boolean bl2 = bl = potion == Potions.WATER && list.isEmpty();
            if (bl) {
                this.applyWater();
            } else if (!list.isEmpty()) {
                if (this.isLingering()) {
                    this.makeAreaOfEffectCloud(itemStack, potion);
                } else {
                    this.func_213888_a(list, rayTraceResult.getType() == RayTraceResult.Type.ENTITY ? ((EntityRayTraceResult)rayTraceResult).getEntity() : null);
                }
            }
            int n = potion.hasInstantEffect() ? 2007 : 2002;
            this.world.playEvent(n, this.getPosition(), PotionUtils.getColor(itemStack));
            this.remove();
        }
    }

    private void applyWater() {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox().grow(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB, WATER_SENSITIVE);
        if (!list.isEmpty()) {
            for (LivingEntity livingEntity : list) {
                double d = this.getDistanceSq(livingEntity);
                if (!(d < 16.0) || !livingEntity.isWaterSensitive()) continue;
                livingEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(livingEntity, this.func_234616_v_()), 1.0f);
            }
        }
    }

    private void func_213888_a(List<EffectInstance> list, @Nullable Entity entity2) {
        AxisAlignedBB axisAlignedBB = this.getBoundingBox().grow(4.0, 2.0, 4.0);
        List<LivingEntity> list2 = this.world.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB);
        if (!list2.isEmpty()) {
            for (LivingEntity livingEntity : list2) {
                double d;
                if (!livingEntity.canBeHitWithPotion() || !((d = this.getDistanceSq(livingEntity)) < 16.0)) continue;
                double d2 = 1.0 - Math.sqrt(d) / 4.0;
                if (livingEntity == entity2) {
                    d2 = 1.0;
                }
                for (EffectInstance effectInstance : list) {
                    Effect effect = effectInstance.getPotion();
                    if (effect.isInstant()) {
                        effect.affectEntity(this, this.func_234616_v_(), livingEntity, effectInstance.getAmplifier(), d2);
                        continue;
                    }
                    int n = (int)(d2 * (double)effectInstance.getDuration() + 0.5);
                    if (n <= 20) continue;
                    livingEntity.addPotionEffect(new EffectInstance(effect, n, effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.doesShowParticles()));
                }
            }
        }
    }

    private void makeAreaOfEffectCloud(ItemStack itemStack, Potion potion) {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
        Entity entity2 = this.func_234616_v_();
        if (entity2 instanceof LivingEntity) {
            areaEffectCloudEntity.setOwner((LivingEntity)entity2);
        }
        areaEffectCloudEntity.setRadius(3.0f);
        areaEffectCloudEntity.setRadiusOnUse(-0.5f);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        areaEffectCloudEntity.setPotion(potion);
        for (EffectInstance effectInstance : PotionUtils.getFullEffectsFromItem(itemStack)) {
            areaEffectCloudEntity.addEffect(new EffectInstance(effectInstance));
        }
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && compoundNBT.contains("CustomPotionColor", 0)) {
            areaEffectCloudEntity.setColor(compoundNBT.getInt("CustomPotionColor"));
        }
        this.world.addEntity(areaEffectCloudEntity);
    }

    private boolean isLingering() {
        return this.getItem().getItem() == Items.LINGERING_POTION;
    }

    private void extinguishFires(BlockPos blockPos, Direction direction) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.FIRE)) {
            this.world.removeBlock(blockPos, true);
        } else if (CampfireBlock.isLit(blockState)) {
            this.world.playEvent(null, 1009, blockPos, 0);
            CampfireBlock.extinguish(this.world, blockPos, blockState);
            this.world.setBlockState(blockPos, (BlockState)blockState.with(CampfireBlock.LIT, false));
        }
    }
}

