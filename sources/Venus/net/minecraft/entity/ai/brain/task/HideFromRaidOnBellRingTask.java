/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class HideFromRaidOnBellRingTask
extends Task<LivingEntity> {
    public HideFromRaidOnBellRingTask() {
        super(ImmutableMap.of(MemoryModuleType.HEARD_BELL_TIME, MemoryModuleStatus.VALUE_PRESENT));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        Raid raid = serverWorld.findRaid(livingEntity.getPosition());
        if (raid == null) {
            brain.switchTo(Activity.HIDE);
        }
    }
}

