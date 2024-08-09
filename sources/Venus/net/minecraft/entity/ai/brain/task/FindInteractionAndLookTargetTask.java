/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class FindInteractionAndLookTargetTask
extends Task<LivingEntity> {
    private final EntityType<?> targetType;
    private final int field_220534_b;
    private final Predicate<LivingEntity> field_220535_c;
    private final Predicate<LivingEntity> field_220536_d;

    public FindInteractionAndLookTargetTask(EntityType<?> entityType, int n, Predicate<LivingEntity> predicate, Predicate<LivingEntity> predicate2) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.targetType = entityType;
        this.field_220534_b = n * n;
        this.field_220535_c = predicate2;
        this.field_220536_d = predicate;
    }

    public FindInteractionAndLookTargetTask(EntityType<?> entityType, int n) {
        this(entityType, n, FindInteractionAndLookTargetTask::lambda$new$0, FindInteractionAndLookTargetTask::lambda$new$1);
    }

    @Override
    public boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        return this.field_220536_d.test(livingEntity) && this.getVisibleMobs(livingEntity).stream().anyMatch(this::isNearInteractableEntity);
    }

    @Override
    public void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        super.startExecuting(serverWorld, livingEntity, l);
        Brain<?> brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(arg_0 -> this.lambda$startExecuting$4(livingEntity, brain, arg_0));
    }

    private boolean isNearInteractableEntity(LivingEntity livingEntity) {
        return this.targetType.equals(livingEntity.getType()) && this.field_220535_c.test(livingEntity);
    }

    private List<LivingEntity> getVisibleMobs(LivingEntity livingEntity) {
        return livingEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get();
    }

    private void lambda$startExecuting$4(LivingEntity livingEntity, Brain brain, List list) {
        list.stream().filter(arg_0 -> this.lambda$startExecuting$2(livingEntity, arg_0)).filter(this::isNearInteractableEntity).findFirst().ifPresent(arg_0 -> FindInteractionAndLookTargetTask.lambda$startExecuting$3(brain, arg_0));
    }

    private static void lambda$startExecuting$3(Brain brain, LivingEntity livingEntity) {
        brain.setMemory(MemoryModuleType.INTERACTION_TARGET, livingEntity);
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
    }

    private boolean lambda$startExecuting$2(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getDistanceSq(livingEntity) <= (double)this.field_220534_b;
    }

    private static boolean lambda$new$1(LivingEntity livingEntity) {
        return false;
    }

    private static boolean lambda$new$0(LivingEntity livingEntity) {
        return false;
    }
}

