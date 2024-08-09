/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
public final class Ascii {
    public static final byte NUL = 0;
    public static final byte SOH = 1;
    public static final byte STX = 2;
    public static final byte ETX = 3;
    public static final byte EOT = 4;
    public static final byte ENQ = 5;
    public static final byte ACK = 6;
    public static final byte BEL = 7;
    public static final byte BS = 8;
    public static final byte HT = 9;
    public static final byte LF = 10;
    public static final byte NL = 10;
    public static final byte VT = 11;
    public static final byte FF = 12;
    public static final byte CR = 13;
    public static final byte SO = 14;
    public static final byte SI = 15;
    public static final byte DLE = 16;
    public static final byte DC1 = 17;
    public static final byte XON = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte XOFF = 19;
    public static final byte DC4 = 20;
    public static final byte NAK = 21;
    public static final byte SYN = 22;
    public static final byte ETB = 23;
    public static final byte CAN = 24;
    public static final byte EM = 25;
    public static final byte SUB = 26;
    public static final byte ESC = 27;
    public static final byte FS = 28;
    public static final byte GS = 29;
    public static final byte RS = 30;
    public static final byte US = 31;
    public static final byte SP = 32;
    public static final byte SPACE = 32;
    public static final byte DEL = 127;
    public static final char MIN = '\u0000';
    public static final char MAX = '\u007f';

    private Ascii() {
    }

    public static String toLowerCase(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (!Ascii.isUpperCase(string.charAt(i))) continue;
            char[] cArray = string.toCharArray();
            while (i < n) {
                char c = cArray[i];
                if (Ascii.isUpperCase(c)) {
                    cArray[i] = (char)(c ^ 0x20);
                }
                ++i;
            }
            return String.valueOf(cArray);
        }
        return string;
    }

    public static String toLowerCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return Ascii.toLowerCase((String)charSequence);
        }
        char[] cArray = new char[charSequence.length()];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = Ascii.toLowerCase(charSequence.charAt(i));
        }
        return String.valueOf(cArray);
    }

    public static char toLowerCase(char c) {
        return Ascii.isUpperCase(c) ? (char)(c ^ 0x20) : c;
    }

    public static String toUpperCase(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (!Ascii.isLowerCase(string.charAt(i))) continue;
            char[] cArray = string.toCharArray();
            while (i < n) {
                char c = cArray[i];
                if (Ascii.isLowerCase(c)) {
                    cArray[i] = (char)(c & 0x5F);
                }
                ++i;
            }
            return String.valueOf(cArray);
        }
        return string;
    }

    public static String toUpperCase(CharSequence charSequence) {
        if (charSequence instanceof String) {
            return Ascii.toUpperCase((String)charSequence);
        }
        char[] cArray = new char[charSequence.length()];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = Ascii.toUpperCase(charSequence.charAt(i));
        }
        return String.valueOf(cArray);
    }

    public static char toUpperCase(char c) {
        return Ascii.isLowerCase(c) ? (char)(c & 0x5F) : c;
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static String truncate(CharSequence charSequence, int n, String string) {
        Preconditions.checkNotNull(charSequence);
        int n2 = n - string.length();
        Preconditions.checkArgument(n2 >= 0, "maxLength (%s) must be >= length of the truncation indicator (%s)", n, string.length());
        if (charSequence.length() <= n) {
            String string2 = charSequence.toString();
            if (string2.length() <= n) {
                return string2;
            }
            charSequence = string2;
        }
        return new StringBuilder(n).append(charSequence, 0, n2).append(string).toString();
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        int n = charSequence.length();
        if (charSequence == charSequence2) {
            return false;
        }
        if (n != charSequence2.length()) {
            return true;
        }
        for (int i = 0; i < n; ++i) {
            int n2;
            char c;
            char c2 = charSequence.charAt(i);
            if (c2 == (c = charSequence2.charAt(i)) || (n2 = Ascii.getAlphaIndex(c2)) < 26 && n2 == Ascii.getAlphaIndex(c)) continue;
            return true;
        }
        return false;
    }

    private static int getAlphaIndex(char c) {
        return (char)((c | 0x20) - 97);
    }
}

