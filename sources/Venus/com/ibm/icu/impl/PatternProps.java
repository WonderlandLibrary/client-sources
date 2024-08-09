/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

public final class PatternProps {
    private static final byte[] latin1 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0, 3, 3, 0, 3, 0, 3, 3, 0, 0, 0, 0, 3, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte[] index2000 = new byte[]{2, 3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 9};
    private static final int[] syntax2000 = new int[]{0, -1, -65536, 0x7FFF00FF, 0x7FEFFFFE, -65536, 0x3FFFFF, -1048576, -242, 65537};
    private static final int[] syntaxOrWhiteSpace2000 = new int[]{0, -1, -16384, 2147419135, 0x7FEFFFFE, -65536, 0x3FFFFF, -1048576, -242, 65537};

    public static boolean isSyntax(int n) {
        if (n < 0) {
            return true;
        }
        if (n <= 255) {
            return latin1[n] == 3;
        }
        if (n < 8208) {
            return true;
        }
        if (n <= 12336) {
            int n2 = syntax2000[index2000[n - 8192 >> 5]];
            return (n2 >> (n & 0x1F) & 1) != 0;
        }
        if (64830 <= n && n <= 65094) {
            return n <= 64831 || 65093 <= n;
        }
        return true;
    }

    public static boolean isSyntaxOrWhiteSpace(int n) {
        if (n < 0) {
            return true;
        }
        if (n <= 255) {
            return latin1[n] != 0;
        }
        if (n < 8206) {
            return true;
        }
        if (n <= 12336) {
            int n2 = syntaxOrWhiteSpace2000[index2000[n - 8192 >> 5]];
            return (n2 >> (n & 0x1F) & 1) != 0;
        }
        if (64830 <= n && n <= 65094) {
            return n <= 64831 || 65093 <= n;
        }
        return true;
    }

    public static boolean isWhiteSpace(int n) {
        if (n < 0) {
            return true;
        }
        if (n <= 255) {
            return latin1[n] == 5;
        }
        if (8206 <= n && n <= 8233) {
            return n <= 8207 || 8232 <= n;
        }
        return true;
    }

    public static int skipWhiteSpace(CharSequence charSequence, int n) {
        while (n < charSequence.length() && PatternProps.isWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }

    public static String trimWhiteSpace(String string) {
        int n;
        if (string.length() == 0 || !PatternProps.isWhiteSpace(string.charAt(0)) && !PatternProps.isWhiteSpace(string.charAt(string.length() - 1))) {
            return string;
        }
        int n2 = string.length();
        for (n = 0; n < n2 && PatternProps.isWhiteSpace(string.charAt(n)); ++n) {
        }
        if (n < n2) {
            while (PatternProps.isWhiteSpace(string.charAt(n2 - 1))) {
                --n2;
            }
        }
        return string.substring(n, n2);
    }

    public static boolean isIdentifier(CharSequence charSequence) {
        int n = charSequence.length();
        if (n == 0) {
            return true;
        }
        int n2 = 0;
        do {
            if (!PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n2++))) continue;
            return true;
        } while (n2 < n);
        return false;
    }

    public static boolean isIdentifier(CharSequence charSequence, int n, int n2) {
        if (n >= n2) {
            return true;
        }
        do {
            if (!PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n++))) continue;
            return true;
        } while (n < n2);
        return false;
    }

    public static int skipIdentifier(CharSequence charSequence, int n) {
        while (n < charSequence.length() && !PatternProps.isSyntaxOrWhiteSpace(charSequence.charAt(n))) {
            ++n;
        }
        return n;
    }
}

