// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import java.util.Random;

public final class RandomUtil {
    public static Random random;

    static {
        RandomUtil.random = new Random(System.currentTimeMillis());
    }

    public static double method401(final double n, final double point) {
        return point * Math.round(n / point);
    }

    public static int getRandom(final int start, final int bound) {
        return RandomUtil.random.nextInt(start, bound);
    }

    public static double method403(double delta, final double start, final double end) {
        delta = Math.max(0.0, Math.min(1.0, delta));
        return start + (end - start) * (delta * delta * (3.0 - 2.0 * delta));
    }
}
