/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.TriggerSkeletonTrapGoal;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SkeletonHorseEntity
extends AbstractHorseEntity {
    private final TriggerSkeletonTrapGoal skeletonTrapAI = new TriggerSkeletonTrapGoal(this);
    private boolean skeletonTrap;
    private int skeletonTrapTime;

    public SkeletonHorseEntity(EntityType<? extends SkeletonHorseEntity> entityType, World world) {
        super((EntityType<? extends AbstractHorseEntity>)entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute func_234250_eJ_() {
        return SkeletonHorseEntity.func_234237_fg_().createMutableAttribute(Attributes.MAX_HEALTH, 15.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void func_230273_eI_() {
        this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    @Override
    protected void initExtraAI() {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return this.areEyesInFluid(FluidTags.WATER) ? SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT_WATER : SoundEvents.ENTITY_SKELETON_HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_SKELETON_HORSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        super.getHurtSound(damageSource);
        return SoundEvents.ENTITY_SKELETON_HORSE_HURT;
    }

    @Override
    protected SoundEvent getSwimSound() {
        if (this.onGround) {
            if (!this.isBeingRidden()) {
                return SoundEvents.ENTITY_SKELETON_HORSE_STEP_WATER;
            }
            ++this.gallopTime;
            if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                return SoundEvents.ENTITY_SKELETON_HORSE_GALLOP_WATER;
            }
            if (this.gallopTime <= 5) {
                return SoundEvents.ENTITY_SKELETON_HORSE_STEP_WATER;
            }
        }
        return SoundEvents.ENTITY_SKELETON_HORSE_SWIM;
    }

    @Override
    protected void playSwimSound(float f) {
        if (this.onGround) {
            super.playSwimSound(0.3f);
        } else {
            super.playSwimSound(Math.min(0.1f, f * 25.0f));
        }
    }

    @Override
    protected void playJumpSound() {
        if (this.isInWater()) {
            this.playSound(SoundEvents.ENTITY_SKELETON_HORSE_JUMP_WATER, 0.4f, 1.0f);
        } else {
            super.playJumpSound();
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset() - 0.1875;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isTrap() && this.skeletonTrapTime++ >= 18000) {
            this.remove();
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("SkeletonTrap", this.isTrap());
        compoundNBT.putInt("SkeletonTrapTime", this.skeletonTrapTime);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setTrap(compoundNBT.getBoolean("SkeletonTrap"));
        this.skeletonTrapTime = compoundNBT.getInt("SkeletonTrapTime");
    }

    @Override
    public boolean canBeRiddenInWater() {
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.96f;
    }

    public boolean isTrap() {
        return this.skeletonTrap;
    }

    public void setTrap(boolean bl) {
        if (bl != this.skeletonTrap) {
            this.skeletonTrap = bl;
            if (bl) {
                this.goalSelector.addGoal(1, this.skeletonTrapAI);
            } else {
                this.goalSelector.removeGoal(this.skeletonTrapAI);
            }
        }
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.SKELETON_HORSE.create(serverWorld);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (!this.isTame()) {
            return ActionResultType.PASS;
        }
        if (this.isChild()) {
            return super.func_230254_b_(playerEntity, hand);
        }
        if (playerEntity.isSecondaryUseActive()) {
            this.openGUI(playerEntity);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (this.isBeingRidden()) {
            return super.func_230254_b_(playerEntity, hand);
        }
        if (!itemStack.isEmpty()) {
            if (itemStack.getItem() == Items.SADDLE && !this.isHorseSaddled()) {
                this.openGUI(playerEntity);
                return ActionResultType.func_233537_a_(this.world.isRemote);
            }
            ActionResultType actionResultType = itemStack.interactWithEntity(playerEntity, this, hand);
            if (actionResultType.isSuccessOrConsume()) {
                return actionResultType;
            }
        }
        this.mountTo(playerEntity);
        return ActionResultType.func_233537_a_(this.world.isRemote);
    }
}

