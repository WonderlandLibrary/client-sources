package io.github.nevalackin.radium.utils;

import java.util.Random;

public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {

    }

    public static double getRandomInRange(double min, double max) {
        return min + (RANDOM.nextDouble() * (max - min));
    }
}
