/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.server.ServerWorld;

public class SwimTask
extends Task<MobEntity> {
    private final float field_220590_b;

    public SwimTask(float f) {
        super(ImmutableMap.of());
        this.field_220590_b = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, MobEntity mobEntity) {
        return mobEntity.isInWater() && mobEntity.func_233571_b_(FluidTags.WATER) > mobEntity.func_233579_cu_() || mobEntity.isInLava();
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        return this.shouldExecute(serverWorld, mobEntity);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (mobEntity.getRNG().nextFloat() < this.field_220590_b) {
            mobEntity.getJumpController().setJumping();
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (MobEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (MobEntity)livingEntity, l);
    }
}

