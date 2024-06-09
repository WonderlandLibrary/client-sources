// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import java.util.Random;
import java.math.MathContext;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.security.SecureRandom;

public class MathUtils
{
    public static SecureRandom RANDOM;
    
    public static float lerp(final float a, final float b, final float c) {
        return a + c * (b - a);
    }
    
    public static double round(final double value, final int scale, final double inc) {
        final double halfOfInc = inc / 2.0;
        final double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc).setScale(scale, RoundingMode.HALF_UP).doubleValue();
        }
        return new BigDecimal(floored).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double roundToDecimalPlace(final double value, final double inc) {
        final double halfOfInc = inc / 2.0;
        final double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        }
        return new BigDecimal(floored, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }
    
    public static double round(final double num, final double increment) {
        if (increment < 0.0) {
            throw new IllegalArgumentException();
        }
        final BigDecimal bd = new BigDecimal(num);
        return bd.setScale((int)increment, RoundingMode.HALF_UP).doubleValue();
    }
    
    public static double getRandomInRange(final double a, final double b) {
        if (a > b) {
            return a;
        }
        return Math.random() * (b - a) + a;
    }
    
    public static int getRandomInRange(final int a, final int b) {
        if (a > b) {
            return a;
        }
        return new Random().nextInt(b) + a;
    }
    
    public static byte getRandomInRange(final byte a, final byte b) {
        if (a > b) {
            return a;
        }
        return (byte)(new Random().nextInt(b) + a);
    }
    
    public static byte[] getRandomBytes(final int minSize, final int maxSize, final byte min, final byte max) {
        final int size = getRandomInRange(minSize, maxSize);
        final byte[] out = new byte[size];
        for (int i = 0; i < size; ++i) {
            out[i] = getRandomInRange(min, max);
        }
        return out;
    }
    
    public static float calculateGaussianValue(final float a, final float b) {
        final double output = 1.0 / Math.sqrt(6.283185307179586 * (b * b));
        return (float)(output * Math.exp(-(a * a) / (2.0 * (b * b))));
    }
    
    public static double roundToHalf(final double d) {
        return Math.round(d * 2.0) / 2.0;
    }
    
    public static double getDifference(final double base, final double yaw) {
        double bigger;
        if (base >= yaw) {
            bigger = base - yaw;
        }
        else {
            bigger = yaw - base;
        }
        return bigger;
    }
    
    public static float getDifference(final float base, final float yaw) {
        float bigger;
        if (base >= yaw) {
            bigger = base - yaw;
        }
        else {
            bigger = yaw - base;
        }
        return bigger;
    }
    
    public static long getDifference(final long base, final long yaw) {
        long bigger;
        if (base >= yaw) {
            bigger = base - yaw;
        }
        else {
            bigger = yaw - base;
        }
        return bigger;
    }
    
    static {
        MathUtils.RANDOM = new SecureRandom();
    }
}
