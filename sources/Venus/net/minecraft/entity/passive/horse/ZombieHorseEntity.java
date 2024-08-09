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
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ZombieHorseEntity
extends AbstractHorseEntity {
    public ZombieHorseEntity(EntityType<? extends ZombieHorseEntity> entityType, World world) {
        super((EntityType<? extends AbstractHorseEntity>)entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute func_234256_eJ_() {
        return ZombieHorseEntity.func_234237_fg_().createMutableAttribute(Attributes.MAX_HEALTH, 15.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void func_230273_eI_() {
        this.getAttribute(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.ENTITY_ZOMBIE_HORSE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.ENTITY_ZOMBIE_HORSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        super.getHurtSound(damageSource);
        return SoundEvents.ENTITY_ZOMBIE_HORSE_HURT;
    }

    @Override
    @Nullable
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return EntityType.ZOMBIE_HORSE.create(serverWorld);
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

    @Override
    protected void initExtraAI() {
    }
}

