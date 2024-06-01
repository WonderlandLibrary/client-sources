package best.actinium.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public static double round(double num, double increment) {
        BigDecimal bigDecimal = new BigDecimal(num);

        bigDecimal = (bigDecimal.setScale(
                (int) increment,
                RoundingMode.HALF_UP
        ));

        return bigDecimal.doubleValue();
    }

    public static double round(double value, int places, double increment) {
        double floor = Math.floor(value / increment) * increment;
        double ceil = Math.ceil(value / increment) * increment;

        boolean above = value >= floor + (increment / 2);

        return BigDecimal.valueOf(above ? ceil : floor)
                .setScale(places, RoundingMode.HALF_UP)
                .doubleValue();
    }

}