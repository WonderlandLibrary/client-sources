/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.StringMatcher;
import com.ibm.icu.text.StringReplacer;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;

class TransliterationRule {
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

    public TransliterationRule(String string, int n, int n2, String string2, int n3, int n4, UnicodeMatcher[] unicodeMatcherArray, boolean bl, boolean bl2, RuleBasedTransliterator.Data data) {
        this.data = data;
        if (n < 0) {
            this.anteContextLength = 0;
        } else {
            if (n > string.length()) {
                throw new IllegalArgumentException("Invalid ante context");
            }
            this.anteContextLength = n;
        }
        if (n2 < 0) {
            this.keyLength = string.length() - this.anteContextLength;
        } else {
            if (n2 < this.anteContextLength || n2 > string.length()) {
                throw new IllegalArgumentException("Invalid post context");
            }
            this.keyLength = n2 - this.anteContextLength;
        }
        if (n3 < 0) {
            n3 = string2.length();
        } else if (n3 > string2.length()) {
            throw new IllegalArgumentException("Invalid cursor position");
        }
        this.segments = unicodeMatcherArray;
        this.pattern = string;
        this.flags = 0;
        if (bl) {
            this.flags = (byte)(this.flags | 1);
        }
        if (bl2) {
            this.flags = (byte)(this.flags | 2);
        }
        this.anteContext = null;
        if (this.anteContextLength > 0) {
            this.anteContext = new StringMatcher(this.pattern.substring(0, this.anteContextLength), 0, this.data);
        }
        this.key = null;
        if (this.keyLength > 0) {
            this.key = new StringMatcher(this.pattern.substring(this.anteContextLength, this.anteContextLength + this.keyLength), 0, this.data);
        }
        int n5 = this.pattern.length() - this.keyLength - this.anteContextLength;
        this.postContext = null;
        if (n5 > 0) {
            this.postContext = new StringMatcher(this.pattern.substring(this.anteContextLength + this.keyLength), 0, this.data);
        }
        this.output = new StringReplacer(string2, n3 + n4, this.data);
    }

    public int getAnteContextLength() {
        return this.anteContextLength + ((this.flags & 1) != 0 ? 1 : 0);
    }

    final int getIndexValue() {
        if (this.anteContextLength == this.pattern.length()) {
            return 1;
        }
        int n = UTF16.charAt(this.pattern, this.anteContextLength);
        return this.data.lookupMatcher(n) == null ? n & 0xFF : -1;
    }

    final boolean matchesIndexValue(int n) {
        StringMatcher stringMatcher = this.key != null ? this.key : this.postContext;
        return stringMatcher != null ? stringMatcher.matchesIndexValue(n) : true;
    }

    public boolean masks(TransliterationRule transliterationRule) {
        int n = this.pattern.length();
        int n2 = this.anteContextLength;
        int n3 = transliterationRule.anteContextLength;
        int n4 = this.pattern.length() - n2;
        int n5 = transliterationRule.pattern.length() - n3;
        if (n2 == n3 && n4 == n5 && this.keyLength <= transliterationRule.keyLength && transliterationRule.pattern.regionMatches(0, this.pattern, 0, n)) {
            return this.flags == transliterationRule.flags || (this.flags & 1) == 0 && (this.flags & 2) == 0 || (transliterationRule.flags & 1) != 0 && (transliterationRule.flags & 2) != 0;
        }
        return n2 <= n3 && (n4 < n5 || n4 == n5 && this.keyLength <= transliterationRule.keyLength) && transliterationRule.pattern.regionMatches(n3 - n2, this.pattern, 0, n);
    }

    static final int posBefore(Replaceable replaceable, int n) {
        return n > 0 ? n - UTF16.getCharCount(replaceable.char32At(n - 1)) : n - 1;
    }

    static final int posAfter(Replaceable replaceable, int n) {
        return n >= 0 && n < replaceable.length() ? n + UTF16.getCharCount(replaceable.char32At(n)) : n + 1;
    }

    public int matchAndReplace(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        int n2;
        if (this.segments != null) {
            for (n2 = 0; n2 < this.segments.length; ++n2) {
                ((StringMatcher)this.segments[n2]).resetMatch();
            }
        }
        int[] nArray = new int[1];
        int n3 = TransliterationRule.posBefore(replaceable, position.contextStart);
        nArray[0] = TransliterationRule.posBefore(replaceable, position.start);
        if (this.anteContext != null && (n = this.anteContext.matches(replaceable, nArray, n3, true)) != 2) {
            return 1;
        }
        int n4 = nArray[0];
        int n5 = TransliterationRule.posAfter(replaceable, n4);
        if ((this.flags & 1) != 0 && n4 != n3) {
            return 1;
        }
        nArray[0] = position.start;
        if (this.key != null && (n = this.key.matches(replaceable, nArray, position.limit, bl)) != 2) {
            return n;
        }
        n2 = nArray[0];
        if (this.postContext != null) {
            if (bl && n2 == position.limit) {
                return 0;
            }
            n = this.postContext.matches(replaceable, nArray, position.contextLimit, bl);
            if (n != 2) {
                return n;
            }
        }
        n4 = nArray[0];
        if ((this.flags & 2) != 0) {
            if (n4 != position.contextLimit) {
                return 1;
            }
            if (bl) {
                return 0;
            }
        }
        int n6 = this.output.replace(replaceable, position.start, n2, nArray);
        int n7 = n6 - (n2 - position.start);
        int n8 = nArray[0];
        position.limit += n7;
        position.contextLimit += n7;
        position.start = Math.max(n5, Math.min(Math.min(n4 += n7, position.limit), n8));
        return 1;
    }

    public String toRule(boolean bl) {
        boolean bl2;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        boolean bl3 = bl2 = this.anteContext != null || this.postContext != null;
        if ((this.flags & 1) != 0) {
            stringBuffer.append('^');
        }
        Utility.appendToRule(stringBuffer, this.anteContext, bl, stringBuffer2);
        if (bl2) {
            Utility.appendToRule(stringBuffer, 123, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, this.key, bl, stringBuffer2);
        if (bl2) {
            Utility.appendToRule(stringBuffer, 125, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, this.postContext, bl, stringBuffer2);
        if ((this.flags & 2) != 0) {
            stringBuffer.append('$');
        }
        Utility.appendToRule(stringBuffer, " > ", true, bl, stringBuffer2);
        Utility.appendToRule(stringBuffer, this.output.toReplacerPattern(bl), true, bl, stringBuffer2);
        Utility.appendToRule(stringBuffer, 59, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }

    public String toString() {
        return '{' + this.toRule(false) + '}';
    }

    void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3, UnicodeSet unicodeSet4) {
        int n = this.anteContextLength + this.keyLength;
        UnicodeSet unicodeSet5 = new UnicodeSet();
        UnicodeSet unicodeSet6 = new UnicodeSet();
        int n2 = this.anteContextLength;
        while (n2 < n) {
            int n3 = UTF16.charAt(this.pattern, n2);
            n2 += UTF16.getCharCount(n3);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n3);
            if (unicodeMatcher == null) {
                if (!unicodeSet.contains(n3)) {
                    return;
                }
                unicodeSet5.add(n3);
                continue;
            }
            try {
                if (!unicodeSet.containsSome((UnicodeSet)unicodeMatcher)) {
                    return;
                }
                unicodeMatcher.addMatchSetTo(unicodeSet5);
            } catch (ClassCastException classCastException) {
                unicodeSet6.clear();
                unicodeMatcher.addMatchSetTo(unicodeSet6);
                if (!unicodeSet.containsSome(unicodeSet6)) {
                    return;
                }
                unicodeSet5.addAll(unicodeSet6);
            }
        }
        unicodeSet2.addAll(unicodeSet5);
        this.output.addReplacementSetTo(unicodeSet3);
    }
}

