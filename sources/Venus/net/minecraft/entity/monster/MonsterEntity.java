/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public abstract class MonsterEntity
extends CreatureEntity
implements IMob {
    protected MonsterEntity(EntityType<? extends MonsterEntity> entityType, World world) {
        super((EntityType<? extends CreatureEntity>)entityType, world);
        this.experienceValue = 5;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public void livingTick() {
        this.updateArmSwingProgress();
        this.idle();
        super.livingTick();
    }

    protected void idle() {
        float f = this.getBrightness();
        if (f > 0.5f) {
            this.idleTime += 2;
        }
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return false;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isInvulnerableTo(damageSource) ? false : super.attackEntityFrom(damageSource, f);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_HOSTILE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HOSTILE_DEATH;
    }

    @Override
    protected SoundEvent getFallSound(int n) {
        return n > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return 0.5f - iWorldReader.getBrightness(blockPos);
    }

    public static boolean isValidLightLevel(IServerWorld iServerWorld, BlockPos blockPos, Random random2) {
        if (iServerWorld.getLightFor(LightType.SKY, blockPos) > random2.nextInt(32)) {
            return true;
        }
        int n = iServerWorld.getWorld().isThundering() ? iServerWorld.getNeighborAwareLightSubtracted(blockPos, 10) : iServerWorld.getLight(blockPos);
        return n <= random2.nextInt(8);
    }

    public static boolean canMonsterSpawnInLight(EntityType<? extends MonsterEntity> entityType, IServerWorld iServerWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iServerWorld.getDifficulty() != Difficulty.PEACEFUL && MonsterEntity.isValidLightLevel(iServerWorld, blockPos, random2) && MonsterEntity.canSpawnOn(entityType, iServerWorld, spawnReason, blockPos, random2);
    }

    public static boolean canMonsterSpawn(EntityType<? extends MonsterEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getDifficulty() != Difficulty.PEACEFUL && MonsterEntity.canSpawnOn(entityType, iWorld, spawnReason, blockPos, random2);
    }

    public static AttributeModifierMap.MutableAttribute func_234295_eP_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected boolean canDropLoot() {
        return false;
    }

    @Override
    protected boolean func_230282_cS_() {
        return false;
    }

    public boolean func_230292_f_(PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public ItemStack findAmmo(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ShootableItem) {
            Predicate<ItemStack> predicate = ((ShootableItem)itemStack.getItem()).getAmmoPredicate();
            ItemStack itemStack2 = ShootableItem.getHeldAmmo(this, predicate);
            return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
        }
        return ItemStack.EMPTY;
    }
}

