package com.ibm.icu.impl.coll;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.util.*;

public final class ContractionsAndExpansions
{
    private CollationData data;
    private UnicodeSet contractions;
    private UnicodeSet expansions;
    private CESink sink;
    private boolean addPrefixes;
    private int checkTailored;
    private UnicodeSet tailored;
    private UnicodeSet ranges;
    private StringBuilder unreversedPrefix;
    private String suffix;
    private long[] ces;
    
    public ContractionsAndExpansions(final UnicodeSet con, final UnicodeSet exp, final CESink s, final boolean prefixes) {
        this.checkTailored = 0;
        this.tailored = new UnicodeSet();
        this.unreversedPrefix = new StringBuilder();
        this.ces = new long[31];
        this.contractions = con;
        this.expansions = exp;
        this.sink = s;
        this.addPrefixes = prefixes;
    }
    
    public void forData(final CollationData d) {
        if (d.base != null) {
            this.checkTailored = -1;
        }
        this.data = d;
        Iterator<Trie2.Range> trieIterator = this.data.trie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
        if (d.base == null) {
            return;
        }
        this.tailored.freeze();
        this.checkTailored = 1;
        this.data = d.base;
        trieIterator = this.data.trie.iterator();
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }
    
    private void enumCnERange(final int start, final int end, final int ce32, final ContractionsAndExpansions cne) {
        if (cne.checkTailored != 0) {
            if (cne.checkTailored < 0) {
                if (ce32 == 192) {
                    return;
                }
                cne.tailored.add(start, end);
            }
            else if (start == end) {
                if (cne.tailored.contains(start)) {
                    return;
                }
            }
            else if (cne.tailored.containsSome(start, end)) {
                if (cne.ranges == null) {
                    cne.ranges = new UnicodeSet();
                }
                cne.ranges.set(start, end).removeAll(cne.tailored);
                for (int count = cne.ranges.getRangeCount(), i = 0; i < count; ++i) {
                    cne.handleCE32(cne.ranges.getRangeStart(i), cne.ranges.getRangeEnd(i), ce32);
                }
            }
        }
        cne.handleCE32(start, end, ce32);
    }
    
    public void forCodePoint(CollationData d, final int c) {
        int ce32 = d.getCE32(c);
        if (ce32 == 192) {
            d = d.base;
            ce32 = d.getCE32(c);
        }
        this.data = d;
        this.handleCE32(c, c, ce32);
    }
    
    private void handleCE32(final int start, final int end, int ce32) {
        while ((ce32 & 0xFF) >= 192) {
            switch (Collation.tagFromCE32(ce32)) {
                case 0: {
                    return;
                }
                case 3:
                case 7:
                case 13: {
                    throw new AssertionError((Object)String.format("Unexpected CE32 tag type %d for ce32=0x%08x", Collation.tagFromCE32(ce32), ce32));
                }
                case 1: {
                    if (this.sink != null) {
                        this.sink.handleCE(Collation.ceFromLongPrimaryCE32(ce32));
                    }
                    return;
                }
                case 2: {
                    if (this.sink != null) {
                        this.sink.handleCE(Collation.ceFromLongSecondaryCE32(ce32));
                    }
                    return;
                }
                case 4: {
                    if (this.sink != null) {
                        this.ces[0] = Collation.latinCE0FromCE32(ce32);
                        this.ces[1] = Collation.latinCE1FromCE32(ce32);
                        this.sink.handleExpansion(this.ces, 0, 2);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(start, end);
                    }
                    return;
                }
                case 5: {
                    if (this.sink != null) {
                        final int idx = Collation.indexFromCE32(ce32);
                        final int length = Collation.lengthFromCE32(ce32);
                        for (int i = 0; i < length; ++i) {
                            this.ces[i] = Collation.ceFromCE32(this.data.ce32s[idx + i]);
                        }
                        this.sink.handleExpansion(this.ces, 0, length);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(start, end);
                    }
                    return;
                }
                case 6: {
                    if (this.sink != null) {
                        final int idx = Collation.indexFromCE32(ce32);
                        final int length = Collation.lengthFromCE32(ce32);
                        this.sink.handleExpansion(this.data.ces, idx, length);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(start, end);
                    }
                    return;
                }
                case 8: {
                    this.handlePrefixes(start, end, ce32);
                    return;
                }
                case 9: {
                    this.handleContractions(start, end, ce32);
                    return;
                }
                case 10: {
                    ce32 = this.data.ce32s[Collation.indexFromCE32(ce32)];
                    continue;
                }
                case 11: {
                    assert start == 0 && end == 0;
                    ce32 = this.data.ce32s[0];
                    continue;
                }
                case 12: {
                    if (this.sink != null) {
                        final UTF16CollationIterator iter = new UTF16CollationIterator(this.data);
                        final StringBuilder hangul = new StringBuilder(1);
                        for (int c = start; c <= end; ++c) {
                            hangul.setLength(0);
                            hangul.appendCodePoint(c);
                            iter.setText(false, hangul, 0);
                            final int length2 = iter.fetchCEs();
                            assert length2 >= 2 && iter.getCE(length2 - 1) == 4311744768L;
                            this.sink.handleExpansion(iter.getCEs(), 0, length2 - 1);
                        }
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(start, end);
                    }
                    return;
                }
                case 14: {
                    return;
                }
                case 15: {
                    return;
                }
            }
        }
        if (this.sink != null) {
            this.sink.handleCE(Collation.ceFromSimpleCE32(ce32));
        }
    }
    
    private void handlePrefixes(final int start, final int end, int ce32) {
        final int index = Collation.indexFromCE32(ce32);
        ce32 = this.data.getCE32FromContexts(index);
        this.handleCE32(start, end, ce32);
        if (!this.addPrefixes) {
            return;
        }
        for (final CharsTrie.Entry e : new CharsTrie(this.data.contexts, index + 2)) {
            this.setPrefix(e.chars);
            this.addStrings(start, end, this.contractions);
            this.addStrings(start, end, this.expansions);
            this.handleCE32(start, end, e.value);
        }
        this.resetPrefix();
    }
    
    void handleContractions(final int start, final int end, int ce32) {
        final int index = Collation.indexFromCE32(ce32);
        if ((ce32 & 0x100) != 0x0) {
            assert this.unreversedPrefix.length() != 0;
        }
        else {
            ce32 = this.data.getCE32FromContexts(index);
            assert !Collation.isContractionCE32(ce32);
            this.handleCE32(start, end, ce32);
        }
        for (final CharsTrie.Entry e : new CharsTrie(this.data.contexts, index + 2)) {
            this.suffix = e.chars.toString();
            this.addStrings(start, end, this.contractions);
            if (this.unreversedPrefix.length() != 0) {
                this.addStrings(start, end, this.expansions);
            }
            this.handleCE32(start, end, e.value);
        }
        this.suffix = null;
    }
    
    void addExpansions(final int start, final int end) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            if (this.expansions != null) {
                this.expansions.add(start, end);
            }
        }
        else {
            this.addStrings(start, end, this.expansions);
        }
    }
    
    void addStrings(int start, final int end, final UnicodeSet set) {
        if (set == null) {
            return;
        }
        final StringBuilder s = new StringBuilder(this.unreversedPrefix);
        do {
            s.appendCodePoint(start);
            if (this.suffix != null) {
                s.append(this.suffix);
            }
            set.add(s);
            s.setLength(this.unreversedPrefix.length());
        } while (++start <= end);
    }
    
    private void setPrefix(final CharSequence pfx) {
        this.unreversedPrefix.setLength(0);
        this.unreversedPrefix.append(pfx).reverse();
    }
    
    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }
    
    public interface CESink
    {
        void handleCE(final long p0);
        
        void handleExpansion(final long[] p0, final int p1, final int p2);
    }
}
