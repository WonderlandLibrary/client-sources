/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.item.Items;
import net.minecraft.world.server.ServerWorld;

public class StartAdmiringItemTask<E extends PiglinEntity>
extends Task<E> {
    public StartAdmiringItemTask() {
        super(ImmutableMap.of(MemoryModuleType.ADMIRING_ITEM, MemoryModuleStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return !((LivingEntity)e).getHeldItemOffhand().isEmpty() && ((LivingEntity)e).getHeldItemOffhand().getItem() != Items.SHIELD;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        PiglinTasks.func_234477_a_(e, true);
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

