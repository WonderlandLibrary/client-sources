package me.xatzdevelopments.util;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

import java.util.List;

public class RaycastUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    /*
            Credits: RaycastUtil from LiquidBounce source
     */
    public static Entity rayCastEntity(double range, float yaw, float pitch) {
        final Entity renderViewEntity = mc.func_175606_aa();

        if (renderViewEntity != null && mc.theWorld != null) {
            final Vec3 eyePosition = renderViewEntity.func_174824_e(1F);

            final float yawCos = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292F);

            final Vec3 entityLook = new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            final Vec3 vector = eyePosition.addVector(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range);
            final List<Entity> entityList = mc.theWorld.func_175674_a(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range).expand(1D, 1D, 1D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));

            Entity pointedEntity = null;

            for (final Entity entity : entityList) {

                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);

                if (axisAlignedBB.isVecInside(eyePosition)) {
                    if (range >= 0.0D) {
                        pointedEntity = entity;
                        range = 0.0D;
                    }
                } else if (movingObjectPosition != null) {
                    final double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);

                    if (eyeDistance < range || range == 0.0D) {
                        if (entity == renderViewEntity.ridingEntity) {
                            if (range == 0.0D)
                                pointedEntity = entity;
                        } else {
                            pointedEntity = entity;
                            range = eyeDistance;
                        }
                    }
                }
            }

            return pointedEntity;
        }

        return null;
    }

}