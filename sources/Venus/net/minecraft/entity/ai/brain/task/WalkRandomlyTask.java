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
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class WalkRandomlyTask
extends Task<CreatureEntity> {
    private final float field_233936_b_;
    private final int field_233937_c_;
    private final int field_233938_d_;

    public WalkRandomlyTask(float f) {
        this(f, 10, 7);
    }

    public WalkRandomlyTask(float f, int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.field_233936_b_ = f;
        this.field_233937_c_ = n;
        this.field_233938_d_ = n2;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        Optional<Vector3d> optional = Optional.ofNullable(RandomPositionGenerator.getLandPos(creatureEntity, this.field_233937_c_, this.field_233938_d_));
        creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, optional.map(this::lambda$startExecuting$0));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }

    private WalkTarget lambda$startExecuting$0(Vector3d vector3d) {
        return new WalkTarget(vector3d, this.field_233936_b_, 0);
    }
}

