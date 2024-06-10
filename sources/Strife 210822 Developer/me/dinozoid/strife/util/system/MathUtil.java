package me.dinozoid.strife.util.system;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

    public static double tryParseDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double random(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
