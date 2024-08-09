/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.DamageSource;
import net.minecraft.world.server.ServerWorld;

public class HurtBySensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        Brain<?> brain = livingEntity.getBrain();
        DamageSource damageSource = livingEntity.getLastDamageSource();
        if (damageSource != null) {
            brain.setMemory(MemoryModuleType.HURT_BY, livingEntity.getLastDamageSource());
            Entity entity2 = damageSource.getTrueSource();
            if (entity2 instanceof LivingEntity) {
                brain.setMemory(MemoryModuleType.HURT_BY_ENTITY, (LivingEntity)entity2);
            }
        } else {
            brain.removeMemory(MemoryModuleType.HURT_BY);
        }
        brain.getMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(arg_0 -> HurtBySensor.lambda$update$0(serverWorld, brain, arg_0));
    }

    private static void lambda$update$0(ServerWorld serverWorld, Brain brain, LivingEntity livingEntity) {
        if (!livingEntity.isAlive() || livingEntity.world != serverWorld) {
            brain.removeMemory(MemoryModuleType.HURT_BY_ENTITY);
        }
    }
}

