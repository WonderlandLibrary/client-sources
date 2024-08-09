/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.ArrayBasedCharEscaper;
import com.google.common.escape.CharEscaper;
import com.google.common.escape.Escaper;
import com.google.common.escape.UnicodeEscaper;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public final class Escapers {
    private static final Escaper NULL_ESCAPER = new CharEscaper(){

        @Override
        public String escape(String string) {
            return Preconditions.checkNotNull(string);
        }

        @Override
        protected char[] escape(char c) {
            return null;
        }
    };

    private Escapers() {
    }

    public static Escaper nullEscaper() {
        return NULL_ESCAPER;
    }

    public static Builder builder() {
        return new Builder(null);
    }

    static UnicodeEscaper asUnicodeEscaper(Escaper escaper) {
        Preconditions.checkNotNull(escaper);
        if (escaper instanceof UnicodeEscaper) {
            return (UnicodeEscaper)escaper;
        }
        if (escaper instanceof CharEscaper) {
            return Escapers.wrap((CharEscaper)escaper);
        }
        throw new IllegalArgumentException("Cannot create a UnicodeEscaper from: " + escaper.getClass().getName());
    }

    public static String computeReplacement(CharEscaper charEscaper, char c) {
        return Escapers.stringOrNull(charEscaper.escape(c));
    }

    public static String computeReplacement(UnicodeEscaper unicodeEscaper, int n) {
        return Escapers.stringOrNull(unicodeEscaper.escape(n));
    }

    private static String stringOrNull(char[] cArray) {
        return cArray == null ? null : new String(cArray);
    }

    private static UnicodeEscaper wrap(CharEscaper charEscaper) {
        return new UnicodeEscaper(charEscaper){
            final CharEscaper val$escaper;
            {
                this.val$escaper = charEscaper;
            }

            @Override
            protected char[] escape(int n) {
                int n2;
                if (n < 65536) {
                    return this.val$escaper.escape((char)n);
                }
                char[] cArray = new char[2];
                Character.toChars(n, cArray, 0);
                char[] cArray2 = this.val$escaper.escape(cArray[0]);
                char[] cArray3 = this.val$escaper.escape(cArray[1]);
                if (cArray2 == null && cArray3 == null) {
                    return null;
                }
                int n3 = cArray2 != null ? cArray2.length : 1;
                int n4 = cArray3 != null ? cArray3.length : 1;
                char[] cArray4 = new char[n3 + n4];
                if (cArray2 != null) {
                    for (n2 = 0; n2 < cArray2.length; ++n2) {
                        cArray4[n2] = cArray2[n2];
                    }
                } else {
                    cArray4[0] = cArray[0];
                }
                if (cArray3 != null) {
                    for (n2 = 0; n2 < cArray3.length; ++n2) {
                        cArray4[n3 + n2] = cArray3[n2];
                    }
                } else {
                    cArray4[n3] = cArray[1];
                }
                return cArray4;
            }
        };
    }

    @Beta
    public static final class Builder {
        private final Map<Character, String> replacementMap = new HashMap<Character, String>();
        private char safeMin = '\u0000';
        private char safeMax = (char)65535;
        private String unsafeReplacement = null;

        private Builder() {
        }

        @CanIgnoreReturnValue
        public Builder setSafeRange(char c, char c2) {
            this.safeMin = c;
            this.safeMax = c2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder setUnsafeReplacement(@Nullable String string) {
            this.unsafeReplacement = string;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder addEscape(char c, String string) {
            Preconditions.checkNotNull(string);
            this.replacementMap.put(Character.valueOf(c), string);
            return this;
        }

        public Escaper build() {
            return new ArrayBasedCharEscaper(this, this.replacementMap, this.safeMin, this.safeMax){
                private final char[] replacementChars;
                final Builder this$0;
                {
                    this.this$0 = builder;
                    super(map, c, c2);
                    this.replacementChars = Builder.access$100(this.this$0) != null ? Builder.access$100(this.this$0).toCharArray() : null;
                }

                @Override
                protected char[] escapeUnsafe(char c) {
                    return this.replacementChars;
                }
            };
        }

        Builder(1 var1_1) {
            this();
        }

        static String access$100(Builder builder) {
            return builder.unsafeReplacement;
        }
    }
}

