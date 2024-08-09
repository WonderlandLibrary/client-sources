package dev.excellent.impl.util.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Interpolator {
    public <T extends Number> T lerp(T input, T target, double step) {
        double start = input.doubleValue();
        double end = target.doubleValue();
        double result = start + step * (end - start);

        if (input instanceof Integer) {
            return (T) Integer.valueOf((int) Math.round(result));
        } else if (input instanceof Double) {
            return (T) Double.valueOf(result);
        } else if (input instanceof Float) {
            return (T) Float.valueOf((float) result);
        } else if (input instanceof Long) {
            return (T) Long.valueOf(Math.round(result));
        } else if (input instanceof Short) {
            return (T) Short.valueOf((short) Math.round(result));
        } else if (input instanceof Byte) {
            return (T) Byte.valueOf((byte) Math.round(result));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + input.getClass().getSimpleName());
        }
    }
}