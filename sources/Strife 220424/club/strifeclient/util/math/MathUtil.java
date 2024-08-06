package club.strifeclient.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;

public final class MathUtil {

    public static float getDifferenceFloat(final float a, final float b) {
        return b - a;
    }

    public static double getDifferenceDouble(final double a, final double b) {
        return b - a;
    }

    public static double randomDouble(double min, double max) {
        if(min > max) return min;
        return new SecureRandom().nextDouble() * (max - min) + min;
    }

    public static float randomFloat(float min, float max) {
        if(min > max) return min;
        return new SecureRandom().nextFloat() * (max - min) + min;
    }

    public static long randomLong(long min, long max) {
        if(min > max) return min;
        return new SecureRandom().nextLong() * (max - min) + min;
    }

    public static int randomInt(int min, int max) {
        if(min > max) return min;
        return new SecureRandom().nextInt(max) + min;
    }
    public static byte randomByte(byte min, byte max) {
        if(min > max) return min;
        return (byte) (new SecureRandom().nextInt(max) + min);
    }

    public static boolean randomBoolean() {
        return randomBoolean(1, 0.5);
    }

    public static boolean randomBoolean(double range, double value) {
        return randomDouble(0, range) > value;
    }

    public static byte[] randomBytes(int minSize, int maxSize, byte min, byte max) {
        int size = randomInt(minSize, maxSize);
        final byte[] out = new byte[size];
        for (int i = 0; i < size; i++) {
            out[i] = randomByte(min, max);
        }
        return out;
    }

    public static double round(double value, int places) {
        return round(value, places, 1);
    }

    public static double round(double value, int places, double increment) {
        final double flooredValue = Math.floor(value / increment) * increment;
        final double ceiledValue = Math.ceil(value / increment) * increment;
        final boolean aboveHalfIncrement = value >= flooredValue + (increment / 2);
        return BigDecimal.valueOf(aboveHalfIncrement ? ceiledValue : flooredValue)
                .setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
