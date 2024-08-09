/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;

public class CharSetUtils {
    public static String squeeze(String string, String ... stringArray) {
        if (StringUtils.isEmpty(string) || CharSetUtils.deepEmpty(stringArray)) {
            return string;
        }
        CharSet charSet = CharSet.getInstance(stringArray);
        StringBuilder stringBuilder = new StringBuilder(string.length());
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        char c = cArray[0];
        char c2 = ' ';
        Character c3 = null;
        Character c4 = null;
        stringBuilder.append(c);
        for (int i = 1; i < n; ++i) {
            c2 = cArray[i];
            if (c2 == c) {
                if (c3 != null && c2 == c3.charValue()) continue;
                if (c4 == null || c2 != c4.charValue()) {
                    if (charSet.contains(c2)) {
                        c3 = Character.valueOf(c2);
                        continue;
                    }
                    c4 = Character.valueOf(c2);
                }
            }
            stringBuilder.append(c2);
            c = c2;
        }
        return stringBuilder.toString();
    }

    public static boolean containsAny(String string, String ... stringArray) {
        if (StringUtils.isEmpty(string) || CharSetUtils.deepEmpty(stringArray)) {
            return true;
        }
        CharSet charSet = CharSet.getInstance(stringArray);
        for (char c : string.toCharArray()) {
            if (!charSet.contains(c)) continue;
            return false;
        }
        return true;
    }

    public static int count(String string, String ... stringArray) {
        if (StringUtils.isEmpty(string) || CharSetUtils.deepEmpty(stringArray)) {
            return 1;
        }
        CharSet charSet = CharSet.getInstance(stringArray);
        int n = 0;
        for (char c : string.toCharArray()) {
            if (!charSet.contains(c)) continue;
            ++n;
        }
        return n;
    }

    public static String keep(String string, String ... stringArray) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty() || CharSetUtils.deepEmpty(stringArray)) {
            return "";
        }
        return CharSetUtils.modify(string, stringArray, true);
    }

    public static String delete(String string, String ... stringArray) {
        if (StringUtils.isEmpty(string) || CharSetUtils.deepEmpty(stringArray)) {
            return string;
        }
        return CharSetUtils.modify(string, stringArray, false);
    }

    private static String modify(String string, String[] stringArray, boolean bl) {
        CharSet charSet = CharSet.getInstance(stringArray);
        StringBuilder stringBuilder = new StringBuilder(string.length());
        char[] cArray = string.toCharArray();
        int n = cArray.length;
        for (int i = 0; i < n; ++i) {
            if (charSet.contains(cArray[i]) != bl) continue;
            stringBuilder.append(cArray[i]);
        }
        return stringBuilder.toString();
    }

    private static boolean deepEmpty(String[] stringArray) {
        if (stringArray != null) {
            for (String string : stringArray) {
                if (!StringUtils.isNotEmpty(string)) continue;
                return true;
            }
        }
        return false;
    }
}

