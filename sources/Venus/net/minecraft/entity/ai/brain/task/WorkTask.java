/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class WorkTask
extends Task<CreatureEntity> {
    private final MemoryModuleType<GlobalPos> field_220565_a;
    private long field_220566_b;
    private final int field_220567_c;
    private float field_242305_e;

    public WorkTask(MemoryModuleType<GlobalPos> memoryModuleType, float f, int n) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220565_a = memoryModuleType;
        this.field_242305_e = f;
        this.field_220567_c = n;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        Optional<GlobalPos> optional = creatureEntity.getBrain().getMemory(this.field_220565_a);
        return optional.isPresent() && serverWorld.getDimensionKey() == optional.get().getDimension() && optional.get().getPos().withinDistance(creatureEntity.getPositionVec(), (double)this.field_220567_c);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        if (l > this.field_220566_b) {
            Optional<Vector3d> optional = Optional.ofNullable(RandomPositionGenerator.getLandPos(creatureEntity, 8, 6));
            creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, optional.map(this::lambda$startExecuting$0));
            this.field_220566_b = l + 180L;
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

    private WalkTarget lambda$startExecuting$0(Vector3d vector3d) {
        return new WalkTarget(vector3d, this.field_242305_e, 1);
    }
}

