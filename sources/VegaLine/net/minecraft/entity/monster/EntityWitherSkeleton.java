/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityWitherSkeleton
extends AbstractSkeleton {
    public EntityWitherSkeleton(World p_i47278_1_) {
        super(p_i47278_1_);
        this.setSize(0.7f, 2.4f);
        this.isImmuneToFire = true;
    }

    public static void func_190729_b(DataFixer p_190729_0_) {
        EntityLiving.registerFixesMob(p_190729_0_, EntityWitherSkeleton.class);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_WITHER_SKELETON;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
    }

    @Override
    SoundEvent func_190727_o() {
        return SoundEvents.ENTITY_WITHER_SKELETON_STEP;
    }

    @Override
    public void onDeath(DamageSource cause) {
        EntityCreeper entitycreeper;
        super.onDeath(cause);
        if (cause.getEntity() instanceof EntityCreeper && (entitycreeper = (EntityCreeper)cause.getEntity()).getPowered() && entitycreeper.isAIEnabled()) {
            entitycreeper.incrementDroppedSkulls();
            this.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0f);
        }
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    @Override
    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
        this.setCombatTask();
        return ientitylivingdata;
    }

    @Override
    public float getEyeHeight() {
        return 2.1f;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        }
        if (entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
        }
        return true;
    }

    @Override
    protected EntityArrow func_190726_a(float p_190726_1_) {
        EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
        entityarrow.setFire(100);
        return entityarrow;
    }
}

