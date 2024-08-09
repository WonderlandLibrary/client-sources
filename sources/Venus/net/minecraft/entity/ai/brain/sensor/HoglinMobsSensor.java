/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HoglinMobsSensor
extends Sensor<HoglinEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_REPELLENT, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, new MemoryModuleType[0]);
    }

    @Override
    protected void update(ServerWorld serverWorld, HoglinEntity hoglinEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(serverWorld, hoglinEntity));
        Optional<Object> optional = Optional.empty();
        int n = 0;
        ArrayList<HoglinEntity> arrayList = Lists.newArrayList();
        for (LivingEntity livingEntity : (List)brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList())) {
            if (livingEntity instanceof PiglinEntity && !livingEntity.isChild()) {
                ++n;
                if (!optional.isPresent()) {
                    optional = Optional.of((PiglinEntity)livingEntity);
                }
            }
            if (!(livingEntity instanceof HoglinEntity) || livingEntity.isChild()) continue;
            arrayList.add((HoglinEntity)livingEntity);
        }
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, optional);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, arrayList);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, n);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, arrayList.size());
    }

    private Optional<BlockPos> findNearestRepellent(ServerWorld serverWorld, HoglinEntity hoglinEntity) {
        return BlockPos.getClosestMatchingPosition(hoglinEntity.getPosition(), 8, 4, arg_0 -> HoglinMobsSensor.lambda$findNearestRepellent$0(serverWorld, arg_0));
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.update(serverWorld, (HoglinEntity)livingEntity);
    }

    private static boolean lambda$findNearestRepellent$0(ServerWorld serverWorld, BlockPos blockPos) {
        return serverWorld.getBlockState(blockPos).isIn(BlockTags.HOGLIN_REPELLENTS);
    }
}

