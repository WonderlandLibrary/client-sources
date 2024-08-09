/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtCompatible
public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.is('-'), "-"){

        @Override
        String normalizeWord(String string) {
            return Ascii.toLowerCase(string);
        }

        @Override
        String convert(CaseFormat caseFormat, String string) {
            if (caseFormat == LOWER_UNDERSCORE) {
                return string.replace('-', '_');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(string.replace('-', '_'));
            }
            return super.convert(caseFormat, string);
        }
    }
    ,
    LOWER_UNDERSCORE(CharMatcher.is('_'), "_"){

        @Override
        String normalizeWord(String string) {
            return Ascii.toLowerCase(string);
        }

        @Override
        String convert(CaseFormat caseFormat, String string) {
            if (caseFormat == LOWER_HYPHEN) {
                return string.replace('_', '-');
            }
            if (caseFormat == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(string);
            }
            return super.convert(caseFormat, string);
        }
    }
    ,
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), ""){

        @Override
        String normalizeWord(String string) {
            return CaseFormat.access$100(string);
        }
    }
    ,
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), ""){

        @Override
        String normalizeWord(String string) {
            return CaseFormat.access$100(string);
        }
    }
    ,
    UPPER_UNDERSCORE(CharMatcher.is('_'), "_"){

        @Override
        String normalizeWord(String string) {
            return Ascii.toUpperCase(string);
        }

        @Override
        String convert(CaseFormat caseFormat, String string) {
            if (caseFormat == LOWER_HYPHEN) {
                return Ascii.toLowerCase(string.replace('_', '-'));
            }
            if (caseFormat == LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(string);
            }
            return super.convert(caseFormat, string);
        }
    };

    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    private CaseFormat(CharMatcher charMatcher, String string2) {
        this.wordBoundary = charMatcher;
        this.wordSeparator = string2;
    }

    public final String to(CaseFormat caseFormat, String string) {
        Preconditions.checkNotNull(caseFormat);
        Preconditions.checkNotNull(string);
        return caseFormat == this ? string : this.convert(caseFormat, string);
    }

    String convert(CaseFormat caseFormat, String string) {
        StringBuilder stringBuilder = null;
        int n = 0;
        int n2 = -1;
        while (true) {
            ++n2;
            if ((n2 = this.wordBoundary.indexIn(string, n2)) == -1) break;
            if (n == 0) {
                stringBuilder = new StringBuilder(string.length() + 4 * this.wordSeparator.length());
                stringBuilder.append(caseFormat.normalizeFirstWord(string.substring(n, n2)));
            } else {
                stringBuilder.append(caseFormat.normalizeWord(string.substring(n, n2)));
            }
            stringBuilder.append(caseFormat.wordSeparator);
            n = n2 + this.wordSeparator.length();
        }
        return n == 0 ? caseFormat.normalizeFirstWord(string) : stringBuilder.append(caseFormat.normalizeWord(string.substring(n))).toString();
    }

    public Converter<String, String> converterTo(CaseFormat caseFormat) {
        return new StringConverter(this, caseFormat);
    }

    abstract String normalizeWord(String var1);

    private String normalizeFirstWord(String string) {
        return this == LOWER_CAMEL ? Ascii.toLowerCase(string) : this.normalizeWord(string);
    }

    private static String firstCharOnlyToUpper(String string) {
        return string.isEmpty() ? string : Ascii.toUpperCase(string.charAt(0)) + Ascii.toLowerCase(string.substring(1));
    }

    CaseFormat(CharMatcher charMatcher, String string2, 1 var5_5) {
        this(charMatcher, string2);
    }

    static String access$100(String string) {
        return CaseFormat.firstCharOnlyToUpper(string);
    }

    private static final class StringConverter
    extends Converter<String, String>
    implements Serializable {
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;
        private static final long serialVersionUID = 0L;

        StringConverter(CaseFormat caseFormat, CaseFormat caseFormat2) {
            this.sourceFormat = Preconditions.checkNotNull(caseFormat);
            this.targetFormat = Preconditions.checkNotNull(caseFormat2);
        }

        @Override
        protected String doForward(String string) {
            return this.sourceFormat.to(this.targetFormat, string);
        }

        @Override
        protected String doBackward(String string) {
            return this.targetFormat.to(this.sourceFormat, string);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof StringConverter) {
                StringConverter stringConverter = (StringConverter)object;
                return this.sourceFormat.equals((Object)stringConverter.sourceFormat) && this.targetFormat.equals((Object)stringConverter.targetFormat);
            }
            return true;
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            return (Object)((Object)this.sourceFormat) + ".converterTo(" + (Object)((Object)this.targetFormat) + ")";
        }

        @Override
        protected Object doBackward(Object object) {
            return this.doBackward((String)object);
        }

        @Override
        protected Object doForward(Object object) {
            return this.doForward((String)object);
        }
    }
}

