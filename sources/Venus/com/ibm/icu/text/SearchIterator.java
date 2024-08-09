/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.BreakIterator;
import java.text.CharacterIterator;

public abstract class SearchIterator {
    protected BreakIterator breakIterator;
    protected CharacterIterator targetText;
    protected int matchLength;
    Search search_ = new Search(this);
    public static final int DONE = -1;

    public void setIndex(int n) {
        if (n < this.search_.beginIndex() || n > this.search_.endIndex()) {
            throw new IndexOutOfBoundsException("setIndex(int) expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        this.search_.reset_ = false;
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
    }

    public void setOverlapping(boolean bl) {
        this.search_.isOverlap_ = bl;
    }

    public void setBreakIterator(BreakIterator breakIterator) {
        this.search_.setBreakIter(breakIterator);
        if (this.search_.breakIter() != null && this.search_.text() != null) {
            this.search_.breakIter().setText((CharacterIterator)this.search_.text().clone());
        }
    }

    public void setTarget(CharacterIterator characterIterator) {
        if (characterIterator == null || characterIterator.getEndIndex() == characterIterator.getIndex()) {
            throw new IllegalArgumentException("Illegal null or empty text");
        }
        characterIterator.setIndex(characterIterator.getBeginIndex());
        this.search_.setTarget(characterIterator);
        this.search_.matchedIndex_ = -1;
        this.search_.setMatchedLength(0);
        this.search_.reset_ = true;
        this.search_.isForwardSearching_ = true;
        if (this.search_.breakIter() != null) {
            this.search_.breakIter().setText((CharacterIterator)characterIterator.clone());
        }
        if (this.search_.internalBreakIter_ != null) {
            this.search_.internalBreakIter_.setText((CharacterIterator)characterIterator.clone());
        }
    }

    public int getMatchStart() {
        return this.search_.matchedIndex_;
    }

    public abstract int getIndex();

    public int getMatchLength() {
        return this.search_.matchedLength();
    }

    public BreakIterator getBreakIterator() {
        return this.search_.breakIter();
    }

    public CharacterIterator getTarget() {
        return this.search_.text();
    }

    public String getMatchedText() {
        if (this.search_.matchedLength() > 0) {
            int n = this.search_.matchedIndex_ + this.search_.matchedLength();
            StringBuilder stringBuilder = new StringBuilder(this.search_.matchedLength());
            CharacterIterator characterIterator = this.search_.text();
            characterIterator.setIndex(this.search_.matchedIndex_);
            while (characterIterator.getIndex() < n) {
                stringBuilder.append(characterIterator.current());
                characterIterator.next();
            }
            characterIterator.setIndex(this.search_.matchedIndex_);
            return stringBuilder.toString();
        }
        return null;
    }

    public int next() {
        int n = this.getIndex();
        int n2 = this.search_.matchedIndex_;
        int n3 = this.search_.matchedLength();
        this.search_.reset_ = false;
        if (this.search_.isForwardSearching_) {
            int n4 = this.search_.endIndex();
            if (n == n4 || n2 == n4 || n2 != -1 && n2 + n3 >= n4) {
                this.setMatchNotFound();
                return 1;
            }
        } else {
            this.search_.isForwardSearching_ = true;
            if (this.search_.matchedIndex_ != -1) {
                return n2;
            }
        }
        if (n3 > 0) {
            n = this.search_.isOverlap_ ? ++n : (n += n3);
        }
        return this.handleNext(n);
    }

    public int previous() {
        int n;
        if (this.search_.reset_) {
            n = this.search_.endIndex();
            this.search_.isForwardSearching_ = false;
            this.search_.reset_ = false;
            this.setIndex(n);
        } else {
            n = this.getIndex();
        }
        int n2 = this.search_.matchedIndex_;
        if (this.search_.isForwardSearching_) {
            this.search_.isForwardSearching_ = false;
            if (n2 != -1) {
                return n2;
            }
        } else {
            int n3 = this.search_.beginIndex();
            if (n == n3 || n2 == n3) {
                this.setMatchNotFound();
                return 1;
            }
        }
        if (n2 != -1) {
            if (this.search_.isOverlap_) {
                n2 += this.search_.matchedLength() - 2;
            }
            return this.handlePrevious(n2);
        }
        return this.handlePrevious(n);
    }

    public boolean isOverlapping() {
        return this.search_.isOverlap_;
    }

    public void reset() {
        this.setMatchNotFound();
        this.setIndex(this.search_.beginIndex());
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }

    public final int first() {
        int n = this.search_.beginIndex();
        this.setIndex(n);
        return this.handleNext(n);
    }

    public final int following(int n) {
        this.setIndex(n);
        return this.handleNext(n);
    }

    public final int last() {
        int n = this.search_.endIndex();
        this.setIndex(n);
        return this.handlePrevious(n);
    }

    public final int preceding(int n) {
        this.setIndex(n);
        return this.handlePrevious(n);
    }

    protected SearchIterator(CharacterIterator characterIterator, BreakIterator breakIterator) {
        if (characterIterator == null || characterIterator.getEndIndex() - characterIterator.getBeginIndex() == 0) {
            throw new IllegalArgumentException("Illegal argument target.  Argument can not be null or of length 0");
        }
        this.search_.setTarget(characterIterator);
        this.search_.setBreakIter(breakIterator);
        if (this.search_.breakIter() != null) {
            this.search_.breakIter().setText((CharacterIterator)characterIterator.clone());
        }
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
        this.search_.matchedIndex_ = -1;
        this.search_.setMatchedLength(0);
    }

    protected void setMatchLength(int n) {
        this.search_.setMatchedLength(n);
    }

    protected abstract int handleNext(int var1);

    protected abstract int handlePrevious(int var1);

    @Deprecated
    protected void setMatchNotFound() {
        this.search_.matchedIndex_ = -1;
        this.search_.setMatchedLength(0);
    }

    public void setElementComparisonType(ElementComparisonType elementComparisonType) {
        this.search_.elementComparisonType_ = elementComparisonType;
    }

    public ElementComparisonType getElementComparisonType() {
        return this.search_.elementComparisonType_;
    }

    public static enum ElementComparisonType {
        STANDARD_ELEMENT_COMPARISON,
        PATTERN_BASE_WEIGHT_IS_WILDCARD,
        ANY_BASE_WEIGHT_IS_WILDCARD;

    }

    final class Search {
        boolean isOverlap_;
        boolean isCanonicalMatch_;
        ElementComparisonType elementComparisonType_;
        BreakIterator internalBreakIter_;
        int matchedIndex_;
        boolean isForwardSearching_;
        boolean reset_;
        final SearchIterator this$0;

        Search(SearchIterator searchIterator) {
            this.this$0 = searchIterator;
        }

        CharacterIterator text() {
            return this.this$0.targetText;
        }

        void setTarget(CharacterIterator characterIterator) {
            this.this$0.targetText = characterIterator;
        }

        BreakIterator breakIter() {
            return this.this$0.breakIterator;
        }

        void setBreakIter(BreakIterator breakIterator) {
            this.this$0.breakIterator = breakIterator;
        }

        int matchedLength() {
            return this.this$0.matchLength;
        }

        void setMatchedLength(int n) {
            this.this$0.matchLength = n;
        }

        int beginIndex() {
            if (this.this$0.targetText == null) {
                return 1;
            }
            return this.this$0.targetText.getBeginIndex();
        }

        int endIndex() {
            if (this.this$0.targetText == null) {
                return 1;
            }
            return this.this$0.targetText.getEndIndex();
        }
    }
}

