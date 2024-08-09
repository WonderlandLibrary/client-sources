package dev.darkmoon.client.utility.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.utility.Utility;

import java.util.Arrays;

public class StringUtility implements Utility {
    public static String getDigits(String string) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static String getStringRedColor(String string) {
        StringBuilder builder = new StringBuilder();
        Arrays.asList(string.split(" ")).forEach(str -> {
            builder.append(ChatFormatting.RED).append(str).append(" ");
        });
        return builder.toString().trim();
    }
}
