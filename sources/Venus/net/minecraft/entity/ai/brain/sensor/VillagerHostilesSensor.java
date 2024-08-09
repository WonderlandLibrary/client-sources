/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class VillagerHostilesSensor
extends Sensor<LivingEntity> {
    private static final ImmutableMap<EntityType<?>, Float> enemyPresenceRange = ImmutableMap.builder().put(EntityType.DROWNED, Float.valueOf(8.0f)).put(EntityType.EVOKER, Float.valueOf(12.0f)).put(EntityType.HUSK, Float.valueOf(8.0f)).put(EntityType.ILLUSIONER, Float.valueOf(12.0f)).put(EntityType.PILLAGER, Float.valueOf(15.0f)).put(EntityType.RAVAGER, Float.valueOf(12.0f)).put(EntityType.VEX, Float.valueOf(8.0f)).put(EntityType.VINDICATOR, Float.valueOf(10.0f)).put(EntityType.ZOGLIN, Float.valueOf(10.0f)).put(EntityType.ZOMBIE, Float.valueOf(8.0f)).put(EntityType.ZOMBIE_VILLAGER, Float.valueOf(8.0f)).build();

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_HOSTILE);
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        livingEntity.getBrain().setMemory(MemoryModuleType.NEAREST_HOSTILE, this.findNearestHostile(livingEntity));
    }

    private Optional<LivingEntity> findNearestHostile(LivingEntity livingEntity) {
        return this.getVisibleEntities(livingEntity).flatMap(arg_0 -> this.lambda$findNearestHostile$2(livingEntity, arg_0));
    }

    private Optional<List<LivingEntity>> getVisibleEntities(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS);
    }

    private int compareHostileDistances(LivingEntity livingEntity, LivingEntity livingEntity2, LivingEntity livingEntity3) {
        return MathHelper.floor(livingEntity2.getDistanceSq(livingEntity) - livingEntity3.getDistanceSq(livingEntity));
    }

    private boolean canNoticePresence(LivingEntity livingEntity, LivingEntity livingEntity2) {
        float f = enemyPresenceRange.get(livingEntity2.getType()).floatValue();
        return livingEntity2.getDistanceSq(livingEntity) <= (double)(f * f);
    }

    private boolean hasPresence(LivingEntity livingEntity) {
        return enemyPresenceRange.containsKey(livingEntity.getType());
    }

    private Optional lambda$findNearestHostile$2(LivingEntity livingEntity, List list) {
        return list.stream().filter(this::hasPresence).filter(arg_0 -> this.lambda$findNearestHostile$0(livingEntity, arg_0)).min((arg_0, arg_1) -> this.lambda$findNearestHostile$1(livingEntity, arg_0, arg_1));
    }

    private int lambda$findNearestHostile$1(LivingEntity livingEntity, LivingEntity livingEntity2, LivingEntity livingEntity3) {
        return this.compareHostileDistances(livingEntity, livingEntity2, livingEntity3);
    }

    private boolean lambda$findNearestHostile$0(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return this.canNoticePresence(livingEntity, livingEntity2);
    }
}

