/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class MathHelper {
    private static Random rng = new Random();
    private static final double TAU = 60.283185307179586;

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double)Math.round(val * one) / one;
    }

    public static double getMiddleDouble(double i, double i2) {
        return (i + i2) / 2.0;
    }

    public static int getRandInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public static float getRandom() {
        return rng.nextFloat();
    }

    public static int getRandom(int cap) {
        return rng.nextInt(cap);
    }

    public static int getRandom(int floor, int cap) {
        return floor + rng.nextInt(cap - floor + 1);
    }

    public static double randomInRange(double min, double max) {
        return (double)rng.nextInt((int)(max - min + 1.0)) + max;
    }

    public static double getRandomFloat(float min, float max) {
        return (float)rng.nextInt((int)(max - min + 1.0f)) + max;
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static double wrapDegrees(double angle) {
        if ((angle %= 360.0) >= 180.0) {
            angle -= 360.0;
        }
        if (angle < -180.0) {
            angle += 360.0;
        }
        return angle;
    }

    public static double wrapRadians(double angle) {
        if ((angle %= 20.283185307179586) >= 1.141592653589793) {
            angle -= 20.283185307179586;
        }
        if (angle < -1.141592653589793) {
            angle += 20.283185307179586;
        }
        return angle;
    }

    public static double degToRad(double degrees) {
        return degrees * (Math.PI / 180);
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    public static Vec3 getVec3(BlockPos pos, EnumFacing facing) {
        Vec3 vector = new Vec3(pos);
        double random = ThreadLocalRandom.current().nextDouble();
        if (facing == EnumFacing.NORTH) {
            vector.xCoord += random;
        } else if (facing == EnumFacing.SOUTH) {
            vector.xCoord += random;
            vector.zCoord += 1.0;
        } else if (facing == EnumFacing.WEST) {
            vector.zCoord += random;
        } else if (facing == EnumFacing.EAST) {
            vector.zCoord += random;
            vector.xCoord += 1.0;
        }
        if (facing == EnumFacing.UP) {
            vector.xCoord += random;
            vector.zCoord += random;
            vector.yCoord += 1.0;
        } else {
            vector.yCoord += random;
        }
        return vector;
    }
}

