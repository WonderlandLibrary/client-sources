/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class WalkTowardsLookTargetTask
extends Task<LivingEntity> {
    private final float field_220543_a;
    private final int field_220544_b;

    public WalkTowardsLookTargetTask(float f, int n) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220543_a = f;
        this.field_220544_b = n;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        IPosWrapper iPosWrapper = brain.getMemory(MemoryModuleType.LOOK_TARGET).get();
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(iPosWrapper, this.field_220543_a, this.field_220544_b));
    }
}

