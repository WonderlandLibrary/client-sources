package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class SmoothRots {

    public float prevServerRotation;
    public float prevPrevServerRotation;

    public synchronized static void faceEntityClient(Entity entity) {
        float[] rotations = getRotationsNeeded(entity);
        if (rotations != null) {
            Minecraft.player.rotationYaw = (float) (limitAngleChange(
                    Minecraft.player.prevRotationYaw, rotations[0], 55) + Math.random() * 10 - 5);// NoCheat+
            // bypass!!!
            Minecraft.player.rotationPitch = (float) (rotations[1] + Math.random() * 10 - 5);
        }
    }

    public synchronized static void faceEntityPacket(EntityLivingBase entity) {
        float[] rotations = getRotationsNeeded(entity);
        if (rotations != null) {
            float yaw = rotations[0];
            float pitch = rotations[1];
            Minecraft.player.connection.sendPacket(
                    new CPacketPlayer.Rotation(yaw, pitch, Minecraft.player.onGround));
        }
    }

    public static float[] getRotationsNeeded(Entity entity) {
        if (entity == null)
            return null;
        double diffX = entity.posX - Minecraft.player.posX;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9
                    - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
        } else
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
                    - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
        double diffZ = entity.posZ - Minecraft.player.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[]{
                Minecraft.player.rotationYaw
                        + MathHelper.wrapAngleTo180_float(yaw - Minecraft.player.rotationYaw),
                Minecraft.player.rotationPitch
                        + MathHelper.wrapAngleTo180_float(pitch - Minecraft.player.rotationPitch)};

    }

    public final static float limitAngleChange(final float current, final float intended, final float maxChange) {
        float change = intended - current;
        if (change > maxChange)
            change = maxChange;
        else if (change < -maxChange)
            change = -maxChange;
        return current + change;
    }

    public void resetPrevRotation() {
        prevServerRotation = 123456789;

    }
}