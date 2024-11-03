package dev.star.utils.player;

import com.google.common.base.Predicates;
import dev.star.event.impl.player.MotionEvent;
import dev.star.utils.Utils;
import dev.star.utils.misc.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.*;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

import java.util.List;

public class RotationUtils implements Utils {

    public static float[] clientRotation = new float[]{0.0F, 0.0F};


    /*
     * Sets the player's head rotations to the given yaw and pitch (visual-only).
     */
    @Exclude(Strategy.NAME_REMAPPING)
    public static void setVisualRotations(float yaw, float pitch) {
        mc.thePlayer.rotationYawHead = mc.thePlayer.renderYawOffset = yaw;
        mc.thePlayer.rotationPitchHead = pitch;
        clientRotation = new float[]{yaw, pitch};
    }


    public static float clampTo90(final float n) {
        return MathHelper.clamp_float(n, -90.0f, 90.0f);
    }

    public static float clamp(final float n) {
        return MathHelper.clamp_float(n, -90.0f, 90.0f);
    }

    public static Vec3 getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public static MovingObjectPosition rayTraceCustom(double blockReachDistance, float yaw, float pitch) {
        final Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        final Vec3 vec31 = getVectorForRotation(pitch, yaw);
        final Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
        return mc.theWorld.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    public static float angle(final double n, final double n2) {
        return (float) (Math.atan2(n - mc.thePlayer.posX, n2 - mc.thePlayer.posZ) * 57.295780181884766 * -1.0);
    }

    public static boolean inFov(float fov, BlockPos blockPos) {
        return inFov(fov, blockPos.getX(), blockPos.getZ());
    }

    public static void setClientRotation(float[] targetRotation) {

            clientRotation = targetRotation;

    }


    public static Vec3 getNormalRotVector(float[] rotation) {
        return getNormalRotVector(rotation[0], rotation[1]);
    }

    public static Vec3 getNormalRotVector(float yaw, float pitch) {
        return mc.thePlayer.getVectorForRotation(pitch, yaw);
    }

    public static boolean inFov(float fov, Entity entity) {
        return inFov(fov, entity.posX, entity.posZ);
    }

    public static boolean inFov(float fov, final double n2, final double n3) {
        fov *= 0.5;
        final double wrapAngleTo180_double = MathHelper.wrapAngleTo180_double((mc.thePlayer.rotationYaw - angle(n2, n3)) % 360.0f);
        if (wrapAngleTo180_double > 0.0) {
            if (wrapAngleTo180_double < fov) {
                return true;
            }
        } else if (wrapAngleTo180_double > -fov) {
            return true;
        }
        return false;
    }

    public static float[] getRotationsToPosition(double x, double y, double z) {
        double deltaX = x - mc.thePlayer.posX;
        double deltaY = y - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
        double deltaZ = z - mc.thePlayer.posZ;

        double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(-Math.atan2(deltaX, deltaZ));
        float pitch = (float) Math.toDegrees(-Math.atan2(deltaY, horizontalDistance));

        return new float[] {yaw, pitch};
    }

    public static MovingObjectPosition rayCast(final double distance, final float yaw, final float pitch) {
        final Vec3 getPositionEyes = mc.thePlayer.getPositionEyes(1.0f);
        final float n4 = -yaw * 0.017453292f;
        final float n5 = -pitch * 0.017453292f;
        final float cos = MathHelper.cos(n4 - 3.1415927f);
        final float sin = MathHelper.sin(n4 - 3.1415927f);
        final float n6 = -MathHelper.cos(n5);
        final Vec3 vec3 = new Vec3(sin * n6, MathHelper.sin(n5), cos * n6);
        return mc.theWorld.rayTraceBlocks(getPositionEyes, getPositionEyes.addVector(vec3.xCoord * distance, vec3.yCoord * distance, vec3.zCoord * distance), false, false, false);
    }

    public static void setVisualRotations(float[] rotations) {
        setVisualRotations(rotations[0], rotations[1]);
    }

    public static void setVisualRotations(MotionEvent e) {
        setVisualRotations(e.getYaw(), e.getPitch());
    }

    public static float clampRotation() {
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        } else if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }

    public static float getSensitivityMultiplier() {
        float SENSITIVITY = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (SENSITIVITY * SENSITIVITY * SENSITIVITY * 8.0F) * 0.15F;
    }

    public static float smoothRotation(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);

        if (f > speed) {
            f = speed;
        }

        if (f < -speed) {
            f = -speed;
        }

        return from + f;
    }

    public static float[] getFacingRotations(ScaffoldUtils.BlockCache blockCache) {
        double d1 = blockCache.getPosition().getX() + 0.5D - mc.thePlayer.posX + blockCache.getFacing().getFrontOffsetX() / 2.0D;
        double d2 = blockCache.getPosition().getZ() + 0.5D - mc.thePlayer.posZ + blockCache.getFacing().getFrontOffsetZ() / 2.0D;
        double d3 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (blockCache.getPosition().getY());
        double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
        float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0D / Math.PI);
        if (f1 < 0.0F) {
            f1 += 360.0F;
        }
        return new float[]{f1, f2};
    }

    public static float[] dosmoothrots(float Yaw, float Pitch, int min, int max) {


        float yaw;
        float pitch;

        yaw = smoothRotation(mc.thePlayer.prevRotationYawHead, Yaw, 10 * MathUtils.getRandomFloat(max, min));
        pitch = smoothRotation(mc.thePlayer.prevRotationPitchHead, Pitch, 10 * MathUtils.getRandomFloat(max, min));
        //  yaw = yaw +  MathUtils.getRandomFloat(3, 1);


        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(BlockPos blockPos, EnumFacing enumFacing) {
        double d = (double) blockPos.getX() + 0.5 - mc.thePlayer.posX + (double) enumFacing.getFrontOffsetX() * 0.25;
        double d2 = (double) blockPos.getZ() + 0.5 - mc.thePlayer.posZ + (double) enumFacing.getFrontOffsetZ() * 0.25;
        double d3 = mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight() - blockPos.getY() - (double) enumFacing.getFrontOffsetY() * 0.25;
        double d4 = MathHelper.sqrt_double(d * d + d2 * d2);
        float f = (float) (Math.atan2(d2, d) * 180.0 / Math.PI) - 90.0f;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0 / Math.PI);
        return new float[]{MathHelper.wrapAngleTo180_float(f), f2};
    }

    public static float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        Minecraft mc = Minecraft.getMinecraft();
        final double xSize = entity.posX - mc.thePlayer.posX;
        final double ySize = entity.posY + entity.getEyeHeight() / 2 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double zSize = entity.posZ - mc.thePlayer.posZ;
        final double theta = MathHelper.sqrt_double(xSize * xSize + zSize * zSize);
        final float yaw = (float) (Math.atan2(zSize, xSize) * 180 / Math.PI) - 90;
        final float pitch = (float) (-(Math.atan2(ySize, theta) * 180 / Math.PI));
        return new float[]{(mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) % 360, (mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) % 360.0f};
    }

    public static float[] getRotationsDiff(Entity entity) {
        if (entity == null) {
            return null;
        }
        float[] rotations = getRotationsNeeded(entity);
        return new float[]{Math.abs(mc.thePlayer.rotationYaw - rotations[0]), Math.abs(mc.thePlayer.rotationPitch - rotations[1])};
    }

    public static float[] getFacingRotations2(final int paramInt1, final double d, final int paramInt3) {
        final EntitySnowball localEntityPig = new EntitySnowball(Minecraft.getMinecraft().theWorld);
        localEntityPig.posX = paramInt1 + 0.5;
        localEntityPig.posY = d + 0.5;
        localEntityPig.posZ = paramInt3 + 0.5;
        return getRotationsNeeded(localEntityPig);
    }

    public static float getEnumRotations(EnumFacing facing) {
        float yaw = 0;
        if (facing == EnumFacing.NORTH) {
            yaw = 0;
        }
        if (facing == EnumFacing.EAST) {
            yaw = 90;
        }
        if (facing == EnumFacing.WEST) {
            yaw = -90;
        }
        if (facing == EnumFacing.SOUTH) {
            yaw = 180;
        }
        return yaw;
    }

    public static float getYaw(Vec3 to) {
        float x = (float) (to.xCoord - mc.thePlayer.posX);
        float z = (float) (to.zCoord - mc.thePlayer.posZ);
        float var1 = (float) (StrictMath.atan2(z, x) * 180.0D / StrictMath.PI) - 90.0F;
        float rotationYaw = mc.thePlayer.rotationYaw;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    public static Vec3 getVecRotations(float yaw, float pitch) {
        double d = Math.cos(Math.toRadians(-yaw) - Math.PI);
        double d1 = Math.sin(Math.toRadians(-yaw) - Math.PI);
        double d2 = -Math.cos(Math.toRadians(-pitch));
        double d3 = Math.sin(Math.toRadians(-pitch));
        return new Vec3(d1 * d2, d3, d * d2);
    }

    public static float[] getRotations(Vec3 vec) {
        return getRotations(vec.xCoord, vec.yCoord, vec.zCoord);
    }


    public static float[] getRotations(double posX, double posY, double posZ) {
        double x = posX - mc.thePlayer.posX, z = posZ - mc.thePlayer.posZ, y = posY - (mc.thePlayer.getEyeHeight() + mc.thePlayer.posY);
        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(MathHelper.atan2(y, d3) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotations(final BlockPos blockPos) {
        final double n = blockPos.getX() + 0.45 - mc.thePlayer.posX;
        final double n2 = blockPos.getY() + 0.45 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        final double n3 = blockPos.getZ() + 0.45 - mc.thePlayer.posZ;
        return new float[] { mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 57.295780181884766) - 90.0f - mc.thePlayer.rotationYaw), clamp(mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, MathHelper.sqrt_double(n * n + n3 * n3)) * 57.295780181884766)) - mc.thePlayer.rotationPitch)) };
    }

    public static boolean inRange(final BlockPos blockPos, final double n) {
        final float[] array = RotationUtils.getRotations(blockPos);
        final Vec3 getPositionEyes = mc.thePlayer.getPositionEyes(1.0f);
        final float n2 = -array[0] * 0.017453292f;
        final float n3 = -array[1] * 0.017453292f;
        final float cos = MathHelper.cos(n2 - 3.1415927f);
        final float sin = MathHelper.sin(n2 - 3.1415927f);
        final float n4 = -MathHelper.cos(n3);
        final Vec3 vec3 = new Vec3(sin * n4, MathHelper.sin(n3), cos * n4);
        Block block = BlockUtils.getBlockAtPos(blockPos);
        IBlockState blockState = BlockUtils.getBlockState(blockPos);
        if (block != null && blockState != null) {
            AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, blockPos, blockState);
            if (boundingBox != null) {
                Vec3 targetVec = getPositionEyes.addVector(vec3.xCoord * n, vec3.yCoord * n, vec3.zCoord * n);
                MovingObjectPosition intercept = boundingBox.calculateIntercept(getPositionEyes, targetVec);
                if (intercept != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public static float[] getRotations(BlockPos blockPos, final float n, final float n2) {
        final float[] lastAngles = new float[]{mc.thePlayer.prevRotationYawHead, mc.thePlayer.prevRotationYawHead};

        final float[] array = getRotations(blockPos);


        float[] nigga = {array[0], array[1]};
        applyGCD(nigga, lastAngles);

        return nigga;
    }

    public static float[] getRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double n = entity.posX - mc.thePlayer.posX;
        final double n2 = entity.posZ - mc.thePlayer.posZ;
        double n3;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            n3 = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        } else {
            n3 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float) (Math.atan2(n2, n) * 57.295780181884766) - 90.0f - mc.thePlayer.rotationYaw), clamp(mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float) (-(Math.atan2(n3, MathHelper.sqrt_double(n * n + n2 * n2)) * 57.295780181884766)) - mc.thePlayer.rotationPitch) + 3.0f)};
    }

    public static float[] getRotations(Entity entity, final float n, final float n2) {
        final float[] array = getRotations(entity);
        if (array == null) {
            return null;
        }
        final float[] lastAngles = new float[]{mc.thePlayer.prevRotationYawHead, mc.thePlayer.prevRotationYawHead};



        float[] nigga = {array[0], array[1]};
        applyGCD(nigga, lastAngles);

        return nigga;
    }

    public static  double getMouseGCD() {
        final float sens = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float pow = sens * sens * sens * 8.0F;
        return (double) pow * 0.15;
    }

    public static void applyGCD(final float[] rotations,
                                final float[] prevRots) {
        final float yawDif = rotations[0] - prevRots[0];
        final float pitchDif = rotations[1] - prevRots[1];
        final double gcd = getMouseGCD();

        rotations[0] -= yawDif % gcd;
        rotations[1] -= pitchDif % gcd;
    }

    public static float[] getSmoothRotations(EntityLivingBase entity, float rotspped) {
        final float[] lastAngles = new float[]{mc.thePlayer.prevRotationYawHead, mc.thePlayer.prevRotationYawHead};

        float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float fac = f1 * f1 * f1 * 256.0F;

        double x = entity.posX - mc.thePlayer.posX;
        double z = entity.posZ - mc.thePlayer.posZ;
        double y = entity.posY + entity.getEyeHeight()
                - (mc.thePlayer.getEntityBoundingBox().minY
                + (mc.thePlayer.getEntityBoundingBox().maxY
                - mc.thePlayer.getEntityBoundingBox().minY));

        double d3 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (MathHelper.atan2(z, x) * 180.0 / Math.PI) - 90.0F;
        float pitch = (float) (-(MathHelper.atan2(y, d3) * 180.0 / Math.PI));
        yaw = smoothRotation(mc.thePlayer.prevRotationYawHead, yaw, fac * rotspped);
        pitch = smoothRotation(mc.thePlayer.prevRotationPitchHead, pitch, fac * rotspped);

        float[] nigga = {yaw, pitch};
        applyGCD(nigga, lastAngles);

        return nigga;
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
