package net.augustus.utils.skid.lorious;

public class MathUtils {
    public static double interpolate(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

    public static double normalize(double start, double end, double val) {
        return (end - start) / (val - start);
    }
}
