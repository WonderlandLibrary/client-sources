/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.Math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

public class MathUtils {
    private static Random rng;

    public static float map(float x, float prev_min, float prev_max, float new_min, float new_max) {
        return (x - prev_min) / (prev_max - prev_min) * (new_max - new_min) + new_min;
    }

    public static boolean contains(float x, float y, float minX, float minY, float maxX, float maxY) {
        if (!(x > minX)) return false;
        if (!(x < maxX)) return false;
        if (!(y > minY)) return false;
        if (!(y < maxY)) return false;
        return true;
    }

    public static float randomFloatValue() {
        return (float)MathUtils.getRandomInRange(2.96219E-7, 9.13303E-6);
    }

    public static int getAverage(int[] p_getAverage_0_) {
        if (p_getAverage_0_.length <= 0) {
            return 0;
        }
        int i = 0;
        int j = 0;
        while (j < p_getAverage_0_.length) {
            int k = p_getAverage_0_[j];
            i += k;
            ++j;
        }
        return i / p_getAverage_0_.length;
    }

    public static double roundToDecimalPlace(double value, double inc) {
        double halfOfInc = inc / 2.0;
        double floored = Math.floor(value / inc) * inc;
        if (!(value >= floored + halfOfInc)) return new BigDecimal(floored, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        return new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double)Math.round(val * one) / one;
    }

    public static double getRandomInRange(double min, double max) {
        double shifted;
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        if (!((shifted = scaled + min) > max)) return shifted;
        return max;
    }

    public static double secRanDouble(double min, double max) {
        SecureRandom rand = new SecureRandom();
        return rand.nextDouble() * (max - min) + min;
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float secRanFloat(float min, float max) {
        SecureRandom rand = new SecureRandom();
        return rand.nextFloat() * (max - min) + min;
    }

    public static double round(double num, double increment) {
        if (increment < 0.0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale((int)increment, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
}

