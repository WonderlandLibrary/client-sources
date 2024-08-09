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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.world.server.ServerWorld;

public class PiglinBruteSpecificSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.NEAREST_ADULT_PIGLINS);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        Brain<?> brain = livingEntity.getBrain();
        Optional<Object> optional = Optional.empty();
        ArrayList<AbstractPiglinEntity> arrayList = Lists.newArrayList();
        for (LivingEntity livingEntity2 : (List)brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
            if (!(livingEntity2 instanceof WitherSkeletonEntity) && !(livingEntity2 instanceof WitherEntity)) continue;
            optional = Optional.of((MobEntity)livingEntity2);
            break;
        }
        for (LivingEntity livingEntity2 : (List)brain.getMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
            if (!(livingEntity2 instanceof AbstractPiglinEntity) || !((AbstractPiglinEntity)livingEntity2).func_242337_eM()) continue;
            arrayList.add((AbstractPiglinEntity)livingEntity2);
        }
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
        brain.setMemory(MemoryModuleType.NEAREST_ADULT_PIGLINS, arrayList);
    }
}

