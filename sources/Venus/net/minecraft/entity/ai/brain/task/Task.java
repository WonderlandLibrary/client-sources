/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import java.util.Map;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.server.ServerWorld;

public abstract class Task<E extends LivingEntity> {
    protected final Map<MemoryModuleType<?>, MemoryModuleStatus> requiredMemoryState;
    private Status status = Status.STOPPED;
    private long stopTime;
    private final int durationMin;
    private final int durationMax;

    public Task(Map<MemoryModuleType<?>, MemoryModuleStatus> map) {
        this(map, 60);
    }

    public Task(Map<MemoryModuleType<?>, MemoryModuleStatus> map, int n) {
        this(map, n, n);
    }

    public Task(Map<MemoryModuleType<?>, MemoryModuleStatus> map, int n, int n2) {
        this.durationMin = n;
        this.durationMax = n2;
        this.requiredMemoryState = map;
    }

    public Status getStatus() {
        return this.status;
    }

    public final boolean start(ServerWorld serverWorld, E e, long l) {
        if (this.hasRequiredMemories(e) && this.shouldExecute(serverWorld, e)) {
            this.status = Status.RUNNING;
            int n = this.durationMin + serverWorld.getRandom().nextInt(this.durationMax + 1 - this.durationMin);
            this.stopTime = l + (long)n;
            this.startExecuting(serverWorld, e, l);
            return false;
        }
        return true;
    }

    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
    }

    public final void tick(ServerWorld serverWorld, E e, long l) {
        if (!this.isTimedOut(l) && this.shouldContinueExecuting(serverWorld, e, l)) {
            this.updateTask(serverWorld, e, l);
        } else {
            this.stop(serverWorld, e, l);
        }
    }

    protected void updateTask(ServerWorld serverWorld, E e, long l) {
    }

    public final void stop(ServerWorld serverWorld, E e, long l) {
        this.status = Status.STOPPED;
        this.resetTask(serverWorld, e, l);
    }

    protected void resetTask(ServerWorld serverWorld, E e, long l) {
    }

    protected boolean shouldContinueExecuting(ServerWorld serverWorld, E e, long l) {
        return true;
    }

    protected boolean isTimedOut(long l) {
        return l > this.stopTime;
    }

    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return false;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    private boolean hasRequiredMemories(E e) {
        for (Map.Entry<MemoryModuleType<?>, MemoryModuleStatus> entry : this.requiredMemoryState.entrySet()) {
            MemoryModuleType<?> memoryModuleType = entry.getKey();
            MemoryModuleStatus memoryModuleStatus = entry.getValue();
            if (((LivingEntity)e).getBrain().hasMemory(memoryModuleType, memoryModuleStatus)) continue;
            return true;
        }
        return false;
    }

    public static enum Status {
        STOPPED,
        RUNNING;

    }
}

