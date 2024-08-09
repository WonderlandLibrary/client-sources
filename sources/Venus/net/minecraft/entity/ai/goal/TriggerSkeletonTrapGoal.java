/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.server.ServerWorld;

public class TriggerSkeletonTrapGoal
extends Goal {
    private final SkeletonHorseEntity horse;

    public TriggerSkeletonTrapGoal(SkeletonHorseEntity skeletonHorseEntity) {
        this.horse = skeletonHorseEntity;
    }

    @Override
    public boolean shouldExecute() {
        return this.horse.world.isPlayerWithin(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ(), 10.0);
    }

    @Override
    public void tick() {
        ServerWorld serverWorld = (ServerWorld)this.horse.world;
        DifficultyInstance difficultyInstance = serverWorld.getDifficultyForLocation(this.horse.getPosition());
        this.horse.setTrap(true);
        this.horse.setHorseTamed(false);
        this.horse.setGrowingAge(0);
        LightningBoltEntity lightningBoltEntity = EntityType.LIGHTNING_BOLT.create(serverWorld);
        lightningBoltEntity.moveForced(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ());
        lightningBoltEntity.setEffectOnly(false);
        serverWorld.addEntity(lightningBoltEntity);
        SkeletonEntity skeletonEntity = this.createSkeleton(difficultyInstance, this.horse);
        skeletonEntity.startRiding(this.horse);
        serverWorld.func_242417_l(skeletonEntity);
        for (int i = 0; i < 3; ++i) {
            AbstractHorseEntity abstractHorseEntity = this.createHorse(difficultyInstance);
            SkeletonEntity skeletonEntity2 = this.createSkeleton(difficultyInstance, abstractHorseEntity);
            skeletonEntity2.startRiding(abstractHorseEntity);
            abstractHorseEntity.addVelocity(this.horse.getRNG().nextGaussian() * 0.5, 0.0, this.horse.getRNG().nextGaussian() * 0.5);
            serverWorld.func_242417_l(abstractHorseEntity);
        }
    }

    private AbstractHorseEntity createHorse(DifficultyInstance difficultyInstance) {
        SkeletonHorseEntity skeletonHorseEntity = EntityType.SKELETON_HORSE.create(this.horse.world);
        skeletonHorseEntity.onInitialSpawn((ServerWorld)this.horse.world, difficultyInstance, SpawnReason.TRIGGERED, null, null);
        skeletonHorseEntity.setPosition(this.horse.getPosX(), this.horse.getPosY(), this.horse.getPosZ());
        skeletonHorseEntity.hurtResistantTime = 60;
        skeletonHorseEntity.enablePersistence();
        skeletonHorseEntity.setHorseTamed(false);
        skeletonHorseEntity.setGrowingAge(0);
        return skeletonHorseEntity;
    }

    private SkeletonEntity createSkeleton(DifficultyInstance difficultyInstance, AbstractHorseEntity abstractHorseEntity) {
        SkeletonEntity skeletonEntity = EntityType.SKELETON.create(abstractHorseEntity.world);
        skeletonEntity.onInitialSpawn((ServerWorld)abstractHorseEntity.world, difficultyInstance, SpawnReason.TRIGGERED, null, null);
        skeletonEntity.setPosition(abstractHorseEntity.getPosX(), abstractHorseEntity.getPosY(), abstractHorseEntity.getPosZ());
        skeletonEntity.hurtResistantTime = 60;
        skeletonEntity.enablePersistence();
        if (skeletonEntity.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty()) {
            skeletonEntity.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.IRON_HELMET));
        }
        skeletonEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, EnchantmentHelper.addRandomEnchantment(skeletonEntity.getRNG(), this.func_242327_a(skeletonEntity.getHeldItemMainhand()), (int)(5.0f + difficultyInstance.getClampedAdditionalDifficulty() * (float)skeletonEntity.getRNG().nextInt(18)), false));
        skeletonEntity.setItemStackToSlot(EquipmentSlotType.HEAD, EnchantmentHelper.addRandomEnchantment(skeletonEntity.getRNG(), this.func_242327_a(skeletonEntity.getItemStackFromSlot(EquipmentSlotType.HEAD)), (int)(5.0f + difficultyInstance.getClampedAdditionalDifficulty() * (float)skeletonEntity.getRNG().nextInt(18)), false));
        return skeletonEntity;
    }

    private ItemStack func_242327_a(ItemStack itemStack) {
        itemStack.removeChildTag("Enchantments");
        return itemStack;
    }
}

