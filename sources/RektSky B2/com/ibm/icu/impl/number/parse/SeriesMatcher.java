package com.ibm.icu.impl.number.parse;

import java.util.*;
import com.ibm.icu.impl.*;

public class SeriesMatcher implements NumberParseMatcher
{
    protected List<NumberParseMatcher> matchers;
    protected boolean frozen;
    
    public SeriesMatcher() {
        this.matchers = null;
        this.frozen = false;
    }
    
    public void addMatcher(final NumberParseMatcher matcher) {
        assert !this.frozen;
        if (this.matchers == null) {
            this.matchers = new ArrayList<NumberParseMatcher>();
        }
        this.matchers.add(matcher);
    }
    
    public void freeze() {
        this.frozen = true;
    }
    
    public int length() {
        return (this.matchers == null) ? 0 : this.matchers.size();
    }
    
    @Override
    public boolean match(final StringSegment segment, final ParsedNumber result) {
        assert this.frozen;
        if (this.matchers == null) {
            return false;
        }
        final ParsedNumber backup = new ParsedNumber();
        backup.copyFrom(result);
        final int initialOffset = segment.getOffset();
        boolean maybeMore = true;
        int i = 0;
        while (i < this.matchers.size()) {
            final NumberParseMatcher matcher = this.matchers.get(i);
            final int matcherOffset = segment.getOffset();
            maybeMore = (segment.length() == 0 || matcher.match(segment, result));
            final boolean success = segment.getOffset() != matcherOffset;
            final boolean isFlexible = matcher instanceof Flexible;
            if (success && isFlexible) {
                continue;
            }
            if (success) {
                if (++i >= this.matchers.size() || segment.getOffset() == result.charEnd || result.charEnd <= matcherOffset) {
                    continue;
                }
                segment.setOffset(result.charEnd);
            }
            else {
                if (!isFlexible) {
                    segment.setOffset(initialOffset);
                    result.copyFrom(backup);
                    return maybeMore;
                }
                ++i;
            }
        }
        return maybeMore;
    }
    
    @Override
    public boolean smokeTest(final StringSegment segment) {
        assert this.frozen;
        if (this.matchers == null) {
            return false;
        }
        assert !(this.matchers.get(0) instanceof Flexible);
        return this.matchers.get(0).smokeTest(segment);
    }
    
    @Override
    public void postProcess(final ParsedNumber result) {
        assert this.frozen;
        if (this.matchers == null) {
            return;
        }
        for (int i = 0; i < this.matchers.size(); ++i) {
            final NumberParseMatcher matcher = this.matchers.get(i);
            matcher.postProcess(result);
        }
    }
    
    @Override
    public String toString() {
        return "<SeriesMatcher " + this.matchers + ">";
    }
}
