/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other;

import java.util.Random;

public class MathUtil {
    private static final Random rng = new Random();

    public static Random getRng() {
        return rng;
    }

    public static float getRandom() {
        return rng.nextFloat();
    }

    public static int getRandom(int cap2) {
        return rng.nextInt(cap2);
    }

    public static int getRandom(int floor, int cap2) {
        return floor + rng.nextInt(cap2 - floor + 1);
    }
}

