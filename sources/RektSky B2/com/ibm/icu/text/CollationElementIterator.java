package com.ibm.icu.text;

import java.text.*;
import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.impl.coll.*;

public final class CollationElementIterator
{
    private CollationIterator iter_;
    private RuleBasedCollator rbc_;
    private int otherHalf_;
    private byte dir_;
    private UVector32 offsets_;
    private String string_;
    public static final int NULLORDER = -1;
    public static final int IGNORABLE = 0;
    
    public static final int primaryOrder(final int ce) {
        return ce >>> 16 & 0xFFFF;
    }
    
    public static final int secondaryOrder(final int ce) {
        return ce >>> 8 & 0xFF;
    }
    
    public static final int tertiaryOrder(final int ce) {
        return ce & 0xFF;
    }
    
    private static final int getFirstHalf(final long p, final int lower32) {
        return ((int)p & 0xFFFF0000) | (lower32 >> 16 & 0xFF00) | (lower32 >> 8 & 0xFF);
    }
    
    private static final int getSecondHalf(final long p, final int lower32) {
        return (int)p << 16 | (lower32 >> 8 & 0xFF00) | (lower32 & 0x3F);
    }
    
    private static final boolean ceNeedsTwoParts(final long ce) {
        return (ce & 0xFFFF00FF003FL) != 0x0L;
    }
    
    private CollationElementIterator(final RuleBasedCollator collator) {
        this.iter_ = null;
        this.rbc_ = collator;
        this.otherHalf_ = 0;
        this.dir_ = 0;
        this.offsets_ = null;
    }
    
    CollationElementIterator(final String source, final RuleBasedCollator collator) {
        this(collator);
        this.setText(source);
    }
    
    CollationElementIterator(final CharacterIterator source, final RuleBasedCollator collator) {
        this(collator);
        this.setText(source);
    }
    
    CollationElementIterator(final UCharacterIterator source, final RuleBasedCollator collator) {
        this(collator);
        this.setText(source);
    }
    
    public int getOffset() {
        if (this.dir_ >= 0 || this.offsets_ == null || this.offsets_.isEmpty()) {
            return this.iter_.getOffset();
        }
        int i = this.iter_.getCEsLength();
        if (this.otherHalf_ != 0) {
            ++i;
        }
        assert i < this.offsets_.size();
        return this.offsets_.elementAti(i);
    }
    
    public int next() {
        if (this.dir_ > 1) {
            if (this.otherHalf_ != 0) {
                final int oh = this.otherHalf_;
                this.otherHalf_ = 0;
                return oh;
            }
        }
        else if (this.dir_ == 1) {
            this.dir_ = 2;
        }
        else {
            if (this.dir_ != 0) {
                throw new IllegalStateException("Illegal change of direction");
            }
            this.dir_ = 2;
        }
        this.iter_.clearCEsIfNoneRemaining();
        final long ce = this.iter_.nextCE();
        if (ce == 4311744768L) {
            return -1;
        }
        final long p = ce >>> 32;
        final int lower32 = (int)ce;
        final int firstHalf = getFirstHalf(p, lower32);
        final int secondHalf = getSecondHalf(p, lower32);
        if (secondHalf != 0) {
            this.otherHalf_ = (secondHalf | 0xC0);
        }
        return firstHalf;
    }
    
    public int previous() {
        if (this.dir_ < 0) {
            if (this.otherHalf_ != 0) {
                final int oh = this.otherHalf_;
                this.otherHalf_ = 0;
                return oh;
            }
        }
        else if (this.dir_ == 0) {
            this.iter_.resetToOffset(this.string_.length());
            this.dir_ = -1;
        }
        else {
            if (this.dir_ != 1) {
                throw new IllegalStateException("Illegal change of direction");
            }
            this.dir_ = -1;
        }
        if (this.offsets_ == null) {
            this.offsets_ = new UVector32();
        }
        final int limitOffset = (this.iter_.getCEsLength() == 0) ? this.iter_.getOffset() : 0;
        final long ce = this.iter_.previousCE(this.offsets_);
        if (ce == 4311744768L) {
            return -1;
        }
        final long p = ce >>> 32;
        final int lower32 = (int)ce;
        final int firstHalf = getFirstHalf(p, lower32);
        final int secondHalf = getSecondHalf(p, lower32);
        if (secondHalf != 0) {
            if (this.offsets_.isEmpty()) {
                this.offsets_.addElement(this.iter_.getOffset());
                this.offsets_.addElement(limitOffset);
            }
            this.otherHalf_ = firstHalf;
            return secondHalf | 0xC0;
        }
        return firstHalf;
    }
    
    public void reset() {
        this.iter_.resetToOffset(0);
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }
    
    public void setOffset(int newOffset) {
        if (0 < newOffset && newOffset < this.string_.length()) {
            int offset = newOffset;
            do {
                final char c = this.string_.charAt(offset);
                if (!this.rbc_.isUnsafe(c)) {
                    break;
                }
                if (Character.isHighSurrogate(c) && !this.rbc_.isUnsafe(this.string_.codePointAt(offset))) {
                    break;
                }
            } while (--offset > 0);
            if (offset < newOffset) {
                int lastSafeOffset = offset;
                do {
                    this.iter_.resetToOffset(lastSafeOffset);
                    do {
                        this.iter_.nextCE();
                    } while ((offset = this.iter_.getOffset()) == lastSafeOffset);
                    if (offset <= newOffset) {
                        lastSafeOffset = offset;
                    }
                } while (offset < newOffset);
                newOffset = lastSafeOffset;
            }
        }
        this.iter_.resetToOffset(newOffset);
        this.otherHalf_ = 0;
        this.dir_ = 1;
    }
    
    public void setText(final String source) {
        this.string_ = source;
        final boolean numeric = this.rbc_.settings.readOnly().isNumeric();
        CollationIterator newIter;
        if (this.rbc_.settings.readOnly().dontCheckFCD()) {
            newIter = new UTF16CollationIterator(this.rbc_.data, numeric, this.string_, 0);
        }
        else {
            newIter = new FCDUTF16CollationIterator(this.rbc_.data, numeric, this.string_, 0);
        }
        this.iter_ = newIter;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }
    
    public void setText(final UCharacterIterator source) {
        this.string_ = source.getText();
        UCharacterIterator src;
        try {
            src = (UCharacterIterator)source.clone();
        }
        catch (CloneNotSupportedException e) {
            this.setText(source.getText());
            return;
        }
        src.setToStart();
        final boolean numeric = this.rbc_.settings.readOnly().isNumeric();
        CollationIterator newIter;
        if (this.rbc_.settings.readOnly().dontCheckFCD()) {
            newIter = new IterCollationIterator(this.rbc_.data, numeric, src);
        }
        else {
            newIter = new FCDIterCollationIterator(this.rbc_.data, numeric, src, 0);
        }
        this.iter_ = newIter;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }
    
    public void setText(final CharacterIterator source) {
        final UCharacterIterator src = new CharacterIteratorWrapper(source);
        src.setToStart();
        this.string_ = src.getText();
        final boolean numeric = this.rbc_.settings.readOnly().isNumeric();
        CollationIterator newIter;
        if (this.rbc_.settings.readOnly().dontCheckFCD()) {
            newIter = new IterCollationIterator(this.rbc_.data, numeric, src);
        }
        else {
            newIter = new FCDIterCollationIterator(this.rbc_.data, numeric, src, 0);
        }
        this.iter_ = newIter;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }
    
    static final Map<Integer, Integer> computeMaxExpansions(final CollationData data) {
        final Map<Integer, Integer> maxExpansions = new HashMap<Integer, Integer>();
        final MaxExpSink sink = new MaxExpSink(maxExpansions);
        new ContractionsAndExpansions(null, null, sink, true).forData(data);
        return maxExpansions;
    }
    
    public int getMaxExpansion(final int ce) {
        return getMaxExpansion(this.rbc_.tailoring.maxExpansions, ce);
    }
    
    static int getMaxExpansion(final Map<Integer, Integer> maxExpansions, final int order) {
        if (order == 0) {
            return 1;
        }
        final Integer max;
        if (maxExpansions != null && (max = maxExpansions.get(order)) != null) {
            return max;
        }
        if ((order & 0xC0) == 0xC0) {
            return 2;
        }
        return 1;
    }
    
    private byte normalizeDir() {
        return (byte)((this.dir_ == 1) ? 0 : this.dir_);
    }
    
    @Override
    public boolean equals(final Object that) {
        if (that == this) {
            return true;
        }
        if (that instanceof CollationElementIterator) {
            final CollationElementIterator thatceiter = (CollationElementIterator)that;
            return this.rbc_.equals(thatceiter.rbc_) && this.otherHalf_ == thatceiter.otherHalf_ && this.normalizeDir() == thatceiter.normalizeDir() && this.string_.equals(thatceiter.string_) && this.iter_.equals(thatceiter.iter_);
        }
        return false;
    }
    
    @Deprecated
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Deprecated
    public RuleBasedCollator getRuleBasedCollator() {
        return this.rbc_;
    }
    
    private static final class MaxExpSink implements ContractionsAndExpansions.CESink
    {
        private Map<Integer, Integer> maxExpansions;
        
        MaxExpSink(final Map<Integer, Integer> h) {
            this.maxExpansions = h;
        }
        
        @Override
        public void handleCE(final long ce) {
        }
        
        @Override
        public void handleExpansion(final long[] ces, final int start, final int length) {
            if (length <= 1) {
                return;
            }
            int count = 0;
            for (int i = 0; i < length; ++i) {
                count += (ceNeedsTwoParts(ces[start + i]) ? 2 : 1);
            }
            final long ce = ces[start + length - 1];
            final long p = ce >>> 32;
            final int lower32 = (int)ce;
            int lastHalf = getSecondHalf(p, lower32);
            if (lastHalf == 0) {
                lastHalf = getFirstHalf(p, lower32);
                assert lastHalf != 0;
            }
            else {
                lastHalf |= 0xC0;
            }
            final Integer oldCount = this.maxExpansions.get(lastHalf);
            if (oldCount == null || count > oldCount) {
                this.maxExpansions.put(lastHalf, count);
            }
        }
    }
}
