/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class NearestBedSensor
extends Sensor<MobEntity> {
    private final Long2LongMap bedPositionToTimeMap = new Long2LongOpenHashMap();
    private int bedsFound;
    private long persistTime;

    public NearestBedSensor() {
        super(20);
    }

    @Override
    public Set<MemoryModuleType<?>> getUsedMemories() {
        return ImmutableSet.of(MemoryModuleType.NEAREST_BED);
    }

    @Override
    protected void update(ServerWorld serverWorld, MobEntity mobEntity) {
        if (mobEntity.isChild()) {
            this.bedsFound = 0;
            this.persistTime = serverWorld.getGameTime() + (long)serverWorld.getRandom().nextInt(20);
            PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
            Predicate<BlockPos> predicate = this::lambda$update$0;
            Stream<BlockPos> stream = pointOfInterestManager.findAll(PointOfInterestType.HOME.getPredicate(), predicate, mobEntity.getPosition(), 48, PointOfInterestManager.Status.ANY);
            Path path = mobEntity.getNavigator().pathfind(stream, PointOfInterestType.HOME.getValidRange());
            if (path != null && path.reachesTarget()) {
                BlockPos blockPos = path.getTarget();
                Optional<PointOfInterestType> optional = pointOfInterestManager.getType(blockPos);
                if (optional.isPresent()) {
                    mobEntity.getBrain().setMemory(MemoryModuleType.NEAREST_BED, blockPos);
                }
            } else if (this.bedsFound < 5) {
                this.bedPositionToTimeMap.long2LongEntrySet().removeIf(this::lambda$update$1);
            }
        }
    }

    @Override
    protected void update(ServerWorld serverWorld, LivingEntity livingEntity) {
        this.update(serverWorld, (MobEntity)livingEntity);
    }

    private boolean lambda$update$1(Long2LongMap.Entry entry) {
        return entry.getLongValue() < this.persistTime;
    }

    private boolean lambda$update$0(BlockPos blockPos) {
        long l = blockPos.toLong();
        if (this.bedPositionToTimeMap.containsKey(l)) {
            return true;
        }
        if (++this.bedsFound >= 5) {
            return true;
        }
        this.bedPositionToTimeMap.put(l, this.persistTime + 40L);
        return false;
    }
}

