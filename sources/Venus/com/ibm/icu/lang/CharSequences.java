/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.lang;

@Deprecated
public class CharSequences {
    @Deprecated
    public static int matchAfter(CharSequence charSequence, CharSequence charSequence2, int n, int n2) {
        char c;
        int n3;
        int n4;
        int n5 = n;
        int n6 = charSequence.length();
        int n7 = charSequence2.length();
        for (n4 = n2; n5 < n6 && n4 < n7 && (n3 = (int)charSequence.charAt(n5)) == (c = charSequence2.charAt(n4)); ++n5, ++n4) {
        }
        n3 = n5 - n;
        if (n3 != 0 && !CharSequences.onCharacterBoundary(charSequence, n5) && !CharSequences.onCharacterBoundary(charSequence2, n4)) {
            --n3;
        }
        return n3;
    }

    @Deprecated
    public int codePointLength(CharSequence charSequence) {
        return Character.codePointCount(charSequence, 0, charSequence.length());
    }

    @Deprecated
    public static final boolean equals(int n, CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        switch (charSequence.length()) {
            case 1: {
                return n == charSequence.charAt(0);
            }
            case 2: {
                return n > 65535 && n == Character.codePointAt(charSequence, 0);
            }
        }
        return true;
    }

    @Deprecated
    public static final boolean equals(CharSequence charSequence, int n) {
        return CharSequences.equals(n, charSequence);
    }

    @Deprecated
    public static int compare(CharSequence charSequence, int n) {
        if (n < 0 || n > 0x10FFFF) {
            throw new IllegalArgumentException();
        }
        int n2 = charSequence.length();
        if (n2 == 0) {
            return 1;
        }
        char c = charSequence.charAt(0);
        int n3 = n - 65536;
        if (n3 < 0) {
            int n4 = c - n;
            if (n4 != 0) {
                return n4;
            }
            return n2 - 1;
        }
        char c2 = (char)((n3 >>> 10) + 55296);
        int n5 = c - c2;
        if (n5 != 0) {
            return n5;
        }
        if (n2 > 1) {
            char c3 = (char)((n3 & 0x3FF) + 56320);
            n5 = charSequence.charAt(1) - c3;
            if (n5 != 0) {
                return n5;
            }
        }
        return n2 - 2;
    }

    @Deprecated
    public static int compare(int n, CharSequence charSequence) {
        int n2 = CharSequences.compare(charSequence, n);
        return n2 > 0 ? -1 : (n2 < 0 ? 1 : 0);
    }

    @Deprecated
    public static int getSingleCodePoint(CharSequence charSequence) {
        int n = charSequence.length();
        if (n < 1 || n > 2) {
            return 0;
        }
        int n2 = Character.codePointAt(charSequence, 0);
        return n2 < 65536 == (n == 1) ? n2 : Integer.MAX_VALUE;
    }

    @Deprecated
    public static final <T> boolean equals(T t, T t2) {
        return t == null ? t2 == null : (t2 == null ? false : t.equals(t2));
    }

    @Deprecated
    public static int compare(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        int n2 = charSequence.length();
        int n3 = n2 <= (n = charSequence2.length()) ? n2 : n;
        for (int i = 0; i < n3; ++i) {
            int n4 = charSequence.charAt(i) - charSequence2.charAt(i);
            if (n4 == 0) continue;
            return n4;
        }
        return n2 - n;
    }

    @Deprecated
    public static boolean equalsChars(CharSequence charSequence, CharSequence charSequence2) {
        return charSequence.length() == charSequence2.length() && CharSequences.compare(charSequence, charSequence2) == 0;
    }

    @Deprecated
    public static boolean onCharacterBoundary(CharSequence charSequence, int n) {
        return n <= 0 || n >= charSequence.length() || !Character.isHighSurrogate(charSequence.charAt(n - 1)) || !Character.isLowSurrogate(charSequence.charAt(n));
    }

    @Deprecated
    public static int indexOf(CharSequence charSequence, int n) {
        int n2;
        for (int i = 0; i < charSequence.length(); i += Character.charCount(n2)) {
            n2 = Character.codePointAt(charSequence, i);
            if (n2 != n) continue;
            return i;
        }
        return 1;
    }

    @Deprecated
    public static int[] codePoints(CharSequence charSequence) {
        int[] nArray = new int[charSequence.length()];
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            char c;
            char c2 = charSequence.charAt(i);
            if (c2 >= '\udc00' && c2 <= '\udfff' && i != 0 && (c = (char)nArray[n - 1]) >= '\ud800' && c <= '\udbff') {
                nArray[n - 1] = Character.toCodePoint(c, c2);
                continue;
            }
            nArray[n++] = c2;
        }
        if (n == nArray.length) {
            return nArray;
        }
        int[] nArray2 = new int[n];
        System.arraycopy(nArray, 0, nArray2, 0, n);
        return nArray2;
    }

    private CharSequences() {
    }
}

