/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.server.ServerWorld;

public class WalkToPOITask
extends Task<VillagerEntity> {
    private final float field_225445_a;
    private final int field_225446_b;

    public WalkToPOITask(float f, int n) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT));
        this.field_225445_a = f;
        this.field_225446_b = n;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        return !serverWorld.isVillage(villagerEntity.getPosition());
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        int n = pointOfInterestManager.sectionsToVillage(SectionPos.from(villagerEntity.getPosition()));
        Vector3d vector3d = null;
        for (int i = 0; i < 5; ++i) {
            Vector3d vector3d2 = RandomPositionGenerator.func_221024_a(villagerEntity, 15, 7, arg_0 -> WalkToPOITask.lambda$startExecuting$0(serverWorld, arg_0));
            if (vector3d2 == null) continue;
            int n2 = pointOfInterestManager.sectionsToVillage(SectionPos.from(new BlockPos(vector3d2)));
            if (n2 < n) {
                vector3d = vector3d2;
                break;
            }
            if (n2 != n) continue;
            vector3d = vector3d2;
        }
        if (vector3d != null) {
            villagerEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vector3d, this.field_225445_a, this.field_225446_b));
        }
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.shouldExecute(serverWorld, (VillagerEntity)livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private static double lambda$startExecuting$0(ServerWorld serverWorld, BlockPos blockPos) {
        return -serverWorld.sectionsToVillage(SectionPos.from(blockPos));
    }
}

