package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class RottUtilis {

    public static float constrainAngle(float angle) {
        angle = angle % 360F;

        while (angle <= -180) {
            angle = angle + 360;
        }

        while (angle > 180) {
            angle = angle - 360;
        }
        return angle;
    }

    public static float[] getRotations(final EntityLivingBase ent, int mode) {
        if (mode == 0) {
            final double x = ent.posX;
            final double z = ent.posZ;
            final double y = ent.posY + ent.getEyeHeight() / 2.0f;
            return getRotationFromPosition(x, z, y);
        }
        if (mode == 1) {
            final double x = ent.posX;
            final double z = ent.posZ;
            final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.75;
            return getRotationFromPosition(x, z, y);
        }
        if (mode == 2) {
            final double x = ent.posX;
            final double z = ent.posZ;
            final double y = ent.posY + ent.getEyeHeight() / 2.0f - 1.2;
            return getRotationFromPosition(x, z, y);
        }
        if (mode == 3) {
            final double x = ent.posX;
            final double z = ent.posZ;
            final double y = ent.posY + ent.getEyeHeight() / 2.0f - 1.5;
            return getRotationFromPosition(x, z, y);
        }
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f - 0.5;
        return getRotationFromPosition(x, z, y);
    }


    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.player.posX;
        final double zDiff = z - Minecraft.player.posZ;
        final double yDiff = y - Minecraft.player.posY - 0.6;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90.0f;
        final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180 / Math.PI));
        return new float[]{yaw, pitch};
    }
}
