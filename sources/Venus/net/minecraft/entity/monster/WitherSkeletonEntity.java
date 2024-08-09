/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class WitherSkeletonEntity
extends AbstractSkeletonEntity {
    public WitherSkeletonEntity(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super((EntityType<? extends AbstractSkeletonEntity>)entityType, world);
        this.setPathPriority(PathNodeType.LAVA, 8.0f);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractPiglinEntity>((MobEntity)this, AbstractPiglinEntity.class, true));
        super.registerGoals();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
    }

    @Override
    SoundEvent getStepSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_STEP;
    }

    @Override
    protected void dropSpecialItems(DamageSource damageSource, int n, boolean bl) {
        CreeperEntity creeperEntity;
        super.dropSpecialItems(damageSource, n, bl);
        Entity entity2 = damageSource.getTrueSource();
        if (entity2 instanceof CreeperEntity && (creeperEntity = (CreeperEntity)entity2).ableToCauseSkullDrop()) {
            creeperEntity.incrementDroppedSkulls();
            this.entityDropItem(Items.WITHER_SKELETON_SKULL);
        }
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    @Override
    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        ILivingEntityData iLivingEntityData2 = super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0);
        this.setCombatTask();
        return iLivingEntityData2;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 2.1f;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity2) {
        if (!super.attackEntityAsMob(entity2)) {
            return true;
        }
        if (entity2 instanceof LivingEntity) {
            ((LivingEntity)entity2).addPotionEffect(new EffectInstance(Effects.WITHER, 200));
        }
        return false;
    }

    @Override
    protected AbstractArrowEntity fireArrow(ItemStack itemStack, float f) {
        AbstractArrowEntity abstractArrowEntity = super.fireArrow(itemStack, f);
        abstractArrowEntity.setFire(100);
        return abstractArrowEntity;
    }

    @Override
    public boolean isPotionApplicable(EffectInstance effectInstance) {
        return effectInstance.getPotion() == Effects.WITHER ? false : super.isPotionApplicable(effectInstance);
    }
}

