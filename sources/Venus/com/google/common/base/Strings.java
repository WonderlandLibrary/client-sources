/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

@GwtCompatible
public final class Strings {
    private Strings() {
    }

    public static String nullToEmpty(@Nullable String string) {
        return string == null ? "" : string;
    }

    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return Strings.isNullOrEmpty(string) ? null : string;
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return Platform.stringIsNullOrEmpty(string);
    }

    public static String padStart(String string, int n, char c) {
        Preconditions.checkNotNull(string);
        if (string.length() >= n) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = string.length(); i < n; ++i) {
            stringBuilder.append(c);
        }
        stringBuilder.append(string);
        return stringBuilder.toString();
    }

    public static String padEnd(String string, int n, char c) {
        Preconditions.checkNotNull(string);
        if (string.length() >= n) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(string);
        for (int i = string.length(); i < n; ++i) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String repeat(String string, int n) {
        int n2;
        Preconditions.checkNotNull(string);
        if (n <= 1) {
            Preconditions.checkArgument(n >= 0, "invalid count: %s", n);
            return n == 0 ? "" : string;
        }
        int n3 = string.length();
        long l = (long)n3 * (long)n;
        int n4 = (int)l;
        if ((long)n4 != l) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + l);
        }
        char[] cArray = new char[n4];
        string.getChars(0, n3, cArray, 0);
        for (n2 = n3; n2 < n4 - n2; n2 <<= 1) {
            System.arraycopy(cArray, 0, cArray, n2, n2);
        }
        System.arraycopy(cArray, 0, cArray, n2, n4 - n2);
        return new String(cArray);
    }

    public static String commonPrefix(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && charSequence.charAt(n) == charSequence2.charAt(n); ++n) {
        }
        if (Strings.validSurrogatePairAt(charSequence, n - 1) || Strings.validSurrogatePairAt(charSequence2, n - 1)) {
            --n;
        }
        return charSequence.subSequence(0, n).toString();
    }

    public static String commonSuffix(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        Preconditions.checkNotNull(charSequence);
        Preconditions.checkNotNull(charSequence2);
        int n2 = Math.min(charSequence.length(), charSequence2.length());
        for (n = 0; n < n2 && charSequence.charAt(charSequence.length() - n - 1) == charSequence2.charAt(charSequence2.length() - n - 1); ++n) {
        }
        if (Strings.validSurrogatePairAt(charSequence, charSequence.length() - n - 1) || Strings.validSurrogatePairAt(charSequence2, charSequence2.length() - n - 1)) {
            --n;
        }
        return charSequence.subSequence(charSequence.length() - n, charSequence.length()).toString();
    }

    @VisibleForTesting
    static boolean validSurrogatePairAt(CharSequence charSequence, int n) {
        return n >= 0 && n <= charSequence.length() - 2 && Character.isHighSurrogate(charSequence.charAt(n)) && Character.isLowSurrogate(charSequence.charAt(n + 1));
    }
}

