package net.optifine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;

public class MathUtils {
    private static Random rng;
    public static int getAverage(int[] p_getAverage_0_) {
        if (p_getAverage_0_.length <= 0) {
            return 0;
        } else {
            int i = 0;

            for (int j = 0; j < p_getAverage_0_.length; ++j) {
                int k = p_getAverage_0_[j];
                i += k;
            }

            int l = i / p_getAverage_0_.length;
            return l;
        }
    }

    public static double getIncremental(final double val, final double inc) {
        final double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }

    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;

        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }

    public static double secRanDouble(double min, double max) {

        SecureRandom rand = new SecureRandom();

        return rand.nextDouble() * (max - min) + min;
    }

    public static double roundToPlace(final double value, final int places) {
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
        if (increment < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale((int) increment, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static float getRandom() {
        return MathUtils.rng.nextFloat();
    }
    public static int getRandom(final int cap) {
        return MathUtils.rng.nextInt(cap);
    }

    public static int getRandom(final int floor, final int cap) {
        return floor + MathUtils.rng.nextInt(cap - floor + 1);
    }

}
