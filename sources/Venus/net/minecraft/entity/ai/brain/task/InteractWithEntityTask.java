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
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.world.server.ServerWorld;

public class InteractWithEntityTask<E extends LivingEntity, T extends LivingEntity>
extends Task<E> {
    private final int field_220446_a;
    private final float field_220447_b;
    private final EntityType<? extends T> field_220448_c;
    private final int field_220449_d;
    private final Predicate<T> field_220450_e;
    private final Predicate<E> field_220451_f;
    private final MemoryModuleType<T> field_220452_g;

    public InteractWithEntityTask(EntityType<? extends T> entityType, int n, Predicate<E> predicate, Predicate<T> predicate2, MemoryModuleType<T> memoryModuleType, float f, int n2) {
        super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleStatus.VALUE_PRESENT));
        this.field_220448_c = entityType;
        this.field_220447_b = f;
        this.field_220449_d = n * n;
        this.field_220446_a = n2;
        this.field_220450_e = predicate2;
        this.field_220451_f = predicate;
        this.field_220452_g = memoryModuleType;
    }

    public static <T extends LivingEntity> InteractWithEntityTask<LivingEntity, T> func_220445_a(EntityType<? extends T> entityType, int n, MemoryModuleType<T> memoryModuleType, float f, int n2) {
        return new InteractWithEntityTask<LivingEntity, LivingEntity>(entityType, n, InteractWithEntityTask::lambda$func_220445_a$0, InteractWithEntityTask::lambda$func_220445_a$1, memoryModuleType, f, n2);
    }

    @Override
    protected boolean shouldExecute(ServerWorld serverWorld, E e) {
        return this.field_220451_f.test(e) && this.func_233913_a_(e);
    }

    private boolean func_233913_a_(E e) {
        List<LivingEntity> list = ((LivingEntity)e).getBrain().getMemory(MemoryModuleType.VISIBLE_MOBS).get();
        return list.stream().anyMatch(this::func_233914_b_);
    }

    private boolean func_233914_b_(LivingEntity livingEntity) {
        return this.field_220448_c.equals(livingEntity.getType()) && this.field_220450_e.test(livingEntity);
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        Brain<?> brain = ((LivingEntity)e).getBrain();
        brain.getMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(arg_0 -> this.lambda$startExecuting$6(e, brain, arg_0));
    }

    private void lambda$startExecuting$6(LivingEntity livingEntity, Brain brain, List list) {
        list.stream().filter(this::lambda$startExecuting$2).map(InteractWithEntityTask::lambda$startExecuting$3).filter(arg_0 -> this.lambda$startExecuting$4(livingEntity, arg_0)).filter(this.field_220450_e).findFirst().ifPresent(arg_0 -> this.lambda$startExecuting$5(brain, arg_0));
    }

    private void lambda$startExecuting$5(Brain brain, LivingEntity livingEntity) {
        brain.setMemory(this.field_220452_g, livingEntity);
        brain.setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity, true));
        brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityPosWrapper(livingEntity, false), this.field_220447_b, this.field_220446_a));
    }

    private boolean lambda$startExecuting$4(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2.getDistanceSq(livingEntity) <= (double)this.field_220449_d;
    }

    private static LivingEntity lambda$startExecuting$3(LivingEntity livingEntity) {
        return livingEntity;
    }

    private boolean lambda$startExecuting$2(LivingEntity livingEntity) {
        return this.field_220448_c.equals(livingEntity.getType());
    }

    private static boolean lambda$func_220445_a$1(LivingEntity livingEntity) {
        return false;
    }

    private static boolean lambda$func_220445_a$0(LivingEntity livingEntity) {
        return false;
    }
}

