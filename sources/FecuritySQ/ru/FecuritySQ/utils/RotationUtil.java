package ru.FecuritySQ.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

import java.security.SecureRandom;
import java.util.Random;

public class RotationUtil {
    static Minecraft mc = Minecraft.getInstance();
    public static float yaw;
    public static float pitch;
    public static boolean rotationInUse;
    public static float[] getDefault(ClientPlayerEntity player, LivingEntity target, boolean isRandom) {
        float yawRandom = (float) getRandom(new SecureRandom(), -0.05, 0.05);
        float pitchRandom = (float) getRandom(new SecureRandom(), -0.05, 0.05);

        if (target != null) {
            double posX = isRandom ? target.getPosX() - player.getPosX() + (double) yawRandom : target.getPosX() - player.getPosX();
            double posY = isRandom ? target.getPosY() + (double) target.getEyeHeight() - (player.getPosY() + (double) player.getEyeHeight() - (double) pitchRandom) : target.getPosY() + (double) target.getEyeHeight() - (player.getPosY() + (double) player.getEyeHeight());
            double posZ = isRandom ? target.getPosZ() - player.getPosZ() - (double) yawRandom : target.getPosZ() - player.getPosZ();
            double sqrt = MathHelper.sqrt(posX * posX + posZ * posZ);
            float yaw = (float) (Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f;
            float pitch = (float) (-(Math.atan2(posY, sqrt) * 180.0 / Math.PI));
            double sens = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            double pow = sens * sens * sens * 1.2F;
            yaw -= yaw % pow;
            pitch -= pitch % (pow * sens);
            return new float[]{(yaw), (pitch)};
        }
        return new float[]{(yaw), (pitch)};
    }
    private static double getRandom(Random random, double min, double max) {
        return min >= max ? min : random.nextDouble() * (max - min) + min;
    }
}
