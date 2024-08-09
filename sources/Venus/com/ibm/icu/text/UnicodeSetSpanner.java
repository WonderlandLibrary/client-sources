/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.OutputInt;

public class UnicodeSetSpanner {
    private final UnicodeSet unicodeSet;

    public UnicodeSetSpanner(UnicodeSet unicodeSet) {
        this.unicodeSet = unicodeSet;
    }

    public UnicodeSet getUnicodeSet() {
        return this.unicodeSet;
    }

    public boolean equals(Object object) {
        return object instanceof UnicodeSetSpanner && this.unicodeSet.equals(((UnicodeSetSpanner)object).unicodeSet);
    }

    public int hashCode() {
        return this.unicodeSet.hashCode();
    }

    public int countIn(CharSequence charSequence) {
        return this.countIn(charSequence, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }

    public int countIn(CharSequence charSequence, CountMethod countMethod) {
        return this.countIn(charSequence, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }

    public int countIn(CharSequence charSequence, CountMethod countMethod, UnicodeSet.SpanCondition spanCondition) {
        int n;
        int n2 = 0;
        int n3 = 0;
        UnicodeSet.SpanCondition spanCondition2 = spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        int n4 = charSequence.length();
        OutputInt outputInt = null;
        while (n3 != n4 && (n = this.unicodeSet.span(charSequence, n3, spanCondition2)) != n4) {
            if (countMethod == CountMethod.WHOLE_SPAN) {
                n3 = this.unicodeSet.span(charSequence, n, spanCondition);
                ++n2;
                continue;
            }
            if (outputInt == null) {
                outputInt = new OutputInt();
            }
            n3 = this.unicodeSet.spanAndCount(charSequence, n, spanCondition, outputInt);
            n2 += outputInt.value;
        }
        return n2;
    }

    public String deleteFrom(CharSequence charSequence) {
        return this.replaceFrom(charSequence, "", CountMethod.WHOLE_SPAN, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String deleteFrom(CharSequence charSequence, UnicodeSet.SpanCondition spanCondition) {
        return this.replaceFrom(charSequence, "", CountMethod.WHOLE_SPAN, spanCondition);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2) {
        return this.replaceFrom(charSequence, charSequence2, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2, CountMethod countMethod) {
        return this.replaceFrom(charSequence, charSequence2, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }

    public String replaceFrom(CharSequence charSequence, CharSequence charSequence2, CountMethod countMethod, UnicodeSet.SpanCondition spanCondition) {
        UnicodeSet.SpanCondition spanCondition2 = spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        boolean bl = charSequence2.length() == 0;
        StringBuilder stringBuilder = new StringBuilder();
        int n = charSequence.length();
        OutputInt outputInt = null;
        int n2 = 0;
        while (n2 != n) {
            int n3;
            if (countMethod == CountMethod.WHOLE_SPAN) {
                n3 = this.unicodeSet.span(charSequence, n2, spanCondition);
            } else {
                if (outputInt == null) {
                    outputInt = new OutputInt();
                }
                n3 = this.unicodeSet.spanAndCount(charSequence, n2, spanCondition, outputInt);
            }
            if (!bl && n3 != 0) {
                if (countMethod == CountMethod.WHOLE_SPAN) {
                    stringBuilder.append(charSequence2);
                } else {
                    for (int i = outputInt.value; i > 0; --i) {
                        stringBuilder.append(charSequence2);
                    }
                }
            }
            if (n3 == n) break;
            n2 = this.unicodeSet.span(charSequence, n3, spanCondition2);
            stringBuilder.append(charSequence.subSequence(n3, n2));
        }
        return stringBuilder.toString();
    }

    public CharSequence trim(CharSequence charSequence) {
        return this.trim(charSequence, TrimOption.BOTH, UnicodeSet.SpanCondition.SIMPLE);
    }

    public CharSequence trim(CharSequence charSequence, TrimOption trimOption) {
        return this.trim(charSequence, trimOption, UnicodeSet.SpanCondition.SIMPLE);
    }

    public CharSequence trim(CharSequence charSequence, TrimOption trimOption, UnicodeSet.SpanCondition spanCondition) {
        int n;
        int n2 = charSequence.length();
        if (trimOption != TrimOption.TRAILING) {
            n = this.unicodeSet.span(charSequence, spanCondition);
            if (n == n2) {
                return "";
            }
        } else {
            n = 0;
        }
        int n3 = trimOption != TrimOption.LEADING ? this.unicodeSet.spanBack(charSequence, spanCondition) : n2;
        return n == 0 && n3 == n2 ? charSequence : charSequence.subSequence(n, n3);
    }

    public static enum TrimOption {
        LEADING,
        BOTH,
        TRAILING;

    }

    public static enum CountMethod {
        WHOLE_SPAN,
        MIN_ELEMENTS;

    }
}

