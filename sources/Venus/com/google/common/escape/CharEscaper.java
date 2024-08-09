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
public abstract class CharEscaper
extends Escaper {
    private static final int DEST_PAD_MULTIPLIER = 2;

    protected CharEscaper() {
    }

    @Override
    public String escape(String string) {
        Preconditions.checkNotNull(string);
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (this.escape(string.charAt(i)) == null) continue;
            return this.escapeSlow(string, i);
        }
        return string;
    }

    protected final String escapeSlow(String string, int n) {
        int n2;
        int n3 = string.length();
        char[] cArray = Platform.charBufferFromThreadLocal();
        int n4 = cArray.length;
        int n5 = 0;
        int n6 = 0;
        while (n < n3) {
            char[] cArray2 = this.escape(string.charAt(n));
            if (cArray2 != null) {
                int n7 = n - n6;
                n2 = cArray2.length;
                int n8 = n5 + n7 + n2;
                if (n4 < n8) {
                    n4 = n8 + 2 * (n3 - n);
                    cArray = CharEscaper.growBuffer(cArray, n5, n4);
                }
                if (n7 > 0) {
                    string.getChars(n6, n, cArray, n5);
                    n5 += n7;
                }
                if (n2 > 0) {
                    System.arraycopy(cArray2, 0, cArray, n5, n2);
                    n5 += n2;
                }
                n6 = n + 1;
            }
            ++n;
        }
        int n9 = n3 - n6;
        if (n9 > 0) {
            n2 = n5 + n9;
            if (n4 < n2) {
                cArray = CharEscaper.growBuffer(cArray, n5, n2);
            }
            string.getChars(n6, n3, cArray, n5);
            n5 = n2;
        }
        return new String(cArray, 0, n5);
    }

    protected abstract char[] escape(char var1);

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

