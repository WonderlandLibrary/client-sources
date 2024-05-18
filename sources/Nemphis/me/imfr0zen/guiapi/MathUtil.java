/*
 * Decompiled with CFR 0_118.
 */
package me.imfr0zen.guiapi;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {
    private MathUtil() {
    }

    public static float round(float f, int i) {
        return new BigDecimal(f).setScale(i, RoundingMode.HALF_EVEN).floatValue();
    }

    public static double round(double f, int i) {
        return new BigDecimal(f).setScale(i, RoundingMode.HALF_EVEN).doubleValue();
    }
}

