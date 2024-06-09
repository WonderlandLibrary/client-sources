/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class MathUtils {
    private static final Random rng = new Random();

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static int getRandom(int cap) {
        return rng.nextInt(cap);
    }
}

