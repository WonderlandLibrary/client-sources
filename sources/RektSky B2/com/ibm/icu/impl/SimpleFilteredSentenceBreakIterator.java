package com.ibm.icu.impl;

import java.text.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import java.util.*;

public class SimpleFilteredSentenceBreakIterator extends BreakIterator
{
    private BreakIterator delegate;
    private UCharacterIterator text;
    private CharsTrie backwardsTrie;
    private CharsTrie forwardsPartialTrie;
    
    public SimpleFilteredSentenceBreakIterator(final BreakIterator adoptBreakIterator, final CharsTrie forwardsPartialTrie, final CharsTrie backwardsTrie) {
        this.delegate = adoptBreakIterator;
        this.forwardsPartialTrie = forwardsPartialTrie;
        this.backwardsTrie = backwardsTrie;
    }
    
    private final void resetState() {
        this.text = UCharacterIterator.getInstance((CharacterIterator)this.delegate.getText().clone());
    }
    
    private final boolean breakExceptionAt(final int n) {
        int bestPosn = -1;
        int bestValue = -1;
        this.text.setIndex(n);
        this.backwardsTrie.reset();
        int uch;
        if ((uch = this.text.previousCodePoint()) != 32) {
            uch = this.text.nextCodePoint();
        }
        BytesTrie.Result r = BytesTrie.Result.INTERMEDIATE_VALUE;
        while ((uch = this.text.previousCodePoint()) != -1 && (r = this.backwardsTrie.nextForCodePoint(uch)).hasNext()) {
            if (r.hasValue()) {
                bestPosn = this.text.getIndex();
                bestValue = this.backwardsTrie.getValue();
            }
        }
        if (r.matches()) {
            bestValue = this.backwardsTrie.getValue();
            bestPosn = this.text.getIndex();
        }
        if (bestPosn >= 0) {
            if (bestValue == 2) {
                return true;
            }
            if (bestValue == 1 && this.forwardsPartialTrie != null) {
                this.forwardsPartialTrie.reset();
                BytesTrie.Result rfwd = BytesTrie.Result.INTERMEDIATE_VALUE;
                this.text.setIndex(bestPosn);
                while ((uch = this.text.nextCodePoint()) != -1 && (rfwd = this.forwardsPartialTrie.nextForCodePoint(uch)).hasNext()) {}
                if (rfwd.matches()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private final int internalNext(int n) {
        if (n == -1 || this.backwardsTrie == null) {
            return n;
        }
        this.resetState();
        for (int textLen = this.text.getLength(); n != -1 && n != textLen; n = this.delegate.next()) {
            if (!this.breakExceptionAt(n)) {
                return n;
            }
        }
        return n;
    }
    
    private final int internalPrev(int n) {
        if (n == 0 || n == -1 || this.backwardsTrie == null) {
            return n;
        }
        this.resetState();
        while (n != -1 && n != 0) {
            if (!this.breakExceptionAt(n)) {
                return n;
            }
            n = this.delegate.previous();
        }
        return n;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)obj;
        return this.delegate.equals(other.delegate) && this.text.equals(other.text) && this.backwardsTrie.equals(other.backwardsTrie) && this.forwardsPartialTrie.equals(other.forwardsPartialTrie);
    }
    
    @Override
    public int hashCode() {
        return this.forwardsPartialTrie.hashCode() * 39 + this.backwardsTrie.hashCode() * 11 + this.delegate.hashCode();
    }
    
    @Override
    public Object clone() {
        final SimpleFilteredSentenceBreakIterator other = (SimpleFilteredSentenceBreakIterator)super.clone();
        return other;
    }
    
    @Override
    public int first() {
        return this.delegate.first();
    }
    
    @Override
    public int preceding(final int offset) {
        return this.internalPrev(this.delegate.preceding(offset));
    }
    
    @Override
    public int previous() {
        return this.internalPrev(this.delegate.previous());
    }
    
    @Override
    public int current() {
        return this.delegate.current();
    }
    
    @Override
    public boolean isBoundary(final int offset) {
        if (!this.delegate.isBoundary(offset)) {
            return false;
        }
        if (this.backwardsTrie == null) {
            return true;
        }
        this.resetState();
        return !this.breakExceptionAt(offset);
    }
    
    @Override
    public int next() {
        return this.internalNext(this.delegate.next());
    }
    
    @Override
    public int next(final int n) {
        return this.internalNext(this.delegate.next(n));
    }
    
    @Override
    public int following(final int offset) {
        return this.internalNext(this.delegate.following(offset));
    }
    
    @Override
    public int last() {
        return this.delegate.last();
    }
    
    @Override
    public CharacterIterator getText() {
        return this.delegate.getText();
    }
    
    @Override
    public void setText(final CharacterIterator newText) {
        this.delegate.setText(newText);
    }
    
    public static class Builder extends FilteredBreakIteratorBuilder
    {
        private HashSet<CharSequence> filterSet;
        static final int PARTIAL = 1;
        static final int MATCH = 2;
        static final int SuppressInReverse = 1;
        static final int AddToForward = 2;
        
        public Builder(final Locale loc) {
            this(ULocale.forLocale(loc));
        }
        
        public Builder(final ULocale loc) {
            this.filterSet = new HashSet<CharSequence>();
            final ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/brkitr", loc, ICUResourceBundle.OpenType.LOCALE_ROOT);
            final ICUResourceBundle breaks = rb.findWithFallback("exceptions/SentenceBreak");
            if (breaks != null) {
                for (int index = 0, size = breaks.getSize(); index < size; ++index) {
                    final ICUResourceBundle b = (ICUResourceBundle)breaks.get(index);
                    final String br = b.getString();
                    this.filterSet.add(br);
                }
            }
        }
        
        public Builder() {
            this.filterSet = new HashSet<CharSequence>();
        }
        
        @Override
        public boolean suppressBreakAfter(final CharSequence str) {
            return this.filterSet.add(str);
        }
        
        @Override
        public boolean unsuppressBreakAfter(final CharSequence str) {
            return this.filterSet.remove(str);
        }
        
        @Override
        public BreakIterator wrapIteratorWithFilter(final BreakIterator adoptBreakIterator) {
            if (this.filterSet.isEmpty()) {
                return adoptBreakIterator;
            }
            final CharsTrieBuilder builder = new CharsTrieBuilder();
            final CharsTrieBuilder builder2 = new CharsTrieBuilder();
            int revCount = 0;
            int fwdCount = 0;
            final int subCount = this.filterSet.size();
            final CharSequence[] ustrs = new CharSequence[subCount];
            final int[] partials = new int[subCount];
            CharsTrie backwardsTrie = null;
            CharsTrie forwardsPartialTrie = null;
            int i = 0;
            for (final CharSequence s : this.filterSet) {
                ustrs[i] = s;
                partials[i] = 0;
                ++i;
            }
            for (i = 0; i < subCount; ++i) {
                final String thisStr = ustrs[i].toString();
                final int nn = thisStr.indexOf(46);
                if (nn > -1 && nn + 1 != thisStr.length()) {
                    int sameAs = -1;
                    for (int j = 0; j < subCount; ++j) {
                        if (j != i) {
                            if (thisStr.regionMatches(0, ustrs[j].toString(), 0, nn + 1)) {
                                if (partials[j] == 0) {
                                    partials[j] = 3;
                                }
                                else if ((partials[j] & 0x1) != 0x0) {
                                    sameAs = j;
                                }
                            }
                        }
                    }
                    if (sameAs == -1 && partials[i] == 0) {
                        final StringBuilder prefix = new StringBuilder(thisStr.substring(0, nn + 1));
                        prefix.reverse();
                        builder.add(prefix, 1);
                        ++revCount;
                        partials[i] = 3;
                    }
                }
            }
            for (i = 0; i < subCount; ++i) {
                final String thisStr = ustrs[i].toString();
                if (partials[i] == 0) {
                    final StringBuilder reversed = new StringBuilder(thisStr).reverse();
                    builder.add(reversed, 2);
                    ++revCount;
                }
                else {
                    builder2.add(thisStr, 2);
                    ++fwdCount;
                }
            }
            if (revCount > 0) {
                backwardsTrie = builder.build(StringTrieBuilder.Option.FAST);
            }
            if (fwdCount > 0) {
                forwardsPartialTrie = builder2.build(StringTrieBuilder.Option.FAST);
            }
            return new SimpleFilteredSentenceBreakIterator(adoptBreakIterator, forwardsPartialTrie, backwardsTrie);
        }
    }
}
