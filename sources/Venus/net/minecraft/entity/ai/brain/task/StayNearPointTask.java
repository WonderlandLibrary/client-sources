/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class StayNearPointTask
extends Task<VillagerEntity> {
    private final MemoryModuleType<GlobalPos> field_220548_a;
    private final float field_220549_b;
    private final int field_220550_c;
    private final int field_220551_d;
    private final int field_223018_e;

    public StayNearPointTask(MemoryModuleType<GlobalPos> memoryModuleType, float f, int n, int n2, int n3) {
        super(ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220548_a = memoryModuleType;
        this.field_220549_b = f;
        this.field_220550_c = n;
        this.field_220551_d = n2;
        this.field_223018_e = n3;
    }

    private void func_225457_a(VillagerEntity villagerEntity, long l) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        villagerEntity.resetMemoryPoint(this.field_220548_a);
        brain.removeMemory(this.field_220548_a);
        brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, l);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        Brain<VillagerEntity> brain = villagerEntity.getBrain();
        brain.getMemory(this.field_220548_a).ifPresent(arg_0 -> this.lambda$startExecuting$0(serverWorld, villagerEntity, l, brain, arg_0));
    }

    private boolean func_223017_a(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        Optional<Long> optional = villagerEntity.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        if (optional.isPresent()) {
            return serverWorld.getGameTime() - optional.get() > (long)this.field_223018_e;
        }
        return true;
    }

    private boolean func_242304_a(VillagerEntity villagerEntity, GlobalPos globalPos) {
        return globalPos.getPos().manhattanDistance(villagerEntity.getPosition()) > this.field_220551_d;
    }

    private boolean func_242303_a(ServerWorld serverWorld, GlobalPos globalPos) {
        return globalPos.getDimension() != serverWorld.getDimensionKey();
    }

    private boolean func_220547_b(ServerWorld serverWorld, VillagerEntity villagerEntity, GlobalPos globalPos) {
        return globalPos.getDimension() == serverWorld.getDimensionKey() && globalPos.getPos().manhattanDistance(villagerEntity.getPosition()) <= this.field_220550_c;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        this.startExecuting(serverWorld, (VillagerEntity)livingEntity, l);
    }

    private void lambda$startExecuting$0(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, Brain brain, GlobalPos globalPos) {
        if (!this.func_242303_a(serverWorld, globalPos) && !this.func_223017_a(serverWorld, villagerEntity)) {
            if (this.func_242304_a(villagerEntity, globalPos)) {
                int n;
                Vector3d vector3d = null;
                int n2 = 1000;
                for (n = 0; n < 1000 && (vector3d == null || this.func_242304_a(villagerEntity, GlobalPos.getPosition(serverWorld.getDimensionKey(), new BlockPos(vector3d)))); ++n) {
                    vector3d = RandomPositionGenerator.findRandomTargetBlockTowards(villagerEntity, 15, 7, Vector3d.copyCenteredHorizontally(globalPos.getPos()));
                }
                if (n == 1000) {
                    this.func_225457_a(villagerEntity, l);
                    return;
                }
                brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(vector3d, this.field_220549_b, this.field_220550_c));
            } else if (!this.func_220547_b(serverWorld, villagerEntity, globalPos)) {
                brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(globalPos.getPos(), this.field_220549_b, this.field_220550_c));
            }
        } else {
            this.func_225457_a(villagerEntity, l);
        }
    }
}

