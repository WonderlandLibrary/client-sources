package net.minecraft.realms;

import java.util.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;

public class RealmsMth
{
    public static int nextInt(final Random random, final int n, final int n2) {
        return MathHelper.getRandomIntegerInRange(random, n, n2);
    }
    
    public static int smallestEncompassingPowerOfTwo(final int n) {
        return MathHelper.roundUpToPowerOfTwo(n);
    }
    
    public static float abs(final float n) {
        return MathHelper.abs(n);
    }
    
    public static float nextFloat(final Random random, final float n, final float n2) {
        return MathHelper.randomFloatClamp(random, n, n2);
    }
    
    public static float clamp(final float n, final float n2, final float n3) {
        return MathHelper.clamp_float(n, n2, n3);
    }
    
    public static int fastFloor(final double n) {
        return MathHelper.truncateDoubleToInt(n);
    }
    
    public static int ceil(final float n) {
        return MathHelper.ceiling_float_int(n);
    }
    
    public static int getInt(final String s, final int n, final int n2) {
        return MathHelper.parseIntWithDefaultAndMax(s, n, n2);
    }
    
    public static float wrapDegrees(final float n) {
        return MathHelper.wrapAngleTo180_float(n);
    }
    
    public static float cos(final float n) {
        return MathHelper.cos(n);
    }
    
    public static double clampedLerp(final double n, final double n2, final double n3) {
        return MathHelper.denormalizeClamp(n, n2, n3);
    }
    
    public static int abs(final int n) {
        return MathHelper.abs_int(n);
    }
    
    public static int floor(final double n) {
        return MathHelper.floor_double(n);
    }
    
    public static double average(final long[] array) {
        return MathHelper.average(array);
    }
    
    public static int getInt(final String s, final int n) {
        return MathHelper.parseIntWithDefault(s, n);
    }
    
    public static int absFloor(final double n) {
        return MathHelper.func_154353_e(n);
    }
    
    public static boolean isEmpty(final String s) {
        return StringUtils.isEmpty((CharSequence)s);
    }
    
    public static long lfloor(final double n) {
        return MathHelper.floor_double_long(n);
    }
    
    public static double absMax(final double n, final double n2) {
        return MathHelper.abs_max(n, n2);
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static double clamp(final double n, final double n2, final double n3) {
        return MathHelper.clamp_double(n, n2, n3);
    }
    
    public static double getDouble(final String s, final double n, final double n2) {
        return MathHelper.parseDoubleWithDefaultAndMax(s, n, n2);
    }
    
    public static int floor(final float n) {
        return MathHelper.floor_float(n);
    }
    
    public static double getDouble(final String s, final double n) {
        return MathHelper.parseDoubleWithDefault(s, n);
    }
    
    public static float sqrt(final double n) {
        return MathHelper.sqrt_double(n);
    }
    
    public static int log2(final int n) {
        return MathHelper.calculateLogBaseTwo(n);
    }
    
    public static int roundUp(final int n, final int n2) {
        return MathHelper.func_154354_b(n, n2);
    }
    
    public static double wrapDegrees(final double n) {
        return MathHelper.wrapAngleTo180_double(n);
    }
    
    public static int intFloorDiv(final int n, final int n2) {
        return MathHelper.bucketInt(n, n2);
    }
    
    public static int ceil(final double n) {
        return MathHelper.ceiling_double_int(n);
    }
    
    public static int clamp(final int n, final int n2, final int n3) {
        return MathHelper.clamp_int(n, n2, n3);
    }
    
    public static double nextDouble(final Random random, final double n, final double n2) {
        return MathHelper.getRandomDoubleInRange(random, n, n2);
    }
    
    public static float sin(final float n) {
        return MathHelper.sin(n);
    }
    
    public static float sqrt(final float n) {
        return MathHelper.sqrt_float(n);
    }
}
