/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class LookAtEntityTask
extends Task<LivingEntity> {
    private final Predicate<LivingEntity> targetPredicate;
    private final float field_220520_b;

    public LookAtEntityTask(EntityClassification entityClassification, float f) {
        this(arg_0 -> LookAtEntityTask.lambda$new$0(entityClassification, arg_0), f);
    }

    public LookAtEntityTask(EntityType<?> entityType, float f) {
        this(arg_0 -> LookAtEntityTask.lambda$new$1(entityType, arg_0), f);
    }

    public LookAtEntityTask(float f) {
        this(LookAtEntityTask::lambda$new$2, f);
    }

    public LookAtEntityTask(Predicate<LivingEntity> predicate, float f) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.targetPredicate = predicate;
        this.field_220520_b = f * f;
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().anyMatch(this.targetPredicate);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(arg_0 -> this.lambda$startExecuting$5(livingEntity, brain, arg_0));
    }

    private void lambda$startExecuting$5(LivingEntity livingEntity, Brain brain, List list) {
        list.stream().filter(this.targetPredicate).filter(arg_0 -> this.lambda$startExecuting$3(livingEntity, arg_0)).findFirst().ifPresent(arg_0 -> LookAtEntityTask.lambda$startExecuting$4(brain, arg_0));
    }

    private static void lambda$startExecuting$4(Brain brain, LivingEntity livingEntity) {
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
    }

    private boolean lambda$startExecuting$3(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getDistanceSq(livingEntity) <= (double)this.field_220520_b;
    }

    private static boolean lambda$new$2(LivingEntity livingEntity) {
        return false;
    }

    private static boolean lambda$new$1(EntityType entityType, LivingEntity livingEntity) {
        return entityType.equals(livingEntity.getType());
    }

    private static boolean lambda$new$0(EntityClassification entityClassification, LivingEntity livingEntity) {
        return entityClassification.equals(livingEntity.getType().getClassification());
    }
}

