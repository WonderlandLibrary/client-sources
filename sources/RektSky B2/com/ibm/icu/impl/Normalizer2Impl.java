package com.ibm.icu.impl;

import java.nio.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.util.*;
import com.ibm.icu.text.*;

public final class Normalizer2Impl
{
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final int DATA_FORMAT = 1316121906;
    private static final Trie2.ValueMapper segmentStarterMapper;
    public static final int MIN_YES_YES_WITH_CC = 65026;
    public static final int JAMO_VT = 65024;
    public static final int MIN_NORMAL_MAYBE_YES = 64512;
    public static final int JAMO_L = 2;
    public static final int INERT = 1;
    public static final int HAS_COMP_BOUNDARY_AFTER = 1;
    public static final int OFFSET_SHIFT = 1;
    public static final int DELTA_TCCC_0 = 0;
    public static final int DELTA_TCCC_1 = 2;
    public static final int DELTA_TCCC_GT_1 = 4;
    public static final int DELTA_TCCC_MASK = 6;
    public static final int DELTA_SHIFT = 3;
    public static final int MAX_DELTA = 64;
    public static final int IX_NORM_TRIE_OFFSET = 0;
    public static final int IX_EXTRA_DATA_OFFSET = 1;
    public static final int IX_SMALL_FCD_OFFSET = 2;
    public static final int IX_RESERVED3_OFFSET = 3;
    public static final int IX_TOTAL_SIZE = 7;
    public static final int IX_MIN_DECOMP_NO_CP = 8;
    public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
    public static final int IX_MIN_YES_NO = 10;
    public static final int IX_MIN_NO_NO = 11;
    public static final int IX_LIMIT_NO_NO = 12;
    public static final int IX_MIN_MAYBE_YES = 13;
    public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
    public static final int IX_MIN_NO_NO_COMP_BOUNDARY_BEFORE = 15;
    public static final int IX_MIN_NO_NO_COMP_NO_MAYBE_CC = 16;
    public static final int IX_MIN_NO_NO_EMPTY = 17;
    public static final int IX_MIN_LCCC_CP = 18;
    public static final int IX_COUNT = 20;
    public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
    public static final int MAPPING_HAS_RAW_MAPPING = 64;
    public static final int MAPPING_LENGTH_MASK = 31;
    public static final int COMP_1_LAST_TUPLE = 32768;
    public static final int COMP_1_TRIPLE = 1;
    public static final int COMP_1_TRAIL_LIMIT = 13312;
    public static final int COMP_1_TRAIL_MASK = 32766;
    public static final int COMP_1_TRAIL_SHIFT = 9;
    public static final int COMP_2_TRAIL_SHIFT = 6;
    public static final int COMP_2_TRAIL_MASK = 65472;
    private VersionInfo dataVersion;
    private int minDecompNoCP;
    private int minCompNoMaybeCP;
    private int minLcccCP;
    private int minYesNo;
    private int minYesNoMappingsOnly;
    private int minNoNo;
    private int minNoNoCompBoundaryBefore;
    private int minNoNoCompNoMaybeCC;
    private int minNoNoEmpty;
    private int limitNoNo;
    private int centerNoNoDelta;
    private int minMaybeYes;
    private Trie2_16 normTrie;
    private String maybeYesCompositions;
    private String extraData;
    private byte[] smallFCD;
    private Trie2_32 canonIterData;
    private ArrayList<UnicodeSet> canonStartSets;
    private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
    private static final int CANON_HAS_COMPOSITIONS = 1073741824;
    private static final int CANON_HAS_SET = 2097152;
    private static final int CANON_VALUE_MASK = 2097151;
    
    public Normalizer2Impl load(final ByteBuffer bytes) {
        try {
            this.dataVersion = ICUBinary.readHeaderAndDataVersion(bytes, 1316121906, Normalizer2Impl.IS_ACCEPTABLE);
            final int indexesLength = bytes.getInt() / 4;
            if (indexesLength <= 18) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough indexes");
            }
            final int[] inIndexes = new int[indexesLength];
            inIndexes[0] = indexesLength * 4;
            for (int i = 1; i < indexesLength; ++i) {
                inIndexes[i] = bytes.getInt();
            }
            this.minDecompNoCP = inIndexes[8];
            this.minCompNoMaybeCP = inIndexes[9];
            this.minLcccCP = inIndexes[18];
            this.minYesNo = inIndexes[10];
            this.minYesNoMappingsOnly = inIndexes[14];
            this.minNoNo = inIndexes[11];
            this.minNoNoCompBoundaryBefore = inIndexes[15];
            this.minNoNoCompNoMaybeCC = inIndexes[16];
            this.minNoNoEmpty = inIndexes[17];
            this.limitNoNo = inIndexes[12];
            this.minMaybeYes = inIndexes[13];
            assert (this.minMaybeYes & 0x7) == 0x0;
            this.centerNoNoDelta = (this.minMaybeYes >> 3) - 64 - 1;
            int offset = inIndexes[0];
            int nextOffset = inIndexes[1];
            this.normTrie = Trie2_16.createFromSerialized(bytes);
            final int trieLength = this.normTrie.getSerializedLength();
            if (trieLength > nextOffset - offset) {
                throw new ICUUncheckedIOException("Normalizer2 data: not enough bytes for normTrie");
            }
            ICUBinary.skipBytes(bytes, nextOffset - offset - trieLength);
            offset = nextOffset;
            nextOffset = inIndexes[2];
            final int numChars = (nextOffset - offset) / 2;
            if (numChars != 0) {
                this.maybeYesCompositions = ICUBinary.getString(bytes, numChars, 0);
                this.extraData = this.maybeYesCompositions.substring(64512 - this.minMaybeYes >> 1);
            }
            offset = nextOffset;
            bytes.get(this.smallFCD = new byte[256]);
            return this;
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException(e);
        }
    }
    
    public Normalizer2Impl load(final String name) {
        return this.load(ICUBinary.getRequiredData(name));
    }
    
    private void enumLcccRange(final int start, final int end, final int norm16, final UnicodeSet set) {
        if (norm16 > 64512 && norm16 != 65024) {
            set.add(start, end);
        }
        else if (this.minNoNoCompNoMaybeCC <= norm16 && norm16 < this.limitNoNo) {
            final int fcd16 = this.getFCD16(start);
            if (fcd16 > 255) {
                set.add(start, end);
            }
        }
    }
    
    private void enumNorm16PropertyStartsRange(int start, final int end, final int value, final UnicodeSet set) {
        set.add(start);
        if (start != end && this.isAlgorithmicNoNo(value) && (value & 0x6) > 2) {
            int prevFCD16 = this.getFCD16(start);
            while (++start <= end) {
                final int fcd16 = this.getFCD16(start);
                if (fcd16 != prevFCD16) {
                    set.add(start);
                    prevFCD16 = fcd16;
                }
            }
        }
    }
    
    public void addLcccChars(final UnicodeSet set) {
        final Iterator<Trie2.Range> trieIterator = this.normTrie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            this.enumLcccRange(range.startCodePoint, range.endCodePoint, range.value, set);
        }
    }
    
    public void addPropertyStarts(final UnicodeSet set) {
        final Iterator<Trie2.Range> trieIterator = this.normTrie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            this.enumNorm16PropertyStartsRange(range.startCodePoint, range.endCodePoint, range.value, set);
        }
        for (int c = 44032; c < 55204; c += 28) {
            set.add(c);
            set.add(c + 1);
        }
        set.add(55204);
    }
    
    public void addCanonIterPropertyStarts(final UnicodeSet set) {
        this.ensureCanonIterData();
        final Iterator<Trie2.Range> trieIterator = this.canonIterData.iterator(Normalizer2Impl.segmentStarterMapper);
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            set.add(range.startCodePoint);
        }
    }
    
    public synchronized Normalizer2Impl ensureCanonIterData() {
        if (this.canonIterData == null) {
            final Trie2Writable newData = new Trie2Writable(0, 0);
            this.canonStartSets = new ArrayList<UnicodeSet>();
            final Iterator<Trie2.Range> trieIterator = this.normTrie.iterator();
            Trie2.Range range;
            while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
                final int norm16 = range.value;
                if (!isInert(norm16)) {
                    if (this.minYesNo <= norm16 && norm16 < this.minNoNo) {
                        continue;
                    }
                    for (int c = range.startCodePoint; c <= range.endCodePoint; ++c) {
                        int newValue;
                        final int oldValue = newValue = newData.get(c);
                        if (this.isMaybeOrNonZeroCC(norm16)) {
                            newValue |= Integer.MIN_VALUE;
                            if (norm16 < 64512) {
                                newValue |= 0x40000000;
                            }
                        }
                        else if (norm16 < this.minYesNo) {
                            newValue |= 0x40000000;
                        }
                        else {
                            int c2 = c;
                            int norm16_2 = norm16;
                            if (this.isDecompNoAlgorithmic(norm16_2)) {
                                c2 = this.mapAlgorithmic(c2, norm16_2);
                                norm16_2 = this.getNorm16(c2);
                                assert !this.isHangulLV(norm16_2) && !this.isHangulLVT(norm16_2);
                            }
                            if (norm16_2 > this.minYesNo) {
                                int mapping = norm16_2 >> 1;
                                final int firstUnit = this.extraData.charAt(mapping);
                                final int length = firstUnit & 0x1F;
                                if ((firstUnit & 0x80) != 0x0 && c == c2 && (this.extraData.charAt(mapping - 1) & '\u00ff') != 0x0) {
                                    newValue |= Integer.MIN_VALUE;
                                }
                                if (length != 0) {
                                    final int limit = ++mapping + length;
                                    c2 = this.extraData.codePointAt(mapping);
                                    this.addToStartSet(newData, c, c2);
                                    if (norm16_2 >= this.minNoNo) {
                                        while ((mapping += Character.charCount(c2)) < limit) {
                                            c2 = this.extraData.codePointAt(mapping);
                                            final int c2Value = newData.get(c2);
                                            if ((c2Value & Integer.MIN_VALUE) == 0x0) {
                                                newData.set(c2, c2Value | Integer.MIN_VALUE);
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                this.addToStartSet(newData, c, c2);
                            }
                        }
                        if (newValue != oldValue) {
                            newData.set(c, newValue);
                        }
                    }
                }
            }
            this.canonIterData = newData.toTrie2_32();
        }
        return this;
    }
    
    public int getNorm16(final int c) {
        return this.normTrie.get(c);
    }
    
    public int getCompQuickCheck(final int norm16) {
        if (norm16 < this.minNoNo || 65026 <= norm16) {
            return 1;
        }
        if (this.minMaybeYes <= norm16) {
            return 2;
        }
        return 0;
    }
    
    public boolean isAlgorithmicNoNo(final int norm16) {
        return this.limitNoNo <= norm16 && norm16 < this.minMaybeYes;
    }
    
    public boolean isCompNo(final int norm16) {
        return this.minNoNo <= norm16 && norm16 < this.minMaybeYes;
    }
    
    public boolean isDecompYes(final int norm16) {
        return norm16 < this.minYesNo || this.minMaybeYes <= norm16;
    }
    
    public int getCC(final int norm16) {
        if (norm16 >= 64512) {
            return getCCFromNormalYesOrMaybe(norm16);
        }
        if (norm16 < this.minNoNo || this.limitNoNo <= norm16) {
            return 0;
        }
        return this.getCCFromNoNo(norm16);
    }
    
    public static int getCCFromNormalYesOrMaybe(final int norm16) {
        return norm16 >> 1 & 0xFF;
    }
    
    public static int getCCFromYesOrMaybe(final int norm16) {
        return (norm16 >= 64512) ? getCCFromNormalYesOrMaybe(norm16) : 0;
    }
    
    public int getCCFromYesOrMaybeCP(final int c) {
        if (c < this.minCompNoMaybeCP) {
            return 0;
        }
        return getCCFromYesOrMaybe(this.getNorm16(c));
    }
    
    public int getFCD16(final int c) {
        if (c < this.minDecompNoCP) {
            return 0;
        }
        if (c <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(c)) {
            return 0;
        }
        return this.getFCD16FromNormData(c);
    }
    
    public boolean singleLeadMightHaveNonZeroFCD16(final int lead) {
        final byte bits = this.smallFCD[lead >> 8];
        return bits != 0 && (bits >> (lead >> 5 & 0x7) & 0x1) != 0x0;
    }
    
    public int getFCD16FromNormData(int c) {
        int norm16 = this.getNorm16(c);
        if (norm16 >= this.limitNoNo) {
            if (norm16 >= 64512) {
                norm16 = getCCFromNormalYesOrMaybe(norm16);
                return norm16 | norm16 << 8;
            }
            if (norm16 >= this.minMaybeYes) {
                return 0;
            }
            final int deltaTrailCC = norm16 & 0x6;
            if (deltaTrailCC <= 2) {
                return deltaTrailCC >> 1;
            }
            c = this.mapAlgorithmic(c, norm16);
            norm16 = this.getNorm16(c);
        }
        if (norm16 <= this.minYesNo || this.isHangulLVT(norm16)) {
            return 0;
        }
        final int mapping = norm16 >> 1;
        final int firstUnit = this.extraData.charAt(mapping);
        int fcd16 = firstUnit >> 8;
        if ((firstUnit & 0x80) != 0x0) {
            fcd16 |= (this.extraData.charAt(mapping - 1) & '\uff00');
        }
        return fcd16;
    }
    
    public String getDecomposition(int c) {
        int norm16;
        if (c < this.minDecompNoCP || this.isMaybeOrNonZeroCC(norm16 = this.getNorm16(c))) {
            return null;
        }
        int decomp = -1;
        if (this.isDecompNoAlgorithmic(norm16)) {
            c = (decomp = this.mapAlgorithmic(c, norm16));
            norm16 = this.getNorm16(c);
        }
        if (norm16 < this.minYesNo) {
            if (decomp < 0) {
                return null;
            }
            return UTF16.valueOf(decomp);
        }
        else {
            if (this.isHangulLV(norm16) || this.isHangulLVT(norm16)) {
                final StringBuilder buffer = new StringBuilder();
                Hangul.decompose(c, buffer);
                return buffer.toString();
            }
            int mapping = norm16 >> 1;
            final int length = this.extraData.charAt(mapping++) & '\u001f';
            return this.extraData.substring(mapping, mapping + length);
        }
    }
    
    public String getRawDecomposition(final int c) {
        final int norm16;
        if (c < this.minDecompNoCP || this.isDecompYes(norm16 = this.getNorm16(c))) {
            return null;
        }
        if (this.isHangulLV(norm16) || this.isHangulLVT(norm16)) {
            final StringBuilder buffer = new StringBuilder();
            Hangul.getRawDecomposition(c, buffer);
            return buffer.toString();
        }
        if (this.isDecompNoAlgorithmic(norm16)) {
            return UTF16.valueOf(this.mapAlgorithmic(c, norm16));
        }
        int mapping = norm16 >> 1;
        final int firstUnit = this.extraData.charAt(mapping);
        final int mLength = firstUnit & 0x1F;
        if ((firstUnit & 0x40) == 0x0) {
            ++mapping;
            return this.extraData.substring(mapping, mapping + mLength);
        }
        final int rawMapping = mapping - (firstUnit >> 7 & 0x1) - 1;
        final char rm0 = this.extraData.charAt(rawMapping);
        if (rm0 <= '\u001f') {
            return this.extraData.substring(rawMapping - rm0, rawMapping);
        }
        final StringBuilder buffer2 = new StringBuilder(mLength - 1).append(rm0);
        mapping += 3;
        return buffer2.append(this.extraData, mapping, mapping + mLength - 2).toString();
    }
    
    public boolean isCanonSegmentStarter(final int c) {
        return this.canonIterData.get(c) >= 0;
    }
    
    public boolean getCanonStartSet(final int c, final UnicodeSet set) {
        final int canonValue = this.canonIterData.get(c) & Integer.MAX_VALUE;
        if (canonValue == 0) {
            return false;
        }
        set.clear();
        final int value = canonValue & 0x1FFFFF;
        if ((canonValue & 0x200000) != 0x0) {
            set.addAll(this.canonStartSets.get(value));
        }
        else if (value != 0) {
            set.add(value);
        }
        if ((canonValue & 0x40000000) != 0x0) {
            final int norm16 = this.getNorm16(c);
            if (norm16 == 2) {
                final int syllable = 44032 + (c - 4352) * 588;
                set.add(syllable, syllable + 588 - 1);
            }
            else {
                this.addComposites(this.getCompositionsList(norm16), set);
            }
        }
        return true;
    }
    
    public Appendable decompose(final CharSequence s, final StringBuilder dest) {
        this.decompose(s, 0, s.length(), dest, s.length());
        return dest;
    }
    
    public void decompose(final CharSequence s, final int src, final int limit, final StringBuilder dest, int destLengthEstimate) {
        if (destLengthEstimate < 0) {
            destLengthEstimate = limit - src;
        }
        dest.setLength(0);
        final ReorderingBuffer buffer = new ReorderingBuffer(this, dest, destLengthEstimate);
        this.decompose(s, src, limit, buffer);
    }
    
    public int decompose(final CharSequence s, int src, final int limit, final ReorderingBuffer buffer) {
        final int minNoCP = this.minDecompNoCP;
        int c = 0;
        int norm16 = 0;
        int prevBoundary = src;
        int prevCC = 0;
        while (true) {
            final int prevSrc = src;
            while (src != limit) {
                if ((c = s.charAt(src)) < minNoCP || this.isMostDecompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                    ++src;
                }
                else {
                    if (!UTF16.isSurrogate((char)c)) {
                        break;
                    }
                    if (UTF16Plus.isSurrogateLead(c)) {
                        final char c2;
                        if (src + 1 != limit && Character.isLowSurrogate(c2 = s.charAt(src + 1))) {
                            c = Character.toCodePoint((char)c, c2);
                        }
                    }
                    else {
                        final char c2;
                        if (prevSrc < src && Character.isHighSurrogate(c2 = s.charAt(src - 1))) {
                            --src;
                            c = Character.toCodePoint(c2, (char)c);
                        }
                    }
                    if (!this.isMostDecompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                        break;
                    }
                    src += Character.charCount(c);
                }
            }
            if (src != prevSrc) {
                if (buffer != null) {
                    buffer.flushAndAppendZeroCC(s, prevSrc, src);
                }
                else {
                    prevCC = 0;
                    prevBoundary = src;
                }
            }
            if (src == limit) {
                return src;
            }
            src += Character.charCount(c);
            if (buffer != null) {
                this.decompose(c, norm16, buffer);
            }
            else {
                if (!this.isDecompYes(norm16)) {
                    break;
                }
                final int cc = getCCFromYesOrMaybe(norm16);
                if (prevCC > cc && cc != 0) {
                    break;
                }
                if ((prevCC = cc) > 1) {
                    continue;
                }
                prevBoundary = src;
            }
        }
        return prevBoundary;
    }
    
    public void decomposeAndAppend(final CharSequence s, final boolean doDecompose, final ReorderingBuffer buffer) {
        final int limit = s.length();
        if (limit == 0) {
            return;
        }
        if (doDecompose) {
            this.decompose(s, 0, limit, buffer);
            return;
        }
        int c = Character.codePointAt(s, 0);
        int src = 0;
        int cc;
        int firstCC;
        int prevCC;
        for (prevCC = (firstCC = (cc = this.getCC(this.getNorm16(c)))); cc != 0; cc = this.getCC(this.getNorm16(c))) {
            prevCC = cc;
            src += Character.charCount(c);
            if (src >= limit) {
                break;
            }
            c = Character.codePointAt(s, src);
        }
        buffer.append(s, 0, src, firstCC, prevCC);
        buffer.append(s, src, limit);
    }
    
    public boolean compose(final CharSequence s, int src, final int limit, final boolean onlyContiguous, final boolean doCompose, final ReorderingBuffer buffer) {
        int prevBoundary = src;
        final int minNoMaybeCP = this.minCompNoMaybeCP;
    Label_0009:
        while (true) {
            int c = 0;
            int norm16 = 0;
            while (src != limit) {
                if ((c = s.charAt(src)) >= minNoMaybeCP && !this.isCompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                    int prevSrc = src++;
                    if (UTF16.isSurrogate((char)c)) {
                        if (UTF16Plus.isSurrogateLead(c)) {
                            final char c2;
                            if (src != limit && Character.isLowSurrogate(c2 = s.charAt(src))) {
                                ++src;
                                c = Character.toCodePoint((char)c, c2);
                            }
                        }
                        else {
                            final char c2;
                            if (prevBoundary < prevSrc && Character.isHighSurrogate(c2 = s.charAt(prevSrc - 1))) {
                                --prevSrc;
                                c = Character.toCodePoint(c2, (char)c);
                            }
                        }
                        if (this.isCompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                            continue;
                        }
                    }
                    Label_0837: {
                        if (!this.isMaybeOrNonZeroCC(norm16)) {
                            if (!doCompose) {
                                return false;
                            }
                            if (this.isDecompNoAlgorithmic(norm16)) {
                                if (this.norm16HasCompBoundaryAfter(norm16, onlyContiguous) || this.hasCompBoundaryBefore(s, src, limit)) {
                                    if (prevBoundary != prevSrc) {
                                        buffer.append(s, prevBoundary, prevSrc);
                                    }
                                    buffer.append(this.mapAlgorithmic(c, norm16), 0);
                                    prevBoundary = src;
                                    continue Label_0009;
                                }
                            }
                            else if (norm16 < this.minNoNoCompBoundaryBefore) {
                                if (this.norm16HasCompBoundaryAfter(norm16, onlyContiguous) || this.hasCompBoundaryBefore(s, src, limit)) {
                                    if (prevBoundary != prevSrc) {
                                        buffer.append(s, prevBoundary, prevSrc);
                                    }
                                    int mapping = norm16 >> 1;
                                    final int length = this.extraData.charAt(mapping++) & '\u001f';
                                    buffer.append((CharSequence)this.extraData, mapping, mapping + length);
                                    prevBoundary = src;
                                    continue Label_0009;
                                }
                            }
                            else if (norm16 >= this.minNoNoEmpty && (this.hasCompBoundaryBefore(s, src, limit) || this.hasCompBoundaryAfter(s, prevBoundary, prevSrc, onlyContiguous))) {
                                if (prevBoundary != prevSrc) {
                                    buffer.append(s, prevBoundary, prevSrc);
                                }
                                prevBoundary = src;
                                continue Label_0009;
                            }
                        }
                        else if (isJamoVT(norm16) && prevBoundary != prevSrc) {
                            final char prev = s.charAt(prevSrc - 1);
                            if (c < 4519) {
                                final char l = (char)(prev - '\u1100');
                                if (l < '\u0013') {
                                    if (!doCompose) {
                                        return false;
                                    }
                                    int t;
                                    if (src != limit && 0 < (t = s.charAt(src) - '\u11a7') && t < 28) {
                                        ++src;
                                    }
                                    else if (this.hasCompBoundaryBefore(s, src, limit)) {
                                        t = 0;
                                    }
                                    else {
                                        t = -1;
                                    }
                                    if (t >= 0) {
                                        final int syllable = 44032 + (l * '\u0015' + (c - 4449)) * 28 + t;
                                        --prevSrc;
                                        if (prevBoundary != prevSrc) {
                                            buffer.append(s, prevBoundary, prevSrc);
                                        }
                                        buffer.append((char)syllable);
                                        prevBoundary = src;
                                        continue Label_0009;
                                    }
                                }
                            }
                            else if (Hangul.isHangulLV(prev)) {
                                if (!doCompose) {
                                    return false;
                                }
                                final int syllable2 = prev + c - 4519;
                                --prevSrc;
                                if (prevBoundary != prevSrc) {
                                    buffer.append(s, prevBoundary, prevSrc);
                                }
                                buffer.append((char)syllable2);
                                prevBoundary = src;
                                continue Label_0009;
                            }
                        }
                        else if (norm16 > 65024) {
                            int cc = getCCFromNormalYesOrMaybe(norm16);
                            if (!onlyContiguous || this.getPreviousTrailCC(s, prevBoundary, prevSrc) <= cc) {
                                while (src != limit) {
                                    final int prevCC = cc;
                                    c = Character.codePointAt(s, src);
                                    final int n16 = this.normTrie.get(c);
                                    if (n16 >= 65026) {
                                        cc = getCCFromNormalYesOrMaybe(n16);
                                        if (prevCC <= cc) {
                                            src += Character.charCount(c);
                                            continue;
                                        }
                                        if (!doCompose) {
                                            return false;
                                        }
                                    }
                                    if (!this.norm16HasCompBoundaryBefore(n16)) {
                                        break Label_0837;
                                    }
                                    if (this.isCompYesAndZeroCC(n16)) {
                                        src += Character.charCount(c);
                                        continue Label_0009;
                                    }
                                    continue Label_0009;
                                }
                                if (doCompose) {
                                    buffer.append(s, prevBoundary, limit);
                                }
                                return true;
                            }
                            if (!doCompose) {
                                return false;
                            }
                        }
                    }
                    if (prevBoundary != prevSrc && !this.norm16HasCompBoundaryBefore(norm16)) {
                        c = Character.codePointBefore(s, prevSrc);
                        norm16 = this.normTrie.get(c);
                        if (!this.norm16HasCompBoundaryAfter(norm16, onlyContiguous)) {
                            prevSrc -= Character.charCount(c);
                        }
                    }
                    if (doCompose && prevBoundary != prevSrc) {
                        buffer.append(s, prevBoundary, prevSrc);
                    }
                    final int recomposeStartIndex = buffer.length();
                    this.decomposeShort(s, prevSrc, src, false, onlyContiguous, buffer);
                    src = this.decomposeShort(s, src, limit, true, onlyContiguous, buffer);
                    this.recompose(buffer, recomposeStartIndex, onlyContiguous);
                    if (!doCompose) {
                        if (!buffer.equals(s, prevSrc, src)) {
                            return false;
                        }
                        buffer.remove();
                    }
                    prevBoundary = src;
                    continue Label_0009;
                }
                ++src;
            }
            if (prevBoundary != limit && doCompose) {
                buffer.append(s, prevBoundary, limit);
            }
            return true;
        }
    }
    
    public int composeQuickCheck(final CharSequence s, int src, final int limit, final boolean onlyContiguous, final boolean doSpan) {
        int qcResult = 0;
        int prevBoundary = src;
        final int minNoMaybeCP = this.minCompNoMaybeCP;
    Label_0415:
        while (true) {
            int c = 0;
            int norm16 = 0;
            while (src != limit) {
                if ((c = s.charAt(src)) < minNoMaybeCP || this.isCompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                    ++src;
                }
                else {
                    int prevSrc = src++;
                    if (UTF16.isSurrogate((char)c)) {
                        if (UTF16Plus.isSurrogateLead(c)) {
                            final char c2;
                            if (src != limit && Character.isLowSurrogate(c2 = s.charAt(src))) {
                                ++src;
                                c = Character.toCodePoint((char)c, c2);
                            }
                        }
                        else {
                            final char c2;
                            if (prevBoundary < prevSrc && Character.isHighSurrogate(c2 = s.charAt(prevSrc - 1))) {
                                --prevSrc;
                                c = Character.toCodePoint(c2, (char)c);
                            }
                        }
                        if (this.isCompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                            continue;
                        }
                    }
                    int prevNorm16 = 1;
                    if (prevBoundary != prevSrc) {
                        prevBoundary = prevSrc;
                        if (!this.norm16HasCompBoundaryBefore(norm16)) {
                            c = Character.codePointBefore(s, prevSrc);
                            final int n16 = this.getNorm16(c);
                            if (!this.norm16HasCompBoundaryAfter(n16, onlyContiguous)) {
                                prevBoundary -= Character.charCount(c);
                                prevNorm16 = n16;
                            }
                        }
                    }
                    if (!this.isMaybeOrNonZeroCC(norm16)) {
                        break Label_0415;
                    }
                    int cc = getCCFromYesOrMaybe(norm16);
                    if (onlyContiguous && cc != 0 && this.getTrailCCFromCompYesAndZeroCC(prevNorm16) > cc) {
                        break Label_0415;
                    }
                    while (true) {
                        if (norm16 < 65026) {
                            if (doSpan) {
                                return prevBoundary << 1;
                            }
                            qcResult = 1;
                        }
                        if (src == limit) {
                            return src << 1 | qcResult;
                        }
                        final int prevCC = cc;
                        c = Character.codePointAt(s, src);
                        norm16 = this.getNorm16(c);
                        if (!this.isMaybeOrNonZeroCC(norm16)) {
                            break;
                        }
                        cc = getCCFromYesOrMaybe(norm16);
                        if (prevCC > cc && cc != 0) {
                            break;
                        }
                        src += Character.charCount(c);
                    }
                    if (this.isCompYesAndZeroCC(norm16)) {
                        prevBoundary = src;
                        src += Character.charCount(c);
                        continue Label_0415;
                    }
                    break Label_0415;
                }
            }
            return src << 1 | qcResult;
        }
        return prevBoundary << 1;
    }
    
    public void composeAndAppend(final CharSequence s, final boolean doCompose, final boolean onlyContiguous, final ReorderingBuffer buffer) {
        int src = 0;
        final int limit = s.length();
        if (!buffer.isEmpty()) {
            final int firstStarterInSrc = this.findNextCompBoundary(s, 0, limit, onlyContiguous);
            if (0 != firstStarterInSrc) {
                final int lastStarterInDest = this.findPreviousCompBoundary(buffer.getStringBuilder(), buffer.length(), onlyContiguous);
                final StringBuilder middle = new StringBuilder(buffer.length() - lastStarterInDest + firstStarterInSrc + 16);
                middle.append(buffer.getStringBuilder(), lastStarterInDest, buffer.length());
                buffer.removeSuffix(buffer.length() - lastStarterInDest);
                middle.append(s, 0, firstStarterInSrc);
                this.compose(middle, 0, middle.length(), onlyContiguous, true, buffer);
                src = firstStarterInSrc;
            }
        }
        if (doCompose) {
            this.compose(s, src, limit, onlyContiguous, true, buffer);
        }
        else {
            buffer.append(s, src, limit);
        }
    }
    
    public int makeFCD(final CharSequence s, int src, final int limit, final ReorderingBuffer buffer) {
        int prevBoundary = src;
        int c = 0;
        int prevFCD16 = 0;
        int fcd16 = 0;
        while (true) {
            int prevSrc = src;
            while (src != limit) {
                if ((c = s.charAt(src)) < this.minLcccCP) {
                    prevFCD16 = ~c;
                    ++src;
                }
                else if (!this.singleLeadMightHaveNonZeroFCD16(c)) {
                    prevFCD16 = 0;
                    ++src;
                }
                else {
                    if (UTF16.isSurrogate((char)c)) {
                        if (UTF16Plus.isSurrogateLead(c)) {
                            final char c2;
                            if (src + 1 != limit && Character.isLowSurrogate(c2 = s.charAt(src + 1))) {
                                c = Character.toCodePoint((char)c, c2);
                            }
                        }
                        else {
                            final char c2;
                            if (prevSrc < src && Character.isHighSurrogate(c2 = s.charAt(src - 1))) {
                                --src;
                                c = Character.toCodePoint(c2, (char)c);
                            }
                        }
                    }
                    if ((fcd16 = this.getFCD16FromNormData(c)) > 255) {
                        break;
                    }
                    prevFCD16 = fcd16;
                    src += Character.charCount(c);
                }
            }
            if (src != prevSrc) {
                if (src == limit) {
                    if (buffer != null) {
                        buffer.flushAndAppendZeroCC(s, prevSrc, src);
                        break;
                    }
                    break;
                }
                else {
                    prevBoundary = src;
                    if (prevFCD16 < 0) {
                        final int prev = ~prevFCD16;
                        if (prev < this.minDecompNoCP) {
                            prevFCD16 = 0;
                        }
                        else {
                            prevFCD16 = this.getFCD16FromNormData(prev);
                            if (prevFCD16 > 1) {
                                --prevBoundary;
                            }
                        }
                    }
                    else {
                        int p = src - 1;
                        if (Character.isLowSurrogate(s.charAt(p)) && prevSrc < p && Character.isHighSurrogate(s.charAt(p - 1))) {
                            --p;
                            prevFCD16 = this.getFCD16FromNormData(Character.toCodePoint(s.charAt(p), s.charAt(p + 1)));
                        }
                        if (prevFCD16 > 1) {
                            prevBoundary = p;
                        }
                    }
                    if (buffer != null) {
                        buffer.flushAndAppendZeroCC(s, prevSrc, prevBoundary);
                        buffer.append(s, prevBoundary, src);
                    }
                    prevSrc = src;
                }
            }
            else if (src == limit) {
                break;
            }
            src += Character.charCount(c);
            if ((prevFCD16 & 0xFF) <= fcd16 >> 8) {
                if ((fcd16 & 0xFF) <= 1) {
                    prevBoundary = src;
                }
                if (buffer != null) {
                    buffer.appendZeroCC(c);
                }
                prevFCD16 = fcd16;
            }
            else {
                if (buffer == null) {
                    return prevBoundary;
                }
                buffer.removeSuffix(prevSrc - prevBoundary);
                src = this.findNextFCDBoundary(s, src, limit);
                this.decomposeShort(s, prevBoundary, src, false, false, buffer);
                prevBoundary = src;
                prevFCD16 = 0;
            }
        }
        return src;
    }
    
    public void makeFCDAndAppend(final CharSequence s, final boolean doMakeFCD, final ReorderingBuffer buffer) {
        int src = 0;
        final int limit = s.length();
        if (!buffer.isEmpty()) {
            final int firstBoundaryInSrc = this.findNextFCDBoundary(s, 0, limit);
            if (0 != firstBoundaryInSrc) {
                final int lastBoundaryInDest = this.findPreviousFCDBoundary(buffer.getStringBuilder(), buffer.length());
                final StringBuilder middle = new StringBuilder(buffer.length() - lastBoundaryInDest + firstBoundaryInSrc + 16);
                middle.append(buffer.getStringBuilder(), lastBoundaryInDest, buffer.length());
                buffer.removeSuffix(buffer.length() - lastBoundaryInDest);
                middle.append(s, 0, firstBoundaryInSrc);
                this.makeFCD(middle, 0, middle.length(), buffer);
                src = firstBoundaryInSrc;
            }
        }
        if (doMakeFCD) {
            this.makeFCD(s, src, limit, buffer);
        }
        else {
            buffer.append(s, src, limit);
        }
    }
    
    public boolean hasDecompBoundaryBefore(final int c) {
        return c < this.minLcccCP || (c <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(c)) || this.norm16HasDecompBoundaryBefore(this.getNorm16(c));
    }
    
    public boolean norm16HasDecompBoundaryBefore(final int norm16) {
        if (norm16 < this.minNoNoCompNoMaybeCC) {
            return true;
        }
        if (norm16 >= this.limitNoNo) {
            return norm16 <= 64512 || norm16 == 65024;
        }
        final int mapping = norm16 >> 1;
        final int firstUnit = this.extraData.charAt(mapping);
        return (firstUnit & 0x80) == 0x0 || (this.extraData.charAt(mapping - 1) & '\uff00') == 0x0;
    }
    
    public boolean hasDecompBoundaryAfter(final int c) {
        return c < this.minDecompNoCP || (c <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(c)) || this.norm16HasDecompBoundaryAfter(this.getNorm16(c));
    }
    
    public boolean norm16HasDecompBoundaryAfter(final int norm16) {
        if (norm16 <= this.minYesNo || this.isHangulLVT(norm16)) {
            return true;
        }
        if (norm16 < this.limitNoNo) {
            final int mapping = norm16 >> 1;
            final int firstUnit = this.extraData.charAt(mapping);
            return firstUnit <= 511 && (firstUnit <= 255 || (firstUnit & 0x80) == 0x0 || (this.extraData.charAt(mapping - 1) & '\uff00') == 0x0);
        }
        if (this.isMaybeOrNonZeroCC(norm16)) {
            return norm16 <= 64512 || norm16 == 65024;
        }
        return (norm16 & 0x6) <= 2;
    }
    
    public boolean isDecompInert(final int c) {
        return this.isDecompYesAndZeroCC(this.getNorm16(c));
    }
    
    public boolean hasCompBoundaryBefore(final int c) {
        return c < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(this.getNorm16(c));
    }
    
    public boolean hasCompBoundaryAfter(final int c, final boolean onlyContiguous) {
        return this.norm16HasCompBoundaryAfter(this.getNorm16(c), onlyContiguous);
    }
    
    public boolean isCompInert(final int c, final boolean onlyContiguous) {
        final int norm16 = this.getNorm16(c);
        return this.isCompYesAndZeroCC(norm16) && (norm16 & 0x1) != 0x0 && (!onlyContiguous || isInert(norm16) || this.extraData.charAt(norm16 >> 1) <= '\u01ff');
    }
    
    public boolean hasFCDBoundaryBefore(final int c) {
        return this.hasDecompBoundaryBefore(c);
    }
    
    public boolean hasFCDBoundaryAfter(final int c) {
        return this.hasDecompBoundaryAfter(c);
    }
    
    public boolean isFCDInert(final int c) {
        return this.getFCD16(c) <= 1;
    }
    
    private boolean isMaybe(final int norm16) {
        return this.minMaybeYes <= norm16 && norm16 <= 65024;
    }
    
    private boolean isMaybeOrNonZeroCC(final int norm16) {
        return norm16 >= this.minMaybeYes;
    }
    
    private static boolean isInert(final int norm16) {
        return norm16 == 1;
    }
    
    private static boolean isJamoL(final int norm16) {
        return norm16 == 2;
    }
    
    private static boolean isJamoVT(final int norm16) {
        return norm16 == 65024;
    }
    
    private int hangulLVT() {
        return this.minYesNoMappingsOnly | 0x1;
    }
    
    private boolean isHangulLV(final int norm16) {
        return norm16 == this.minYesNo;
    }
    
    private boolean isHangulLVT(final int norm16) {
        return norm16 == this.hangulLVT();
    }
    
    private boolean isCompYesAndZeroCC(final int norm16) {
        return norm16 < this.minNoNo;
    }
    
    private boolean isDecompYesAndZeroCC(final int norm16) {
        return norm16 < this.minYesNo || norm16 == 65024 || (this.minMaybeYes <= norm16 && norm16 <= 64512);
    }
    
    private boolean isMostDecompYesAndZeroCC(final int norm16) {
        return norm16 < this.minYesNo || norm16 == 64512 || norm16 == 65024;
    }
    
    private boolean isDecompNoAlgorithmic(final int norm16) {
        return norm16 >= this.limitNoNo;
    }
    
    private int getCCFromNoNo(final int norm16) {
        final int mapping = norm16 >> 1;
        if ((this.extraData.charAt(mapping) & '\u0080') != 0x0) {
            return this.extraData.charAt(mapping - 1) & '\u00ff';
        }
        return 0;
    }
    
    int getTrailCCFromCompYesAndZeroCC(final int norm16) {
        if (norm16 <= this.minYesNo) {
            return 0;
        }
        return this.extraData.charAt(norm16 >> 1) >> 8;
    }
    
    private int mapAlgorithmic(final int c, final int norm16) {
        return c + (norm16 >> 3) - this.centerNoNoDelta;
    }
    
    private int getCompositionsListForDecompYes(int norm16) {
        if (norm16 < 2 || 64512 <= norm16) {
            return -1;
        }
        if ((norm16 -= this.minMaybeYes) < 0) {
            norm16 += 64512;
        }
        return norm16 >> 1;
    }
    
    private int getCompositionsListForComposite(final int norm16) {
        final int list = 64512 - this.minMaybeYes + norm16 >> 1;
        final int firstUnit = this.maybeYesCompositions.charAt(list);
        return list + 1 + (firstUnit & 0x1F);
    }
    
    private int getCompositionsListForMaybe(final int norm16) {
        return norm16 - this.minMaybeYes >> 1;
    }
    
    private int getCompositionsList(final int norm16) {
        return this.isDecompYes(norm16) ? this.getCompositionsListForDecompYes(norm16) : this.getCompositionsListForComposite(norm16);
    }
    
    private int decomposeShort(final CharSequence s, int src, final int limit, final boolean stopAtCompBoundary, final boolean onlyContiguous, final ReorderingBuffer buffer) {
        while (src < limit) {
            final int c = Character.codePointAt(s, src);
            if (stopAtCompBoundary && c < this.minCompNoMaybeCP) {
                return src;
            }
            final int norm16 = this.getNorm16(c);
            if (stopAtCompBoundary && this.norm16HasCompBoundaryBefore(norm16)) {
                return src;
            }
            src += Character.charCount(c);
            this.decompose(c, norm16, buffer);
            if (stopAtCompBoundary && this.norm16HasCompBoundaryAfter(norm16, onlyContiguous)) {
                return src;
            }
        }
        return src;
    }
    
    private void decompose(int c, int norm16, final ReorderingBuffer buffer) {
        if (norm16 >= this.limitNoNo) {
            if (this.isMaybeOrNonZeroCC(norm16)) {
                buffer.append(c, getCCFromYesOrMaybe(norm16));
                return;
            }
            c = this.mapAlgorithmic(c, norm16);
            norm16 = this.getNorm16(c);
        }
        if (norm16 < this.minYesNo) {
            buffer.append(c, 0);
        }
        else if (this.isHangulLV(norm16) || this.isHangulLVT(norm16)) {
            Hangul.decompose(c, buffer);
        }
        else {
            int mapping = norm16 >> 1;
            final int firstUnit = this.extraData.charAt(mapping);
            final int length = firstUnit & 0x1F;
            final int trailCC = firstUnit >> 8;
            int leadCC;
            if ((firstUnit & 0x80) != 0x0) {
                leadCC = this.extraData.charAt(mapping - 1) >> 8;
            }
            else {
                leadCC = 0;
            }
            ++mapping;
            buffer.append(this.extraData, mapping, mapping + length, leadCC, trailCC);
        }
    }
    
    private static int combine(final String compositions, int list, final int trail) {
        if (trail < 13312) {
            int key1;
            int firstUnit;
            for (key1 = trail << 1; key1 > (firstUnit = compositions.charAt(list)); list += 2 + (firstUnit & 0x1)) {}
            if (key1 == (firstUnit & 0x7FFE)) {
                if ((firstUnit & 0x1) != 0x0) {
                    return compositions.charAt(list + 1) << 16 | compositions.charAt(list + 2);
                }
                return compositions.charAt(list + 1);
            }
        }
        else {
            final int key1 = 13312 + (trail >> 9 & 0xFFFFFFFE);
            final int key2 = trail << 6 & 0xFFFF;
            while (true) {
                final int firstUnit;
                if (key1 > (firstUnit = compositions.charAt(list))) {
                    list += 2 + (firstUnit & 0x1);
                }
                else {
                    if (key1 != (firstUnit & 0x7FFE)) {
                        break;
                    }
                    final int secondUnit;
                    if (key2 > (secondUnit = compositions.charAt(list + 1))) {
                        if ((firstUnit & 0x8000) != 0x0) {
                            break;
                        }
                        list += 3;
                    }
                    else {
                        if (key2 == (secondUnit & 0xFFC0)) {
                            return (secondUnit & 0xFFFF003F) << 16 | compositions.charAt(list + 2);
                        }
                        break;
                    }
                }
            }
        }
        return -1;
    }
    
    private void addComposites(int list, final UnicodeSet set) {
        int firstUnit;
        do {
            firstUnit = this.maybeYesCompositions.charAt(list);
            int compositeAndFwd;
            if ((firstUnit & 0x1) == 0x0) {
                compositeAndFwd = this.maybeYesCompositions.charAt(list + 1);
                list += 2;
            }
            else {
                compositeAndFwd = ((this.maybeYesCompositions.charAt(list + 1) & 0xFFFF003F) << 16 | this.maybeYesCompositions.charAt(list + 2));
                list += 3;
            }
            final int composite = compositeAndFwd >> 1;
            if ((compositeAndFwd & 0x1) != 0x0) {
                this.addComposites(this.getCompositionsListForComposite(this.getNorm16(composite)), set);
            }
            set.add(composite);
        } while ((firstUnit & 0x8000) == 0x0);
    }
    
    private void recompose(final ReorderingBuffer buffer, final int recomposeStartIndex, final boolean onlyContiguous) {
        final StringBuilder sb = buffer.getStringBuilder();
        int p = recomposeStartIndex;
        if (p == sb.length()) {
            return;
        }
        int compositionsList = -1;
        int starter = -1;
        boolean starterIsSupplementary = false;
        int prevCC = 0;
        while (true) {
            final int c = sb.codePointAt(p);
            p += Character.charCount(c);
            final int norm16 = this.getNorm16(c);
            final int cc = getCCFromYesOrMaybe(norm16);
            if (this.isMaybe(norm16) && compositionsList >= 0 && (prevCC < cc || prevCC == 0)) {
                if (isJamoVT(norm16)) {
                    if (c < 4519) {
                        final char prev = (char)(sb.charAt(starter) - '\u1100');
                        if (prev < '\u0013') {
                            final int pRemove = p - 1;
                            char syllable = (char)(44032 + (prev * '\u0015' + (c - 4449)) * 28);
                            final char t;
                            if (p != sb.length() && (t = (char)(sb.charAt(p) - '\u11a7')) < '\u001c') {
                                ++p;
                                syllable += t;
                            }
                            sb.setCharAt(starter, syllable);
                            sb.delete(pRemove, p);
                            p = pRemove;
                        }
                    }
                    if (p == sb.length()) {
                        break;
                    }
                    compositionsList = -1;
                    continue;
                }
                else {
                    final int compositeAndFwd;
                    if ((compositeAndFwd = combine(this.maybeYesCompositions, compositionsList, c)) >= 0) {
                        final int composite = compositeAndFwd >> 1;
                        final int pRemove = p - Character.charCount(c);
                        sb.delete(pRemove, p);
                        p = pRemove;
                        if (starterIsSupplementary) {
                            if (composite > 65535) {
                                sb.setCharAt(starter, UTF16.getLeadSurrogate(composite));
                                sb.setCharAt(starter + 1, UTF16.getTrailSurrogate(composite));
                            }
                            else {
                                sb.setCharAt(starter, (char)c);
                                sb.deleteCharAt(starter + 1);
                                starterIsSupplementary = false;
                                --p;
                            }
                        }
                        else if (composite > 65535) {
                            starterIsSupplementary = true;
                            sb.setCharAt(starter, UTF16.getLeadSurrogate(composite));
                            sb.insert(starter + 1, UTF16.getTrailSurrogate(composite));
                            ++p;
                        }
                        else {
                            sb.setCharAt(starter, (char)composite);
                        }
                        if (p == sb.length()) {
                            break;
                        }
                        if ((compositeAndFwd & 0x1) != 0x0) {
                            compositionsList = this.getCompositionsListForComposite(this.getNorm16(composite));
                            continue;
                        }
                        compositionsList = -1;
                        continue;
                    }
                }
            }
            prevCC = cc;
            if (p == sb.length()) {
                break;
            }
            if (cc == 0) {
                if ((compositionsList = this.getCompositionsListForDecompYes(norm16)) < 0) {
                    continue;
                }
                if (c <= 65535) {
                    starterIsSupplementary = false;
                    starter = p - 1;
                }
                else {
                    starterIsSupplementary = true;
                    starter = p - 2;
                }
            }
            else {
                if (!onlyContiguous) {
                    continue;
                }
                compositionsList = -1;
            }
        }
        buffer.flush();
    }
    
    public int composePair(final int a, int b) {
        final int norm16 = this.getNorm16(a);
        if (isInert(norm16)) {
            return -1;
        }
        int list;
        if (norm16 < this.minYesNoMappingsOnly) {
            if (isJamoL(norm16)) {
                b -= 4449;
                if (0 <= b && b < 21) {
                    return 44032 + ((a - 4352) * 21 + b) * 28;
                }
                return -1;
            }
            else if (this.isHangulLV(norm16)) {
                b -= 4519;
                if (0 < b && b < 28) {
                    return a + b;
                }
                return -1;
            }
            else {
                list = 64512 - this.minMaybeYes + norm16 >> 1;
                if (norm16 > this.minYesNo) {
                    list += 1 + (this.maybeYesCompositions.charAt(list) & '\u001f');
                }
            }
        }
        else {
            if (norm16 < this.minMaybeYes || 64512 <= norm16) {
                return -1;
            }
            list = this.getCompositionsListForMaybe(norm16);
        }
        if (b < 0 || 1114111 < b) {
            return -1;
        }
        return combine(this.maybeYesCompositions, list, b) >> 1;
    }
    
    private boolean hasCompBoundaryBefore(final int c, final int norm16) {
        return c < this.minCompNoMaybeCP || this.norm16HasCompBoundaryBefore(norm16);
    }
    
    private boolean norm16HasCompBoundaryBefore(final int norm16) {
        return norm16 < this.minNoNoCompNoMaybeCC || this.isAlgorithmicNoNo(norm16);
    }
    
    private boolean hasCompBoundaryBefore(final CharSequence s, final int src, final int limit) {
        return src == limit || this.hasCompBoundaryBefore(Character.codePointAt(s, src));
    }
    
    private boolean norm16HasCompBoundaryAfter(final int norm16, final boolean onlyContiguous) {
        return (norm16 & 0x1) != 0x0 && (!onlyContiguous || this.isTrailCC01ForCompBoundaryAfter(norm16));
    }
    
    private boolean hasCompBoundaryAfter(final CharSequence s, final int start, final int p, final boolean onlyContiguous) {
        return start == p || this.hasCompBoundaryAfter(Character.codePointBefore(s, p), onlyContiguous);
    }
    
    private boolean isTrailCC01ForCompBoundaryAfter(final int norm16) {
        return isInert(norm16) || (this.isDecompNoAlgorithmic(norm16) ? ((norm16 & 0x6) <= 2) : (this.extraData.charAt(norm16 >> 1) <= '\u01ff'));
    }
    
    private int findPreviousCompBoundary(final CharSequence s, int p, final boolean onlyContiguous) {
        while (p > 0) {
            final int c = Character.codePointBefore(s, p);
            final int norm16 = this.getNorm16(c);
            if (this.norm16HasCompBoundaryAfter(norm16, onlyContiguous)) {
                break;
            }
            p -= Character.charCount(c);
            if (this.hasCompBoundaryBefore(c, norm16)) {
                break;
            }
        }
        return p;
    }
    
    private int findNextCompBoundary(final CharSequence s, int p, final int limit, final boolean onlyContiguous) {
        while (p < limit) {
            final int c = Character.codePointAt(s, p);
            final int norm16 = this.normTrie.get(c);
            if (this.hasCompBoundaryBefore(c, norm16)) {
                break;
            }
            p += Character.charCount(c);
            if (this.norm16HasCompBoundaryAfter(norm16, onlyContiguous)) {
                break;
            }
        }
        return p;
    }
    
    private int findPreviousFCDBoundary(final CharSequence s, int p) {
        while (p > 0) {
            final int c = Character.codePointBefore(s, p);
            if (c < this.minDecompNoCP) {
                break;
            }
            final int norm16;
            if (this.norm16HasDecompBoundaryAfter(norm16 = this.getNorm16(c))) {
                break;
            }
            p -= Character.charCount(c);
            if (this.norm16HasDecompBoundaryBefore(norm16)) {
                break;
            }
        }
        return p;
    }
    
    private int findNextFCDBoundary(final CharSequence s, int p, final int limit) {
        while (p < limit) {
            final int c = Character.codePointAt(s, p);
            if (c < this.minLcccCP) {
                break;
            }
            final int norm16;
            if (this.norm16HasDecompBoundaryBefore(norm16 = this.getNorm16(c))) {
                break;
            }
            p += Character.charCount(c);
            if (this.norm16HasDecompBoundaryAfter(norm16)) {
                break;
            }
        }
        return p;
    }
    
    private int getPreviousTrailCC(final CharSequence s, final int start, final int p) {
        if (start == p) {
            return 0;
        }
        return this.getFCD16(Character.codePointBefore(s, p));
    }
    
    private void addToStartSet(final Trie2Writable newData, final int origin, final int decompLead) {
        int canonValue = newData.get(decompLead);
        if ((canonValue & 0x3FFFFF) == 0x0 && origin != 0) {
            newData.set(decompLead, canonValue | origin);
        }
        else {
            UnicodeSet set;
            if ((canonValue & 0x200000) == 0x0) {
                final int firstOrigin = canonValue & 0x1FFFFF;
                canonValue = ((canonValue & 0xFFE00000) | 0x200000 | this.canonStartSets.size());
                newData.set(decompLead, canonValue);
                this.canonStartSets.add(set = new UnicodeSet());
                if (firstOrigin != 0) {
                    set.add(firstOrigin);
                }
            }
            else {
                set = this.canonStartSets.get(canonValue & 0x1FFFFF);
            }
            set.add(origin);
        }
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable();
        segmentStarterMapper = new Trie2.ValueMapper() {
            @Override
            public int map(final int in) {
                return in & Integer.MIN_VALUE;
            }
        };
    }
    
    public static final class Hangul
    {
        public static final int JAMO_L_BASE = 4352;
        public static final int JAMO_L_END = 4370;
        public static final int JAMO_V_BASE = 4449;
        public static final int JAMO_V_END = 4469;
        public static final int JAMO_T_BASE = 4519;
        public static final int JAMO_T_END = 4546;
        public static final int HANGUL_BASE = 44032;
        public static final int HANGUL_END = 55203;
        public static final int JAMO_L_COUNT = 19;
        public static final int JAMO_V_COUNT = 21;
        public static final int JAMO_T_COUNT = 28;
        public static final int JAMO_L_LIMIT = 4371;
        public static final int JAMO_V_LIMIT = 4470;
        public static final int JAMO_VT_COUNT = 588;
        public static final int HANGUL_COUNT = 11172;
        public static final int HANGUL_LIMIT = 55204;
        
        public static boolean isHangul(final int c) {
            return 44032 <= c && c < 55204;
        }
        
        public static boolean isHangulLV(int c) {
            c -= 44032;
            return 0 <= c && c < 11172 && c % 28 == 0;
        }
        
        public static boolean isJamoL(final int c) {
            return 4352 <= c && c < 4371;
        }
        
        public static boolean isJamoV(final int c) {
            return 4449 <= c && c < 4470;
        }
        
        public static boolean isJamoT(final int c) {
            final int t = c - 4519;
            return 0 < t && t < 28;
        }
        
        public static boolean isJamo(final int c) {
            return 4352 <= c && c <= 4546 && (c <= 4370 || (4449 <= c && c <= 4469) || 4519 < c);
        }
        
        public static int decompose(int c, final Appendable buffer) {
            try {
                c -= 44032;
                final int c2 = c % 28;
                c /= 28;
                buffer.append((char)(4352 + c / 21));
                buffer.append((char)(4449 + c % 21));
                if (c2 == 0) {
                    return 2;
                }
                buffer.append((char)(4519 + c2));
                return 3;
            }
            catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }
        
        public static void getRawDecomposition(int c, final Appendable buffer) {
            try {
                final int orig = c;
                c -= 44032;
                final int c2 = c % 28;
                if (c2 == 0) {
                    c /= 28;
                    buffer.append((char)(4352 + c / 21));
                    buffer.append((char)(4449 + c % 21));
                }
                else {
                    buffer.append((char)(orig - c2));
                    buffer.append((char)(4519 + c2));
                }
            }
            catch (IOException e) {
                throw new ICUUncheckedIOException(e);
            }
        }
    }
    
    public static final class ReorderingBuffer implements Appendable
    {
        private final Normalizer2Impl impl;
        private final Appendable app;
        private final StringBuilder str;
        private final boolean appIsStringBuilder;
        private int reorderStart;
        private int lastCC;
        private int codePointStart;
        private int codePointLimit;
        
        public ReorderingBuffer(final Normalizer2Impl ni, final Appendable dest, final int destCapacity) {
            this.impl = ni;
            this.app = dest;
            if (this.app instanceof StringBuilder) {
                this.appIsStringBuilder = true;
                (this.str = (StringBuilder)dest).ensureCapacity(destCapacity);
                this.reorderStart = 0;
                if (this.str.length() == 0) {
                    this.lastCC = 0;
                }
                else {
                    this.setIterator();
                    this.lastCC = this.previousCC();
                    if (this.lastCC > 1) {
                        while (this.previousCC() > 1) {}
                    }
                    this.reorderStart = this.codePointLimit;
                }
            }
            else {
                this.appIsStringBuilder = false;
                this.str = new StringBuilder();
                this.reorderStart = 0;
                this.lastCC = 0;
            }
        }
        
        public boolean isEmpty() {
            return this.str.length() == 0;
        }
        
        public int length() {
            return this.str.length();
        }
        
        public int getLastCC() {
            return this.lastCC;
        }
        
        public StringBuilder getStringBuilder() {
            return this.str;
        }
        
        public boolean equals(final CharSequence s, final int start, final int limit) {
            return UTF16Plus.equal(this.str, 0, this.str.length(), s, start, limit);
        }
        
        public void append(final int c, final int cc) {
            if (this.lastCC <= cc || cc == 0) {
                this.str.appendCodePoint(c);
                if ((this.lastCC = cc) <= 1) {
                    this.reorderStart = this.str.length();
                }
            }
            else {
                this.insert(c, cc);
            }
        }
        
        public void append(final CharSequence s, int start, final int limit, int leadCC, final int trailCC) {
            if (start == limit) {
                return;
            }
            if (this.lastCC <= leadCC || leadCC == 0) {
                if (trailCC <= 1) {
                    this.reorderStart = this.str.length() + (limit - start);
                }
                else if (leadCC <= 1) {
                    this.reorderStart = this.str.length() + 1;
                }
                this.str.append(s, start, limit);
                this.lastCC = trailCC;
            }
            else {
                int c = Character.codePointAt(s, start);
                start += Character.charCount(c);
                this.insert(c, leadCC);
                while (start < limit) {
                    c = Character.codePointAt(s, start);
                    start += Character.charCount(c);
                    if (start < limit) {
                        leadCC = Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(c));
                    }
                    else {
                        leadCC = trailCC;
                    }
                    this.append(c, leadCC);
                }
            }
        }
        
        @Override
        public ReorderingBuffer append(final char c) {
            this.str.append(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
            return this;
        }
        
        public void appendZeroCC(final int c) {
            this.str.appendCodePoint(c);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }
        
        @Override
        public ReorderingBuffer append(final CharSequence s) {
            if (s.length() != 0) {
                this.str.append(s);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }
        
        @Override
        public ReorderingBuffer append(final CharSequence s, final int start, final int limit) {
            if (start != limit) {
                this.str.append(s, start, limit);
                this.lastCC = 0;
                this.reorderStart = this.str.length();
            }
            return this;
        }
        
        public void flush() {
            if (this.appIsStringBuilder) {
                this.reorderStart = this.str.length();
            }
            else {
                try {
                    this.app.append(this.str);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                }
                catch (IOException e) {
                    throw new ICUUncheckedIOException(e);
                }
            }
            this.lastCC = 0;
        }
        
        public ReorderingBuffer flushAndAppendZeroCC(final CharSequence s, final int start, final int limit) {
            if (this.appIsStringBuilder) {
                this.str.append(s, start, limit);
                this.reorderStart = this.str.length();
            }
            else {
                try {
                    this.app.append(this.str).append(s, start, limit);
                    this.str.setLength(0);
                    this.reorderStart = 0;
                }
                catch (IOException e) {
                    throw new ICUUncheckedIOException(e);
                }
            }
            this.lastCC = 0;
            return this;
        }
        
        public void remove() {
            this.str.setLength(0);
            this.lastCC = 0;
            this.reorderStart = 0;
        }
        
        public void removeSuffix(final int suffixLength) {
            final int oldLength = this.str.length();
            this.str.delete(oldLength - suffixLength, oldLength);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
        }
        
        private void insert(final int c, final int cc) {
            this.setIterator();
            this.skipPrevious();
            while (this.previousCC() > cc) {}
            if (c <= 65535) {
                this.str.insert(this.codePointLimit, (char)c);
                if (cc <= 1) {
                    this.reorderStart = this.codePointLimit + 1;
                }
            }
            else {
                this.str.insert(this.codePointLimit, Character.toChars(c));
                if (cc <= 1) {
                    this.reorderStart = this.codePointLimit + 2;
                }
            }
        }
        
        private void setIterator() {
            this.codePointStart = this.str.length();
        }
        
        private void skipPrevious() {
            this.codePointLimit = this.codePointStart;
            this.codePointStart = this.str.offsetByCodePoints(this.codePointStart, -1);
        }
        
        private int previousCC() {
            this.codePointLimit = this.codePointStart;
            if (this.reorderStart >= this.codePointStart) {
                return 0;
            }
            final int c = this.str.codePointBefore(this.codePointStart);
            this.codePointStart -= Character.charCount(c);
            return this.impl.getCCFromYesOrMaybeCP(c);
        }
    }
    
    public static final class UTF16Plus
    {
        public static boolean isSurrogateLead(final int c) {
            return (c & 0x400) == 0x0;
        }
        
        public static boolean equal(final CharSequence s1, final CharSequence s2) {
            if (s1 == s2) {
                return true;
            }
            final int length = s1.length();
            if (length != s2.length()) {
                return false;
            }
            for (int i = 0; i < length; ++i) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        
        public static boolean equal(final CharSequence s1, int start1, final int limit1, final CharSequence s2, int start2, final int limit2) {
            if (limit1 - start1 != limit2 - start2) {
                return false;
            }
            if (s1 == s2 && start1 == start2) {
                return true;
            }
            while (start1 < limit1) {
                if (s1.charAt(start1++) != s2.charAt(start2++)) {
                    return false;
                }
            }
            return true;
        }
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] version) {
            return version[0] == 3;
        }
    }
}
