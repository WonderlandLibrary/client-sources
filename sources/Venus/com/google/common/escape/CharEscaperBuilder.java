/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.CharEscaper;
import com.google.common.escape.Escaper;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;

@Beta
@GwtCompatible
public final class CharEscaperBuilder {
    private final Map<Character, String> map = new HashMap<Character, String>();
    private int max = -1;

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscape(char c, String string) {
        this.map.put(Character.valueOf(c), Preconditions.checkNotNull(string));
        if (c > this.max) {
            this.max = c;
        }
        return this;
    }

    @CanIgnoreReturnValue
    public CharEscaperBuilder addEscapes(char[] cArray, String string) {
        Preconditions.checkNotNull(string);
        for (char c : cArray) {
            this.addEscape(c, string);
        }
        return this;
    }

    public char[][] toArray() {
        char[][] cArrayArray = new char[this.max + 1][];
        for (Map.Entry<Character, String> entry : this.map.entrySet()) {
            cArrayArray[entry.getKey().charValue()] = entry.getValue().toCharArray();
        }
        return cArrayArray;
    }

    public Escaper toEscaper() {
        return new CharArrayDecorator(this.toArray());
    }

    private static class CharArrayDecorator
    extends CharEscaper {
        private final char[][] replacements;
        private final int replaceLength;

        CharArrayDecorator(char[][] cArray) {
            this.replacements = cArray;
            this.replaceLength = cArray.length;
        }

        @Override
        public String escape(String string) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (c >= this.replacements.length || this.replacements[c] == null) continue;
                return this.escapeSlow(string, i);
            }
            return string;
        }

        @Override
        protected char[] escape(char c) {
            return c < this.replaceLength ? this.replacements[c] : null;
        }
    }
}

