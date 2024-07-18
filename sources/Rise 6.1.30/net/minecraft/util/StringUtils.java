package net.minecraft.util;

import com.alan.clients.util.font.Font;

import java.util.regex.Pattern;

public class StringUtils {
    private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    /**
     * Returns the time elapsed for the given number of ticks, in "mm:ss" format.
     */
    public static String ticksToElapsedTime(final int ticks) {
        int i = ticks / 20;
        final int j = i / 60;
        i = i % 60;
        return i < 10 ? j + ":0" + i : j + ":" + i;
    }

    public static String stripControlCodes(final String p_76338_0_) {
        return patternControlCode.matcher(p_76338_0_).replaceAll("");
    }

    /**
     * Returns a value indicating whether the given string is null or empty.
     */
    public static boolean isNullOrEmpty(final String string) {
        return org.apache.commons.lang3.StringUtils.isEmpty(string);
    }

    public static String getToFit(Font font, String string, double length) {
        double l = 0;
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while (l < length && index < string.length()) {
            String character = String.valueOf(string.charAt(index));
            l += font.width(character);
            index++;
            stringBuilder.append(character);
        }

        return stringBuilder.toString();
    }
}
