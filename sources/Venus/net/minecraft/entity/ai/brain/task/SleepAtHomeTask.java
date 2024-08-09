/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.InteractWithDoorTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class SleepAtHomeTask
extends Task<LivingEntity> {
    private long field_220552_a;

    public SleepAtHomeTask() {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryModuleStatus.REGISTERED));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        long l;
        if (livingEntity.isPassenger()) {
            return true;
        }
        Brain<?> brain = livingEntity.getBrain();
        GlobalPos globalPos = brain.getMemory(MemoryModuleType.HOME).get();
        if (serverWorld.getDimensionKey() != globalPos.getDimension()) {
            return true;
        }
        Optional<Long> optional = brain.getMemory(MemoryModuleType.LAST_WOKEN);
        if (optional.isPresent() && (l = serverWorld.getGameTime() - optional.get()) > 0L && l < 100L) {
            return true;
        }
        BlockState blockState = serverWorld.getBlockState(globalPos.getPos());
        return globalPos.getPos().withinDistance(livingEntity.getPositionVec(), 2.0) && blockState.getBlock().isIn(BlockTags.BEDS) && blockState.get(BedBlock.OCCUPIED) == false;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Optional<GlobalPos> optional = livingEntity.getBrain().getMemory(MemoryModuleType.HOME);
        if (!optional.isPresent()) {
            return true;
        }
        BlockPos blockPos = optional.get().getPos();
        return livingEntity.getBrain().hasActivity(Activity.REST) && livingEntity.getPosY() > (double)blockPos.getY() + 0.4 && blockPos.withinDistance(livingEntity.getPositionVec(), 1.14);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        if (l > this.field_220552_a) {
            InteractWithDoorTask.func_242294_a(serverWorld, livingEntity, null, null);
            livingEntity.startSleeping(livingEntity.getBrain().getMemory(MemoryModuleType.HOME).get().getPos());
        }
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        if (livingEntity.isSleeping()) {
            livingEntity.wakeUp();
            this.field_220552_a = l + 40L;
        }
    }
}

