/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class PiglinMobsSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.MOBS, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEAREST_ADULT_PIGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, MemoryModuleType.NEAREST_REPELLENT);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        Brain<?> brain = livingEntity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, PiglinMobsSensor.findNearestRepellent(serverWorld, livingEntity));
        Optional<Object> optional = Optional.empty();
        Optional<Object> optional2 = Optional.empty();
        Optional<Object> optional3 = Optional.empty();
        Optional<Object> optional4 = Optional.empty();
        Optional<Object> optional5 = Optional.empty();
        Optional<Object> optional6 = Optional.empty();
        Optional<Object> optional7 = Optional.empty();
        int n = 0;
        ArrayList<LivingEntity> arrayList = Lists.newArrayList();
        ArrayList<AbstractPiglinEntity> arrayList2 = Lists.newArrayList();
        for (LivingEntity livingEntity2 : (List)brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
            LivingEntity livingEntity3;
            if (livingEntity2 instanceof HoglinEntity) {
                livingEntity3 = (HoglinEntity)livingEntity2;
                if (((AgeableEntity)livingEntity3).isChild() && !optional3.isPresent()) {
                    optional3 = Optional.of(livingEntity3);
                    continue;
                }
                if (!((HoglinEntity)livingEntity3).func_234363_eJ_()) continue;
                ++n;
                if (optional2.isPresent() || !((HoglinEntity)livingEntity3).func_234365_eM_()) continue;
                optional2 = Optional.of(livingEntity3);
                continue;
            }
            if (livingEntity2 instanceof PiglinBruteEntity) {
                arrayList.add((PiglinBruteEntity)livingEntity2);
                continue;
            }
            if (livingEntity2 instanceof PiglinEntity) {
                livingEntity3 = (PiglinEntity)livingEntity2;
                if (((PiglinEntity)livingEntity3).isChild() && !optional4.isPresent()) {
                    optional4 = Optional.of(livingEntity3);
                    continue;
                }
                if (!((AbstractPiglinEntity)livingEntity3).func_242337_eM()) continue;
                arrayList.add(livingEntity3);
                continue;
            }
            if (livingEntity2 instanceof PlayerEntity) {
                livingEntity3 = (PlayerEntity)livingEntity2;
                if (!optional6.isPresent() && EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity2) && !PiglinTasks.func_234460_a_(livingEntity3)) {
                    optional6 = Optional.of(livingEntity3);
                }
                if (optional7.isPresent() || ((PlayerEntity)livingEntity3).isSpectator() || !PiglinTasks.func_234482_b_(livingEntity3)) continue;
                optional7 = Optional.of(livingEntity3);
                continue;
            }
            if (optional.isPresent() || !(livingEntity2 instanceof WitherSkeletonEntity) && !(livingEntity2 instanceof WitherEntity)) {
                if (optional5.isPresent() || !PiglinTasks.func_234459_a_(livingEntity2.getType())) continue;
                optional5 = Optional.of(livingEntity2);
                continue;
            }
            optional = Optional.of((MobEntity)livingEntity2);
        }
        for (LivingEntity livingEntity2 : (List)brain.getMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
            if (!(livingEntity2 instanceof AbstractPiglinEntity) || !((AbstractPiglinEntity)livingEntity2).func_242337_eM()) continue;
            arrayList2.add((AbstractPiglinEntity)livingEntity2);
        }
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, optional2);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, optional3);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional5);
        brain.setMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, optional6);
        brain.setMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, optional7);
        brain.setMemory(MemoryModuleType.NEAREST_ADULT_PIGLINS, arrayList2);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, arrayList);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, arrayList.size());
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, n);
    }

    private static Optional<BlockPos> findNearestRepellent(ServerWorld serverWorld, LivingEntity livingEntity) {
        return BlockPos.getClosestMatchingPosition(livingEntity.getPosition(), 8, 4, arg_0 -> PiglinMobsSensor.lambda$findNearestRepellent$0(serverWorld, arg_0));
    }

    private static boolean isRepellent(ServerWorld serverWorld, BlockPos blockPos) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        boolean bl = blockState.isIn(BlockTags.PIGLIN_REPELLENTS);
        return bl && blockState.isIn(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLit(blockState) : bl;
    }

    private static boolean lambda$findNearestRepellent$0(ServerWorld serverWorld, BlockPos blockPos) {
        return PiglinMobsSensor.isRepellent(serverWorld, blockPos);
    }
}

