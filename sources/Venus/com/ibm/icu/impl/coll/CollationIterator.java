/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFCD;
import com.ibm.icu.impl.coll.UVector32;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.ICUException;

public abstract class CollationIterator {
    protected static final long NO_CP_AND_CE32 = -4294967104L;
    protected final Trie2_32 trie;
    protected final CollationData data;
    private CEBuffer ceBuffer;
    private int cesIndex;
    private SkippedState skipped;
    private int numCpFwd;
    private boolean isNumeric;
    static final boolean $assertionsDisabled = !CollationIterator.class.desiredAssertionStatus();

    public CollationIterator(CollationData collationData) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = false;
        this.ceBuffer = null;
    }

    public CollationIterator(CollationData collationData, boolean bl) {
        this.trie = collationData.trie;
        this.data = collationData;
        this.numCpFwd = -1;
        this.isNumeric = bl;
        this.ceBuffer = new CEBuffer();
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (!this.getClass().equals(object.getClass())) {
            return true;
        }
        CollationIterator collationIterator = (CollationIterator)object;
        if (this.ceBuffer.length != collationIterator.ceBuffer.length || this.cesIndex != collationIterator.cesIndex || this.numCpFwd != collationIterator.numCpFwd || this.isNumeric != collationIterator.isNumeric) {
            return true;
        }
        for (int i = 0; i < this.ceBuffer.length; ++i) {
            if (this.ceBuffer.get(i) == collationIterator.ceBuffer.get(i)) continue;
            return true;
        }
        return false;
    }

    public int hashCode() {
        return 1;
    }

    public abstract void resetToOffset(int var1);

    public abstract int getOffset();

    public final long nextCE() {
        CollationData collationData;
        if (this.cesIndex < this.ceBuffer.length) {
            return this.ceBuffer.get(this.cesIndex++);
        }
        if (!$assertionsDisabled && this.cesIndex != this.ceBuffer.length) {
            throw new AssertionError();
        }
        this.ceBuffer.incLength();
        long l = this.handleNextCE32();
        int n = (int)(l >> 32);
        int n2 = (int)l;
        int n3 = n2 & 0xFF;
        if (n3 < 192) {
            return this.ceBuffer.set(this.cesIndex++, (long)(n2 & 0xFFFF0000) << 32 | (long)(n2 & 0xFF00) << 16 | (long)(n3 << 8));
        }
        if (n3 == 192) {
            if (n < 0) {
                return this.ceBuffer.set(this.cesIndex++, 0x101000100L);
            }
            collationData = this.data.base;
            n2 = collationData.getCE32(n);
            n3 = n2 & 0xFF;
            if (n3 < 192) {
                return this.ceBuffer.set(this.cesIndex++, (long)(n2 & 0xFFFF0000) << 32 | (long)(n2 & 0xFF00) << 16 | (long)(n3 << 8));
            }
        } else {
            collationData = this.data;
        }
        if (n3 == 193) {
            return this.ceBuffer.set(this.cesIndex++, (long)(n2 - n3) << 32 | 0x5000500L);
        }
        return this.nextCEFromCE32(collationData, n, n2);
    }

    public final int fetchCEs() {
        while (this.nextCE() != 0x101000100L) {
            this.cesIndex = this.ceBuffer.length;
        }
        return this.ceBuffer.length;
    }

    final void setCurrentCE(long l) {
        if (!$assertionsDisabled && this.cesIndex <= 0) {
            throw new AssertionError();
        }
        this.ceBuffer.set(this.cesIndex - 1, l);
    }

    public final long previousCE(UVector32 uVector32) {
        CollationData collationData;
        if (this.ceBuffer.length > 0) {
            return this.ceBuffer.get(--this.ceBuffer.length);
        }
        uVector32.removeAllElements();
        int n = this.getOffset();
        int n2 = this.previousCodePoint();
        if (n2 < 0) {
            return 0x101000100L;
        }
        if (this.data.isUnsafeBackward(n2, this.isNumeric)) {
            return this.previousCEUnsafe(n2, uVector32);
        }
        int n3 = this.data.getCE32(n2);
        if (n3 == 192) {
            collationData = this.data.base;
            n3 = collationData.getCE32(n2);
        } else {
            collationData = this.data;
        }
        if (Collation.isSimpleOrLongCE32(n3)) {
            return Collation.ceFromCE32(n3);
        }
        this.appendCEsFromCE32(collationData, n2, n3, true);
        if (this.ceBuffer.length > 1) {
            uVector32.addElement(this.getOffset());
            while (uVector32.size() <= this.ceBuffer.length) {
                uVector32.addElement(n);
            }
        }
        return this.ceBuffer.get(--this.ceBuffer.length);
    }

    public final int getCEsLength() {
        return this.ceBuffer.length;
    }

    public final long getCE(int n) {
        return this.ceBuffer.get(n);
    }

    public final long[] getCEs() {
        return this.ceBuffer.getCEs();
    }

    final void clearCEs() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
    }

    public final void clearCEsIfNoneRemaining() {
        if (this.cesIndex == this.ceBuffer.length) {
            this.clearCEs();
        }
    }

    public abstract int nextCodePoint();

    public abstract int previousCodePoint();

    protected final void reset() {
        this.ceBuffer.length = 0;
        this.cesIndex = 0;
        if (this.skipped != null) {
            this.skipped.clear();
        }
    }

    protected final void reset(boolean bl) {
        if (this.ceBuffer == null) {
            this.ceBuffer = new CEBuffer();
        }
        this.reset();
        this.isNumeric = bl;
    }

    protected long handleNextCE32() {
        int n = this.nextCodePoint();
        if (n < 0) {
            return -4294967104L;
        }
        return this.makeCodePointAndCE32Pair(n, this.data.getCE32(n));
    }

    protected long makeCodePointAndCE32Pair(int n, int n2) {
        return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
    }

    protected char handleGetTrailSurrogate() {
        return '\u0001';
    }

    protected boolean forbidSurrogateCodePoints() {
        return true;
    }

    protected abstract void forwardNumCodePoints(int var1);

    protected abstract void backwardNumCodePoints(int var1);

    protected int getDataCE32(int n) {
        return this.data.getCE32(n);
    }

    protected int getCE32FromBuilderData(int n) {
        throw new ICUException("internal program error: should be unreachable");
    }

    protected final void appendCEsFromCE32(CollationData collationData, int n, int n2, boolean bl) {
        while (Collation.isSpecialCE32(n2)) {
            switch (Collation.tagFromCE32(n2)) {
                case 0: 
                case 3: {
                    throw new ICUException("internal program error: should be unreachable");
                }
                case 1: {
                    this.ceBuffer.append(Collation.ceFromLongPrimaryCE32(n2));
                    return;
                }
                case 2: {
                    this.ceBuffer.append(Collation.ceFromLongSecondaryCE32(n2));
                    return;
                }
                case 4: {
                    this.ceBuffer.ensureAppendCapacity(2);
                    this.ceBuffer.set(this.ceBuffer.length, Collation.latinCE0FromCE32(n2));
                    this.ceBuffer.set(this.ceBuffer.length + 1, Collation.latinCE1FromCE32(n2));
                    this.ceBuffer.length += 2;
                    return;
                }
                case 5: {
                    int n3 = Collation.indexFromCE32(n2);
                    int n4 = Collation.lengthFromCE32(n2);
                    this.ceBuffer.ensureAppendCapacity(n4);
                    do {
                        this.ceBuffer.appendUnsafe(Collation.ceFromCE32(collationData.ce32s[n3++]));
                    } while (--n4 > 0);
                    return;
                }
                case 6: {
                    int n5 = Collation.indexFromCE32(n2);
                    int n4 = Collation.lengthFromCE32(n2);
                    this.ceBuffer.ensureAppendCapacity(n4);
                    do {
                        this.ceBuffer.appendUnsafe(collationData.ces[n5++]);
                    } while (--n4 > 0);
                    return;
                }
                case 7: {
                    n2 = this.getCE32FromBuilderData(n2);
                    if (n2 != 192) break;
                    collationData = this.data.base;
                    n2 = collationData.getCE32(n);
                    break;
                }
                case 8: {
                    if (bl) {
                        this.backwardNumCodePoints(1);
                    }
                    n2 = this.getCE32FromPrefix(collationData, n2);
                    if (!bl) break;
                    this.forwardNumCodePoints(1);
                    break;
                }
                case 9: {
                    int n6;
                    int n7 = Collation.indexFromCE32(n2);
                    int n4 = collationData.getCE32FromContexts(n7);
                    if (!bl) {
                        n2 = n4;
                        break;
                    }
                    if (this.skipped == null && this.numCpFwd < 0) {
                        n6 = this.nextCodePoint();
                        if (n6 < 0) {
                            n2 = n4;
                            break;
                        }
                        if ((n2 & 0x200) != 0 && !CollationFCD.mayHaveLccc(n6)) {
                            this.backwardNumCodePoints(1);
                            n2 = n4;
                            break;
                        }
                    } else {
                        n6 = this.nextSkippedCodePoint();
                        if (n6 < 0) {
                            n2 = n4;
                            break;
                        }
                        if ((n2 & 0x200) != 0 && !CollationFCD.mayHaveLccc(n6)) {
                            this.backwardNumSkipped(1);
                            n2 = n4;
                            break;
                        }
                    }
                    if ((n2 = this.nextCE32FromContraction(collationData, n2, collationData.contexts, n7 + 2, n4, n6)) != 1) break;
                    return;
                }
                case 10: {
                    if (this.isNumeric) {
                        this.appendNumericCEs(n2, bl);
                        return;
                    }
                    n2 = collationData.ce32s[Collation.indexFromCE32(n2)];
                    break;
                }
                case 11: {
                    if (!$assertionsDisabled && n != 0) {
                        throw new AssertionError();
                    }
                    n2 = collationData.ce32s[0];
                    break;
                }
                case 12: {
                    int[] nArray = collationData.jamoCE32s;
                    int n4 = (n -= 44032) % 28;
                    int n6 = (n /= 28) % 21;
                    n /= 21;
                    if ((n2 & 0x100) != 0) {
                        this.ceBuffer.ensureAppendCapacity(n4 == 0 ? 2 : 3);
                        this.ceBuffer.set(this.ceBuffer.length, Collation.ceFromCE32(nArray[n]));
                        this.ceBuffer.set(this.ceBuffer.length + 1, Collation.ceFromCE32(nArray[19 + n6]));
                        this.ceBuffer.length += 2;
                        if (n4 != 0) {
                            this.ceBuffer.appendUnsafe(Collation.ceFromCE32(nArray[39 + n4]));
                        }
                        return;
                    }
                    this.appendCEsFromCE32(collationData, -1, nArray[n], bl);
                    this.appendCEsFromCE32(collationData, -1, nArray[19 + n6], bl);
                    if (n4 == 0) {
                        return;
                    }
                    n2 = nArray[39 + n4];
                    n = -1;
                    break;
                }
                case 13: {
                    if (!$assertionsDisabled && !bl) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && !CollationIterator.isLeadSurrogate(n)) {
                        throw new AssertionError();
                    }
                    char c = this.handleGetTrailSurrogate();
                    if (Character.isLowSurrogate(c)) {
                        n = Character.toCodePoint((char)n, c);
                        if ((n2 &= 0x300) == 0) {
                            n2 = -1;
                            break;
                        }
                        if (n2 != 256 && (n2 = collationData.getCE32FromSupplementary(n)) != 192) break;
                        collationData = collationData.base;
                        n2 = collationData.getCE32FromSupplementary(n);
                        break;
                    }
                    n2 = -1;
                    break;
                }
                case 14: {
                    if (!$assertionsDisabled && n < 0) {
                        throw new AssertionError();
                    }
                    this.ceBuffer.append(collationData.getCEFromOffsetCE32(n, n2));
                    return;
                }
                case 15: {
                    if (!$assertionsDisabled && n < 0) {
                        throw new AssertionError();
                    }
                    if (CollationIterator.isSurrogate(n) && this.forbidSurrogateCodePoints()) {
                        n2 = -195323;
                        break;
                    }
                    this.ceBuffer.append(Collation.unassignedCEFromCodePoint(n));
                    return;
                }
            }
        }
        this.ceBuffer.append(Collation.ceFromSimpleCE32(n2));
    }

    private static final boolean isSurrogate(int n) {
        return (n & 0xFFFFF800) == 55296;
    }

    protected static final boolean isLeadSurrogate(int n) {
        return (n & 0xFFFFFC00) == 55296;
    }

    protected static final boolean isTrailSurrogate(int n) {
        return (n & 0xFFFFFC00) == 56320;
    }

    private final long nextCEFromCE32(CollationData collationData, int n, int n2) {
        --this.ceBuffer.length;
        this.appendCEsFromCE32(collationData, n, n2, false);
        return this.ceBuffer.get(this.cesIndex++);
    }

    private final int getCE32FromPrefix(CollationData collationData, int n) {
        int n2;
        int n3 = Collation.indexFromCE32(n);
        n = collationData.getCE32FromContexts(n3);
        int n4 = 0;
        CharsTrie charsTrie = new CharsTrie(collationData.contexts, n3 += 2);
        while ((n2 = this.previousCodePoint()) >= 0) {
            ++n4;
            BytesTrie.Result result = charsTrie.nextForCodePoint(n2);
            if (result.hasValue()) {
                n = charsTrie.getValue();
            }
            if (result.hasNext()) continue;
            break;
        }
        this.forwardNumCodePoints(n4);
        return n;
    }

    private final int nextSkippedCodePoint() {
        if (this.skipped != null && this.skipped.hasNext()) {
            return this.skipped.next();
        }
        if (this.numCpFwd == 0) {
            return 1;
        }
        int n = this.nextCodePoint();
        if (this.skipped != null && !this.skipped.isEmpty() && n >= 0) {
            this.skipped.incBeyond();
        }
        if (this.numCpFwd > 0 && n >= 0) {
            --this.numCpFwd;
        }
        return n;
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

    private final int nextCE32FromContraction(CollationData collationData, int n, CharSequence charSequence, int n2, int n3, int n4) {
        int n5 = 1;
        int n6 = 1;
        CharsTrie charsTrie = new CharsTrie(charSequence, n2);
        if (this.skipped != null && !this.skipped.isEmpty()) {
            this.skipped.saveTrieState(charsTrie);
        }
        BytesTrie.Result result = charsTrie.firstForCodePoint(n4);
        while (true) {
            if (result.hasValue()) {
                n3 = charsTrie.getValue();
                if (!result.hasNext() || (n4 = this.nextSkippedCodePoint()) < 0) {
                    return n3;
                }
                if (this.skipped != null && !this.skipped.isEmpty()) {
                    this.skipped.saveTrieState(charsTrie);
                }
                n6 = 1;
            } else {
                int n7;
                if (result == BytesTrie.Result.NO_MATCH || (n7 = this.nextSkippedCodePoint()) < 0) {
                    if ((n & 0x400) == 0 || (n & 0x100) != 0 && n6 >= n5) break;
                    if (n6 > 1) {
                        this.backwardNumSkipped(n6);
                        n4 = this.nextSkippedCodePoint();
                        n5 -= n6 - 1;
                        n6 = 1;
                    }
                    if (collationData.getFCD16(n4) <= 255) break;
                    return this.nextCE32FromDiscontiguousContraction(collationData, charsTrie, n3, n5, n4);
                }
                n4 = n7;
                ++n6;
            }
            ++n5;
            result = charsTrie.nextForCodePoint(n4);
        }
        this.backwardNumSkipped(n6);
        return n3;
    }

    private final int nextCE32FromDiscontiguousContraction(CollationData collationData, CharsTrie charsTrie, int n, int n2, int n3) {
        int n4;
        int n5 = collationData.getFCD16(n3);
        if (!$assertionsDisabled && n5 <= 255) {
            throw new AssertionError();
        }
        int n6 = this.nextSkippedCodePoint();
        if (n6 < 0) {
            this.backwardNumSkipped(1);
            return n;
        }
        ++n2;
        int n7 = n5 & 0xFF;
        n5 = collationData.getFCD16(n6);
        if (n5 <= 255) {
            this.backwardNumSkipped(2);
            return n;
        }
        if (this.skipped == null || this.skipped.isEmpty()) {
            if (this.skipped == null) {
                this.skipped = new SkippedState();
            }
            charsTrie.reset();
            if (n2 > 2) {
                this.backwardNumCodePoints(n2);
                charsTrie.firstForCodePoint(this.nextCodePoint());
                for (n4 = 3; n4 < n2; ++n4) {
                    charsTrie.nextForCodePoint(this.nextCodePoint());
                }
                this.forwardNumCodePoints(2);
            }
            this.skipped.saveTrieState(charsTrie);
        } else {
            this.skipped.resetToTrieState(charsTrie);
        }
        this.skipped.setFirstSkipped(n3);
        n4 = 2;
        n3 = n6;
        do {
            BytesTrie.Result result;
            if (n7 < n5 >> 8 && (result = charsTrie.nextForCodePoint(n3)).hasValue()) {
                n = charsTrie.getValue();
                n4 = 0;
                this.skipped.recordMatch();
                if (!result.hasNext()) break;
                this.skipped.saveTrieState(charsTrie);
            } else {
                this.skipped.skip(n3);
                this.skipped.resetToTrieState(charsTrie);
                n7 = n5 & 0xFF;
            }
            n3 = this.nextSkippedCodePoint();
            if (n3 < 0) break;
            ++n4;
        } while ((n5 = collationData.getFCD16(n3)) > 255);
        this.backwardNumSkipped(n4);
        boolean bl = this.skipped.isEmpty();
        this.skipped.replaceMatch();
        if (bl && !this.skipped.isEmpty()) {
            n3 = -1;
            while (true) {
                this.appendCEsFromCE32(collationData, n3, n, false);
                if (!this.skipped.hasNext()) break;
                n3 = this.skipped.next();
                n = this.getDataCE32(n3);
                if (n == 192) {
                    collationData = this.data.base;
                    n = collationData.getCE32(n3);
                    continue;
                }
                collationData = this.data;
            }
            this.skipped.clear();
            n = 1;
        }
        return n;
    }

    private final long previousCEUnsafe(int n, UVector32 uVector32) {
        int n2 = 1;
        while ((n = this.previousCodePoint()) >= 0) {
            ++n2;
            if (this.data.isUnsafeBackward(n, this.isNumeric)) continue;
        }
        this.numCpFwd = n2;
        this.cesIndex = 0;
        if (!$assertionsDisabled && this.ceBuffer.length != 0) {
            throw new AssertionError();
        }
        int n3 = this.getOffset();
        while (this.numCpFwd > 0) {
            --this.numCpFwd;
            this.nextCE();
            if (!$assertionsDisabled && this.ceBuffer.get(this.ceBuffer.length - 1) == 0x101000100L) {
                throw new AssertionError();
            }
            this.cesIndex = this.ceBuffer.length;
            if (!$assertionsDisabled && uVector32.size() >= this.ceBuffer.length) {
                throw new AssertionError();
            }
            uVector32.addElement(n3);
            n3 = this.getOffset();
            while (uVector32.size() < this.ceBuffer.length) {
                uVector32.addElement(n3);
            }
        }
        if (!$assertionsDisabled && uVector32.size() != this.ceBuffer.length) {
            throw new AssertionError();
        }
        uVector32.addElement(n3);
        this.numCpFwd = -1;
        this.backwardNumCodePoints(n2);
        this.cesIndex = 0;
        return this.ceBuffer.get(--this.ceBuffer.length);
    }

    private final void appendNumericCEs(int n, boolean bl) {
        int n2;
        int n3;
        StringBuilder stringBuilder;
        block8: {
            block9: {
                stringBuilder = new StringBuilder();
                if (bl) {
                    while (true) {
                        n3 = Collation.digitFromCE32(n);
                        stringBuilder.append((char)n3);
                        if (this.numCpFwd == 0 || (n2 = this.nextCodePoint()) < 0) break block8;
                        n = this.data.getCE32(n2);
                        if (n == 192) {
                            n = this.data.base.getCE32(n2);
                        }
                        if (!Collation.hasCE32Tag(n, 10)) {
                            this.backwardNumCodePoints(1);
                            break block8;
                        }
                        if (this.numCpFwd <= 0) continue;
                        --this.numCpFwd;
                    }
                }
                do {
                    n3 = Collation.digitFromCE32(n);
                    stringBuilder.append((char)n3);
                    n2 = this.previousCodePoint();
                    if (n2 < 0) break block9;
                    n = this.data.getCE32(n2);
                    if (n != 192) continue;
                    n = this.data.base.getCE32(n2);
                } while (Collation.hasCE32Tag(n, 10));
                this.forwardNumCodePoints(1);
            }
            stringBuilder.reverse();
        }
        n3 = 0;
        while (true) {
            if (n3 < stringBuilder.length() - 1 && stringBuilder.charAt(n3) == '\u0000') {
                ++n3;
                continue;
            }
            n2 = stringBuilder.length() - n3;
            if (n2 > 254) {
                n2 = 254;
            }
            this.appendNumericSegmentCEs(stringBuilder.subSequence(n3, n3 + n2));
            if ((n3 += n2) >= stringBuilder.length()) break;
        }
    }

    private final void appendNumericSegmentCEs(CharSequence charSequence) {
        int n;
        int n2;
        int n3;
        int n4 = charSequence.length();
        if (!($assertionsDisabled || 1 <= n4 && n4 <= 254)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n4 != 1 && charSequence.charAt(0) == '\u0000') {
            throw new AssertionError();
        }
        long l = this.data.numericPrimary;
        if (n4 <= 7) {
            int n5;
            n3 = charSequence.charAt(0);
            for (n5 = 1; n5 < n4; ++n5) {
                n3 = n3 * 10 + charSequence.charAt(n5);
            }
            n5 = 2;
            int n6 = 74;
            if (n3 < n6) {
                long l2 = l | (long)(n5 + n3 << 16);
                this.ceBuffer.append(Collation.makeCE(l2));
                return;
            }
            n3 -= n6;
            n5 += n6;
            n6 = 40;
            if (n3 < n6 * 254) {
                long l3 = l | (long)(n5 + n3 / 254 << 16) | (long)(2 + n3 % 254 << 8);
                this.ceBuffer.append(Collation.makeCE(l3));
                return;
            }
            n3 -= n6 * 254;
            n5 += n6;
            n6 = 16;
            if (n3 < n6 * 254 * 254) {
                long l4 = l | (long)(2 + n3 % 254);
                l4 |= (long)(2 + (n3 /= 254) % 254 << 8);
                this.ceBuffer.append(Collation.makeCE(l4 |= (long)(n5 + (n3 /= 254) % 254 << 16)));
                return;
            }
        }
        if (!$assertionsDisabled && n4 < 7) {
            throw new AssertionError();
        }
        n3 = (n4 + 1) / 2;
        long l5 = l | (long)(128 + n3 << 16);
        while (charSequence.charAt(n4 - 1) == '\u0000' && charSequence.charAt(n4 - 2) == '\u0000') {
            n4 -= 2;
        }
        if ((n4 & 1) != 0) {
            n2 = charSequence.charAt(0);
            n = 1;
        } else {
            n2 = charSequence.charAt(0) * 10 + charSequence.charAt(1);
            n = 2;
        }
        n2 = 11 + 2 * n2;
        int n7 = 8;
        while (n < n4) {
            if (n7 == 0) {
                this.ceBuffer.append(Collation.makeCE(l5 |= (long)n2));
                l5 = l;
                n7 = 16;
            } else {
                l5 |= (long)(n2 << n7);
                n7 -= 8;
            }
            n2 = 11 + 2 * (charSequence.charAt(n) * 10 + charSequence.charAt(n + 1));
            n += 2;
        }
        this.ceBuffer.append(Collation.makeCE(l5 |= (long)(n2 - 1 << n7)));
    }

    private static final class SkippedState {
        private final StringBuilder oldBuffer = new StringBuilder();
        private final StringBuilder newBuffer = new StringBuilder();
        private int pos;
        private int skipLengthAtMatch;
        private CharsTrie.State state = new CharsTrie.State();
        static final boolean $assertionsDisabled = !CollationIterator.class.desiredAssertionStatus();

        SkippedState() {
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
            int n = this.oldBuffer.codePointAt(this.pos);
            this.pos += Character.charCount(n);
            return n;
        }

        void incBeyond() {
            if (!$assertionsDisabled && this.hasNext()) {
                throw new AssertionError();
            }
            ++this.pos;
        }

        int backwardNumCodePoints(int n) {
            int n2 = this.oldBuffer.length();
            int n3 = this.pos - n2;
            if (n3 > 0) {
                if (n3 >= n) {
                    this.pos -= n;
                    return n;
                }
                this.pos = this.oldBuffer.offsetByCodePoints(n2, n3 - n);
                return n3;
            }
            this.pos = this.oldBuffer.offsetByCodePoints(this.pos, -n);
            return 1;
        }

        void setFirstSkipped(int n) {
            this.skipLengthAtMatch = 0;
            this.newBuffer.setLength(0);
            this.newBuffer.appendCodePoint(n);
        }

        void skip(int n) {
            this.newBuffer.appendCodePoint(n);
        }

        void recordMatch() {
            this.skipLengthAtMatch = this.newBuffer.length();
        }

        void replaceMatch() {
            int n = this.oldBuffer.length();
            if (this.pos > n) {
                this.pos = n;
            }
            this.oldBuffer.delete(0, this.pos).insert(0, this.newBuffer, 0, this.skipLengthAtMatch);
            this.pos = 0;
        }

        void saveTrieState(CharsTrie charsTrie) {
            charsTrie.saveState(this.state);
        }

        void resetToTrieState(CharsTrie charsTrie) {
            charsTrie.resetToState(this.state);
        }
    }

    private static final class CEBuffer {
        private static final int INITIAL_CAPACITY = 40;
        int length = 0;
        private long[] buffer = new long[40];

        CEBuffer() {
        }

        void append(long l) {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            this.buffer[this.length++] = l;
        }

        void appendUnsafe(long l) {
            this.buffer[this.length++] = l;
        }

        void ensureAppendCapacity(int n) {
            int n2 = this.buffer.length;
            if (this.length + n <= n2) {
                return;
            }
            do {
                if (n2 < 1000) {
                    n2 *= 4;
                    continue;
                }
                n2 *= 2;
            } while (n2 < this.length + n);
            long[] lArray = new long[n2];
            System.arraycopy(this.buffer, 0, lArray, 0, this.length);
            this.buffer = lArray;
        }

        void incLength() {
            if (this.length >= 40) {
                this.ensureAppendCapacity(1);
            }
            ++this.length;
        }

        long set(int n, long l) {
            this.buffer[n] = l;
            return this.buffer[n];
        }

        long get(int n) {
            return this.buffer[n];
        }

        long[] getCEs() {
            return this.buffer;
        }
    }
}

