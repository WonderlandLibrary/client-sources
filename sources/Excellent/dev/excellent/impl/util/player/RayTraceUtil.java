package dev.excellent.impl.util.player;

import dev.excellent.api.interfaces.client.IAccess;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public class RayTraceUtil implements IAccess {

    public Entity getMouseOver(Entity target,
                               float yaw,
                               float pitch,
                               double distance) {
        RayTraceResult objectMouseOver;
        Entity entity = mc.getRenderViewEntity();

        if (entity != null && mc.world != null) {
            objectMouseOver = null;
            boolean flag = distance > 3;

            Vector3d startVec = entity.getEyePosition(mc.getRenderPartialTicks());
            Vector3d directionVec = getVectorForRotation(pitch, yaw);
            Vector3d endVec = startVec.add(
                    directionVec.x * distance,
                    directionVec.y * distance,
                    directionVec.z * distance
            );

            AxisAlignedBB axisalignedbb = target.getBoundingBox().grow(target.getCollisionBorderSize());

            EntityRayTraceResult entityraytraceresult = rayTraceEntities(entity,
                    startVec,
                    endVec,
                    axisalignedbb,
                    (entity1) ->
                            !entity1.isSpectator()
                                    && entity1.canBeCollidedWith(), distance
            );

            if (entityraytraceresult != null) {
                if (flag && startVec.distanceTo(startVec) > distance) {
                    objectMouseOver = BlockRayTraceResult.createMiss(startVec, Direction.UP, new BlockPos(startVec));
                }
                if ((distance < distance || objectMouseOver == null)) {
                    objectMouseOver = entityraytraceresult;
                }
            }
            if (objectMouseOver == null) {
                return null;
            }
            try {
                return ((EntityRayTraceResult) objectMouseOver).getEntity();
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Тоже майнкрафт метод :/
     * Выполняет трассировку луча между сущностями
     *
     * @param shooter     сущность, инициировавшая трассировку
     * @param startVec    начальная точка трассировки
     * @param endVec      конечная точка трассировки
     * @param boundingBox ограничивающий параллелепипед для поиска сущностей
     * @param filter      предикат для фильтрации сущностей
     * @param distance    максимальное расстояние трассировки
     * @return результат трассировки луча между сущностями или null, если сущностей не найдено
     */
    public EntityRayTraceResult rayTraceEntities(Entity shooter,
                                                 Vector3d startVec,
                                                 Vector3d endVec,
                                                 AxisAlignedBB boundingBox,
                                                 Predicate<Entity> filter,
                                                 double distance) {
        World world = shooter.world;
        double closestDistance = distance;
        Entity entity = null;
        Vector3d closestHitVec = null;

        for (Entity entity1 : world.getEntitiesInAABBexcluding(shooter, boundingBox, filter)) {
            AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(entity1.getCollisionBorderSize());
            Optional<Vector3d> optional = axisalignedbb.rayTrace(startVec, endVec);

            if (axisalignedbb.contains(startVec)) {
                if (closestDistance >= 0.0D) {
                    entity = entity1;
                    closestHitVec = startVec;
                    closestDistance = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vector3d vector3d1 = optional.get();
                double d3 = startVec.distanceTo(optional.get());

                if (d3 < closestDistance || closestDistance == 0.0D) {
                    boolean flag1 = false;

                    if (!flag1 && entity1.getLowestRidingEntity() == shooter.getLowestRidingEntity()) {
                        if (closestDistance == 0.0D) {
                            entity = entity1;
                            closestHitVec = vector3d1;
                        }
                    } else {
                        entity = entity1;
                        closestHitVec = vector3d1;
                        closestDistance = d3;
                    }
                }
            }
        }

        return entity == null ? null : new EntityRayTraceResult(entity, closestHitVec);
    }

    public RayTraceResult rayTrace(double rayTraceDistance,
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


    public RayTraceResult rayTraceResult(double rayTraceDistance,
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
            double d1;

            if (mc.playerController.extendedReach()) {
                d1 = 6.0D;
                distance = d1;
            } else {
                if (distance > 3.0D) {
                    flag = true;
                }

            }

            d1 = object.getHitVec().squareDistanceTo(vector3d);

            Vector3d vector3d1 = getVectorForRotation(pitch, yaw);
            Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vector3d1.scale(distance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2, axisalignedbb, (p_lambda$getMouseOver$0_0_) ->
                    !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith(), d1);

            if (entityraytraceresult != null) {
                Vector3d vector3d3 = entityraytraceresult.getHitVec();
                double d2 = vector3d.squareDistanceTo(vector3d3);

                if (flag && d2 > 9.0D) {
                    object = BlockRayTraceResult.createMiss(vector3d3, Direction.getFacingFromVector(vector3d1.x, vector3d1.y, vector3d1.z), new BlockPos(vector3d3));
                } else if (d2 < d1) {
                    object = entityraytraceresult;
                }
            }
        }
        return object;
    }


    public boolean rayTraceWithBlock(double rayTraceDistance,
                                     float yaw,
                                     float pitch,
                                     Entity entity, Entity target) {

        RayTraceResult object = null;

        if (entity != null && mc.world != null) {
            float partialTicks = mc.getRenderPartialTicks();
            double distance = rayTraceDistance;
            object = rayTrace(rayTraceDistance, yaw, pitch, entity);
            Vector3d vector3d = entity.getEyePosition(partialTicks);
            boolean flag = false;
            double d1;

            if (mc.playerController.extendedReach()) {
                d1 = 6.0D;
                distance = d1;
            } else {
                if (distance > 3.0D) {
                    flag = true;
                }

            }

            d1 = object.getHitVec().squareDistanceTo(vector3d);

            Vector3d vector3d1 = getVectorForRotation(pitch, yaw);
            Vector3d vector3d2 = vector3d.add(vector3d1.x * distance, vector3d1.y * distance, vector3d1.z * distance);
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vector3d1.scale(distance)).grow(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.rayTraceEntities(entity, vector3d, vector3d2, axisalignedbb, (p_lambda$getMouseOver$0_0_) ->
                    !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith(), d1);

            if (entityraytraceresult != null) {
                Vector3d vector3d3 = entityraytraceresult.getHitVec();
                double d2 = vector3d.squareDistanceTo(vector3d3);

                if (flag && d2 > 9.0D) {
                    object = BlockRayTraceResult.createMiss(vector3d3, Direction.getFacingFromVector(vector3d1.x, vector3d1.y, vector3d1.z), new BlockPos(vector3d3));
                } else if (d2 < d1) {
                    object = entityraytraceresult;
                }
            }
        }
        if (object instanceof EntityRayTraceResult) {
            return ((EntityRayTraceResult) object).getEntity().getEntityId() == target.getEntityId();
        }
        return false;
    }

    public Vector3d getVectorForRotation(float pitch, float yaw) {
        float yawRadians = -yaw * ((float) Math.PI / 180) - (float) Math.PI;
        float pitchRadians = -pitch * ((float) Math.PI / 180);

        float cosYaw = MathHelper.cos(yawRadians);
        float sinYaw = MathHelper.sin(yawRadians);
        float cosPitch = -MathHelper.cos(pitchRadians);
        float sinPitch = MathHelper.sin(pitchRadians);

        return new Vector3d(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
    }

    public RayTraceResult petRayTrace(double rayTraceDistance,
                                      float yaw,
                                      float pitch,
                                      Entity entity,
                                      Vector3d startVec) {
        net.minecraft.util.math.vector.Vector3d directionVec = getVectorForRotation(pitch, yaw);
        net.minecraft.util.math.vector.Vector3d endVec = startVec.add(
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

    public boolean rayTraceSingleEntity(float yaw, float pitch, double distance, Entity entity) {
        Vector3d eyeVec = mc.player.getEyePosition(1.0F);
        Vector3d lookVec = mc.player.getVectorForRotation(pitch, yaw);
        Vector3d extendedVec = eyeVec.add(lookVec.scale(distance));

        AxisAlignedBB AABB = entity.getBoundingBox();

        return AABB.contains(eyeVec) || AABB.rayTrace(eyeVec, extendedVec).isPresent();
    }

    public boolean rayTraceSingleEntity(double distance, AxisAlignedBB entity, Vector3d vector3d) {
        Vector3d eyeVec = mc.player.getEyePosition(1.0F);
        Vector3d extendedVec = eyeVec.add(vector3d.scale(distance));

        return entity.contains(eyeVec) || entity.rayTrace(eyeVec, extendedVec).isPresent();
    }
}