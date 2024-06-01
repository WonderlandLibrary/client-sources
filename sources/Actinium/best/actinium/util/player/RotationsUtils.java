package best.actinium.util.player;

import best.actinium.util.IAccess;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;

import java.util.List;

public class RotationsUtils implements IAccess {
    public static float[] getSmoothRotations(BlockPos blockPosition, EnumFacing enumFacing, boolean y,float bruteforeamount,float yaw) {
        double d = (double) blockPosition.getX() + 0.5 - mc.thePlayer.posX + (double) enumFacing.getFrontOffsetX() * 0.35;
        double d2 = (double) blockPosition.getZ() + 0.5 - mc.thePlayer.posZ + (double) enumFacing.getFrontOffsetZ() * 0.35;
        double d3 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - blockPosition.getY() - (double) enumFacing.getFrontOffsetY() * 0.25;
        double d4 = MathHelper.sqrt_double(d * d + d2 * d2);

        float f = (float) (Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0 / Math.PI);

        float[] smoothRotations = new float[]{
                MathHelper.wrapAngleTo180_float(applySmoothing(f, mc.thePlayer.rotationYaw - yaw, bruteforeamount)),
                y ? applySmoothing(f2, mc.thePlayer.rotationPitch, bruteforeamount) : 81.5f
        };

        return smoothRotations;
    }

    private static float applySmoothing(float target, float current, float smoothing) {
        float angle = MathHelper.wrapAngleTo180_float(target - current);
        return current + angle * smoothing;
    }

    public static Vec3 getHitVec3(Entity entity) {
        Vec3 eyesPosition = mc.thePlayer.getPositionEyes(1.0f);

        float size = entity.getCollisionBorderSize();

        AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox().expand(
                size,
                size,
                size
        );

        double x = MathHelper.clamp_double(eyesPosition.xCoord, entityBoundingBox.minX, entityBoundingBox.maxX);
        double y = MathHelper.clamp_double(eyesPosition.yCoord, entityBoundingBox.minY, entityBoundingBox.maxY);
        double z = MathHelper.clamp_double(eyesPosition.zCoord, entityBoundingBox.minZ, entityBoundingBox.maxZ);

        return new Vec3(x, y, z);
    }

    public static float[] getRotations(Entity entity) {
        Vec3 vec3 = getHitVec3(entity);

        double x = vec3.xCoord - mc.thePlayer.posX;
        double y = vec3.yCoord - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = vec3.zCoord - mc.thePlayer.posZ;

        double theta = MathHelper.sqrt_double(x * x + z * z);

        float yaw = (float) (Math.atan2(z, x) * 180.0 / Math.PI - 90.0);
        float pitch = (float) (-(Math.atan2(y, theta) * 180.0 / Math.PI));

        return new float[] {
                (mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) % 360,
                (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) % 360
        };
    }

    public static boolean isMouseOver(final float yaw, final float pitch, final Entity target, final float range) {
        final float partialTicks = mc.timer.renderPartialTicks;
        final Entity entity = mc.getRenderViewEntity();
        MovingObjectPosition objectMouseOver;
        Entity mcPointedEntity = null;

        if (entity != null && mc.theWorld != null) {

            mc.mcProfiler.startSection("pick");
            final double d0 = mc.playerController.getBlockReachDistance();
            objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            final Vec3 vec3 = entity.getPositionEyes(partialTicks);
            final boolean flag = d0 > (double) range;

            if (objectMouseOver != null) {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }

            final Vec3 vec31 = mc.thePlayer.getVectorForRotation(pitch, yaw);
            final Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec33 = null;
            final float f = 1.0F;
            final List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;

            for (final Entity entity1 : list) {
                final float f1 = entity1.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
                final MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3)) {
                    if (d2 >= 0.0D) {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                } else if (movingobjectposition != null) {
                    final double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D) {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition.hitVec;
                        d2 = d3;
                    }
                }
            }

            if (pointedEntity != null && flag && vec3.distanceTo(vec33) > (double) range) {
                pointedEntity = null;
                objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
            }

            if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                    mcPointedEntity = pointedEntity;
                }
            }

            mc.mcProfiler.endSection();

            return mcPointedEntity == target;
        }

        return false;
    }
}
