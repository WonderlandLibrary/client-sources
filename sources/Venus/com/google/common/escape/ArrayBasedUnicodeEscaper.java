/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedEscaperMap;
import com.google.common.escape.UnicodeEscaper;
import java.util.Map;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public abstract class ArrayBasedUnicodeEscaper
extends UnicodeEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMin;
    private final int safeMax;
    private final char safeMinChar;
    private final char safeMaxChar;

    protected ArrayBasedUnicodeEscaper(Map<Character, String> map, int n, int n2, @Nullable String string) {
        this(ArrayBasedEscaperMap.create(map), n, n2, string);
    }

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, int n, int n2, @Nullable String string) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.replacements = arrayBasedEscaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        if (n2 < n) {
            n2 = -1;
            n = Integer.MAX_VALUE;
        }
        this.safeMin = n;
        this.safeMax = n2;
        if (n >= 55296) {
            this.safeMinChar = (char)65535;
            this.safeMaxChar = '\u0000';
        } else {
            this.safeMinChar = (char)n;
            this.safeMaxChar = (char)Math.min(n2, 55295);
        }
    }

    @Override
    public final String escape(String string) {
        Preconditions.checkNotNull(string);
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ((c >= this.replacementsLength || this.replacements[c] == null) && c <= this.safeMaxChar && c >= this.safeMinChar) continue;
            return this.escapeSlow(string, i);
        }
        return string;
    }

    @Override
    protected final int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        char c;
        while (n < n2 && ((c = charSequence.charAt(n)) >= this.replacementsLength || this.replacements[c] == null) && c <= this.safeMaxChar && c >= this.safeMinChar) {
            ++n;
        }
        return n;
    }

    @Override
    protected final char[] escape(int n) {
        char[] cArray;
        if (n < this.replacementsLength && (cArray = this.replacements[n]) != null) {
            return cArray;
        }
        if (n >= this.safeMin && n <= this.safeMax) {
            return null;
        }
        return this.escapeUnsafe(n);
    }

    protected abstract char[] escapeUnsafe(int var1);
}

