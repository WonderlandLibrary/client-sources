/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.impl.StringSegment;
import com.ibm.icu.impl.number.parse.NumberParseMatcher;
import com.ibm.icu.impl.number.parse.ParsedNumber;
import java.util.ArrayList;
import java.util.List;

public class SeriesMatcher
implements NumberParseMatcher {
    protected List<NumberParseMatcher> matchers = null;
    protected boolean frozen = false;
    static final boolean $assertionsDisabled = !SeriesMatcher.class.desiredAssertionStatus();

    public void addMatcher(NumberParseMatcher numberParseMatcher) {
        if (!$assertionsDisabled && this.frozen) {
            throw new AssertionError();
        }
        if (this.matchers == null) {
            this.matchers = new ArrayList<NumberParseMatcher>();
        }
        this.matchers.add(numberParseMatcher);
    }

    public void freeze() {
        this.frozen = true;
    }

    public int length() {
        return this.matchers == null ? 0 : this.matchers.size();
    }

    @Override
    public boolean match(StringSegment stringSegment, ParsedNumber parsedNumber) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (this.matchers == null) {
            return true;
        }
        ParsedNumber parsedNumber2 = new ParsedNumber();
        parsedNumber2.copyFrom(parsedNumber);
        int n = stringSegment.getOffset();
        boolean bl = true;
        int n2 = 0;
        while (n2 < this.matchers.size()) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(n2);
            int n3 = stringSegment.getOffset();
            bl = stringSegment.length() != 0 ? numberParseMatcher.match(stringSegment, parsedNumber) : true;
            boolean bl2 = stringSegment.getOffset() != n3;
            boolean bl3 = numberParseMatcher instanceof NumberParseMatcher.Flexible;
            if (bl2 && bl3) continue;
            if (bl2) {
                if (++n2 >= this.matchers.size() || stringSegment.getOffset() == parsedNumber.charEnd || parsedNumber.charEnd <= n3) continue;
                stringSegment.setOffset(parsedNumber.charEnd);
                continue;
            }
            if (bl3) {
                ++n2;
                continue;
            }
            stringSegment.setOffset(n);
            parsedNumber.copyFrom(parsedNumber2);
            return bl;
        }
        return bl;
    }

    @Override
    public boolean smokeTest(StringSegment stringSegment) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (this.matchers == null) {
            return true;
        }
        if (!$assertionsDisabled && this.matchers.get(0) instanceof NumberParseMatcher.Flexible) {
            throw new AssertionError();
        }
        return this.matchers.get(0).smokeTest(stringSegment);
    }

    @Override
    public void postProcess(ParsedNumber parsedNumber) {
        if (!$assertionsDisabled && !this.frozen) {
            throw new AssertionError();
        }
        if (this.matchers == null) {
            return;
        }
        for (int i = 0; i < this.matchers.size(); ++i) {
            NumberParseMatcher numberParseMatcher = this.matchers.get(i);
            numberParseMatcher.postProcess(parsedNumber);
        }
    }

    public String toString() {
        return "<SeriesMatcher " + this.matchers + ">";
    }
}

