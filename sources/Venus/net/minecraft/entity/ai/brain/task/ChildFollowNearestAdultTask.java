/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.RangedInteger;
import net.minecraft.world.server.ServerWorld;

public class ChildFollowNearestAdultTask<E extends AgeableEntity>
extends Task<E> {
    private final RangedInteger distance;
    private final float speed;

    public ChildFollowNearestAdultTask(RangedInteger rangedInteger, float f) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.distance = rangedInteger;
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        if (!((AgeableEntity)e).isChild()) {
            return true;
        }
        AgeableEntity ageableEntity = this.getNearestVisibleAdult(e);
        return ((Entity)e).isEntityInRange(ageableEntity, this.distance.getMax() + 1) && !((Entity)e).isEntityInRange(ageableEntity, this.distance.getMinInclusive());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        BrainUtil.setTargetEntity(e, this.getNearestVisibleAdult(e), this.speed, this.distance.getMinInclusive() - 1);
    }

    private AgeableEntity getNearestVisibleAdult(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT).get();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((AgeableEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((AgeableEntity)livingEntity), l);
    }
}

