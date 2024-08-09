/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.EntityPredicates;
import net.minecraft.world.server.ServerWorld;

public class FindNewAttackTargetTask<E extends MobEntity>
extends Task<E> {
    private final Predicate<LivingEntity> field_233981_b_;

    public FindNewAttackTargetTask(Predicate<LivingEntity> predicate) {
        super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleStatus.REGISTERED));
        this.field_233981_b_ = predicate;
    }

    public FindNewAttackTargetTask() {
        this(FindNewAttackTargetTask::lambda$new$0);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        if (FindNewAttackTargetTask.func_233982_a_(e)) {
            this.func_233987_d_(e);
        } else if (this.func_233986_c_(e)) {
            this.func_233987_d_(e);
        } else if (this.func_233983_a_(e)) {
            this.func_233987_d_(e);
        } else if (!EntityPredicates.CAN_HOSTILE_AI_TARGET.test(this.func_233985_b_(e))) {
            this.func_233987_d_(e);
        } else if (this.field_233981_b_.test(this.func_233985_b_(e))) {
            this.func_233987_d_(e);
        }
    }

    private boolean func_233983_a_(E e) {
        return this.func_233985_b_(e).world != ((MobEntity)e).world;
    }

    private LivingEntity func_233985_b_(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
    }

    private static <E extends LivingEntity> boolean func_233982_a_(E e) {
        Optional<Long> optional = e.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        return optional.isPresent() && e.world.getGameTime() - optional.get() > 200L;
    }

    private boolean func_233986_c_(E e) {
        Optional<LivingEntity> optional = ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        return optional.isPresent() && !optional.get().isAlive();
    }

    private void func_233987_d_(E e) {
        ((LivingEntity)e).getBrain().removeMemory(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((MobEntity)livingEntity), l);
    }

    private static boolean lambda$new$0(LivingEntity livingEntity) {
        return true;
    }
}

