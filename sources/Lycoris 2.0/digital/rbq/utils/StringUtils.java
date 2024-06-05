/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils;

public final class StringUtils {
    public static String formatEnum(String string) {
        String[] split = string.replace('_', ' ').split(" ");
        StringBuilder sb = new StringBuilder();
        if (split.length == 0) {
            return "";
        }
        for (String str : split) {
            sb.append(str.toUpperCase(), 0, 1).append(str.toLowerCase().substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}

