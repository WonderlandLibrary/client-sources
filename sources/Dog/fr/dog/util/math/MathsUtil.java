package fr.dog.util.math;

public class MathsUtil {

    public static double interpolate(final double old, final double now, final float partialTicks) {
        return old + (now - old) * partialTicks;
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return (int) interpolate(oldValue, newValue, (float) interpolationValue);
    }

    public static float interpolate(final float old, final float now, final float partialTicks) {
        return old + (now - old) * partialTicks;
    }

    public static final String digits = "0123456789ABCDEF";
    public static String intToHex(int input) {
        if (input <= 0)
            return "0";
        StringBuilder hex = new StringBuilder();
        while (input > 0) {
            int digit = input % 16;
            hex.insert(0, digits.charAt(digit));
            input = input / 16;
        }
        return hex.toString();
        // baeldung
    }

}
