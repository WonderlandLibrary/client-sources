package io.github.nevalackin.radium.utils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public final class RotationUtils {

    private RotationUtils() {
    }

    public static float[] getRotationsToEntity(Entity entity) {
        final EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = entity.posX - player.posX;
        double zDist = entity.posZ - player.posZ;

        double entEyeHeight = entity.getEyeHeight();
        double yDist = ((entity.posY + entEyeHeight) - Math.min(Math.max(entity.posY - player.posY, 0), entEyeHeight)) -
                (player.posY + player.getEyeHeight());
        double fDist = MathHelper.sqrt_double(xDist * xDist + zDist * zDist);
        float rotationYaw = Wrapper.getPlayer().rotationYaw;
        float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;

        float yaw = rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
        float rotationPitch = Wrapper.getPlayer().rotationPitch;

        float var2 = (float) (-(Math.atan2(yDist, fDist) * 180.0D / Math.PI));
        float pitch = rotationPitch + MathHelper.wrapAngleTo180_float(var2 - rotationPitch);

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90.0f, 90.0f)};
    }

    public static float getYawToEntity(Entity entity, boolean useOldPos) {
        final EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = (useOldPos ? entity.prevPosX : entity.posX) -
                (useOldPos ? player.prevPosX : player.posX);
        double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) -
                (useOldPos ? player.prevPosZ : player.posZ);
        float rotationYaw = useOldPos ? Wrapper.getPlayer().prevRotationYaw : Wrapper.getPlayer().rotationYaw;
        float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }
}
