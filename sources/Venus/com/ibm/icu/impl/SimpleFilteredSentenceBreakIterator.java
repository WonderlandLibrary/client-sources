/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.FilteredBreakIteratorBuilder;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.StringTrieBuilder;
import com.ibm.icu.util.ULocale;
import java.text.CharacterIterator;
import java.util.HashSet;
import java.util.Locale;

public class SimpleFilteredSentenceBreakIterator
extends BreakIterator {
    private BreakIterator delegate;
    private UCharacterIterator text;
    private CharsTrie backwardsTrie;
    private CharsTrie forwardsPartialTrie;

    public SimpleFilteredSentenceBreakIterator(BreakIterator breakIterator, CharsTrie charsTrie, CharsTrie charsTrie2) {
        this.delegate = breakIterator;
        this.forwardsPartialTrie = charsTrie;
        this.backwardsTrie = charsTrie2;
    }

    private final void resetState() {
        this.text = UCharacterIterator.getInstance((CharacterIterator)this.delegate.getText().clone());
    }

    private final boolean breakExceptionAt(int n) {
        int n2 = -1;
        int n3 = -1;
        this.text.setIndex(n);
        this.backwardsTrie.reset();
        int n4 = this.text.previousCodePoint();
        if (n4 != 32) {
            n4 = this.text.nextCodePoint();
        }
        BytesTrie.Result result = BytesTrie.Result.INTERMEDIATE_VALUE;
        while ((n4 = this.text.previousCodePoint()) != -1 && (result = this.backwardsTrie.nextForCodePoint(n4)).hasNext()) {
            if (!result.hasValue()) continue;
            n2 = this.text.getIndex();
            n3 = this.backwardsTrie.getValue();
        }
        if (result.matches()) {
            n3 = this.backwardsTrie.getValue();
            n2 = this.text.getIndex();
        }
        if (n2 >= 0) {
            if (n3 == 2) {
                return false;
            }
            if (n3 == 1 && this.forwardsPartialTrie != null) {
                this.forwardsPartialTrie.reset();
                BytesTrie.Result result2 = BytesTrie.Result.INTERMEDIATE_VALUE;
                this.text.setIndex(n2);
                while ((n4 = this.text.nextCodePoint()) != -1 && (result2 = this.forwardsPartialTrie.nextForCodePoint(n4)).hasNext()) {
                }
                if (result2.matches()) {
                    return false;
                }
            }
        }
        return true;
    }

    private final int internalNext(int n) {
        if (n == -1 || this.backwardsTrie == null) {
            return n;
        }
        this.resetState();
        int n2 = this.text.getLength();
        while (n != -1 && n != n2) {
            if (this.breakExceptionAt(n)) {
                n = this.delegate.next();
                continue;
            }
            return n;
        }
        return n;
    }

    private final int internalPrev(int n) {
        if (n == 0 || n == -1 || this.backwardsTrie == null) {
            return n;
        }
        this.resetState();
        while (n != -1 && n != 0) {
            if (this.breakExceptionAt(n)) {
                n = this.delegate.previous();
                continue;
            }
            return n;
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        SimpleFilteredSentenceBreakIterator simpleFilteredSentenceBreakIterator = (SimpleFilteredSentenceBreakIterator)object;
        return this.delegate.equals(simpleFilteredSentenceBreakIterator.delegate) && this.text.equals(simpleFilteredSentenceBreakIterator.text) && this.backwardsTrie.equals(simpleFilteredSentenceBreakIterator.backwardsTrie) && this.forwardsPartialTrie.equals(simpleFilteredSentenceBreakIterator.forwardsPartialTrie);
    }

    public int hashCode() {
        return this.forwardsPartialTrie.hashCode() * 39 + this.backwardsTrie.hashCode() * 11 + this.delegate.hashCode();
    }

    @Override
    public Object clone() {
        SimpleFilteredSentenceBreakIterator simpleFilteredSentenceBreakIterator = (SimpleFilteredSentenceBreakIterator)super.clone();
        return simpleFilteredSentenceBreakIterator;
    }

    @Override
    public int first() {
        return this.delegate.first();
    }

    @Override
    public int preceding(int n) {
        return this.internalPrev(this.delegate.preceding(n));
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
    public boolean isBoundary(int n) {
        if (!this.delegate.isBoundary(n)) {
            return true;
        }
        if (this.backwardsTrie == null) {
            return false;
        }
        this.resetState();
        return !this.breakExceptionAt(n);
    }

    @Override
    public int next() {
        return this.internalNext(this.delegate.next());
    }

    @Override
    public int next(int n) {
        return this.internalNext(this.delegate.next(n));
    }

    @Override
    public int following(int n) {
        return this.internalNext(this.delegate.following(n));
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
    public void setText(CharacterIterator characterIterator) {
        this.delegate.setText(characterIterator);
    }

    public static class Builder
    extends FilteredBreakIteratorBuilder {
        private HashSet<CharSequence> filterSet = new HashSet();
        static final int PARTIAL = 1;
        static final int MATCH = 2;
        static final int SuppressInReverse = 1;
        static final int AddToForward = 2;

        public Builder(Locale locale) {
            this(ULocale.forLocale(locale));
        }

        public Builder(ULocale uLocale) {
            ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/brkitr", uLocale, ICUResourceBundle.OpenType.LOCALE_ROOT);
            ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.findWithFallback("exceptions/SentenceBreak");
            if (iCUResourceBundle2 != null) {
                int n = iCUResourceBundle2.getSize();
                for (int i = 0; i < n; ++i) {
                    ICUResourceBundle iCUResourceBundle3 = (ICUResourceBundle)iCUResourceBundle2.get(i);
                    String string = iCUResourceBundle3.getString();
                    this.filterSet.add(string);
                }
            }
        }

        public Builder() {
        }

        @Override
        public boolean suppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.add(charSequence);
        }

        @Override
        public boolean unsuppressBreakAfter(CharSequence charSequence) {
            return this.filterSet.remove(charSequence);
        }

        @Override
        public BreakIterator wrapIteratorWithFilter(BreakIterator breakIterator) {
            if (this.filterSet.isEmpty()) {
                return breakIterator;
            }
            CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
            CharsTrieBuilder charsTrieBuilder2 = new CharsTrieBuilder();
            int n = 0;
            int n2 = 0;
            int n3 = this.filterSet.size();
            CharSequence[] charSequenceArray = new CharSequence[n3];
            int[] nArray = new int[n3];
            CharsTrie charsTrie = null;
            CharsTrie charsTrie2 = null;
            int n4 = 0;
            Object object = this.filterSet.iterator();
            while (object.hasNext()) {
                CharSequence charSequence;
                charSequenceArray[n4] = charSequence = object.next();
                nArray[n4] = 0;
                ++n4;
            }
            for (n4 = 0; n4 < n3; ++n4) {
                object = charSequenceArray[n4].toString();
                int n5 = ((String)object).indexOf(46);
                if (n5 <= -1 || n5 + 1 == ((String)object).length()) continue;
                int n6 = -1;
                for (int i = 0; i < n3; ++i) {
                    if (i == n4 || !((String)object).regionMatches(0, charSequenceArray[i].toString(), 0, n5 + 1)) continue;
                    if (nArray[i] == 0) {
                        nArray[i] = 3;
                        continue;
                    }
                    if ((nArray[i] & 1) == 0) continue;
                    n6 = i;
                }
                if (n6 != -1 || nArray[n4] != 0) continue;
                StringBuilder stringBuilder = new StringBuilder(((String)object).substring(0, n5 + 1));
                stringBuilder.reverse();
                charsTrieBuilder.add(stringBuilder, 1);
                ++n;
                nArray[n4] = 3;
            }
            for (n4 = 0; n4 < n3; ++n4) {
                object = charSequenceArray[n4].toString();
                if (nArray[n4] == 0) {
                    StringBuilder stringBuilder = new StringBuilder((String)object).reverse();
                    charsTrieBuilder.add(stringBuilder, 2);
                    ++n;
                    continue;
                }
                charsTrieBuilder2.add((CharSequence)object, 2);
                ++n2;
            }
            if (n > 0) {
                charsTrie = charsTrieBuilder.build(StringTrieBuilder.Option.FAST);
            }
            if (n2 > 0) {
                charsTrie2 = charsTrieBuilder2.build(StringTrieBuilder.Option.FAST);
            }
            return new SimpleFilteredSentenceBreakIterator(breakIterator, charsTrie2, charsTrie);
        }
    }
}

