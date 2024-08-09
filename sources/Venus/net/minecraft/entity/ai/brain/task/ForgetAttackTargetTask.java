/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class ForgetAttackTargetTask<E extends MobEntity>
extends Task<E> {
    private final Predicate<E> field_233973_b_;
    private final Function<E, Optional<? extends LivingEntity>> field_233974_c_;

    public ForgetAttackTargetTask(Predicate<E> predicate, Function<E, Optional<? extends LivingEntity>> function) {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleStatus.REGISTERED));
        this.field_233973_b_ = predicate;
        this.field_233974_c_ = function;
    }

    public ForgetAttackTargetTask(Function<E, Optional<? extends LivingEntity>> function) {
        this(ForgetAttackTargetTask::lambda$new$0, function);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        if (!this.field_233973_b_.test(e)) {
            return true;
        }
        Optional<? extends LivingEntity> optional = this.field_233974_c_.apply(e);
        return optional.isPresent() && optional.get().isAlive();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        this.field_233974_c_.apply(e).ifPresent(arg_0 -> this.lambda$startExecuting$1(e, arg_0));
    }

    private void func_233976_a_(E e, LivingEntity livingEntity) {
        ((LivingEntity)e).getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, livingEntity);
        ((LivingEntity)e).getBrain().removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((MobEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    private void lambda$startExecuting$1(MobEntity mobEntity, LivingEntity livingEntity) {
        this.func_233976_a_(mobEntity, livingEntity);
    }

    private static boolean lambda$new$0(MobEntity mobEntity) {
        return false;
    }
}

