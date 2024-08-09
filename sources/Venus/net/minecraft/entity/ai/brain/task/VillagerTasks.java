/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.AssignProfessionTask;
import net.minecraft.entity.ai.brain.task.BeginRaidTask;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.entity.ai.brain.task.CelebrateRaidVictoryTask;
import net.minecraft.entity.ai.brain.task.ChangeJobTask;
import net.minecraft.entity.ai.brain.task.ClearHurtTask;
import net.minecraft.entity.ai.brain.task.CongregateTask;
import net.minecraft.entity.ai.brain.task.CreateBabyVillagerTask;
import net.minecraft.entity.ai.brain.task.DummyTask;
import net.minecraft.entity.ai.brain.task.ExpireHidingTask;
import net.minecraft.entity.ai.brain.task.ExpirePOITask;
import net.minecraft.entity.ai.brain.task.FarmTask;
import net.minecraft.entity.ai.brain.task.FarmerWorkTask;
import net.minecraft.entity.ai.brain.task.FindHidingPlaceDuringRaidTask;
import net.minecraft.entity.ai.brain.task.FindHidingPlaceTask;
import net.minecraft.entity.ai.brain.task.FindInteractionAndLookTargetTask;
import net.minecraft.entity.ai.brain.task.FindJobTask;
import net.minecraft.entity.ai.brain.task.FindPotentialJobTask;
import net.minecraft.entity.ai.brain.task.FindWalkTargetAfterRaidVictoryTask;
import net.minecraft.entity.ai.brain.task.FindWalkTargetTask;
import net.minecraft.entity.ai.brain.task.FirstShuffledTask;
import net.minecraft.entity.ai.brain.task.ForgetRaidTask;
import net.minecraft.entity.ai.brain.task.GatherPOITask;
import net.minecraft.entity.ai.brain.task.GiveHeroGiftsTask;
import net.minecraft.entity.ai.brain.task.GoOutsideAfterRaidTask;
import net.minecraft.entity.ai.brain.task.HideFromRaidOnBellRingTask;
import net.minecraft.entity.ai.brain.task.InteractWithDoorTask;
import net.minecraft.entity.ai.brain.task.InteractWithEntityTask;
import net.minecraft.entity.ai.brain.task.JumpOnBedTask;
import net.minecraft.entity.ai.brain.task.LookAtEntityTask;
import net.minecraft.entity.ai.brain.task.LookTask;
import net.minecraft.entity.ai.brain.task.MultiTask;
import net.minecraft.entity.ai.brain.task.PanicTask;
import net.minecraft.entity.ai.brain.task.PickupWantedItemTask;
import net.minecraft.entity.ai.brain.task.RingBellTask;
import net.minecraft.entity.ai.brain.task.RunAwayTask;
import net.minecraft.entity.ai.brain.task.ShareItemsTask;
import net.minecraft.entity.ai.brain.task.ShowWaresTask;
import net.minecraft.entity.ai.brain.task.SleepAtHomeTask;
import net.minecraft.entity.ai.brain.task.SpawnGolemTask;
import net.minecraft.entity.ai.brain.task.StayNearPointTask;
import net.minecraft.entity.ai.brain.task.SwimTask;
import net.minecraft.entity.ai.brain.task.SwitchVillagerJobTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TradeTask;
import net.minecraft.entity.ai.brain.task.UpdateActivityTask;
import net.minecraft.entity.ai.brain.task.WakeUpTask;
import net.minecraft.entity.ai.brain.task.WalkRandomlyInsideTask;
import net.minecraft.entity.ai.brain.task.WalkToHouseTask;
import net.minecraft.entity.ai.brain.task.WalkToPOITask;
import net.minecraft.entity.ai.brain.task.WalkToTargetTask;
import net.minecraft.entity.ai.brain.task.WalkToVillagerBabiesTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsLookTargetTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsPosTask;
import net.minecraft.entity.ai.brain.task.WalkTowardsRandomSecondaryPosTask;
import net.minecraft.entity.ai.brain.task.WorkTask;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;

public class VillagerTasks {
    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> core(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(0, new SwimTask(0.8f)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new PanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new HideFromRaidOnBellRingTask()), Pair.of(0, new BeginRaidTask()), Pair.of(0, new ExpirePOITask(villagerProfession.getPointOfInterest(), MemoryModuleType.JOB_SITE)), Pair.of(0, new ExpirePOITask(villagerProfession.getPointOfInterest(), MemoryModuleType.POTENTIAL_JOB_SITE)), Pair.of(1, new WalkToTargetTask()), Pair.of(2, new SwitchVillagerJobTask(villagerProfession)), Pair.of(3, new TradeTask(f)), Pair.of(5, new PickupWantedItemTask(f, false, 4)), Pair.of(6, new GatherPOITask(villagerProfession.getPointOfInterest(), MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, true, Optional.empty())), Pair.of(7, new FindPotentialJobTask(f)), Pair.of(8, new FindJobTask(f)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false, Optional.of((byte)14))), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true, Optional.of((byte)14))), Pair.of(10, new AssignProfessionTask()), Pair.of(10, new ChangeJobTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> work(VillagerProfession villagerProfession, float f) {
        SpawnGolemTask spawnGolemTask = villagerProfession == VillagerProfession.FARMER ? new FarmerWorkTask() : new SpawnGolemTask();
        return ImmutableList.of(VillagerTasks.lookAtPlayerOrVillager(), Pair.of(5, new FirstShuffledTask(ImmutableList.of(Pair.of(spawnGolemTask, 7), Pair.of(new WorkTask(MemoryModuleType.JOB_SITE, 0.4f, 4), 2), Pair.of(new WalkTowardsPosTask(MemoryModuleType.JOB_SITE, 0.4f, 1, 10), 5), Pair.of(new WalkTowardsRandomSecondaryPosTask(MemoryModuleType.SECONDARY_JOB_SITE, f, 1, 6, MemoryModuleType.JOB_SITE), 5), Pair.of(new FarmTask(), villagerProfession == VillagerProfession.FARMER ? 2 : 5), Pair.of(new BoneMealCropsTask(), villagerProfession == VillagerProfession.FARMER ? 4 : 7)))), Pair.of(10, new ShowWaresTask(400, 1600)), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new StayNearPointTask(MemoryModuleType.JOB_SITE, f, 9, 100, 1200)), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> play(float f) {
        return ImmutableList.of(Pair.of(0, new WalkToTargetTask(80, 120)), VillagerTasks.lookAtMany(), Pair.of(5, new WalkToVillagerBabiesTask()), Pair.of(5, new FirstShuffledTask(ImmutableMap.of(MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(InteractWithEntityTask.func_220445_a(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, f, 2), 2), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, f, 2), 1), Pair.of(new FindWalkTargetTask(f), 1), Pair.of(new WalkTowardsLookTargetTask(f, 2), 1), Pair.of(new JumpOnBedTask(f), 2), Pair.of(new DummyTask(20, 40), 2)))), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> rest(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(2, new StayNearPointTask(MemoryModuleType.HOME, f, 1, 150, 1200)), Pair.of(3, new ExpirePOITask(PointOfInterestType.HOME, MemoryModuleType.HOME)), Pair.of(3, new SleepAtHomeTask()), Pair.of(5, new FirstShuffledTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(new WalkToHouseTask(f), 1), Pair.of(new WalkRandomlyInsideTask(f), 4), Pair.of(new WalkToPOITask(f, 4), 2), Pair.of(new DummyTask(20, 40), 2)))), VillagerTasks.lookAtPlayerOrVillager(), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> meet(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(2, new FirstShuffledTask(ImmutableList.of(Pair.of(new WorkTask(MemoryModuleType.MEETING_POINT, 0.4f, 40), 2), Pair.of(new CongregateTask(), 2)))), Pair.of(10, new ShowWaresTask(400, 1600)), Pair.of(10, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(2, new StayNearPointTask(MemoryModuleType.MEETING_POINT, f, 6, 100, 200)), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(3, new ExpirePOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT)), Pair.of(3, new MultiTask(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), MultiTask.Ordering.ORDERED, MultiTask.RunType.RUN_ONE, ImmutableList.of(Pair.of(new ShareItemsTask(), 1)))), VillagerTasks.lookAtMany(), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> idle(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(2, new FirstShuffledTask(ImmutableList.of(Pair.of(InteractWithEntityTask.func_220445_a(EntityType.VILLAGER, 8, MemoryModuleType.INTERACTION_TARGET, f, 2), 2), Pair.of(new InteractWithEntityTask<VillagerEntity, AgeableEntity>(EntityType.VILLAGER, 8, AgeableEntity::canBreed, AgeableEntity::canBreed, MemoryModuleType.BREED_TARGET, f, 2), 1), Pair.of(InteractWithEntityTask.func_220445_a(EntityType.CAT, 8, MemoryModuleType.INTERACTION_TARGET, f, 2), 1), Pair.of(new FindWalkTargetTask(f), 1), Pair.of(new WalkTowardsLookTargetTask(f, 2), 1), Pair.of(new JumpOnBedTask(f), 1), Pair.of(new DummyTask(30, 60), 1)))), Pair.of(3, new GiveHeroGiftsTask(100)), Pair.of(3, new FindInteractionAndLookTargetTask(EntityType.PLAYER, 4)), Pair.of(3, new ShowWaresTask(400, 1600)), Pair.of(3, new MultiTask(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.INTERACTION_TARGET), MultiTask.Ordering.ORDERED, MultiTask.RunType.RUN_ONE, ImmutableList.of(Pair.of(new ShareItemsTask(), 1)))), Pair.of(3, new MultiTask(ImmutableMap.of(), ImmutableSet.of(MemoryModuleType.BREED_TARGET), MultiTask.Ordering.ORDERED, MultiTask.RunType.RUN_ONE, ImmutableList.of(Pair.of(new CreateBabyVillagerTask(), 1)))), VillagerTasks.lookAtMany(), Pair.of(99, new UpdateActivityTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> panic(VillagerProfession villagerProfession, float f) {
        float f2 = f * 1.5f;
        return ImmutableList.of(Pair.of(0, new ClearHurtTask()), Pair.of(1, RunAwayTask.func_233965_b_(MemoryModuleType.NEAREST_HOSTILE, f2, 6, false)), Pair.of(1, RunAwayTask.func_233965_b_(MemoryModuleType.HURT_BY_ENTITY, f2, 6, false)), Pair.of(3, new FindWalkTargetTask(f2, 2, 2)), VillagerTasks.lookAtPlayerOrVillager());
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> preRaid(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(0, new RingBellTask()), Pair.of(0, new FirstShuffledTask(ImmutableList.of(Pair.of(new StayNearPointTask(MemoryModuleType.MEETING_POINT, f * 1.5f, 2, 150, 200), 6), Pair.of(new FindWalkTargetTask(f * 1.5f), 2)))), VillagerTasks.lookAtPlayerOrVillager(), Pair.of(99, new ForgetRaidTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> raid(VillagerProfession villagerProfession, float f) {
        return ImmutableList.of(Pair.of(0, new FirstShuffledTask(ImmutableList.of(Pair.of(new GoOutsideAfterRaidTask(f), 5), Pair.of(new FindWalkTargetAfterRaidVictoryTask(f * 1.1f), 2)))), Pair.of(0, new CelebrateRaidVictoryTask(600, 600)), Pair.of(2, new FindHidingPlaceDuringRaidTask(24, f * 1.4f)), VillagerTasks.lookAtPlayerOrVillager(), Pair.of(99, new ForgetRaidTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> hide(VillagerProfession villagerProfession, float f) {
        int n = 2;
        return ImmutableList.of(Pair.of(0, new ExpireHidingTask(15, 3)), Pair.of(1, new FindHidingPlaceTask(32, f * 1.25f, 2)), VillagerTasks.lookAtPlayerOrVillager());
    }

    private static Pair<Integer, Task<LivingEntity>> lookAtMany() {
        return Pair.of(5, new FirstShuffledTask(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.CAT, 8.0f), 8), Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0f), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0f), 2), Pair.of(new LookAtEntityTask(EntityClassification.CREATURE, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_CREATURE, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityClassification.WATER_AMBIENT, 8.0f), 1), Pair.of(new LookAtEntityTask(EntityClassification.MONSTER, 8.0f), 1), Pair.of(new DummyTask(30, 60), 2))));
    }

    private static Pair<Integer, Task<LivingEntity>> lookAtPlayerOrVillager() {
        return Pair.of(5, new FirstShuffledTask(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0f), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0f), 2), Pair.of(new DummyTask(30, 60), 8))));
    }
}

