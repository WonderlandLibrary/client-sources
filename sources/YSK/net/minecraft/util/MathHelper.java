package net.minecraft.util;

import java.util.*;

public class MathHelper
{
    public static final float PI = 3.1415927f;
    private static final float degToIndex = 11.377778f;
    public static final float PId2 = 1.5707964f;
    private static final float degFull = 360.0f;
    public static final float deg2Rad = 0.017453292f;
    private static final double field_181163_d;
    private static final int SIN_MASK;
    private static final int[] multiplyDeBruijnBitPosition;
    private static final float[] SIN_TABLE;
    private static final float[] SIN_TABLE_FAST;
    private static final double[] field_181165_f;
    private static final double[] field_181164_e;
    private static final String __OBFID;
    private static final float radFull = 6.2831855f;
    public static boolean fastMath;
    public static final float SQRT_2;
    public static final float PI2 = 6.2831855f;
    private static final int SIN_BITS;
    private static final float radToIndex = 651.8986f;
    private static final int SIN_COUNT;
    private static final String[] I;
    
    public static double parseDoubleWithDefaultAndMax(final String s, final double n, final double n2) {
        return Math.max(n2, parseDoubleWithDefault(s, n));
    }
    
    public static float sqrt_float(final float n) {
        return (float)Math.sqrt(n);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int truncateDoubleToInt(final double n) {
        return (int)(n + 1024.0) - (806 + 871 - 875 + 222);
    }
    
    public static long getPositionRandom(final Vec3i vec3i) {
        return getCoordinateRandom(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }
    
    public static float clamp_float(final float n, final float n2, final float n3) {
        float n4;
        if (n < n2) {
            n4 = n2;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (n > n3) {
            n4 = n3;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        return n4;
    }
    
    public static int floor_double(final double n) {
        final int n2 = (int)n;
        int n3;
        if (n < n2) {
            n3 = n2 - " ".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    static {
        I();
        SIN_MASK = 3505 + 916 - 3899 + 3573;
        SIN_COUNT = 2098 + 1638 - 724 + 1084;
        __OBFID = MathHelper.I["".length()];
        SIN_BITS = (0x46 ^ 0x4A);
        SQRT_2 = sqrt_float(2.0f);
        SIN_TABLE_FAST = new float[1899 + 2807 - 2451 + 1841];
        MathHelper.fastMath = ("".length() != 0);
        SIN_TABLE = new float[9405 + 3054 + 7956 + 45121];
        int i = "".length();
        "".length();
        if (0 >= 3) {
            throw null;
        }
        while (i < 60955 + 39240 - 81504 + 46845) {
            MathHelper.SIN_TABLE[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
            ++i;
        }
        int j = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (j < 3091 + 1993 - 2026 + 1038) {
            MathHelper.SIN_TABLE_FAST[j] = (float)Math.sin((j + 0.5f) / 4096.0f * 6.2831855f);
            ++j;
        }
        int k = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (k < 60 + 98 + 149 + 53) {
            MathHelper.SIN_TABLE_FAST[(int)(k * 11.377778f) & 891 + 2614 + 307 + 283] = (float)Math.sin(k * 0.017453292f);
            k += 90;
        }
        final int[] multiplyDeBruijnBitPosition2 = new int[0xE5 ^ 0xC5];
        multiplyDeBruijnBitPosition2[" ".length()] = " ".length();
        multiplyDeBruijnBitPosition2["  ".length()] = (0x70 ^ 0x6C);
        multiplyDeBruijnBitPosition2["   ".length()] = "  ".length();
        multiplyDeBruijnBitPosition2[0x7D ^ 0x79] = (0x6 ^ 0x1B);
        multiplyDeBruijnBitPosition2[0x45 ^ 0x40] = (0xB5 ^ 0xBB);
        multiplyDeBruijnBitPosition2[0x24 ^ 0x22] = (0x12 ^ 0xA);
        multiplyDeBruijnBitPosition2[0x76 ^ 0x71] = "   ".length();
        multiplyDeBruijnBitPosition2[0x1 ^ 0x9] = (0x2F ^ 0x31);
        multiplyDeBruijnBitPosition2[0x33 ^ 0x3A] = (0x92 ^ 0x84);
        multiplyDeBruijnBitPosition2[0xCF ^ 0xC5] = (0x82 ^ 0x96);
        multiplyDeBruijnBitPosition2[0x88 ^ 0x83] = (0x62 ^ 0x6D);
        multiplyDeBruijnBitPosition2[0x17 ^ 0x1B] = (0x55 ^ 0x4C);
        multiplyDeBruijnBitPosition2[0xE ^ 0x3] = (0x4E ^ 0x5F);
        multiplyDeBruijnBitPosition2[0x94 ^ 0x9A] = (0x8C ^ 0x88);
        multiplyDeBruijnBitPosition2[0x69 ^ 0x66] = (0x1A ^ 0x12);
        multiplyDeBruijnBitPosition2[0xA4 ^ 0xB4] = (0x2B ^ 0x34);
        multiplyDeBruijnBitPosition2[0x19 ^ 0x8] = (0x87 ^ 0x9C);
        multiplyDeBruijnBitPosition2[0x8C ^ 0x9E] = (0xAA ^ 0xA7);
        multiplyDeBruijnBitPosition2[0x7D ^ 0x6E] = (0x84 ^ 0x93);
        multiplyDeBruijnBitPosition2[0xD3 ^ 0xC7] = (0x6E ^ 0x7B);
        multiplyDeBruijnBitPosition2[0x5E ^ 0x4B] = (0x6F ^ 0x7C);
        multiplyDeBruijnBitPosition2[0xA8 ^ 0xBE] = (0x6B ^ 0x7B);
        multiplyDeBruijnBitPosition2[0x21 ^ 0x36] = (0x7C ^ 0x7B);
        multiplyDeBruijnBitPosition2[0x64 ^ 0x7C] = (0x2D ^ 0x37);
        multiplyDeBruijnBitPosition2[0x88 ^ 0x91] = (0xB ^ 0x7);
        multiplyDeBruijnBitPosition2[0x3A ^ 0x20] = (0xB4 ^ 0xA6);
        multiplyDeBruijnBitPosition2[0x2F ^ 0x34] = (0x2F ^ 0x29);
        multiplyDeBruijnBitPosition2[0xA5 ^ 0xB9] = (0x35 ^ 0x3E);
        multiplyDeBruijnBitPosition2[0x4D ^ 0x50] = (0x24 ^ 0x21);
        multiplyDeBruijnBitPosition2[0x60 ^ 0x7E] = (0x80 ^ 0x8A);
        multiplyDeBruijnBitPosition2[0x0 ^ 0x1F] = (0x1C ^ 0x15);
        multiplyDeBruijnBitPosition = multiplyDeBruijnBitPosition2;
        field_181163_d = Double.longBitsToDouble(4805340802404319232L);
        field_181164_e = new double[223 + 107 - 76 + 3];
        field_181165_f = new double[226 + 54 - 107 + 84];
        int l = "".length();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (l < 207 + 115 - 266 + 201) {
            final double asin = Math.asin(l / 256.0);
            MathHelper.field_181165_f[l] = Math.cos(asin);
            MathHelper.field_181164_e[l] = asin;
            ++l;
        }
    }
    
    public static double func_181162_h(final double n) {
        return n - Math.floor(n);
    }
    
    private static boolean isPowerOfTwo(final int n) {
        if (n != 0 && (n & n - " ".length()) == 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private static int calculateLogBaseTwoDeBruijn(int n) {
        int roundUpToPowerOfTwo;
        if (isPowerOfTwo(n)) {
            roundUpToPowerOfTwo = n;
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            roundUpToPowerOfTwo = roundUpToPowerOfTwo(n);
        }
        n = roundUpToPowerOfTwo;
        return MathHelper.multiplyDeBruijnBitPosition[(int)(n * 125613361L >> (0x25 ^ 0x3E)) & (0x10 ^ 0xF)];
    }
    
    public static float wrapAngleTo180_float(float n) {
        n %= 360.0f;
        if (n >= 180.0f) {
            n -= 360.0f;
        }
        if (n < -180.0f) {
            n += 360.0f;
        }
        return n;
    }
    
    private static void I() {
        (I = new String[0xB2 ^ 0xB6])["".length()] = I(" >\u0010VZSB~RSU", "crOfj");
        MathHelper.I[" ".length()] = I("\"\"7\u001d\"\u0019$4\u001fv\u0006(4\fv\u0006?5\u00161Q:2\u001d8Q.5\u0016 \u0014?.\u00118\u0016m<\n9\u001cm\u0012+\u0000Q95X\u00046\u000ftX\u001f\u001f=/\fv\u0006,)X", "qMZxV");
        MathHelper.I["  ".length()] = I("`g", "LGmmJ");
        MathHelper.I["   ".length()] = I("|W", "PwaAH");
    }
    
    public static float sqrt_double(final double n) {
        return (float)Math.sqrt(n);
    }
    
    public static double getRandomDoubleInRange(final Random random, final double n, final double n2) {
        double n3;
        if (n >= n2) {
            n3 = n;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            n3 = random.nextDouble() * (n2 - n) + n;
        }
        return n3;
    }
    
    public static double denormalizeClamp(final double n, final double n2, final double n3) {
        double n4;
        if (n3 < 0.0) {
            n4 = n;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (n3 > 1.0) {
            n4 = n2;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n4 = n + (n2 - n) * n3;
        }
        return n4;
    }
    
    public static int clamp_int(final int n, final int n2, final int n3) {
        int n4;
        if (n < n2) {
            n4 = n2;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (n > n3) {
            n4 = n3;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        return n4;
    }
    
    public static double abs_max(double n, double n2) {
        if (n < 0.0) {
            n = -n;
        }
        if (n2 < 0.0) {
            n2 = -n2;
        }
        double n3;
        if (n > n2) {
            n3 = n;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    public static int roundUpToPowerOfTwo(final int n) {
        final int n2 = n - " ".length();
        final int n3 = n2 | n2 >> " ".length();
        final int n4 = n3 | n3 >> "  ".length();
        final int n5 = n4 | n4 >> (0xBB ^ 0xBF);
        final int n6 = n5 | n5 >> (0x77 ^ 0x7F);
        return (n6 | n6 >> (0xD2 ^ 0xC2)) + " ".length();
    }
    
    public static long floor_double_long(final double n) {
        final long n2 = (long)n;
        long n3;
        if (n < n2) {
            n3 = n2 - 1L;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    public static float abs(final float n) {
        float n2;
        if (n >= 0.0f) {
            n2 = n;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n2 = -n;
        }
        return n2;
    }
    
    public static long getCoordinateRandom(final int n, final int n2, final int n3) {
        final long n4 = n * (2753237 + 1150641 - 2984996 + 2210989) ^ n3 * 116129781L ^ n2;
        return n4 * n4 * 42317861L + n4 * 11L;
    }
    
    public static int ceiling_double_int(final double n) {
        final int n2 = (int)n;
        int n3;
        if (n > n2) {
            n3 = n2 + " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    public static float sin(final float n) {
        float n2;
        if (MathHelper.fastMath) {
            n2 = MathHelper.SIN_TABLE_FAST[(int)(n * 651.8986f) & 1181 + 2675 - 3027 + 3266];
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            n2 = MathHelper.SIN_TABLE[(int)(n * 10430.378f) & 33427 + 21569 - 12355 + 22894];
        }
        return n2;
    }
    
    public static int func_181758_c(final float n, final float n2, final float n3) {
        final int n4 = (int)(n * 6.0f) % (0xB3 ^ 0xB5);
        final float n5 = n * 6.0f - n4;
        final float n6 = n3 * (1.0f - n2);
        final float n7 = n3 * (1.0f - n5 * n2);
        final float n8 = n3 * (1.0f - (1.0f - n5) * n2);
        float n9 = 0.0f;
        float n10 = 0.0f;
        float n11 = 0.0f;
        switch (n4) {
            case 0: {
                n9 = n3;
                n10 = n8;
                n11 = n6;
                "".length();
                if (true != true) {
                    throw null;
                }
                break;
            }
            case 1: {
                n9 = n7;
                n10 = n3;
                n11 = n6;
                "".length();
                if (4 < -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                n9 = n6;
                n10 = n3;
                n11 = n8;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 3: {
                n9 = n6;
                n10 = n7;
                n11 = n3;
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                break;
            }
            case 4: {
                n9 = n8;
                n10 = n6;
                n11 = n3;
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 5: {
                n9 = n3;
                n10 = n6;
                n11 = n7;
                "".length();
                if (4 < 0) {
                    throw null;
                }
                break;
            }
            default: {
                throw new RuntimeException(MathHelper.I[" ".length()] + n + MathHelper.I["  ".length()] + n2 + MathHelper.I["   ".length()] + n3);
            }
        }
        return clamp_int((int)(n9 * 255.0f), "".length(), 140 + 224 - 339 + 230) << (0x88 ^ 0x98) | clamp_int((int)(n10 * 255.0f), "".length(), 248 + 41 - 157 + 123) << (0x3F ^ 0x37) | clamp_int((int)(n11 * 255.0f), "".length(), 58 + 103 + 75 + 19);
    }
    
    public static int func_154353_e(final double n) {
        double n2;
        if (n >= 0.0) {
            n2 = n;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n2 = -n + 1.0;
        }
        return (int)n2;
    }
    
    public static int parseIntWithDefaultAndMax(final String s, final int n, final int n2) {
        return Math.max(n2, parseIntWithDefault(s, n));
    }
    
    public static double func_181161_i(double longBitsToDouble) {
        final double n = 0.5 * longBitsToDouble;
        longBitsToDouble = Double.longBitsToDouble(6910469410427058090L - (Double.doubleToRawLongBits(longBitsToDouble) >> " ".length()));
        longBitsToDouble *= 1.5 - n * longBitsToDouble * longBitsToDouble;
        return longBitsToDouble;
    }
    
    public static double clamp_double(final double n, final double n2, final double n3) {
        double n4;
        if (n < n2) {
            n4 = n2;
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else if (n > n3) {
            n4 = n3;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n4 = n;
        }
        return n4;
    }
    
    public static int floor_float(final float n) {
        final int n2 = (int)n;
        int n3;
        if (n < n2) {
            n3 = n2 - " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    public static float cos(final float n) {
        float n2;
        if (MathHelper.fastMath) {
            n2 = MathHelper.SIN_TABLE_FAST[(int)((n + 1.5707964f) * 651.8986f) & 2452 + 1706 - 956 + 893];
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n2 = MathHelper.SIN_TABLE[(int)(n * 10430.378f + 16384.0f) & 25893 + 17059 - 40001 + 62584];
        }
        return n2;
    }
    
    public static double func_181159_b(double n, double n2) {
        final double n3 = n2 * n2 + n * n;
        if (Double.isNaN(n3)) {
            return Double.NaN;
        }
        int n4;
        if (n < 0.0) {
            n4 = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        if (n5 != 0) {
            n = -n;
        }
        int n6;
        if (n2 < 0.0) {
            n6 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n6 = "".length();
        }
        final int n7 = n6;
        if (n7 != 0) {
            n2 = -n2;
        }
        int n8;
        if (n > n2) {
            n8 = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n8 = "".length();
        }
        final int n9 = n8;
        if (n9 != 0) {
            final double n10 = n2;
            n2 = n;
            n = n10;
        }
        final double func_181161_i = func_181161_i(n3);
        n2 *= func_181161_i;
        n *= func_181161_i;
        final double n11 = MathHelper.field_181163_d + n;
        final int n12 = (int)Double.doubleToRawLongBits(n11);
        final double n13 = MathHelper.field_181164_e[n12];
        final double n14 = n * MathHelper.field_181165_f[n12] - n2 * (n11 - MathHelper.field_181163_d);
        double n15 = n13 + (6.0 + n14 * n14) * n14 * 0.16666666666666666;
        if (n9 != 0) {
            n15 = 1.5707963267948966 - n15;
        }
        if (n7 != 0) {
            n15 = 3.141592653589793 - n15;
        }
        if (n5 != 0) {
            n15 = -n15;
        }
        return n15;
    }
    
    public static boolean epsilonEquals(final float n, final float n2) {
        if (abs(n2 - n) < 1.0E-5f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static UUID getRandomUuid(final Random random) {
        return new UUID((random.nextLong() & 0xFFFFFFFFFFFF0FFFL) | 0x4000L, (random.nextLong() & 0x3FFFFFFFFFFFFFFFL) | Long.MIN_VALUE);
    }
    
    public static int normalizeAngle(final int n, final int n2) {
        return (n % n2 + n2) % n2;
    }
    
    public static double func_181160_c(final double n, final double n2, final double n3) {
        return (n - n2) / (n3 - n2);
    }
    
    public static int calculateLogBaseTwo(final int n) {
        final int calculateLogBaseTwoDeBruijn = calculateLogBaseTwoDeBruijn(n);
        int n2;
        if (isPowerOfTwo(n)) {
            n2 = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return calculateLogBaseTwoDeBruijn - n2;
    }
    
    public static int abs_int(final int n) {
        int n2;
        if (n >= 0) {
            n2 = n;
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n2 = -n;
        }
        return n2;
    }
    
    public static int func_154354_b(final int n, int n2) {
        if (n2 == 0) {
            return "".length();
        }
        if (n == 0) {
            return n2;
        }
        if (n < 0) {
            n2 *= -" ".length();
        }
        final int n3 = n % n2;
        int n4;
        if (n3 == 0) {
            n4 = n;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n4 = n + n2 - n3;
        }
        return n4;
    }
    
    public static int ceiling_float_int(final float n) {
        final int n2 = (int)n;
        int n3;
        if (n > n2) {
            n3 = n2 + " ".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            n3 = n2;
        }
        return n3;
    }
    
    public static double average(final long[] array) {
        long n = 0L;
        final int length = array.length;
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < length) {
            n += array[i];
            ++i;
        }
        return n / array.length;
    }
    
    public static int bucketInt(final int n, final int n2) {
        int n3;
        if (n < 0) {
            n3 = -((-n - " ".length()) / n2) - " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n3 = n / n2;
        }
        return n3;
    }
    
    public static int func_180188_d(final int n, final int n2) {
        return (n & -(5355421 + 5610353 - 3867327 + 9678769)) | (int)(((n & 7704498 + 12602024 - 11887987 + 8293145) >> (0x98 ^ 0x88)) * ((n2 & 12162362 + 118437 - 2924043 + 7354924) >> (0x4C ^ 0x5C)) / 255.0f) << (0xBA ^ 0xAA) | (int)(((n & 49088 + 18205 - 39063 + 37050) >> (0x18 ^ 0x10)) * ((n2 & 33187 + 40 - 2505 + 34558) >> (0x8E ^ 0x86)) / 255.0f) << (0x2A ^ 0x22) | (int)(((n & 67 + 13 - 38 + 213) >> "".length()) * ((n2 & 232 + 231 - 455 + 247) >> "".length()) / 255.0f);
    }
    
    public static int parseIntWithDefault(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Throwable t) {
            return n;
        }
    }
    
    public static float randomFloatClamp(final Random random, final float n, final float n2) {
        float n3;
        if (n >= n2) {
            n3 = n;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n3 = random.nextFloat() * (n2 - n) + n;
        }
        return n3;
    }
    
    public static int getRandomIntegerInRange(final Random random, final int n, final int n2) {
        int n3;
        if (n >= n2) {
            n3 = n;
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n3 = random.nextInt(n2 - n + " ".length()) + n;
        }
        return n3;
    }
    
    public static double wrapAngleTo180_double(double n) {
        n %= 360.0;
        if (n >= 180.0) {
            n -= 360.0;
        }
        if (n < -180.0) {
            n += 360.0;
        }
        return n;
    }
    
    public static int func_180183_b(final float n, final float n2, final float n3) {
        return func_180181_b(floor_float(n * 255.0f), floor_float(n2 * 255.0f), floor_float(n3 * 255.0f));
    }
    
    public static int func_180181_b(final int n, final int n2, final int n3) {
        return ((n << (0x1D ^ 0x15)) + n2 << (0x77 ^ 0x7F)) + n3;
    }
    
    public static double parseDoubleWithDefault(final String s, final double n) {
        try {
            return Double.parseDouble(s);
        }
        catch (Throwable t) {
            return n;
        }
    }
}
