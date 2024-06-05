package com.ibm.icu.text;

import java.util.*;
import java.text.*;
import com.ibm.icu.util.*;

public final class StringSearch extends SearchIterator
{
    private Pattern pattern_;
    private RuleBasedCollator collator_;
    private CollationElementIterator textIter_;
    private CollationPCE textProcessedIter_;
    private CollationElementIterator utilIter_;
    private Normalizer2 nfd_;
    private int strength_;
    int ceMask_;
    int variableTop_;
    private boolean toShift_;
    private static final int INITIAL_ARRAY_SIZE_ = 256;
    private static final int PRIMARYORDERMASK = -65536;
    private static final int SECONDARYORDERMASK = 65280;
    private static final int TERTIARYORDERMASK = 255;
    private static final int CE_MATCH = -1;
    private static final int CE_NO_MATCH = 0;
    private static final int CE_SKIP_TARG = 1;
    private static final int CE_SKIP_PATN = 2;
    private static int CE_LEVEL2_BASE;
    private static int CE_LEVEL3_BASE;
    
    public StringSearch(final String pattern, final CharacterIterator target, final RuleBasedCollator collator, final BreakIterator breakiter) {
        super(target, breakiter);
        if (collator.getNumericCollation()) {
            throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
        }
        this.collator_ = collator;
        this.strength_ = collator.getStrength();
        this.ceMask_ = getMask(this.strength_);
        this.toShift_ = collator.isAlternateHandlingShifted();
        this.variableTop_ = collator.getVariableTop();
        this.nfd_ = Normalizer2.getNFDInstance();
        this.pattern_ = new Pattern(pattern);
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.utilIter_ = null;
        this.textIter_ = new CollationElementIterator(target, collator);
        this.textProcessedIter_ = null;
        final ULocale collLocale = collator.getLocale(ULocale.VALID_LOCALE);
        (this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance((collLocale == null) ? ULocale.ROOT : collLocale)).setText((CharacterIterator)target.clone());
        this.initialize();
    }
    
    public StringSearch(final String pattern, final CharacterIterator target, final RuleBasedCollator collator) {
        this(pattern, target, collator, null);
    }
    
    public StringSearch(final String pattern, final CharacterIterator target, final Locale locale) {
        this(pattern, target, ULocale.forLocale(locale));
    }
    
    public StringSearch(final String pattern, final CharacterIterator target, final ULocale locale) {
        this(pattern, target, (RuleBasedCollator)Collator.getInstance(locale), null);
    }
    
    public StringSearch(final String pattern, final String target) {
        this(pattern, new StringCharacterIterator(target), (RuleBasedCollator)Collator.getInstance(), null);
    }
    
    public RuleBasedCollator getCollator() {
        return this.collator_;
    }
    
    public void setCollator(final RuleBasedCollator collator) {
        if (collator == null) {
            throw new IllegalArgumentException("Collator can not be null");
        }
        this.collator_ = collator;
        this.ceMask_ = getMask(this.collator_.getStrength());
        final ULocale collLocale = collator.getLocale(ULocale.VALID_LOCALE);
        (this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance((collLocale == null) ? ULocale.ROOT : collLocale)).setText((CharacterIterator)this.search_.text().clone());
        this.toShift_ = collator.isAlternateHandlingShifted();
        this.variableTop_ = collator.getVariableTop();
        this.textIter_ = new CollationElementIterator(this.pattern_.text_, collator);
        this.utilIter_ = new CollationElementIterator(this.pattern_.text_, collator);
        this.initialize();
    }
    
    public String getPattern() {
        return this.pattern_.text_;
    }
    
    public void setPattern(final String pattern) {
        if (pattern == null || pattern.length() <= 0) {
            throw new IllegalArgumentException("Pattern to search for can not be null or of length 0");
        }
        this.pattern_.text_ = pattern;
        this.initialize();
    }
    
    public boolean isCanonical() {
        return this.search_.isCanonicalMatch_;
    }
    
    public void setCanonical(final boolean allowCanonical) {
        this.search_.isCanonicalMatch_ = allowCanonical;
    }
    
    @Override
    public void setTarget(final CharacterIterator text) {
        super.setTarget(text);
        this.textIter_.setText(text);
    }
    
    @Override
    public int getIndex() {
        final int result = this.textIter_.getOffset();
        if (isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), result)) {
            return -1;
        }
        return result;
    }
    
    @Override
    public void setIndex(final int position) {
        super.setIndex(position);
        this.textIter_.setOffset(position);
    }
    
    @Override
    public void reset() {
        boolean sameCollAttribute = true;
        final int newStrength = this.collator_.getStrength();
        if ((this.strength_ < 3 && newStrength >= 3) || (this.strength_ >= 3 && newStrength < 3)) {
            sameCollAttribute = false;
        }
        this.strength_ = this.collator_.getStrength();
        final int ceMask = getMask(this.strength_);
        if (this.ceMask_ != ceMask) {
            this.ceMask_ = ceMask;
            sameCollAttribute = false;
        }
        final boolean shift = this.collator_.isAlternateHandlingShifted();
        if (this.toShift_ != shift) {
            this.toShift_ = shift;
            sameCollAttribute = false;
        }
        final int varTop = this.collator_.getVariableTop();
        if (this.variableTop_ != varTop) {
            this.variableTop_ = varTop;
            sameCollAttribute = false;
        }
        if (!sameCollAttribute) {
            this.initialize();
        }
        this.textIter_.setText(this.search_.text());
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }
    
    @Override
    protected int handleNext(final int position) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = ((this.search_.matchedIndex_ == -1) ? this.getIndex() : (this.search_.matchedIndex_ + 1));
            this.search_.setMatchedLength(0);
            this.textIter_.setOffset(this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.endIndex()) {
                this.search_.matchedIndex_ = -1;
            }
            return -1;
        }
        if (this.search_.matchedLength() <= 0) {
            this.search_.matchedIndex_ = position - 1;
        }
        this.textIter_.setOffset(position);
        if (this.search_.isCanonicalMatch_) {
            this.handleNextCanonical();
        }
        else {
            this.handleNextExact();
        }
        if (this.search_.matchedIndex_ == -1) {
            this.textIter_.setOffset(this.search_.endIndex());
        }
        else {
            this.textIter_.setOffset(this.search_.matchedIndex_);
        }
        return this.search_.matchedIndex_;
    }
    
    @Override
    protected int handlePrevious(final int position) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = ((this.search_.matchedIndex_ == -1) ? this.getIndex() : this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.beginIndex()) {
                this.setMatchNotFound();
            }
            else {
                final Search search_ = this.search_;
                --search_.matchedIndex_;
                this.textIter_.setOffset(this.search_.matchedIndex_);
                this.search_.setMatchedLength(0);
            }
        }
        else {
            this.textIter_.setOffset(position);
            if (this.search_.isCanonicalMatch_) {
                this.handlePreviousCanonical();
            }
            else {
                this.handlePreviousExact();
            }
        }
        return this.search_.matchedIndex_;
    }
    
    private static int getMask(final int strength) {
        switch (strength) {
            case 0: {
                return -65536;
            }
            case 1: {
                return -256;
            }
            default: {
                return -1;
            }
        }
    }
    
    private int getCE(int sourcece) {
        sourcece &= this.ceMask_;
        if (this.toShift_) {
            if (this.variableTop_ > sourcece) {
                if (this.strength_ >= 3) {
                    sourcece &= 0xFFFF0000;
                }
                else {
                    sourcece = 0;
                }
            }
        }
        else if (this.strength_ >= 3 && sourcece == 0) {
            sourcece = 65535;
        }
        return sourcece;
    }
    
    private static int[] addToIntArray(int[] destination, final int offset, final int value, final int increments) {
        int newlength = destination.length;
        if (offset + 1 == newlength) {
            newlength += increments;
            final int[] temp = new int[newlength];
            System.arraycopy(destination, 0, temp, 0, offset);
            destination = temp;
        }
        destination[offset] = value;
        return destination;
    }
    
    private static long[] addToLongArray(long[] destination, final int offset, final int destinationlength, final long value, final int increments) {
        int newlength = destinationlength;
        if (offset + 1 == newlength) {
            newlength += increments;
            final long[] temp = new long[newlength];
            System.arraycopy(destination, 0, temp, 0, offset);
            destination = temp;
        }
        destination[offset] = value;
        return destination;
    }
    
    private int initializePatternCETable() {
        int[] cetable = new int[256];
        final int patternlength = this.pattern_.text_.length();
        CollationElementIterator coleiter = this.utilIter_;
        if (coleiter == null) {
            coleiter = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = coleiter;
        }
        else {
            coleiter.setText(this.pattern_.text_);
        }
        int offset = 0;
        int result = 0;
        int ce;
        while ((ce = coleiter.next()) != -1) {
            final int newce = this.getCE(ce);
            if (newce != 0) {
                final int[] temp = addToIntArray(cetable, offset, newce, patternlength - coleiter.getOffset() + 1);
                ++offset;
                cetable = temp;
            }
            result += coleiter.getMaxExpansion(ce) - 1;
        }
        cetable[offset] = 0;
        this.pattern_.CE_ = cetable;
        this.pattern_.CELength_ = offset;
        return result;
    }
    
    private int initializePatternPCETable() {
        long[] pcetable = new long[256];
        final int pcetablesize = pcetable.length;
        final int patternlength = this.pattern_.text_.length();
        CollationElementIterator coleiter = this.utilIter_;
        if (coleiter == null) {
            coleiter = new CollationElementIterator(this.pattern_.text_, this.collator_);
            this.utilIter_ = coleiter;
        }
        else {
            coleiter.setText(this.pattern_.text_);
        }
        int offset = 0;
        final int result = 0;
        final CollationPCE iter = new CollationPCE(coleiter);
        long pce;
        while ((pce = iter.nextProcessed(null)) != -1L) {
            final long[] temp = addToLongArray(pcetable, offset, pcetablesize, pce, patternlength - coleiter.getOffset() + 1);
            ++offset;
            pcetable = temp;
        }
        pcetable[offset] = 0L;
        this.pattern_.PCE_ = pcetable;
        this.pattern_.PCELength_ = offset;
        return result;
    }
    
    private int initializePattern() {
        this.pattern_.PCE_ = null;
        return this.initializePatternCETable();
    }
    
    private void initialize() {
        this.initializePattern();
    }
    
    @Deprecated
    @Override
    protected void setMatchNotFound() {
        super.setMatchNotFound();
        if (this.search_.isForwardSearching_) {
            this.textIter_.setOffset(this.search_.text().getEndIndex());
        }
        else {
            this.textIter_.setOffset(0);
        }
    }
    
    private static final boolean isOutOfBounds(final int textstart, final int textlimit, final int offset) {
        return offset < textstart || offset > textlimit;
    }
    
    private boolean checkIdentical(final int start, final int end) {
        if (this.strength_ != 15) {
            return true;
        }
        String textstr = getString(this.targetText, start, end - start);
        if (Normalizer.quickCheck(textstr, Normalizer.NFD, 0) == Normalizer.NO) {
            textstr = Normalizer.decompose(textstr, false);
        }
        String patternstr = this.pattern_.text_;
        if (Normalizer.quickCheck(patternstr, Normalizer.NFD, 0) == Normalizer.NO) {
            patternstr = Normalizer.decompose(patternstr, false);
        }
        return textstr.equals(patternstr);
    }
    
    private boolean initTextProcessedIter() {
        if (this.textProcessedIter_ == null) {
            this.textProcessedIter_ = new CollationPCE(this.textIter_);
        }
        else {
            this.textProcessedIter_.init(this.textIter_);
        }
        return true;
    }
    
    private int nextBoundaryAfter(final int startIndex) {
        BreakIterator breakiterator = this.search_.breakIter();
        if (breakiterator == null) {
            breakiterator = this.search_.internalBreakIter_;
        }
        if (breakiterator != null) {
            return breakiterator.following(startIndex);
        }
        return startIndex;
    }
    
    private boolean isBreakBoundary(final int index) {
        BreakIterator breakiterator = this.search_.breakIter();
        if (breakiterator == null) {
            breakiterator = this.search_.internalBreakIter_;
        }
        return breakiterator != null && breakiterator.isBoundary(index);
    }
    
    private static int compareCE64s(final long targCE, final long patCE, final ElementComparisonType compareType) {
        if (targCE == patCE) {
            return -1;
        }
        if (compareType == ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 0;
        }
        final long targCEshifted = targCE >>> 32;
        final long patCEshifted = patCE >>> 32;
        long mask = 4294901760L;
        final int targLev1 = (int)(targCEshifted & mask);
        final int patLev1 = (int)(patCEshifted & mask);
        if (targLev1 != patLev1) {
            if (targLev1 == 0) {
                return 1;
            }
            if (patLev1 == 0 && compareType == ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 2;
            }
            return 0;
        }
        else {
            mask = 65535L;
            final int targLev2 = (int)(targCEshifted & mask);
            final int patLev2 = (int)(patCEshifted & mask);
            if (targLev2 != patLev2) {
                if (targLev2 == 0) {
                    return 1;
                }
                if (patLev2 == 0 && compareType == ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                    return 2;
                }
                return (patLev2 == StringSearch.CE_LEVEL2_BASE || (compareType == ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev2 == StringSearch.CE_LEVEL2_BASE)) ? -1 : 0;
            }
            else {
                mask = 4294901760L;
                final int targLev3 = (int)(targCE & mask);
                final int patLev3 = (int)(patCE & mask);
                if (targLev3 != patLev3) {
                    return (patLev3 == StringSearch.CE_LEVEL3_BASE || (compareType == ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && targLev3 == StringSearch.CE_LEVEL3_BASE)) ? -1 : 0;
                }
                return -1;
            }
        }
    }
    
    private boolean search(final int startIdx, final Match m) {
        if (this.pattern_.CELength_ == 0 || startIdx < this.search_.beginIndex() || startIdx > this.search_.endIndex()) {
            throw new IllegalArgumentException("search(" + startIdx + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            this.initializePatternPCETable();
        }
        this.textIter_.setOffset(startIdx);
        final CEBuffer ceb = new CEBuffer(this);
        int targetIx = 0;
        CEI targetCEI = null;
        int mStart = -1;
        int mLimit = -1;
        targetIx = 0;
        boolean found;
        while (true) {
            found = true;
            int targetIxOffset = 0;
            long patCE = 0L;
            final CEI firstCEI = ceb.get(targetIx);
            if (firstCEI == null) {
                throw new ICUException("CEBuffer.get(" + targetIx + ") returned null.");
            }
            for (int patIx = 0; patIx < this.pattern_.PCELength_; ++patIx) {
                patCE = this.pattern_.PCE_[patIx];
                targetCEI = ceb.get(targetIx + patIx + targetIxOffset);
                final int ceMatch = compareCE64s(targetCEI.ce_, patCE, this.search_.elementComparisonType_);
                if (ceMatch == 0) {
                    found = false;
                    break;
                }
                if (ceMatch > 0) {
                    if (ceMatch == 1) {
                        --patIx;
                        ++targetIxOffset;
                    }
                    else {
                        --targetIxOffset;
                    }
                }
            }
            targetIxOffset += this.pattern_.PCELength_;
            Label_0780: {
                if (!found) {
                    if (targetCEI == null) {
                        break Label_0780;
                    }
                    if (targetCEI.ce_ != -1L) {
                        break Label_0780;
                    }
                }
                if (!found) {
                    break;
                }
                final CEI lastCEI = ceb.get(targetIx + targetIxOffset - 1);
                mStart = firstCEI.lowIndex_;
                final int minLimit = lastCEI.lowIndex_;
                CEI nextCEI = null;
                int maxLimit;
                if (this.search_.elementComparisonType_ == ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
                    nextCEI = ceb.get(targetIx + targetIxOffset);
                    maxLimit = nextCEI.lowIndex_;
                    if (nextCEI.lowIndex_ == nextCEI.highIndex_ && nextCEI.ce_ != -1L) {
                        found = false;
                    }
                }
                else {
                    while (true) {
                        nextCEI = ceb.get(targetIx + targetIxOffset);
                        maxLimit = nextCEI.lowIndex_;
                        if (nextCEI.ce_ == -1L) {
                            break;
                        }
                        if ((nextCEI.ce_ >>> 32 & 0xFFFF0000L) == 0x0L) {
                            final int ceMatch2 = compareCE64s(nextCEI.ce_, patCE, this.search_.elementComparisonType_);
                            if (ceMatch2 == 0 || ceMatch2 == 2) {
                                found = false;
                                break;
                            }
                            ++targetIxOffset;
                        }
                        else {
                            if (nextCEI.lowIndex_ == nextCEI.highIndex_) {
                                found = false;
                                break;
                            }
                            break;
                        }
                    }
                }
                if (!this.isBreakBoundary(mStart)) {
                    found = false;
                }
                final int secondIx = firstCEI.highIndex_;
                if (mStart == secondIx) {
                    found = false;
                }
                final boolean allowMidclusterMatch = this.breakIterator == null && (nextCEI.ce_ >>> 32 & 0xFFFF0000L) != 0x0L && maxLimit >= lastCEI.highIndex_ && nextCEI.highIndex_ > maxLimit && (this.nfd_.hasBoundaryBefore(codePointAt(this.targetText, maxLimit)) || this.nfd_.hasBoundaryAfter(codePointBefore(this.targetText, maxLimit)));
                if (minLimit < (mLimit = maxLimit)) {
                    if (minLimit == lastCEI.highIndex_ && this.isBreakBoundary(minLimit)) {
                        mLimit = minLimit;
                    }
                    else {
                        final int nba = this.nextBoundaryAfter(minLimit);
                        if (nba >= lastCEI.highIndex_ && (!allowMidclusterMatch || nba < maxLimit)) {
                            mLimit = nba;
                        }
                    }
                }
                if (!allowMidclusterMatch) {
                    if (mLimit > maxLimit) {
                        found = false;
                    }
                    if (!this.isBreakBoundary(mLimit)) {
                        found = false;
                    }
                }
                if (!this.checkIdentical(mStart, mLimit)) {
                    found = false;
                }
                if (found) {
                    break;
                }
            }
            ++targetIx;
        }
        if (!found) {
            mLimit = -1;
            mStart = -1;
        }
        if (m != null) {
            m.start_ = mStart;
            m.limit_ = mLimit;
        }
        return found;
    }
    
    private static int codePointAt(final CharacterIterator iter, final int index) {
        final int currentIterIndex = iter.getIndex();
        int cp;
        final char codeUnit = (char)(cp = iter.setIndex(index));
        if (Character.isHighSurrogate(codeUnit)) {
            final char nextUnit = iter.next();
            if (Character.isLowSurrogate(nextUnit)) {
                cp = Character.toCodePoint(codeUnit, nextUnit);
            }
        }
        iter.setIndex(currentIterIndex);
        return cp;
    }
    
    private static int codePointBefore(final CharacterIterator iter, final int index) {
        final int currentIterIndex = iter.getIndex();
        iter.setIndex(index);
        int cp;
        final char codeUnit = (char)(cp = iter.previous());
        if (Character.isLowSurrogate(codeUnit)) {
            final char prevUnit = iter.previous();
            if (Character.isHighSurrogate(prevUnit)) {
                cp = Character.toCodePoint(prevUnit, codeUnit);
            }
        }
        iter.setIndex(currentIterIndex);
        return cp;
    }
    
    private boolean searchBackwards(final int startIdx, final Match m) {
        if (this.pattern_.CELength_ == 0 || startIdx < this.search_.beginIndex() || startIdx > this.search_.endIndex()) {
            throw new IllegalArgumentException("searchBackwards(" + startIdx + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            this.initializePatternPCETable();
        }
        final CEBuffer ceb = new CEBuffer(this);
        int targetIx = 0;
        if (startIdx < this.search_.endIndex()) {
            final BreakIterator bi = this.search_.internalBreakIter_;
            final int next = bi.following(startIdx);
            this.textIter_.setOffset(next);
            for (targetIx = 0; ceb.getPrevious(targetIx).lowIndex_ >= startIdx; ++targetIx) {}
        }
        else {
            this.textIter_.setOffset(startIdx);
        }
        CEI targetCEI = null;
        final int limitIx = targetIx;
        int mStart = -1;
        int mLimit = -1;
        targetIx = limitIx;
        boolean found;
        while (true) {
            found = true;
            final CEI lastCEI = ceb.getPrevious(targetIx);
            if (lastCEI == null) {
                throw new ICUException("CEBuffer.getPrevious(" + targetIx + ") returned null.");
            }
            int targetIxOffset = 0;
            for (int patIx = this.pattern_.PCELength_ - 1; patIx >= 0; --patIx) {
                final long patCE = this.pattern_.PCE_[patIx];
                targetCEI = ceb.getPrevious(targetIx + this.pattern_.PCELength_ - 1 - patIx + targetIxOffset);
                final int ceMatch = compareCE64s(targetCEI.ce_, patCE, this.search_.elementComparisonType_);
                if (ceMatch == 0) {
                    found = false;
                    break;
                }
                if (ceMatch > 0) {
                    if (ceMatch == 1) {
                        ++patIx;
                        ++targetIxOffset;
                    }
                    else {
                        --targetIxOffset;
                    }
                }
            }
            Label_0727: {
                if (!found) {
                    if (targetCEI == null) {
                        break Label_0727;
                    }
                    if (targetCEI.ce_ != -1L) {
                        break Label_0727;
                    }
                }
                if (!found) {
                    break;
                }
                final CEI firstCEI = ceb.getPrevious(targetIx + this.pattern_.PCELength_ - 1 + targetIxOffset);
                mStart = firstCEI.lowIndex_;
                if (!this.isBreakBoundary(mStart)) {
                    found = false;
                }
                if (mStart == firstCEI.highIndex_) {
                    found = false;
                }
                final int minLimit = lastCEI.lowIndex_;
                if (targetIx > 0) {
                    final CEI nextCEI = ceb.getPrevious(targetIx - 1);
                    if (nextCEI.lowIndex_ == nextCEI.highIndex_ && nextCEI.ce_ != -1L) {
                        found = false;
                    }
                    final int maxLimit = mLimit = nextCEI.lowIndex_;
                    final boolean allowMidclusterMatch = this.breakIterator == null && (nextCEI.ce_ >>> 32 & 0xFFFF0000L) != 0x0L && maxLimit >= lastCEI.highIndex_ && nextCEI.highIndex_ > maxLimit && (this.nfd_.hasBoundaryBefore(codePointAt(this.targetText, maxLimit)) || this.nfd_.hasBoundaryAfter(codePointBefore(this.targetText, maxLimit)));
                    if (minLimit < maxLimit) {
                        final int nba = this.nextBoundaryAfter(minLimit);
                        if (nba >= lastCEI.highIndex_ && (!allowMidclusterMatch || nba < maxLimit)) {
                            mLimit = nba;
                        }
                    }
                    if (!allowMidclusterMatch) {
                        if (mLimit > maxLimit) {
                            found = false;
                        }
                        if (!this.isBreakBoundary(mLimit)) {
                            found = false;
                        }
                    }
                }
                else {
                    final int nba2 = this.nextBoundaryAfter(minLimit);
                    int n2;
                    int n;
                    if (nba2 > 0 && startIdx > nba2) {
                        n = (n2 = nba2);
                    }
                    else {
                        n = startIdx;
                        n2 = startIdx;
                    }
                    final int maxLimit = n2;
                    mLimit = n;
                }
                if (!this.checkIdentical(mStart, mLimit)) {
                    found = false;
                }
                if (found) {
                    break;
                }
            }
            ++targetIx;
        }
        if (!found) {
            mLimit = -1;
            mStart = -1;
        }
        if (m != null) {
            m.start_ = mStart;
            m.limit_ = mLimit;
        }
        return found;
    }
    
    private boolean handleNextExact() {
        return this.handleNextCommonImpl();
    }
    
    private boolean handleNextCanonical() {
        return this.handleNextCommonImpl();
    }
    
    private boolean handleNextCommonImpl() {
        final int textOffset = this.textIter_.getOffset();
        final Match match = new Match();
        if (this.search(textOffset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        this.setMatchNotFound();
        return false;
    }
    
    private boolean handlePreviousExact() {
        return this.handlePreviousCommonImpl();
    }
    
    private boolean handlePreviousCanonical() {
        return this.handlePreviousCommonImpl();
    }
    
    private boolean handlePreviousCommonImpl() {
        int textOffset;
        if (this.search_.isOverlap_) {
            if (this.search_.matchedIndex_ != -1) {
                textOffset = this.search_.matchedIndex_ + this.search_.matchedLength() - 1;
            }
            else {
                this.initializePatternPCETable();
                if (!this.initTextProcessedIter()) {
                    this.setMatchNotFound();
                    return false;
                }
                for (int nPCEs = 0; nPCEs < this.pattern_.PCELength_ - 1; ++nPCEs) {
                    final long pce = this.textProcessedIter_.nextProcessed(null);
                    if (pce == -1L) {
                        break;
                    }
                }
                textOffset = this.textIter_.getOffset();
            }
        }
        else {
            textOffset = this.textIter_.getOffset();
        }
        final Match match = new Match();
        if (this.searchBackwards(textOffset, match)) {
            this.search_.matchedIndex_ = match.start_;
            this.search_.setMatchedLength(match.limit_ - match.start_);
            return true;
        }
        this.setMatchNotFound();
        return false;
    }
    
    private static final String getString(final CharacterIterator text, final int start, final int length) {
        final StringBuilder result = new StringBuilder(length);
        final int offset = text.getIndex();
        text.setIndex(start);
        for (int i = 0; i < length; ++i) {
            result.append(text.current());
            text.next();
        }
        text.setIndex(offset);
        return result.toString();
    }
    
    static {
        StringSearch.CE_LEVEL2_BASE = 5;
        StringSearch.CE_LEVEL3_BASE = 327680;
    }
    
    private static class Match
    {
        int start_;
        int limit_;
        
        private Match() {
            this.start_ = -1;
            this.limit_ = -1;
        }
    }
    
    private static final class Pattern
    {
        String text_;
        long[] PCE_;
        int PCELength_;
        int[] CE_;
        int CELength_;
        
        protected Pattern(final String pattern) {
            this.PCELength_ = 0;
            this.CELength_ = 0;
            this.text_ = pattern;
        }
    }
    
    private static class CollationPCE
    {
        public static final long PROCESSED_NULLORDER = -1L;
        private static final int DEFAULT_BUFFER_SIZE = 16;
        private static final int BUFFER_GROW = 8;
        private static final int PRIMARYORDERMASK = -65536;
        private static final int CONTINUATION_MARKER = 192;
        private PCEBuffer pceBuffer_;
        private CollationElementIterator cei_;
        private int strength_;
        private boolean toShift_;
        private boolean isShifted_;
        private int variableTop_;
        
        public CollationPCE(final CollationElementIterator iter) {
            this.pceBuffer_ = new PCEBuffer();
            this.init(iter);
        }
        
        public void init(final CollationElementIterator iter) {
            this.cei_ = iter;
            this.init(iter.getRuleBasedCollator());
        }
        
        private void init(final RuleBasedCollator coll) {
            this.strength_ = coll.getStrength();
            this.toShift_ = coll.isAlternateHandlingShifted();
            this.isShifted_ = false;
            this.variableTop_ = coll.getVariableTop();
        }
        
        private long processCE(final int ce) {
            long primary = 0L;
            long secondary = 0L;
            long tertiary = 0L;
            long quaternary = 0L;
            switch (this.strength_) {
                default: {
                    tertiary = CollationElementIterator.tertiaryOrder(ce);
                }
                case 1: {
                    secondary = CollationElementIterator.secondaryOrder(ce);
                }
                case 0: {
                    primary = CollationElementIterator.primaryOrder(ce);
                    if ((this.toShift_ && this.variableTop_ > ce && primary != 0L) || (this.isShifted_ && primary == 0L)) {
                        if (primary == 0L) {
                            return 0L;
                        }
                        if (this.strength_ >= 3) {
                            quaternary = primary;
                        }
                        secondary = (primary = (tertiary = 0L));
                        this.isShifted_ = true;
                    }
                    else {
                        if (this.strength_ >= 3) {
                            quaternary = 65535L;
                        }
                        this.isShifted_ = false;
                    }
                    return primary << 48 | secondary << 32 | tertiary << 16 | quaternary;
                }
            }
        }
        
        public long nextProcessed(final Range range) {
            long result = 0L;
            int low = 0;
            int high = 0;
            this.pceBuffer_.reset();
            do {
                low = this.cei_.getOffset();
                final int ce = this.cei_.next();
                high = this.cei_.getOffset();
                if (ce == -1) {
                    result = -1L;
                    break;
                }
                result = this.processCE(ce);
            } while (result == 0L);
            if (range != null) {
                range.ixLow_ = low;
                range.ixHigh_ = high;
            }
            return result;
        }
        
        public long previousProcessed(final Range range) {
            long result = 0L;
            int low = 0;
            int high = 0;
            while (this.pceBuffer_.empty()) {
                final RCEBuffer rceb = new RCEBuffer();
                boolean finish = false;
                int ce;
                do {
                    high = this.cei_.getOffset();
                    ce = this.cei_.previous();
                    low = this.cei_.getOffset();
                    if (ce == -1) {
                        if (!rceb.empty()) {
                            break;
                        }
                        finish = true;
                        break;
                    }
                    else {
                        rceb.put(ce, low, high);
                    }
                } while ((ce & 0xFFFF0000) == 0x0 || isContinuation(ce));
                if (finish) {
                    break;
                }
                while (!rceb.empty()) {
                    final RCEI rcei = rceb.get();
                    result = this.processCE(rcei.ce_);
                    if (result != 0L) {
                        this.pceBuffer_.put(result, rcei.low_, rcei.high_);
                    }
                }
            }
            if (this.pceBuffer_.empty()) {
                if (range != null) {
                    range.ixLow_ = -1;
                    range.ixHigh_ = -1;
                }
                return -1L;
            }
            final PCEI pcei = this.pceBuffer_.get();
            if (range != null) {
                range.ixLow_ = pcei.low_;
                range.ixHigh_ = pcei.high_;
            }
            return pcei.ce_;
        }
        
        private static boolean isContinuation(final int ce) {
            return (ce & 0xC0) == 0xC0;
        }
        
        public static final class Range
        {
            int ixLow_;
            int ixHigh_;
        }
        
        private static final class PCEI
        {
            long ce_;
            int low_;
            int high_;
        }
        
        private static final class PCEBuffer
        {
            private PCEI[] buffer_;
            private int bufferIndex_;
            
            private PCEBuffer() {
                this.buffer_ = new PCEI[16];
                this.bufferIndex_ = 0;
            }
            
            void reset() {
                this.bufferIndex_ = 0;
            }
            
            boolean empty() {
                return this.bufferIndex_ <= 0;
            }
            
            void put(final long ce, final int ixLow, final int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    final PCEI[] newBuffer = new PCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new PCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                ++this.bufferIndex_;
            }
            
            PCEI get() {
                if (this.bufferIndex_ > 0) {
                    final PCEI[] buffer_ = this.buffer_;
                    final int bufferIndex_ = this.bufferIndex_ - 1;
                    this.bufferIndex_ = bufferIndex_;
                    return buffer_[bufferIndex_];
                }
                return null;
            }
        }
        
        private static final class RCEI
        {
            int ce_;
            int low_;
            int high_;
        }
        
        private static final class RCEBuffer
        {
            private RCEI[] buffer_;
            private int bufferIndex_;
            
            private RCEBuffer() {
                this.buffer_ = new RCEI[16];
                this.bufferIndex_ = 0;
            }
            
            boolean empty() {
                return this.bufferIndex_ <= 0;
            }
            
            void put(final int ce, final int ixLow, final int ixHigh) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    final RCEI[] newBuffer = new RCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, newBuffer, 0, this.buffer_.length);
                    this.buffer_ = newBuffer;
                }
                this.buffer_[this.bufferIndex_] = new RCEI();
                this.buffer_[this.bufferIndex_].ce_ = ce;
                this.buffer_[this.bufferIndex_].low_ = ixLow;
                this.buffer_[this.bufferIndex_].high_ = ixHigh;
                ++this.bufferIndex_;
            }
            
            RCEI get() {
                if (this.bufferIndex_ > 0) {
                    final RCEI[] buffer_ = this.buffer_;
                    final int bufferIndex_ = this.bufferIndex_ - 1;
                    this.bufferIndex_ = bufferIndex_;
                    return buffer_[bufferIndex_];
                }
                return null;
            }
        }
    }
    
    private static class CEI
    {
        long ce_;
        int lowIndex_;
        int highIndex_;
    }
    
    private static class CEBuffer
    {
        static final int CEBUFFER_EXTRA = 32;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_JAMO_L = 8;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_OTHER = 3;
        CEI[] buf_;
        int bufSize_;
        int firstIx_;
        int limitIx_;
        StringSearch strSearch_;
        
        CEBuffer(final StringSearch ss) {
            this.strSearch_ = ss;
            this.bufSize_ = ss.pattern_.PCELength_ + 32;
            if (ss.search_.elementComparisonType_ != ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
                final String patText = ss.pattern_.text_;
                if (patText != null) {
                    for (int i = 0; i < patText.length(); ++i) {
                        final char c = patText.charAt(i);
                        if (MIGHT_BE_JAMO_L(c)) {
                            this.bufSize_ += 8;
                        }
                        else {
                            this.bufSize_ += 3;
                        }
                    }
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (!ss.initTextProcessedIter()) {
                return;
            }
            this.buf_ = new CEI[this.bufSize_];
        }
        
        CEI get(final int index) {
            final int i = index % this.bufSize_;
            if (index >= this.firstIx_ && index < this.limitIx_) {
                return this.buf_[i];
            }
            if (index == this.limitIx_) {
                ++this.limitIx_;
                if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                    ++this.firstIx_;
                }
                final CollationPCE.Range range = new CollationPCE.Range();
                if (this.buf_[i] == null) {
                    this.buf_[i] = new CEI();
                }
                this.buf_[i].ce_ = this.strSearch_.textProcessedIter_.nextProcessed(range);
                this.buf_[i].lowIndex_ = range.ixLow_;
                this.buf_[i].highIndex_ = range.ixHigh_;
                return this.buf_[i];
            }
            assert false;
            return null;
        }
        
        CEI getPrevious(final int index) {
            final int i = index % this.bufSize_;
            if (index >= this.firstIx_ && index < this.limitIx_) {
                return this.buf_[i];
            }
            if (index == this.limitIx_) {
                ++this.limitIx_;
                if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                    ++this.firstIx_;
                }
                final CollationPCE.Range range = new CollationPCE.Range();
                if (this.buf_[i] == null) {
                    this.buf_[i] = new CEI();
                }
                this.buf_[i].ce_ = this.strSearch_.textProcessedIter_.previousProcessed(range);
                this.buf_[i].lowIndex_ = range.ixLow_;
                this.buf_[i].highIndex_ = range.ixHigh_;
                return this.buf_[i];
            }
            assert false;
            return null;
        }
        
        static boolean MIGHT_BE_JAMO_L(final char c) {
            return (c >= '\u1100' && c <= '\u115e') || (c >= '\u3131' && c <= '\u314e') || (c >= '\u3165' && c <= '\u3186');
        }
    }
}
