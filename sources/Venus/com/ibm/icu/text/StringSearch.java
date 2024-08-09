/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.CollationElementIterator;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.text.SearchIterator;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Locale;

public final class StringSearch
extends SearchIterator {
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
    private static int CE_LEVEL2_BASE = 5;
    private static int CE_LEVEL3_BASE = 327680;

    public StringSearch(String string, CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator, BreakIterator breakIterator) {
        super(characterIterator, breakIterator);
        if (ruleBasedCollator.getNumericCollation()) {
            throw new UnsupportedOperationException("Numeric collation is not supported by StringSearch");
        }
        this.collator_ = ruleBasedCollator;
        this.strength_ = ruleBasedCollator.getStrength();
        this.ceMask_ = StringSearch.getMask(this.strength_);
        this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
        this.variableTop_ = ruleBasedCollator.getVariableTop();
        this.nfd_ = Normalizer2.getNFDInstance();
        this.pattern_ = new Pattern(string);
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.utilIter_ = null;
        this.textIter_ = new CollationElementIterator(characterIterator, ruleBasedCollator);
        this.textProcessedIter_ = null;
        ULocale uLocale = ruleBasedCollator.getLocale(ULocale.VALID_LOCALE);
        this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance(uLocale == null ? ULocale.ROOT : uLocale);
        this.search_.internalBreakIter_.setText((CharacterIterator)characterIterator.clone());
        this.initialize();
    }

    public StringSearch(String string, CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator) {
        this(string, characterIterator, ruleBasedCollator, null);
    }

    public StringSearch(String string, CharacterIterator characterIterator, Locale locale) {
        this(string, characterIterator, ULocale.forLocale(locale));
    }

    public StringSearch(String string, CharacterIterator characterIterator, ULocale uLocale) {
        this(string, characterIterator, (RuleBasedCollator)Collator.getInstance(uLocale), null);
    }

    public StringSearch(String string, String string2) {
        this(string, new StringCharacterIterator(string2), (RuleBasedCollator)Collator.getInstance(), null);
    }

    public RuleBasedCollator getCollator() {
        return this.collator_;
    }

    public void setCollator(RuleBasedCollator ruleBasedCollator) {
        if (ruleBasedCollator == null) {
            throw new IllegalArgumentException("Collator can not be null");
        }
        this.collator_ = ruleBasedCollator;
        this.ceMask_ = StringSearch.getMask(this.collator_.getStrength());
        ULocale uLocale = ruleBasedCollator.getLocale(ULocale.VALID_LOCALE);
        this.search_.internalBreakIter_ = BreakIterator.getCharacterInstance(uLocale == null ? ULocale.ROOT : uLocale);
        this.search_.internalBreakIter_.setText((CharacterIterator)this.search_.text().clone());
        this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
        this.variableTop_ = ruleBasedCollator.getVariableTop();
        this.textIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
        this.utilIter_ = new CollationElementIterator(this.pattern_.text_, ruleBasedCollator);
        this.initialize();
    }

    public String getPattern() {
        return this.pattern_.text_;
    }

    public void setPattern(String string) {
        if (string == null || string.length() <= 0) {
            throw new IllegalArgumentException("Pattern to search for can not be null or of length 0");
        }
        this.pattern_.text_ = string;
        this.initialize();
    }

    public boolean isCanonical() {
        return this.search_.isCanonicalMatch_;
    }

    public void setCanonical(boolean bl) {
        this.search_.isCanonicalMatch_ = bl;
    }

    @Override
    public void setTarget(CharacterIterator characterIterator) {
        super.setTarget(characterIterator);
        this.textIter_.setText(characterIterator);
    }

    @Override
    public int getIndex() {
        int n = this.textIter_.getOffset();
        if (StringSearch.isOutOfBounds(this.search_.beginIndex(), this.search_.endIndex(), n)) {
            return 1;
        }
        return n;
    }

    @Override
    public void setIndex(int n) {
        super.setIndex(n);
        this.textIter_.setOffset(n);
    }

    @Override
    public void reset() {
        int n;
        boolean bl;
        boolean bl2 = true;
        int n2 = this.collator_.getStrength();
        if (this.strength_ < 3 && n2 >= 3 || this.strength_ >= 3 && n2 < 3) {
            bl2 = false;
        }
        this.strength_ = this.collator_.getStrength();
        int n3 = StringSearch.getMask(this.strength_);
        if (this.ceMask_ != n3) {
            this.ceMask_ = n3;
            bl2 = false;
        }
        if (this.toShift_ != (bl = this.collator_.isAlternateHandlingShifted())) {
            this.toShift_ = bl;
            bl2 = false;
        }
        if (this.variableTop_ != (n = this.collator_.getVariableTop())) {
            this.variableTop_ = n;
            bl2 = false;
        }
        if (!bl2) {
            this.initialize();
        }
        this.textIter_.setText(this.search_.text());
        this.search_.setMatchedLength(0);
        this.search_.matchedIndex_ = -1;
        this.search_.isOverlap_ = false;
        this.search_.isCanonicalMatch_ = false;
        this.search_.elementComparisonType_ = SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON;
        this.search_.isForwardSearching_ = true;
        this.search_.reset_ = true;
    }

    @Override
    protected int handleNext(int n) {
        if (this.pattern_.CELength_ == 0) {
            this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? this.getIndex() : this.search_.matchedIndex_ + 1;
            this.search_.setMatchedLength(0);
            this.textIter_.setOffset(this.search_.matchedIndex_);
            if (this.search_.matchedIndex_ == this.search_.endIndex()) {
                this.search_.matchedIndex_ = -1;
            }
        } else {
            if (this.search_.matchedLength() <= 0) {
                this.search_.matchedIndex_ = n - 1;
            }
            this.textIter_.setOffset(n);
            if (this.search_.isCanonicalMatch_) {
                this.handleNextCanonical();
            } else {
                this.handleNextExact();
            }
            if (this.search_.matchedIndex_ == -1) {
                this.textIter_.setOffset(this.search_.endIndex());
            } else {
                this.textIter_.setOffset(this.search_.matchedIndex_);
            }
            return this.search_.matchedIndex_;
        }
        return 1;
    }

    @Override
    protected int handlePrevious(int n) {
        if (this.pattern_.CELength_ == 0) {
            int n2 = this.search_.matchedIndex_ = this.search_.matchedIndex_ == -1 ? this.getIndex() : this.search_.matchedIndex_;
            if (this.search_.matchedIndex_ == this.search_.beginIndex()) {
                this.setMatchNotFound();
            } else {
                --this.search_.matchedIndex_;
                this.textIter_.setOffset(this.search_.matchedIndex_);
                this.search_.setMatchedLength(0);
            }
        } else {
            this.textIter_.setOffset(n);
            if (this.search_.isCanonicalMatch_) {
                this.handlePreviousCanonical();
            } else {
                this.handlePreviousExact();
            }
        }
        return this.search_.matchedIndex_;
    }

    private static int getMask(int n) {
        switch (n) {
            case 0: {
                return 1;
            }
            case 1: {
                return 1;
            }
        }
        return 1;
    }

    private int getCE(int n) {
        n &= this.ceMask_;
        if (this.toShift_) {
            if (this.variableTop_ > n) {
                n = this.strength_ >= 3 ? (n &= 0xFFFF0000) : 0;
            }
        } else if (this.strength_ >= 3 && n == 0) {
            n = 65535;
        }
        return n;
    }

    private static int[] addToIntArray(int[] nArray, int n, int n2, int n3) {
        int n4 = nArray.length;
        if (n + 1 == n4) {
            int[] nArray2 = new int[n4 += n3];
            System.arraycopy(nArray, 0, nArray2, 0, n);
            nArray = nArray2;
        }
        nArray[n] = n2;
        return nArray;
    }

    private static long[] addToLongArray(long[] lArray, int n, int n2, long l, int n3) {
        int n4 = n2;
        if (n + 1 == n4) {
            long[] lArray2 = new long[n4 += n3];
            System.arraycopy(lArray, 0, lArray2, 0, n);
            lArray = lArray2;
        }
        lArray[n] = l;
        return lArray;
    }

    private int initializePatternCETable() {
        int n;
        int[] nArray = new int[256];
        int n2 = this.pattern_.text_.length();
        CollationElementIterator collationElementIterator = this.utilIter_;
        if (collationElementIterator == null) {
            this.utilIter_ = collationElementIterator = new CollationElementIterator(this.pattern_.text_, this.collator_);
        } else {
            collationElementIterator.setText(this.pattern_.text_);
        }
        int n3 = 0;
        int n4 = 0;
        while ((n = collationElementIterator.next()) != -1) {
            int n5 = this.getCE(n);
            if (n5 != 0) {
                int[] nArray2 = StringSearch.addToIntArray(nArray, n3, n5, n2 - collationElementIterator.getOffset() + 1);
                ++n3;
                nArray = nArray2;
            }
            n4 += collationElementIterator.getMaxExpansion(n) - 1;
        }
        nArray[n3] = 0;
        this.pattern_.CE_ = nArray;
        this.pattern_.CELength_ = n3;
        return n4;
    }

    private int initializePatternPCETable() {
        long l;
        long[] lArray = new long[256];
        int n = lArray.length;
        int n2 = this.pattern_.text_.length();
        CollationElementIterator collationElementIterator = this.utilIter_;
        if (collationElementIterator == null) {
            this.utilIter_ = collationElementIterator = new CollationElementIterator(this.pattern_.text_, this.collator_);
        } else {
            collationElementIterator.setText(this.pattern_.text_);
        }
        int n3 = 0;
        int n4 = 0;
        CollationPCE collationPCE = new CollationPCE(collationElementIterator);
        while ((l = collationPCE.nextProcessed(null)) != -1L) {
            long[] lArray2 = StringSearch.addToLongArray(lArray, n3, n, l, n2 - collationElementIterator.getOffset() + 1);
            ++n3;
            lArray = lArray2;
        }
        lArray[n3] = 0L;
        this.pattern_.PCE_ = lArray;
        this.pattern_.PCELength_ = n3;
        return n4;
    }

    private int initializePattern() {
        this.pattern_.PCE_ = null;
        return this.initializePatternCETable();
    }

    private void initialize() {
        this.initializePattern();
    }

    @Override
    @Deprecated
    protected void setMatchNotFound() {
        super.setMatchNotFound();
        if (this.search_.isForwardSearching_) {
            this.textIter_.setOffset(this.search_.text().getEndIndex());
        } else {
            this.textIter_.setOffset(0);
        }
    }

    private static final boolean isOutOfBounds(int n, int n2, int n3) {
        return n3 < n || n3 > n2;
    }

    private boolean checkIdentical(int n, int n2) {
        String string;
        if (this.strength_ != 15) {
            return false;
        }
        String string2 = StringSearch.getString(this.targetText, n, n2 - n);
        if (Normalizer.quickCheck(string2, Normalizer.NFD, 0) == Normalizer.NO) {
            string2 = Normalizer.decompose(string2, false);
        }
        if (Normalizer.quickCheck(string = this.pattern_.text_, Normalizer.NFD, 0) == Normalizer.NO) {
            string = Normalizer.decompose(string, false);
        }
        return string2.equals(string);
    }

    private boolean initTextProcessedIter() {
        if (this.textProcessedIter_ == null) {
            this.textProcessedIter_ = new CollationPCE(this.textIter_);
        } else {
            this.textProcessedIter_.init(this.textIter_);
        }
        return false;
    }

    private int nextBoundaryAfter(int n) {
        BreakIterator breakIterator = this.search_.breakIter();
        if (breakIterator == null) {
            breakIterator = this.search_.internalBreakIter_;
        }
        if (breakIterator != null) {
            return breakIterator.following(n);
        }
        return n;
    }

    private boolean isBreakBoundary(int n) {
        BreakIterator breakIterator = this.search_.breakIter();
        if (breakIterator == null) {
            breakIterator = this.search_.internalBreakIter_;
        }
        return breakIterator != null && breakIterator.isBoundary(n);
    }

    private static int compareCE64s(long l, long l2, SearchIterator.ElementComparisonType elementComparisonType) {
        if (l == l2) {
            return 1;
        }
        if (elementComparisonType == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
            return 1;
        }
        long l3 = l >>> 32;
        long l4 = 0xFFFF0000L;
        int n = (int)(l3 & l4);
        long l5 = l2 >>> 32;
        int n2 = (int)(l5 & l4);
        if (n != n2) {
            if (n == 0) {
                return 0;
            }
            if (n2 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 1;
            }
            return 1;
        }
        l4 = 65535L;
        int n3 = (int)(l3 & l4);
        int n4 = (int)(l5 & l4);
        if (n3 != n4) {
            if (n3 == 0) {
                return 0;
            }
            if (n4 == 0 && elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD) {
                return 1;
            }
            return n4 == CE_LEVEL2_BASE || elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && n3 == CE_LEVEL2_BASE ? -1 : 0;
        }
        l4 = 0xFFFF0000L;
        int n5 = (int)(l & l4);
        int n6 = (int)(l2 & l4);
        if (n5 != n6) {
            return n6 == CE_LEVEL3_BASE || elementComparisonType == SearchIterator.ElementComparisonType.ANY_BASE_WEIGHT_IS_WILDCARD && n5 == CE_LEVEL3_BASE ? -1 : 0;
        }
        return 1;
    }

    private boolean search(int n, Match match2) {
        boolean bl;
        if (this.pattern_.CELength_ == 0 || n < this.search_.beginIndex() || n > this.search_.endIndex()) {
            throw new IllegalArgumentException("search(" + n + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            this.initializePatternPCETable();
        }
        this.textIter_.setOffset(n);
        CEBuffer cEBuffer = new CEBuffer(this);
        int n2 = 0;
        CEI cEI = null;
        int n3 = -1;
        int n4 = -1;
        n2 = 0;
        while (true) {
            bl = true;
            int n5 = 0;
            long l = 0L;
            CEI cEI2 = cEBuffer.get(n2);
            if (cEI2 == null) {
                throw new ICUException("CEBuffer.get(" + n2 + ") returned null.");
            }
            for (int i = 0; i < this.pattern_.PCELength_; ++i) {
                l = this.pattern_.PCE_[i];
                cEI = cEBuffer.get(n2 + i + n5);
                int n6 = StringSearch.compareCE64s(cEI.ce_, l, this.search_.elementComparisonType_);
                if (n6 == 0) {
                    bl = false;
                    break;
                }
                if (n6 <= 0) continue;
                if (n6 == 1) {
                    --i;
                    ++n5;
                    continue;
                }
                --n5;
            }
            n5 += this.pattern_.PCELength_;
            if (bl || cEI != null && cEI.ce_ == -1L) {
                int n7;
                int n8;
                if (!bl) break;
                CEI cEI3 = cEBuffer.get(n2 + n5 - 1);
                n3 = cEI2.lowIndex_;
                int n9 = cEI3.lowIndex_;
                CEI cEI4 = null;
                if (this.search_.elementComparisonType_ == SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON) {
                    cEI4 = cEBuffer.get(n2 + n5);
                    n8 = cEI4.lowIndex_;
                    if (cEI4.lowIndex_ == cEI4.highIndex_ && cEI4.ce_ != -1L) {
                        bl = false;
                    }
                } else {
                    while (true) {
                        cEI4 = cEBuffer.get(n2 + n5);
                        n8 = cEI4.lowIndex_;
                        if (cEI4.ce_ == -1L) break;
                        if ((cEI4.ce_ >>> 32 & 0xFFFF0000L) == 0L) {
                            n7 = StringSearch.compareCE64s(cEI4.ce_, l, this.search_.elementComparisonType_);
                            if (n7 == 0 || n7 == 2) {
                                bl = false;
                                break;
                            }
                        } else {
                            if (cEI4.lowIndex_ != cEI4.highIndex_) break;
                            bl = false;
                            break;
                        }
                        ++n5;
                    }
                }
                if (!this.isBreakBoundary(n3)) {
                    bl = false;
                }
                if (n3 == (n7 = cEI2.highIndex_)) {
                    bl = false;
                }
                boolean bl2 = this.breakIterator == null && (cEI4.ce_ >>> 32 & 0xFFFF0000L) != 0L && n8 >= cEI3.highIndex_ && cEI4.highIndex_ > n8 && (this.nfd_.hasBoundaryBefore(StringSearch.codePointAt(this.targetText, n8)) || this.nfd_.hasBoundaryAfter(StringSearch.codePointBefore(this.targetText, n8)));
                n4 = n8;
                if (n9 < n8) {
                    if (n9 == cEI3.highIndex_ && this.isBreakBoundary(n9)) {
                        n4 = n9;
                    } else {
                        int n10 = this.nextBoundaryAfter(n9);
                        if (!(n10 < cEI3.highIndex_ || bl2 && n10 >= n8)) {
                            n4 = n10;
                        }
                    }
                }
                if (!bl2) {
                    if (n4 > n8) {
                        bl = false;
                    }
                    if (!this.isBreakBoundary(n4)) {
                        bl = false;
                    }
                }
                if (!this.checkIdentical(n3, n4)) {
                    bl = false;
                }
                if (bl) break;
            }
            ++n2;
        }
        if (!bl) {
            n4 = -1;
            n3 = -1;
        }
        if (match2 != null) {
            match2.start_ = n3;
            match2.limit_ = n4;
        }
        return bl;
    }

    private static int codePointAt(CharacterIterator characterIterator, int n) {
        char c;
        int n2;
        int n3 = characterIterator.getIndex();
        int n4 = n2 = characterIterator.setIndex(n);
        if (Character.isHighSurrogate((char)n2) && Character.isLowSurrogate(c = characterIterator.next())) {
            n4 = Character.toCodePoint((char)n2, c);
        }
        characterIterator.setIndex(n3);
        return n4;
    }

    private static int codePointBefore(CharacterIterator characterIterator, int n) {
        char c;
        int n2;
        int n3 = characterIterator.getIndex();
        characterIterator.setIndex(n);
        int n4 = n2 = characterIterator.previous();
        if (Character.isLowSurrogate((char)n2) && Character.isHighSurrogate(c = characterIterator.previous())) {
            n4 = Character.toCodePoint(c, (char)n2);
        }
        characterIterator.setIndex(n3);
        return n4;
    }

    private boolean searchBackwards(int n, Match match2) {
        boolean bl;
        int n2;
        Object object;
        if (this.pattern_.CELength_ == 0 || n < this.search_.beginIndex() || n > this.search_.endIndex()) {
            throw new IllegalArgumentException("searchBackwards(" + n + ", m) - expected position to be between " + this.search_.beginIndex() + " and " + this.search_.endIndex());
        }
        if (this.pattern_.PCE_ == null) {
            this.initializePatternPCETable();
        }
        CEBuffer cEBuffer = new CEBuffer(this);
        int n3 = 0;
        if (n < this.search_.endIndex()) {
            object = this.search_.internalBreakIter_;
            n2 = ((BreakIterator)object).following(n);
            this.textIter_.setOffset(n2);
            n3 = 0;
            while (cEBuffer.getPrevious((int)n3).lowIndex_ >= n) {
                ++n3;
            }
        } else {
            this.textIter_.setOffset(n);
        }
        object = null;
        int n4 = n3;
        int n5 = -1;
        int n6 = -1;
        n3 = n4;
        while (true) {
            int n7;
            bl = true;
            CEI cEI = cEBuffer.getPrevious(n3);
            if (cEI == null) {
                throw new ICUException("CEBuffer.getPrevious(" + n3 + ") returned null.");
            }
            int n8 = 0;
            for (n2 = this.pattern_.PCELength_ - 1; n2 >= 0; --n2) {
                long l = this.pattern_.PCE_[n2];
                object = cEBuffer.getPrevious(n3 + this.pattern_.PCELength_ - 1 - n2 + n8);
                n7 = StringSearch.compareCE64s(((CEI)object).ce_, l, this.search_.elementComparisonType_);
                if (n7 == 0) {
                    bl = false;
                    break;
                }
                if (n7 <= 0) continue;
                if (n7 == 1) {
                    ++n2;
                    ++n8;
                    continue;
                }
                --n8;
            }
            if (bl || object != null && ((CEI)object).ce_ == -1L) {
                int n9;
                if (!bl) break;
                CEI cEI2 = cEBuffer.getPrevious(n3 + this.pattern_.PCELength_ - 1 + n8);
                n5 = cEI2.lowIndex_;
                if (!this.isBreakBoundary(n5)) {
                    bl = false;
                }
                if (n5 == cEI2.highIndex_) {
                    bl = false;
                }
                int n10 = cEI.lowIndex_;
                if (n3 > 0) {
                    int n11;
                    CEI cEI3 = cEBuffer.getPrevious(n3 - 1);
                    if (cEI3.lowIndex_ == cEI3.highIndex_ && cEI3.ce_ != -1L) {
                        bl = false;
                    }
                    n6 = n9 = cEI3.lowIndex_;
                    int n12 = n7 = this.breakIterator == null && (cEI3.ce_ >>> 32 & 0xFFFF0000L) != 0L && n9 >= cEI.highIndex_ && cEI3.highIndex_ > n9 && (this.nfd_.hasBoundaryBefore(StringSearch.codePointAt(this.targetText, n9)) || this.nfd_.hasBoundaryAfter(StringSearch.codePointBefore(this.targetText, n9))) ? 1 : 0;
                    if (n10 < n9 && (n11 = this.nextBoundaryAfter(n10)) >= cEI.highIndex_ && (n7 == 0 || n11 < n9)) {
                        n6 = n11;
                    }
                    if (n7 == 0) {
                        if (n6 > n9) {
                            bl = false;
                        }
                        if (!this.isBreakBoundary(n6)) {
                            bl = false;
                        }
                    }
                } else {
                    int n13 = this.nextBoundaryAfter(n10);
                    n9 = n13 > 0 && n > n13 ? n13 : n;
                    n6 = n9;
                }
                if (!this.checkIdentical(n5, n6)) {
                    bl = false;
                }
                if (bl) break;
            }
            ++n3;
        }
        if (!bl) {
            n6 = -1;
            n5 = -1;
        }
        if (match2 != null) {
            match2.start_ = n5;
            match2.limit_ = n6;
        }
        return bl;
    }

    private boolean handleNextExact() {
        return this.handleNextCommonImpl();
    }

    private boolean handleNextCanonical() {
        return this.handleNextCommonImpl();
    }

    private boolean handleNextCommonImpl() {
        Match match2;
        int n = this.textIter_.getOffset();
        if (this.search(n, match2 = new Match(null))) {
            this.search_.matchedIndex_ = match2.start_;
            this.search_.setMatchedLength(match2.limit_ - match2.start_);
            return false;
        }
        this.setMatchNotFound();
        return true;
    }

    private boolean handlePreviousExact() {
        return this.handlePreviousCommonImpl();
    }

    private boolean handlePreviousCanonical() {
        return this.handlePreviousCommonImpl();
    }

    private boolean handlePreviousCommonImpl() {
        int n;
        if (this.search_.isOverlap_) {
            if (this.search_.matchedIndex_ != -1) {
                n = this.search_.matchedIndex_ + this.search_.matchedLength() - 1;
            } else {
                long l;
                this.initializePatternPCETable();
                if (!this.initTextProcessedIter()) {
                    this.setMatchNotFound();
                    return true;
                }
                for (int i = 0; i < this.pattern_.PCELength_ - 1 && (l = this.textProcessedIter_.nextProcessed(null)) != -1L; ++i) {
                }
                n = this.textIter_.getOffset();
            }
        } else {
            n = this.textIter_.getOffset();
        }
        Match match2 = new Match(null);
        if (this.searchBackwards(n, match2)) {
            this.search_.matchedIndex_ = match2.start_;
            this.search_.setMatchedLength(match2.limit_ - match2.start_);
            return false;
        }
        this.setMatchNotFound();
        return true;
    }

    private static final String getString(CharacterIterator characterIterator, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = characterIterator.getIndex();
        characterIterator.setIndex(n);
        for (int i = 0; i < n2; ++i) {
            stringBuilder.append(characterIterator.current());
            characterIterator.next();
        }
        characterIterator.setIndex(n3);
        return stringBuilder.toString();
    }

    static Pattern access$500(StringSearch stringSearch) {
        return stringSearch.pattern_;
    }

    static boolean access$600(StringSearch stringSearch) {
        return stringSearch.initTextProcessedIter();
    }

    static CollationPCE access$800(StringSearch stringSearch) {
        return stringSearch.textProcessedIter_;
    }

    private static class CEBuffer {
        static final int CEBUFFER_EXTRA = 32;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_JAMO_L = 8;
        static final int MAX_TARGET_IGNORABLES_PER_PAT_OTHER = 3;
        CEI[] buf_;
        int bufSize_;
        int firstIx_;
        int limitIx_;
        StringSearch strSearch_;
        static final boolean $assertionsDisabled = !StringSearch.class.desiredAssertionStatus();

        CEBuffer(StringSearch stringSearch) {
            String string;
            this.strSearch_ = stringSearch;
            this.bufSize_ = StringSearch.access$500((StringSearch)stringSearch).PCELength_ + 32;
            if (stringSearch.search_.elementComparisonType_ != SearchIterator.ElementComparisonType.STANDARD_ELEMENT_COMPARISON && (string = StringSearch.access$500((StringSearch)stringSearch).text_) != null) {
                for (int i = 0; i < string.length(); ++i) {
                    char c = string.charAt(i);
                    if (CEBuffer.MIGHT_BE_JAMO_L(c)) {
                        this.bufSize_ += 8;
                        continue;
                    }
                    this.bufSize_ += 3;
                }
            }
            this.firstIx_ = 0;
            this.limitIx_ = 0;
            if (!StringSearch.access$600(stringSearch)) {
                return;
            }
            this.buf_ = new CEI[this.bufSize_];
        }

        CEI get(int n) {
            int n2 = n % this.bufSize_;
            if (n >= this.firstIx_ && n < this.limitIx_) {
                return this.buf_[n2];
            }
            if (n != this.limitIx_) {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                return null;
            }
            ++this.limitIx_;
            if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                ++this.firstIx_;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            if (this.buf_[n2] == null) {
                this.buf_[n2] = new CEI(null);
            }
            this.buf_[n2].ce_ = StringSearch.access$800(this.strSearch_).nextProcessed(range);
            this.buf_[n2].lowIndex_ = range.ixLow_;
            this.buf_[n2].highIndex_ = range.ixHigh_;
            return this.buf_[n2];
        }

        CEI getPrevious(int n) {
            int n2 = n % this.bufSize_;
            if (n >= this.firstIx_ && n < this.limitIx_) {
                return this.buf_[n2];
            }
            if (n != this.limitIx_) {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                return null;
            }
            ++this.limitIx_;
            if (this.limitIx_ - this.firstIx_ >= this.bufSize_) {
                ++this.firstIx_;
            }
            CollationPCE.Range range = new CollationPCE.Range();
            if (this.buf_[n2] == null) {
                this.buf_[n2] = new CEI(null);
            }
            this.buf_[n2].ce_ = StringSearch.access$800(this.strSearch_).previousProcessed(range);
            this.buf_[n2].lowIndex_ = range.ixLow_;
            this.buf_[n2].highIndex_ = range.ixHigh_;
            return this.buf_[n2];
        }

        static boolean MIGHT_BE_JAMO_L(char c) {
            return c >= '\u1100' && c <= '\u115e' || c >= '\u3131' && c <= '\u314e' || c >= '\u3165' && c <= '\u3186';
        }
    }

    private static class CEI {
        long ce_;
        int lowIndex_;
        int highIndex_;

        private CEI() {
        }

        CEI(1 var1_1) {
            this();
        }
    }

    private static class CollationPCE {
        public static final long PROCESSED_NULLORDER = -1L;
        private static final int DEFAULT_BUFFER_SIZE = 16;
        private static final int BUFFER_GROW = 8;
        private static final int PRIMARYORDERMASK = -65536;
        private static final int CONTINUATION_MARKER = 192;
        private PCEBuffer pceBuffer_ = new PCEBuffer(null);
        private CollationElementIterator cei_;
        private int strength_;
        private boolean toShift_;
        private boolean isShifted_;
        private int variableTop_;

        public CollationPCE(CollationElementIterator collationElementIterator) {
            this.init(collationElementIterator);
        }

        public void init(CollationElementIterator collationElementIterator) {
            this.cei_ = collationElementIterator;
            this.init(collationElementIterator.getRuleBasedCollator());
        }

        private void init(RuleBasedCollator ruleBasedCollator) {
            this.strength_ = ruleBasedCollator.getStrength();
            this.toShift_ = ruleBasedCollator.isAlternateHandlingShifted();
            this.isShifted_ = false;
            this.variableTop_ = ruleBasedCollator.getVariableTop();
        }

        private long processCE(int n) {
            long l = 0L;
            long l2 = 0L;
            long l3 = 0L;
            long l4 = 0L;
            switch (this.strength_) {
                default: {
                    l3 = CollationElementIterator.tertiaryOrder(n);
                }
                case 1: {
                    l2 = CollationElementIterator.secondaryOrder(n);
                }
                case 0: 
            }
            l = CollationElementIterator.primaryOrder(n);
            if (this.toShift_ && this.variableTop_ > n && l != 0L || this.isShifted_ && l == 0L) {
                if (l == 0L) {
                    return 0L;
                }
                if (this.strength_ >= 3) {
                    l4 = l;
                }
                l3 = 0L;
                l2 = 0L;
                l = 0L;
                this.isShifted_ = true;
            } else {
                if (this.strength_ >= 3) {
                    l4 = 65535L;
                }
                this.isShifted_ = false;
            }
            return l << 48 | l2 << 32 | l3 << 16 | l4;
        }

        public long nextProcessed(Range range) {
            int n;
            long l = 0L;
            int n2 = 0;
            int n3 = 0;
            this.pceBuffer_.reset();
            do {
                n2 = this.cei_.getOffset();
                n = this.cei_.next();
                n3 = this.cei_.getOffset();
                if (n != -1) continue;
                l = -1L;
                break;
            } while ((l = this.processCE(n)) == 0L);
            if (range != null) {
                range.ixLow_ = n2;
                range.ixHigh_ = n3;
            }
            return l;
        }

        public long previousProcessed(Range range) {
            Object object;
            long l = 0L;
            int n = 0;
            int n2 = 0;
            while (this.pceBuffer_.empty()) {
                int n3;
                object = new RCEBuffer(null);
                boolean bl = false;
                do {
                    n2 = this.cei_.getOffset();
                    n3 = this.cei_.previous();
                    n = this.cei_.getOffset();
                    if (n3 == -1) {
                        if (!((RCEBuffer)object).empty()) break;
                        bl = true;
                        break;
                    }
                    ((RCEBuffer)object).put(n3, n, n2);
                } while ((n3 & 0xFFFF0000) == 0 || CollationPCE.isContinuation(n3));
                if (bl) break;
                while (!((RCEBuffer)object).empty()) {
                    RCEI rCEI = ((RCEBuffer)object).get();
                    l = this.processCE(rCEI.ce_);
                    if (l == 0L) continue;
                    this.pceBuffer_.put(l, rCEI.low_, rCEI.high_);
                }
            }
            if (this.pceBuffer_.empty()) {
                if (range != null) {
                    range.ixLow_ = -1;
                    range.ixHigh_ = -1;
                }
                return -1L;
            }
            object = this.pceBuffer_.get();
            if (range != null) {
                range.ixLow_ = ((PCEI)object).low_;
                range.ixHigh_ = ((PCEI)object).high_;
            }
            return ((PCEI)object).ce_;
        }

        private static boolean isContinuation(int n) {
            return (n & 0xC0) == 192;
        }

        private static final class RCEBuffer {
            private RCEI[] buffer_ = new RCEI[16];
            private int bufferIndex_ = 0;

            private RCEBuffer() {
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(int n, int n2, int n3) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    RCEI[] rCEIArray = new RCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, rCEIArray, 0, this.buffer_.length);
                    this.buffer_ = rCEIArray;
                }
                this.buffer_[this.bufferIndex_] = new RCEI(null);
                this.buffer_[this.bufferIndex_].ce_ = n;
                this.buffer_[this.bufferIndex_].low_ = n2;
                this.buffer_[this.bufferIndex_].high_ = n3;
                ++this.bufferIndex_;
            }

            RCEI get() {
                if (this.bufferIndex_ > 0) {
                    return this.buffer_[--this.bufferIndex_];
                }
                return null;
            }

            RCEBuffer(1 var1_1) {
                this();
            }
        }

        private static final class RCEI {
            int ce_;
            int low_;
            int high_;

            private RCEI() {
            }

            RCEI(1 var1_1) {
                this();
            }
        }

        private static final class PCEBuffer {
            private PCEI[] buffer_ = new PCEI[16];
            private int bufferIndex_ = 0;

            private PCEBuffer() {
            }

            void reset() {
                this.bufferIndex_ = 0;
            }

            boolean empty() {
                return this.bufferIndex_ <= 0;
            }

            void put(long l, int n, int n2) {
                if (this.bufferIndex_ >= this.buffer_.length) {
                    PCEI[] pCEIArray = new PCEI[this.buffer_.length + 8];
                    System.arraycopy(this.buffer_, 0, pCEIArray, 0, this.buffer_.length);
                    this.buffer_ = pCEIArray;
                }
                this.buffer_[this.bufferIndex_] = new PCEI(null);
                this.buffer_[this.bufferIndex_].ce_ = l;
                this.buffer_[this.bufferIndex_].low_ = n;
                this.buffer_[this.bufferIndex_].high_ = n2;
                ++this.bufferIndex_;
            }

            PCEI get() {
                if (this.bufferIndex_ > 0) {
                    return this.buffer_[--this.bufferIndex_];
                }
                return null;
            }

            PCEBuffer(1 var1_1) {
                this();
            }
        }

        private static final class PCEI {
            long ce_;
            int low_;
            int high_;

            private PCEI() {
            }

            PCEI(1 var1_1) {
                this();
            }
        }

        public static final class Range {
            int ixLow_;
            int ixHigh_;
        }
    }

    private static final class Pattern {
        String text_;
        long[] PCE_;
        int PCELength_ = 0;
        int[] CE_;
        int CELength_ = 0;

        protected Pattern(String string) {
            this.text_ = string;
        }
    }

    private static class Match {
        int start_ = -1;
        int limit_ = -1;

        private Match() {
        }

        Match(1 var1_1) {
            this();
        }
    }
}

