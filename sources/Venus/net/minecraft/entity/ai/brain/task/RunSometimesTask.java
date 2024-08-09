/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.RangedInteger;
import net.minecraft.world.server.ServerWorld;

public class RunSometimesTask<E extends LivingEntity>
extends Task<E> {
    private boolean field_233944_b_;
    private boolean field_233945_c_;
    private final RangedInteger field_233946_d_;
    private final Task<? super E> field_233947_e_;
    private int field_233948_f_;

    public RunSometimesTask(Task<? super E> task, RangedInteger rangedInteger) {
        this(task, false, rangedInteger);
    }

    public RunSometimesTask(Task<? super E> task, boolean bl, RangedInteger rangedInteger) {
        super(task.requiredMemoryState);
        this.field_233947_e_ = task;
        this.field_233944_b_ = !bl;
        this.field_233946_d_ = rangedInteger;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        if (!this.field_233947_e_.shouldExecute(serverWorld, e)) {
            return true;
        }
        if (this.field_233944_b_) {
            this.func_233949_a_(serverWorld);
            this.field_233944_b_ = false;
        }
        if (this.field_233948_f_ > 0) {
            --this.field_233948_f_;
        }
        return !this.field_233945_c_ && this.field_233948_f_ == 0;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        this.field_233947_e_.startExecuting(serverWorld, e, l);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, E e, long l) {
        return this.field_233947_e_.shouldContinueExecuting(serverWorld, e, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, E e, long l) {
        this.field_233947_e_.updateTask(serverWorld, e, l);
        this.field_233945_c_ = this.field_233947_e_.getStatus() == Task.Status.RUNNING;
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, E e, long l) {
        this.func_233949_a_(serverWorld);
        this.field_233947_e_.resetTask(serverWorld, e, l);
    }

    private void func_233949_a_(ServerWorld serverWorld) {
        this.field_233948_f_ = this.field_233946_d_.getRandomWithinRange(serverWorld.rand);
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    @Override
    public String toString() {
        return "RunSometimes: " + this.field_233947_e_;
    }
}

