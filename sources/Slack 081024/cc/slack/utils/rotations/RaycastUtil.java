package cc.slack.utils.rotations;

import cc.slack.utils.client.IMinecraft;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import java.util.List;

public class RaycastUtil implements IMinecraft {

    public static EntityLivingBase rayCast(double range) {
        return rayCast(range, RotationUtil.clientRotation);
    }

    public static EntityLivingBase rayCast(double range, float[] rotations) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        List<Entity> entities = mc.theWorld.getEntitiesInAABBexcluding(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().addCoord(
                        look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(1, 1, 1),
                Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        EntityLivingBase raycastedEntity = null;

        for (Entity ent : entities) {
            if (!(ent instanceof EntityLivingBase)) return null;
            EntityLivingBase entity = (EntityLivingBase) ent;
            if (entity == mc.thePlayer) continue;
            final float borderSize = entity.getCollisionBorderSize();
            final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
            final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

            if (axisalignedbb.isVecInside(eyes)) {
                if (range >= 0) {
                    raycastedEntity = entity;
                    range = 0;
                }
            } else if (movingobjectposition != null) {
                final double distance = eyes.distanceTo(movingobjectposition.hitVec);

                if (distance < range || range == 0) {
                    if (entity == entity.ridingEntity) {
                        if (range == 0) raycastedEntity = entity;
                    } else {
                        raycastedEntity = entity;
                        range = distance;
                    }
                }
            }
        }

        return raycastedEntity;
    }

    public static boolean itHitable(double range, float[] rotations, Entity ent) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);

        if (!(ent instanceof EntityLivingBase)) return false;
        EntityLivingBase entity = (EntityLivingBase) ent;
        if (entity == mc.thePlayer) return true;
        final float borderSize = entity.getCollisionBorderSize();
        final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
        final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

        return movingobjectposition != null;
    }

    public static boolean isHitable(double range, float[] rotations, Entity ent, float expand) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);

        if (!(ent instanceof EntityLivingBase)) return false;
        EntityLivingBase entity = (EntityLivingBase) ent;
        if (entity == mc.thePlayer) return true;
        final float borderSize = entity.getCollisionBorderSize() + expand;
        final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
        final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

        return movingobjectposition != null;
    }

    public static Vec3 rayCastHitVec(EntityLivingBase entity, double range, float[] rotations) {
        Vec3 eyes = mc.thePlayer.getPositionEyes(mc.timer.renderPartialTicks);
        Vec3 look = mc.thePlayer.getVectorForRotation(rotations[1], rotations[0]);
        Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);

        final float borderSize = entity.getCollisionBorderSize();
        final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
        final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);

        if (movingobjectposition == null) return null;

        return movingobjectposition.hitVec;
    }
}
