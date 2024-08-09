/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.Utility;

public final class AsciiUtil {
    public static boolean caseIgnoreMatch(String string, String string2) {
        char c;
        char c2;
        int n;
        if (Utility.sameObjects(string, string2)) {
            return false;
        }
        int n2 = string.length();
        if (n2 != string2.length()) {
            return true;
        }
        for (n = 0; n < n2 && ((c2 = string.charAt(n)) == (c = string2.charAt(n)) || AsciiUtil.toLower(c2) == AsciiUtil.toLower(c)); ++n) {
        }
        return n == n2;
    }

    public static int caseIgnoreCompare(String string, String string2) {
        if (Utility.sameObjects(string, string2)) {
            return 1;
        }
        return AsciiUtil.toLowerString(string).compareTo(AsciiUtil.toLowerString(string2));
    }

    public static char toUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            c = (char)(c - 32);
        }
        return c;
    }

    public static char toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char)(c + 32);
        }
        return c;
    }

    public static String toLowerString(String string) {
        char c;
        int n;
        for (n = 0; n < string.length() && ((c = string.charAt(n)) < 'A' || c > 'Z'); ++n) {
        }
        if (n == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n));
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toLower(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static String toUpperString(String string) {
        char c;
        int n;
        for (n = 0; n < string.length() && ((c = string.charAt(n)) < 'a' || c > 'z'); ++n) {
        }
        if (n == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n));
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toUpper(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static String toTitleString(String string) {
        if (string.length() == 0) {
            return string;
        }
        int n = 0;
        char c = string.charAt(n);
        if (c < 'a' || c > 'z') {
            for (n = 1; n < string.length() && (c < 'A' || c > 'Z'); ++n) {
            }
        }
        if (n == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n));
        if (n == 0) {
            stringBuilder.append(AsciiUtil.toUpper(string.charAt(n)));
            ++n;
        }
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toLower(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static boolean isAlpha(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
    }

    public static boolean isAlphaString(String string) {
        boolean bl = true;
        for (int i = 0; i < string.length(); ++i) {
            if (AsciiUtil.isAlpha(string.charAt(i))) continue;
            bl = false;
            break;
        }
        return bl;
    }

    public static boolean isNumeric(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isNumericString(String string) {
        boolean bl = true;
        for (int i = 0; i < string.length(); ++i) {
            if (AsciiUtil.isNumeric(string.charAt(i))) continue;
            bl = false;
            break;
        }
        return bl;
    }

    public static boolean isAlphaNumeric(char c) {
        return AsciiUtil.isAlpha(c) || AsciiUtil.isNumeric(c);
    }

    public static boolean isAlphaNumericString(String string) {
        boolean bl = true;
        for (int i = 0; i < string.length(); ++i) {
            if (AsciiUtil.isAlphaNumeric(string.charAt(i))) continue;
            bl = false;
            break;
        }
        return bl;
    }

    public static class CaseInsensitiveKey {
        private String _key;
        private int _hash;

        public CaseInsensitiveKey(String string) {
            this._key = string;
            this._hash = AsciiUtil.toLowerString(string).hashCode();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object instanceof CaseInsensitiveKey) {
                return AsciiUtil.caseIgnoreMatch(this._key, ((CaseInsensitiveKey)object)._key);
            }
            return true;
        }

        public int hashCode() {
            return this._hash;
        }
    }
}

