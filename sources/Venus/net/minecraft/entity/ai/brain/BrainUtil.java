/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.brain;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

public class BrainUtil {
    public static void lookApproachEachOther(LivingEntity livingEntity, LivingEntity livingEntity2, float f) {
        BrainUtil.lookAtEachOther(livingEntity, livingEntity2);
        BrainUtil.approachEachOther(livingEntity, livingEntity2, f);
    }

    public static boolean canSee(Brain<?> brain, LivingEntity livingEntity) {
        return brain.getMemory(MemoryModuleType.VISIBLE_MOBS).filter(arg_0 -> BrainUtil.lambda$canSee$0(livingEntity, arg_0)).isPresent();
    }

    public static boolean isCorrectVisibleType(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryModuleType, EntityType<?> entityType) {
        return BrainUtil.canSeeEntity(brain, memoryModuleType, arg_0 -> BrainUtil.lambda$isCorrectVisibleType$1(entityType, arg_0));
    }

    private static boolean canSeeEntity(Brain<?> brain, MemoryModuleType<? extends LivingEntity> memoryModuleType, Predicate<LivingEntity> predicate) {
        return brain.getMemory(memoryModuleType).filter(predicate).filter(LivingEntity::isAlive).filter(arg_0 -> BrainUtil.lambda$canSeeEntity$2(brain, arg_0)).isPresent();
    }

    private static void lookAtEachOther(LivingEntity livingEntity, LivingEntity livingEntity2) {
        BrainUtil.lookAt(livingEntity, livingEntity2);
        BrainUtil.lookAt(livingEntity2, livingEntity);
    }

    public static void lookAt(LivingEntity livingEntity, LivingEntity livingEntity2) {
        livingEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(livingEntity2, true));
    }

    private static void approachEachOther(LivingEntity livingEntity, LivingEntity livingEntity2, float f) {
        int n = 2;
        BrainUtil.setTargetEntity(livingEntity, livingEntity2, f, 2);
        BrainUtil.setTargetEntity(livingEntity2, livingEntity, f, 2);
    }

    public static void setTargetEntity(LivingEntity livingEntity, Entity entity2, float f, int n) {
        WalkTarget walkTarget = new WalkTarget(new EntityPosWrapper(entity2, false), f, n);
        livingEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityPosWrapper(entity2, true));
        livingEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    public static void setTargetPosition(LivingEntity livingEntity, BlockPos blockPos, float f, int n) {
        WalkTarget walkTarget = new WalkTarget(new BlockPosWrapper(blockPos), f, n);
        livingEntity.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosWrapper(blockPos));
        livingEntity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, walkTarget);
    }

    public static void spawnItemNearEntity(LivingEntity livingEntity, ItemStack itemStack, Vector3d vector3d) {
        double d = livingEntity.getPosYEye() - (double)0.3f;
        ItemEntity itemEntity = new ItemEntity(livingEntity.world, livingEntity.getPosX(), d, livingEntity.getPosZ(), itemStack);
        float f = 0.3f;
        Vector3d vector3d2 = vector3d.subtract(livingEntity.getPositionVec());
        vector3d2 = vector3d2.normalize().scale(0.3f);
        itemEntity.setMotion(vector3d2);
        itemEntity.setDefaultPickupDelay();
        livingEntity.world.addEntity(itemEntity);
    }

    public static SectionPos getClosestVillageSection(ServerWorld serverWorld, SectionPos sectionPos, int n) {
        int n2 = serverWorld.sectionsToVillage(sectionPos);
        return SectionPos.getAllInBox(sectionPos, n).filter(arg_0 -> BrainUtil.lambda$getClosestVillageSection$3(serverWorld, n2, arg_0)).min(Comparator.comparingInt(serverWorld::sectionsToVillage)).orElse(sectionPos);
    }

    public static boolean canFireAtTarget(MobEntity mobEntity, LivingEntity livingEntity, int n) {
        Item item = mobEntity.getHeldItemMainhand().getItem();
        if (item instanceof ShootableItem && mobEntity.func_230280_a_((ShootableItem)item)) {
            int n2 = ((ShootableItem)item).func_230305_d_() - n;
            return mobEntity.isEntityInRange(livingEntity, n2);
        }
        return BrainUtil.canAttackTarget(mobEntity, livingEntity);
    }

    public static boolean canAttackTarget(LivingEntity livingEntity, LivingEntity livingEntity2) {
        double d;
        double d2 = livingEntity.getDistanceSq(livingEntity2.getPosX(), livingEntity2.getPosY(), livingEntity2.getPosZ());
        return d2 <= (d = (double)(livingEntity.getWidth() * 2.0f * livingEntity.getWidth() * 2.0f + livingEntity2.getWidth()));
    }

    public static boolean isTargetWithinDistance(LivingEntity livingEntity, LivingEntity livingEntity2, double d) {
        Optional<LivingEntity> optional = livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        if (!optional.isPresent()) {
            return true;
        }
        double d2 = livingEntity.getDistanceSq(optional.get().getPositionVec());
        double d3 = livingEntity.getDistanceSq(livingEntity2.getPositionVec());
        return d3 > d2 + d * d;
    }

    public static boolean isMobVisible(LivingEntity livingEntity, LivingEntity livingEntity2) {
        Brain<List<LivingEntity>> brain = livingEntity.getBrain();
        return !brain.hasMemory(MemoryModuleType.VISIBLE_MOBS) ? false : brain.getMemory(MemoryModuleType.VISIBLE_MOBS).get().contains(livingEntity2);
    }

    public static LivingEntity getNearestEntity(LivingEntity livingEntity, Optional<LivingEntity> optional, LivingEntity livingEntity2) {
        return !optional.isPresent() ? livingEntity2 : BrainUtil.getNearestEntity(livingEntity, optional.get(), livingEntity2);
    }

    public static LivingEntity getNearestEntity(LivingEntity livingEntity, LivingEntity livingEntity2, LivingEntity livingEntity3) {
        Vector3d vector3d = livingEntity2.getPositionVec();
        Vector3d vector3d2 = livingEntity3.getPositionVec();
        return livingEntity.getDistanceSq(vector3d) < livingEntity.getDistanceSq(vector3d2) ? livingEntity2 : livingEntity3;
    }

    public static Optional<LivingEntity> getTargetFromMemory(LivingEntity livingEntity, MemoryModuleType<UUID> memoryModuleType) {
        Optional<UUID> optional = livingEntity.getBrain().getMemory(memoryModuleType);
        return optional.map(arg_0 -> BrainUtil.lambda$getTargetFromMemory$4(livingEntity, arg_0));
    }

    public static Stream<VillagerEntity> getNearbyVillagers(VillagerEntity villagerEntity, Predicate<VillagerEntity> predicate) {
        return villagerEntity.getBrain().getMemory(MemoryModuleType.MOBS).map(arg_0 -> BrainUtil.lambda$getNearbyVillagers$7(villagerEntity, predicate, arg_0)).orElseGet(Stream::empty);
    }

    private static Stream lambda$getNearbyVillagers$7(VillagerEntity villagerEntity, Predicate predicate, List list) {
        return list.stream().filter(arg_0 -> BrainUtil.lambda$getNearbyVillagers$5(villagerEntity, arg_0)).map(BrainUtil::lambda$getNearbyVillagers$6).filter(LivingEntity::isAlive).filter(predicate);
    }

    private static VillagerEntity lambda$getNearbyVillagers$6(LivingEntity livingEntity) {
        return (VillagerEntity)livingEntity;
    }

    private static boolean lambda$getNearbyVillagers$5(VillagerEntity villagerEntity, LivingEntity livingEntity) {
        return livingEntity instanceof VillagerEntity && livingEntity != villagerEntity;
    }

    private static LivingEntity lambda$getTargetFromMemory$4(LivingEntity livingEntity, UUID uUID) {
        return (LivingEntity)((ServerWorld)livingEntity.world).getEntityByUuid(uUID);
    }

    private static boolean lambda$getClosestVillageSection$3(ServerWorld serverWorld, int n, SectionPos sectionPos) {
        return serverWorld.sectionsToVillage(sectionPos) < n;
    }

    private static boolean lambda$canSeeEntity$2(Brain brain, LivingEntity livingEntity) {
        return BrainUtil.canSee(brain, livingEntity);
    }

    private static boolean lambda$isCorrectVisibleType$1(EntityType entityType, LivingEntity livingEntity) {
        return livingEntity.getType() == entityType;
    }

    private static boolean lambda$canSee$0(LivingEntity livingEntity, List list) {
        return list.contains(livingEntity);
    }
}

