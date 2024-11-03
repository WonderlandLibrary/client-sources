package net.silentclient.client.utils;

public class AngleUtilities {

    private static final double[] angleSteps;

    public static double a(final double n) {
        return angleSteps[(int) (n * 10430.3779296875) & 0xFFFF];
    }

    public static double b(final double n) {
        return angleSteps[(int) (n * 10430.3779296875 + 16384.0) & 0xFFFF];
    }

    public static float a(final float n, final float n2, final float n3) {
        return (n < n2) ? n2 : (Math.min(n, n3));
    }

    public static int a(final float n) {
        final int n2 = (int) n;
        return (n < n2) ? (n2 - 1) : n2;
    }

    static {
        angleSteps = new double[65536];
        for (int i = 0; i < 65536; ++i) {
            angleSteps[i] = Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
    }
}