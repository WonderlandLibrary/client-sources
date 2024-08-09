/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.world.server.ServerWorld;

public class VillagerBabiesSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        livingEntity.getBrain().setMemory(MemoryModuleType.VISIBLE_VILLAGER_BABIES, this.getVisibleVillagerChildren(livingEntity));
    }

    private List<LivingEntity> getVisibleVillagerChildren(LivingEntity livingEntity) {
        return this.getVisibleEntities(livingEntity).stream().filter(this::isVillagerChild).collect(Collectors.toList());
    }

    private boolean isVillagerChild(LivingEntity livingEntity) {
        return livingEntity.getType() == EntityType.VILLAGER && livingEntity.isChild();
    }

    private List<LivingEntity> getVisibleEntities(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList());
    }
}

