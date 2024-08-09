/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3i;
import net.optifine.util.MathUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class MathHelper {
    public static final float SQRT_2;
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    private static final int SIN_COUNT_D4 = 1024;
    public static final float PI;
    public static final float PI2;
    public static final float PId2;
    private static final float radToIndex;
    public static final float deg2Rad;
    private static final float[] SIN_TABLE_FAST;
    public static boolean fastMath;
    private static final float[] SIN_TABLE;
    private static final Random RANDOM;
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION;
    private static final double FRAC_BIAS;
    private static final double[] ASINE_TAB;
    private static final double[] COS_TAB;

    public static float sin(float f) {
        return fastMath ? SIN_TABLE_FAST[(int)(f * radToIndex) & 0xFFF] : SIN_TABLE[(int)(f * 10430.378f) & 0xFFFF];
    }

    public static float cos(float f) {
        return fastMath ? SIN_TABLE_FAST[(int)(f * radToIndex + 1024.0f) & 0xFFF] : SIN_TABLE[(int)(f * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static double lengthSquared(double d, double d2) {
        return d * d + d2 * d2;
    }

    public static double length(double d, double d2) {
        return Math.sqrt(MathHelper.lengthSquared(d, d2));
    }

    public static double lengthSquared(double d, double d2, double d3) {
        return d * d + d2 * d2 + d3 * d3;
    }

    public static double length(double d, double d2, double d3) {
        return Math.sqrt(MathHelper.lengthSquared(d, d2, d3));
    }

    public static float square(float f) {
        return f * f;
    }

    public static double square(double d) {
        return d * d;
    }

    public static int square(int n) {
        return n * n;
    }

    public static long square(long l) {
        return l * l;
    }

    public static float sqrt(float f) {
        return (float)Math.sqrt(f);
    }

    public static float sqrt(double d) {
        return (float)Math.sqrt(d);
    }

    public static int floor(float f) {
        int n = (int)f;
        return f < (float)n ? n - 1 : n;
    }

    public static int fastFloor(double d) {
        return (int)(d + 1024.0) - 1024;
    }

    public static int floor(double d) {
        int n = (int)d;
        return d < (double)n ? n - 1 : n;
    }

    public static long lfloor(double d) {
        long l = (long)d;
        return d < (double)l ? l - 1L : l;
    }

    public static float abs(float f) {
        return Math.abs(f);
    }

    public static int abs(int n) {
        return Math.abs(n);
    }

    public static int ceil(float f) {
        int n = (int)f;
        return f > (float)n ? n + 1 : n;
    }

    public static int ceil(double d) {
        int n = (int)d;
        return d > (double)n ? n + 1 : n;
    }

    public static int clamp(int n, int n2, int n3) {
        if (n < n2) {
            return n2;
        }
        return n > n3 ? n3 : n;
    }

    public static long clamp(long l, long l2, long l3) {
        if (l < l2) {
            return l2;
        }
        return l > l3 ? l3 : l;
    }

    public static float clamp(float f, float f2, float f3) {
        if (f < f2) {
            return f2;
        }
        return f > f3 ? f3 : f;
    }

    public static double clamp(double d, double d2, double d3) {
        if (d < d2) {
            return d2;
        }
        return d > d3 ? d3 : d;
    }

    public static double clampedLerp(double d, double d2, double d3) {
        if (d3 < 0.0) {
            return d;
        }
        return d3 > 1.0 ? d2 : MathHelper.lerp(d3, d, d2);
    }

    public static double absMax(double d, double d2) {
        if (d < 0.0) {
            d = -d;
        }
        if (d2 < 0.0) {
            d2 = -d2;
        }
        return d > d2 ? d : d2;
    }

    public static int intFloorDiv(int n, int n2) {
        return Math.floorDiv(n, n2);
    }

    public static int nextInt(Random random2, int n, int n2) {
        return n >= n2 ? n : random2.nextInt(n2 - n + 1) + n;
    }

    public static float nextFloat(Random random2, float f, float f2) {
        return f >= f2 ? f : random2.nextFloat() * (f2 - f) + f;
    }

    public static double nextDouble(Random random2, double d, double d2) {
        return d >= d2 ? d : random2.nextDouble() * (d2 - d) + d;
    }

    public static double average(long[] lArray) {
        long l = 0L;
        for (long l2 : lArray) {
            l += l2;
        }
        return (double)l / (double)lArray.length;
    }

    public static boolean epsilonEquals(float f, float f2) {
        return Math.abs(f2 - f) < 1.0E-5f;
    }

    public static boolean epsilonEquals(double d, double d2) {
        return Math.abs(d2 - d) < (double)1.0E-5f;
    }

    public static int normalizeAngle(int n, int n2) {
        return Math.floorMod(n, n2);
    }

    public static float positiveModulo(float f, float f2) {
        return (f % f2 + f2) % f2;
    }

    public static double positiveModulo(double d, double d2) {
        return (d % d2 + d2) % d2;
    }

    public static int wrapDegrees(int n) {
        int n2 = n % 360;
        if (n2 >= 180) {
            n2 -= 360;
        }
        if (n2 < -180) {
            n2 += 360;
        }
        return n2;
    }

    public static float wrapDegrees(float f) {
        float f2 = f % 360.0f;
        if (f2 >= 180.0f) {
            f2 -= 360.0f;
        }
        if (f2 < -180.0f) {
            f2 += 360.0f;
        }
        return f2;
    }

    public static double wrapDegrees(double d) {
        double d2 = d % 360.0;
        if (d2 >= 180.0) {
            d2 -= 360.0;
        }
        if (d2 < -180.0) {
            d2 += 360.0;
        }
        return d2;
    }

    public static float wrapSubtractDegrees(float f, float f2) {
        return MathHelper.wrapDegrees(f2 - f);
    }

    public static float degreesDifferenceAbs(float f, float f2) {
        return MathHelper.abs(MathHelper.wrapSubtractDegrees(f, f2));
    }

    public static float func_219800_b(float f, float f2, float f3) {
        float f4 = MathHelper.wrapSubtractDegrees(f, f2);
        float f5 = MathHelper.clamp(f4, -f3, f3);
        return f2 - f5;
    }

    public static float approach(float f, float f2, float f3) {
        f3 = MathHelper.abs(f3);
        return f < f2 ? MathHelper.clamp(f + f3, f, f2) : MathHelper.clamp(f - f3, f2, f);
    }

    public static float approachDegrees(float f, float f2, float f3) {
        float f4 = MathHelper.wrapSubtractDegrees(f, f2);
        return MathHelper.approach(f, f + f4, f3);
    }

    public static int getInt(String string, int n) {
        return NumberUtils.toInt(string, n);
    }

    public static int smallestEncompassingPowerOfTwo(int n) {
        int n2 = n - 1;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return n2 + 1;
    }

    public static boolean isPowerOfTwo(int n) {
        return n != 0 && (n & n - 1) == 0;
    }

    public static int log2DeBruijn(int n) {
        n = MathHelper.isPowerOfTwo(n) ? n : MathHelper.smallestEncompassingPowerOfTwo(n);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)n * 125613361L >> 27) & 0x1F];
    }

    public static int log2(int n) {
        return MathHelper.log2DeBruijn(n) - (MathHelper.isPowerOfTwo(n) ? 0 : 1);
    }

    public static int roundUp(int n, int n2) {
        int n3;
        if (n2 == 0) {
            return 1;
        }
        if (n == 0) {
            return n2;
        }
        if (n < 0) {
            n2 *= -1;
        }
        return (n3 = n % n2) == 0 ? n : n + n2 - n3;
    }

    public static int rgb(float f, float f2, float f3) {
        return MathHelper.rgb(MathHelper.floor(f * 255.0f), MathHelper.floor(f2 * 255.0f), MathHelper.floor(f3 * 255.0f));
    }

    public static int rgb(int n, int n2, int n3) {
        int n4 = (n << 8) + n2;
        return (n4 << 8) + n3;
    }

    public static float frac(float f) {
        return f - (float)MathHelper.floor(f);
    }

    public static double frac(double d) {
        return d - (double)MathHelper.lfloor(d);
    }

    public static long getPositionRandom(Vector3i vector3i) {
        return MathHelper.getCoordinateRandom(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public static long getCoordinateRandom(int n, int n2, int n3) {
        long l = (long)(n * 3129871) ^ (long)n3 * 116129781L ^ (long)n2;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    public static UUID getRandomUUID(Random random2) {
        long l = random2.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
        long l2 = random2.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
        return new UUID(l, l2);
    }

    public static UUID getRandomUUID() {
        return MathHelper.getRandomUUID(RANDOM);
    }

    public static double func_233020_c_(double d, double d2, double d3) {
        return (d - d2) / (d3 - d2);
    }

    public static double atan2(double d, double d2) {
        double d3;
        boolean bl;
        boolean bl2;
        boolean bl3;
        double d4 = d2 * d2 + d * d;
        if (Double.isNaN(d4)) {
            return Double.NaN;
        }
        boolean bl4 = bl3 = d < 0.0;
        if (bl3) {
            d = -d;
        }
        boolean bl5 = bl2 = d2 < 0.0;
        if (bl2) {
            d2 = -d2;
        }
        boolean bl6 = bl = d > d2;
        if (bl) {
            d3 = d2;
            d2 = d;
            d = d3;
        }
        d3 = MathHelper.fastInvSqrt(d4);
        d2 *= d3;
        double d5 = FRAC_BIAS + (d *= d3);
        int n = (int)Double.doubleToRawLongBits(d5);
        double d6 = ASINE_TAB[n];
        double d7 = COS_TAB[n];
        double d8 = d5 - FRAC_BIAS;
        double d9 = d * d7 - d2 * d8;
        double d10 = (6.0 + d9 * d9) * d9 * 0.16666666666666666;
        double d11 = d6 + d10;
        if (bl) {
            d11 = 1.5707963267948966 - d11;
        }
        if (bl2) {
            d11 = Math.PI - d11;
        }
        if (bl3) {
            d11 = -d11;
        }
        return d11;
    }

    public static float fastInvSqrt(float f) {
        float f2 = 0.5f * f;
        int n = Float.floatToIntBits(f);
        n = 1597463007 - (n >> 1);
        f = Float.intBitsToFloat(n);
        return f * (1.5f - f2 * f * f);
    }

    public static double fastInvSqrt(double d) {
        double d2 = 0.5 * d;
        long l = Double.doubleToRawLongBits(d);
        l = 6910469410427058090L - (l >> 1);
        d = Double.longBitsToDouble(l);
        return d * (1.5 - d2 * d * d);
    }

    public static float fastInvCubeRoot(float f) {
        int n = Float.floatToIntBits(f);
        n = 1419967116 - n / 3;
        float f2 = Float.intBitsToFloat(n);
        f2 = 0.6666667f * f2 + 1.0f / (3.0f * f2 * f2 * f);
        return 0.6666667f * f2 + 1.0f / (3.0f * f2 * f2 * f);
    }

    public static int hsvToRGB(float f, float f2, float f3) {
        float f4;
        float f5;
        int n = (int)(f * 6.0f) % 6;
        float f6 = f * 6.0f - (float)n;
        float f7 = f3 * (1.0f - f2);
        float f8 = f3 * (1.0f - f6 * f2);
        float f9 = f3 * (1.0f - (1.0f - f6) * f2);
        float f10 = switch (n) {
            case 0 -> {
                f5 = f3;
                f4 = f9;
                yield f7;
            }
            case 1 -> {
                f5 = f8;
                f4 = f3;
                yield f7;
            }
            case 2 -> {
                f5 = f7;
                f4 = f3;
                yield f9;
            }
            case 3 -> {
                f5 = f7;
                f4 = f8;
                yield f3;
            }
            case 4 -> {
                f5 = f9;
                f4 = f7;
                yield f3;
            }
            case 5 -> {
                f5 = f3;
                f4 = f7;
                yield f8;
            }
            default -> throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + f + ", " + f2 + ", " + f3);
        };
        int n2 = MathHelper.clamp((int)(f5 * 255.0f), 0, 255);
        int n3 = MathHelper.clamp((int)(f4 * 255.0f), 0, 255);
        int n4 = MathHelper.clamp((int)(f10 * 255.0f), 0, 255);
        return n2 << 16 | n3 << 8 | n4;
    }

    public static int hash(int n) {
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        return (n *= -1028477387) ^ n >>> 16;
    }

    public static int binarySearch(int n, int n2, IntPredicate intPredicate) {
        int n3 = n2 - n;
        while (n3 > 0) {
            int n4 = n3 / 2;
            int n5 = n + n4;
            if (intPredicate.test(n5)) {
                n3 = n4;
                continue;
            }
            n = n5 + 1;
            n3 -= n4 + 1;
        }
        return n;
    }

    public static float lerp(float f, float f2, float f3) {
        return f2 + f * (f3 - f2);
    }

    public static double lerp(double d, double d2, double d3) {
        return d2 + d * (d3 - d2);
    }

    public static double lerp2(double d, double d2, double d3, double d4, double d5, double d6) {
        return MathHelper.lerp(d2, MathHelper.lerp(d, d3, d4), MathHelper.lerp(d, d5, d6));
    }

    public static double lerp3(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11) {
        return MathHelper.lerp(d3, MathHelper.lerp2(d, d2, d4, d5, d6, d7), MathHelper.lerp2(d, d2, d8, d9, d10, d11));
    }

    public static double perlinFade(double d) {
        return d * d * d * (d * (d * 6.0 - 15.0) + 10.0);
    }

    public static int signum(double d) {
        if (d == 0.0) {
            return 1;
        }
        return d > 0.0 ? 1 : -1;
    }

    public static float interpolateAngle(float f, float f2, float f3) {
        return f2 + f * MathHelper.wrapDegrees(f3 - f2);
    }

    @Deprecated
    public static float rotLerp(float f, float f2, float f3) {
        float f4;
        for (f4 = f2 - f; f4 < -180.0f; f4 += 360.0f) {
        }
        while (f4 >= 180.0f) {
            f4 -= 360.0f;
        }
        return f + f3 * f4;
    }

    @Deprecated
    public static float rotWrap(double d) {
        while (d >= 180.0) {
            d -= 360.0;
        }
        while (d < -180.0) {
            d += 360.0;
        }
        return (float)d;
    }

    public static float func_233021_e_(float f, float f2) {
        return (Math.abs(f % f2 - f2 * 0.5f) - f2 * 0.25f) / (f2 * 0.25f);
    }

    public static float squareFloat(float f) {
        return f * f;
    }

    private static void lambda$static$0(float[] fArray) {
        for (int i = 0; i < fArray.length; ++i) {
            fArray[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
        }
    }

    static {
        int n;
        SQRT_2 = MathHelper.sqrt(2.0f);
        PI = MathUtils.roundToFloat(Math.PI);
        PI2 = MathUtils.roundToFloat(Math.PI * 2);
        PId2 = MathUtils.roundToFloat(1.5707963267948966);
        radToIndex = MathUtils.roundToFloat(651.8986469044033);
        deg2Rad = MathUtils.roundToFloat(Math.PI / 180);
        SIN_TABLE_FAST = new float[4096];
        fastMath = false;
        SIN_TABLE = Util.make(new float[65536], MathHelper::lambda$static$0);
        RANDOM = new Random();
        MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
        FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
        ASINE_TAB = new double[257];
        COS_TAB = new double[257];
        for (n = 0; n < 257; ++n) {
            double d = (double)n / 256.0;
            double d2 = Math.asin(d);
            MathHelper.COS_TAB[n] = Math.cos(d2);
            MathHelper.ASINE_TAB[n] = d2;
        }
        for (n = 0; n < SIN_TABLE_FAST.length; ++n) {
            MathHelper.SIN_TABLE_FAST[n] = MathUtils.roundToFloat(Math.sin((double)n * Math.PI * 2.0 / 4096.0));
        }
    }
}

