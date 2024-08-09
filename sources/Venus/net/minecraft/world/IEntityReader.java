/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public interface IEntityReader {
    public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity var1, AxisAlignedBB var2, @Nullable Predicate<? super Entity> var3);

    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> var1, AxisAlignedBB var2, @Nullable Predicate<? super T> var3);

    default public <T extends Entity> List<T> getLoadedEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB, @Nullable Predicate<? super T> predicate) {
        return this.getEntitiesWithinAABB(clazz, axisAlignedBB, predicate);
    }

    public List<? extends PlayerEntity> getPlayers();

    default public List<Entity> getEntitiesWithinAABBExcludingEntity(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesInAABBexcluding(entity2, axisAlignedBB, EntityPredicates.NOT_SPECTATING);
    }

    default public boolean checkNoEntityCollision(@Nullable Entity entity2, VoxelShape voxelShape) {
        if (voxelShape.isEmpty()) {
            return false;
        }
        for (Entity entity3 : this.getEntitiesWithinAABBExcludingEntity(entity2, voxelShape.getBoundingBox())) {
            if (entity3.removed || !entity3.preventEntitySpawning || entity2 != null && entity3.isRidingSameEntity(entity2) || !VoxelShapes.compare(voxelShape, VoxelShapes.create(entity3.getBoundingBox()), IBooleanFunction.AND)) continue;
            return true;
        }
        return false;
    }

    default public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesWithinAABB(clazz, axisAlignedBB, EntityPredicates.NOT_SPECTATING);
    }

    default public <T extends Entity> List<T> getLoadedEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB axisAlignedBB) {
        return this.getLoadedEntitiesWithinAABB(clazz, axisAlignedBB, EntityPredicates.NOT_SPECTATING);
    }

    default public Stream<VoxelShape> func_230318_c_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        if (axisAlignedBB.getAverageEdgeLength() < 1.0E-7) {
            return Stream.empty();
        }
        AxisAlignedBB axisAlignedBB2 = axisAlignedBB.grow(1.0E-7);
        return this.getEntitiesInAABBexcluding(entity2, axisAlignedBB2, predicate.and(arg_0 -> IEntityReader.lambda$func_230318_c_$0(axisAlignedBB2, entity2, arg_0))).stream().map(Entity::getBoundingBox).map(VoxelShapes::create);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(double d, double d2, double d3, double d4, @Nullable Predicate<Entity> predicate) {
        double d5 = -1.0;
        PlayerEntity playerEntity = null;
        for (PlayerEntity playerEntity2 : this.getPlayers()) {
            if (predicate != null && !predicate.test(playerEntity2)) continue;
            double d6 = playerEntity2.getDistanceSq(d, d2, d3);
            if (!(d4 < 0.0) && !(d6 < d4 * d4) || d5 != -1.0 && !(d6 < d5)) continue;
            d5 = d6;
            playerEntity = playerEntity2;
        }
        return playerEntity;
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(Entity entity2, double d) {
        return this.getClosestPlayer(entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), d, false);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(double d, double d2, double d3, double d4, boolean bl) {
        Predicate<Entity> predicate = bl ? EntityPredicates.CAN_AI_TARGET : EntityPredicates.NOT_SPECTATING;
        return this.getClosestPlayer(d, d2, d3, d4, predicate);
    }

    default public boolean isPlayerWithin(double d, double d2, double d3, double d4) {
        for (PlayerEntity playerEntity : this.getPlayers()) {
            if (!EntityPredicates.NOT_SPECTATING.test(playerEntity) || !EntityPredicates.IS_LIVING_ALIVE.test(playerEntity)) continue;
            double d5 = playerEntity.getDistanceSq(d, d2, d3);
            if (!(d4 < 0.0) && !(d5 < d4 * d4)) continue;
            return false;
        }
        return true;
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(EntityPredicate entityPredicate, LivingEntity livingEntity) {
        return this.getClosestEntity(this.getPlayers(), entityPredicate, livingEntity, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ());
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(EntityPredicate entityPredicate, LivingEntity livingEntity, double d, double d2, double d3) {
        return this.getClosestEntity(this.getPlayers(), entityPredicate, livingEntity, d, d2, d3);
    }

    @Nullable
    default public PlayerEntity getClosestPlayer(EntityPredicate entityPredicate, double d, double d2, double d3) {
        return this.getClosestEntity(this.getPlayers(), entityPredicate, null, d, d2, d3);
    }

    @Nullable
    default public <T extends LivingEntity> T getClosestEntityWithinAABB(Class<? extends T> clazz, EntityPredicate entityPredicate, @Nullable LivingEntity livingEntity, double d, double d2, double d3, AxisAlignedBB axisAlignedBB) {
        return this.getClosestEntity(this.getEntitiesWithinAABB(clazz, axisAlignedBB, null), entityPredicate, livingEntity, d, d2, d3);
    }

    @Nullable
    default public <T extends LivingEntity> T func_225318_b(Class<? extends T> clazz, EntityPredicate entityPredicate, @Nullable LivingEntity livingEntity, double d, double d2, double d3, AxisAlignedBB axisAlignedBB) {
        return this.getClosestEntity(this.getLoadedEntitiesWithinAABB(clazz, axisAlignedBB, null), entityPredicate, livingEntity, d, d2, d3);
    }

    @Nullable
    default public <T extends LivingEntity> T getClosestEntity(List<? extends T> list, EntityPredicate entityPredicate, @Nullable LivingEntity livingEntity, double d, double d2, double d3) {
        double d4 = -1.0;
        LivingEntity livingEntity2 = null;
        for (LivingEntity livingEntity3 : list) {
            if (!entityPredicate.canTarget(livingEntity, livingEntity3)) continue;
            double d5 = livingEntity3.getDistanceSq(d, d2, d3);
            if (d4 != -1.0 && !(d5 < d4)) continue;
            d4 = d5;
            livingEntity2 = livingEntity3;
        }
        return (T)livingEntity2;
    }

    default public List<PlayerEntity> getTargettablePlayersWithinAABB(EntityPredicate entityPredicate, LivingEntity livingEntity, AxisAlignedBB axisAlignedBB) {
        ArrayList<PlayerEntity> arrayList = Lists.newArrayList();
        for (PlayerEntity playerEntity : this.getPlayers()) {
            if (!axisAlignedBB.contains(playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ()) || !entityPredicate.canTarget(livingEntity, playerEntity)) continue;
            arrayList.add(playerEntity);
        }
        return arrayList;
    }

    default public <T extends LivingEntity> List<T> getTargettableEntitiesWithinAABB(Class<? extends T> clazz, EntityPredicate entityPredicate, LivingEntity livingEntity, AxisAlignedBB axisAlignedBB) {
        List<T> list = this.getEntitiesWithinAABB(clazz, axisAlignedBB, null);
        ArrayList<LivingEntity> arrayList = Lists.newArrayList();
        for (LivingEntity livingEntity2 : list) {
            if (!entityPredicate.canTarget(livingEntity, livingEntity2)) continue;
            arrayList.add(livingEntity2);
        }
        return arrayList;
    }

    @Nullable
    default public PlayerEntity getPlayerByUuid(UUID uUID) {
        for (int i = 0; i < this.getPlayers().size(); ++i) {
            PlayerEntity playerEntity = this.getPlayers().get(i);
            if (!uUID.equals(playerEntity.getUniqueID())) continue;
            return playerEntity;
        }
        return null;
    }

    private static boolean lambda$func_230318_c_$0(AxisAlignedBB axisAlignedBB, Entity entity2, Entity entity3) {
        return !entity3.getBoundingBox().intersects(axisAlignedBB) || !(entity2 == null ? entity3.func_241845_aY() : entity2.canCollide(entity3));
    }
}

