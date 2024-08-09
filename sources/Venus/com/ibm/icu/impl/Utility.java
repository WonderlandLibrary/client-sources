/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

public final class Utility {
    private static final char APOSTROPHE = '\'';
    private static final char BACKSLASH = '\\';
    private static final int MAGIC_UNSIGNED = Integer.MIN_VALUE;
    private static final char ESCAPE = '\ua5a5';
    static final byte ESCAPE_BYTE = -91;
    public static String LINE_SEPARATOR = System.getProperty("line.separator");
    static final char[] HEX_DIGIT = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] UNESCAPE_MAP = new char[]{'a', '\u0007', 'b', '\b', 'e', '\u001b', 'f', '\f', 'n', '\n', 'r', '\r', 't', '\t', 'v', '\u000b'};
    static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static final boolean arrayEquals(Object[] objectArray, Object object) {
        if (objectArray == null) {
            return object == null;
        }
        if (!(object instanceof Object[])) {
            return true;
        }
        Object[] objectArray2 = (Object[])object;
        return objectArray.length == objectArray2.length && Utility.arrayRegionMatches(objectArray, 0, objectArray2, 0, objectArray.length);
    }

    public static final boolean arrayEquals(int[] nArray, Object object) {
        if (nArray == null) {
            return object == null;
        }
        if (!(object instanceof int[])) {
            return true;
        }
        int[] nArray2 = (int[])object;
        return nArray.length == nArray2.length && Utility.arrayRegionMatches(nArray, 0, nArray2, 0, nArray.length);
    }

    public static final boolean arrayEquals(double[] dArray, Object object) {
        if (dArray == null) {
            return object == null;
        }
        if (!(object instanceof double[])) {
            return true;
        }
        double[] dArray2 = (double[])object;
        return dArray.length == dArray2.length && Utility.arrayRegionMatches(dArray, 0, dArray2, 0, dArray.length);
    }

    public static final boolean arrayEquals(byte[] byArray, Object object) {
        if (byArray == null) {
            return object == null;
        }
        if (!(object instanceof byte[])) {
            return true;
        }
        byte[] byArray2 = (byte[])object;
        return byArray.length == byArray2.length && Utility.arrayRegionMatches(byArray, 0, byArray2, 0, byArray.length);
    }

    public static final boolean arrayEquals(Object object, Object object2) {
        if (object == null) {
            return object2 == null;
        }
        if (object instanceof Object[]) {
            return Utility.arrayEquals((Object[])object, object2);
        }
        if (object instanceof int[]) {
            return Utility.arrayEquals((int[])object, object2);
        }
        if (object instanceof double[]) {
            return Utility.arrayEquals((double[])object, object2);
        }
        if (object instanceof byte[]) {
            return Utility.arrayEquals((byte[])object, object2);
        }
        return object.equals(object2);
    }

    public static final boolean arrayRegionMatches(Object[] objectArray, int n, Object[] objectArray2, int n2, int n3) {
        int n4 = n + n3;
        int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (Utility.arrayEquals(objectArray[i], objectArray2[i + n5])) continue;
            return true;
        }
        return false;
    }

    public static final boolean arrayRegionMatches(char[] cArray, int n, char[] cArray2, int n2, int n3) {
        int n4 = n + n3;
        int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (cArray[i] == cArray2[i + n5]) continue;
            return true;
        }
        return false;
    }

    public static final boolean arrayRegionMatches(int[] nArray, int n, int[] nArray2, int n2, int n3) {
        int n4 = n + n3;
        int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (nArray[i] == nArray2[i + n5]) continue;
            return true;
        }
        return false;
    }

    public static final boolean arrayRegionMatches(double[] dArray, int n, double[] dArray2, int n2, int n3) {
        int n4 = n + n3;
        int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (dArray[i] == dArray2[i + n5]) continue;
            return true;
        }
        return false;
    }

    public static final boolean arrayRegionMatches(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        int n4 = n + n3;
        int n5 = n2 - n;
        for (int i = n; i < n4; ++i) {
            if (byArray[i] == byArray2[i + n5]) continue;
            return true;
        }
        return false;
    }

    public static final boolean sameObjects(Object object, Object object2) {
        return object == object2;
    }

    public static <T extends Comparable<T>> int checkCompare(T t, T t2) {
        return t == null ? (t2 == null ? 0 : -1) : (t2 == null ? 1 : t.compareTo(t2));
    }

    public static int checkHash(Object object) {
        return object == null ? 0 : object.hashCode();
    }

    public static final String arrayToRLEString(int[] nArray) {
        StringBuilder stringBuilder = new StringBuilder();
        Utility.appendInt(stringBuilder, nArray.length);
        int n = nArray[0];
        int n2 = 1;
        for (int i = 1; i < nArray.length; ++i) {
            int n3 = nArray[i];
            if (n3 == n && n2 < 65535) {
                ++n2;
                continue;
            }
            Utility.encodeRun(stringBuilder, n, n2);
            n = n3;
            n2 = 1;
        }
        Utility.encodeRun(stringBuilder, n, n2);
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(short[] sArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(sArray.length >> 16));
        stringBuilder.append((char)sArray.length);
        short s = sArray[0];
        int n = 1;
        for (int i = 1; i < sArray.length; ++i) {
            short s2 = sArray[i];
            if (s2 == s && n < 65535) {
                ++n;
                continue;
            }
            Utility.encodeRun(stringBuilder, s, n);
            s = s2;
            n = 1;
        }
        Utility.encodeRun(stringBuilder, s, n);
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(char[] cArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(cArray.length >> 16));
        stringBuilder.append((char)cArray.length);
        char c = cArray[0];
        int n = 1;
        for (int i = 1; i < cArray.length; ++i) {
            char c2 = cArray[i];
            if (c2 == c && n < 65535) {
                ++n;
                continue;
            }
            Utility.encodeRun(stringBuilder, (short)c, n);
            c = c2;
            n = 1;
        }
        Utility.encodeRun(stringBuilder, (short)c, n);
        return stringBuilder.toString();
    }

    public static final String arrayToRLEString(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(byArray.length >> 16));
        stringBuilder.append((char)byArray.length);
        byte by = byArray[0];
        int n = 1;
        byte[] byArray2 = new byte[2];
        for (int i = 1; i < byArray.length; ++i) {
            byte by2 = byArray[i];
            if (by2 == by && n < 255) {
                ++n;
                continue;
            }
            Utility.encodeRun(stringBuilder, by, n, byArray2);
            by = by2;
            n = 1;
        }
        Utility.encodeRun(stringBuilder, by, n, byArray2);
        if (byArray2[0] != 0) {
            Utility.appendEncodedByte(stringBuilder, (byte)0, byArray2);
        }
        return stringBuilder.toString();
    }

    private static final <T extends Appendable> void encodeRun(T t, int n, int n2) {
        if (n2 < 4) {
            for (int i = 0; i < n2; ++i) {
                if (n == 42405) {
                    Utility.appendInt(t, n);
                }
                Utility.appendInt(t, n);
            }
        } else {
            if (n2 == 42405) {
                if (n == 42405) {
                    Utility.appendInt(t, 42405);
                }
                Utility.appendInt(t, n);
                --n2;
            }
            Utility.appendInt(t, 42405);
            Utility.appendInt(t, n2);
            Utility.appendInt(t, n);
        }
    }

    private static final <T extends Appendable> void appendInt(T t, int n) {
        try {
            t.append((char)(n >>> 16));
            t.append((char)(n & 0xFFFF));
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    private static final <T extends Appendable> void encodeRun(T t, short s, int n) {
        try {
            char c = (char)s;
            if (n < 4) {
                for (int i = 0; i < n; ++i) {
                    if (c == '\ua5a5') {
                        t.append('\ua5a5');
                    }
                    t.append(c);
                }
            } else {
                if (n == 42405) {
                    if (c == '\ua5a5') {
                        t.append('\ua5a5');
                    }
                    t.append(c);
                    --n;
                }
                t.append('\ua5a5');
                t.append((char)n);
                t.append(c);
            }
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    private static final <T extends Appendable> void encodeRun(T t, byte by, int n, byte[] byArray) {
        if (n < 4) {
            for (int i = 0; i < n; ++i) {
                if (by == -91) {
                    Utility.appendEncodedByte(t, (byte)-91, byArray);
                }
                Utility.appendEncodedByte(t, by, byArray);
            }
        } else {
            if ((byte)n == -91) {
                if (by == -91) {
                    Utility.appendEncodedByte(t, (byte)-91, byArray);
                }
                Utility.appendEncodedByte(t, by, byArray);
                --n;
            }
            Utility.appendEncodedByte(t, (byte)-91, byArray);
            Utility.appendEncodedByte(t, (byte)n, byArray);
            Utility.appendEncodedByte(t, by, byArray);
        }
    }

    private static final <T extends Appendable> void appendEncodedByte(T t, byte by, byte[] byArray) {
        try {
            if (byArray[0] != 0) {
                char c = (char)(byArray[1] << 8 | by & 0xFF);
                t.append(c);
                byArray[0] = 0;
            } else {
                byArray[0] = 1;
                byArray[1] = by;
            }
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static final int[] RLEStringToIntArray(String string) {
        int n = Utility.getInt(string, 0);
        int[] nArray = new int[n];
        int n2 = 0;
        int n3 = 1;
        int n4 = string.length() / 2;
        while (n2 < n && n3 < n4) {
            int n5;
            if ((n5 = Utility.getInt(string, n3++)) == 42405) {
                if ((n5 = Utility.getInt(string, n3++)) == 42405) {
                    nArray[n2++] = n5;
                    continue;
                }
                int n6 = n5;
                int n7 = Utility.getInt(string, n3++);
                for (int i = 0; i < n6; ++i) {
                    nArray[n2++] = n7;
                }
                continue;
            }
            nArray[n2++] = n5;
        }
        if (n2 != n || n3 != n4) {
            throw new IllegalStateException("Bad run-length encoded int array");
        }
        return nArray;
    }

    static final int getInt(String string, int n) {
        return string.charAt(2 * n) << 16 | string.charAt(2 * n + 1);
    }

    public static final short[] RLEStringToShortArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        short[] sArray = new short[n];
        int n2 = 0;
        for (int i = 2; i < string.length(); ++i) {
            int n3 = string.charAt(i);
            if (n3 == 42405) {
                if ((n3 = string.charAt(++i)) == 42405) {
                    sArray[n2++] = (short)n3;
                    continue;
                }
                int n4 = n3;
                short s = (short)string.charAt(++i);
                for (int j = 0; j < n4; ++j) {
                    sArray[n2++] = s;
                }
                continue;
            }
            sArray[n2++] = (short)n3;
        }
        if (n2 != n) {
            throw new IllegalStateException("Bad run-length encoded short array");
        }
        return sArray;
    }

    public static final char[] RLEStringToCharArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        char[] cArray = new char[n];
        int n2 = 0;
        for (int i = 2; i < string.length(); ++i) {
            int n3 = string.charAt(i);
            if (n3 == 42405) {
                if ((n3 = string.charAt(++i)) == 42405) {
                    cArray[n2++] = n3;
                    continue;
                }
                int n4 = n3;
                char c = string.charAt(++i);
                for (int j = 0; j < n4; ++j) {
                    cArray[n2++] = c;
                }
                continue;
            }
            cArray[n2++] = n3;
        }
        if (n2 != n) {
            throw new IllegalStateException("Bad run-length encoded short array");
        }
        return cArray;
    }

    public static final byte[] RLEStringToByteArray(String string) {
        int n = string.charAt(0) << 16 | string.charAt(1);
        byte[] byArray = new byte[n];
        boolean bl = true;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 2;
        int n6 = 0;
        while (n6 < n) {
            int n7;
            if (bl) {
                n2 = string.charAt(n5++);
                n7 = (byte)(n2 >> 8);
                bl = false;
            } else {
                n7 = n2 & 0xFF;
                bl = true;
            }
            switch (n3) {
                case 0: {
                    if (n7 == -91) {
                        n3 = 1;
                        break;
                    }
                    byArray[n6++] = n7;
                    break;
                }
                case 1: {
                    if (n7 == -91) {
                        byArray[n6++] = -91;
                        n3 = 0;
                        break;
                    }
                    n4 = n7;
                    if (n4 < 0) {
                        n4 += 256;
                    }
                    n3 = 2;
                    break;
                }
                case 2: {
                    for (int i = 0; i < n4; ++i) {
                        byArray[n6++] = n7;
                    }
                    n3 = 0;
                }
            }
        }
        if (n3 != 0) {
            throw new IllegalStateException("Bad run-length encoded byte array");
        }
        if (n5 != string.length()) {
            throw new IllegalStateException("Excess data in RLE byte array string");
        }
        return byArray;
    }

    public static final String formatForSource(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < string.length()) {
            if (n > 0) {
                stringBuilder.append('+').append(LINE_SEPARATOR);
            }
            stringBuilder.append("        \"");
            int n2 = 11;
            while (n < string.length() && n2 < 80) {
                char c;
                if ((c = string.charAt(n++)) < ' ' || c == '\"' || c == '\\') {
                    if (c == '\n') {
                        stringBuilder.append("\\n");
                        n2 += 2;
                        continue;
                    }
                    if (c == '\t') {
                        stringBuilder.append("\\t");
                        n2 += 2;
                        continue;
                    }
                    if (c == '\r') {
                        stringBuilder.append("\\r");
                        n2 += 2;
                        continue;
                    }
                    stringBuilder.append('\\');
                    stringBuilder.append(HEX_DIGIT[(c & 0x1C0) >> 6]);
                    stringBuilder.append(HEX_DIGIT[(c & 0x38) >> 3]);
                    stringBuilder.append(HEX_DIGIT[c & 7]);
                    n2 += 4;
                    continue;
                }
                if (c <= '~') {
                    stringBuilder.append(c);
                    ++n2;
                    continue;
                }
                stringBuilder.append("\\u");
                stringBuilder.append(HEX_DIGIT[(c & 0xF000) >> 12]);
                stringBuilder.append(HEX_DIGIT[(c & 0xF00) >> 8]);
                stringBuilder.append(HEX_DIGIT[(c & 0xF0) >> 4]);
                stringBuilder.append(HEX_DIGIT[c & 0xF]);
                n2 += 6;
            }
            stringBuilder.append('\"');
        }
        return stringBuilder.toString();
    }

    public static final String format1ForSource(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        int n = 0;
        while (n < string.length()) {
            char c;
            if ((c = string.charAt(n++)) < ' ' || c == '\"' || c == '\\') {
                if (c == '\n') {
                    stringBuilder.append("\\n");
                    continue;
                }
                if (c == '\t') {
                    stringBuilder.append("\\t");
                    continue;
                }
                if (c == '\r') {
                    stringBuilder.append("\\r");
                    continue;
                }
                stringBuilder.append('\\');
                stringBuilder.append(HEX_DIGIT[(c & 0x1C0) >> 6]);
                stringBuilder.append(HEX_DIGIT[(c & 0x38) >> 3]);
                stringBuilder.append(HEX_DIGIT[c & 7]);
                continue;
            }
            if (c <= '~') {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append("\\u");
            stringBuilder.append(HEX_DIGIT[(c & 0xF000) >> 12]);
            stringBuilder.append(HEX_DIGIT[(c & 0xF00) >> 8]);
            stringBuilder.append(HEX_DIGIT[(c & 0xF0) >> 4]);
            stringBuilder.append(HEX_DIGIT[c & 0xF]);
        }
        stringBuilder.append('\"');
        return stringBuilder.toString();
    }

    public static final String escape(String string) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i += UTF16.getCharCount(n)) {
            n = Character.codePointAt(string, i);
            if (n >= 32 && n <= 127) {
                if (n == 92) {
                    stringBuilder.append("\\\\");
                    continue;
                }
                stringBuilder.append((char)n);
                continue;
            }
            boolean bl = n <= 65535;
            stringBuilder.append(bl ? "\\u" : "\\U");
            stringBuilder.append(Utility.hex(n, bl ? 4 : 8));
        }
        return stringBuilder.toString();
    }

    public static int unescapeAt(String string, int[] nArray) {
        int n;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 4;
        boolean bl = false;
        int n7 = nArray[0];
        int n8 = string.length();
        if (n7 < 0 || n7 >= n8) {
            return 1;
        }
        int n9 = Character.codePointAt(string, n7);
        n7 += UTF16.getCharCount(n9);
        switch (n9) {
            case 117: {
                n5 = 4;
                n4 = 4;
                break;
            }
            case 85: {
                n5 = 8;
                n4 = 8;
                break;
            }
            case 120: {
                n4 = 1;
                if (n7 < n8 && UTF16.charAt(string, n7) == 123) {
                    ++n7;
                    bl = true;
                    n5 = 8;
                    break;
                }
                n5 = 2;
                break;
            }
            default: {
                n = UCharacter.digit(n9, 8);
                if (n < 0) break;
                n4 = 1;
                n5 = 3;
                n3 = 1;
                n6 = 3;
                n2 = n;
            }
        }
        if (n4 != 0) {
            while (n7 < n8 && n3 < n5 && (n = UCharacter.digit(n9 = UTF16.charAt(string, n7), n6 == 3 ? 8 : 16)) >= 0) {
                n2 = n2 << n6 | n;
                n7 += UTF16.getCharCount(n9);
                ++n3;
            }
            if (n3 < n4) {
                return 1;
            }
            if (bl) {
                if (n9 != 125) {
                    return 1;
                }
                ++n7;
            }
            if (n2 < 0 || n2 >= 0x110000) {
                return 1;
            }
            if (n7 < n8 && UTF16.isLeadSurrogate((char)n2)) {
                int n10 = n7 + 1;
                n9 = string.charAt(n7);
                if (n9 == 92 && n10 < n8) {
                    int[] nArray2 = new int[]{n10};
                    n9 = Utility.unescapeAt(string, nArray2);
                    n10 = nArray2[0];
                }
                if (UTF16.isTrailSurrogate((char)n9)) {
                    n7 = n10;
                    n2 = Character.toCodePoint((char)n2, (char)n9);
                }
            }
            nArray[0] = n7;
            return n2;
        }
        for (int i = 0; i < UNESCAPE_MAP.length; i += 2) {
            if (n9 == UNESCAPE_MAP[i]) {
                nArray[0] = n7;
                return UNESCAPE_MAP[i + 1];
            }
            if (n9 < UNESCAPE_MAP[i]) break;
        }
        if (n9 == 99 && n7 < n8) {
            n9 = UTF16.charAt(string, n7);
            nArray[0] = n7 + UTF16.getCharCount(n9);
            return 0x1F & n9;
        }
        nArray[0] = n7;
        return n9;
    }

    public static String unescape(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = new int[1];
        int n = 0;
        while (n < string.length()) {
            char c;
            if ((c = string.charAt(n++)) == '\\') {
                nArray[0] = n;
                int n2 = Utility.unescapeAt(string, nArray);
                if (n2 < 0) {
                    throw new IllegalArgumentException("Invalid escape sequence " + string.substring(n - 1, Math.min(n + 8, string.length())));
                }
                stringBuilder.appendCodePoint(n2);
                n = nArray[0];
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String unescapeLeniently(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int[] nArray = new int[1];
        int n = 0;
        while (n < string.length()) {
            char c;
            if ((c = string.charAt(n++)) == '\\') {
                nArray[0] = n;
                int n2 = Utility.unescapeAt(string, nArray);
                if (n2 < 0) {
                    stringBuilder.append(c);
                    continue;
                }
                stringBuilder.appendCodePoint(n2);
                n = nArray[0];
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String hex(long l) {
        return Utility.hex(l, 4);
    }

    public static String hex(long l, int n) {
        String string;
        boolean bl;
        if (l == Long.MIN_VALUE) {
            return "-8000000000000000";
        }
        boolean bl2 = bl = l < 0L;
        if (bl) {
            l = -l;
        }
        if ((string = Long.toString(l, 16).toUpperCase(Locale.ENGLISH)).length() < n) {
            string = "0000000000000000".substring(string.length(), n) + string;
        }
        if (bl) {
            return '-' + string;
        }
        return string;
    }

    public static String hex(CharSequence charSequence) {
        return Utility.hex(charSequence, 4, ",", true, new StringBuilder()).toString();
    }

    public static <S extends CharSequence, U extends CharSequence, T extends Appendable> T hex(S s, int n, U u, boolean bl, T t) {
        try {
            if (bl) {
                int n2;
                for (int i = 0; i < s.length(); i += UTF16.getCharCount(n2)) {
                    n2 = Character.codePointAt(s, i);
                    if (i != 0) {
                        t.append(u);
                    }
                    t.append(Utility.hex(n2, n));
                }
            } else {
                for (int i = 0; i < s.length(); ++i) {
                    if (i != 0) {
                        t.append(u);
                    }
                    t.append(Utility.hex(s.charAt(i), n));
                }
            }
            return t;
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static String hex(byte[] byArray, int n, int n2, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = n; i < n2; ++i) {
            if (i != 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(Utility.hex(byArray[i]));
        }
        return stringBuilder.toString();
    }

    public static <S extends CharSequence> String hex(S s, int n, S s2) {
        return Utility.hex(s, n, s2, true, new StringBuilder()).toString();
    }

    public static void split(String string, char c, String[] stringArray) {
        int n;
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < string.length(); ++n) {
            if (string.charAt(n) != c) continue;
            stringArray[n3++] = string.substring(n2, n);
            n2 = n + 1;
        }
        stringArray[n3++] = string.substring(n2, n);
        while (n3 < stringArray.length) {
            stringArray[n3++] = "";
        }
    }

    public static String[] split(String string, char c) {
        int n;
        int n2 = 0;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (n = 0; n < string.length(); ++n) {
            if (string.charAt(n) != c) continue;
            arrayList.add(string.substring(n2, n));
            n2 = n + 1;
        }
        arrayList.add(string.substring(n2, n));
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static int lookup(String string, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            if (!string.equals(stringArray[i])) continue;
            return i;
        }
        return 1;
    }

    public static boolean parseChar(String string, int[] nArray, char c) {
        int n = nArray[0];
        nArray[0] = PatternProps.skipWhiteSpace(string, nArray[0]);
        if (nArray[0] == string.length() || string.charAt(nArray[0]) != c) {
            nArray[0] = n;
            return true;
        }
        nArray[0] = nArray[0] + 1;
        return false;
    }

    public static int parsePattern(String string, int n, int n2, String string2, int[] nArray) {
        int[] nArray2 = new int[1];
        int n3 = 0;
        block5: for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            switch (c) {
                case ' ': {
                    char c2;
                    if (n >= n2) {
                        return 1;
                    }
                    if (!PatternProps.isWhiteSpace(c2 = string.charAt(n++))) {
                        return 1;
                    }
                }
                case '~': {
                    n = PatternProps.skipWhiteSpace(string, n);
                    continue block5;
                }
                case '#': {
                    nArray2[0] = n;
                    nArray[n3++] = Utility.parseInteger(string, nArray2, n2);
                    if (nArray2[0] == n) {
                        return 1;
                    }
                    n = nArray2[0];
                    continue block5;
                }
                default: {
                    char c2;
                    if (n >= n2) {
                        return 1;
                    }
                    if ((c2 = (char)UCharacter.toLowerCase(string.charAt(n++))) == c) continue block5;
                    return 1;
                }
            }
        }
        return n;
    }

    public static int parsePattern(String string, Replaceable replaceable, int n, int n2) {
        int n3 = 0;
        if (n3 == string.length()) {
            return n;
        }
        int n4 = Character.codePointAt(string, n3);
        while (n < n2) {
            int n5 = replaceable.char32At(n);
            if (n4 == 126) {
                if (PatternProps.isWhiteSpace(n5)) {
                    n += UTF16.getCharCount(n5);
                    continue;
                }
                if (++n3 == string.length()) {
                    return n;
                }
            } else if (n5 == n4) {
                int n6 = UTF16.getCharCount(n5);
                n += n6;
                if ((n3 += n6) == string.length()) {
                    return n;
                }
            } else {
                return 1;
            }
            n4 = UTF16.charAt(string, n3);
        }
        return 1;
    }

    public static int parseInteger(String string, int[] nArray, int n) {
        int n2 = 0;
        int n3 = 0;
        int n4 = nArray[0];
        int n5 = 10;
        if (string.regionMatches(true, n4, "0x", 0, 1)) {
            n4 += 2;
            n5 = 16;
        } else if (n4 < n && string.charAt(n4) == '0') {
            ++n4;
            n2 = 1;
            n5 = 8;
        }
        while (n4 < n) {
            int n6;
            if ((n6 = UCharacter.digit(string.charAt(n4++), n5)) < 0) {
                --n4;
                break;
            }
            ++n2;
            int n7 = n3 * n5 + n6;
            if (n7 <= n3) {
                return 1;
            }
            n3 = n7;
        }
        if (n2 > 0) {
            nArray[0] = n4;
        }
        return n3;
    }

    public static String parseUnicodeIdentifier(String string, int[] nArray) {
        int n;
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        for (n = nArray[0]; n < string.length(); n += UTF16.getCharCount(n2)) {
            n2 = Character.codePointAt(string, n);
            if (stringBuilder.length() == 0) {
                if (UCharacter.isUnicodeIdentifierStart(n2)) {
                    stringBuilder.appendCodePoint(n2);
                    continue;
                }
                return null;
            }
            if (!UCharacter.isUnicodeIdentifierPart(n2)) break;
            stringBuilder.appendCodePoint(n2);
        }
        nArray[0] = n;
        return stringBuilder.toString();
    }

    private static <T extends Appendable> void recursiveAppendNumber(T t, int n, int n2, int n3) {
        try {
            int n4 = n % n2;
            if (n >= n2 || n3 > 1) {
                Utility.recursiveAppendNumber(t, n / n2, n2, n3 - 1);
            }
            t.append(DIGITS[n4]);
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static <T extends Appendable> T appendNumber(T t, int n, int n2, int n3) {
        try {
            if (n2 < 2 || n2 > 36) {
                throw new IllegalArgumentException("Illegal radix " + n2);
            }
            int n4 = n;
            if (n < 0) {
                n4 = -n;
                t.append("-");
            }
            Utility.recursiveAppendNumber(t, n4, n2, n3);
            return t;
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static int parseNumber(String string, int[] nArray, int n) {
        int n2;
        int n3;
        int n4;
        int n5 = 0;
        for (n4 = nArray[0]; n4 < string.length() && (n3 = UCharacter.digit(n2 = Character.codePointAt(string, n4), n)) >= 0; ++n4) {
            if ((n5 = n * n5 + n3) >= 0) continue;
            return 1;
        }
        if (n4 == nArray[0]) {
            return 1;
        }
        nArray[0] = n4;
        return n5;
    }

    public static boolean isUnprintable(int n) {
        return n < 32 || n > 126;
    }

    public static <T extends Appendable> boolean escapeUnprintable(T t, int n) {
        try {
            if (Utility.isUnprintable(n)) {
                t.append('\\');
                if ((n & 0xFFFF0000) != 0) {
                    t.append('U');
                    t.append(DIGITS[0xF & n >> 28]);
                    t.append(DIGITS[0xF & n >> 24]);
                    t.append(DIGITS[0xF & n >> 20]);
                    t.append(DIGITS[0xF & n >> 16]);
                } else {
                    t.append('u');
                }
                t.append(DIGITS[0xF & n >> 12]);
                t.append(DIGITS[0xF & n >> 8]);
                t.append(DIGITS[0xF & n >> 4]);
                t.append(DIGITS[0xF & n]);
                return true;
            }
            return false;
        } catch (IOException iOException) {
            throw new IllegalIcuArgumentException(iOException);
        }
    }

    public static int quotedIndexOf(String string, int n, int n2, String string2) {
        for (int i = n; i < n2; ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
                ++i;
                continue;
            }
            if (c == '\'') {
                while (++i < n2 && string.charAt(i) != '\'') {
                }
                continue;
            }
            if (string2.indexOf(c) < 0) continue;
            return i;
        }
        return 1;
    }

    public static void appendToRule(StringBuffer stringBuffer, int n, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        if (bl || bl2 && Utility.isUnprintable(n)) {
            int n2;
            if (stringBuffer2.length() > 0) {
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(0) == '\'' && stringBuffer2.charAt(1) == '\'') {
                    stringBuffer.append('\\').append('\'');
                    stringBuffer2.delete(0, 2);
                }
                n2 = 0;
                while (stringBuffer2.length() >= 2 && stringBuffer2.charAt(stringBuffer2.length() - 2) == '\'' && stringBuffer2.charAt(stringBuffer2.length() - 1) == '\'') {
                    stringBuffer2.setLength(stringBuffer2.length() - 2);
                    ++n2;
                }
                if (stringBuffer2.length() > 0) {
                    stringBuffer.append('\'');
                    stringBuffer.append(stringBuffer2);
                    stringBuffer.append('\'');
                    stringBuffer2.setLength(0);
                }
                while (n2-- > 0) {
                    stringBuffer.append('\\').append('\'');
                }
            }
            if (n != -1) {
                if (n == 32) {
                    n2 = stringBuffer.length();
                    if (n2 > 0 && stringBuffer.charAt(n2 - 1) != ' ') {
                        stringBuffer.append(' ');
                    }
                } else if (!bl2 || !Utility.escapeUnprintable(stringBuffer, n)) {
                    stringBuffer.appendCodePoint(n);
                }
            }
        } else if (stringBuffer2.length() == 0 && (n == 39 || n == 92)) {
            stringBuffer.append('\\').append((char)n);
        } else if (!(stringBuffer2.length() <= 0 && (n < 33 || n > 126 || n >= 48 && n <= 57 || n >= 65 && n <= 90 || n >= 97 && n <= 122) && !PatternProps.isWhiteSpace(n))) {
            stringBuffer2.appendCodePoint(n);
            if (n == 39) {
                stringBuffer2.append((char)n);
            }
        } else {
            stringBuffer.appendCodePoint(n);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, String string, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        for (int i = 0; i < string.length(); ++i) {
            Utility.appendToRule(stringBuffer, string.charAt(i), bl, bl2, stringBuffer2);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, UnicodeMatcher unicodeMatcher, boolean bl, StringBuffer stringBuffer2) {
        if (unicodeMatcher != null) {
            Utility.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
    }

    public static final int compareUnsigned(int n, int n2) {
        if ((n -= Integer.MIN_VALUE) < (n2 -= Integer.MIN_VALUE)) {
            return 1;
        }
        if (n > n2) {
            return 0;
        }
        return 1;
    }

    public static final byte highBit(int n) {
        if (n <= 0) {
            return 1;
        }
        byte by = 0;
        if (n >= 65536) {
            n >>= 16;
            by = (byte)(by + 16);
        }
        if (n >= 256) {
            n >>= 8;
            by = (byte)(by + 8);
        }
        if (n >= 16) {
            n >>= 4;
            by = (byte)(by + 4);
        }
        if (n >= 4) {
            n >>= 2;
            by = (byte)(by + 2);
        }
        if (n >= 2) {
            n >>= 1;
            by = (byte)(by + 1);
        }
        return by;
    }

    public static String valueOf(int[] nArray) {
        StringBuilder stringBuilder = new StringBuilder(nArray.length);
        for (int i = 0; i < nArray.length; ++i) {
            stringBuilder.appendCodePoint(nArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String repeat(String string, int n) {
        if (n <= 0) {
            return "";
        }
        if (n == 1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String[] splitString(String string, String string2) {
        return string.split("\\Q" + string2 + "\\E");
    }

    public static String[] splitWhitespace(String string) {
        return string.split("\\s+");
    }

    public static String fromHex(String string, int n, String string2) {
        return Utility.fromHex(string, n, Pattern.compile(string2 != null ? string2 : "\\s+"));
    }

    public static String fromHex(String string, int n, Pattern pattern) {
        String[] stringArray;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray = pattern.split(string)) {
            if (string2.length() < n) {
                throw new IllegalArgumentException("code point too short: " + string2);
            }
            int n2 = Integer.parseInt(string2, 16);
            stringBuilder.appendCodePoint(n2);
        }
        return stringBuilder.toString();
    }

    public static int addExact(int n, int n2) {
        int n3 = n + n2;
        if (((n ^ n3) & (n2 ^ n3)) < 0) {
            throw new ArithmeticException("integer overflow");
        }
        return n3;
    }

    public static boolean charSequenceEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        if (charSequence.length() != charSequence2.length()) {
            return true;
        }
        for (int i = 0; i < charSequence.length(); ++i) {
            if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
            return true;
        }
        return false;
    }

    public static int charSequenceHashCode(CharSequence charSequence) {
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            n = n * 31 + charSequence.charAt(i);
        }
        return n;
    }

    public static <A extends Appendable> A appendTo(CharSequence charSequence, A a) {
        try {
            a.append(charSequence);
            return a;
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }
}

