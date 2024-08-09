/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class JumpOnBedTask
extends Task<MobEntity> {
    private final float speed;
    @Nullable
    private BlockPos bedPos;
    private int field_220472_c;
    private int field_220473_d;
    private int field_220474_e;

    public JumpOnBedTask(float f) {
        super(ImmutableMap.of(MemoryModuleType.NEAREST_BED, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.speed = f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, MobEntity mobEntity) {
        return mobEntity.isChild() && this.func_220469_b(serverWorld, mobEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        super.startExecuting(serverWorld, mobEntity, l);
        this.getBed(mobEntity).ifPresent(arg_0 -> this.lambda$startExecuting$0(serverWorld, mobEntity, arg_0));
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        super.resetTask(serverWorld, mobEntity, l);
        this.bedPos = null;
        this.field_220472_c = 0;
        this.field_220473_d = 0;
        this.field_220474_e = 0;
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        return mobEntity.isChild() && this.bedPos != null && this.isBed(serverWorld, this.bedPos) && !this.func_220464_e(serverWorld, mobEntity) && !this.func_220462_f(serverWorld, mobEntity);
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (!this.func_220468_c(serverWorld, mobEntity)) {
            --this.field_220472_c;
        } else if (this.field_220474_e > 0) {
            --this.field_220474_e;
        } else if (this.func_220465_d(serverWorld, mobEntity)) {
            mobEntity.getJumpController().setJumping();
            --this.field_220473_d;
            this.field_220474_e = 5;
        }
    }

    private void setWalkTarget(MobEntity mobEntity, BlockPos blockPos) {
        mobEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, this.speed, 0));
    }

    private boolean func_220469_b(ServerWorld serverWorld, MobEntity mobEntity) {
        return this.func_220468_c(serverWorld, mobEntity) || this.getBed(mobEntity).isPresent();
    }

    private boolean func_220468_c(ServerWorld serverWorld, MobEntity mobEntity) {
        BlockPos blockPos = mobEntity.getPosition();
        BlockPos blockPos2 = blockPos.down();
        return this.isBed(serverWorld, blockPos) || this.isBed(serverWorld, blockPos2);
    }

    private boolean func_220465_d(ServerWorld serverWorld, MobEntity mobEntity) {
        return this.isBed(serverWorld, mobEntity.getPosition());
    }

    private boolean isBed(ServerWorld serverWorld, BlockPos blockPos) {
        return serverWorld.getBlockState(blockPos).isIn(BlockTags.BEDS);
    }

    private Optional<BlockPos> getBed(MobEntity mobEntity) {
        return mobEntity.getBrain().getMemory(MemoryModuleType.NEAREST_BED);
    }

    private boolean func_220464_e(ServerWorld serverWorld, MobEntity mobEntity) {
        return !this.func_220468_c(serverWorld, mobEntity) && this.field_220472_c <= 0;
    }

    private boolean func_220462_f(ServerWorld serverWorld, MobEntity mobEntity) {
        return this.func_220468_c(serverWorld, mobEntity) && this.field_220473_d <= 0;
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

    private void lambda$startExecuting$0(ServerWorld serverWorld, MobEntity mobEntity, BlockPos blockPos) {
        this.bedPos = blockPos;
        this.field_220472_c = 100;
        this.field_220473_d = 3 + serverWorld.rand.nextInt(4);
        this.field_220474_e = 0;
        this.setWalkTarget(mobEntity, blockPos);
    }
}

