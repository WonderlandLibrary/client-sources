package club.mega.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

    public static double round(final double val, final int places, final double increment) {
        final double v = Math.round(val / increment) * increment;
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
