/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class SupplementedTask<E extends LivingEntity>
extends Task<E> {
    private final Predicate<E> field_233940_b_;
    private final Task<? super E> field_233941_c_;
    private final boolean field_233942_d_;

    public SupplementedTask(Map<MemoryModuleType<?>, MemoryModuleStatus> map, Predicate<E> predicate, Task<? super E> task, boolean bl) {
        super(SupplementedTask.func_233943_a_(map, task.requiredMemoryState));
        this.field_233940_b_ = predicate;
        this.field_233941_c_ = task;
        this.field_233942_d_ = bl;
    }

    private static Map<MemoryModuleType<?>, MemoryModuleStatus> func_233943_a_(Map<MemoryModuleType<?>, MemoryModuleStatus> map, Map<MemoryModuleType<?>, MemoryModuleStatus> map2) {
        HashMap<MemoryModuleType<?>, MemoryModuleStatus> hashMap = Maps.newHashMap();
        hashMap.putAll(map);
        hashMap.putAll(map2);
        return hashMap;
    }

    public SupplementedTask(Predicate<E> predicate, Task<? super E> task) {
        this(ImmutableMap.of(), predicate, task, false);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.field_233940_b_.test(e) && this.field_233941_c_.shouldExecute(serverWorld, e);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, E e, long l) {
        return this.field_233942_d_ && this.field_233940_b_.test(e) && this.field_233941_c_.shouldContinueExecuting(serverWorld, e, l);
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        this.field_233941_c_.startExecuting(serverWorld, e, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, E e, long l) {
        this.field_233941_c_.updateTask(serverWorld, e, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, E e, long l) {
        this.field_233941_c_.resetTask(serverWorld, e, l);
    }

    @Override
    public String toString() {
        return "RunIf: " + this.field_233941_c_;
    }
}

