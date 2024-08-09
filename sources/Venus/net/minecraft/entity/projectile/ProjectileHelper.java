/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public final class ProjectileHelper {
    public static RayTraceResult func_234618_a_(Entity entity2, Predicate<Entity> predicate) {
        EntityRayTraceResult entityRayTraceResult;
        Vector3d vector3d;
        Vector3d vector3d2 = entity2.getMotion();
        World world = entity2.world;
        Vector3d vector3d3 = entity2.getPositionVec();
        RayTraceResult rayTraceResult = world.rayTraceBlocks(new RayTraceContext(vector3d3, vector3d = vector3d3.add(vector3d2), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity2));
        if (((RayTraceResult)rayTraceResult).getType() != RayTraceResult.Type.MISS) {
            vector3d = rayTraceResult.getHitVec();
        }
        if ((entityRayTraceResult = ProjectileHelper.rayTraceEntities(world, entity2, vector3d3, vector3d, entity2.getBoundingBox().expand(entity2.getMotion()).grow(1.0), predicate)) != null) {
            rayTraceResult = entityRayTraceResult;
        }
        return rayTraceResult;
    }

    @Nullable
    public static EntityRayTraceResult rayTraceEntities(Entity entity2, Vector3d vector3d, Vector3d vector3d2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate, double d) {
        World world = entity2.world;
        double d2 = d;
        Entity entity3 = null;
        Vector3d vector3d3 = null;
        for (Entity entity4 : world.getEntitiesInAABBexcluding(entity2, axisAlignedBB, predicate)) {
            Vector3d vector3d4;
            double d3;
            AxisAlignedBB axisAlignedBB2 = entity4.getBoundingBox().grow(entity4.getCollisionBorderSize());
            Optional<Vector3d> optional = axisAlignedBB2.rayTrace(vector3d, vector3d2);
            if (axisAlignedBB2.contains(vector3d)) {
                if (!(d2 >= 0.0)) continue;
                entity3 = entity4;
                vector3d3 = optional.orElse(vector3d);
                d2 = 0.0;
                continue;
            }
            if (!optional.isPresent() || !((d3 = vector3d.squareDistanceTo(vector3d4 = optional.get())) < d2) && d2 != 0.0) continue;
            if (entity4.getLowestRidingEntity() == entity2.getLowestRidingEntity()) {
                if (d2 != 0.0) continue;
                entity3 = entity4;
                vector3d3 = vector3d4;
                continue;
            }
            entity3 = entity4;
            vector3d3 = vector3d4;
            d2 = d3;
        }
        return entity3 == null ? null : new EntityRayTraceResult(entity3, vector3d3);
    }

    @Nullable
    public static EntityRayTraceResult rayTraceEntities(World world, Entity entity2, Vector3d vector3d, Vector3d vector3d2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        double d = Double.MAX_VALUE;
        Entity entity3 = null;
        for (Entity entity4 : world.getEntitiesInAABBexcluding(entity2, axisAlignedBB, predicate)) {
            double d2;
            AxisAlignedBB axisAlignedBB2 = entity4.getBoundingBox().grow(0.3f);
            Optional<Vector3d> optional = axisAlignedBB2.rayTrace(vector3d, vector3d2);
            if (!optional.isPresent() || !((d2 = vector3d.squareDistanceTo(optional.get())) < d)) continue;
            entity3 = entity4;
            d = d2;
        }
        return entity3 == null ? null : new EntityRayTraceResult(entity3);
    }

    public static final void rotateTowardsMovement(Entity entity2, float f) {
        Vector3d vector3d = entity2.getMotion();
        if (vector3d.lengthSquared() != 0.0) {
            float f2 = MathHelper.sqrt(Entity.horizontalMag(vector3d));
            entity2.rotationYaw = (float)(MathHelper.atan2(vector3d.z, vector3d.x) * 57.2957763671875) + 90.0f;
            entity2.rotationPitch = (float)(MathHelper.atan2(f2, vector3d.y) * 57.2957763671875) - 90.0f;
            while (entity2.rotationPitch - entity2.prevRotationPitch < -180.0f) {
                entity2.prevRotationPitch -= 360.0f;
            }
            while (entity2.rotationPitch - entity2.prevRotationPitch >= 180.0f) {
                entity2.prevRotationPitch += 360.0f;
            }
            while (entity2.rotationYaw - entity2.prevRotationYaw < -180.0f) {
                entity2.prevRotationYaw -= 360.0f;
            }
            while (entity2.rotationYaw - entity2.prevRotationYaw >= 180.0f) {
                entity2.prevRotationYaw += 360.0f;
            }
            entity2.rotationPitch = MathHelper.lerp(f, entity2.prevRotationPitch, entity2.rotationPitch);
            entity2.rotationYaw = MathHelper.lerp(f, entity2.prevRotationYaw, entity2.rotationYaw);
        }
    }

    public static Hand getHandWith(LivingEntity livingEntity, Item item) {
        return livingEntity.getHeldItemMainhand().getItem() == item ? Hand.MAIN_HAND : Hand.OFF_HAND;
    }

    public static AbstractArrowEntity fireArrow(LivingEntity livingEntity, ItemStack itemStack, float f) {
        ArrowItem arrowItem = (ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(livingEntity.world, itemStack, livingEntity);
        abstractArrowEntity.setEnchantmentEffectsFromEntity(livingEntity, f);
        if (itemStack.getItem() == Items.TIPPED_ARROW && abstractArrowEntity instanceof ArrowEntity) {
            ((ArrowEntity)abstractArrowEntity).setPotionEffect(itemStack);
        }
        return abstractArrowEntity;
    }
}

