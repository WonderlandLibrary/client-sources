/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class WalkTowardsRandomSecondaryPosTask
extends Task<VillagerEntity> {
    private final MemoryModuleType<List<GlobalPos>> field_220573_a;
    private final MemoryModuleType<GlobalPos> field_220574_b;
    private final float field_220575_c;
    private final int field_220576_d;
    private final int field_220577_e;
    private long field_220578_f;
    @Nullable
    private GlobalPos field_220579_g;

    public WalkTowardsRandomSecondaryPosTask(MemoryModuleType<List<GlobalPos>> memoryModuleType, float f, int n, int n2, MemoryModuleType<GlobalPos> memoryModuleType2) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT, memoryModuleType2, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220573_a = memoryModuleType;
        this.field_220575_c = f;
        this.field_220576_d = n;
        this.field_220577_e = n2;
        this.field_220574_b = memoryModuleType2;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, VillagerEntity villagerEntity) {
        List<GlobalPos> list;
        Optional<List<GlobalPos>> optional = villagerEntity.getBrain().getMemory(this.field_220573_a);
        Optional<GlobalPos> optional2 = villagerEntity.getBrain().getMemory(this.field_220574_b);
        if (optional.isPresent() && optional2.isPresent() && !(list = optional.get()).isEmpty()) {
            this.field_220579_g = list.get(serverWorld.getRandom().nextInt(list.size()));
            return this.field_220579_g != null && serverWorld.getDimensionKey() == this.field_220579_g.getDimension() && optional2.get().getPos().withinDistance(villagerEntity.getPositionVec(), (double)this.field_220577_e);
        }
        return true;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, VillagerEntity villagerEntity, long l) {
        if (l > this.field_220578_f && this.field_220579_g != null) {
            villagerEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(this.field_220579_g.getPos(), this.field_220575_c, this.field_220576_d));
            this.field_220578_f = l + 100L;
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
}

