/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;

public class NearestPlayersSensor
extends Sensor<LivingEntity> {
    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        List list = serverWorld.getPlayers().stream().filter(EntityPredicates.NOT_SPECTATING).filter(arg_0 -> NearestPlayersSensor.lambda$update$0(livingEntity, arg_0)).sorted(Comparator.comparingDouble(livingEntity::getDistanceSq)).collect(Collectors.toList());
        Brain<?> brain = livingEntity.getBrain();
        brain.setMemory(MemoryModuleType.NEAREST_PLAYERS, list);
        List list2 = list.stream().filter(arg_0 -> NearestPlayersSensor.lambda$update$1(livingEntity, arg_0)).collect(Collectors.toList());
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER, list2.isEmpty() ? null : (PlayerEntity)list2.get(0));
        Optional<Entity> optional = list2.stream().filter(EntityPredicates.CAN_HOSTILE_AI_TARGET).findFirst();
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, optional);
    }

    private static boolean lambda$update$1(LivingEntity livingEntity, PlayerEntity playerEntity) {
        return NearestPlayersSensor.canAttackTarget(livingEntity, playerEntity);
    }

    private static boolean lambda$update$0(LivingEntity livingEntity, ServerPlayerEntity serverPlayerEntity) {
        return livingEntity.isEntityInRange(serverPlayerEntity, 16.0);
    }
}

