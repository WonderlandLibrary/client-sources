package net.optifine.util;

import lombok.Getter;

import java.util.Random;

public class RandomUtils {
    @Getter
    private static final Random random = new Random();

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }
}
