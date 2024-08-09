/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.utils;

import java.util.regex.Pattern;

public class ChatUtil {
    private static final Pattern UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>\u00a7[0-fk-or])*(\u00a7r|\\Z))|(?>(?>\u00a7[0-f])*(\u00a7[0-f]))");
    private static final Pattern UNUSED_COLOR_PATTERN_PREFIX = Pattern.compile("(?>(?>\u00a7[0-fk-or])*(\u00a7r))|(?>(?>\u00a7[0-f])*(\u00a7[0-f]))");

    public static String removeUnusedColor(String string, char c) {
        return ChatUtil.removeUnusedColor(string, c, false);
    }

    public static String removeUnusedColor(String string, char c, boolean bl) {
        if (string == null) {
            return null;
        }
        Pattern pattern = bl ? UNUSED_COLOR_PATTERN_PREFIX : UNUSED_COLOR_PATTERN;
        string = pattern.matcher(string).replaceAll("$1$2");
        StringBuilder stringBuilder = new StringBuilder();
        char c2 = c;
        for (int i = 0; i < string.length(); ++i) {
            char c3 = string.charAt(i);
            if (c3 != '\u00a7' || i == string.length() - 1) {
                stringBuilder.append(c3);
                continue;
            }
            if ((c3 = string.charAt(++i)) == c2) continue;
            stringBuilder.append('\u00a7').append(c3);
            c2 = c3;
        }
        return stringBuilder.toString();
    }
}

