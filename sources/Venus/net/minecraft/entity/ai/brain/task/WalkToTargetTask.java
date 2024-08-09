/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class WalkToTargetTask
extends Task<MobEntity> {
    private int field_242302_b;
    @Nullable
    private Path field_220488_a;
    @Nullable
    private BlockPos field_220489_b;
    private float field_220490_c;

    public WalkToTargetTask() {
        this(150, 250);
    }

    public WalkToTargetTask(int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleStatus.REGISTERED, MemoryModuleType.PATH, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_PRESENT), n, n2);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, MobEntity mobEntity) {
        if (this.field_242302_b > 0) {
            --this.field_242302_b;
            return true;
        }
        Brain<?> brain = mobEntity.getBrain();
        WalkTarget walkTarget = brain.getMemory(MemoryModuleType.WALK_TARGET).get();
        boolean bl = this.hasReachedTarget(mobEntity, walkTarget);
        if (!bl && this.func_220487_a(mobEntity, walkTarget, serverWorld.getGameTime())) {
            this.field_220489_b = walkTarget.getTarget().getBlockPos();
            return false;
        }
        brain.removeMemory(MemoryModuleType.WALK_TARGET);
        if (bl) {
            brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
        return true;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (this.field_220488_a != null && this.field_220489_b != null) {
            Optional<WalkTarget> optional = mobEntity.getBrain().getMemory(MemoryModuleType.WALK_TARGET);
            PathNavigator pathNavigator = mobEntity.getNavigator();
            return !pathNavigator.noPath() && optional.isPresent() && !this.hasReachedTarget(mobEntity, optional.get());
        }
        return true;
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (mobEntity.getBrain().hasMemory(MemoryModuleType.WALK_TARGET) && !this.hasReachedTarget(mobEntity, mobEntity.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get()) && mobEntity.getNavigator().func_244428_t()) {
            this.field_242302_b = serverWorld.getRandom().nextInt(40);
        }
        mobEntity.getNavigator().clearPath();
        mobEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        mobEntity.getBrain().removeMemory(MemoryModuleType.PATH);
        this.field_220488_a = null;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        mobEntity.getBrain().setMemory(MemoryModuleType.PATH, this.field_220488_a);
        mobEntity.getNavigator().setPath(this.field_220488_a, this.field_220490_c);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        WalkTarget walkTarget;
        Path path = mobEntity.getNavigator().getPath();
        Brain<?> brain = mobEntity.getBrain();
        if (this.field_220488_a != path) {
            this.field_220488_a = path;
            brain.setMemory(MemoryModuleType.PATH, path);
        }
        if (path != null && this.field_220489_b != null && (walkTarget = brain.getMemory(MemoryModuleType.WALK_TARGET).get()).getTarget().getBlockPos().distanceSq(this.field_220489_b) > 4.0 && this.func_220487_a(mobEntity, walkTarget, serverWorld.getGameTime())) {
            this.field_220489_b = walkTarget.getTarget().getBlockPos();
            this.startExecuting(serverWorld, mobEntity, l);
        }
    }

    private boolean func_220487_a(MobEntity mobEntity, WalkTarget walkTarget, long l) {
        BlockPos blockPos = walkTarget.getTarget().getBlockPos();
        this.field_220488_a = mobEntity.getNavigator().getPathToPos(blockPos, 0);
        this.field_220490_c = walkTarget.getSpeed();
        Brain<Long> brain = mobEntity.getBrain();
        if (this.hasReachedTarget(mobEntity, walkTarget)) {
            brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        } else {
            boolean bl;
            boolean bl2 = bl = this.field_220488_a != null && this.field_220488_a.reachesTarget();
            if (bl) {
                brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            } else if (!brain.hasMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, l);
            }
            if (this.field_220488_a != null) {
                return false;
            }
            Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockTowards((CreatureEntity)mobEntity, 10, 7, Vector3d.copyCenteredHorizontally(blockPos));
            if (vector3d != null) {
                this.field_220488_a = mobEntity.getNavigator().getPathToPos(vector3d.x, vector3d.y, vector3d.z, 0);
                return this.field_220488_a != null;
            }
        }
        return true;
    }

    private boolean hasReachedTarget(MobEntity mobEntity, WalkTarget walkTarget) {
        return walkTarget.getTarget().getBlockPos().manhattanDistance(mobEntity.getPosition()) <= walkTarget.getDistance();
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (MobEntity)livingEntity);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        return this.shouldContinueExecuting(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.resetTask(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.updateTask(serverWorld, (MobEntity)livingEntity, l);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (MobEntity)livingEntity, l);
    }
}

