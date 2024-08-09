/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class UpdateActivityTask
extends Task<LivingEntity> {
    public UpdateActivityTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        livingEntity.getBrain().updateActivity(serverWorld.getDayTime(), serverWorld.getGameTime());
    }
}

