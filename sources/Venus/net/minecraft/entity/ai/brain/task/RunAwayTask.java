/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class RunAwayTask<T>
extends Task<CreatureEntity> {
    private final MemoryModuleType<T> field_233957_b_;
    private final float field_233958_c_;
    private final int field_233959_d_;
    private final Function<T, Vector3d> field_233960_e_;

    public RunAwayTask(MemoryModuleType<T> memoryModuleType, float f, int n, boolean bl, Function<T, Vector3d> function) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, bl ? MemoryModuleStatus.REGISTERED : MemoryModuleStatus.VALUE_ABSENT, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_233957_b_ = memoryModuleType;
        this.field_233958_c_ = f;
        this.field_233959_d_ = n;
        this.field_233960_e_ = function;
    }

    public static RunAwayTask<BlockPos> func_233963_a_(MemoryModuleType<BlockPos> memoryModuleType, float f, int n, boolean bl) {
        return new RunAwayTask<BlockPos>(memoryModuleType, f, n, bl, Vector3d::copyCenteredHorizontally);
    }

    public static RunAwayTask<? extends Entity> func_233965_b_(MemoryModuleType<? extends Entity> memoryModuleType, float f, int n, boolean bl) {
        return new RunAwayTask<Entity>(memoryModuleType, f, n, bl, Entity::getPositionVec);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        return this.func_233964_b_(creatureEntity) ? false : creatureEntity.getPositionVec().isWithinDistanceOf(this.func_233961_a_(creatureEntity), this.field_233959_d_);
    }

    private Vector3d func_233961_a_(CreatureEntity creatureEntity) {
        return this.field_233960_e_.apply(creatureEntity.getBrain().getMemory(this.field_233957_b_).get());
    }

    private boolean func_233964_b_(CreatureEntity creatureEntity) {
        Vector3d vector3d;
        if (!creatureEntity.getBrain().hasMemory(MemoryModuleType.WALK_TARGET)) {
            return true;
        }
        WalkTarget walkTarget = creatureEntity.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get();
        if (walkTarget.getSpeed() != this.field_233958_c_) {
            return true;
        }
        Vector3d vector3d2 = walkTarget.getTarget().getPos().subtract(creatureEntity.getPositionVec());
        return vector3d2.dotProduct(vector3d = this.func_233961_a_(creatureEntity).subtract(creatureEntity.getPositionVec())) < 0.0;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        RunAwayTask.func_233962_a_(creatureEntity, this.func_233961_a_(creatureEntity), this.field_233958_c_);
    }

    private static void func_233962_a_(CreatureEntity creatureEntity, Vector3d vector3d, float f) {
        for (int i = 0; i < 10; ++i) {
            Vector3d vector3d2 = RandomPositionGenerator.func_223548_b(creatureEntity, 16, 7, vector3d);
            if (vector3d2 == null) continue;
            creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vector3d2, f, 0));
            return;
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (CreatureEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }
}

