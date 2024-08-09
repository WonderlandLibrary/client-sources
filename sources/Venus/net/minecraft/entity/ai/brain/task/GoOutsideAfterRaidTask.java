/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.MoveToSkylightTask;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class GoOutsideAfterRaidTask
extends MoveToSkylightTask {
    public GoOutsideAfterRaidTask(float f) {
        super(f);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        Raid raid = serverWorld.findRaid(livingEntity.getPosition());
        return raid != null && raid.isVictory() && super.shouldExecute(serverWorld, livingEntity);
    }
}

