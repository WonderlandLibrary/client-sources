package club.mega.util;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.impl.combat.KillAura;
import club.mega.module.impl.player.Scaffold;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

public final class RotationUtil implements MinecraftInterface {

    private static Random RANDOM = new Random();

    /*public static float[] predictRotations(final Entity entity) {
        final double x = entity.posX + (entity.posX - entity.lastTickPosX) * (MC.getNetHandler().getPlayerInfo(MC.thePlayer.getUniqueID()).getResponseTime() / 50D);
        final double y = entity.posY + (entity.posY - entity.lastTickPosY) * (MC.getNetHandler().getPlayerInfo(MC.thePlayer.getUniqueID()).getResponseTime() / 50D);
        final double z = entity.posZ + (entity.posZ - entity.lastTickPosZ) * (MC.getNetHandler().getPlayerInfo(MC.thePlayer.getUniqueID()).getResponseTime() / 50D);
        return getRotations(x, y, z);
    }*/

    public static float[] getRotations(final double x, final double y, final double z) {
        final double d0 = x - MC.thePlayer.posX;
        final double d1 = y - (MC.thePlayer.posY + (double)MC.thePlayer.getEyeHeight());
        final double d2 = z - MC.thePlayer.posZ;
        final double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        final float f1 = (float)(-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));

        return new float[] {f, f1};
    }

    public static float[] getRotations(final Vec3 vec3) {
        final Vec3 playerPositionEyes = MC.thePlayer.getPositionEyes(1.0F);

        final double d0 = vec3.xCoord - playerPositionEyes.xCoord;
        final double d1 = vec3.yCoord - playerPositionEyes.yCoord;
        final double d2 = vec3.zCoord - playerPositionEyes.zCoord;
        final double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        final float f1 = (float)(-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI));

        return new float[] {f, f1};
    }

    /**
     * applays the cga to the rots
     * @param rotations the rots
     * @param prevRots the prev rots
     * @return the new rots
     */
    public static float[] applyGCD(final float[] rotations, final float[] prevRots) {
        final float yawDif = rotations[0] - prevRots[0];
        final float pitchDif = rotations[1] - prevRots[1];
        float f = 0.5F * 0.6F + 0.2F;
        float f1 = f * f * f * 8.0F;

        float f2 = (int) (yawDif * f1 * 5);
        float f3 = (int) (pitchDif * f1 * 5);

        return setAngles(prevRots[0], prevRots[1], f2, f3);
    }

    public static float[] setAngles(float currentYaw, float currentPitch, float yaw, float pitch) {
        currentYaw = (float) ((double) currentYaw + (double) yaw * 0.15D);
        currentPitch = (float) ((double) currentPitch + (double) pitch * 0.15D);
        return new float[]{currentYaw, currentPitch};
    }

    public static Vec3 getNearestPositionInBB(final Vec3 paramVec3, final AxisAlignedBB paramAxisAlignedBB) {
        final double d1 = MathHelper.clamp_double(paramVec3.xCoord, paramAxisAlignedBB.minX, paramAxisAlignedBB.maxX);
        final double d2 = MathHelper.clamp_double(paramVec3.yCoord, paramAxisAlignedBB.minY - 0.1F, paramAxisAlignedBB.maxY + 0.1F);
        final double d3 = MathHelper.clamp_double(paramVec3.zCoord, paramAxisAlignedBB.minZ, paramAxisAlignedBB.maxZ);
        return new Vec3(d1, d2, d3);
    }

    public static float[] getScaffoldRotations(final ScaffoldUtil.BlockData data, final boolean legit) {
        final Vec3 eyes = MC.thePlayer.getPositionEyes(RandomUtils.nextFloat(2.997f, 3.997f));
        final Vec3 position = new Vec3(data.position.getX() + 0.49, data.position.getY() + 0.49, data.position.getZ() + 0.49).add(new Vec3(data.face.getDirectionVec()).scale(0.489997f));
        final Vec3 resultPosition = position.subtract(eyes);
        float yaw = (float) Math.toDegrees(Math.atan2(resultPosition.zCoord, resultPosition.xCoord)) - 90.0F;
        float pitch = (float) -Math.toDegrees(Math.atan2(resultPosition.yCoord, Math.hypot(resultPosition.xCoord, resultPosition.zCoord)));
        final float[] rotations = new float[] {yaw, pitch};

        if (legit) {
            return new float[] {MC.thePlayer.rotationYaw + 180F, updateRotation(Scaffold.getInstance().getPrevRotations()[1], applyGCD(new float[] {yaw, pitch}, Scaffold.getInstance().getPrevRotations())[1], (float) RandomUtil.getRandomNumber(30, 80))};
        }

        return applyGCD(rotations, Scaffold.getInstance().getPrevRotations());
    }

    public static float[] getKillAuraRotations(final EntityLivingBase target, final boolean legit) {
        final float[] raw = new float[] {(float) (getRotations(getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), target.getEntityBoundingBox()))[0] + (Mega.INSTANCE.getModuleManager().getModule(KillAura.class).random.get() ? RANDOM.nextGaussian() * RANDOM.nextGaussian() * 2.0F : 0)), (float) (getRotations(getNearestPositionInBB(MC.thePlayer.getPositionEyes(1.0F), target.getEntityBoundingBox()))[1] + (Mega.INSTANCE.getModuleManager().getModule(KillAura.class).random.get() ? RANDOM.nextGaussian() * RANDOM.nextGaussian() * 1.1F : 0))};
        final float[] smooth = new float[] {updateRotation(AuraUtil.getPrevRotations()[0], raw[0], getRotationSpeed()), updateRotation(AuraUtil.getPrevRotations()[1], raw[1], getRotationSpeed())};

        return applyGCD(Mega.INSTANCE.getModuleManager().getModule(KillAura.class).smoothRotations.get() ? smooth : raw, AuraUtil.getPrevRotations());
    }

    private static float getRotationSpeed() {
        if (Mega.INSTANCE.getModuleManager().getModule(KillAura.class).calculateSmoothRotations.get()) {
            if (MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) <= 0.4)
                AuraUtil.reduceRotationSpeed((float) RandomUtil.getRandomNumber(2, 3));
            if (MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) <= 3 && MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) > 0.4)
                AuraUtil.reduceRotationSpeed((float) RandomUtil.getRandomNumber(1, 2));
            if (MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) <= (AuraUtil.getRange() + AuraUtil.getPreRange() - 0.4) && MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) > 3)
                AuraUtil.reduceRotationSpeed((float) RandomUtil.getRandomNumber(0.3, 1.2));
            if (MC.thePlayer.getDistanceToEntity(AuraUtil.getTarget()) >= (AuraUtil.getRange() + AuraUtil.getPreRange()) - 0.3)
                AuraUtil.setRotationSpeed(70);
        }
        return AuraUtil.getRotationSpeed();
    }

    public static float updateRotation(float paramFloat1, float paramFloat2, float paramFloat3) {
        float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
        if (f > paramFloat3) {
            f = paramFloat3;
        }
        if (f < -paramFloat3) {
            f = -paramFloat3;
        }
        return paramFloat1 + f;
    }

}
