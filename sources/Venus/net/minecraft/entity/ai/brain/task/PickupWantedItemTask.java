/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.world.server.ServerWorld;

public class PickupWantedItemTask<E extends LivingEntity>
extends Task<E> {
    private final Predicate<E> field_233906_b_;
    private final int field_233907_c_;
    private final float field_233908_d_;

    public PickupWantedItemTask(float f, boolean bl, int n) {
        this(PickupWantedItemTask::lambda$new$0, f, bl, n);
    }

    public PickupWantedItemTask(Predicate<E> predicate, float f, boolean bl, int n) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.WALK_TARGET, bl ? MemoryModuleStatus.REGISTERED : MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233906_b_ = predicate;
        this.field_233907_c_ = n;
        this.field_233908_d_ = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.field_233906_b_.test(e) && this.func_233909_a_(e).isEntityInRange((Entity)e, this.field_233907_c_);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        BrainUtil.setTargetEntity(e, this.func_233909_a_(e), this.field_233908_d_, 0);
    }

    private ItemEntity func_233909_a_(E e) {
        return ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM).get();
    }

    private static boolean lambda$new$0(LivingEntity livingEntity) {
        return false;
    }
}

