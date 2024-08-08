package lol.point.returnclient.util.system;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(places, RoundingMode.HALF_UP);

        return decimal.doubleValue();
    }

    public static int random(int min, int max) {
        return -min + (int) (Math.random() * (max - -min + 1));
    }

    public static double random(double min, double max) {
        return min + (Math.random() * (max - min));
    }

    public static double clamp(double value, double minimum, double maximum) {
        return value < minimum ? minimum : (Math.min(value, maximum));
    }

    public static float clamp(float value, float minimum, float maximum) {
        return value < minimum ? minimum : (Math.min(value, maximum));
    }

    public static int clamp(int value, int minimum, int maximum) {
        return value < minimum ? minimum : (Math.min(value, maximum));
    }

    public static int range(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static double range(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    public static float range(float min, float max) {
        return (float) ((Math.random() * (max - min)) + min);
    }

    public static long range(long min, long max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
