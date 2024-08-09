/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;

public class GolemLastSeenSensor
extends Sensor<LivingEntity> {
    public GolemLastSeenSensor() {
        this(200);
    }

    public GolemLastSeenSensor(int n) {
        super(n);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        GolemLastSeenSensor.update(livingEntity);
    }

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.MOBS);
    }

    public static void update(LivingEntity livingEntity) {
        boolean bl;
        Optional<List<LivingEntity>> optional = livingEntity.getBrain().getMemory(MemoryModuleType.MOBS);
        if (optional.isPresent() && (bl = optional.get().stream().anyMatch(GolemLastSeenSensor::lambda$update$0))) {
            GolemLastSeenSensor.reset(livingEntity);
        }
    }

    public static void reset(LivingEntity livingEntity) {
        livingEntity.getBrain().replaceMemory(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, 600L);
    }

    private static boolean lambda$update$0(LivingEntity livingEntity) {
        return livingEntity.getType().equals(EntityType.IRON_GOLEM);
    }
}

