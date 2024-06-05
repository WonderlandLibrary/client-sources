package com.ibm.icu.text;

import com.ibm.icu.impl.*;

class TransliterationRule
{
    private StringMatcher anteContext;
    private StringMatcher key;
    private StringMatcher postContext;
    private UnicodeReplacer output;
    private String pattern;
    UnicodeMatcher[] segments;
    private int anteContextLength;
    private int keyLength;
    byte flags;
    static final int ANCHOR_START = 1;
    static final int ANCHOR_END = 2;
    private final RuleBasedTransliterator.Data data;
    
    public TransliterationRule(final String input, final int anteContextPos, final int postContextPos, final String output, int cursorPos, final int cursorOffset, final UnicodeMatcher[] segs, final boolean anchorStart, final boolean anchorEnd, final RuleBasedTransliterator.Data theData) {
        this.data = theData;
        if (anteContextPos < 0) {
            this.anteContextLength = 0;
        }
        else {
            if (anteContextPos > input.length()) {
                throw new IllegalArgumentException("Invalid ante context");
            }
            this.anteContextLength = anteContextPos;
        }
        if (postContextPos < 0) {
            this.keyLength = input.length() - this.anteContextLength;
        }
        else {
            if (postContextPos < this.anteContextLength || postContextPos > input.length()) {
                throw new IllegalArgumentException("Invalid post context");
            }
            this.keyLength = postContextPos - this.anteContextLength;
        }
        if (cursorPos < 0) {
            cursorPos = output.length();
        }
        else if (cursorPos > output.length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }
        this.segments = segs;
        this.pattern = input;
        this.flags = 0;
        if (anchorStart) {
            this.flags |= 0x1;
        }
        if (anchorEnd) {
            this.flags |= 0x2;
        }
        this.anteContext = null;
        if (this.anteContextLength > 0) {
            this.anteContext = new StringMatcher(this.pattern.substring(0, this.anteContextLength), 0, this.data);
        }
        this.key = null;
        if (this.keyLength > 0) {
            this.key = new StringMatcher(this.pattern.substring(this.anteContextLength, this.anteContextLength + this.keyLength), 0, this.data);
        }
        final int postContextLength = this.pattern.length() - this.keyLength - this.anteContextLength;
        this.postContext = null;
        if (postContextLength > 0) {
            this.postContext = new StringMatcher(this.pattern.substring(this.anteContextLength + this.keyLength), 0, this.data);
        }
        this.output = new StringReplacer(output, cursorPos + cursorOffset, this.data);
    }
    
    public int getAnteContextLength() {
        return this.anteContextLength + (((this.flags & 0x1) != 0x0) ? 1 : 0);
    }
    
    final int getIndexValue() {
        if (this.anteContextLength == this.pattern.length()) {
            return -1;
        }
        final int c = UTF16.charAt(this.pattern, this.anteContextLength);
        return (this.data.lookupMatcher(c) == null) ? (c & 0xFF) : -1;
    }
    
    final boolean matchesIndexValue(final int v) {
        final UnicodeMatcher m = (this.key != null) ? this.key : this.postContext;
        return m == null || m.matchesIndexValue(v);
    }
    
    public boolean masks(final TransliterationRule r2) {
        final int len = this.pattern.length();
        final int left = this.anteContextLength;
        final int left2 = r2.anteContextLength;
        final int right = this.pattern.length() - left;
        final int right2 = r2.pattern.length() - left2;
        if (left == left2 && right == right2 && this.keyLength <= r2.keyLength && r2.pattern.regionMatches(0, this.pattern, 0, len)) {
            return this.flags == r2.flags || ((this.flags & 0x1) == 0x0 && (this.flags & 0x2) == 0x0) || ((r2.flags & 0x1) != 0x0 && (r2.flags & 0x2) != 0x0);
        }
        return left <= left2 && (right < right2 || (right == right2 && this.keyLength <= r2.keyLength)) && r2.pattern.regionMatches(left2 - left, this.pattern, 0, len);
    }
    
    static final int posBefore(final Replaceable str, final int pos) {
        return (pos > 0) ? (pos - UTF16.getCharCount(str.char32At(pos - 1))) : (pos - 1);
    }
    
    static final int posAfter(final Replaceable str, final int pos) {
        return (pos >= 0 && pos < str.length()) ? (pos + UTF16.getCharCount(str.char32At(pos))) : (pos + 1);
    }
    
    public int matchAndReplace(final Replaceable text, final Transliterator.Position pos, final boolean incremental) {
        if (this.segments != null) {
            for (int i = 0; i < this.segments.length; ++i) {
                ((StringMatcher)this.segments[i]).resetMatch();
            }
        }
        final int[] intRef = { 0 };
        final int anteLimit = posBefore(text, pos.contextStart);
        intRef[0] = posBefore(text, pos.start);
        if (this.anteContext != null) {
            final int match = this.anteContext.matches(text, intRef, anteLimit, false);
            if (match != 2) {
                return 0;
            }
        }
        int oText = intRef[0];
        final int minOText = posAfter(text, oText);
        if ((this.flags & 0x1) != 0x0 && oText != anteLimit) {
            return 0;
        }
        intRef[0] = pos.start;
        if (this.key != null) {
            final int match = this.key.matches(text, intRef, pos.limit, incremental);
            if (match != 2) {
                return match;
            }
        }
        final int keyLimit = intRef[0];
        if (this.postContext != null) {
            if (incremental && keyLimit == pos.limit) {
                return 1;
            }
            final int match = this.postContext.matches(text, intRef, pos.contextLimit, incremental);
            if (match != 2) {
                return match;
            }
        }
        oText = intRef[0];
        if ((this.flags & 0x2) != 0x0) {
            if (oText != pos.contextLimit) {
                return 0;
            }
            if (incremental) {
                return 1;
            }
        }
        final int newLength = this.output.replace(text, pos.start, keyLimit, intRef);
        final int lenDelta = newLength - (keyLimit - pos.start);
        final int newStart = intRef[0];
        oText += lenDelta;
        pos.limit += lenDelta;
        pos.contextLimit += lenDelta;
        pos.start = Math.max(minOText, Math.min(Math.min(oText, pos.limit), newStart));
        return 2;
    }
    
    public String toRule(final boolean escapeUnprintable) {
        final StringBuffer rule = new StringBuffer();
        final StringBuffer quoteBuf = new StringBuffer();
        final boolean emitBraces = this.anteContext != null || this.postContext != null;
        if ((this.flags & 0x1) != 0x0) {
            rule.append('^');
        }
        Utility.appendToRule(rule, this.anteContext, escapeUnprintable, quoteBuf);
        if (emitBraces) {
            Utility.appendToRule(rule, 123, true, escapeUnprintable, quoteBuf);
        }
        Utility.appendToRule(rule, this.key, escapeUnprintable, quoteBuf);
        if (emitBraces) {
            Utility.appendToRule(rule, 125, true, escapeUnprintable, quoteBuf);
        }
        Utility.appendToRule(rule, this.postContext, escapeUnprintable, quoteBuf);
        if ((this.flags & 0x2) != 0x0) {
            rule.append('$');
        }
        Utility.appendToRule(rule, " > ", true, escapeUnprintable, quoteBuf);
        Utility.appendToRule(rule, this.output.toReplacerPattern(escapeUnprintable), true, escapeUnprintable, quoteBuf);
        Utility.appendToRule(rule, 59, true, escapeUnprintable, quoteBuf);
        return rule.toString();
    }
    
    @Override
    public String toString() {
        return '{' + this.toRule(true) + '}';
    }
    
    void addSourceTargetSet(final UnicodeSet filter, final UnicodeSet sourceSet, final UnicodeSet targetSet, final UnicodeSet revisiting) {
        final int limit = this.anteContextLength + this.keyLength;
        final UnicodeSet tempSource = new UnicodeSet();
        final UnicodeSet temp = new UnicodeSet();
        int i = this.anteContextLength;
        while (i < limit) {
            final int ch = UTF16.charAt(this.pattern, i);
            i += UTF16.getCharCount(ch);
            final UnicodeMatcher matcher = this.data.lookupMatcher(ch);
            if (matcher == null) {
                if (!filter.contains(ch)) {
                    return;
                }
                tempSource.add(ch);
            }
            else {
                try {
                    if (!filter.containsSome((UnicodeSet)matcher)) {
                        return;
                    }
                    matcher.addMatchSetTo(tempSource);
                }
                catch (ClassCastException e) {
                    temp.clear();
                    matcher.addMatchSetTo(temp);
                    if (!filter.containsSome(temp)) {
                        return;
                    }
                    tempSource.addAll(temp);
                }
            }
        }
        sourceSet.addAll(tempSource);
        this.output.addReplacementSetTo(targetSet);
    }
}
