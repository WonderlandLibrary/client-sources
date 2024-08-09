/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedEscaperMap;
import com.google.common.escape.CharEscaper;
import java.util.Map;

@Beta
@GwtCompatible
public abstract class ArrayBasedCharEscaper
extends CharEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final char safeMin;
    private final char safeMax;

    protected ArrayBasedCharEscaper(Map<Character, String> map, char c, char c2) {
        this(ArrayBasedEscaperMap.create(map), c, c2);
    }

    protected ArrayBasedCharEscaper(ArrayBasedEscaperMap arrayBasedEscaperMap, char c, char c2) {
        Preconditions.checkNotNull(arrayBasedEscaperMap);
        this.replacements = arrayBasedEscaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        if (c2 < c) {
            c2 = '\u0000';
            c = (char)65535;
        }
        this.safeMin = c;
        this.safeMax = c2;
    }

    @Override
    public final String escape(String string) {
        Preconditions.checkNotNull(string);
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if ((c >= this.replacementsLength || this.replacements[c] == null) && c <= this.safeMax && c >= this.safeMin) continue;
            return this.escapeSlow(string, i);
        }
        return string;
    }

    @Override
    protected final char[] escape(char c) {
        char[] cArray;
        if (c < this.replacementsLength && (cArray = this.replacements[c]) != null) {
            return cArray;
        }
        if (c >= this.safeMin && c <= this.safeMax) {
            return null;
        }
        return this.escapeUnsafe(c);
    }

    protected abstract char[] escapeUnsafe(char var1);
}

