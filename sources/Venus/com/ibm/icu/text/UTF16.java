/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Replaceable;
import java.util.Comparator;

public final class UTF16 {
    public static final int SINGLE_CHAR_BOUNDARY = 1;
    public static final int LEAD_SURROGATE_BOUNDARY = 2;
    public static final int TRAIL_SURROGATE_BOUNDARY = 5;
    public static final int CODEPOINT_MIN_VALUE = 0;
    public static final int CODEPOINT_MAX_VALUE = 0x10FFFF;
    public static final int SUPPLEMENTARY_MIN_VALUE = 65536;
    public static final int LEAD_SURROGATE_MIN_VALUE = 55296;
    public static final int TRAIL_SURROGATE_MIN_VALUE = 56320;
    public static final int LEAD_SURROGATE_MAX_VALUE = 56319;
    public static final int TRAIL_SURROGATE_MAX_VALUE = 57343;
    public static final int SURROGATE_MIN_VALUE = 55296;
    public static final int SURROGATE_MAX_VALUE = 57343;
    private static final int LEAD_SURROGATE_BITMASK = -1024;
    private static final int TRAIL_SURROGATE_BITMASK = -1024;
    private static final int SURROGATE_BITMASK = -2048;
    private static final int LEAD_SURROGATE_BITS = 55296;
    private static final int TRAIL_SURROGATE_BITS = 56320;
    private static final int SURROGATE_BITS = 55296;
    private static final int LEAD_SURROGATE_SHIFT_ = 10;
    private static final int TRAIL_SURROGATE_MASK_ = 1023;
    private static final int LEAD_SURROGATE_OFFSET_ = 55232;

    private UTF16() {
    }

    public static int charAt(String string, int n) {
        char c = string.charAt(n);
        if (c < '\ud800') {
            return c;
        }
        return UTF16._charAt(string, n, c);
    }

    private static int _charAt(String string, int n, char c) {
        char c2;
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            char c3;
            if (string.length() != ++n && (c3 = string.charAt(n)) >= '\udc00' && c3 <= '\udfff') {
                return Character.toCodePoint(c, c3);
            }
        } else if (--n >= 0 && (c2 = string.charAt(n)) >= '\ud800' && c2 <= '\udbff') {
            return Character.toCodePoint(c2, c);
        }
        return c;
    }

    public static int charAt(CharSequence charSequence, int n) {
        char c = charSequence.charAt(n);
        if (c < '\ud800') {
            return c;
        }
        return UTF16._charAt(charSequence, n, c);
    }

    private static int _charAt(CharSequence charSequence, int n, char c) {
        char c2;
        if (c > '\udfff') {
            return c;
        }
        if (c <= '\udbff') {
            char c3;
            if (charSequence.length() != ++n && (c3 = charSequence.charAt(n)) >= '\udc00' && c3 <= '\udfff') {
                return Character.toCodePoint(c, c3);
            }
        } else if (--n >= 0 && (c2 = charSequence.charAt(n)) >= '\ud800' && c2 <= '\udbff') {
            return Character.toCodePoint(c2, c);
        }
        return c;
    }

    public static int charAt(StringBuffer stringBuffer, int n) {
        char c;
        if (n < 0 || n >= stringBuffer.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        char c2 = stringBuffer.charAt(n);
        if (!UTF16.isSurrogate(c2)) {
            return c2;
        }
        if (c2 <= '\udbff') {
            char c3;
            if (stringBuffer.length() != ++n && UTF16.isTrailSurrogate(c3 = stringBuffer.charAt(n))) {
                return Character.toCodePoint(c2, c3);
            }
        } else if (--n >= 0 && UTF16.isLeadSurrogate(c = stringBuffer.charAt(n))) {
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    public static int charAt(char[] cArray, int n, int n2, int n3) {
        if ((n3 += n) < n || n3 >= n2) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        char c = cArray[n3];
        if (!UTF16.isSurrogate(c)) {
            return c;
        }
        if (c <= '\udbff') {
            if (++n3 >= n2) {
                return c;
            }
            char c2 = cArray[n3];
            if (UTF16.isTrailSurrogate(c2)) {
                return Character.toCodePoint(c, c2);
            }
        } else {
            char c3;
            if (n3 == n) {
                return c;
            }
            if (UTF16.isLeadSurrogate(c3 = cArray[--n3])) {
                return Character.toCodePoint(c3, c);
            }
        }
        return c;
    }

    public static int charAt(Replaceable replaceable, int n) {
        char c;
        if (n < 0 || n >= replaceable.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        char c2 = replaceable.charAt(n);
        if (!UTF16.isSurrogate(c2)) {
            return c2;
        }
        if (c2 <= '\udbff') {
            char c3;
            if (replaceable.length() != ++n && UTF16.isTrailSurrogate(c3 = replaceable.charAt(n))) {
                return Character.toCodePoint(c2, c3);
            }
        } else if (--n >= 0 && UTF16.isLeadSurrogate(c = replaceable.charAt(n))) {
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    public static int getCharCount(int n) {
        if (n < 65536) {
            return 0;
        }
        return 1;
    }

    public static int bounds(String string, int n) {
        char c = string.charAt(n);
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c)) {
                if (++n < string.length() && UTF16.isTrailSurrogate(string.charAt(n))) {
                    return 1;
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(string.charAt(n))) {
                return 0;
            }
        }
        return 0;
    }

    public static int bounds(StringBuffer stringBuffer, int n) {
        char c = stringBuffer.charAt(n);
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c)) {
                if (++n < stringBuffer.length() && UTF16.isTrailSurrogate(stringBuffer.charAt(n))) {
                    return 1;
                }
            } else if (--n >= 0 && UTF16.isLeadSurrogate(stringBuffer.charAt(n))) {
                return 0;
            }
        }
        return 0;
    }

    public static int bounds(char[] cArray, int n, int n2, int n3) {
        if ((n3 += n) < n || n3 >= n2) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        char c = cArray[n3];
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c)) {
                if (++n3 < n2 && UTF16.isTrailSurrogate(cArray[n3])) {
                    return 1;
                }
            } else if (--n3 >= n && UTF16.isLeadSurrogate(cArray[n3])) {
                return 0;
            }
        }
        return 0;
    }

    public static boolean isSurrogate(char c) {
        return (c & 0xFFFFF800) == 55296;
    }

    public static boolean isTrailSurrogate(char c) {
        return (c & 0xFFFFFC00) == 56320;
    }

    public static boolean isLeadSurrogate(char c) {
        return (c & 0xFFFFFC00) == 55296;
    }

    public static char getLeadSurrogate(int n) {
        if (n >= 65536) {
            return (char)(55232 + (n >> 10));
        }
        return '\u0001';
    }

    public static char getTrailSurrogate(int n) {
        if (n >= 65536) {
            return (char)(56320 + (n & 0x3FF));
        }
        return (char)n;
    }

    public static String valueOf(int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Illegal codepoint");
        }
        return UTF16.toString(n);
    }

    public static String valueOf(String string, int n) {
        switch (UTF16.bounds(string, n)) {
            case 2: {
                return string.substring(n, n + 2);
            }
            case 5: {
                return string.substring(n - 1, n + 1);
            }
        }
        return string.substring(n, n + 1);
    }

    public static String valueOf(StringBuffer stringBuffer, int n) {
        switch (UTF16.bounds(stringBuffer, n)) {
            case 2: {
                return stringBuffer.substring(n, n + 2);
            }
            case 5: {
                return stringBuffer.substring(n - 1, n + 1);
            }
        }
        return stringBuffer.substring(n, n + 1);
    }

    public static String valueOf(char[] cArray, int n, int n2, int n3) {
        switch (UTF16.bounds(cArray, n, n2, n3)) {
            case 2: {
                return new String(cArray, n + n3, 2);
            }
            case 5: {
                return new String(cArray, n + n3 - 1, 2);
            }
        }
        return new String(cArray, n + n3, 1);
    }

    public static int findOffsetFromCodePoint(String string, int n) {
        int n2;
        int n3 = string.length();
        int n4 = 0;
        if (n < 0 || n > n3) {
            throw new StringIndexOutOfBoundsException(n);
        }
        for (n2 = n; n4 < n3 && n2 > 0; --n2, ++n4) {
            char c = string.charAt(n4);
            if (!UTF16.isLeadSurrogate(c) || n4 + 1 >= n3 || !UTF16.isTrailSurrogate(string.charAt(n4 + 1))) continue;
            ++n4;
        }
        if (n2 != 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return n4;
    }

    public static int findOffsetFromCodePoint(StringBuffer stringBuffer, int n) {
        int n2;
        int n3 = stringBuffer.length();
        int n4 = 0;
        if (n < 0 || n > n3) {
            throw new StringIndexOutOfBoundsException(n);
        }
        for (n2 = n; n4 < n3 && n2 > 0; --n2, ++n4) {
            char c = stringBuffer.charAt(n4);
            if (!UTF16.isLeadSurrogate(c) || n4 + 1 >= n3 || !UTF16.isTrailSurrogate(stringBuffer.charAt(n4 + 1))) continue;
            ++n4;
        }
        if (n2 != 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return n4;
    }

    public static int findOffsetFromCodePoint(char[] cArray, int n, int n2, int n3) {
        int n4;
        int n5 = n;
        if (n3 > n2 - n) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        for (n4 = n3; n5 < n2 && n4 > 0; --n4, ++n5) {
            char c = cArray[n5];
            if (!UTF16.isLeadSurrogate(c) || n5 + 1 >= n2 || !UTF16.isTrailSurrogate(cArray[n5 + 1])) continue;
            ++n5;
        }
        if (n4 != 0) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
        return n5 - n;
    }

    public static int findCodePointOffset(String string, int n) {
        if (n < 0 || n > string.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int n2 = 0;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (bl && UTF16.isTrailSurrogate(c)) {
                bl = false;
                continue;
            }
            bl = UTF16.isLeadSurrogate(c);
            ++n2;
        }
        if (n == string.length()) {
            return n2;
        }
        if (bl && UTF16.isTrailSurrogate(string.charAt(n))) {
            --n2;
        }
        return n2;
    }

    public static int findCodePointOffset(StringBuffer stringBuffer, int n) {
        if (n < 0 || n > stringBuffer.length()) {
            throw new StringIndexOutOfBoundsException(n);
        }
        int n2 = 0;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            char c = stringBuffer.charAt(i);
            if (bl && UTF16.isTrailSurrogate(c)) {
                bl = false;
                continue;
            }
            bl = UTF16.isLeadSurrogate(c);
            ++n2;
        }
        if (n == stringBuffer.length()) {
            return n2;
        }
        if (bl && UTF16.isTrailSurrogate(stringBuffer.charAt(n))) {
            --n2;
        }
        return n2;
    }

    public static int findCodePointOffset(char[] cArray, int n, int n2, int n3) {
        if ((n3 += n) > n2) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        int n4 = 0;
        boolean bl = false;
        for (int i = n; i < n3; ++i) {
            char c = cArray[i];
            if (bl && UTF16.isTrailSurrogate(c)) {
                bl = false;
                continue;
            }
            bl = UTF16.isLeadSurrogate(c);
            ++n4;
        }
        if (n3 == n2) {
            return n4;
        }
        if (bl && UTF16.isTrailSurrogate(cArray[n3])) {
            --n4;
        }
        return n4;
    }

    public static StringBuffer append(StringBuffer stringBuffer, int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Illegal codepoint: " + Integer.toHexString(n));
        }
        if (n >= 65536) {
            stringBuffer.append(UTF16.getLeadSurrogate(n));
            stringBuffer.append(UTF16.getTrailSurrogate(n));
        } else {
            stringBuffer.append((char)n);
        }
        return stringBuffer;
    }

    public static StringBuffer appendCodePoint(StringBuffer stringBuffer, int n) {
        return UTF16.append(stringBuffer, n);
    }

    public static int append(char[] cArray, int n, int n2) {
        if (n2 < 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Illegal codepoint");
        }
        if (n2 >= 65536) {
            cArray[n++] = UTF16.getLeadSurrogate(n2);
            cArray[n++] = UTF16.getTrailSurrogate(n2);
        } else {
            cArray[n++] = (char)n2;
        }
        return n;
    }

    public static int countCodePoint(String string) {
        if (string == null || string.length() == 0) {
            return 1;
        }
        return UTF16.findCodePointOffset(string, string.length());
    }

    public static int countCodePoint(StringBuffer stringBuffer) {
        if (stringBuffer == null || stringBuffer.length() == 0) {
            return 1;
        }
        return UTF16.findCodePointOffset(stringBuffer, stringBuffer.length());
    }

    public static int countCodePoint(char[] cArray, int n, int n2) {
        if (cArray == null || cArray.length == 0) {
            return 1;
        }
        return UTF16.findCodePointOffset(cArray, n, n2, n2 - n);
    }

    public static void setCharAt(StringBuffer stringBuffer, int n, int n2) {
        int n3 = 1;
        char c = stringBuffer.charAt(n);
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c) && stringBuffer.length() > n + 1 && UTF16.isTrailSurrogate(stringBuffer.charAt(n + 1))) {
                ++n3;
            } else if (UTF16.isTrailSurrogate(c) && n > 0 && UTF16.isLeadSurrogate(stringBuffer.charAt(n - 1))) {
                --n;
                ++n3;
            }
        }
        stringBuffer.replace(n, n + n3, UTF16.valueOf(n2));
    }

    public static int setCharAt(char[] cArray, int n, int n2, int n3) {
        if (n2 >= n) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        int n4 = 1;
        char c = cArray[n2];
        if (UTF16.isSurrogate(c)) {
            if (UTF16.isLeadSurrogate(c) && cArray.length > n2 + 1 && UTF16.isTrailSurrogate(cArray[n2 + 1])) {
                ++n4;
            } else if (UTF16.isTrailSurrogate(c) && n2 > 0 && UTF16.isLeadSurrogate(cArray[n2 - 1])) {
                --n2;
                ++n4;
            }
        }
        String string = UTF16.valueOf(n3);
        int n5 = n;
        int n6 = string.length();
        cArray[n2] = string.charAt(0);
        if (n4 == n6) {
            if (n4 == 2) {
                cArray[n2 + 1] = string.charAt(1);
            }
        } else {
            System.arraycopy(cArray, n2 + n4, cArray, n2 + n6, n - (n2 + n4));
            if (n4 < n6) {
                cArray[n2 + 1] = string.charAt(1);
                if (++n5 < cArray.length) {
                    cArray[n5] = '\u0000';
                }
            } else {
                cArray[--n5] = '\u0000';
            }
        }
        return n5;
    }

    public static int moveCodePointOffset(String string, int n, int n2) {
        int n3;
        int n4 = n;
        int n5 = string.length();
        if (n < 0 || n > n5) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > 0) {
            if (n2 + n > n5) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (n3 = n2; n4 < n5 && n3 > 0; --n3, ++n4) {
                char c = string.charAt(n4);
                if (!UTF16.isLeadSurrogate(c) || n4 + 1 >= n5 || !UTF16.isTrailSurrogate(string.charAt(n4 + 1))) continue;
                ++n4;
            }
        } else {
            if (n + n2 < 0) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (n3 = -n2; n3 > 0 && --n4 >= 0; --n3) {
                char c = string.charAt(n4);
                if (!UTF16.isTrailSurrogate(c) || n4 <= 0 || !UTF16.isLeadSurrogate(string.charAt(n4 - 1))) continue;
                --n4;
            }
        }
        if (n3 != 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        return n4;
    }

    public static int moveCodePointOffset(StringBuffer stringBuffer, int n, int n2) {
        int n3;
        int n4 = n;
        int n5 = stringBuffer.length();
        if (n < 0 || n > n5) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > 0) {
            if (n2 + n > n5) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (n3 = n2; n4 < n5 && n3 > 0; --n3, ++n4) {
                char c = stringBuffer.charAt(n4);
                if (!UTF16.isLeadSurrogate(c) || n4 + 1 >= n5 || !UTF16.isTrailSurrogate(stringBuffer.charAt(n4 + 1))) continue;
                ++n4;
            }
        } else {
            if (n + n2 < 0) {
                throw new StringIndexOutOfBoundsException(n);
            }
            for (n3 = -n2; n3 > 0 && --n4 >= 0; --n3) {
                char c = stringBuffer.charAt(n4);
                if (!UTF16.isTrailSurrogate(c) || n4 <= 0 || !UTF16.isLeadSurrogate(stringBuffer.charAt(n4 - 1))) continue;
                --n4;
            }
        }
        if (n3 != 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        return n4;
    }

    public static int moveCodePointOffset(char[] cArray, int n, int n2, int n3, int n4) {
        int n5;
        int n6 = cArray.length;
        int n7 = n3 + n;
        if (n < 0 || n2 < n) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > n6) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n3 < 0 || n7 > n2) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        if (n4 > 0) {
            if (n4 + n7 > n6) {
                throw new StringIndexOutOfBoundsException(n7);
            }
            for (n5 = n4; n7 < n2 && n5 > 0; --n5, ++n7) {
                char c = cArray[n7];
                if (!UTF16.isLeadSurrogate(c) || n7 + 1 >= n2 || !UTF16.isTrailSurrogate(cArray[n7 + 1])) continue;
                ++n7;
            }
        } else {
            if (n7 + n4 < n) {
                throw new StringIndexOutOfBoundsException(n7);
            }
            for (n5 = -n4; n5 > 0 && --n7 >= n; --n5) {
                char c = cArray[n7];
                if (!UTF16.isTrailSurrogate(c) || n7 <= n || !UTF16.isLeadSurrogate(cArray[n7 - 1])) continue;
                --n7;
            }
        }
        if (n5 != 0) {
            throw new StringIndexOutOfBoundsException(n4);
        }
        return n7 -= n;
    }

    public static StringBuffer insert(StringBuffer stringBuffer, int n, int n2) {
        String string = UTF16.valueOf(n2);
        if (n != stringBuffer.length() && UTF16.bounds(stringBuffer, n) == 5) {
            ++n;
        }
        stringBuffer.insert(n, string);
        return stringBuffer;
    }

    public static int insert(char[] cArray, int n, int n2, int n3) {
        int n4;
        String string = UTF16.valueOf(n3);
        if (n2 != n && UTF16.bounds(cArray, 0, n, n2) == 5) {
            ++n2;
        }
        if (n + (n4 = string.length()) > cArray.length) {
            throw new ArrayIndexOutOfBoundsException(n2 + n4);
        }
        System.arraycopy(cArray, n2, cArray, n2 + n4, n - n2);
        cArray[n2] = string.charAt(0);
        if (n4 == 2) {
            cArray[n2 + 1] = string.charAt(1);
        }
        return n + n4;
    }

    public static StringBuffer delete(StringBuffer stringBuffer, int n) {
        int n2 = 1;
        switch (UTF16.bounds(stringBuffer, n)) {
            case 2: {
                ++n2;
                break;
            }
            case 5: {
                ++n2;
                --n;
            }
        }
        stringBuffer.delete(n, n + n2);
        return stringBuffer;
    }

    public static int delete(char[] cArray, int n, int n2) {
        int n3 = 1;
        switch (UTF16.bounds(cArray, 0, n, n2)) {
            case 2: {
                ++n3;
                break;
            }
            case 5: {
                ++n3;
                --n2;
            }
        }
        System.arraycopy(cArray, n2 + n3, cArray, n2, n - (n2 + n3));
        cArray[n - n3] = '\u0000';
        return n - n3;
    }

    public static int indexOf(String string, int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || n > 57343 && n < 65536) {
            return string.indexOf((char)n);
        }
        if (n < 65536) {
            int n2 = string.indexOf((char)n);
            if (n2 >= 0) {
                if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                    return UTF16.indexOf(string, n, n2 + 1);
                }
                if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                    return UTF16.indexOf(string, n, n2 + 1);
                }
            }
            return n2;
        }
        String string2 = UTF16.toString(n);
        return string.indexOf(string2);
    }

    public static int indexOf(String string, String string2) {
        int n = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n - 1))) {
            return string.indexOf(string2);
        }
        int n2 = string.indexOf(string2);
        int n3 = n2 + n;
        if (n2 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n - 1)) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n3 + 1))) {
                return UTF16.indexOf(string, string2, n3 + 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                return UTF16.indexOf(string, string2, n3 + 1);
            }
        }
        return n2;
    }

    public static int indexOf(String string, int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || n > 57343 && n < 65536) {
            return string.indexOf((char)n, n2);
        }
        if (n < 65536) {
            int n3 = string.indexOf((char)n, n2);
            if (n3 >= 0) {
                if (UTF16.isLeadSurrogate((char)n) && n3 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n3 + 1))) {
                    return UTF16.indexOf(string, n, n3 + 1);
                }
                if (n3 > 0 && UTF16.isLeadSurrogate(string.charAt(n3 - 1))) {
                    return UTF16.indexOf(string, n, n3 + 1);
                }
            }
            return n3;
        }
        String string2 = UTF16.toString(n);
        return string.indexOf(string2, n2);
    }

    public static int indexOf(String string, String string2, int n) {
        int n2 = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n2 - 1))) {
            return string.indexOf(string2, n);
        }
        int n3 = string.indexOf(string2, n);
        int n4 = n3 + n2;
        if (n3 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n2 - 1)) && n3 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n4))) {
                return UTF16.indexOf(string, string2, n4 + 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n3 > 0 && UTF16.isLeadSurrogate(string.charAt(n3 - 1))) {
                return UTF16.indexOf(string, string2, n4 + 1);
            }
        }
        return n3;
    }

    public static int lastIndexOf(String string, int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || n > 57343 && n < 65536) {
            return string.lastIndexOf((char)n);
        }
        if (n < 65536) {
            int n2 = string.lastIndexOf((char)n);
            if (n2 >= 0) {
                if (UTF16.isLeadSurrogate((char)n) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + 1))) {
                    return UTF16.lastIndexOf(string, n, n2 - 1);
                }
                if (n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                    return UTF16.lastIndexOf(string, n, n2 - 1);
                }
            }
            return n2;
        }
        String string2 = UTF16.toString(n);
        return string.lastIndexOf(string2);
    }

    public static int lastIndexOf(String string, String string2) {
        int n = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n - 1))) {
            return string.lastIndexOf(string2);
        }
        int n2 = string.lastIndexOf(string2);
        if (n2 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n - 1)) && n2 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n2 + n + 1))) {
                return UTF16.lastIndexOf(string, string2, n2 - 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n2 > 0 && UTF16.isLeadSurrogate(string.charAt(n2 - 1))) {
                return UTF16.lastIndexOf(string, string2, n2 - 1);
            }
        }
        return n2;
    }

    public static int lastIndexOf(String string, int n, int n2) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Argument char32 is not a valid codepoint");
        }
        if (n < 55296 || n > 57343 && n < 65536) {
            return string.lastIndexOf((char)n, n2);
        }
        if (n < 65536) {
            int n3 = string.lastIndexOf((char)n, n2);
            if (n3 >= 0) {
                if (UTF16.isLeadSurrogate((char)n) && n3 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n3 + 1))) {
                    return UTF16.lastIndexOf(string, n, n3 - 1);
                }
                if (n3 > 0 && UTF16.isLeadSurrogate(string.charAt(n3 - 1))) {
                    return UTF16.lastIndexOf(string, n, n3 - 1);
                }
            }
            return n3;
        }
        String string2 = UTF16.toString(n);
        return string.lastIndexOf(string2, n2);
    }

    public static int lastIndexOf(String string, String string2, int n) {
        int n2 = string2.length();
        if (!UTF16.isTrailSurrogate(string2.charAt(0)) && !UTF16.isLeadSurrogate(string2.charAt(n2 - 1))) {
            return string.lastIndexOf(string2, n);
        }
        int n3 = string.lastIndexOf(string2, n);
        if (n3 >= 0) {
            if (UTF16.isLeadSurrogate(string2.charAt(n2 - 1)) && n3 < string.length() - 1 && UTF16.isTrailSurrogate(string.charAt(n3 + n2))) {
                return UTF16.lastIndexOf(string, string2, n3 - 1);
            }
            if (UTF16.isTrailSurrogate(string2.charAt(0)) && n3 > 0 && UTF16.isLeadSurrogate(string.charAt(n3 - 1))) {
                return UTF16.lastIndexOf(string, string2, n3 - 1);
            }
        }
        return n3;
    }

    public static String replace(String string, int n, int n2) {
        if (n <= 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException("Argument oldChar32 is not a valid codepoint");
        }
        if (n2 <= 0 || n2 > 0x10FFFF) {
            throw new IllegalArgumentException("Argument newChar32 is not a valid codepoint");
        }
        int n3 = UTF16.indexOf(string, n);
        if (n3 == -1) {
            return string;
        }
        String string2 = UTF16.toString(n2);
        int n4 = 1;
        int n5 = string2.length();
        StringBuffer stringBuffer = new StringBuffer(string);
        int n6 = n3;
        if (n >= 65536) {
            n4 = 2;
        }
        while (n3 != -1) {
            int n7 = n6 + n4;
            stringBuffer.replace(n6, n7, string2);
            int n8 = n3 + n4;
            n3 = UTF16.indexOf(string, n, n8);
            n6 += n5 + n3 - n8;
        }
        return stringBuffer.toString();
    }

    public static String replace(String string, String string2, String string3) {
        int n = UTF16.indexOf(string, string2);
        if (n == -1) {
            return string;
        }
        int n2 = string2.length();
        int n3 = string3.length();
        StringBuffer stringBuffer = new StringBuffer(string);
        int n4 = n;
        while (n != -1) {
            int n5 = n4 + n2;
            stringBuffer.replace(n4, n5, string3);
            int n6 = n + n2;
            n = UTF16.indexOf(string, string2, n6);
            n4 += n3 + n - n6;
        }
        return stringBuffer.toString();
    }

    public static StringBuffer reverse(StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        StringBuffer stringBuffer2 = new StringBuffer(n);
        int n2 = n;
        while (n2-- > 0) {
            char c;
            char c2 = stringBuffer.charAt(n2);
            if (UTF16.isTrailSurrogate(c2) && n2 > 0 && UTF16.isLeadSurrogate(c = stringBuffer.charAt(n2 - 1))) {
                stringBuffer2.append(c);
                stringBuffer2.append(c2);
                --n2;
                continue;
            }
            stringBuffer2.append(c2);
        }
        return stringBuffer2;
    }

    public static boolean hasMoreCodePointsThan(String string, int n) {
        if (n < 0) {
            return false;
        }
        if (string == null) {
            return true;
        }
        int n2 = string.length();
        if (n2 + 1 >> 1 > n) {
            return false;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return true;
        }
        int n4 = 0;
        while (n2 != 0) {
            if (n == 0) {
                return false;
            }
            if (UTF16.isLeadSurrogate(string.charAt(n4++)) && n4 != n2 && UTF16.isTrailSurrogate(string.charAt(n4))) {
                ++n4;
                if (--n3 <= 0) {
                    return true;
                }
            }
            --n;
        }
        return true;
    }

    public static boolean hasMoreCodePointsThan(char[] cArray, int n, int n2, int n3) {
        int n4 = n2 - n;
        if (n4 < 0 || n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException("Start and limit indexes should be non-negative and start <= limit");
        }
        if (n3 < 0) {
            return false;
        }
        if (cArray == null) {
            return true;
        }
        if (n4 + 1 >> 1 > n3) {
            return false;
        }
        int n5 = n4 - n3;
        if (n5 <= 0) {
            return true;
        }
        while (n4 != 0) {
            if (n3 == 0) {
                return false;
            }
            if (UTF16.isLeadSurrogate(cArray[n++]) && n != n2 && UTF16.isTrailSurrogate(cArray[n])) {
                ++n;
                if (--n5 <= 0) {
                    return true;
                }
            }
            --n3;
        }
        return true;
    }

    public static boolean hasMoreCodePointsThan(StringBuffer stringBuffer, int n) {
        if (n < 0) {
            return false;
        }
        if (stringBuffer == null) {
            return true;
        }
        int n2 = stringBuffer.length();
        if (n2 + 1 >> 1 > n) {
            return false;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return true;
        }
        int n4 = 0;
        while (n2 != 0) {
            if (n == 0) {
                return false;
            }
            if (UTF16.isLeadSurrogate(stringBuffer.charAt(n4++)) && n4 != n2 && UTF16.isTrailSurrogate(stringBuffer.charAt(n4))) {
                ++n4;
                if (--n3 <= 0) {
                    return true;
                }
            }
            --n;
        }
        return true;
    }

    public static String newString(int[] nArray, int n, int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        char[] cArray = new char[n2];
        int n3 = 0;
        int n4 = n + n2;
        block2: for (int i = n; i < n4; ++i) {
            int n5 = nArray[i];
            if (n5 < 0 || n5 > 0x10FFFF) {
                throw new IllegalArgumentException();
            }
            while (true) {
                try {
                    if (n5 < 65536) {
                        cArray[n3] = (char)n5;
                        ++n3;
                        continue block2;
                    }
                    cArray[n3] = (char)(55232 + (n5 >> 10));
                    cArray[n3 + 1] = (char)(56320 + (n5 & 0x3FF));
                    n3 += 2;
                    continue block2;
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    int n6 = (int)Math.ceil((double)nArray.length * (double)(n3 + 2) / (double)(i - n + 1));
                    char[] cArray2 = new char[n6];
                    System.arraycopy(cArray, 0, cArray2, 0, n3);
                    cArray = cArray2;
                    continue;
                }
                break;
            }
        }
        return new String(cArray, 0, n3);
    }

    public static int getSingleCodePoint(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return 1;
        }
        if (charSequence.length() == 1) {
            return charSequence.charAt(0);
        }
        if (charSequence.length() > 2) {
            return 1;
        }
        int n = Character.codePointAt(charSequence, 0);
        if (n > 65535) {
            return n;
        }
        return 1;
    }

    public static int compareCodePoint(int n, CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        int n2 = charSequence.length();
        if (n2 == 0) {
            return 0;
        }
        int n3 = Character.codePointAt(charSequence, 0);
        int n4 = n - n3;
        if (n4 != 0) {
            return n4;
        }
        return n2 == Character.charCount(n) ? 0 : -1;
    }

    private static String toString(int n) {
        if (n < 65536) {
            return String.valueOf((char)n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UTF16.getLeadSurrogate(n));
        stringBuilder.append(UTF16.getTrailSurrogate(n));
        return stringBuilder.toString();
    }

    public static final class StringComparator
    implements Comparator<String> {
        public static final int FOLD_CASE_DEFAULT = 0;
        public static final int FOLD_CASE_EXCLUDE_SPECIAL_I = 1;
        private int m_codePointCompare_;
        private int m_foldCase_;
        private boolean m_ignoreCase_;
        private static final int CODE_POINT_COMPARE_SURROGATE_OFFSET_ = 10240;

        public StringComparator() {
            this(false, false, 0);
        }

        public StringComparator(boolean bl, boolean bl2, int n) {
            this.setCodePointCompare(bl);
            this.m_ignoreCase_ = bl2;
            if (n < 0 || n > 1) {
                throw new IllegalArgumentException("Invalid fold case option");
            }
            this.m_foldCase_ = n;
        }

        public void setCodePointCompare(boolean bl) {
            this.m_codePointCompare_ = bl ? 32768 : 0;
        }

        public void setIgnoreCase(boolean bl, int n) {
            this.m_ignoreCase_ = bl;
            if (n < 0 || n > 1) {
                throw new IllegalArgumentException("Invalid fold case option");
            }
            this.m_foldCase_ = n;
        }

        public boolean getCodePointCompare() {
            return this.m_codePointCompare_ == 32768;
        }

        public boolean getIgnoreCase() {
            return this.m_ignoreCase_;
        }

        public int getIgnoreCaseOption() {
            return this.m_foldCase_;
        }

        @Override
        public int compare(String string, String string2) {
            if (Utility.sameObjects(string, string2)) {
                return 1;
            }
            if (string == null) {
                return 1;
            }
            if (string2 == null) {
                return 0;
            }
            if (this.m_ignoreCase_) {
                return this.compareCaseInsensitive(string, string2);
            }
            return this.compareCaseSensitive(string, string2);
        }

        private int compareCaseInsensitive(String string, String string2) {
            return Normalizer.cmpEquivFold(string, string2, this.m_foldCase_ | this.m_codePointCompare_ | 0x10000);
        }

        private int compareCaseSensitive(String string, String string2) {
            boolean bl;
            int n;
            int n2 = string.length();
            int n3 = string2.length();
            int n4 = n2;
            int n5 = 0;
            if (n2 < n3) {
                n5 = -1;
            } else if (n2 > n3) {
                n5 = 1;
                n4 = n3;
            }
            char c = '\u0000';
            char c2 = '\u0000';
            for (n = 0; n < n4 && (c = string.charAt(n)) == (c2 = string2.charAt(n)); ++n) {
            }
            if (n == n4) {
                return n5;
            }
            boolean bl2 = bl = this.m_codePointCompare_ == 32768;
            if (c >= '\ud800' && c2 >= '\ud800' && bl) {
                if (!(c <= '\udbff' && n + 1 != n2 && UTF16.isTrailSurrogate(string.charAt(n + 1)) || UTF16.isTrailSurrogate(c) && n != 0 && UTF16.isLeadSurrogate(string.charAt(n - 1)))) {
                    c = (char)(c - 10240);
                }
                if (!(c2 <= '\udbff' && n + 1 != n3 && UTF16.isTrailSurrogate(string2.charAt(n + 1)) || UTF16.isTrailSurrogate(c2) && n != 0 && UTF16.isLeadSurrogate(string2.charAt(n - 1)))) {
                    c2 = (char)(c2 - 10240);
                }
            }
            return c - c2;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((String)object, (String)object2);
        }
    }
}

