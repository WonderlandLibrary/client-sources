/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.util.math;

import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class MathUtil {

    public static float boxMuellerDistribution(Random random, float min, float max, float mean, float sigma) {
        float u1, u2;
        float z0;

        do {
            u1 = random.nextFloat();
            u2 = random.nextFloat();
            z0 = MathHelper.sqrt(-2.0F * (float) Math.log(u1)) * MathHelper.cos(MathHelper.TAU * u2);
        } while ((int) (z0 * sigma + mean) < min || (int) (z0 * sigma + mean) > max);

        return mean + sigma * z0;
    }

    public static Vec3d clamp(Vec3d vec, Box box) {
        return new Vec3d(
                MathHelper.clamp(vec.x, box.minX, box.maxX),
                MathHelper.clamp(vec.y, box.minY, box.maxY),
                MathHelper.clamp(vec.z, box.minZ, box.maxZ)
        );
    }

    public static void getRotations(Vec3d from, Vec3d to, float[] rotations) {
        Vec3d delta = from.subtract(to);
        rotations[0] = MathHelper.wrapDegrees((float) Math.toDegrees(Math.atan2(delta.x, -delta.z)));
        rotations[1] = (float) Math.toDegrees(Math.atan2(delta.y, delta.horizontalLength()));
        rotations[1] = MathHelper.wrapDegrees(rotations[1]);
        if (rotations[1] > 90) {
            rotations[0] += 180;
            rotations[1] = 180 - rotations[1];
        } else if (rotations[1] < -90) {
            rotations[0] += 180;
            rotations[1] = 180 + rotations[1];
        }
    }

}
