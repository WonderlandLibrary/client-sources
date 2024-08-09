/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Random;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HuntCelebrationTask<E extends MobEntity>
extends Task<E> {
    private final int field_233897_b_;
    private final float field_233898_c_;

    public HuntCelebrationTask(int n, float f) {
        super(ImmutableMap.of(MemoryModuleType.CELEBRATE_LOCATION, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED));
        this.field_233897_b_ = n;
        this.field_233898_c_ = f;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        BlockPos blockPos = HuntCelebrationTask.func_233899_a_(mobEntity);
        boolean bl = blockPos.withinDistance(mobEntity.getPosition(), (double)this.field_233897_b_);
        if (!bl) {
            BrainUtil.setTargetPosition(mobEntity, HuntCelebrationTask.func_233900_a_(mobEntity, blockPos), this.field_233898_c_, this.field_233897_b_);
        }
    }

    private static BlockPos func_233900_a_(MobEntity mobEntity, BlockPos blockPos) {
        Random random2 = mobEntity.world.rand;
        return blockPos.add(HuntCelebrationTask.func_233901_a_(random2), 0, HuntCelebrationTask.func_233901_a_(random2));
    }

    private static int func_233901_a_(Random random2) {
        return random2.nextInt(3) - 1;
    }

    private static BlockPos func_233899_a_(MobEntity mobEntity) {
        return mobEntity.getBrain().getMemory(MemoryModuleType.CELEBRATE_LOCATION).get();
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (MobEntity)livingEntity, l);
    }
}

