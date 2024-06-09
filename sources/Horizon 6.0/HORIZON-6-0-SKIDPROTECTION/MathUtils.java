package HORIZON-6-0-SKIDPROTECTION;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class MathUtils
{
    public static int HorizonCode_Horizon_È(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.intValue();
    }
}
