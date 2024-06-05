package optifine;

import java.math.*;

public class MathUtils
{
    public static int getAverage(final int[] vals) {
        if (vals.length <= 0) {
            return 0;
        }
        int sum = 0;
        for (int avg = 0; avg < vals.length; ++avg) {
            final int val = vals[avg];
            sum += val;
        }
        int avg = sum / vals.length;
        return avg;
    }
    
    public static double square(final double range) {
        return range * range;
    }
    
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
