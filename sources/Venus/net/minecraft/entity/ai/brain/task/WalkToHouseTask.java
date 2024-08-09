/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class WalkToHouseTask
extends Task<LivingEntity> {
    private final float field_220524_a;
    private final Long2LongMap field_225455_b = new Long2LongOpenHashMap();
    private int field_225456_c;
    private long field_220525_b;

    public WalkToHouseTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.HOME, MemoryModuleStatus.VALUE_ABSENT));
        this.field_220524_a = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        if (serverWorld.getGameTime() - this.field_220525_b < 20L) {
            return true;
        }
        CreatureEntity creatureEntity = (CreatureEntity)livingEntity;
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        Optional<BlockPos> optional = pointOfInterestManager.func_234148_d_(PointOfInterestType.HOME.getPredicate(), livingEntity.getPosition(), 48, PointOfInterestManager.Status.ANY);
        return optional.isPresent() && !(optional.get().distanceSq(creatureEntity.getPosition()) <= 4.0);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.field_225456_c = 0;
        this.field_220525_b = serverWorld.getGameTime() + (long)serverWorld.getRandom().nextInt(20);
        CreatureEntity creatureEntity = (CreatureEntity)livingEntity;
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        Predicate<BlockPos> predicate = this::lambda$startExecuting$0;
        Stream<BlockPos> stream = pointOfInterestManager.findAll(PointOfInterestType.HOME.getPredicate(), predicate, livingEntity.getPosition(), 48, PointOfInterestManager.Status.ANY);
        Path path = creatureEntity.getNavigator().pathfind(stream, PointOfInterestType.HOME.getValidRange());
        if (path != null && path.reachesTarget()) {
            BlockPos blockPos = path.getTarget();
            Optional<PointOfInterestType> optional = pointOfInterestManager.getType(blockPos);
            if (optional.isPresent()) {
                livingEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, this.field_220524_a, 1));
                DebugPacketSender.func_218801_c(serverWorld, blockPos);
            }
        } else if (this.field_225456_c < 5) {
            this.field_225455_b.long2LongEntrySet().removeIf(this::lambda$startExecuting$1);
        }
    }

    private boolean lambda$startExecuting$1(Long2LongMap.Entry entry) {
        return entry.getLongValue() < this.field_220525_b;
    }

    private boolean lambda$startExecuting$0(BlockPos blockPos) {
        long l = blockPos.toLong();
        if (this.field_225455_b.containsKey(l)) {
            return true;
        }
        if (++this.field_225456_c >= 5) {
            return true;
        }
        this.field_225455_b.put(l, this.field_220525_b + 40L);
        return false;
    }
}

