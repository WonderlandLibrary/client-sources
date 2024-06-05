package alphentus.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

import java.util.Random;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class RotationUtil {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static RandomUtil randomUtil = new RandomUtil();

    public static float[] faceEntity(Entity entityIn, float currentYaw, float currentPitch, float yawSpeed, float pitchSpeed, boolean canHit) {
        double x = entityIn.posX - mc.thePlayer.posX;
        double y = entityIn.posY + entityIn.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = entityIn.posZ - mc.thePlayer.posZ;

        float range = mc.thePlayer.getDistanceToEntity(entityIn);
        float calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) ((MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F + randomUtil.randomGaussian((int) (4 / range), 0, true));
        float calcPitch = (float) ((-(MathHelper.func_181159_b(y, calculate) * 180.0D / Math.PI)) + randomUtil.randomGaussian((int) (5 / range), 0, false));

        float f = mc.gameSettings.mouseSensitivity * 0.8F;
        float gcd = f * f * f * 1.2F;

        float yaw = updateRotation(currentYaw, calcYaw, yawSpeed);
        float pitch = updateRotation(currentPitch, calcPitch, pitchSpeed);

        yaw -= yaw % gcd;
        pitch -= pitch % gcd;

        return new float[]{yaw, pitch};
    }

    public static float[] getFaceDirectionToBlockPos(BlockPos pos, float currentYaw, float currentPitch) {
        double x = (pos.getX() + 0.5F) - mc.thePlayer.posX;
        double y = (pos.getY()) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = (pos.getZ() + 0.5F) - mc.thePlayer.posZ;

        double calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float calcPitch = (float) -(MathHelper.func_181159_b(y, calculate) * 180.0D / Math.PI);

        float finalPitch = calcPitch >= 90 ? 90 : calcPitch;

        float yaw = updateRotation(currentYaw, calcYaw, 360);
        float pitch = updateRotation(currentPitch, finalPitch, 360);

        return new float[]{yaw, pitch};
    }

    public static float updateRotation(float current, float intended, float speed) {
        float f = MathHelper.wrapAngleTo180_float(intended - current);
        if (f > speed)
            f = speed;
        if (f < -speed)
            f = -speed;
        return current + f;
    }

}