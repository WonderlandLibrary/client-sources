package club.mega.util;

import java.util.Random;

public final class RandomUtil {

    public static boolean get(final int chance) {
        return Math.round(getRandomNumber(0, 100)) <= chance;
    }

    public static String randomNumber(final int length) {
        return random(length, "0123456789".toCharArray());
    }

    public static double getRandomNumber(final double min, final double max) {
        return ((Math.random() * (max - min)) + min);
    }

    public static String randomString(final int length) {
        return random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray());
    }

    private static String random(final int length, final char[] chars) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars[(new Random()).nextInt(chars.length)]);
        return stringBuilder.toString();
    }

}
