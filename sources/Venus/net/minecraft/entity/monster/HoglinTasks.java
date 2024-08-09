/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.AnimalBreedTask;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.ChildFollowNearestAdultTask;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.FindNewAttackTargetTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.PredicateTask;
import net.minecraft.entity.ai.brain.task.RandomlyStopAttackingTask;
import net.minecraft.entity.ai.brain.task.RunAwayTask;
import net.minecraft.entity.ai.brain.task.RunSometimesTask;
import net.minecraft.entity.ai.brain.task.SupplementedTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;

public class HoglinTasks {
    private static final RangedInteger field_234372_a_ = TickRangeConverter.convertRange(5, 20);
    private static final RangedInteger field_234373_b_ = RangedInteger.createRangedInteger(5, 16);

    protected static Brain<?> func_234376_a_(Brain<HoglinEntity> brain) {
        HoglinTasks.func_234382_b_(brain);
        HoglinTasks.func_234385_c_(brain);
        HoglinTasks.func_234388_d_(brain);
        HoglinTasks.func_234391_e_(brain);
        brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        brain.setFallbackActivity(Activity.IDLE);
        brain.switchToFallbackActivity();
        return brain;
    }

    private static void func_234382_b_(Brain<HoglinEntity> brain) {
        brain.registerActivity(Activity.CORE, 0, ImmutableList.of(new LookTask(45, 90), new WalkToTargetTask()));
    }

    private static void func_234385_c_(Brain<HoglinEntity> brain) {
        brain.registerActivity(Activity.IDLE, 10, ImmutableList.of(new RandomlyStopAttackingTask(MemoryModuleType.NEAREST_REPELLENT, 200), new AnimalBreedTask(EntityType.HOGLIN, 0.6f), RunAwayTask.func_233963_a_(MemoryModuleType.NEAREST_REPELLENT, 1.0f, 8, true), new ForgetAttackTargetTask<HoglinEntity>(HoglinTasks::func_234392_e_), new SupplementedTask<CreatureEntity>(HoglinEntity::func_234363_eJ_, RunAwayTask.func_233965_b_(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, 0.4f, 8, false)), new RunSometimesTask<LivingEntity>(new LookAtEntityTask(8.0f), RangedInteger.createRangedInteger(30, 60)), new ChildFollowNearestAdultTask(field_234373_b_, 0.6f), HoglinTasks.func_234374_a_()));
    }

    private static void func_234388_d_(Brain<HoglinEntity> brain) {
        brain.registerActivity(Activity.FIGHT, 10, ImmutableList.of(new RandomlyStopAttackingTask(MemoryModuleType.NEAREST_REPELLENT, 200), new AnimalBreedTask(EntityType.HOGLIN, 0.6f), new MoveToTargetTask(1.0f), new SupplementedTask<MobEntity>(HoglinEntity::func_234363_eJ_, new AttackTargetTask(40)), new SupplementedTask<MobEntity>(AgeableEntity::isChild, new AttackTargetTask(15)), new FindNewAttackTargetTask(), new PredicateTask<HoglinEntity>(HoglinTasks::func_234402_j_, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    private static void func_234391_e_(Brain<HoglinEntity> brain) {
        brain.registerActivity(Activity.AVOID, 10, ImmutableList.of(RunAwayTask.func_233965_b_(MemoryModuleType.AVOID_TARGET, 1.3f, 15, false), HoglinTasks.func_234374_a_(), new RunSometimesTask<LivingEntity>(new LookAtEntityTask(8.0f), RangedInteger.createRangedInteger(30, 60)), new PredicateTask<HoglinEntity>(HoglinTasks::func_234394_f_, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static FirstShuffledTask<HoglinEntity> func_234374_a_() {
        return new FirstShuffledTask(ImmutableList.of(Pair.of(new WalkRandomlyTask(0.4f), 2), Pair.of(new WalkTowardsLookTargetTask(0.4f, 3), 2), Pair.of(new DummyTask(30, 60), 1)));
    }

    protected static void func_234377_a_(HoglinEntity hoglinEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        Activity activity = brain.getTemporaryActivity().orElse(null);
        brain.switchActivities(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
        Activity activity2 = brain.getTemporaryActivity().orElse(null);
        if (activity != activity2) {
            HoglinTasks.func_234398_h_(hoglinEntity).ifPresent(hoglinEntity::func_241412_a_);
        }
        hoglinEntity.setAggroed(brain.hasMemory(MemoryModuleType.ATTACK_TARGET));
    }

    protected static void func_234378_a_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        if (!hoglinEntity.isChild()) {
            if (livingEntity.getType() == EntityType.PIGLIN && HoglinTasks.func_234396_g_(hoglinEntity)) {
                HoglinTasks.func_234393_e_(hoglinEntity, livingEntity);
                HoglinTasks.func_234387_c_(hoglinEntity, livingEntity);
            } else {
                HoglinTasks.func_234399_h_(hoglinEntity, livingEntity);
            }
        }
    }

    private static void func_234387_c_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        HoglinTasks.func_234400_i_(hoglinEntity).forEach(arg_0 -> HoglinTasks.lambda$func_234387_c_$0(livingEntity, arg_0));
    }

    private static void func_234390_d_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        LivingEntity livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)hoglinEntity, brain.getMemory(MemoryModuleType.AVOID_TARGET), livingEntity);
        livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)hoglinEntity, brain.getMemory(MemoryModuleType.ATTACK_TARGET), livingEntity2);
        HoglinTasks.func_234393_e_(hoglinEntity, livingEntity2);
    }

    private static void func_234393_e_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        hoglinEntity.getBrain().removeMemory(MemoryModuleType.ATTACK_TARGET);
        hoglinEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        hoglinEntity.getBrain().replaceMemory(MemoryModuleType.AVOID_TARGET, livingEntity, field_234372_a_.getRandomWithinRange(hoglinEntity.world.rand));
    }

    private static Optional<? extends LivingEntity> func_234392_e_(HoglinEntity hoglinEntity) {
        return !HoglinTasks.func_234386_c_(hoglinEntity) && !HoglinTasks.func_234402_j_(hoglinEntity) ? hoglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) : Optional.empty();
    }

    static boolean func_234380_a_(HoglinEntity hoglinEntity, BlockPos blockPos) {
        Optional<BlockPos> optional = hoglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_REPELLENT);
        return optional.isPresent() && optional.get().withinDistance(blockPos, 8.0);
    }

    private static boolean func_234394_f_(HoglinEntity hoglinEntity) {
        return hoglinEntity.func_234363_eJ_() && !HoglinTasks.func_234396_g_(hoglinEntity);
    }

    private static boolean func_234396_g_(HoglinEntity hoglinEntity) {
        int n;
        if (hoglinEntity.isChild()) {
            return true;
        }
        int n2 = hoglinEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0);
        return n2 > (n = hoglinEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0) + 1);
    }

    protected static void func_234384_b_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        brain.removeMemory(MemoryModuleType.PACIFIED);
        brain.removeMemory(MemoryModuleType.BREED_TARGET);
        if (hoglinEntity.isChild()) {
            HoglinTasks.func_234390_d_(hoglinEntity, livingEntity);
        } else {
            HoglinTasks.func_234395_f_(hoglinEntity, livingEntity);
        }
    }

    private static void func_234395_f_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        if (!(hoglinEntity.getBrain().hasActivity(Activity.AVOID) && livingEntity.getType() == EntityType.PIGLIN || !EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity) || livingEntity.getType() == EntityType.HOGLIN || BrainUtil.isTargetWithinDistance(hoglinEntity, livingEntity, 4.0))) {
            HoglinTasks.func_234397_g_(hoglinEntity, livingEntity);
            HoglinTasks.func_234399_h_(hoglinEntity, livingEntity);
        }
    }

    private static void func_234397_g_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        Brain<HoglinEntity> brain = hoglinEntity.getBrain();
        brain.removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.removeMemory(MemoryModuleType.BREED_TARGET);
        brain.replaceMemory(MemoryModuleType.ATTACK_TARGET, livingEntity, 200L);
    }

    private static void func_234399_h_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        HoglinTasks.func_234400_i_(hoglinEntity).forEach(arg_0 -> HoglinTasks.lambda$func_234399_h_$1(livingEntity, arg_0));
    }

    private static void func_234401_i_(HoglinEntity hoglinEntity, LivingEntity livingEntity) {
        if (!HoglinTasks.func_234386_c_(hoglinEntity)) {
            Optional<LivingEntity> optional = hoglinEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
            LivingEntity livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)hoglinEntity, optional, livingEntity);
            HoglinTasks.func_234397_g_(hoglinEntity, livingEntity2);
        }
    }

    public static Optional<SoundEvent> func_234398_h_(HoglinEntity hoglinEntity) {
        return hoglinEntity.getBrain().getTemporaryActivity().map(arg_0 -> HoglinTasks.lambda$func_234398_h_$2(hoglinEntity, arg_0));
    }

    private static SoundEvent func_241413_a_(HoglinEntity hoglinEntity, Activity activity) {
        if (activity != Activity.AVOID && !hoglinEntity.func_234364_eK_()) {
            if (activity == Activity.FIGHT) {
                return SoundEvents.ENTITY_HOGLIN_ANGRY;
            }
            return HoglinTasks.func_241416_h_(hoglinEntity) ? SoundEvents.ENTITY_HOGLIN_RETREAT : SoundEvents.ENTITY_HOGLIN_AMBIENT;
        }
        return SoundEvents.ENTITY_HOGLIN_RETREAT;
    }

    private static List<HoglinEntity> func_234400_i_(HoglinEntity hoglinEntity) {
        return hoglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS).orElse(ImmutableList.of());
    }

    private static boolean func_241416_h_(HoglinEntity hoglinEntity) {
        return hoglinEntity.getBrain().hasMemory(MemoryModuleType.NEAREST_REPELLENT);
    }

    private static boolean func_234402_j_(HoglinEntity hoglinEntity) {
        return hoglinEntity.getBrain().hasMemory(MemoryModuleType.BREED_TARGET);
    }

    protected static boolean func_234386_c_(HoglinEntity hoglinEntity) {
        return hoglinEntity.getBrain().hasMemory(MemoryModuleType.PACIFIED);
    }

    private static SoundEvent lambda$func_234398_h_$2(HoglinEntity hoglinEntity, Activity activity) {
        return HoglinTasks.func_241413_a_(hoglinEntity, activity);
    }

    private static void lambda$func_234399_h_$1(LivingEntity livingEntity, HoglinEntity hoglinEntity) {
        HoglinTasks.func_234401_i_(hoglinEntity, livingEntity);
    }

    private static void lambda$func_234387_c_$0(LivingEntity livingEntity, HoglinEntity hoglinEntity) {
        HoglinTasks.func_234390_d_(hoglinEntity, livingEntity);
    }
}

