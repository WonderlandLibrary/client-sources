package io.github.liticane.clients.util.misc;

import io.github.liticane.clients.util.interfaces.IMethods;

public class MathUtils implements IMethods {
    public static float interpolate(float before, float current, float offset) {
        return (float) interpolate(before, current, (double) offset);
    }

    public static double interpolate(double before, double current, double offset) {
        return before + (current - before) * offset;
    }
    public static double abs(double x) {
        return Double.longBitsToDouble(0x7fffffffffffffffl & Double.doubleToRawLongBits(x));
    }
    public static double clamp(double min, double max, double n) {
        return Math.max(min, Math.min(max, n));
    }
}
