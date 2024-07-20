/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Combat;

import java.util.Random;

public final class CountHelper {
    public static int nextInt(int startInclusive, int endExclusive) {
        if (startInclusive == endExclusive || endExclusive - startInclusive <= 0) {
            return startInclusive;
        }
        return startInclusive + CountHelper.getRandom().nextInt(endExclusive - startInclusive);
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0) {
            return startInclusive;
        }
        return startInclusive + (endInclusive - startInclusive) * Math.random();
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0f) {
            return startInclusive;
        }
        return (float)((double)startInclusive + (double)(endInclusive - startInclusive) * Math.random());
    }

    public static String random(int length, String chars) {
        return CountHelper.random(length, chars.toCharArray());
    }

    public static String random(int length, char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            stringBuilder.append(chars[CountHelper.getRandom().nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    public static Random getRandom() {
        return new Random();
    }
}

