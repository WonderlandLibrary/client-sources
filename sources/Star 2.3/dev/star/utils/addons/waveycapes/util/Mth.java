package dev.star.utils.addons.waveycapes.util;

public class Mth {
    public static float lerp(float f, float g2, float h) {
        return g2 + f * (h - g2);
    }

    public static double lerp(double d2, double e, double f) {
        return e + d2 * (f - e);
    }

    public static float fastInvSqrt(float f) {
        float g2 = 0.5f * f;
        int i = Float.floatToIntBits(f);
        i = 1597463007 - (i >> 1);
        f = Float.intBitsToFloat(i);
        f *= 1.5f - g2 * f * f;
        return f;
    }

    public static float fastInvCubeRoot(float f) {
        int i = Float.floatToIntBits(f);
        i = 1419967116 - i / 3;
        float g2 = Float.intBitsToFloat(i);
        g2 = 0.6666667f * g2 + 0.33333334f * g2 * g2 * f;
        g2 = 0.6666667f * g2 + 0.33333334f * g2 * g2 * f;
        return g2;
    }
}

