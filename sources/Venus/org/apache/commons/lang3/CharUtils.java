/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import org.apache.commons.lang3.StringUtils;

public class CharUtils {
    private static final String[] CHAR_STRING_ARRAY = new String[128];
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final char LF = '\n';
    public static final char CR = '\r';

    @Deprecated
    public static Character toCharacterObject(char c) {
        return Character.valueOf(c);
    }

    public static Character toCharacterObject(String string) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        return Character.valueOf(string.charAt(0));
    }

    public static char toChar(Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The Character must not be null");
        }
        return c.charValue();
    }

    public static char toChar(Character c, char c2) {
        if (c == null) {
            return c2;
        }
        return c.charValue();
    }

    public static char toChar(String string) {
        if (StringUtils.isEmpty(string)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return string.charAt(0);
    }

    public static char toChar(String string, char c) {
        if (StringUtils.isEmpty(string)) {
            return c;
        }
        return string.charAt(0);
    }

    public static int toIntValue(char c) {
        if (!CharUtils.isAsciiNumeric(c)) {
            throw new IllegalArgumentException("The character " + c + " is not in the range '0' - '9'");
        }
        return c - 48;
    }

    public static int toIntValue(char c, int n) {
        if (!CharUtils.isAsciiNumeric(c)) {
            return n;
        }
        return c - 48;
    }

    public static int toIntValue(Character c) {
        if (c == null) {
            throw new IllegalArgumentException("The character must not be null");
        }
        return CharUtils.toIntValue(c.charValue());
    }

    public static int toIntValue(Character c, int n) {
        if (c == null) {
            return n;
        }
        return CharUtils.toIntValue(c.charValue(), n);
    }

    public static String toString(char c) {
        if (c < '\u0080') {
            return CHAR_STRING_ARRAY[c];
        }
        return new String(new char[]{c});
    }

    public static String toString(Character c) {
        if (c == null) {
            return null;
        }
        return CharUtils.toString(c.charValue());
    }

    public static String unicodeEscaped(char c) {
        StringBuilder stringBuilder = new StringBuilder(6);
        stringBuilder.append("\\u");
        stringBuilder.append(HEX_DIGITS[c >> 12 & 0xF]);
        stringBuilder.append(HEX_DIGITS[c >> 8 & 0xF]);
        stringBuilder.append(HEX_DIGITS[c >> 4 & 0xF]);
        stringBuilder.append(HEX_DIGITS[c & 0xF]);
        return stringBuilder.toString();
    }

    public static String unicodeEscaped(Character c) {
        if (c == null) {
            return null;
        }
        return CharUtils.unicodeEscaped(c.charValue());
    }

    public static boolean isAscii(char c) {
        return c < '\u0080';
    }

    public static boolean isAsciiPrintable(char c) {
        return c >= ' ' && c < '\u007f';
    }

    public static boolean isAsciiControl(char c) {
        return c < ' ' || c == '\u007f';
    }

    public static boolean isAsciiAlpha(char c) {
        return CharUtils.isAsciiAlphaUpper(c) || CharUtils.isAsciiAlphaLower(c);
    }

    public static boolean isAsciiAlphaUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isAsciiAlphaLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isAsciiNumeric(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isAsciiAlphanumeric(char c) {
        return CharUtils.isAsciiAlpha(c) || CharUtils.isAsciiNumeric(c);
    }

    public static int compare(char c, char c2) {
        return c - c2;
    }

    static {
        for (char c = '\u0000'; c < CHAR_STRING_ARRAY.length; c = (char)(c + '\u0001')) {
            CharUtils.CHAR_STRING_ARRAY[c] = String.valueOf(c);
        }
    }
}

