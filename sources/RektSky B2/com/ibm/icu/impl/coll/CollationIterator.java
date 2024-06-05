package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public abstract class CollationIterator
{
    protected static final long NO_CP_AND_CE32 = -4294967104L;
    protected final Trie2_32 trie;
    protected final CollationData data;
    private CEBuffer ceBuffer;
    private int cesIndex;
    private SkippedState skipped;
    private int numCpFwd;
    private boolean isNumeric;
    
    public CollationIterator(final CollationData d) {
        this.trie = d.trie;
        this.data = d;
        this.numCpFwd = -1;
        this.isNumeric = false;
        this.ceBuffer = null;
    }
    
    public CollationIterator(final CollationData d, final boolean numeric) {
        this.trie = d.trie;
        this.data = d;
        this.numCpFwd = -1;
        this.isNumeric = numeric;
        this.ceBuffer = new CEBuffer();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }
        if (!this.getClass().equals(other.getClass())) {
            return false;
        }
        final CollationIterator o = (CollationIterator)other;
        if (this.ceBuffer.length != o.ceBuffer.length || this.cesIndex != o.cesIndex || this.numCpFwd != o.numCpFwd || this.isNumeric != o.isNumeric) {
            return false;
        }
        for (int i = 0; i < this.ceBuffer.length; ++i) {
            if (this.ceBuffer.get(i) != o.ceBuffer.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    public abstract void resetToOffset(final int p0);
    
    public abstract int getOffset();
    
    public final long nextCE() {
        if (this.cesIndex < this.ceBuffer.length) {
            return this.ceBuffer.get(this.cesIndex++);
        }
        assert this.cesIndex == this.ceBuffer.length;
        this.ceBuffer.incLength();
        final long cAndCE32 = this.handleNextCE32();
        final int c = (int)(cAndCE32 >> 32);
        int ce32 = (int)cAndCE32;
        int t = ce32 & 0xFF;
        if (t < 192) {
            return this.ceBuffer.set(this.cesIndex++, (long)(ce32 & 0xFFFF0000) << 32 | (long)(ce32 & 0xFF00) << 16 | (long)(t << 8));
        }
        CollationData d;
        if (t == 192) {
            if (c < 0) {
                return this.ceBuffer.set(this.cesIndex++, 4311744768L);
            }
            d = this.data.base;
            ce32 = d.getCE32(c);
            t = (ce32 & 0xFF);
            if (t < 192) {
                return this.ceBuffer.set(this.cesIndex++, (long)(ce32 & 0xFFFF0000) << 32 | (long)(ce32 & 0xFF00) << 16 | (long)(t << 8));
            }
        }
        else {
            d = this.data;
        }
        if (t == 193) {
            return this.ceBuffer.set(this.cesIndex++, (long)(ce32 - t) << 32 | 0x5000500L);
        }
        return this.nextCEFromCE32(d, c, ce32);
    }
    
    public final int fetchCEs() {
        while (this.nextCE() != 4311744768L) {
            this.cesIndex = this.ceBuffer.length;
        }
        return this.ceBuffer.length;
    }
    
    final void setCurrentCE(final long ce) {
        assert this.cesIndex > 0;
        this.ceBuffer.set(this.cesIndex - 1, ce);
    }
    
    public final long previousCE(final UVector32 offsets) {
        if (this.ceBuffer.length > 0) {
            final CEBuffer ceBuffer = this.ceBuffer;
            final CEBuffer ceBuffer2 = this.ceBuffer;
            final int n = ceBuffer2.length - 1;
            ceBuffer2.length = n;
            return ceBuffer.get(n);
        }
        offsets.removeAllElements();
        final int limitOffset = this.getOffset();
        final int c = this.previousCodePoint();
        if (c < 0) {
            return 4311744768L;
        }
        if (this.data.isUnsafeBackward(c, this.isNumeric)) {
            return this.previousCEUnsafe(c, offsets);
        }
        int ce32 = this.data.getCE32(c);
        CollationData d;
        if (ce32 == 192) {
            d = this.data.base;
            ce32 = d.getCE32(c);
        }
        else {
            d = this.data;
        }
        if (Collation.isSimpleOrLongCE32(ce32)) {
            return Collation.ceFromCE32(ce32);
        }
        this.appendCEsFromCE32(d, c, ce32, false);
        if (this.ceBuffer.length > 1) {
            offsets.addElement(this.getOffset());
            while (offsets.size() <= this.ceBuffer.length) {
                offsets.addElement(limitOffset);
            }
        }
        final CEBuffer ceBuffer3 = this.ceBuffer;
        final CEBuffer ceBuffer4 = this.ceBuffer;
        final int n2 = ceBuffer4.length - 1;
        ceBuffer4.length = n2;
        return ceBuffer3.get(n2);
    }
    
    public final int getCEsLength() {
        return this.ceBuffer.length;
    }
    
    public final long getCE(final int i) {
        return this.ceBuffer.get(i);
    }
    
    public final long[] getCEs() {
        return this.ceBuffer.getCEs();
    }
    
    final void clearCEs() {
        final CEBuffer ceBuffer = this.ceBuffer;
        final int n = 0;
        ceBuffer.length = n;
        this.cesIndex = n;
    }
    
    public final void clearCEsIfNoneRemaining() {
        if (this.cesIndex == this.ceBuffer.length) {
            this.clearCEs();
        }
    }
    
    public abstract int nextCodePoint();
    
    public abstract int previousCodePoint();
    
    protected final void reset() {
        final CEBuffer ceBuffer = this.ceBuffer;
        final int n = 0;
        ceBuffer.length = n;
        this.cesIndex = n;
        if (this.skipped != null) {
            this.skipped.clear();
        }
    }
    
    protected final void reset(final boolean numeric) {
        if (this.ceBuffer == null) {
            this.ceBuffer = new CEBuffer();
        }
        this.reset();
        this.isNumeric = numeric;
    }
    
    protected long handleNextCE32() {
        final int c = this.nextCodePoint();
        if (c < 0) {
            return -4294967104L;
        }
        return this.makeCodePointAndCE32Pair(c, this.data.getCE32(c));
    }
    
    protected long makeCodePointAndCE32Pair(final int c, final int ce32) {
        return (long)c << 32 | ((long)ce32 & 0xFFFFFFFFL);
    }
    
    protected char handleGetTrailSurrogate() {
        return '\0';
    }
    
    protected boolean forbidSurrogateCodePoints() {
        return false;
    }
    
    protected abstract void forwardNumCodePoints(final int p0);
    
    protected abstract void backwardNumCodePoints(final int p0);
    
    protected int getDataCE32(final int c) {
        return this.data.getCE32(c);
    }
    
    protected int getCE32FromBuilderData(final int ce32) {
        throw new ICUException("internal program error: should be unreachable");
    }
    
    protected final void appendCEsFromCE32(CollationData d, int c, int ce32, final boolean forward) {
        while (Collation.isSpecialCE32(ce32)) {
            switch (Collation.tagFromCE32(ce32)) {
                case 0:
                case 3: {
                    throw new ICUException("internal program error: should be unreachable");
                }
                case 1: {
                    this.ceBuffer.append(Collation.ceFromLongPrimaryCE32(ce32));
                    return;
                }
                case 2: {
                    this.ceBuffer.append(Collation.ceFromLongSecondaryCE32(ce32));
                    return;
                }
                case 4: {
                    this.ceBuffer.ensureAppendCapacity(2);
                    this.ceBuffer.set(this.ceBuffer.length, Collation.latinCE0FromCE32(ce32));
                    this.ceBuffer.set(this.ceBuffer.length + 1, Collation.latinCE1FromCE32(ce32));
                    final CEBuffer ceBuffer = this.ceBuffer;
                    ceBuffer.length += 2;
                    return;
                }
                case 5: {
                    int index = Collation.indexFromCE32(ce32);
                    int length = Collation.lengthFromCE32(ce32);
                    this.ceBuffer.ensureAppendCapacity(length);
                    do {
                        this.ceBuffer.appendUnsafe(Collation.ceFromCE32(d.ce32s[index++]));
                    } while (--length > 0);
                    return;
                }
                case 6: {
                    int index = Collation.indexFromCE32(ce32);
                    int length = Collation.lengthFromCE32(ce32);
                    this.ceBuffer.ensureAppendCapacity(length);
                    do {
                        this.ceBuffer.appendUnsafe(d.ces[index++]);
                    } while (--length > 0);
                    return;
                }
                case 7: {
                    ce32 = this.getCE32FromBuilderData(ce32);
                    if (ce32 == 192) {
                        d = this.data.base;
                        ce32 = d.getCE32(c);
                        continue;
                    }
                    continue;
                }
                case 8: {
                    if (forward) {
                        this.backwardNumCodePoints(1);
                    }
                    ce32 = this.getCE32FromPrefix(d, ce32);
                    if (forward) {
                        this.forwardNumCodePoints(1);
                        continue;
                    }
                    continue;
                }
                case 9: {
                    final int index = Collation.indexFromCE32(ce32);
                    final int defaultCE32 = d.getCE32FromContexts(index);
                    if (!forward) {
                        ce32 = defaultCE32;
                        continue;
                    }
                    int nextCp;
                    if (this.skipped == null && this.numCpFwd < 0) {
                        nextCp = this.nextCodePoint();
                        if (nextCp < 0) {
                            ce32 = defaultCE32;
                            continue;
                        }
                        if ((ce32 & 0x200) != 0x0 && !CollationFCD.mayHaveLccc(nextCp)) {
                            this.backwardNumCodePoints(1);
                            ce32 = defaultCE32;
                            continue;
                        }
                    }
                    else {
                        nextCp = this.nextSkippedCodePoint();
                        if (nextCp < 0) {
                            ce32 = defaultCE32;
                            continue;
                        }
                        if ((ce32 & 0x200) != 0x0 && !CollationFCD.mayHaveLccc(nextCp)) {
                            this.backwardNumSkipped(1);
                            ce32 = defaultCE32;
                            continue;
                        }
                    }
                    ce32 = this.nextCE32FromContraction(d, ce32, d.contexts, index + 2, defaultCE32, nextCp);
                    if (ce32 == 1) {
                        return;
                    }
                    continue;
                }
                case 10: {
                    if (this.isNumeric) {
                        this.appendNumericCEs(ce32, forward);
                        return;
                    }
                    ce32 = d.ce32s[Collation.indexFromCE32(ce32)];
                    continue;
                }
                case 11: {
                    assert c == 0;
                    ce32 = d.ce32s[0];
                    continue;
                }
                case 12: {
                    final int[] jamoCE32s = d.jamoCE32s;
                    c -= 44032;
                    final int t = c % 28;
                    c /= 28;
                    final int v = c % 21;
                    c /= 21;
                    if ((ce32 & 0x100) != 0x0) {
                        this.ceBuffer.ensureAppendCapacity((t == 0) ? 2 : 3);
                        this.ceBuffer.set(this.ceBuffer.length, Collation.ceFromCE32(jamoCE32s[c]));
                        this.ceBuffer.set(this.ceBuffer.length + 1, Collation.ceFromCE32(jamoCE32s[19 + v]));
                        final CEBuffer ceBuffer2 = this.ceBuffer;
                        ceBuffer2.length += 2;
                        if (t != 0) {
                            this.ceBuffer.appendUnsafe(Collation.ceFromCE32(jamoCE32s[39 + t]));
                        }
                        return;
                    }
                    this.appendCEsFromCE32(d, -1, jamoCE32s[c], forward);
                    this.appendCEsFromCE32(d, -1, jamoCE32s[19 + v], forward);
                    if (t == 0) {
                        return;
                    }
                    ce32 = jamoCE32s[39 + t];
                    c = -1;
                    continue;
                }
                case 13: {
                    assert forward;
                    assert isLeadSurrogate(c);
                    final char trail;
                    if (!Character.isLowSurrogate(trail = this.handleGetTrailSurrogate())) {
                        ce32 = -1;
                        continue;
                    }
                    c = Character.toCodePoint((char)c, trail);
                    ce32 &= 0x300;
                    if (ce32 == 0) {
                        ce32 = -1;
                        continue;
                    }
                    if (ce32 == 256 || (ce32 = d.getCE32FromSupplementary(c)) == 192) {
                        d = d.base;
                        ce32 = d.getCE32FromSupplementary(c);
                        continue;
                    }
                    continue;
                }
                case 14: {
                    assert c >= 0;
                    this.ceBuffer.append(d.getCEFromOffsetCE32(c, ce32));
                    return;
                }
                case 15: {
                    assert c >= 0;
                    if (isSurrogate(c) && this.forbidSurrogateCodePoints()) {
                        ce32 = -195323;
                        continue;
                    }
                    this.ceBuffer.append(Collation.unassignedCEFromCodePoint(c));
                    return;
                }
            }
        }
        this.ceBuffer.append(Collation.ceFromSimpleCE32(ce32));
    }
    
    private static final boolean isSurrogate(final int c) {
        return (c & 0xFFFFF800) == 0xD800;
    }
    
    protected static final boolean isLeadSurrogate(final int c) {
        return (c & 0xFFFFFC00) == 0xD800;
    }
    
    protected static final boolean isTrailSurrogate(final int c) {
        return (c & 0xFFFFFC00) == 0xDC00;
    }
    
    private final long nextCEFromCE32(final CollationData d, final int c, final int ce32) {
        final CEBuffer ceBuffer = this.ceBuffer;
        --ceBuffer.length;
        this.appendCEsFromCE32(d, c, ce32, true);
        return this.ceBuffer.get(this.cesIndex++);
    }
    
    private final int getCE32FromPrefix(final CollationData d, int ce32) {
        int index = Collation.indexFromCE32(ce32);
        ce32 = d.getCE32FromContexts(index);
        index += 2;
        int lookBehind = 0;
        final CharsTrie prefixes = new CharsTrie(d.contexts, index);
        BytesTrie.Result match;
        do {
            final int c = this.previousCodePoint();
            if (c < 0) {
                break;
            }
            ++lookBehind;
            match = prefixes.nextForCodePoint(c);
            if (match.hasValue()) {
                ce32 = prefixes.getValue();
            }
        } while (match.hasNext());
        this.forwardNumCodePoints(lookBehind);
        return ce32;
    }
    
    private final int nextSkippedCodePoint() {
        if (this.skipped != null && this.skipped.hasNext()) {
            return this.skipped.next();
        }
        if (this.numCpFwd == 0) {
            return -1;
        }
        final int c = this.nextCodePoint();
        if (this.skipped != null && !this.skipped.isEmpty() && c >= 0) {
            this.skipped.incBeyond();
        }
        if (this.numCpFwd > 0 && c >= 0) {
            --this.numCpFwd;
        }
        return c;
    }
    
    private final void backwardNumSkipped(int n) {
        if (this.skipped != null && !this.skipped.isEmpty()) {
            n = this.skipped.backwardNumCodePoints(n);
        }
        this.backwardNumCodePoints(n);
        if (this.numCpFwd >= 0) {
            this.numCpFwd += n;
        }
    }
    
    private final int nextCE32FromContraction(final CollationData d, final int contractionCE32, final CharSequence trieChars, final int trieOffset, int ce32, int c) {
        int lookAhead = 1;
        int sinceMatch = 1;
        final CharsTrie suffixes = new CharsTrie(trieChars, trieOffset);
        if (this.skipped != null && !this.skipped.isEmpty()) {
            this.skipped.saveTrieState(suffixes);
        }
        BytesTrie.Result match = suffixes.firstForCodePoint(c);
        while (true) {
            if (match.hasValue()) {
                ce32 = suffixes.getValue();
                if (!match.hasNext() || (c = this.nextSkippedCodePoint()) < 0) {
                    return ce32;
                }
                if (this.skipped != null && !this.skipped.isEmpty()) {
                    this.skipped.saveTrieState(suffixes);
                }
                sinceMatch = 1;
            }
            else {
                final int nextCp;
                if (match == BytesTrie.Result.NO_MATCH || (nextCp = this.nextSkippedCodePoint()) < 0) {
                    if ((contractionCE32 & 0x400) != 0x0 && ((contractionCE32 & 0x100) == 0x0 || sinceMatch < lookAhead)) {
                        if (sinceMatch > 1) {
                            this.backwardNumSkipped(sinceMatch);
                            c = this.nextSkippedCodePoint();
                            lookAhead -= sinceMatch - 1;
                            sinceMatch = 1;
                        }
                        if (d.getFCD16(c) > 255) {
                            return this.nextCE32FromDiscontiguousContraction(d, suffixes, ce32, lookAhead, c);
                        }
                    }
                    this.backwardNumSkipped(sinceMatch);
                    return ce32;
                }
                c = nextCp;
                ++sinceMatch;
            }
            ++lookAhead;
            match = suffixes.nextForCodePoint(c);
        }
    }
    
    private final int nextCE32FromDiscontiguousContraction(CollationData d, final CharsTrie suffixes, int ce32, int lookAhead, int c) {
        int fcd16 = d.getFCD16(c);
        assert fcd16 > 255;
        final int nextCp = this.nextSkippedCodePoint();
        if (nextCp < 0) {
            this.backwardNumSkipped(1);
            return ce32;
        }
        ++lookAhead;
        int prevCC = fcd16 & 0xFF;
        fcd16 = d.getFCD16(nextCp);
        if (fcd16 <= 255) {
            this.backwardNumSkipped(2);
            return ce32;
        }
        if (this.skipped == null || this.skipped.isEmpty()) {
            if (this.skipped == null) {
                this.skipped = new SkippedState();
            }
            suffixes.reset();
            if (lookAhead > 2) {
                this.backwardNumCodePoints(lookAhead);
                suffixes.firstForCodePoint(this.nextCodePoint());
                for (int i = 3; i < lookAhead; ++i) {
                    suffixes.nextForCodePoint(this.nextCodePoint());
                }
                this.forwardNumCodePoints(2);
            }
            this.skipped.saveTrieState(suffixes);
        }
        else {
            this.skipped.resetToTrieState(suffixes);
        }
        this.skipped.setFirstSkipped(c);
        int sinceMatch = 2;
        c = nextCp;
        do {
            final BytesTrie.Result match;
            if (prevCC < fcd16 >> 8 && (match = suffixes.nextForCodePoint(c)).hasValue()) {
                ce32 = suffixes.getValue();
                sinceMatch = 0;
                this.skipped.recordMatch();
                if (!match.hasNext()) {
                    break;
                }
                this.skipped.saveTrieState(suffixes);
            }
            else {
                this.skipped.skip(c);
                this.skipped.resetToTrieState(suffixes);
                prevCC = (fcd16 & 0xFF);
            }
            if ((c = this.nextSkippedCodePoint()) < 0) {
                break;
            }
            ++sinceMatch;
            fcd16 = d.getFCD16(c);
        } while (fcd16 > 255);
        this.backwardNumSkipped(sinceMatch);
        final boolean isTopDiscontiguous = this.skipped.isEmpty();
        this.skipped.replaceMatch();
        if (isTopDiscontiguous && !this.skipped.isEmpty()) {
            c = -1;
            while (true) {
                this.appendCEsFromCE32(d, c, ce32, true);
                if (!this.skipped.hasNext()) {
                    break;
                }
                c = this.skipped.next();
                ce32 = this.getDataCE32(c);
                if (ce32 == 192) {
                    d = this.data.base;
                    ce32 = d.getCE32(c);
                }
                else {
                    d = this.data;
                }
            }
            this.skipped.clear();
            ce32 = 1;
        }
        return ce32;
    }
    
    private final long previousCEUnsafe(int c, final UVector32 offsets) {
        int numBackward = 1;
        while ((c = this.previousCodePoint()) >= 0) {
            ++numBackward;
            if (!this.data.isUnsafeBackward(c, this.isNumeric)) {
                break;
            }
        }
        this.numCpFwd = numBackward;
        this.cesIndex = 0;
        assert this.ceBuffer.length == 0;
        int offset = this.getOffset();
        while (this.numCpFwd > 0) {
            --this.numCpFwd;
            this.nextCE();
            assert this.ceBuffer.get(this.ceBuffer.length - 1) != 4311744768L;
            this.cesIndex = this.ceBuffer.length;
            assert offsets.size() < this.ceBuffer.length;
            offsets.addElement(offset);
            offset = this.getOffset();
            while (offsets.size() < this.ceBuffer.length) {
                offsets.addElement(offset);
            }
        }
        assert offsets.size() == this.ceBuffer.length;
        offsets.addElement(offset);
        this.numCpFwd = -1;
        this.backwardNumCodePoints(numBackward);
        this.cesIndex = 0;
        final CEBuffer ceBuffer = this.ceBuffer;
        final CEBuffer ceBuffer2 = this.ceBuffer;
        final int n = ceBuffer2.length - 1;
        ceBuffer2.length = n;
        return ceBuffer.get(n);
    }
    
    private final void appendNumericCEs(int ce32, final boolean forward) {
        final StringBuilder digits = new StringBuilder();
        if (forward) {
            while (true) {
                final char digit = Collation.digitFromCE32(ce32);
                digits.append(digit);
                if (this.numCpFwd == 0) {
                    break;
                }
                final int c = this.nextCodePoint();
                if (c < 0) {
                    break;
                }
                ce32 = this.data.getCE32(c);
                if (ce32 == 192) {
                    ce32 = this.data.base.getCE32(c);
                }
                if (!Collation.hasCE32Tag(ce32, 10)) {
                    this.backwardNumCodePoints(1);
                    break;
                }
                if (this.numCpFwd <= 0) {
                    continue;
                }
                --this.numCpFwd;
            }
        }
        else {
            while (true) {
                final char digit = Collation.digitFromCE32(ce32);
                digits.append(digit);
                final int c = this.previousCodePoint();
                if (c < 0) {
                    break;
                }
                ce32 = this.data.getCE32(c);
                if (ce32 == 192) {
                    ce32 = this.data.base.getCE32(c);
                }
                if (!Collation.hasCE32Tag(ce32, 10)) {
                    this.forwardNumCodePoints(1);
                    break;
                }
            }
            digits.reverse();
        }
        int pos = 0;
        while (true) {
            if (pos < digits.length() - 1 && digits.charAt(pos) == '\0') {
                ++pos;
            }
            else {
                int segmentLength = digits.length() - pos;
                if (segmentLength > 254) {
                    segmentLength = 254;
                }
                this.appendNumericSegmentCEs(digits.subSequence(pos, pos + segmentLength));
                pos += segmentLength;
                if (pos >= digits.length()) {
                    break;
                }
                continue;
            }
        }
    }
    
    private final void appendNumericSegmentCEs(final CharSequence digits) {
        int length = digits.length();
        assert 1 <= length && length <= 254;
        assert digits.charAt(0) != '\0';
        final long numericPrimary = this.data.numericPrimary;
        if (length <= 7) {
            int value = digits.charAt(0);
            for (int i = 1; i < length; ++i) {
                value = value * 10 + digits.charAt(i);
            }
            int firstByte = 2;
            int numBytes = 74;
            if (value < numBytes) {
                final long primary = numericPrimary | (long)(firstByte + value << 16);
                this.ceBuffer.append(Collation.makeCE(primary));
                return;
            }
            value -= numBytes;
            firstByte += numBytes;
            numBytes = 40;
            if (value < numBytes * 254) {
                final long primary = numericPrimary | (long)(firstByte + value / 254 << 16) | (long)(2 + value % 254 << 8);
                this.ceBuffer.append(Collation.makeCE(primary));
                return;
            }
            value -= numBytes * 254;
            firstByte += numBytes;
            numBytes = 16;
            if (value < numBytes * 254 * 254) {
                long primary = numericPrimary | (long)(2 + value % 254);
                value /= 254;
                primary |= 2 + value % 254 << 8;
                value /= 254;
                primary |= firstByte + value % 254 << 16;
                this.ceBuffer.append(Collation.makeCE(primary));
                return;
            }
        }
        assert length >= 7;
        final int numPairs = (length + 1) / 2;
        long primary2 = numericPrimary | (long)(128 + numPairs << 16);
        while (digits.charAt(length - 1) == '\0' && digits.charAt(length - 2) == '\0') {
            length -= 2;
        }
        int pair;
        int pos;
        if ((length & 0x1) != 0x0) {
            pair = digits.charAt(0);
            pos = 1;
        }
        else {
            pair = digits.charAt(0) * '\n' + digits.charAt(1);
            pos = 2;
        }
        pair = 11 + 2 * pair;
        int shift = 8;
        while (pos < length) {
            if (shift == 0) {
                primary2 |= pair;
                this.ceBuffer.append(Collation.makeCE(primary2));
                primary2 = numericPrimary;
                shift = 16;
            }
            else {
                primary2 |= pair << shift;
                shift -= 8;
            }
            pair = 11 + 2 * (digits.charAt(pos) * '\n' + digits.charAt(pos + 1));
            pos += 2;
        }
        primary2 |= pair - 1 << shift;
        this.ceBuffer.append(Collation.makeCE(primary2));
    }
    
    private static final class CEBuffer
    {
        private static final int INITIAL_CAPACITY = 40;
        int length;
        private long[] buffer;
        
        CEBuffer() {
            this.length = 0;
            this.buffer = new long[40];
        }
        
        void append(final long ce) {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            this.buffer[this.length++] = ce;
        }
        
        void appendUnsafe(final long ce) {
            this.buffer[this.length++] = ce;
        }
        
        void ensureAppendCapacity(final int appCap) {
            int capacity = this.buffer.length;
            if (this.length + appCap <= capacity) {
                return;
            }
            do {
                if (capacity < 1000) {
                    capacity *= 4;
                }
                else {
                    capacity *= 2;
                }
            } while (capacity < this.length + appCap);
            final long[] newBuffer = new long[capacity];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.length);
            this.buffer = newBuffer;
        }
        
        void incLength() {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            ++this.length;
        }
        
        long set(final int i, final long ce) {
            return this.buffer[i] = ce;
        }
        
        long get(final int i) {
            return this.buffer[i];
        }
        
        long[] getCEs() {
            return this.buffer;
        }
    }
    
    private static final class SkippedState
    {
        private final StringBuilder oldBuffer;
        private final StringBuilder newBuffer;
        private int pos;
        private int skipLengthAtMatch;
        private CharsTrie.State state;
        
        SkippedState() {
            this.oldBuffer = new StringBuilder();
            this.newBuffer = new StringBuilder();
            this.state = new CharsTrie.State();
        }
        
        void clear() {
            this.oldBuffer.setLength(0);
            this.pos = 0;
        }
        
        boolean isEmpty() {
            return this.oldBuffer.length() == 0;
        }
        
        boolean hasNext() {
            return this.pos < this.oldBuffer.length();
        }
        
        int next() {
            final int c = this.oldBuffer.codePointAt(this.pos);
            this.pos += Character.charCount(c);
            return c;
        }
        
        void incBeyond() {
            assert !this.hasNext();
            ++this.pos;
        }
        
        int backwardNumCodePoints(final int n) {
            final int length = this.oldBuffer.length();
            final int beyond = this.pos - length;
            if (beyond <= 0) {
                this.pos = this.oldBuffer.offsetByCodePoints(this.pos, -n);
                return 0;
            }
            if (beyond >= n) {
                this.pos -= n;
                return n;
            }
            this.pos = this.oldBuffer.offsetByCodePoints(length, beyond - n);
            return beyond;
        }
        
        void setFirstSkipped(final int c) {
            this.skipLengthAtMatch = 0;
            this.newBuffer.setLength(0);
            this.newBuffer.appendCodePoint(c);
        }
        
        void skip(final int c) {
            this.newBuffer.appendCodePoint(c);
        }
        
        void recordMatch() {
            this.skipLengthAtMatch = this.newBuffer.length();
        }
        
        void replaceMatch() {
            final int oldLength = this.oldBuffer.length();
            if (this.pos > oldLength) {
                this.pos = oldLength;
            }
            this.oldBuffer.delete(0, this.pos).insert(0, this.newBuffer, 0, this.skipLengthAtMatch);
            this.pos = 0;
        }
        
        void saveTrieState(final CharsTrie trie) {
            trie.saveState(this.state);
        }
        
        void resetToTrieState(final CharsTrie trie) {
            trie.resetToState(this.state);
        }
    }
}
