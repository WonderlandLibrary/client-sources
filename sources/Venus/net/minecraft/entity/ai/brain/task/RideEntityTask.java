/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class RideEntityTask<E extends LivingEntity>
extends Task<E> {
    private final float field_233924_b_;

    public RideEntityTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.RIDE_TARGET, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233924_b_ = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return !((Entity)e).isPassenger();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        if (this.func_233925_a_(e)) {
            ((Entity)e).startRiding(this.func_233926_b_(e));
        } else {
            BrainUtil.setTargetEntity(e, this.func_233926_b_(e), this.field_233924_b_, 1);
        }
    }

    private boolean func_233925_a_(E e) {
        return this.func_233926_b_(e).isEntityInRange((Entity)e, 1.0);
    }

    private Entity func_233926_b_(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.RIDE_TARGET).get();
    }
}

