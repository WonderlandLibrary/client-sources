/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class CongregateTask
extends Task<LivingEntity> {
    public CongregateTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.MEETING_POINT, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.INTERACTION_TARGET, MemoryModuleStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, LivingEntity livingEntity) {
        Brain<?> brain = livingEntity.getBrain();
        Optional<GlobalPos> optional = brain.getMemory(MemoryModuleType.MEETING_POINT);
        return serverWorld.getRandom().nextInt(100) == 0 && optional.isPresent() && serverWorld.getDimensionKey() == optional.get().getDimension() && optional.get().getPos().withinDistance(livingEntity.getPositionVec(), 4.0) && brain.getMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().anyMatch(CongregateTask::lambda$shouldExecute$0);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, LivingEntity livingEntity, long l) {
        Brain<?> brain = livingEntity.getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(arg_0 -> CongregateTask.lambda$startExecuting$4(livingEntity, brain, arg_0));
    }

    private static void lambda$startExecuting$4(LivingEntity livingEntity, Brain brain, List list) {
        list.stream().filter(CongregateTask::lambda$startExecuting$1).filter(arg_0 -> CongregateTask.lambda$startExecuting$2(livingEntity, arg_0)).findFirst().ifPresent(arg_0 -> CongregateTask.lambda$startExecuting$3(brain, arg_0));
    }

    private static void lambda$startExecuting$3(Brain brain, LivingEntity livingEntity) {
        brain.setMemory(MemoryModuleType.INTERACTION_TARGET, livingEntity);
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(livingEntity, false), 0.3f, 1));
    }

    private static boolean lambda$startExecuting$2(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getDistanceSq(livingEntity) <= 32.0;
    }

    private static boolean lambda$startExecuting$1(LivingEntity livingEntity) {
        return EntityType.VILLAGER.equals(livingEntity.getType());
    }

    private static boolean lambda$shouldExecute$0(LivingEntity livingEntity) {
        return EntityType.VILLAGER.equals(livingEntity.getType());
    }
}

