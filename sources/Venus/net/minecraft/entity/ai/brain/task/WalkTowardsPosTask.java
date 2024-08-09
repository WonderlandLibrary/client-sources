/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class WalkTowardsPosTask
extends Task<CreatureEntity> {
    private final MemoryModuleType<GlobalPos> field_220581_a;
    private final int field_220582_b;
    private final int field_220583_c;
    private final float field_242306_e;
    private long field_220584_d;

    public WalkTowardsPosTask(MemoryModuleType<GlobalPos> memoryModuleType, float f, int n, int n2) {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, memoryModuleType, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220581_a = memoryModuleType;
        this.field_242306_e = f;
        this.field_220582_b = n;
        this.field_220583_c = n2;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, CreatureEntity creatureEntity) {
        Optional<GlobalPos> optional = creatureEntity.getBrain().getMemory(this.field_220581_a);
        return optional.isPresent() && serverWorld.getDimensionKey() == optional.get().getDimension() && optional.get().getPos().withinDistance(creatureEntity.getPositionVec(), (double)this.field_220583_c);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, CreatureEntity creatureEntity, long l) {
        if (l > this.field_220584_d) {
            Brain<?> brain = creatureEntity.getBrain();
            Optional<GlobalPos> optional = brain.getMemory(this.field_220581_a);
            optional.ifPresent(arg_0 -> this.lambda$startExecuting$0(brain, arg_0));
            this.field_220584_d = l + 80L;
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

    private void lambda$startExecuting$0(Brain brain, GlobalPos globalPos) {
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(globalPos.getPos(), this.field_242306_e, this.field_220582_b));
    }
}

