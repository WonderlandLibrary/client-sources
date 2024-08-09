package dev.darkmoon.client.utility.math;

import java.util.Random;

public class RandomUtility {
    private static final Random random = new Random();

    public static String randomString(int length) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = "qwertyuiopasdfghjklzxcvbnm1234567890".toCharArray();
        for (int i = 0; i < length; i++) {
            String s = new String(new char[]{buffer[random.nextInt(buffer.length)]});
            builder.append(random.nextBoolean() ? s : s.toUpperCase());
        }
        return builder.toString();
    }

    public static String randomString() {
        return randomString(random.nextInt(10));
    }
}
