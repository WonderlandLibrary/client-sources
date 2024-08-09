/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;

public class AttackStrafingTask<E extends MobEntity>
extends Task<E> {
    private final int distance;
    private final float speed;

    public AttackStrafingTask(int n, float f) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.distance = n;
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.hasSeen(e) && this.isTargetWithinDistance(e);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        ((LivingEntity)e).getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(this.getAttackTarget(e), true));
        ((MobEntity)e).getMoveHelper().strafe(-this.speed, 0.0f);
        ((MobEntity)e).rotationYaw = MathHelper.func_219800_b(((MobEntity)e).rotationYaw, ((MobEntity)e).rotationYawHead, 0.0f);
    }

    private boolean hasSeen(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(this.getAttackTarget(e));
    }

    private boolean isTargetWithinDistance(E e) {
        return this.getAttackTarget(e).isEntityInRange((Entity)e, this.distance);
    }

    private LivingEntity getAttackTarget(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((MobEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }
}

