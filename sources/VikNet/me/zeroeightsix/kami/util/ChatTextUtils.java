package me.zeroeightsix.kami.util;

import me.zeroeightsix.kami.KamiMod;

import java.util.Random;

/**
 * Created by hub on 15 June 2019
 * Updated 1 December 2019 by hub
 */
public class ChatTextUtils {

    public static final String CHAT_SUFFIX = " \u23D0 " + KamiMod.NAME_UNICODE;
    public static final String SECTIONSIGN = "\u00A7";

    private static Random rand = new Random();

    public static String appendChatSuffix(String message) {
        message = cropMaxLengthMessage(message, CHAT_SUFFIX.length());
        message += CHAT_SUFFIX;
        return cropMaxLengthMessage(message);
    }

    public static String appendChatSuffix(String message, String suffix) {
        message = cropMaxLengthMessage(message, suffix.length());
        message += suffix;
        return cropMaxLengthMessage(message);
    }

    public static String generateRandomHexSuffix(int n) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(Integer.toHexString((rand.nextInt() + 11) * rand.nextInt()).substring(0, n));
        sb.append(']');
        return sb.toString();
    }

    public static String cropMaxLengthMessage(String s, int i) {
        if (s.length() > 255 - i) {
            s = s.substring(0, 255 - i);
        }
        return s;
    }

    public static String cropMaxLengthMessage(String s) {
        return cropMaxLengthMessage(s, 0);
    }

    public static String transformPlainToFancy(final String input) {

        String output = input.toLowerCase();

        output = output.replace("a", "\u1d00");
        output = output.replace("b", "\u0299");
        output = output.replace("c", "\u1d04");
        output = output.replace("d", "\u1d05");
        output = output.replace("e", "\u1d07");
        output = output.replace("f", "\u0493");
        output = output.replace("g", "\u0262");
        output = output.replace("h", "\u029c");
        output = output.replace("i", "\u026a");
        output = output.replace("j", "\u1d0a");
        output = output.replace("k", "\u1d0b");
        output = output.replace("l", "\u029f");
        output = output.replace("m", "\u1d0d");
        output = output.replace("n", "\u0274");
        output = output.replace("o", "\u1d0f");
        output = output.replace("p", "\u1d18");
        output = output.replace("q", "\u01eb");
        output = output.replace("r", "\u0280");
        output = output.replace("s", "\u0455");
        output = output.replace("t", "\u1d1b");
        output = output.replace("u", "\u1d1c");
        output = output.replace("v", "\u1d20");
        output = output.replace("w", "\u1d21");
        output = output.replace("x", "\u0445");
        output = output.replace("y", "\u028f");
        output = output.replace("z", "\u1d22");

        return output;

    }

}
