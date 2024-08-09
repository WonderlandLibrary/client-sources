/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;

public class MathUtils {
    public static final float PI = (float)Math.PI;
    public static final float PI2 = (float)Math.PI * 2;
    public static final float PId2 = 1.5707964f;
    private static final float[] ASIN_TABLE;

    public static float asin(float f) {
        return ASIN_TABLE[(int)((double)(f + 1.0f) * 32767.5) & 0xFFFF];
    }

    public static float acos(float f) {
        return 1.5707964f - ASIN_TABLE[(int)((double)(f + 1.0f) * 32767.5) & 0xFFFF];
    }

    public static int getAverage(int[] nArray) {
        if (nArray.length <= 0) {
            return 1;
        }
        int n = MathUtils.getSum(nArray);
        return n / nArray.length;
    }

    public static int getSum(int[] nArray) {
        if (nArray.length <= 0) {
            return 1;
        }
        int n = 0;
        for (int i = 0; i < nArray.length; ++i) {
            int n2 = nArray[i];
            n += n2;
        }
        return n;
    }

    public static int roundDownToPowerOfTwo(int n) {
        int n2 = MathHelper.smallestEncompassingPowerOfTwo(n);
        return n == n2 ? n2 : n2 / 2;
    }

    public static boolean equalsDelta(float f, float f2, float f3) {
        return Math.abs(f - f2) <= f3;
    }

    public static float toDeg(float f) {
        return f * 180.0f / MathHelper.PI;
    }

    public static float toRad(float f) {
        return f / 180.0f * MathHelper.PI;
    }

    public static float roundToFloat(double d) {
        return (float)((double)Math.round(d * 1.0E8) / 1.0E8);
    }

    public static double distanceSq(BlockPos blockPos, double d, double d2, double d3) {
        return MathUtils.distanceSq((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), d, d2, d3);
    }

    public static float distanceSq(BlockPos blockPos, float f, float f2, float f3) {
        return MathUtils.distanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ(), f, f2, f3);
    }

    public static double distanceSq(double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        return d7 * d7 + d8 * d8 + d9 * d9;
    }

    public static float distanceSq(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f - f4;
        float f8 = f2 - f5;
        float f9 = f3 - f6;
        return f7 * f7 + f8 * f8 + f9 * f9;
    }

    public static Matrix4f makeMatrixIdentity() {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.setIdentity();
        return matrix4f;
    }

    static {
        int n;
        ASIN_TABLE = new float[65536];
        for (n = 0; n < 65536; ++n) {
            MathUtils.ASIN_TABLE[n] = (float)Math.asin((double)n / 32767.5 - 1.0);
        }
        for (n = -1; n < 2; ++n) {
            MathUtils.ASIN_TABLE[(int)(((double)n + 1.0) * 32767.5) & 0xFFFF] = (float)Math.asin(n);
        }
    }
}

