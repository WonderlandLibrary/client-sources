/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.math;

import org.apache.commons.lang3.Validate;

public class IEEE754rUtils {
    public static double min(double ... dArray) {
        if (dArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        Validate.isTrue(dArray.length != 0, "Array cannot be empty.", new Object[0]);
        double d = dArray[0];
        for (int i = 1; i < dArray.length; ++i) {
            d = IEEE754rUtils.min(dArray[i], d);
        }
        return d;
    }

    public static float min(float ... fArray) {
        if (fArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        Validate.isTrue(fArray.length != 0, "Array cannot be empty.", new Object[0]);
        float f = fArray[0];
        for (int i = 1; i < fArray.length; ++i) {
            f = IEEE754rUtils.min(fArray[i], f);
        }
        return f;
    }

    public static double min(double d, double d2, double d3) {
        return IEEE754rUtils.min(IEEE754rUtils.min(d, d2), d3);
    }

    public static double min(double d, double d2) {
        if (Double.isNaN(d)) {
            return d2;
        }
        if (Double.isNaN(d2)) {
            return d;
        }
        return Math.min(d, d2);
    }

    public static float min(float f, float f2, float f3) {
        return IEEE754rUtils.min(IEEE754rUtils.min(f, f2), f3);
    }

    public static float min(float f, float f2) {
        if (Float.isNaN(f)) {
            return f2;
        }
        if (Float.isNaN(f2)) {
            return f;
        }
        return Math.min(f, f2);
    }

    public static double max(double ... dArray) {
        if (dArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        Validate.isTrue(dArray.length != 0, "Array cannot be empty.", new Object[0]);
        double d = dArray[0];
        for (int i = 1; i < dArray.length; ++i) {
            d = IEEE754rUtils.max(dArray[i], d);
        }
        return d;
    }

    public static float max(float ... fArray) {
        if (fArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        Validate.isTrue(fArray.length != 0, "Array cannot be empty.", new Object[0]);
        float f = fArray[0];
        for (int i = 1; i < fArray.length; ++i) {
            f = IEEE754rUtils.max(fArray[i], f);
        }
        return f;
    }

    public static double max(double d, double d2, double d3) {
        return IEEE754rUtils.max(IEEE754rUtils.max(d, d2), d3);
    }

    public static double max(double d, double d2) {
        if (Double.isNaN(d)) {
            return d2;
        }
        if (Double.isNaN(d2)) {
            return d;
        }
        return Math.max(d, d2);
    }

    public static float max(float f, float f2, float f3) {
        return IEEE754rUtils.max(IEEE754rUtils.max(f, f2), f3);
    }

    public static float max(float f, float f2) {
        if (Float.isNaN(f)) {
            return f2;
        }
        if (Float.isNaN(f2)) {
            return f;
        }
        return Math.max(f, f2);
    }
}

