/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class ForgetRaidTask
extends Task<LivingEntity> {
    public ForgetRaidTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return serverWorld.rand.nextInt(20) == 0;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        Raid raid = serverWorld.findRaid(livingEntity.getPosition());
        if (raid == null || raid.isStopped() || raid.isLoss()) {
            brain.setFallbackActivity(Activity.IDLE);
            brain.updateActivity(serverWorld.getDayTime(), serverWorld.getGameTime());
        }
    }
}

