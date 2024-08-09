/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.goal.Goal;

public class PrioritizedGoal
extends Goal {
    private final Goal inner;
    private final int priority;
    private boolean running;

    public PrioritizedGoal(int n, Goal goal) {
        this.priority = n;
        this.inner = goal;
    }

    public boolean isPreemptedBy(PrioritizedGoal prioritizedGoal) {
        return this.isPreemptible() && prioritizedGoal.getPriority() < this.getPriority();
    }

    @Override
    public boolean shouldExecute() {
        return this.inner.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.inner.shouldContinueExecuting();
    }

    @Override
    public boolean isPreemptible() {
        return this.inner.isPreemptible();
    }

    @Override
    public void startExecuting() {
        if (!this.running) {
            this.running = true;
            this.inner.startExecuting();
        }
    }

    @Override
    public void resetTask() {
        if (this.running) {
            this.running = false;
            this.inner.resetTask();
        }
    }

    @Override
    public void tick() {
        this.inner.tick();
    }

    @Override
    public void setMutexFlags(EnumSet<Goal.Flag> enumSet) {
        this.inner.setMutexFlags(enumSet);
    }

    @Override
    public EnumSet<Goal.Flag> getMutexFlags() {
        return this.inner.getMutexFlags();
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getPriority() {
        return this.priority;
    }

    public Goal getGoal() {
        return this.inner;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        return object != null && this.getClass() == object.getClass() ? this.inner.equals(((PrioritizedGoal)object).inner) : false;
    }

    public int hashCode() {
        return this.inner.hashCode();
    }
}

