/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;

public class MateSensor
extends Sensor<AgeableEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.VISIBLE_MOBS);
    }

    @Override
    protected void update(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        ageableEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(arg_0 -> this.lambda$update$0(ageableEntity, arg_0));
    }

    private void addMemory(AgeableEntity ageableEntity, List<LivingEntity> list) {
        Optional<AgeableEntity> optional = list.stream().filter(arg_0 -> MateSensor.lambda$addMemory$1(ageableEntity, arg_0)).map(MateSensor::lambda$addMemory$2).filter(MateSensor::lambda$addMemory$3).findFirst();
        ageableEntity.getBrain().setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT, optional);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.update(serverWorld, (AgeableEntity)livingEntity);
    }

    private static boolean lambda$addMemory$3(AgeableEntity ageableEntity) {
        return !ageableEntity.isChild();
    }

    private static AgeableEntity lambda$addMemory$2(LivingEntity livingEntity) {
        return (AgeableEntity)livingEntity;
    }

    private static boolean lambda$addMemory$1(AgeableEntity ageableEntity, LivingEntity livingEntity) {
        return livingEntity.getType() == ageableEntity.getType();
    }

    private void lambda$update$0(AgeableEntity ageableEntity, List list) {
        this.addMemory(ageableEntity, list);
    }
}

