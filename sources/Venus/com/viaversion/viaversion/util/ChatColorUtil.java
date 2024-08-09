/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import java.util.regex.Pattern;

public class ChatColorUtil {
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    public static final char COLOR_CHAR = '\u00a7';
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-ORX]");
    private static final Int2IntMap COLOR_ORDINALS = new Int2IntOpenHashMap();
    private static int ordinalCounter;

    public static boolean isColorCode(char c) {
        return COLOR_ORDINALS.containsKey(c);
    }

    public static int getColorOrdinal(char c) {
        return COLOR_ORDINALS.getOrDefault(c, -1);
    }

    public static String translateAlternateColorCodes(String string) {
        char[] cArray = string.toCharArray();
        for (int i = 0; i < cArray.length - 1; ++i) {
            if (cArray[i] != '&' || ALL_CODES.indexOf(cArray[i + 1]) <= -1) continue;
            cArray[i] = 167;
            cArray[i + 1] = Character.toLowerCase(cArray[i + 1]);
        }
        return new String(cArray);
    }

    public static String stripColor(String string) {
        return STRIP_COLOR_PATTERN.matcher(string).replaceAll("");
    }

    private static void addColorOrdinal(int n, int n2) {
        for (int i = n; i <= n2; ++i) {
            ChatColorUtil.addColorOrdinal(i);
        }
    }

    private static void addColorOrdinal(int n) {
        COLOR_ORDINALS.put(n, ordinalCounter++);
    }

    static {
        ChatColorUtil.addColorOrdinal(48, 57);
        ChatColorUtil.addColorOrdinal(97, 102);
        ChatColorUtil.addColorOrdinal(107, 111);
        ChatColorUtil.addColorOrdinal(114);
    }
}

