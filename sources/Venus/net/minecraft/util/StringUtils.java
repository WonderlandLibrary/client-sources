/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class StringUtils {
    private static final Pattern PATTERN_CONTROL_CODE = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public static String ticksToElapsedTime(int n) {
        int n2 = n / 20;
        int n3 = n2 / 60;
        return (n2 %= 60) < 10 ? n3 + ":0" + n2 : n3 + ":" + n2;
    }

    public static String stripControlCodes(String string) {
        return PATTERN_CONTROL_CODE.matcher(string).replaceAll("");
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return org.apache.commons.lang3.StringUtils.isEmpty(string);
    }
}

