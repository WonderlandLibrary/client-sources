/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class WalkRandomlyInsideTask
extends Task<CreatureEntity> {
    private final float field_233911_b_;

    public WalkRandomlyInsideTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.field_233911_b_ = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        return !serverWorld.canSeeSky(creatureEntity.getPosition());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        BlockPos blockPos = creatureEntity.getPosition();
        List list = BlockPos.getAllInBox(blockPos.add(-1, -1, -1), blockPos.add(1, 1, 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
        Collections.shuffle(list);
        Optional<BlockPos> optional = list.stream().filter(arg_0 -> WalkRandomlyInsideTask.lambda$startExecuting$0(serverWorld, arg_0)).filter(arg_0 -> WalkRandomlyInsideTask.lambda$startExecuting$1(serverWorld, creatureEntity, arg_0)).filter(arg_0 -> WalkRandomlyInsideTask.lambda$startExecuting$2(serverWorld, creatureEntity, arg_0)).findFirst();
        optional.ifPresent(arg_0 -> this.lambda$startExecuting$3(creatureEntity, arg_0));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (CreatureEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }

    private void lambda$startExecuting$3(CreatureEntity creatureEntity, BlockPos blockPos) {
        creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, this.field_233911_b_, 0));
    }

    private static boolean lambda$startExecuting$2(ServerWorld serverWorld, CreatureEntity creatureEntity, BlockPos blockPos) {
        return serverWorld.hasNoCollisions(creatureEntity);
    }

    private static boolean lambda$startExecuting$1(ServerWorld serverWorld, CreatureEntity creatureEntity, BlockPos blockPos) {
        return serverWorld.isTopSolid(blockPos, creatureEntity);
    }

    private static boolean lambda$startExecuting$0(ServerWorld serverWorld, BlockPos blockPos) {
        return !serverWorld.canSeeSky(blockPos);
    }
}

