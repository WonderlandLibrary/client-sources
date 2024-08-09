/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class MoveToTargetTask
extends Task<MobEntity> {
    private final float speed;

    public MoveToTargetTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.REGISTERED));
        this.speed = f;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        LivingEntity livingEntity = mobEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        if (BrainUtil.isMobVisible(mobEntity, livingEntity) && BrainUtil.canFireAtTarget(mobEntity, livingEntity, 1)) {
            this.clearTargetMemory(mobEntity);
        } else {
            this.setTargetMemory(mobEntity, livingEntity);
        }
    }

    private void setTargetMemory(LivingEntity livingEntity, LivingEntity livingEntity2) {
        Brain<?> brain = livingEntity.getBrain();
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity2, true));
        WalkTarget walkTarget = new WalkTarget(new EntityPosWrapper(livingEntity2, false), this.speed, 0);
        brain.setMemory(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    private void clearTargetMemory(LivingEntity livingEntity) {
        livingEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (MobEntity)livingEntity, l);
    }
}

