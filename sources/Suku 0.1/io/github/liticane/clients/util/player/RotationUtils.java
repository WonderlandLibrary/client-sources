package io.github.liticane.clients.util.player;

import com.google.common.base.Predicates;
import io.github.liticane.clients.util.interfaces.IMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;

import java.util.List;

public class RotationUtils implements IMethods {
    public static Vec3 getHitVec3(Entity entity) {
        Vec3 eyesPosition = mc.player.getPositionEyes(1.0f);

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

    public static float[] getRotations(Entity entity, float currentYaw, float currentPitch) {
        Vec3 vec3 = getHitVec3(entity);

        double x = vec3.xCoord - mc.player.posX;
        double y = vec3.yCoord - (mc.player.posY + mc.player.getEyeHeight());
        double z = vec3.zCoord - mc.player.posZ;

        double theta = MathHelper.sqrt_double(x * x + z * z);

        float yaw = (float) (Math.atan2(z, x) * 180.0 / Math.PI - 90.0);
        float pitch = (float) (-(Math.atan2(y, theta) * 180.0 / Math.PI));

        return new float[] {
                (mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw)) % 360,
                (mc.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch)) % 360
        };
    }

    public static float[] getRotations(BlockPos blockPosition, EnumFacing enumFacing,boolean y) {
        double d = (double) blockPosition.getX() + 0.5 - mc.player.posX + (double) enumFacing.getFrontOffsetX() * 0.35;
        double d2 = (double) blockPosition.getZ() + 0.5 - mc.player.posZ + (double) enumFacing.getFrontOffsetZ() * 0.35;
        double d3 = mc.player.posY + (double) mc.player.getEyeHeight() - blockPosition.getY() - (double) enumFacing.getFrontOffsetY() * 0.25;
        double d4 = MathHelper.sqrt_double(d * d + d2 * d2);
        //90
        float f = (float) (Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0 / Math.PI);
        //blockPosition.getY()
        return new float[]{MathHelper.wrapAngleTo180_float(f), y ? f2 :81.5f};
    }

    public static float[] getSmoothRotations(BlockPos blockPosition, EnumFacing enumFacing, boolean y,float bruteforeamount) {
        double d = (double) blockPosition.getX() + 0.5 - mc.player.posX + (double) enumFacing.getFrontOffsetX() * 0.35;
        double d2 = (double) blockPosition.getZ() + 0.5 - mc.player.posZ + (double) enumFacing.getFrontOffsetZ() * 0.35;
        double d3 = mc.player.posY + (double) mc.player.getEyeHeight() - blockPosition.getY() - (double) enumFacing.getFrontOffsetY() * 0.25;
        double d4 = MathHelper.sqrt_double(d * d + d2 * d2);

        float f = (float) (Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0 / Math.PI);

        float smoothness = bruteforeamount;
        float[] smoothRotations = new float[]{
                MathHelper.wrapAngleTo180_float(applySmoothing(f, mc.player.rotationYaw - 160, smoothness)),
                y ? applySmoothing(f2, mc.player.rotationPitch, smoothness) : 81.5f
        };

        return smoothRotations;
    }

    private static float applySmoothing(float target, float current, float smoothing) {
        float angle = MathHelper.wrapAngleTo180_float(target - current);
        return current + angle * smoothing;
    }



    public static float[] getFixedRotations(float[] rotations, float[] lastRotations) {
        Minecraft mc = Minecraft.getInstance();

        float yaw = rotations[0];
        float pitch = rotations[1];

        float lastYaw = lastRotations[0];
        float lastPitch = lastRotations[1];

        float f = mc.settings.mouseSensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 1.2F;

        float deltaYaw = yaw - lastYaw;
        float deltaPitch = pitch - lastPitch;

        float fixedDeltaYaw = deltaYaw - (deltaYaw % gcd);
        float fixedDeltaPitch = deltaPitch - (deltaPitch % gcd);

        float fixedYaw = lastYaw + fixedDeltaYaw;
        float fixedPitch = lastPitch + fixedDeltaPitch;

        return new float[] { fixedYaw, fixedPitch };
    }

    public static float[] getFacingRotations(ScafUtil.BlockData blockCache) {
        double d1 = blockCache.getPosition().getX() - mc.player.posX + blockCache.getFacing().getFrontOffsetX() / 2.0D;
        double d2 = blockCache.getPosition().getZ() - mc.player.posZ + blockCache.getFacing().getFrontOffsetZ() / 2.0D;
        double d3 = (blockCache.getPosition().getY());
        double d4 = Math.sqrt(d1 * d1 + d2 * d2);
        float f1 = (float) (Math.atan2(d2, d1) * (180.0D / Math.PI)) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * (180.0D / Math.PI));

        //if (f1 > 0.0F) {
        //            ChatUtil.display("Corrected");
        //            f1 = 360.0F;
        //        }

        return new float[]{f1, f2};
    }


    public static float getRotsbsmove() {
        //160
        float yaw = mc.player.rotationYaw - 180;
        if(mc.settings.keyBindLeft.isKeyDown()) {
            yaw = mc.player.rotationYaw - 220;
        } else if (mc.settings.keyBindRight.isKeyDown()) {
            yaw = mc.player.rotationYaw - 150;
        }
        return yaw;
    }


    public static void setVS(float yaw, float pitch) {
        Minecraft.getInstance().player.rotationYawHead = Minecraft.getInstance().player.renderYawOffset = yaw;
        Minecraft.getInstance().player.rotationPitchHead = pitch;
    }

    public static void setVS(float yaw, float pitch,float lastyaw,int ticks) {
        Minecraft.getInstance().player.rotationYawHead = yaw;
        if(mc.player.ticksExisted % ticks == 0) {
            Minecraft.getInstance().player.renderYawOffset = lastyaw;
        } else {
            Minecraft.getInstance().player.renderYawOffset = Minecraft.getInstance().player.prevRenderYawOffset;
        }
        Minecraft.getInstance().player.rotationPitchHead = pitch;
    }

    public static boolean isMouseOver(final float yaw, final float pitch, final Entity target, final float range) {
        final float partialTicks = mc.timer.renderPartialTicks;
        final Entity entity = mc.getRenderViewEntity();
        MovingObjectPosition objectMouseOver;
        Entity mcPointedEntity = null;

        if (entity != null && mc.world != null) {

            mc.mcProfiler.startSection("pick");
            final double d0 = mc.controller.getBlockReachDistance();
            objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            final Vec3 vec3 = entity.getPositionEyes(partialTicks);
            final boolean flag = d0 > (double) range;

            if (objectMouseOver != null) {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }

            final Vec3 vec31 = mc.player.getVectorForRotation(pitch, yaw);
            final Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            Entity pointedEntity = null;
            Vec3 vec33 = null;
            final float f = 1.0F;
            final List<Entity> list = mc.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
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
