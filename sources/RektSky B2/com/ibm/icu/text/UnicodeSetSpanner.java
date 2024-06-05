package com.ibm.icu.text;

import com.ibm.icu.util.*;

public class UnicodeSetSpanner
{
    private final UnicodeSet unicodeSet;
    
    public UnicodeSetSpanner(final UnicodeSet source) {
        this.unicodeSet = source;
    }
    
    public UnicodeSet getUnicodeSet() {
        return this.unicodeSet;
    }
    
    @Override
    public boolean equals(final Object other) {
        return other instanceof UnicodeSetSpanner && this.unicodeSet.equals(((UnicodeSetSpanner)other).unicodeSet);
    }
    
    @Override
    public int hashCode() {
        return this.unicodeSet.hashCode();
    }
    
    public int countIn(final CharSequence sequence) {
        return this.countIn(sequence, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public int countIn(final CharSequence sequence, final CountMethod countMethod) {
        return this.countIn(sequence, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public int countIn(final CharSequence sequence, final CountMethod countMethod, final UnicodeSet.SpanCondition spanCondition) {
        int count = 0;
        int start = 0;
        final UnicodeSet.SpanCondition skipSpan = (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        final int length = sequence.length();
        OutputInt spanCount = null;
        while (start != length) {
            final int endOfSpan = this.unicodeSet.span(sequence, start, skipSpan);
            if (endOfSpan == length) {
                break;
            }
            if (countMethod == CountMethod.WHOLE_SPAN) {
                start = this.unicodeSet.span(sequence, endOfSpan, spanCondition);
                ++count;
            }
            else {
                if (spanCount == null) {
                    spanCount = new OutputInt();
                }
                start = this.unicodeSet.spanAndCount(sequence, endOfSpan, spanCondition, spanCount);
                count += spanCount.value;
            }
        }
        return count;
    }
    
    public String deleteFrom(final CharSequence sequence) {
        return this.replaceFrom(sequence, "", CountMethod.WHOLE_SPAN, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public String deleteFrom(final CharSequence sequence, final UnicodeSet.SpanCondition spanCondition) {
        return this.replaceFrom(sequence, "", CountMethod.WHOLE_SPAN, spanCondition);
    }
    
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement) {
        return this.replaceFrom(sequence, replacement, CountMethod.MIN_ELEMENTS, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement, final CountMethod countMethod) {
        return this.replaceFrom(sequence, replacement, countMethod, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public String replaceFrom(final CharSequence sequence, final CharSequence replacement, final CountMethod countMethod, final UnicodeSet.SpanCondition spanCondition) {
        final UnicodeSet.SpanCondition copySpan = (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) ? UnicodeSet.SpanCondition.SIMPLE : UnicodeSet.SpanCondition.NOT_CONTAINED;
        final boolean remove = replacement.length() == 0;
        final StringBuilder result = new StringBuilder();
        final int length = sequence.length();
        OutputInt spanCount = null;
        int endCopy = 0;
        while (endCopy != length) {
            int endModify;
            if (countMethod == CountMethod.WHOLE_SPAN) {
                endModify = this.unicodeSet.span(sequence, endCopy, spanCondition);
            }
            else {
                if (spanCount == null) {
                    spanCount = new OutputInt();
                }
                endModify = this.unicodeSet.spanAndCount(sequence, endCopy, spanCondition, spanCount);
            }
            if (!remove) {
                if (endModify != 0) {
                    if (countMethod == CountMethod.WHOLE_SPAN) {
                        result.append(replacement);
                    }
                    else {
                        for (int i = spanCount.value; i > 0; --i) {
                            result.append(replacement);
                        }
                    }
                }
            }
            if (endModify == length) {
                break;
            }
            endCopy = this.unicodeSet.span(sequence, endModify, copySpan);
            result.append(sequence.subSequence(endModify, endCopy));
        }
        return result.toString();
    }
    
    public CharSequence trim(final CharSequence sequence) {
        return this.trim(sequence, TrimOption.BOTH, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public CharSequence trim(final CharSequence sequence, final TrimOption trimOption) {
        return this.trim(sequence, trimOption, UnicodeSet.SpanCondition.SIMPLE);
    }
    
    public CharSequence trim(final CharSequence sequence, final TrimOption trimOption, final UnicodeSet.SpanCondition spanCondition) {
        final int length = sequence.length();
        int endLeadContained;
        if (trimOption != TrimOption.TRAILING) {
            endLeadContained = this.unicodeSet.span(sequence, spanCondition);
            if (endLeadContained == length) {
                return "";
            }
        }
        else {
            endLeadContained = 0;
        }
        int startTrailContained;
        if (trimOption != TrimOption.LEADING) {
            startTrailContained = this.unicodeSet.spanBack(sequence, spanCondition);
        }
        else {
            startTrailContained = length;
        }
        return (endLeadContained == 0 && startTrailContained == length) ? sequence : sequence.subSequence(endLeadContained, startTrailContained);
    }
    
    public enum CountMethod
    {
        WHOLE_SPAN, 
        MIN_ELEMENTS;
    }
    
    public enum TrimOption
    {
        LEADING, 
        BOTH, 
        TRAILING;
    }
}
