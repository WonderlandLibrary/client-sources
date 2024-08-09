/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.FindInteractionAndLookTargetTask;
import net.minecraft.entity.ai.brain.task.FindNewAttackTargetTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.GetAngryTask;
import net.minecraft.entity.ai.brain.task.InteractWithDoorTask;
import net.minecraft.entity.ai.brain.task.InteractWithEntityTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsPosTask;
import net.minecraft.entity.ai.brain.task.WorkTask;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.GlobalPos;

public class PiglinBruteBrain {
    protected static Brain<?> func_242354_a(PiglinBruteEntity piglinBruteEntity, Brain<PiglinBruteEntity> brain) {
        PiglinBruteBrain.func_242359_b(piglinBruteEntity, brain);
        PiglinBruteBrain.func_242362_c(piglinBruteEntity, brain);
        PiglinBruteBrain.func_242364_d(piglinBruteEntity, brain);
        brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        brain.setFallbackActivity(Activity.IDLE);
        brain.switchToFallbackActivity();
        return brain;
    }

    protected static void func_242352_a(PiglinBruteEntity piglinBruteEntity) {
        GlobalPos globalPos = GlobalPos.getPosition(piglinBruteEntity.world.getDimensionKey(), piglinBruteEntity.getPosition());
        piglinBruteEntity.getBrain().setMemory(MemoryModuleType.HOME, globalPos);
    }

    private static void func_242359_b(PiglinBruteEntity piglinBruteEntity, Brain<PiglinBruteEntity> brain) {
        brain.registerActivity(Activity.CORE, 0, ImmutableList.of(new LookTask(45, 90), new WalkToTargetTask(), new InteractWithDoorTask(), new GetAngryTask()));
    }

    private static void func_242362_c(PiglinBruteEntity piglinBruteEntity, Brain<PiglinBruteEntity> brain) {
        brain.registerActivity(Activity.IDLE, 10, ImmutableList.of(new ForgetAttackTargetTask<PiglinBruteEntity>(PiglinBruteBrain::func_242349_a), PiglinBruteBrain.func_242346_a(), PiglinBruteBrain.func_242356_b(), new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)));
    }

    private static void func_242364_d(PiglinBruteEntity piglinBruteEntity, Brain<PiglinBruteEntity> brain) {
        brain.registerActivity(Activity.FIGHT, 10, ImmutableList.of(new FindNewAttackTargetTask(arg_0 -> PiglinBruteBrain.lambda$func_242364_d$0(piglinBruteEntity, arg_0)), new MoveToTargetTask(1.0f), new AttackTargetTask(20)), MemoryModuleType.ATTACK_TARGET);
    }

    private static FirstShuffledTask<PiglinBruteEntity> func_242346_a() {
        return new FirstShuffledTask(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityType.PIGLIN, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityType.field_242287_aj, 8.0f), 1), Pair.of(new LookAtEntityTask(8.0f), 1), Pair.of(new DummyTask(30, 60), 1)));
    }

    private static FirstShuffledTask<PiglinBruteEntity> func_242356_b() {
        return new FirstShuffledTask(ImmutableList.of(Pair.of(new WalkRandomlyTask(0.6f), 2), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6f, 2), 2), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.field_242287_aj, 8, MemoryModuleType.INTERACTION_TARGET, 0.6f, 2), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.HOME, 0.6f, 2, 100), 2), Pair.of(new WorkTask(MemoryModuleType.HOME, 0.6f, 5), 2), Pair.of(new DummyTask(30, 60), 1)));
    }

    protected static void func_242358_b(PiglinBruteEntity piglinBruteEntity) {
        Brain<PiglinBruteEntity> brain = piglinBruteEntity.getBrain();
        Activity activity = brain.getTemporaryActivity().orElse(null);
        brain.switchActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        Activity activity2 = brain.getTemporaryActivity().orElse(null);
        if (activity != activity2) {
            PiglinBruteBrain.func_242363_d(piglinBruteEntity);
        }
        piglinBruteEntity.setAggroed(brain.hasMemory(MemoryModuleType.ATTACK_TARGET));
    }

    private static boolean func_242350_a(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        return PiglinBruteBrain.func_242349_a(abstractPiglinEntity).filter(arg_0 -> PiglinBruteBrain.lambda$func_242350_a$1(livingEntity, arg_0)).isPresent();
    }

    private static Optional<? extends LivingEntity> func_242349_a(AbstractPiglinEntity abstractPiglinEntity) {
        Optional<LivingEntity> optional = BrainUtil.getTargetFromMemory(abstractPiglinEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && PiglinBruteBrain.func_242347_a(optional.get())) {
            return optional;
        }
        Optional<? extends LivingEntity> optional2 = PiglinBruteBrain.func_242351_a(abstractPiglinEntity, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        return optional2.isPresent() ? optional2 : abstractPiglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
    }

    private static boolean func_242347_a(LivingEntity livingEntity) {
        return EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity);
    }

    private static Optional<? extends LivingEntity> func_242351_a(AbstractPiglinEntity abstractPiglinEntity, MemoryModuleType<? extends LivingEntity> memoryModuleType) {
        return abstractPiglinEntity.getBrain().getMemory(memoryModuleType).filter(arg_0 -> PiglinBruteBrain.lambda$func_242351_a$2(abstractPiglinEntity, arg_0));
    }

    protected static void func_242353_a(PiglinBruteEntity piglinBruteEntity, LivingEntity livingEntity) {
        if (!(livingEntity instanceof AbstractPiglinEntity)) {
            PiglinTasks.func_234509_e_(piglinBruteEntity, livingEntity);
        }
    }

    protected static void func_242360_c(PiglinBruteEntity piglinBruteEntity) {
        if ((double)piglinBruteEntity.world.rand.nextFloat() < 0.0125) {
            PiglinBruteBrain.func_242363_d(piglinBruteEntity);
        }
    }

    private static void func_242363_d(PiglinBruteEntity piglinBruteEntity) {
        piglinBruteEntity.getBrain().getTemporaryActivity().ifPresent(arg_0 -> PiglinBruteBrain.lambda$func_242363_d$3(piglinBruteEntity, arg_0));
    }

    private static void lambda$func_242363_d$3(PiglinBruteEntity piglinBruteEntity, Activity activity) {
        if (activity == Activity.FIGHT) {
            piglinBruteEntity.func_242345_eT();
        }
    }

    private static boolean lambda$func_242351_a$2(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        return livingEntity.isEntityInRange(abstractPiglinEntity, 12.0);
    }

    private static boolean lambda$func_242350_a$1(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2 == livingEntity;
    }

    private static boolean lambda$func_242364_d$0(PiglinBruteEntity piglinBruteEntity, LivingEntity livingEntity) {
        return !PiglinBruteBrain.func_242350_a(piglinBruteEntity, livingEntity);
    }
}

