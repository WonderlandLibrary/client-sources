package com.ibm.icu.impl.coll;

import com.ibm.icu.util.*;

final class CollationFastLatinBuilder
{
    private static final int NUM_SPECIAL_GROUPS = 4;
    private static final long CONTRACTION_FLAG = 2147483648L;
    private long ce0;
    private long ce1;
    private long[][] charCEs;
    private UVector64 contractionCEs;
    private UVector64 uniqueCEs;
    private char[] miniCEs;
    long[] lastSpecialPrimaries;
    private long firstDigitPrimary;
    private long firstLatinPrimary;
    private long lastLatinPrimary;
    private long firstShortPrimary;
    private boolean shortPrimaryOverflow;
    private StringBuilder result;
    private int headerLength;
    
    private static final int compareInt64AsUnsigned(long a, long b) {
        a += Long.MIN_VALUE;
        b += Long.MIN_VALUE;
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        return 0;
    }
    
    private static final int binarySearch(final long[] list, int limit, final long ce) {
        if (limit == 0) {
            return -1;
        }
        int start = 0;
        while (true) {
            final int i = (int)((start + (long)limit) / 2L);
            final int cmp = compareInt64AsUnsigned(ce, list[i]);
            if (cmp == 0) {
                return i;
            }
            if (cmp < 0) {
                if (i == start) {
                    return ~start;
                }
                limit = i;
            }
            else {
                if (i == start) {
                    return ~(start + 1);
                }
                start = i;
            }
        }
    }
    
    CollationFastLatinBuilder() {
        this.charCEs = new long[448][2];
        this.lastSpecialPrimaries = new long[4];
        this.result = new StringBuilder();
        this.ce0 = 0L;
        this.ce1 = 0L;
        this.contractionCEs = new UVector64();
        this.uniqueCEs = new UVector64();
        this.miniCEs = null;
        this.firstDigitPrimary = 0L;
        this.firstLatinPrimary = 0L;
        this.lastLatinPrimary = 0L;
        this.firstShortPrimary = 0L;
        this.shortPrimaryOverflow = false;
        this.headerLength = 0;
    }
    
    boolean forData(final CollationData data) {
        if (this.result.length() != 0) {
            throw new IllegalStateException("attempt to reuse a CollationFastLatinBuilder");
        }
        if (!this.loadGroups(data)) {
            return false;
        }
        this.firstShortPrimary = this.firstDigitPrimary;
        this.getCEs(data);
        this.encodeUniqueCEs();
        if (this.shortPrimaryOverflow) {
            this.firstShortPrimary = this.firstLatinPrimary;
            this.resetCEs();
            this.getCEs(data);
            this.encodeUniqueCEs();
        }
        final boolean ok = !this.shortPrimaryOverflow;
        if (ok) {
            this.encodeCharCEs();
            this.encodeContractions();
        }
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        return ok;
    }
    
    char[] getHeader() {
        final char[] resultArray = new char[this.headerLength];
        this.result.getChars(0, this.headerLength, resultArray, 0);
        return resultArray;
    }
    
    char[] getTable() {
        final char[] resultArray = new char[this.result.length() - this.headerLength];
        this.result.getChars(this.headerLength, this.result.length(), resultArray, 0);
        return resultArray;
    }
    
    private boolean loadGroups(final CollationData data) {
        this.headerLength = 5;
        final int r0 = 0x200 | this.headerLength;
        this.result.append((char)r0);
        for (int i = 0; i < 4; ++i) {
            this.lastSpecialPrimaries[i] = data.getLastPrimaryForGroup(4096 + i);
            if (this.lastSpecialPrimaries[i] == 0L) {
                return false;
            }
            this.result.append(0);
        }
        this.firstDigitPrimary = data.getFirstPrimaryForGroup(4100);
        this.firstLatinPrimary = data.getFirstPrimaryForGroup(25);
        this.lastLatinPrimary = data.getLastPrimaryForGroup(25);
        return this.firstDigitPrimary != 0L && this.firstLatinPrimary != 0L;
    }
    
    private boolean inSameGroup(final long p, final long q) {
        if (p >= this.firstShortPrimary) {
            return q >= this.firstShortPrimary;
        }
        if (q >= this.firstShortPrimary) {
            return false;
        }
        final long lastVariablePrimary = this.lastSpecialPrimaries[3];
        if (p > lastVariablePrimary) {
            return q > lastVariablePrimary;
        }
        if (q > lastVariablePrimary) {
            return false;
        }
        assert p != 0L && q != 0L;
        int i = 0;
        while (true) {
            final long lastPrimary = this.lastSpecialPrimaries[i];
            if (p <= lastPrimary) {
                return q <= lastPrimary;
            }
            if (q <= lastPrimary) {
                return false;
            }
            ++i;
        }
    }
    
    private void resetCEs() {
        this.contractionCEs.removeAllElements();
        this.uniqueCEs.removeAllElements();
        this.shortPrimaryOverflow = false;
        this.result.setLength(this.headerLength);
    }
    
    private void getCEs(final CollationData data) {
        int i = 0;
        char c = '\0';
        while (true) {
            if (c == '\u0180') {
                c = '\u2000';
            }
            else if (c == '\u2040') {
                this.contractionCEs.addElement(511L);
                return;
            }
            int ce32 = data.getCE32(c);
            CollationData d;
            if (ce32 == 192) {
                d = data.base;
                ce32 = d.getCE32(c);
            }
            else {
                d = data;
            }
            if (this.getCEsFromCE32(d, c, ce32)) {
                this.charCEs[i][0] = this.ce0;
                this.charCEs[i][1] = this.ce1;
                this.addUniqueCE(this.ce0);
                this.addUniqueCE(this.ce1);
            }
            else {
                this.charCEs[i][0] = (this.ce0 = 4311744768L);
                this.charCEs[i][1] = (this.ce1 = 0L);
            }
            if (c == '\0' && !isContractionCharCE(this.ce0)) {
                assert this.contractionCEs.isEmpty();
                this.addContractionEntry(511, this.ce0, this.ce1);
                this.charCEs[0][0] = 6442450944L;
                this.charCEs[0][1] = 0L;
            }
            ++i;
            ++c;
        }
    }
    
    private boolean getCEsFromCE32(final CollationData data, final int c, int ce32) {
        ce32 = data.getFinalCE32(ce32);
        this.ce1 = 0L;
        if (Collation.isSimpleOrLongCE32(ce32)) {
            this.ce0 = Collation.ceFromCE32(ce32);
        }
        else {
            switch (Collation.tagFromCE32(ce32)) {
                case 4: {
                    this.ce0 = Collation.latinCE0FromCE32(ce32);
                    this.ce1 = Collation.latinCE1FromCE32(ce32);
                    break;
                }
                case 5: {
                    final int index = Collation.indexFromCE32(ce32);
                    final int length = Collation.lengthFromCE32(ce32);
                    if (length > 2) {
                        return false;
                    }
                    this.ce0 = Collation.ceFromCE32(data.ce32s[index]);
                    if (length == 2) {
                        this.ce1 = Collation.ceFromCE32(data.ce32s[index + 1]);
                        break;
                    }
                    break;
                }
                case 6: {
                    final int index = Collation.indexFromCE32(ce32);
                    final int length = Collation.lengthFromCE32(ce32);
                    if (length > 2) {
                        return false;
                    }
                    this.ce0 = data.ces[index];
                    if (length == 2) {
                        this.ce1 = data.ces[index + 1];
                        break;
                    }
                    break;
                }
                case 9: {
                    assert c >= 0;
                    return this.getCEsFromContractionCE32(data, ce32);
                }
                case 14: {
                    assert c >= 0;
                    this.ce0 = data.getCEFromOffsetCE32(c, ce32);
                    break;
                }
                default: {
                    return false;
                }
            }
        }
        if (this.ce0 == 0L) {
            return this.ce1 == 0L;
        }
        final long p0 = this.ce0 >>> 32;
        if (p0 == 0L) {
            return false;
        }
        if (p0 > this.lastLatinPrimary) {
            return false;
        }
        final int lower32_0 = (int)this.ce0;
        if (p0 < this.firstShortPrimary) {
            final int sc0 = lower32_0 & 0xFFFFC000;
            if (sc0 != 83886080) {
                return false;
            }
        }
        if ((lower32_0 & 0x3F3F) < 1280) {
            return false;
        }
        if (this.ce1 != 0L) {
            final long p2 = this.ce1 >>> 32;
            Label_0434: {
                if (p2 == 0L) {
                    if (p0 >= this.firstShortPrimary) {
                        break Label_0434;
                    }
                }
                else if (this.inSameGroup(p0, p2)) {
                    break Label_0434;
                }
                return false;
            }
            final int lower32_2 = (int)this.ce1;
            if (lower32_2 >>> 16 == 0) {
                return false;
            }
            if (p2 != 0L && p2 < this.firstShortPrimary) {
                final int sc2 = lower32_2 & 0xFFFFC000;
                if (sc2 != 83886080) {
                    return false;
                }
            }
            if ((lower32_0 & 0x3F3F) < 1280) {
                return false;
            }
        }
        return ((this.ce0 | this.ce1) & 0xC0L) == 0x0L;
    }
    
    private boolean getCEsFromContractionCE32(final CollationData data, int ce32) {
        final int trieIndex = Collation.indexFromCE32(ce32);
        ce32 = data.getCE32FromContexts(trieIndex);
        assert !Collation.isContractionCE32(ce32);
        final int contractionIndex = this.contractionCEs.size();
        if (this.getCEsFromCE32(data, -1, ce32)) {
            this.addContractionEntry(511, this.ce0, this.ce1);
        }
        else {
            this.addContractionEntry(511, 4311744768L, 0L);
        }
        int prevX = -1;
        boolean addContraction = false;
        final CharsTrie.Iterator suffixes = CharsTrie.iterator(data.contexts, trieIndex + 2, 0);
        while (suffixes.hasNext()) {
            final CharsTrie.Entry entry = suffixes.next();
            final CharSequence suffix = entry.chars;
            final int x = CollationFastLatin.getCharIndex(suffix.charAt(0));
            if (x < 0) {
                continue;
            }
            if (x == prevX) {
                if (!addContraction) {
                    continue;
                }
                this.addContractionEntry(x, 4311744768L, 0L);
                addContraction = false;
            }
            else {
                if (addContraction) {
                    this.addContractionEntry(prevX, this.ce0, this.ce1);
                }
                ce32 = entry.value;
                if (suffix.length() == 1 && this.getCEsFromCE32(data, -1, ce32)) {
                    addContraction = true;
                }
                else {
                    this.addContractionEntry(x, 4311744768L, 0L);
                    addContraction = false;
                }
                prevX = x;
            }
        }
        if (addContraction) {
            this.addContractionEntry(prevX, this.ce0, this.ce1);
        }
        this.ce0 = (0x180000000L | (long)contractionIndex);
        this.ce1 = 0L;
        return true;
    }
    
    private void addContractionEntry(final int x, final long cce0, final long cce1) {
        this.contractionCEs.addElement(x);
        this.contractionCEs.addElement(cce0);
        this.contractionCEs.addElement(cce1);
        this.addUniqueCE(cce0);
        this.addUniqueCE(cce1);
    }
    
    private void addUniqueCE(long ce) {
        if (ce == 0L || ce >>> 32 == 1L) {
            return;
        }
        ce &= 0xFFFFFFFFFFFF3FFFL;
        final int i = binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), ce);
        if (i < 0) {
            this.uniqueCEs.insertElementAt(ce, ~i);
        }
    }
    
    private int getMiniCE(long ce) {
        ce &= 0xFFFFFFFFFFFF3FFFL;
        final int index = binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), ce);
        assert index >= 0;
        return this.miniCEs[index];
    }
    
    private void encodeUniqueCEs() {
        this.miniCEs = new char[this.uniqueCEs.size()];
        int group = 0;
        long lastGroupPrimary = this.lastSpecialPrimaries[group];
        assert (int)this.uniqueCEs.elementAti(0) >>> 16 != 0;
        long prevPrimary = 0L;
        int prevSecondary = 0;
        int pri = 0;
        int sec = 0;
        int ter = 0;
        for (int i = 0; i < this.uniqueCEs.size(); ++i) {
            final long ce = this.uniqueCEs.elementAti(i);
            final long p = ce >>> 32;
            if (p != prevPrimary) {
                while (p > lastGroupPrimary) {
                    assert pri <= 4088;
                    this.result.setCharAt(1 + group, (char)pri);
                    if (++group >= 4) {
                        lastGroupPrimary = 4294967295L;
                        break;
                    }
                    lastGroupPrimary = this.lastSpecialPrimaries[group];
                }
                if (p < this.firstShortPrimary) {
                    if (pri == 0) {
                        pri = 3072;
                    }
                    else {
                        if (pri >= 4088) {
                            this.miniCEs[i] = '\u0001';
                            continue;
                        }
                        pri += 8;
                    }
                }
                else if (pri < 4096) {
                    pri = 4096;
                }
                else {
                    if (pri >= 63488) {
                        this.shortPrimaryOverflow = true;
                        this.miniCEs[i] = '\u0001';
                        continue;
                    }
                    pri += 1024;
                }
                prevPrimary = p;
                prevSecondary = 1280;
                sec = 160;
                ter = 0;
            }
            final int lower32 = (int)ce;
            final int s = lower32 >>> 16;
            if (s != prevSecondary) {
                if (pri == 0) {
                    if (sec == 0) {
                        sec = 384;
                    }
                    else {
                        if (sec >= 992) {
                            this.miniCEs[i] = '\u0001';
                            continue;
                        }
                        sec += 32;
                    }
                    prevSecondary = s;
                    ter = 0;
                }
                else if (s < 1280) {
                    if (sec == 160) {
                        sec = 0;
                    }
                    else {
                        if (sec >= 128) {
                            this.miniCEs[i] = '\u0001';
                            continue;
                        }
                        sec += 32;
                    }
                }
                else if (s == 1280) {
                    sec = 160;
                }
                else if (sec < 192) {
                    sec = 192;
                }
                else {
                    if (sec >= 352) {
                        this.miniCEs[i] = '\u0001';
                        continue;
                    }
                    sec += 32;
                }
                prevSecondary = s;
                ter = 0;
            }
            assert (lower32 & 0xC000) == 0x0;
            final int t = lower32 & 0x3F3F;
            if (t > 1280) {
                if (ter >= 7) {
                    this.miniCEs[i] = '\u0001';
                    continue;
                }
                ++ter;
            }
            if (3072 <= pri && pri <= 4088) {
                assert sec == 160;
                this.miniCEs[i] = (char)(pri | ter);
            }
            else {
                this.miniCEs[i] = (char)(pri | sec | ter);
            }
        }
    }
    
    private void encodeCharCEs() {
        final int miniCEsStart = this.result.length();
        for (int i = 0; i < 448; ++i) {
            this.result.append(0);
        }
        final int indexBase = this.result.length();
        for (int j = 0; j < 448; ++j) {
            final long ce = this.charCEs[j][0];
            if (!isContractionCharCE(ce)) {
                int miniCE = this.encodeTwoCEs(ce, this.charCEs[j][1]);
                if (miniCE >>> 16 > 0) {
                    final int expansionIndex = this.result.length() - indexBase;
                    if (expansionIndex > 1023) {
                        miniCE = 1;
                    }
                    else {
                        this.result.append((char)(miniCE >> 16)).append((char)miniCE);
                        miniCE = (0x800 | expansionIndex);
                    }
                }
                this.result.setCharAt(miniCEsStart + j, (char)miniCE);
            }
        }
    }
    
    private void encodeContractions() {
        final int indexBase = this.headerLength + 448;
        final int firstContractionIndex = this.result.length();
        for (int i = 0; i < 448; ++i) {
            final long ce = this.charCEs[i][0];
            if (isContractionCharCE(ce)) {
                final int contractionIndex = this.result.length() - indexBase;
                if (contractionIndex > 1023) {
                    this.result.setCharAt(this.headerLength + i, '\u0001');
                }
                else {
                    boolean firstTriple = true;
                    int index = (int)ce & Integer.MAX_VALUE;
                    while (true) {
                        final long x = this.contractionCEs.elementAti(index);
                        if (x == 511L && !firstTriple) {
                            break;
                        }
                        final long cce0 = this.contractionCEs.elementAti(index + 1);
                        final long cce2 = this.contractionCEs.elementAti(index + 2);
                        final int miniCE = this.encodeTwoCEs(cce0, cce2);
                        if (miniCE == 1) {
                            this.result.append((char)(x | 0x200L));
                        }
                        else if (miniCE >>> 16 == 0) {
                            this.result.append((char)(x | 0x400L));
                            this.result.append((char)miniCE);
                        }
                        else {
                            this.result.append((char)(x | 0x600L));
                            this.result.append((char)(miniCE >> 16)).append((char)miniCE);
                        }
                        firstTriple = false;
                        index += 3;
                    }
                    this.result.setCharAt(this.headerLength + i, (char)(0x400 | contractionIndex));
                }
            }
        }
        if (this.result.length() > firstContractionIndex) {
            this.result.append('\u01ff');
        }
    }
    
    private int encodeTwoCEs(final long first, final long second) {
        if (first == 0L) {
            return 0;
        }
        if (first == 4311744768L) {
            return 1;
        }
        assert first >>> 32 != 1L;
        int miniCE = this.getMiniCE(first);
        if (miniCE == 1) {
            return miniCE;
        }
        if (miniCE >= 4096) {
            int c = ((int)first & 0xC000) >> 11;
            c += 8;
            miniCE |= c;
        }
        if (second == 0L) {
            return miniCE;
        }
        int miniCE2 = this.getMiniCE(second);
        if (miniCE2 == 1) {
            return miniCE2;
        }
        int case1 = (int)second & 0xC000;
        if (miniCE >= 4096 && (miniCE & 0x3E0) == 0xA0) {
            final int sec1 = miniCE2 & 0x3E0;
            final int ter1 = miniCE2 & 0x7;
            if (sec1 >= 384 && case1 == 0 && ter1 == 0) {
                return (miniCE & 0xFFFFFC1F) | sec1;
            }
        }
        if (miniCE2 <= 992 || 4096 <= miniCE2) {
            case1 = (case1 >> 11) + 8;
            miniCE2 |= case1;
        }
        return miniCE << 16 | miniCE2;
    }
    
    private static boolean isContractionCharCE(final long ce) {
        return ce >>> 32 == 1L && ce != 4311744768L;
    }
}
