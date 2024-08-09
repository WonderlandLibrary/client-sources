/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;

public class NearestLivingEntitiesSensor
extends Sensor<LivingEntity> {
    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        AxisAlignedBB axisAlignedBB = livingEntity.getBoundingBox().grow(16.0, 16.0, 16.0);
        List<LivingEntity> list = serverWorld.getEntitiesWithinAABB(LivingEntity.class, axisAlignedBB, arg_0 -> NearestLivingEntitiesSensor.lambda$update$0(livingEntity, arg_0));
        list.sort(Comparator.comparingDouble(livingEntity::getDistanceSq));
        Brain<?> brain = livingEntity.getBrain();
        brain.setMemory(MemoryModuleType.MOBS, list);
        brain.setMemory(MemoryModuleType.VISIBLE_MOBS, list.stream().filter(arg_0 -> NearestLivingEntitiesSensor.lambda$update$1(livingEntity, arg_0)).collect(Collectors.toList()));
    }

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
    }

    private static boolean lambda$update$1(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return NearestLivingEntitiesSensor.canAttackTarget(livingEntity, livingEntity2);
    }

    private static boolean lambda$update$0(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2 != livingEntity && livingEntity2.isAlive();
    }
}

