/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.WeightedList;
import net.minecraft.world.server.ServerWorld;

public class MultiTask<E extends LivingEntity>
extends Task<E> {
    private final Set<MemoryModuleType<?>> field_220416_b;
    private final Ordering field_220417_c;
    private final RunType field_220418_d;
    private final WeightedList<Task<? super E>> field_220419_e = new WeightedList();

    public MultiTask(Map<MemoryModuleType<?>, MemoryModuleStatus> map, Set<MemoryModuleType<?>> set, Ordering ordering, RunType runType, List<Pair<Task<? super E>, Integer>> list) {
        super(map);
        this.field_220416_b = set;
        this.field_220417_c = ordering;
        this.field_220418_d = runType;
        list.forEach(this::lambda$new$0);
    }

    @Override
    protected boolean shouldContinueExecuting(ServerWorld serverWorld, E e, long l) {
        return this.field_220419_e.func_220655_b().filter(MultiTask::lambda$shouldContinueExecuting$1).anyMatch(arg_0 -> MultiTask.lambda$shouldContinueExecuting$2(serverWorld, e, l, arg_0));
    }

    @Override
    protected boolean isTimedOut(long l) {
        return true;
    }

    @Override
    protected void startExecuting(ServerWorld serverWorld, E e, long l) {
        this.field_220417_c.func_220628_a(this.field_220419_e);
        this.field_220418_d.func_220630_a(this.field_220419_e, serverWorld, e, l);
    }

    @Override
    protected void updateTask(ServerWorld serverWorld, E e, long l) {
        this.field_220419_e.func_220655_b().filter(MultiTask::lambda$updateTask$3).forEach(arg_0 -> MultiTask.lambda$updateTask$4(serverWorld, e, l, arg_0));
    }

    @Override
    protected void resetTask(ServerWorld serverWorld, E e, long l) {
        this.field_220419_e.func_220655_b().filter(MultiTask::lambda$resetTask$5).forEach(arg_0 -> MultiTask.lambda$resetTask$6(serverWorld, e, l, arg_0));
        this.field_220416_b.forEach(((LivingEntity)e).getBrain()::removeMemory);
    }

    @Override
    public String toString() {
        Set set = this.field_220419_e.func_220655_b().filter(MultiTask::lambda$toString$7).collect(Collectors.toSet());
        return "(" + this.getClass().getSimpleName() + "): " + set;
    }

    private static boolean lambda$toString$7(Task task) {
        return task.getStatus() == Task.Status.RUNNING;
    }

    private static void lambda$resetTask$6(ServerWorld serverWorld, LivingEntity livingEntity, long l, Task task) {
        task.stop(serverWorld, livingEntity, l);
    }

    private static boolean lambda$resetTask$5(Task task) {
        return task.getStatus() == Task.Status.RUNNING;
    }

    private static void lambda$updateTask$4(ServerWorld serverWorld, LivingEntity livingEntity, long l, Task task) {
        task.tick(serverWorld, livingEntity, l);
    }

    private static boolean lambda$updateTask$3(Task task) {
        return task.getStatus() == Task.Status.RUNNING;
    }

    private static boolean lambda$shouldContinueExecuting$2(ServerWorld serverWorld, LivingEntity livingEntity, long l, Task task) {
        return task.shouldContinueExecuting(serverWorld, livingEntity, l);
    }

    private static boolean lambda$shouldContinueExecuting$1(Task task) {
        return task.getStatus() == Task.Status.RUNNING;
    }

    private void lambda$new$0(Pair pair) {
        this.field_220419_e.func_226313_a_((Task)pair.getFirst(), (Integer)pair.getSecond());
    }

    static enum Ordering {
        ORDERED(Ordering::lambda$static$0),
        SHUFFLED(WeightedList::func_226309_a_);

        private final Consumer<WeightedList<?>> field_220629_c;

        private Ordering(Consumer<WeightedList<?>> consumer) {
            this.field_220629_c = consumer;
        }

        public void func_220628_a(WeightedList<?> weightedList) {
            this.field_220629_c.accept(weightedList);
        }

        private static void lambda$static$0(WeightedList weightedList) {
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    static enum RunType {
        RUN_ONE{

            @Override
            public <E extends LivingEntity> void func_220630_a(WeightedList<Task<? super E>> weightedList, ServerWorld serverWorld, E e, long l) {
                weightedList.func_220655_b().filter(1::lambda$func_220630_a$0).filter(arg_0 -> 1.lambda$func_220630_a$1(serverWorld, e, l, arg_0)).findFirst();
            }

            private static boolean lambda$func_220630_a$1(ServerWorld serverWorld, LivingEntity livingEntity, long l, Task task) {
                return task.start(serverWorld, livingEntity, l);
            }

            private static boolean lambda$func_220630_a$0(Task task) {
                return task.getStatus() == Task.Status.STOPPED;
            }
        }
        ,
        TRY_ALL{

            @Override
            public <E extends LivingEntity> void func_220630_a(WeightedList<Task<? super E>> weightedList, ServerWorld serverWorld, E e, long l) {
                weightedList.func_220655_b().filter(2::lambda$func_220630_a$0).forEach(arg_0 -> 2.lambda$func_220630_a$1(serverWorld, e, l, arg_0));
            }

            private static void lambda$func_220630_a$1(ServerWorld serverWorld, LivingEntity livingEntity, long l, Task task) {
                task.start(serverWorld, livingEntity, l);
            }

            private static boolean lambda$func_220630_a$0(Task task) {
                return task.getStatus() == Task.Status.STOPPED;
            }
        };


        public abstract <E extends LivingEntity> void func_220630_a(WeightedList<Task<? super E>> var1, ServerWorld var2, E var3, long var4);
    }
}

