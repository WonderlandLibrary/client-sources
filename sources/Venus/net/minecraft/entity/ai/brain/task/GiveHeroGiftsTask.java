/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class GiveHeroGiftsTask
extends Task<VillagerEntity> {
    private static final Map<VillagerProfession, ResourceLocation> GIFTS = Util.make(Maps.newHashMap(), GiveHeroGiftsTask::lambda$static$0);
    private int cooldown = 600;
    private boolean done;
    private long startTime;

    public GiveHeroGiftsTask(int n) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleStatus.VALUE_PRESENT), n);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        if (!this.hasNearestPlayer(villagerEntity)) {
            return true;
        }
        if (this.cooldown > 0) {
            --this.cooldown;
            return true;
        }
        return false;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.done = false;
        this.startTime = l;
        PlayerEntity playerEntity = this.getNearestPlayer(villagerEntity).get();
        villagerEntity.getBrain().setMemory(MemoryModuleType.INTERACTION_TARGET, playerEntity);
        BrainUtil.lookAt(villagerEntity, playerEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        return this.hasNearestPlayer(villagerEntity) && !this.done;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        PlayerEntity playerEntity = this.getNearestPlayer(villagerEntity).get();
        BrainUtil.lookAt(villagerEntity, playerEntity);
        if (this.isCloseEnough(villagerEntity, playerEntity)) {
            if (l - this.startTime > 20L) {
                this.giveGifts(villagerEntity, playerEntity);
                this.done = true;
            }
        } else {
            BrainUtil.setTargetEntity(villagerEntity, playerEntity, 0.5f, 5);
        }
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        this.cooldown = GiveHeroGiftsTask.getNextCooldown(serverWorld);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.INTERACTION_TARGET);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        villagerEntity.getBrain().removeMemory(MemoryModuleType.LOOK_TARGET);
    }

    private void giveGifts(VillagerEntity villagerEntity, LivingEntity livingEntity) {
        for (ItemStack itemStack : this.getGifts(villagerEntity)) {
            BrainUtil.spawnItemNearEntity(villagerEntity, itemStack, livingEntity.getPositionVec());
        }
    }

    private List<ItemStack> getGifts(VillagerEntity villagerEntity) {
        if (villagerEntity.isChild()) {
            return ImmutableList.of(new ItemStack(Items.POPPY));
        }
        VillagerProfession villagerProfession = villagerEntity.getVillagerData().getProfession();
        if (GIFTS.containsKey(villagerProfession)) {
            LootTable lootTable = villagerEntity.world.getServer().getLootTableManager().getLootTableFromLocation(GIFTS.get(villagerProfession));
            LootContext.Builder builder = new LootContext.Builder((ServerWorld)villagerEntity.world).withParameter(LootParameters.field_237457_g_, villagerEntity.getPositionVec()).withParameter(LootParameters.THIS_ENTITY, villagerEntity).withRandom(villagerEntity.getRNG());
            return lootTable.generate(builder.build(LootParameterSets.GIFT));
        }
        return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
    }

    private boolean hasNearestPlayer(VillagerEntity villagerEntity) {
        return this.getNearestPlayer(villagerEntity).isPresent();
    }

    private Optional<PlayerEntity> getNearestPlayer(VillagerEntity villagerEntity) {
        return villagerEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).filter(this::isHero);
    }

    private boolean isHero(PlayerEntity playerEntity) {
        return playerEntity.isPotionActive(Effects.HERO_OF_THE_VILLAGE);
    }

    private boolean isCloseEnough(VillagerEntity villagerEntity, PlayerEntity playerEntity) {
        BlockPos blockPos = playerEntity.getPosition();
        BlockPos blockPos2 = villagerEntity.getPosition();
        return blockPos2.withinDistance(blockPos, 5.0);
    }

    private static int getNextCooldown(ServerWorld serverWorld) {
        return 600 + serverWorld.rand.nextInt(6001);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (VillagerEntity)livingEntity, l);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put(VillagerProfession.ARMORER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_ARMORER_GIFT);
        hashMap.put(VillagerProfession.BUTCHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_BUTCHER_GIFT);
        hashMap.put(VillagerProfession.CARTOGRAPHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT);
        hashMap.put(VillagerProfession.CLERIC, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_CLERIC_GIFT);
        hashMap.put(VillagerProfession.FARMER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FARMER_GIFT);
        hashMap.put(VillagerProfession.FISHERMAN, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FISHERMAN_GIFT);
        hashMap.put(VillagerProfession.FLETCHER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_FLETCHER_GIFT);
        hashMap.put(VillagerProfession.LEATHERWORKER, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT);
        hashMap.put(VillagerProfession.LIBRARIAN, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT);
        hashMap.put(VillagerProfession.MASON, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_MASON_GIFT);
        hashMap.put(VillagerProfession.SHEPHERD, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_SHEPHERD_GIFT);
        hashMap.put(VillagerProfession.TOOLSMITH, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT);
        hashMap.put(VillagerProfession.WEAPONSMITH, LootTables.GAMEPLAY_HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT);
    }
}

