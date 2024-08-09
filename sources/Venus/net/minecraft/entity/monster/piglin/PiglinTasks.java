/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.AttackStrafingTask;
import net.minecraft.entity.ai.brain.task.AttackTargetTask;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.EndAttackTask;
import net.minecraft.entity.ai.brain.task.FindInteractionAndLookTargetTask;
import net.minecraft.entity.ai.brain.task.FindNewAttackTargetTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.GetAngryTask;
import net.minecraft.entity.ai.brain.task.HuntCelebrationTask;
import net.minecraft.entity.ai.brain.task.InteractWithDoorTask;
import net.minecraft.entity.ai.brain.task.InteractWithEntityTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.PickupWantedItemTask;
import net.minecraft.entity.ai.brain.task.PiglinIdleActivityTask;
import net.minecraft.entity.ai.brain.task.PredicateTask;
import net.minecraft.entity.ai.brain.task.RideEntityTask;
import net.minecraft.entity.ai.brain.task.RunAwayTask;
import net.minecraft.entity.ai.brain.task.RunSometimesTask;
import net.minecraft.entity.ai.brain.task.ShootTargetTask;
import net.minecraft.entity.ai.brain.task.StopRidingEntityTask;
import net.minecraft.entity.ai.brain.task.SupplementedTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyTask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.AdmireItemTask;
import net.minecraft.entity.monster.piglin.FinishedHuntTask;
import net.minecraft.entity.monster.piglin.ForgetAdmiredItemTask;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.monster.piglin.StartAdmiringItemTask;
import net.minecraft.entity.monster.piglin.StartHuntTask;
import net.minecraft.entity.monster.piglin.StopReachingItemTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.TickRangeConverter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;

public class PiglinTasks {
    public static final Item field_234444_a_ = Items.GOLD_INGOT;
    private static final RangedInteger field_234445_b_ = TickRangeConverter.convertRange(30, 120);
    private static final RangedInteger field_234446_c_ = TickRangeConverter.convertRange(10, 40);
    private static final RangedInteger field_234447_d_ = TickRangeConverter.convertRange(10, 30);
    private static final RangedInteger field_234448_e_ = TickRangeConverter.convertRange(5, 20);
    private static final RangedInteger field_234449_f_ = TickRangeConverter.convertRange(5, 7);
    private static final RangedInteger field_241418_g_ = TickRangeConverter.convertRange(5, 7);
    private static final Set<Item> field_234450_g_ = ImmutableSet.of(Items.PORKCHOP, Items.COOKED_PORKCHOP);

    protected static Brain<?> func_234469_a_(PiglinEntity piglinEntity, Brain<PiglinEntity> brain) {
        PiglinTasks.func_234464_a_(brain);
        PiglinTasks.func_234485_b_(brain);
        PiglinTasks.func_234502_d_(brain);
        PiglinTasks.func_234488_b_(piglinEntity, brain);
        PiglinTasks.func_234495_c_(brain);
        PiglinTasks.func_234507_e_(brain);
        PiglinTasks.func_234511_f_(brain);
        brain.setDefaultActivities(ImmutableSet.of(Activity.CORE));
        brain.setFallbackActivity(Activity.IDLE);
        brain.switchToFallbackActivity();
        return brain;
    }

    protected static void func_234466_a_(PiglinEntity piglinEntity) {
        int n = field_234445_b_.getRandomWithinRange(piglinEntity.world.rand);
        piglinEntity.getBrain().replaceMemory(MemoryModuleType.HUNTED_RECENTLY, true, n);
    }

    private static void func_234464_a_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.CORE, 0, ImmutableList.of(new LookTask(45, 90), new WalkToTargetTask(), new InteractWithDoorTask(), PiglinTasks.func_241428_d_(), PiglinTasks.func_234500_d_(), new StartAdmiringItemTask(), new AdmireItemTask(120), new EndAttackTask(300, PiglinTasks::func_234461_a_), new GetAngryTask()));
    }

    private static void func_234485_b_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.IDLE, 10, ImmutableList.of(new LookAtEntityTask(PiglinTasks::func_234482_b_, 14.0f), new ForgetAttackTargetTask<PiglinEntity>(AbstractPiglinEntity::func_242337_eM, PiglinTasks::func_234526_m_), new SupplementedTask<PiglinEntity>(PiglinEntity::func_234422_eK_, new StartHuntTask()), PiglinTasks.func_234493_c_(), PiglinTasks.func_234505_e_(), PiglinTasks.func_234458_a_(), PiglinTasks.func_234481_b_(), new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)));
    }

    private static void func_234488_b_(PiglinEntity piglinEntity, Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.FIGHT, 10, ImmutableList.of(new FindNewAttackTargetTask(arg_0 -> PiglinTasks.lambda$func_234488_b_$0(piglinEntity, arg_0)), new SupplementedTask<PiglinEntity>(PiglinTasks::func_234494_c_, new AttackStrafingTask(5, 0.75f)), new MoveToTargetTask(1.0f), new AttackTargetTask(20), new ShootTargetTask(), new FinishedHuntTask(), new PredicateTask<PiglinEntity>(PiglinTasks::func_234525_l_, MemoryModuleType.ATTACK_TARGET)), MemoryModuleType.ATTACK_TARGET);
    }

    private static void func_234495_c_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.CELEBRATE, 10, ImmutableList.of(PiglinTasks.func_234493_c_(), new LookAtEntityTask(PiglinTasks::func_234482_b_, 14.0f), new ForgetAttackTargetTask<PiglinEntity>(AbstractPiglinEntity::func_242337_eM, PiglinTasks::func_234526_m_), new SupplementedTask<PiglinEntity>(PiglinTasks::lambda$func_234495_c_$1, new HuntCelebrationTask(2, 1.0f)), new SupplementedTask<PiglinEntity>(PiglinEntity::func_234425_eN_, new HuntCelebrationTask(4, 0.6f)), new FirstShuffledTask(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.PIGLIN, 8.0f), 1), Pair.of(new WalkRandomlyTask(0.6f, 2, 1), 1), Pair.of(new DummyTask(10, 20), 1)))), MemoryModuleType.CELEBRATE_LOCATION);
    }

    private static void func_234502_d_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.ADMIRE_ITEM, 10, ImmutableList.of(new PickupWantedItemTask<PiglinEntity>(PiglinTasks::func_234455_E_, 1.0f, true, 9), new ForgetAdmiredItemTask(9), new StopReachingItemTask(200, 200)), MemoryModuleType.ADMIRING_ITEM);
    }

    private static void func_234507_e_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.AVOID, 10, ImmutableList.of(RunAwayTask.func_233965_b_(MemoryModuleType.AVOID_TARGET, 1.0f, 12, true), PiglinTasks.func_234458_a_(), PiglinTasks.func_234481_b_(), new PredicateTask<PiglinEntity>(PiglinTasks::func_234533_t_, MemoryModuleType.AVOID_TARGET)), MemoryModuleType.AVOID_TARGET);
    }

    private static void func_234511_f_(Brain<PiglinEntity> brain) {
        brain.registerActivity(Activity.RIDE, 10, ImmutableList.of(new RideEntityTask(0.8f), new LookAtEntityTask(PiglinTasks::func_234482_b_, 8.0f), new SupplementedTask<PiglinEntity>(Entity::isPassenger, PiglinTasks.func_234458_a_()), new StopRidingEntityTask(8, PiglinTasks::func_234467_a_)), MemoryModuleType.RIDE_TARGET);
    }

    private static FirstShuffledTask<PiglinEntity> func_234458_a_() {
        return new FirstShuffledTask(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityType.PIGLIN, 8.0f), 1), Pair.of(new LookAtEntityTask(8.0f), 1), Pair.of(new DummyTask(30, 60), 1)));
    }

    private static FirstShuffledTask<PiglinEntity> func_234481_b_() {
        return new FirstShuffledTask(ImmutableList.of(Pair.of(new WalkRandomlyTask(0.6f), 2), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6f, 2), 2), Pair.of(new SupplementedTask<LivingEntity>(PiglinTasks::func_234514_g_, new WalkTowardsLookTargetTask(0.6f, 3)), 2), Pair.of(new DummyTask(30, 60), 1)));
    }

    private static RunAwayTask<BlockPos> func_234493_c_() {
        return RunAwayTask.func_233963_a_(MemoryModuleType.NEAREST_REPELLENT, 1.0f, 8, false);
    }

    private static PiglinIdleActivityTask<PiglinEntity, LivingEntity> func_241428_d_() {
        return new PiglinIdleActivityTask<PiglinEntity, LivingEntity>(PiglinEntity::isChild, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, field_241418_g_);
    }

    private static PiglinIdleActivityTask<PiglinEntity, LivingEntity> func_234500_d_() {
        return new PiglinIdleActivityTask<PiglinEntity, LivingEntity>(PiglinTasks::func_234525_l_, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, field_234449_f_);
    }

    protected static void func_234486_b_(PiglinEntity piglinEntity) {
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        Activity activity = brain.getTemporaryActivity().orElse(null);
        brain.switchActivities(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.AVOID, Activity.CELEBRATE, Activity.RIDE, Activity.IDLE));
        Activity activity2 = brain.getTemporaryActivity().orElse(null);
        if (activity != activity2) {
            PiglinTasks.func_241429_d_(piglinEntity).ifPresent(piglinEntity::func_241417_a_);
        }
        piglinEntity.setAggroed(brain.hasMemory(MemoryModuleType.ATTACK_TARGET));
        if (!brain.hasMemory(MemoryModuleType.RIDE_TARGET) && PiglinTasks.func_234522_j_(piglinEntity)) {
            piglinEntity.stopRiding();
        }
        if (!brain.hasMemory(MemoryModuleType.CELEBRATE_LOCATION)) {
            brain.removeMemory(MemoryModuleType.DANCING);
        }
        piglinEntity.func_234442_u_(brain.hasMemory(MemoryModuleType.DANCING));
    }

    private static boolean func_234522_j_(PiglinEntity piglinEntity) {
        if (!piglinEntity.isChild()) {
            return true;
        }
        Entity entity2 = piglinEntity.getRidingEntity();
        return entity2 instanceof PiglinEntity && ((PiglinEntity)entity2).isChild() || entity2 instanceof HoglinEntity && ((HoglinEntity)entity2).isChild();
    }

    protected static void func_234470_a_(PiglinEntity piglinEntity, ItemEntity itemEntity) {
        ItemStack itemStack;
        PiglinTasks.func_234531_r_(piglinEntity);
        if (itemEntity.getItem().getItem() == Items.GOLD_NUGGET) {
            piglinEntity.onItemPickup(itemEntity, itemEntity.getItem().getCount());
            itemStack = itemEntity.getItem();
            itemEntity.remove();
        } else {
            piglinEntity.onItemPickup(itemEntity, 1);
            itemStack = PiglinTasks.func_234465_a_(itemEntity);
        }
        Item item = itemStack.getItem();
        if (PiglinTasks.func_234480_a_(item)) {
            piglinEntity.getBrain().removeMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            PiglinTasks.func_241427_c_(piglinEntity, itemStack);
            PiglinTasks.func_234501_d_(piglinEntity);
        } else if (PiglinTasks.func_234499_c_(item) && !PiglinTasks.func_234538_z_(piglinEntity)) {
            PiglinTasks.func_234536_x_(piglinEntity);
        } else {
            boolean bl = piglinEntity.func_233665_g_(itemStack);
            if (!bl) {
                PiglinTasks.func_234498_c_(piglinEntity, itemStack);
            }
        }
    }

    private static void func_241427_c_(PiglinEntity piglinEntity, ItemStack itemStack) {
        if (PiglinTasks.func_234454_D_(piglinEntity)) {
            piglinEntity.entityDropItem(piglinEntity.getHeldItem(Hand.OFF_HAND));
        }
        piglinEntity.func_234439_n_(itemStack);
    }

    private static ItemStack func_234465_a_(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        ItemStack itemStack2 = itemStack.split(1);
        if (itemStack.isEmpty()) {
            itemEntity.remove();
        } else {
            itemEntity.setItem(itemStack);
        }
        return itemStack2;
    }

    protected static void func_234477_a_(PiglinEntity piglinEntity, boolean bl) {
        ItemStack itemStack = piglinEntity.getHeldItem(Hand.OFF_HAND);
        piglinEntity.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
        if (piglinEntity.func_242337_eM()) {
            boolean bl2;
            boolean bl3 = PiglinTasks.func_234492_b_(itemStack.getItem());
            if (bl && bl3) {
                PiglinTasks.func_234475_a_(piglinEntity, PiglinTasks.func_234524_k_(piglinEntity));
            } else if (!bl3 && !(bl2 = piglinEntity.func_233665_g_(itemStack))) {
                PiglinTasks.func_234498_c_(piglinEntity, itemStack);
            }
        } else {
            boolean bl4 = piglinEntity.func_233665_g_(itemStack);
            if (!bl4) {
                ItemStack itemStack2 = piglinEntity.getHeldItemMainhand();
                if (PiglinTasks.func_234480_a_(itemStack2.getItem())) {
                    PiglinTasks.func_234498_c_(piglinEntity, itemStack2);
                } else {
                    PiglinTasks.func_234475_a_(piglinEntity, Collections.singletonList(itemStack2));
                }
                piglinEntity.func_234438_m_(itemStack);
            }
        }
    }

    protected static void func_234496_c_(PiglinEntity piglinEntity) {
        if (PiglinTasks.func_234451_A_(piglinEntity) && !piglinEntity.getHeldItemOffhand().isEmpty()) {
            piglinEntity.entityDropItem(piglinEntity.getHeldItemOffhand());
            piglinEntity.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
        }
    }

    private static void func_234498_c_(PiglinEntity piglinEntity, ItemStack itemStack) {
        ItemStack itemStack2 = piglinEntity.func_234436_k_(itemStack);
        PiglinTasks.func_234490_b_(piglinEntity, Collections.singletonList(itemStack2));
    }

    private static void func_234475_a_(PiglinEntity piglinEntity, List<ItemStack> list) {
        Optional<PlayerEntity> optional = piglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isPresent()) {
            PiglinTasks.func_234472_a_(piglinEntity, optional.get(), list);
        } else {
            PiglinTasks.func_234490_b_(piglinEntity, list);
        }
    }

    private static void func_234490_b_(PiglinEntity piglinEntity, List<ItemStack> list) {
        PiglinTasks.func_234476_a_(piglinEntity, list, PiglinTasks.func_234537_y_(piglinEntity));
    }

    private static void func_234472_a_(PiglinEntity piglinEntity, PlayerEntity playerEntity, List<ItemStack> list) {
        PiglinTasks.func_234476_a_(piglinEntity, list, playerEntity.getPositionVec());
    }

    private static void func_234476_a_(PiglinEntity piglinEntity, List<ItemStack> list, Vector3d vector3d) {
        if (!list.isEmpty()) {
            piglinEntity.swingArm(Hand.OFF_HAND);
            for (ItemStack itemStack : list) {
                BrainUtil.spawnItemNearEntity(piglinEntity, itemStack, vector3d.add(0.0, 1.0, 0.0));
            }
        }
    }

    private static List<ItemStack> func_234524_k_(PiglinEntity piglinEntity) {
        LootTable lootTable = piglinEntity.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.PIGLIN_BARTERING);
        return lootTable.generate(new LootContext.Builder((ServerWorld)piglinEntity.world).withParameter(LootParameters.THIS_ENTITY, piglinEntity).withRandom(piglinEntity.world.rand).build(LootParameterSets.field_237453_h_));
    }

    private static boolean func_234461_a_(LivingEntity livingEntity, LivingEntity livingEntity2) {
        if (livingEntity2.getType() != EntityType.HOGLIN) {
            return true;
        }
        return new Random(livingEntity.world.getGameTime()).nextFloat() < 0.1f;
    }

    protected static boolean func_234474_a_(PiglinEntity piglinEntity, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item.isIn(ItemTags.PIGLIN_REPELLENTS)) {
            return true;
        }
        if (PiglinTasks.func_234453_C_(piglinEntity) && piglinEntity.getBrain().hasMemory(MemoryModuleType.ATTACK_TARGET)) {
            return true;
        }
        if (PiglinTasks.func_234492_b_(item)) {
            return PiglinTasks.func_234455_E_(piglinEntity);
        }
        boolean bl = piglinEntity.func_234437_l_(itemStack);
        if (item == Items.GOLD_NUGGET) {
            return bl;
        }
        if (PiglinTasks.func_234499_c_(item)) {
            return !PiglinTasks.func_234538_z_(piglinEntity) && bl;
        }
        if (!PiglinTasks.func_234480_a_(item)) {
            return piglinEntity.func_234440_o_(itemStack);
        }
        return PiglinTasks.func_234455_E_(piglinEntity) && bl;
    }

    protected static boolean func_234480_a_(Item item) {
        return item.isIn(ItemTags.PIGLIN_LOVED);
    }

    private static boolean func_234467_a_(PiglinEntity piglinEntity, Entity entity2) {
        if (!(entity2 instanceof MobEntity)) {
            return true;
        }
        MobEntity mobEntity = (MobEntity)entity2;
        return !mobEntity.isChild() || !mobEntity.isAlive() || PiglinTasks.func_234517_h_(piglinEntity) || PiglinTasks.func_234517_h_(mobEntity) || mobEntity instanceof PiglinEntity && mobEntity.getRidingEntity() == null;
    }

    private static boolean func_234504_d_(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        return PiglinTasks.func_234526_m_(piglinEntity).filter(arg_0 -> PiglinTasks.lambda$func_234504_d_$2(livingEntity, arg_0)).isPresent();
    }

    private static boolean func_234525_l_(PiglinEntity piglinEntity) {
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        if (brain.hasMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)) {
            LivingEntity livingEntity = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED).get();
            return piglinEntity.isEntityInRange(livingEntity, 6.0);
        }
        return true;
    }

    private static Optional<? extends LivingEntity> func_234526_m_(PiglinEntity piglinEntity) {
        Optional<LivingEntity> optional;
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        if (PiglinTasks.func_234525_l_(piglinEntity)) {
            return Optional.empty();
        }
        Optional<LivingEntity> optional2 = BrainUtil.getTargetFromMemory(piglinEntity, MemoryModuleType.ANGRY_AT);
        if (optional2.isPresent() && PiglinTasks.func_234506_e_(optional2.get())) {
            return optional2;
        }
        if (brain.hasMemory(MemoryModuleType.UNIVERSAL_ANGER) && (optional = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)).isPresent()) {
            return optional;
        }
        optional = brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        if (optional.isPresent()) {
            return optional;
        }
        Optional<PlayerEntity> optional3 = brain.getMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
        return optional3.isPresent() && PiglinTasks.func_234506_e_(optional3.get()) ? optional3 : Optional.empty();
    }

    public static void func_234478_a_(PlayerEntity playerEntity, boolean bl) {
        List<PiglinEntity> list = playerEntity.world.getEntitiesWithinAABB(PiglinEntity.class, playerEntity.getBoundingBox().grow(16.0));
        list.stream().filter(PiglinTasks::func_234520_i_).filter(arg_0 -> PiglinTasks.lambda$func_234478_a_$3(bl, playerEntity, arg_0)).forEach(arg_0 -> PiglinTasks.lambda$func_234478_a_$4(playerEntity, arg_0));
    }

    public static ActionResultType func_234471_a_(PiglinEntity piglinEntity, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (PiglinTasks.func_234489_b_(piglinEntity, itemStack)) {
            ItemStack itemStack2 = itemStack.split(1);
            PiglinTasks.func_241427_c_(piglinEntity, itemStack2);
            PiglinTasks.func_234501_d_(piglinEntity);
            PiglinTasks.func_234531_r_(piglinEntity);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.PASS;
    }

    protected static boolean func_234489_b_(PiglinEntity piglinEntity, ItemStack itemStack) {
        return !PiglinTasks.func_234453_C_(piglinEntity) && !PiglinTasks.func_234451_A_(piglinEntity) && piglinEntity.func_242337_eM() && PiglinTasks.func_234492_b_(itemStack.getItem());
    }

    protected static void func_234468_a_(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        if (!(livingEntity instanceof PiglinEntity)) {
            if (PiglinTasks.func_234454_D_(piglinEntity)) {
                PiglinTasks.func_234477_a_(piglinEntity, false);
            }
            Brain<PiglinEntity> brain = piglinEntity.getBrain();
            brain.removeMemory(MemoryModuleType.CELEBRATE_LOCATION);
            brain.removeMemory(MemoryModuleType.DANCING);
            brain.removeMemory(MemoryModuleType.ADMIRING_ITEM);
            if (livingEntity instanceof PlayerEntity) {
                brain.replaceMemory(MemoryModuleType.ADMIRING_DISABLED, true, 400L);
            }
            PiglinTasks.func_234515_g_(piglinEntity).ifPresent(arg_0 -> PiglinTasks.lambda$func_234468_a_$5(livingEntity, brain, arg_0));
            if (piglinEntity.isChild()) {
                brain.replaceMemory(MemoryModuleType.AVOID_TARGET, livingEntity, 100L);
                if (PiglinTasks.func_234506_e_(livingEntity)) {
                    PiglinTasks.func_234487_b_(piglinEntity, livingEntity);
                }
            } else if (livingEntity.getType() == EntityType.HOGLIN && PiglinTasks.func_234535_v_(piglinEntity)) {
                PiglinTasks.func_234521_i_(piglinEntity, livingEntity);
                PiglinTasks.func_234516_g_(piglinEntity, livingEntity);
            } else {
                PiglinTasks.func_234509_e_(piglinEntity, livingEntity);
            }
        }
    }

    protected static void func_234509_e_(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        if (!abstractPiglinEntity.getBrain().hasActivity(Activity.AVOID) && PiglinTasks.func_234506_e_(livingEntity) && !BrainUtil.isTargetWithinDistance(abstractPiglinEntity, livingEntity, 4.0)) {
            if (livingEntity.getType() == EntityType.PLAYER && abstractPiglinEntity.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                PiglinTasks.func_241431_f_(abstractPiglinEntity, livingEntity);
                PiglinTasks.func_241430_f_(abstractPiglinEntity);
            } else {
                PiglinTasks.func_234497_c_(abstractPiglinEntity, livingEntity);
                PiglinTasks.func_234487_b_(abstractPiglinEntity, livingEntity);
            }
        }
    }

    public static Optional<SoundEvent> func_241429_d_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().getTemporaryActivity().map(arg_0 -> PiglinTasks.lambda$func_241429_d_$6(piglinEntity, arg_0));
    }

    private static SoundEvent func_241422_a_(PiglinEntity piglinEntity, Activity activity) {
        if (activity == Activity.FIGHT) {
            return SoundEvents.ENTITY_PIGLIN_ANGRY;
        }
        if (piglinEntity.func_242336_eL()) {
            return SoundEvents.ENTITY_PIGLIN_RETREAT;
        }
        if (activity == Activity.AVOID && PiglinTasks.func_234528_o_(piglinEntity)) {
            return SoundEvents.ENTITY_PIGLIN_RETREAT;
        }
        if (activity == Activity.ADMIRE_ITEM) {
            return SoundEvents.ENTITY_PIGLIN_ADMIRING_ITEM;
        }
        if (activity == Activity.CELEBRATE) {
            return SoundEvents.ENTITY_PIGLIN_CELEBRATE;
        }
        if (PiglinTasks.func_234510_f_(piglinEntity)) {
            return SoundEvents.ENTITY_PIGLIN_JEALOUS;
        }
        return PiglinTasks.func_234452_B_(piglinEntity) ? SoundEvents.ENTITY_PIGLIN_RETREAT : SoundEvents.ENTITY_PIGLIN_AMBIENT;
    }

    private static boolean func_234528_o_(PiglinEntity piglinEntity) {
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        return !brain.hasMemory(MemoryModuleType.AVOID_TARGET) ? false : brain.getMemory(MemoryModuleType.AVOID_TARGET).get().isEntityInRange(piglinEntity, 12.0);
    }

    protected static boolean func_234508_e_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.HUNTED_RECENTLY) || PiglinTasks.func_234529_p_(piglinEntity).stream().anyMatch(PiglinTasks::lambda$func_234508_e_$7);
    }

    private static List<AbstractPiglinEntity> func_234529_p_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS).orElse(ImmutableList.of());
    }

    private static List<AbstractPiglinEntity> func_234530_q_(AbstractPiglinEntity abstractPiglinEntity) {
        return abstractPiglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_ADULT_PIGLINS).orElse(ImmutableList.of());
    }

    public static boolean func_234460_a_(LivingEntity livingEntity) {
        for (ItemStack itemStack : livingEntity.getArmorInventoryList()) {
            Item item = itemStack.getItem();
            if (!(item instanceof ArmorItem) || ((ArmorItem)item).getArmorMaterial() != ArmorMaterial.GOLD) continue;
            return false;
        }
        return true;
    }

    private static void func_234531_r_(PiglinEntity piglinEntity) {
        piglinEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        piglinEntity.getNavigator().clearPath();
    }

    private static RunSometimesTask<PiglinEntity> func_234505_e_() {
        return new RunSometimesTask<PiglinEntity>(new PiglinIdleActivityTask<PiglinEntity, Entity>(PiglinEntity::isChild, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, field_234447_d_), field_234446_c_);
    }

    protected static void func_234487_b_(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        PiglinTasks.func_234530_q_(abstractPiglinEntity).forEach(arg_0 -> PiglinTasks.lambda$func_234487_b_$8(livingEntity, arg_0));
    }

    protected static void func_241430_f_(AbstractPiglinEntity abstractPiglinEntity) {
        PiglinTasks.func_234530_q_(abstractPiglinEntity).forEach(PiglinTasks::lambda$func_241430_f_$10);
    }

    protected static void func_234512_f_(PiglinEntity piglinEntity) {
        PiglinTasks.func_234529_p_(piglinEntity).forEach(PiglinTasks::func_234518_h_);
    }

    protected static void func_234497_c_(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        if (PiglinTasks.func_234506_e_(livingEntity)) {
            abstractPiglinEntity.getBrain().removeMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            abstractPiglinEntity.getBrain().replaceMemory(MemoryModuleType.ANGRY_AT, livingEntity.getUniqueID(), 600L);
            if (livingEntity.getType() == EntityType.HOGLIN && abstractPiglinEntity.func_234422_eK_()) {
                PiglinTasks.func_234518_h_(abstractPiglinEntity);
            }
            if (livingEntity.getType() == EntityType.PLAYER && abstractPiglinEntity.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                abstractPiglinEntity.getBrain().replaceMemory(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
            }
        }
    }

    private static void func_241431_f_(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        Optional<PlayerEntity> optional = PiglinTasks.func_241432_i_(abstractPiglinEntity);
        if (optional.isPresent()) {
            PiglinTasks.func_234497_c_(abstractPiglinEntity, optional.get());
        } else {
            PiglinTasks.func_234497_c_(abstractPiglinEntity, livingEntity);
        }
    }

    private static void func_234513_f_(AbstractPiglinEntity abstractPiglinEntity, LivingEntity livingEntity) {
        Optional<LivingEntity> optional = PiglinTasks.func_234532_s_(abstractPiglinEntity);
        LivingEntity livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)abstractPiglinEntity, optional, livingEntity);
        if (!optional.isPresent() || optional.get() != livingEntity2) {
            PiglinTasks.func_234497_c_(abstractPiglinEntity, livingEntity2);
        }
    }

    private static Optional<LivingEntity> func_234532_s_(AbstractPiglinEntity abstractPiglinEntity) {
        return BrainUtil.getTargetFromMemory(abstractPiglinEntity, MemoryModuleType.ANGRY_AT);
    }

    public static Optional<LivingEntity> func_234515_g_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.AVOID_TARGET) ? piglinEntity.getBrain().getMemory(MemoryModuleType.AVOID_TARGET) : Optional.empty();
    }

    public static Optional<PlayerEntity> func_241432_i_(AbstractPiglinEntity abstractPiglinEntity) {
        return abstractPiglinEntity.getBrain().hasMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) ? abstractPiglinEntity.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER) : Optional.empty();
    }

    private static void func_234516_g_(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        PiglinTasks.func_234529_p_(piglinEntity).stream().filter(PiglinTasks::lambda$func_234516_g_$11).forEach(arg_0 -> PiglinTasks.lambda$func_234516_g_$12(livingEntity, arg_0));
    }

    private static void func_234519_h_(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        LivingEntity livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)piglinEntity, brain.getMemory(MemoryModuleType.AVOID_TARGET), livingEntity);
        livingEntity2 = BrainUtil.getNearestEntity((LivingEntity)piglinEntity, brain.getMemory(MemoryModuleType.ATTACK_TARGET), livingEntity2);
        PiglinTasks.func_234521_i_(piglinEntity, livingEntity2);
    }

    private static boolean func_234533_t_(PiglinEntity piglinEntity) {
        Brain<PiglinEntity> brain = piglinEntity.getBrain();
        if (!brain.hasMemory(MemoryModuleType.AVOID_TARGET)) {
            return false;
        }
        LivingEntity livingEntity = brain.getMemory(MemoryModuleType.AVOID_TARGET).get();
        EntityType<?> entityType = livingEntity.getType();
        if (entityType == EntityType.HOGLIN) {
            return PiglinTasks.func_234534_u_(piglinEntity);
        }
        if (PiglinTasks.func_234459_a_(entityType)) {
            return !brain.hasMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, livingEntity);
        }
        return true;
    }

    private static boolean func_234534_u_(PiglinEntity piglinEntity) {
        return !PiglinTasks.func_234535_v_(piglinEntity);
    }

    private static boolean func_234535_v_(PiglinEntity piglinEntity) {
        int n = piglinEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0) + 1;
        int n2 = piglinEntity.getBrain().getMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0);
        return n2 > n;
    }

    private static void func_234521_i_(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        piglinEntity.getBrain().removeMemory(MemoryModuleType.ANGRY_AT);
        piglinEntity.getBrain().removeMemory(MemoryModuleType.ATTACK_TARGET);
        piglinEntity.getBrain().removeMemory(MemoryModuleType.WALK_TARGET);
        piglinEntity.getBrain().replaceMemory(MemoryModuleType.AVOID_TARGET, livingEntity, field_234448_e_.getRandomWithinRange(piglinEntity.world.rand));
        PiglinTasks.func_234518_h_(piglinEntity);
    }

    protected static void func_234518_h_(AbstractPiglinEntity abstractPiglinEntity) {
        abstractPiglinEntity.getBrain().replaceMemory(MemoryModuleType.HUNTED_RECENTLY, true, field_234445_b_.getRandomWithinRange(abstractPiglinEntity.world.rand));
    }

    private static void func_234536_x_(PiglinEntity piglinEntity) {
        piglinEntity.getBrain().replaceMemory(MemoryModuleType.ATE_RECENTLY, true, 200L);
    }

    private static Vector3d func_234537_y_(PiglinEntity piglinEntity) {
        Vector3d vector3d = RandomPositionGenerator.getLandPos(piglinEntity, 4, 2);
        return vector3d == null ? piglinEntity.getPositionVec() : vector3d;
    }

    private static boolean func_234538_z_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.ATE_RECENTLY);
    }

    protected static boolean func_234520_i_(AbstractPiglinEntity abstractPiglinEntity) {
        return abstractPiglinEntity.getBrain().hasActivity(Activity.IDLE);
    }

    private static boolean func_234494_c_(LivingEntity livingEntity) {
        return livingEntity.canEquip(Items.CROSSBOW);
    }

    private static void func_234501_d_(LivingEntity livingEntity) {
        livingEntity.getBrain().replaceMemory(MemoryModuleType.ADMIRING_ITEM, true, 120L);
    }

    private static boolean func_234451_A_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.ADMIRING_ITEM);
    }

    private static boolean func_234492_b_(Item item) {
        return item == field_234444_a_;
    }

    private static boolean func_234499_c_(Item item) {
        return field_234450_g_.contains(item);
    }

    private static boolean func_234506_e_(LivingEntity livingEntity) {
        return EntityPredicates.CAN_HOSTILE_AI_TARGET.test(livingEntity);
    }

    private static boolean func_234452_B_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.NEAREST_REPELLENT);
    }

    private static boolean func_234510_f_(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemory(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    private static boolean func_234514_g_(LivingEntity livingEntity) {
        return !PiglinTasks.func_234510_f_(livingEntity);
    }

    public static boolean func_234482_b_(LivingEntity livingEntity) {
        return livingEntity.getType() == EntityType.PLAYER && livingEntity.func_233634_a_(PiglinTasks::func_234480_a_);
    }

    private static boolean func_234453_C_(PiglinEntity piglinEntity) {
        return piglinEntity.getBrain().hasMemory(MemoryModuleType.ADMIRING_DISABLED);
    }

    private static boolean func_234517_h_(LivingEntity livingEntity) {
        return livingEntity.getBrain().hasMemory(MemoryModuleType.HURT_BY);
    }

    private static boolean func_234454_D_(PiglinEntity piglinEntity) {
        return !piglinEntity.getHeldItemOffhand().isEmpty();
    }

    private static boolean func_234455_E_(PiglinEntity piglinEntity) {
        return piglinEntity.getHeldItemOffhand().isEmpty() || !PiglinTasks.func_234480_a_(piglinEntity.getHeldItemOffhand().getItem());
    }

    public static boolean func_234459_a_(EntityType entityType) {
        return entityType == EntityType.ZOMBIFIED_PIGLIN || entityType == EntityType.ZOGLIN;
    }

    private static void lambda$func_234516_g_$12(LivingEntity livingEntity, AbstractPiglinEntity abstractPiglinEntity) {
        PiglinTasks.func_234519_h_((PiglinEntity)abstractPiglinEntity, livingEntity);
    }

    private static boolean lambda$func_234516_g_$11(AbstractPiglinEntity abstractPiglinEntity) {
        return abstractPiglinEntity instanceof PiglinEntity;
    }

    private static void lambda$func_241430_f_$10(AbstractPiglinEntity abstractPiglinEntity) {
        PiglinTasks.func_241432_i_(abstractPiglinEntity).ifPresent(arg_0 -> PiglinTasks.lambda$func_241430_f_$9(abstractPiglinEntity, arg_0));
    }

    private static void lambda$func_241430_f_$9(AbstractPiglinEntity abstractPiglinEntity, PlayerEntity playerEntity) {
        PiglinTasks.func_234497_c_(abstractPiglinEntity, playerEntity);
    }

    private static void lambda$func_234487_b_$8(LivingEntity livingEntity, AbstractPiglinEntity abstractPiglinEntity) {
        if (livingEntity.getType() != EntityType.HOGLIN || abstractPiglinEntity.func_234422_eK_() && ((HoglinEntity)livingEntity).func_234365_eM_()) {
            PiglinTasks.func_234513_f_(abstractPiglinEntity, livingEntity);
        }
    }

    private static boolean lambda$func_234508_e_$7(AbstractPiglinEntity abstractPiglinEntity) {
        return abstractPiglinEntity.getBrain().hasMemory(MemoryModuleType.HUNTED_RECENTLY);
    }

    private static SoundEvent lambda$func_241429_d_$6(PiglinEntity piglinEntity, Activity activity) {
        return PiglinTasks.func_241422_a_(piglinEntity, activity);
    }

    private static void lambda$func_234468_a_$5(LivingEntity livingEntity, Brain brain, LivingEntity livingEntity2) {
        if (livingEntity2.getType() != livingEntity.getType()) {
            brain.removeMemory(MemoryModuleType.AVOID_TARGET);
        }
    }

    private static void lambda$func_234478_a_$4(PlayerEntity playerEntity, PiglinEntity piglinEntity) {
        if (piglinEntity.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            PiglinTasks.func_241431_f_(piglinEntity, playerEntity);
        } else {
            PiglinTasks.func_234497_c_(piglinEntity, playerEntity);
        }
    }

    private static boolean lambda$func_234478_a_$3(boolean bl, PlayerEntity playerEntity, PiglinEntity piglinEntity) {
        return !bl || BrainUtil.isMobVisible(piglinEntity, playerEntity);
    }

    private static boolean lambda$func_234504_d_$2(LivingEntity livingEntity, LivingEntity livingEntity2) {
        return livingEntity2 == livingEntity;
    }

    private static boolean lambda$func_234495_c_$1(PiglinEntity piglinEntity) {
        return !piglinEntity.func_234425_eN_();
    }

    private static boolean lambda$func_234488_b_$0(PiglinEntity piglinEntity, LivingEntity livingEntity) {
        return !PiglinTasks.func_234504_d_(piglinEntity, livingEntity);
    }
}

