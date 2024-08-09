/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.server.ServerWorld;

public class WantedItemsSensor
extends Sensor<MobEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
    }

    @Override
    protected void update(ServerWorld serverWorld, MobEntity mobEntity) {
        Brain<?> brain = mobEntity.getBrain();
        List<ItemEntity> list = serverWorld.getEntitiesWithinAABB(ItemEntity.class, mobEntity.getBoundingBox().grow(8.0, 4.0, 8.0), WantedItemsSensor::lambda$update$0);
        list.sort(Comparator.comparingDouble(mobEntity::getDistanceSq));
        Optional<ItemEntity> optional = list.stream().filter(arg_0 -> WantedItemsSensor.lambda$update$1(mobEntity, arg_0)).filter(arg_0 -> WantedItemsSensor.lambda$update$2(mobEntity, arg_0)).filter(mobEntity::canEntityBeSeen).findFirst();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, optional);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.update(serverWorld, (MobEntity)livingEntity);
    }

    private static boolean lambda$update$2(MobEntity mobEntity, ItemEntity itemEntity) {
        return itemEntity.isEntityInRange(mobEntity, 9.0);
    }

    private static boolean lambda$update$1(MobEntity mobEntity, ItemEntity itemEntity) {
        return mobEntity.func_230293_i_(itemEntity.getItem());
    }

    private static boolean lambda$update$0(ItemEntity itemEntity) {
        return false;
    }
}

