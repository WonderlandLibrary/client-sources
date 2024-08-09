/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.server.ServerWorld;

public class FindHidingPlaceTask
extends Task<LivingEntity> {
    private final float speed;
    private final int field_220458_b;
    private final int field_220459_c;
    private Optional<BlockPos> hidingPos = Optional.empty();

    public FindHidingPlaceTask(int n, float f, int n2) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.HOME, MemoryModuleStatus.REGISTERED, MemoryModuleType.HIDING_PLACE, MemoryModuleStatus.REGISTERED));
        this.field_220458_b = n;
        this.speed = f;
        this.field_220459_c = n2;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        Optional<BlockPos> optional = serverWorld.getPointOfInterestManager().find(FindHidingPlaceTask::lambda$shouldExecute$0, FindHidingPlaceTask::lambda$shouldExecute$1, livingEntity.getPosition(), this.field_220459_c + 1, PointOfInterestManager.Status.ANY);
        this.hidingPos = optional.isPresent() && optional.get().withinDistance(livingEntity.getPositionVec(), (double)this.field_220459_c) ? optional : Optional.empty();
        return false;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Optional<GlobalPos> optional;
        Brain<?> brain = livingEntity.getBrain();
        Optional<BlockPos> optional2 = this.hidingPos;
        if (!optional2.isPresent() && !(optional2 = serverWorld.getPointOfInterestManager().getRandom(FindHidingPlaceTask::lambda$startExecuting$2, FindHidingPlaceTask::lambda$startExecuting$3, PointOfInterestManager.Status.ANY, livingEntity.getPosition(), this.field_220458_b, livingEntity.getRNG())).isPresent() && (optional = brain.getMemory(MemoryModuleType.HOME)).isPresent()) {
            optional2 = Optional.of(optional.get().getPos());
        }
        if (optional2.isPresent()) {
            brain.removeMemory(MemoryModuleType.PATH);
            brain.removeMemory(MemoryModuleType.LOOK_TARGET);
            brain.removeMemory(MemoryModuleType.BREED_TARGET);
            brain.removeMemory(MemoryModuleType.INTERACTION_TARGET);
            brain.setMemory(MemoryModuleType.HIDING_PLACE, GlobalPos.getPosition(serverWorld.getDimensionKey(), optional2.get()));
            if (!optional2.get().withinDistance(livingEntity.getPositionVec(), (double)this.field_220459_c)) {
                brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(optional2.get(), this.speed, this.field_220459_c));
            }
        }
    }

    private static boolean lambda$startExecuting$3(BlockPos blockPos) {
        return false;
    }

    private static boolean lambda$startExecuting$2(PointOfInterestType pointOfInterestType) {
        return pointOfInterestType == PointOfInterestType.HOME;
    }

    private static boolean lambda$shouldExecute$1(BlockPos blockPos) {
        return false;
    }

    private static boolean lambda$shouldExecute$0(PointOfInterestType pointOfInterestType) {
        return pointOfInterestType == PointOfInterestType.HOME;
    }
}

