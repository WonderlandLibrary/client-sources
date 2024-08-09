/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class FindWalkTargetTask
extends Task<CreatureEntity> {
    private final float speed;
    private final int maxXZ;
    private final int maxY;

    public FindWalkTargetTask(float f) {
        this(f, 10, 7);
    }

    public FindWalkTargetTask(float f, int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.speed = f;
        this.maxXZ = n;
        this.maxY = n2;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        BlockPos blockPos = creatureEntity.getPosition();
        if (serverWorld.isVillage(blockPos)) {
            this.func_220593_a(creatureEntity);
        } else {
            SectionPos sectionPos = SectionPos.from(blockPos);
            SectionPos sectionPos2 = BrainUtil.getClosestVillageSection(serverWorld, sectionPos, 2);
            if (sectionPos2 != sectionPos) {
                this.func_220594_a(creatureEntity, sectionPos2);
            } else {
                this.func_220593_a(creatureEntity);
            }
        }
    }

    private void func_220594_a(CreatureEntity creatureEntity, SectionPos sectionPos) {
        Optional<Vector3d> optional = Optional.ofNullable(RandomPositionGenerator.findRandomTargetBlockTowards(creatureEntity, this.maxXZ, this.maxY, Vector3d.copyCenteredHorizontally(sectionPos.getCenter())));
        creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, optional.map(this::lambda$func_220594_a$0));
    }

    private void func_220593_a(CreatureEntity creatureEntity) {
        Optional<Vector3d> optional = Optional.ofNullable(RandomPositionGenerator.getLandPos(creatureEntity, this.maxXZ, this.maxY));
        creatureEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, optional.map(this::lambda$func_220593_a$1));
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (CreatureEntity)livingEntity, l);
    }

    private WalkTarget lambda$func_220593_a$1(Vector3d vector3d) {
        return new WalkTarget(vector3d, this.speed, 0);
    }

    private WalkTarget lambda$func_220594_a$0(Vector3d vector3d) {
        return new WalkTarget(vector3d, this.speed, 0);
    }
}

