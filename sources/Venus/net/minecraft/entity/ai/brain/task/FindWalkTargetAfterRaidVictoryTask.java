/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.FindWalkTargetTask;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;

public class FindWalkTargetAfterRaidVictoryTask
extends FindWalkTargetTask {
    public FindWalkTargetAfterRaidVictoryTask(float f) {
        super(f);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        Raid raid = serverWorld.findRaid(creatureEntity.getPosition());
        return raid != null && raid.isVictory() && super.shouldExecute(serverWorld, creatureEntity);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (CreatureEntity)livingEntity);
    }
}

