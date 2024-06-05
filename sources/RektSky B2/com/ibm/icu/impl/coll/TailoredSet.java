package com.ibm.icu.impl.coll;

import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public final class TailoredSet
{
    private CollationData data;
    private CollationData baseData;
    private UnicodeSet tailored;
    private StringBuilder unreversedPrefix;
    private String suffix;
    
    public TailoredSet(final UnicodeSet t) {
        this.unreversedPrefix = new StringBuilder();
        this.tailored = t;
    }
    
    public void forData(final CollationData d) {
        this.data = d;
        this.baseData = d.base;
        assert this.baseData != null;
        final Iterator<Trie2.Range> trieIterator = this.data.trie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            this.enumTailoredRange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }
    
    private void enumTailoredRange(final int start, final int end, final int ce32, final TailoredSet ts) {
        if (ce32 == 192) {
            return;
        }
        ts.handleCE32(start, end, ce32);
    }
    
    private void handleCE32(int start, final int end, int ce32) {
        assert ce32 != 192;
        if (Collation.isSpecialCE32(ce32)) {
            ce32 = this.data.getIndirectCE32(ce32);
            if (ce32 == 192) {
                return;
            }
        }
        do {
            final int baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32(start));
            if (Collation.isSelfContainedCE32(ce32) && Collation.isSelfContainedCE32(baseCE32)) {
                if (ce32 == baseCE32) {
                    continue;
                }
                this.tailored.add(start);
            }
            else {
                this.compare(start, ce32, baseCE32);
            }
        } while (++start <= end);
    }
    
    private void compare(final int c, int ce32, int baseCE32) {
        if (Collation.isPrefixCE32(ce32)) {
            final int dataIndex = Collation.indexFromCE32(ce32);
            ce32 = this.data.getFinalCE32(this.data.getCE32FromContexts(dataIndex));
            if (Collation.isPrefixCE32(baseCE32)) {
                final int baseIndex = Collation.indexFromCE32(baseCE32);
                baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
                this.comparePrefixes(c, this.data.contexts, dataIndex + 2, this.baseData.contexts, baseIndex + 2);
            }
            else {
                this.addPrefixes(this.data, c, this.data.contexts, dataIndex + 2);
            }
        }
        else if (Collation.isPrefixCE32(baseCE32)) {
            final int baseIndex2 = Collation.indexFromCE32(baseCE32);
            baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex2));
            this.addPrefixes(this.baseData, c, this.baseData.contexts, baseIndex2 + 2);
        }
        if (Collation.isContractionCE32(ce32)) {
            final int dataIndex = Collation.indexFromCE32(ce32);
            if ((ce32 & 0x100) != 0x0) {
                ce32 = 1;
            }
            else {
                ce32 = this.data.getFinalCE32(this.data.getCE32FromContexts(dataIndex));
            }
            if (Collation.isContractionCE32(baseCE32)) {
                final int baseIndex = Collation.indexFromCE32(baseCE32);
                if ((baseCE32 & 0x100) != 0x0) {
                    baseCE32 = 1;
                }
                else {
                    baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex));
                }
                this.compareContractions(c, this.data.contexts, dataIndex + 2, this.baseData.contexts, baseIndex + 2);
            }
            else {
                this.addContractions(c, this.data.contexts, dataIndex + 2);
            }
        }
        else if (Collation.isContractionCE32(baseCE32)) {
            final int baseIndex2 = Collation.indexFromCE32(baseCE32);
            baseCE32 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(baseIndex2));
            this.addContractions(c, this.baseData.contexts, baseIndex2 + 2);
        }
        int tag;
        if (Collation.isSpecialCE32(ce32)) {
            tag = Collation.tagFromCE32(ce32);
            assert tag != 8;
            assert tag != 9;
            assert tag != 14;
        }
        else {
            tag = -1;
        }
        int baseTag;
        if (Collation.isSpecialCE32(baseCE32)) {
            baseTag = Collation.tagFromCE32(baseCE32);
            assert baseTag != 8;
            assert baseTag != 9;
        }
        else {
            baseTag = -1;
        }
        if (baseTag == 14) {
            if (!Collation.isLongPrimaryCE32(ce32)) {
                this.add(c);
                return;
            }
            final long dataCE = this.baseData.ces[Collation.indexFromCE32(baseCE32)];
            final long p = Collation.getThreeBytePrimaryForOffsetData(c, dataCE);
            if (Collation.primaryFromLongPrimaryCE32(ce32) != p) {
                this.add(c);
                return;
            }
        }
        if (tag != baseTag) {
            this.add(c);
            return;
        }
        if (tag == 5) {
            final int length = Collation.lengthFromCE32(ce32);
            final int baseLength = Collation.lengthFromCE32(baseCE32);
            if (length != baseLength) {
                this.add(c);
                return;
            }
            final int idx0 = Collation.indexFromCE32(ce32);
            final int idx2 = Collation.indexFromCE32(baseCE32);
            for (int i = 0; i < length; ++i) {
                if (this.data.ce32s[idx0 + i] != this.baseData.ce32s[idx2 + i]) {
                    this.add(c);
                    break;
                }
            }
        }
        else if (tag == 6) {
            final int length = Collation.lengthFromCE32(ce32);
            final int baseLength = Collation.lengthFromCE32(baseCE32);
            if (length != baseLength) {
                this.add(c);
                return;
            }
            final int idx0 = Collation.indexFromCE32(ce32);
            final int idx2 = Collation.indexFromCE32(baseCE32);
            for (int i = 0; i < length; ++i) {
                if (this.data.ces[idx0 + i] != this.baseData.ces[idx2 + i]) {
                    this.add(c);
                    break;
                }
            }
        }
        else if (tag == 12) {
            final StringBuilder jamos = new StringBuilder();
            final int length2 = Normalizer2Impl.Hangul.decompose(c, jamos);
            if (this.tailored.contains(jamos.charAt(0)) || this.tailored.contains(jamos.charAt(1)) || (length2 == 3 && this.tailored.contains(jamos.charAt(2)))) {
                this.add(c);
            }
        }
        else if (ce32 != baseCE32) {
            this.add(c);
        }
    }
    
    private void comparePrefixes(final int c, final CharSequence p, final int pidx, final CharSequence q, final int qidx) {
        final CharsTrie.Iterator prefixes = new CharsTrie(p, pidx).iterator();
        final CharsTrie.Iterator basePrefixes = new CharsTrie(q, qidx).iterator();
        String tp = null;
        String bp = null;
        final String none = "\uffff";
        CharsTrie.Entry te = null;
        CharsTrie.Entry be = null;
        while (true) {
            if (tp == null) {
                if (prefixes.hasNext()) {
                    te = prefixes.next();
                    tp = te.chars.toString();
                }
                else {
                    te = null;
                    tp = none;
                }
            }
            if (bp == null) {
                if (basePrefixes.hasNext()) {
                    be = basePrefixes.next();
                    bp = be.chars.toString();
                }
                else {
                    be = null;
                    bp = none;
                }
            }
            if (Utility.sameObjects(tp, none) && Utility.sameObjects(bp, none)) {
                return;
            }
            final int cmp = tp.compareTo(bp);
            if (cmp < 0) {
                assert te != null;
                this.addPrefix(this.data, tp, c, te.value);
                te = null;
                tp = null;
            }
            else if (cmp > 0) {
                assert be != null;
                this.addPrefix(this.baseData, bp, c, be.value);
                be = null;
                bp = null;
            }
            else {
                this.setPrefix(tp);
                assert te != null && be != null;
                this.compare(c, te.value, be.value);
                this.resetPrefix();
                be = (te = null);
                bp = (tp = null);
            }
        }
    }
    
    private void compareContractions(final int c, final CharSequence p, final int pidx, final CharSequence q, final int qidx) {
        final CharsTrie.Iterator suffixes = new CharsTrie(p, pidx).iterator();
        final CharsTrie.Iterator baseSuffixes = new CharsTrie(q, qidx).iterator();
        String ts = null;
        String bs = null;
        final String none = "\uffff\uffff";
        CharsTrie.Entry te = null;
        CharsTrie.Entry be = null;
        while (true) {
            if (ts == null) {
                if (suffixes.hasNext()) {
                    te = suffixes.next();
                    ts = te.chars.toString();
                }
                else {
                    te = null;
                    ts = none;
                }
            }
            if (bs == null) {
                if (baseSuffixes.hasNext()) {
                    be = baseSuffixes.next();
                    bs = be.chars.toString();
                }
                else {
                    be = null;
                    bs = none;
                }
            }
            if (Utility.sameObjects(ts, none) && Utility.sameObjects(bs, none)) {
                break;
            }
            final int cmp = ts.compareTo(bs);
            if (cmp < 0) {
                this.addSuffix(c, ts);
                te = null;
                ts = null;
            }
            else if (cmp > 0) {
                this.addSuffix(c, bs);
                be = null;
                bs = null;
            }
            else {
                this.suffix = ts;
                this.compare(c, te.value, be.value);
                this.suffix = null;
                be = (te = null);
                bs = (ts = null);
            }
        }
    }
    
    private void addPrefixes(final CollationData d, final int c, final CharSequence p, final int pidx) {
        for (final CharsTrie.Entry e : new CharsTrie(p, pidx)) {
            this.addPrefix(d, e.chars, c, e.value);
        }
    }
    
    private void addPrefix(final CollationData d, final CharSequence pfx, final int c, int ce32) {
        this.setPrefix(pfx);
        ce32 = d.getFinalCE32(ce32);
        if (Collation.isContractionCE32(ce32)) {
            final int idx = Collation.indexFromCE32(ce32);
            this.addContractions(c, d.contexts, idx + 2);
        }
        this.tailored.add(new StringBuilder(this.unreversedPrefix.appendCodePoint(c)));
        this.resetPrefix();
    }
    
    private void addContractions(final int c, final CharSequence p, final int pidx) {
        for (final CharsTrie.Entry e : new CharsTrie(p, pidx)) {
            this.addSuffix(c, e.chars);
        }
    }
    
    private void addSuffix(final int c, final CharSequence sfx) {
        this.tailored.add(new StringBuilder(this.unreversedPrefix).appendCodePoint(c).append(sfx));
    }
    
    private void add(final int c) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            this.tailored.add(c);
        }
        else {
            final StringBuilder s = new StringBuilder(this.unreversedPrefix);
            s.appendCodePoint(c);
            if (this.suffix != null) {
                s.append(this.suffix);
            }
            this.tailored.add(s);
        }
    }
    
    private void setPrefix(final CharSequence pfx) {
        this.unreversedPrefix.setLength(0);
        this.unreversedPrefix.append(pfx).reverse();
    }
    
    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }
}
