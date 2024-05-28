package dev.vertic.util.math;

import java.util.Random;

public class MathUtil {

    public static final Random RANDOM = new Random();

    public static int randomInt(int value1, int value2) {
        return RANDOM.nextInt(value2 - value1 + 1) + value1;
    }

}
