/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.world.server.ServerWorld;

public class ForgetAdmiredItemTask<E extends PiglinEntity>
extends Task<E> {
    private final int field_234541_b_;

    public ForgetAdmiredItemTask(int n) {
        super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleStatus.REGISTERED));
        this.field_234541_b_ = n;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        if (!((LivingEntity)e).getHeldItemOffhand().isEmpty()) {
            return true;
        }
        Optional<ItemEntity> optional = ((PiglinEntity)e).getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
        if (!optional.isPresent()) {
            return false;
        }
        return !optional.get().isEntityInRange((Entity)e, this.field_234541_b_);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        ((PiglinEntity)e).getBrain().removeMemory(MemoryModuleType.ADMIRING_ITEM);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (E)((PiglinEntity)livingEntity));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (E)((PiglinEntity)livingEntity), l);
    }
}

