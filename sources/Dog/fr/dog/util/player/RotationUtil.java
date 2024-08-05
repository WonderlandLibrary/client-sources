package fr.dog.util.player;

import com.google.common.base.Predicates;
import fr.dog.util.InstanceAccess;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import org.lwjglx.util.vector.Vector2f;

import java.util.List;

@UtilityClass
public class RotationUtil implements InstanceAccess {
    private static final double RAD_TO_DEG = 180 / Math.PI;
    private static final double RANDOM_SENSITIVITY_VARIANCE = 1 + Math.random() / 10000000;
    private static final double SENSITIVITY_MULTIPLIER = 0.6f;
    private static final double SENSITIVITY_BASE = 0.2f;
    private static final double SENSITIVITY_EXPONENT = 8.0f * 0.15;
    private static final float EXTENDED_REACH_DISTANCE = 3.0F;
    private static final float COLLISION_BORDER_SIZE = 1.0F;

    public float[] applySensitivity(final float[] rotation, final float[] previousRotation) {
        float sensitivity = (float) (mc.gameSettings.mouseSensitivity * RANDOM_SENSITIVITY_VARIANCE * SENSITIVITY_MULTIPLIER + SENSITIVITY_BASE);
        double multiplier = sensitivity * sensitivity * sensitivity * SENSITIVITY_EXPONENT;

        float yaw = (float) (previousRotation[0] + Math.round((rotation[0] - previousRotation[0]) / multiplier) * multiplier),
                pitch = (float) (previousRotation[1] + Math.round((rotation[1] - previousRotation[1]) / multiplier) * multiplier);

        return new float[] { yaw, MathHelper.clamp_float(pitch, -90, 90) };
    }

    public float[] reset(final float[] rotation) {
        if (rotation == null) return null;

        float yaw = rotation[0] + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - rotation[0]),
                pitch = mc.thePlayer.rotationPitch;

        return new float[] { yaw, pitch };
    }

    public float[] getRotationsVector(Vec3 start, Vec3 end) {
        Vec3 delta = end.subtract(start);
        double distance = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
        float yaw = (float) Math.toDegrees(Math.atan2(delta.zCoord, delta.xCoord)) - 90.0F,
                pitch = (float) -Math.toDegrees(Math.atan2(delta.yCoord, distance));

        return new float[]{yaw, pitch};
    }

    public float[] smooth(final float[] lastRotation, final float[] targetRotation, final double speed) {
        double deltaYaw = MathHelper.wrapAngleTo180_double(targetRotation[0] - lastRotation[0]),
                deltaPitch = targetRotation[1] - lastRotation[1];
        double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);

        double maxYaw = speed * Math.abs(deltaYaw / distance),
                maxPitch = speed * Math.abs(deltaPitch / distance);

        float yaw = lastRotation[0] + (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw),
                pitch = lastRotation[1] + (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

        return applySensitivity(new float[] { yaw, pitch }, lastRotation);
    }

    public static Vec3 getBestLookVector(Vec3 look, AxisAlignedBB aabb, double expand) {
        aabb = aabb.expand(expand, expand, expand);
        return new Vec3(
                Math.max(aabb.minX, Math.min(aabb.maxX, look.xCoord)),
                Math.max(aabb.minY, Math.min(aabb.maxY, look.yCoord)),
                Math.max(aabb.minZ, Math.min(aabb.maxZ, look.zCoord))
        );
    }

    public static double getDistanceToEntity(Entity entity) {
        if (mc.thePlayer == null || entity == null) return 0;
        return mc.thePlayer.getPositionEyes(1.0f).distanceTo(getBestLookVector(mc.thePlayer.getPositionEyes(1F), entity.getEntityBoundingBox(), 0.0));
    }

    public static float[] getRotation(Entity entity) {
        final double differenceX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double differenceY = entity.posY + entity.height - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.height) - 0.092312;
        final double differenceZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double distance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        final float rotationYaw = (float) Math.toDegrees(Math.atan2(differenceZ, differenceX)) - 90.0f;
        final float rotationPitch = (float) Math.toDegrees(Math.atan2(differenceY, Math.hypot(differenceX, differenceZ)));
        final float finishedYaw = Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - Minecraft.getMinecraft().thePlayer.rotationYaw);
        final float finishedPitch = -MathHelper.clamp_float(Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - Minecraft.getMinecraft().thePlayer.rotationPitch), -90, 90);
        return new float[]{finishedYaw, finishedPitch};
    }

    public static float normalizeAngleF(final float angle){
        float normalizedAngle = angle;

        while (normalizedAngle <= -180) {
            normalizedAngle += 360;
        }

        while (normalizedAngle > 180) {
            normalizedAngle -= 360;
        }

        return normalizedAngle;
    }


    public static MovingObjectPosition raycast(Vector2f vec, double reach) {
        Entity entity = mc.getRenderViewEntity();
        Entity pointedEntity;

        MovingObjectPosition objectMouseOver;

        if (entity != null && mc.theWorld != null) {
            mc.mcProfiler.startSection("pick");
            pointedEntity = null;
            double reachDistance = reach;
            objectMouseOver = mc.thePlayer.rayTrace(reachDistance, vec.x);
            double closestDistance = reachDistance;
            Vec3 entityPosition = entity.getPositionEyes(1);
            boolean isExtendedReach = false;
            int maxIterations = 3;

            if (mc.playerController.extendedReach()) {
                reachDistance = EXTENDED_REACH_DISTANCE;
                closestDistance = EXTENDED_REACH_DISTANCE;
            } else if (reachDistance > EXTENDED_REACH_DISTANCE) {
                isExtendedReach = true;
            }

            if (objectMouseOver != null) {
                closestDistance = objectMouseOver.hitVec.distanceTo(entityPosition);
            }

            Vec3 entityLookVec = entity.getLook(1);
            Vec3 extendedLookVec = entityPosition.addVector(entityLookVec.xCoord * reachDistance, entityLookVec.yCoord * reachDistance, entityLookVec.zCoord * reachDistance);
            Vec3 closestEntityVec = null;
            List<Entity> nearbyEntities = getNearbyEntities(entity, entityLookVec, reachDistance);

            for (Entity nearbyEntity : nearbyEntities) {
                AxisAlignedBB entityBoundingBox = nearbyEntity.getEntityBoundingBox().expand(COLLISION_BORDER_SIZE, COLLISION_BORDER_SIZE, COLLISION_BORDER_SIZE);
                MovingObjectPosition interceptPosition = entityBoundingBox.calculateIntercept(entityPosition, extendedLookVec);

                if (entityBoundingBox.isVecInside(entityPosition)) {
                    if (closestDistance >= 0.0D) {
                        pointedEntity = nearbyEntity;
                        closestEntityVec = interceptPosition == null ? entityPosition : interceptPosition.hitVec;
                        closestDistance = 0.0D;
                    }
                } else if (interceptPosition != null) {
                    double interceptDistance = entityPosition.distanceTo(interceptPosition.hitVec);

                    if (interceptDistance < closestDistance || closestDistance == 0.0D) {
                        boolean isEntityRiding = false;
                        if (!isEntityRiding && nearbyEntity == entity.ridingEntity) {
                            if (closestDistance == 0.0D) {
                                pointedEntity = nearbyEntity;
                                closestEntityVec = interceptPosition.hitVec;
                            }
                        } else {
                            pointedEntity = nearbyEntity;
                            closestEntityVec = interceptPosition.hitVec;
                            closestDistance = interceptDistance;
                        }
                    }
                }
            }

            if (pointedEntity != null && isExtendedReach && entityPosition.distanceTo(closestEntityVec) > EXTENDED_REACH_DISTANCE) {
                pointedEntity = null;
                objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, closestEntityVec, (EnumFacing) null, new BlockPos(closestEntityVec));
            }

            if (pointedEntity != null && (closestDistance < reachDistance || objectMouseOver == null)) {
                objectMouseOver = new MovingObjectPosition(pointedEntity, closestEntityVec);
            }

            mc.mcProfiler.endSection();
            return objectMouseOver;
        }
        return null;
    }

    private static float[] calculateRotation(Vec3 delta) {
        double distance = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
        float yaw = (float) Math.toDegrees(Math.atan2(delta.zCoord, delta.xCoord)) - 90.0F,
                pitch = (float) -Math.toDegrees(Math.atan2(delta.yCoord, distance));

        return new float[]{yaw, pitch};
    }
    private static List<Entity> getNearbyEntities(Entity entity, Vec3 entityLookVec, double reachDistance) {
        return mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(entityLookVec.xCoord * reachDistance, entityLookVec.yCoord * reachDistance, entityLookVec.zCoord * reachDistance).expand(COLLISION_BORDER_SIZE, COLLISION_BORDER_SIZE, COLLISION_BORDER_SIZE), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
    }
}