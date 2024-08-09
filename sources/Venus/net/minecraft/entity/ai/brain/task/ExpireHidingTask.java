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
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExpireHidingTask
extends Task<LivingEntity> {
    private final int hidingDistance;
    private final int field_220538_b;
    private int hidingDuration;

    public ExpireHidingTask(int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.HIDING_PLACE, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220538_b = n * 20;
        this.hidingDuration = 0;
        this.hidingDistance = n2;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        boolean bl;
        Brain<?> brain = livingEntity.getBrain();
        Optional<Long> optional = brain.getMemory(MemoryModuleType.HEARD_BELL_TIME);
        boolean bl2 = bl = optional.get() + 300L <= l;
        if (this.hidingDuration <= this.field_220538_b && !bl) {
            BlockPos blockPos = brain.getMemory(MemoryModuleType.HIDING_PLACE).get().getPos();
            if (blockPos.withinDistance(livingEntity.getPosition(), (double)this.hidingDistance)) {
                ++this.hidingDuration;
            }
        } else {
            brain.removeMemory(MemoryModuleType.HEARD_BELL_TIME);
            brain.removeMemory(MemoryModuleType.HIDING_PLACE);
            brain.updateActivity(serverWorld.getDayTime(), serverWorld.getGameTime());
            this.hidingDuration = 0;
        }
    }
}

