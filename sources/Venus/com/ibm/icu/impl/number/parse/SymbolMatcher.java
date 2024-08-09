/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StaticUnicodeSets;
import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import com.ibm.icu.text.UnicodeSet;

public abstract class SymbolMatcher
implements NumberParseMatcher {
    protected final String string;
    protected final UnicodeSet uniSet;

    protected SymbolMatcher(String string, UnicodeSet unicodeSet) {
        this.string = string;
        this.uniSet = unicodeSet;
    }

    protected SymbolMatcher(StaticUnicodeSets.Key key) {
        this.string = "";
        this.uniSet = StaticUnicodeSets.get(key);
    }

    public UnicodeSet getSet() {
        return this.uniSet;
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (this.isDisabled(parsedNumber)) {
            return true;
        }
        int n = 0;
        if (!this.string.isEmpty() && (n = stringSegment.getCommonPrefixLength(this.string)) == this.string.length()) {
            stringSegment.adjustOffset(this.string.length());
            this.accept(stringSegment, parsedNumber);
            return true;
        }
        if (stringSegment.startsWith(this.uniSet)) {
            stringSegment.adjustOffsetByCodePoint();
            this.accept(stringSegment, parsedNumber);
            return true;
        }
        return n == stringSegment.length();
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        return stringSegment.startsWith(this.uniSet) || stringSegment.startsWith(this.string);
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
    }

    protected abstract boolean isDisabled(ParsedNumber var1);

    protected abstract void accept(StringSegment var1, ParsedNumber var2);
}

