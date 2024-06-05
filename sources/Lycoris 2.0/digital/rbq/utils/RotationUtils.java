/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import digital.rbq.utils.MathUtils;
import digital.rbq.utils.PlayerUtils;

public final class RotationUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float[] getRotations(double posX, double posY, double posZ) {
        EntityPlayerSP player = RotationUtils.mc.thePlayer;
        double x = posX - player.posX;
        double y = posY - (player.posY + (double)player.getEyeHeight());
        double z = posZ - player.posZ;
        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationsEntity(EntityLivingBase entity) {
        if (PlayerUtils.isOnHypixel() && RotationUtils.mc.thePlayer.isMoving()) {
            return RotationUtils.getRotations(entity.posX + MathUtils.randomNumber(0.03, -0.03), entity.posY + (double)entity.getEyeHeight() - 0.4 + MathUtils.randomNumber(0.07, -0.07), entity.posZ + MathUtils.randomNumber(0.03, -0.03));
        }
        return RotationUtils.getRotations(entity.posX, entity.posY + (double)entity.getEyeHeight() - 0.4, entity.posZ);
    }
}

