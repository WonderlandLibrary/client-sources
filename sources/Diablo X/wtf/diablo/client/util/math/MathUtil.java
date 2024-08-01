package wtf.diablo.client.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtil {
    private final static Random RNG = new Random();

    private MathUtil()
    {
    }

    public static float getRandomFloat(final float min, final float max) {
        return RNG.nextInt((int) (max - min + 1.0f)) + max;
    }

    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }

    public static double roundToPlace(final double value, final int place)
    {
        final BigDecimal bd = new BigDecimal(value).setScale(place, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue){
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    /**
     *
     * @return the closest value to 'valueToRound' that is divisible by 'divisor'
     */
    public static double getClosestMultipleOfDivisor(final double valueToRound, final double divisor) {
        // Calculate the quotient by dividing valueToRound by divisor and rounding to the nearest integer
        final double quotient = Math.round(valueToRound / divisor);

        // Return the closest multiple of divisor by multiplying quotient by divisor
        return divisor * quotient;
    }

    public static int getRandomInt(final int min, final int max) {
        return RNG.nextInt((max - min) + 1) + min;
    }
}
