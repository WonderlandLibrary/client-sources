/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class RingBellTask
extends Task<LivingEntity> {
    public RingBellTask() {
        super(ImmutableMap.of(MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return serverWorld.rand.nextFloat() > 0.95f;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        BlockState blockState;
        Brain<?> brain = livingEntity.getBrain();
        BlockPos blockPos = brain.getMemory(MemoryModuleType.MEETING_POINT).get().getPos();
        if (blockPos.withinDistance(livingEntity.getPosition(), 3.0) && (blockState = serverWorld.getBlockState(blockPos)).isIn(Blocks.BELL)) {
            BellBlock bellBlock = (BellBlock)blockState.getBlock();
            bellBlock.ring(serverWorld, blockPos, null);
        }
    }
}

