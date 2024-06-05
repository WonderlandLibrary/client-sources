package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class StringMatcher implements UnicodeMatcher, UnicodeReplacer
{
    private String pattern;
    private int matchStart;
    private int matchLimit;
    private int segmentNumber;
    private final RuleBasedTransliterator.Data data;
    
    public StringMatcher(final String theString, final int segmentNum, final RuleBasedTransliterator.Data theData) {
        this.data = theData;
        this.pattern = theString;
        final int n = -1;
        this.matchLimit = n;
        this.matchStart = n;
        this.segmentNumber = segmentNum;
    }
    
    public StringMatcher(final String theString, final int start, final int limit, final int segmentNum, final RuleBasedTransliterator.Data theData) {
        this(theString.substring(start, limit), segmentNum, theData);
    }
    
    @Override
    public int matches(final Replaceable text, final int[] offset, final int limit, final boolean incremental) {
        final int[] cursor = { offset[0] };
        if (limit < cursor[0]) {
            for (int i = this.pattern.length() - 1; i >= 0; --i) {
                final char keyChar = this.pattern.charAt(i);
                final UnicodeMatcher subm = this.data.lookupMatcher(keyChar);
                if (subm == null) {
                    if (cursor[0] <= limit || keyChar != text.charAt(cursor[0])) {
                        return 0;
                    }
                    final int[] array = cursor;
                    final int n = 0;
                    --array[n];
                }
                else {
                    final int m = subm.matches(text, cursor, limit, incremental);
                    if (m != 2) {
                        return m;
                    }
                }
            }
            if (this.matchStart < 0) {
                this.matchStart = cursor[0] + 1;
                this.matchLimit = offset[0] + 1;
            }
        }
        else {
            for (int i = 0; i < this.pattern.length(); ++i) {
                if (incremental && cursor[0] == limit) {
                    return 1;
                }
                final char keyChar = this.pattern.charAt(i);
                final UnicodeMatcher subm = this.data.lookupMatcher(keyChar);
                if (subm == null) {
                    if (cursor[0] >= limit || keyChar != text.charAt(cursor[0])) {
                        return 0;
                    }
                    final int[] array2 = cursor;
                    final int n2 = 0;
                    ++array2[n2];
                }
                else {
                    final int m = subm.matches(text, cursor, limit, incremental);
                    if (m != 2) {
                        return m;
                    }
                }
            }
            this.matchStart = offset[0];
            this.matchLimit = cursor[0];
        }
        offset[0] = cursor[0];
        return 2;
    }
    
    @Override
    public String toPattern(final boolean escapeUnprintable) {
        final StringBuffer result = new StringBuffer();
        final StringBuffer quoteBuf = new StringBuffer();
        if (this.segmentNumber > 0) {
            result.append('(');
        }
        for (int i = 0; i < this.pattern.length(); ++i) {
            final char keyChar = this.pattern.charAt(i);
            final UnicodeMatcher m = this.data.lookupMatcher(keyChar);
            if (m == null) {
                Utility.appendToRule(result, keyChar, false, escapeUnprintable, quoteBuf);
            }
            else {
                Utility.appendToRule(result, m.toPattern(escapeUnprintable), true, escapeUnprintable, quoteBuf);
            }
        }
        if (this.segmentNumber > 0) {
            result.append(')');
        }
        Utility.appendToRule(result, -1, true, escapeUnprintable, quoteBuf);
        return result.toString();
    }
    
    @Override
    public boolean matchesIndexValue(final int v) {
        if (this.pattern.length() == 0) {
            return true;
        }
        final int c = UTF16.charAt(this.pattern, 0);
        final UnicodeMatcher m = this.data.lookupMatcher(c);
        return (m == null) ? ((c & 0xFF) == v) : m.matchesIndexValue(v);
    }
    
    @Override
    public void addMatchSetTo(final UnicodeSet toUnionTo) {
        int ch;
        for (int i = 0; i < this.pattern.length(); i += UTF16.getCharCount(ch)) {
            ch = UTF16.charAt(this.pattern, i);
            final UnicodeMatcher matcher = this.data.lookupMatcher(ch);
            if (matcher == null) {
                toUnionTo.add(ch);
            }
            else {
                matcher.addMatchSetTo(toUnionTo);
            }
        }
    }
    
    @Override
    public int replace(final Replaceable text, final int start, final int limit, final int[] cursor) {
        int outLen = 0;
        final int dest = limit;
        if (this.matchStart >= 0 && this.matchStart != this.matchLimit) {
            text.copy(this.matchStart, this.matchLimit, dest);
            outLen = this.matchLimit - this.matchStart;
        }
        text.replace(start, limit, "");
        return outLen;
    }
    
    @Override
    public String toReplacerPattern(final boolean escapeUnprintable) {
        final StringBuffer rule = new StringBuffer("$");
        Utility.appendNumber(rule, this.segmentNumber, 10, 1);
        return rule.toString();
    }
    
    public void resetMatch() {
        final int n = -1;
        this.matchLimit = n;
        this.matchStart = n;
    }
    
    @Override
    public void addReplacementSetTo(final UnicodeSet toUnionTo) {
    }
}
