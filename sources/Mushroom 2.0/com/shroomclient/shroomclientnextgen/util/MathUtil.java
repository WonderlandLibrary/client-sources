package com.shroomclient.shroomclientnextgen.util;

import java.util.Random;

public class MathUtil {

    private static final Random rand = new Random();

    public static double roundTo(double num, int precision) {
        double multi = Math.pow(10, precision);
        return Math.round(num * multi) / multi;
    }

    public static float roundTo(float num, int precision) {
        double multi = Math.pow(10, precision);
        return (float) (Math.round(((double) num) * multi) / multi);
    }

    public static double getRandomInRange(final double max, final double min) {
        return min + (max - min) * rand.nextFloat();
    }
}
