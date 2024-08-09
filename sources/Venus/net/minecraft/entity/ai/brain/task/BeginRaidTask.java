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

public class BeginRaidTask
extends Task<LivingEntity> {
    public BeginRaidTask() {
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
        if (raid != null) {
            if (raid.func_221297_c() && !raid.isBetweenWaves()) {
                brain.setFallbackActivity(Activity.RAID);
                brain.switchTo(Activity.RAID);
            } else {
                brain.setFallbackActivity(Activity.PRE_RAID);
                brain.switchTo(Activity.PRE_RAID);
            }
        }
    }
}

