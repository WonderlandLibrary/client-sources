/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.FindHidingPlaceTask;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class FindHidingPlaceDuringRaidTask
extends FindHidingPlaceTask {
    public FindHidingPlaceDuringRaidTask(int n, float f) {
        super(n, f, 1);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        Raid raid = serverWorld.findRaid(livingEntity.getPosition());
        return super.shouldExecute(serverWorld, livingEntity) && raid != null && raid.isActive() && !raid.isVictory() && !raid.isLoss();
    }
}

