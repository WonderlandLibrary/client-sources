/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.utils.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.concurrent.ThreadLocalRandom;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class MathUtils {

    public static boolean chance(int min, int max, int desired) {
        return MathHelper.nextInt(Random.create(), min, max + 1) > desired;
    }

    public static boolean chance(int min, int max, double desired) {
        return MathHelper.nextDouble(Random.create(), min, max + 1) > desired;
    }

    public static double getRandomDouble(double from, double to) {
        if (from >= to) return from;
        return ThreadLocalRandom.current().nextDouble(from, to);
    }

    public static boolean isInFOV(final Entity entity, double angle) {
        angle *= 0.5;

        if(entity != null) {
            final double angleDiff = getAngleDifference(mc.player.getYaw(), getRotations(entity)[0]);

            return angleDiff > 0 && angleDiff < angle || -angle < angleDiff && angleDiff < 0 && entity != null;
        }
        return false;
    }

    public static int getRandomNumber(int max, int min) {
        return  -min + (int) (Math.random() * ((max - (-min)) + 1));
    }

    public static float[] getRotations(final Entity ent) {
        final double x = ent.getPos().getX();
        final double y = ent.getPos().getY() + ent.getEyeHeight(ent.getPose());
        final double z = ent.getPos().getZ();
        return getRotationFromPosition(x, y, z);
    }

    public static float[] getRotationFromPosition(final double x, final double y, final double z) {
        final double xDiff = x - mc.player.getPos().getX();
        final double yDiff = y - (mc.player.getPos().getY() + mc.player.getEyeHeight(mc.player.getPose()));
        final double zDiff = z - mc.player.getPos().getZ();

        final double dist = MathHelper.hypot(xDiff, zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0f;
        final float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / Math.PI);

        return new float[]
            { yaw, pitch };
    }

    public static float getAngleDifference(final float dir, final float yaw) {
        final float f = Math.abs(yaw - dir) % 360;
        float dist = f;

        if(f > 180.0F) dist = 360.0F - f;

        return dist;
    }
}
