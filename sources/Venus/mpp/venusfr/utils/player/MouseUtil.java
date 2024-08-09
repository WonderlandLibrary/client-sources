/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.player;

import java.util.Optional;
import java.util.function.Predicate;
import mpp.venusfr.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MouseUtil
implements IMinecraft {
    public static Entity getMouseOver(Entity entity2, float f, float f2, double d) {
        Entity entity3 = mc.getRenderViewEntity();
        if (entity3 != null && MouseUtil.mc.world != null) {
            AxisAlignedBB axisAlignedBB;
            RayTraceResult rayTraceResult = null;
            boolean bl = d > 3.0;
            Vector3d vector3d = entity3.getEyePosition(1.0f);
            Vector3d vector3d2 = MouseUtil.getVectorForRotation(f2, f);
            Vector3d vector3d3 = vector3d.add(vector3d2.x * d, vector3d2.y * d, vector3d2.z * d);
            EntityRayTraceResult entityRayTraceResult = MouseUtil.rayTraceEntities(entity3, vector3d, vector3d3, axisAlignedBB = entity2.getBoundingBox().grow(entity2.getCollisionBorderSize()), MouseUtil::lambda$getMouseOver$0, d);
            if (entityRayTraceResult != null) {
                if (bl && vector3d.distanceTo(vector3d) > d) {
                    rayTraceResult = BlockRayTraceResult.createMiss(vector3d, null, new BlockPos(vector3d));
                }
                if (d < d || rayTraceResult == null) {
                    rayTraceResult = entityRayTraceResult;
                }
            }
            if (rayTraceResult == null) {
                return null;
            }
            if (rayTraceResult instanceof EntityRayTraceResult) {
                EntityRayTraceResult entityRayTraceResult2 = rayTraceResult;
                return entityRayTraceResult2.getEntity();
            }
        }
        return null;
    }

    public static EntityRayTraceResult rayTraceEntities(Entity entity2, Vector3d vector3d, Vector3d vector3d2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate, double d) {
        World world = entity2.world;
        double d2 = d;
        Entity entity3 = null;
        Vector3d vector3d3 = null;
        for (Entity entity4 : world.getEntitiesInAABBexcluding(entity2, axisAlignedBB, predicate)) {
            AxisAlignedBB axisAlignedBB2 = entity4.getBoundingBox().grow(entity4.getCollisionBorderSize());
            Optional<Vector3d> optional = axisAlignedBB2.rayTrace(vector3d, vector3d2);
            if (axisAlignedBB2.contains(vector3d)) {
                if (!(d2 >= 0.0)) continue;
                entity3 = entity4;
                vector3d3 = vector3d;
                d2 = 0.0;
                continue;
            }
            if (!optional.isPresent()) continue;
            Vector3d vector3d4 = optional.get();
            double d3 = vector3d.distanceTo(optional.get());
            if (!(d3 < d2) && d2 != 0.0) continue;
            boolean bl = false;
            if (!bl && entity4.getLowestRidingEntity() == entity2.getLowestRidingEntity()) {
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

    public static RayTraceResult rayTrace(double d, float f, float f2, Entity entity2) {
        Vector3d vector3d = MouseUtil.mc.player.getEyePosition(1.0f);
        Vector3d vector3d2 = MouseUtil.getVectorForRotation(f2, f);
        Vector3d vector3d3 = vector3d.add(vector3d2.x * d, vector3d2.y * d, vector3d2.z * d);
        return MouseUtil.mc.world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d3, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity2));
    }

    public static RayTraceResult rayTraceResult(double d, float f, float f2, Entity entity2) {
        RayTraceResult rayTraceResult = null;
        if (entity2 != null && MouseUtil.mc.world != null) {
            float f3 = mc.getRenderPartialTicks();
            double d2 = d;
            rayTraceResult = MouseUtil.rayTrace(d, f, f2, entity2);
            Vector3d vector3d = entity2.getEyePosition(f3);
            boolean bl = false;
            double d3 = d2;
            if (MouseUtil.mc.playerController.extendedReach()) {
                d2 = d3 = 6.0;
            } else if (d2 > 3.0) {
                bl = true;
            }
            d3 *= d3;
            if (rayTraceResult != null) {
                d3 = rayTraceResult.getHitVec().squareDistanceTo(vector3d);
            }
            Vector3d vector3d2 = MouseUtil.getVectorForRotation(f2, f);
            Vector3d vector3d3 = vector3d.add(vector3d2.x * d2, vector3d2.y * d2, vector3d2.z * d2);
            float f4 = 1.0f;
            AxisAlignedBB axisAlignedBB = entity2.getBoundingBox().expand(vector3d2.scale(d2)).grow(1.0, 1.0, 1.0);
            EntityRayTraceResult entityRayTraceResult = ProjectileHelper.rayTraceEntities(entity2, vector3d, vector3d3, axisAlignedBB, MouseUtil::lambda$rayTraceResult$1, d3);
            if (entityRayTraceResult != null) {
                Entity entity3 = entityRayTraceResult.getEntity();
                Vector3d vector3d4 = entityRayTraceResult.getHitVec();
                double d4 = vector3d.squareDistanceTo(vector3d4);
                if (bl && d4 > 9.0) {
                    rayTraceResult = BlockRayTraceResult.createMiss(vector3d4, Direction.getFacingFromVector(vector3d2.x, vector3d2.y, vector3d2.z), new BlockPos(vector3d4));
                } else if (d4 < d3 || rayTraceResult == null) {
                    rayTraceResult = entityRayTraceResult;
                }
            }
        }
        return rayTraceResult;
    }

    public static boolean rayTraceWithBlock(double d, float f, float f2, Entity entity2, Entity entity3) {
        RayTraceResult rayTraceResult = null;
        if (entity2 != null && MouseUtil.mc.world != null) {
            float f3 = mc.getRenderPartialTicks();
            double d2 = d;
            rayTraceResult = MouseUtil.rayTrace(d, f, f2, entity2);
            Vector3d vector3d = entity2.getEyePosition(f3);
            boolean bl = false;
            double d3 = d2;
            if (MouseUtil.mc.playerController.extendedReach()) {
                d2 = d3 = 6.0;
            } else if (d2 > 3.0) {
                bl = true;
            }
            d3 *= d3;
            if (rayTraceResult != null) {
                d3 = rayTraceResult.getHitVec().squareDistanceTo(vector3d);
            }
            Vector3d vector3d2 = MouseUtil.getVectorForRotation(f2, f);
            Vector3d vector3d3 = vector3d.add(vector3d2.x * d2, vector3d2.y * d2, vector3d2.z * d2);
            float f4 = 1.0f;
            AxisAlignedBB axisAlignedBB = entity2.getBoundingBox().expand(vector3d2.scale(d2)).grow(1.0, 1.0, 1.0);
            EntityRayTraceResult entityRayTraceResult = ProjectileHelper.rayTraceEntities(entity2, vector3d, vector3d3, axisAlignedBB, MouseUtil::lambda$rayTraceWithBlock$2, d3);
            if (entityRayTraceResult != null) {
                Entity entity4 = entityRayTraceResult.getEntity();
                Vector3d vector3d4 = entityRayTraceResult.getHitVec();
                double d4 = vector3d.squareDistanceTo(vector3d4);
                if (bl && d4 > 9.0) {
                    rayTraceResult = BlockRayTraceResult.createMiss(vector3d4, Direction.getFacingFromVector(vector3d2.x, vector3d2.y, vector3d2.z), new BlockPos(vector3d4));
                } else if (d4 < d3 || rayTraceResult == null) {
                    rayTraceResult = entityRayTraceResult;
                }
            }
        }
        if (rayTraceResult instanceof EntityRayTraceResult) {
            return ((EntityRayTraceResult)rayTraceResult).getEntity().getEntityId() == entity3.getEntityId();
        }
        return true;
    }

    public static Vector3d getVectorForRotation(float f, float f2) {
        float f3 = -f2 * ((float)Math.PI / 180) - (float)Math.PI;
        float f4 = -f * ((float)Math.PI / 180);
        float f5 = MathHelper.cos(f3);
        float f6 = MathHelper.sin(f3);
        float f7 = -MathHelper.cos(f4);
        float f8 = MathHelper.sin(f4);
        return new Vector3d(f6 * f7, f8, f5 * f7);
    }

    private static boolean lambda$rayTraceWithBlock$2(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }

    private static boolean lambda$rayTraceResult$1(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }

    private static boolean lambda$getMouseOver$0(Entity entity2) {
        return !entity2.isSpectator() && entity2.canBeCollidedWith();
    }
}

