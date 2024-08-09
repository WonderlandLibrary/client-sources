package im.expensive.utils.player;

import im.expensive.Expensive;
import im.expensive.functions.api.FunctionRegistry;
import im.expensive.functions.impl.combat.NoEntityTrace;
import im.expensive.utils.client.IMinecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

public class MouseUtil implements IMinecraft {
    public static Entity getMouseOver(Entity target, float yaw, float pitch, double distance) {
        Entity pointedEntity;
        RayTraceResult objectMouseOver;
        Entity entity = mc.getRenderViewEntity();
        if (entity != null && mc.world != null) {
            objectMouseOver = null;
            Vector3d vec3d = entity.getEyePosition(1);
            boolean flag = false;
            if (distance > 3) {
                flag = true;
            }
            Vector3d vec3d1 = getVectorForRotation(pitch, yaw);
            Vector3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
            pointedEntity = null;
            Vector3d vec3d3 = null;
            double d2 = distance;

            AxisAlignedBB axisalignedbb = target.getBoundingBox().grow(target.getCollisionBorderSize());
            Optional<Vector3d> optional = axisalignedbb.rayTrace(vec3d, vec3d2);

            if (axisalignedbb.contains(vec3d)) {
                if (d2 >= 0.0D) {
                    pointedEntity = target;
                    vec3d3 = optional.orElse(vec3d);
                    d2 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vector3d vector3d1 = optional.get();

                double d3 = vec3d.distanceTo(vector3d1);

                if (d3 < d2 || d2 == 0.0D) {
                    boolean flag1 = false;

                    if (!flag1 && target.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
                        if (d2 == 0.0D) {
                            pointedEntity = target;
                            vec3d3 = vector3d1;
                        }
                    } else {
                        pointedEntity = target;
                        vec3d3 = vector3d1;
                        d2 = d3;
                    }
                }
            }
            if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > distance) {
                pointedEntity = null;
                objectMouseOver = BlockRayTraceResult.createMiss(vec3d3,
                        Direction.getFacingFromVector(vec3d1.x, vec3d1.y, vec3d1.z),
                        new BlockPos(vec3d3));
            }
            if (pointedEntity != null && (d2 < distance)) {
                objectMouseOver = new EntityRayTraceResult(pointedEntity, vec3d3);
            }

            if (objectMouseOver == null) {
                return null;
            }
            if (objectMouseOver instanceof EntityRayTraceResult entityRayTraceResult) {
                return entityRayTraceResult.getEntity();
            }
        }
        return null;
    }


    public static RayTraceResult rayTrace(double rayTraceDistance,
                                          float yaw,
                                          float pitch,
                                          Entity entity) {
        Vector3d startVec = mc.player.getEyePosition(1.0F);
        Vector3d directionVec = getVectorForRotation(pitch, yaw);
        Vector3d endVec = startVec.add(
                directionVec.x * rayTraceDistance,
                directionVec.y * rayTraceDistance,
                directionVec.z * rayTraceDistance
        );

        return mc.world.rayTraceBlocks(new RayTraceContext(
                startVec,
                endVec,
                RayTraceContext.BlockMode.OUTLINE,
                RayTraceContext.FluidMode.NONE,
                entity)
        );
    }

    public static RayTraceResult rayTraceResult(double rayTraceDistance,
                                                float yaw,
                                                float pitch,
                                                Entity entity) {

        RayTraceResult object = null;

        if (entity != null && mc.world != null) {
            float partialTicks = mc.getRenderPartialTicks();
            double distance = rayTraceDistance;
            object = rayTrace(rayTraceDistance, yaw, pitch, entity);
            Vector3d vector3d = entity.getEyePosition(partialTicks);
            boolean flag = false;
            double d1 = distance;

            if (mc.playerController.extendedReach()) {
                d1 = 6.0D;
                distance = d1;
            } else {
                if (distance > 3.0D) {
                    flag = true;
                }

                distance = distance;
            }

            d1 = d1 * d1;

            if (object != null) {
                d1 = object.getHitVec().squareDistanceTo(vector3d);
            }

            Vector3d vector3d1 = getVectorForRotation(pitch, yaw);
            Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
            float f = 1.0F;
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vector3d1.scale(distance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2, axisalignedbb, (p_lambda$getMouseOver$0_0_) ->
            {
                return !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith();
            }, d1);

            if (entityraytraceresult != null) {
                Entity entity1 = entityraytraceresult.getEntity();
                Vector3d vector3d3 = entityraytraceresult.getHitVec();
                double d2 = vector3d.squareDistanceTo(vector3d3);

                if (flag && d2 > 9.0D) {
                    object = BlockRayTraceResult.createMiss(vector3d3, Direction.getFacingFromVector(vector3d1.x, vector3d1.y, vector3d1.z), new BlockPos(vector3d3));
                } else if (d2 < d1 || object == null) {
                    object = entityraytraceresult;
                }
            }
        }
        return object;
    }


    public static Vector3d getVectorForRotation(float pitch, float yaw) {
        float yawRadians = -yaw * ((float) Math.PI / 180) - (float) Math.PI;
        float pitchRadians = -pitch * ((float) Math.PI / 180);

        float cosYaw = MathHelper.cos(yawRadians);
        float sinYaw = MathHelper.sin(yawRadians);
        float cosPitch = -MathHelper.cos(pitchRadians);
        float sinPitch = MathHelper.sin(pitchRadians);

        return new Vector3d(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
    }


}