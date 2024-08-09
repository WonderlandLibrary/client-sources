/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;

class StringMatcher
implements UnicodeMatcher,
UnicodeReplacer {
    private String pattern;
    private int matchStart;
    private int matchLimit;
    private int segmentNumber;
    private final RuleBasedTransliterator.Data data;

    public StringMatcher(String string, int n, RuleBasedTransliterator.Data data) {
        this.data = data;
        this.pattern = string;
        this.matchLimit = -1;
        this.matchStart = -1;
        this.segmentNumber = n;
    }

    public StringMatcher(String string, int n, int n2, int n3, RuleBasedTransliterator.Data data) {
        this(string.substring(n, n2), n3, data);
    }

    @Override
    public int matches(Replaceable replaceable, int[] nArray, int n, boolean bl) {
        int[] nArray2 = new int[]{nArray[0]};
        if (n < nArray2[0]) {
            for (int i = this.pattern.length() - 1; i >= 0; --i) {
                char c = this.pattern.charAt(i);
                UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(c);
                if (unicodeMatcher == null) {
                    if (nArray2[0] > n && c == replaceable.charAt(nArray2[0])) {
                        nArray2[0] = nArray2[0] - 1;
                        continue;
                    }
                    return 1;
                }
                int n2 = unicodeMatcher.matches(replaceable, nArray2, n, bl);
                if (n2 == 2) continue;
                return n2;
            }
            if (this.matchStart < 0) {
                this.matchStart = nArray2[0] + 1;
                this.matchLimit = nArray[0] + 1;
            }
        } else {
            for (int i = 0; i < this.pattern.length(); ++i) {
                if (bl && nArray2[0] == n) {
                    return 0;
                }
                char c = this.pattern.charAt(i);
                UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(c);
                if (unicodeMatcher == null) {
                    if (nArray2[0] < n && c == replaceable.charAt(nArray2[0])) {
                        nArray2[0] = nArray2[0] + 1;
                        continue;
                    }
                    return 1;
                }
                int n3 = unicodeMatcher.matches(replaceable, nArray2, n, bl);
                if (n3 == 2) continue;
                return n3;
            }
            this.matchStart = nArray[0];
            this.matchLimit = nArray2[0];
        }
        nArray[0] = nArray2[0];
        return 1;
    }

    @Override
    public String toPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.segmentNumber > 0) {
            stringBuffer.append('(');
        }
        for (int i = 0; i < this.pattern.length(); ++i) {
            char c = this.pattern.charAt(i);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(c);
            if (unicodeMatcher == null) {
                Utility.appendToRule(stringBuffer, c, false, bl, stringBuffer2);
                continue;
            }
            Utility.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
        if (this.segmentNumber > 0) {
            stringBuffer.append(')');
        }
        Utility.appendToRule(stringBuffer, -1, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }

    @Override
    public boolean matchesIndexValue(int n) {
        if (this.pattern.length() == 0) {
            return false;
        }
        int n2 = UTF16.charAt(this.pattern, 0);
        UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n2);
        return unicodeMatcher == null ? (n2 & 0xFF) == n : unicodeMatcher.matchesIndexValue(n);
    }

    @Override
    public void addMatchSetTo(UnicodeSet unicodeSet) {
        int n;
        for (int i = 0; i < this.pattern.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(this.pattern, i);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n);
            if (unicodeMatcher == null) {
                unicodeSet.add(n);
                continue;
            }
            unicodeMatcher.addMatchSetTo(unicodeSet);
        }
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] nArray) {
        int n3 = 0;
        int n4 = n2;
        if (this.matchStart >= 0 && this.matchStart != this.matchLimit) {
            replaceable.copy(this.matchStart, this.matchLimit, n4);
            n3 = this.matchLimit - this.matchStart;
        }
        replaceable.replace(n, n2, "");
        return n3;
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer("$");
        Utility.appendNumber(stringBuffer, this.segmentNumber, 10, 1);
        return stringBuffer.toString();
    }

    public void resetMatch() {
        this.matchLimit = -1;
        this.matchStart = -1;
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
    }
}

