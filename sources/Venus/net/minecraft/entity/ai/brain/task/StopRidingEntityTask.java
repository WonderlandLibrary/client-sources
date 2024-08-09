/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.world.server.ServerWorld;

public class StopRidingEntityTask<E extends LivingEntity, T extends Entity>
extends Task<E> {
    private final int field_233890_b_;
    private final BiPredicate<E, Entity> field_233891_c_;

    public StopRidingEntityTask(int n, BiPredicate<E, Entity> biPredicate) {
        super(ImmutableMap.of(MemoryModuleType.RIDE_TARGET, MemoryModuleStatus.REGISTERED));
        this.field_233890_b_ = n;
        this.field_233891_c_ = biPredicate;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        Entity entity2 = ((Entity)e).getRidingEntity();
        Entity entity3 = ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.RIDE_TARGET).orElse(null);
        if (entity2 == null && entity3 == null) {
            return true;
        }
        Entity entity4 = entity2 == null ? entity3 : entity2;
        return !this.func_233892_a_(e, entity4) || this.field_233891_c_.test(e, entity4);
    }

    private boolean func_233892_a_(E e, Entity entity2) {
        return entity2.isAlive() && entity2.isEntityInRange((Entity)e, this.field_233890_b_) && entity2.world == ((LivingEntity)e).world;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        ((LivingEntity)e).stopRiding();
        ((LivingEntity)e).getBrain().removeMemory(MemoryModuleType.RIDE_TARGET);
    }
}

