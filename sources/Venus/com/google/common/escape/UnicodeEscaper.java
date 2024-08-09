/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.Escaper;
import com.google.common.escape.Platform;

@Beta
@GwtCompatible
public abstract class UnicodeEscaper
extends Escaper {
    private static final int DEST_PAD = 32;

    protected UnicodeEscaper() {
    }

    protected abstract char[] escape(int var1);

    protected int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        int n3;
        int n4;
        for (n3 = n; n3 < n2 && (n4 = UnicodeEscaper.codePointAt(charSequence, n3, n2)) >= 0 && this.escape(n4) == null; n3 += Character.isSupplementaryCodePoint(n4) ? 2 : 1) {
        }
        return n3;
    }

    @Override
    public String escape(String string) {
        Preconditions.checkNotNull(string);
        int n = string.length();
        int n2 = this.nextEscapeIndex(string, 0, n);
        return n2 == n ? string : this.escapeSlow(string, n2);
    }

    protected final String escapeSlow(String string, int n) {
        int n2;
        int n3 = string.length();
        char[] cArray = Platform.charBufferFromThreadLocal();
        int n4 = 0;
        int n5 = 0;
        while (n < n3) {
            n2 = UnicodeEscaper.codePointAt(string, n, n3);
            if (n2 < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] cArray2 = this.escape(n2);
            int n6 = n + (Character.isSupplementaryCodePoint(n2) ? 2 : 1);
            if (cArray2 != null) {
                int n7 = n - n5;
                int n8 = n4 + n7 + cArray2.length;
                if (cArray.length < n8) {
                    int n9 = n8 + (n3 - n) + 32;
                    cArray = UnicodeEscaper.growBuffer(cArray, n4, n9);
                }
                if (n7 > 0) {
                    string.getChars(n5, n, cArray, n4);
                    n4 += n7;
                }
                if (cArray2.length > 0) {
                    System.arraycopy(cArray2, 0, cArray, n4, cArray2.length);
                    n4 += cArray2.length;
                }
                n5 = n6;
            }
            n = this.nextEscapeIndex(string, n6, n3);
        }
        n2 = n3 - n5;
        if (n2 > 0) {
            int n10 = n4 + n2;
            if (cArray.length < n10) {
                cArray = UnicodeEscaper.growBuffer(cArray, n4, n10);
            }
            string.getChars(n5, n3, cArray, n4);
            n4 = n10;
        }
        return new String(cArray, 0, n4);
    }

    protected static int codePointAt(CharSequence charSequence, int n, int n2) {
        Preconditions.checkNotNull(charSequence);
        if (n < n2) {
            char c;
            if ((c = charSequence.charAt(n++)) < '\ud800' || c > '\udfff') {
                return c;
            }
            if (c <= '\udbff') {
                if (n == n2) {
                    return -c;
                }
                char c2 = charSequence.charAt(n);
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c, c2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + n + " in '" + charSequence + "'");
            }
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c + " at index " + (n - 1) + " in '" + charSequence + "'");
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static char[] growBuffer(char[] cArray, int n, int n2) {
        if (n2 < 0) {
            throw new AssertionError((Object)"Cannot increase internal buffer any further");
        }
        char[] cArray2 = new char[n2];
        if (n > 0) {
            System.arraycopy(cArray, 0, cArray2, 0, n);
        }
        return cArray2;
    }
}

