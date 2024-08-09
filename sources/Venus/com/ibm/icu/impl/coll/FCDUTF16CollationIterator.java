/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFCD;
import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.impl.coll.UTF16CollationIterator;

public final class FCDUTF16CollationIterator
extends UTF16CollationIterator {
    private CharSequence rawSeq;
    private static final int rawStart = 0;
    private int segmentStart;
    private int segmentLimit;
    private int rawLimit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int checkDir;
    static final boolean $assertionsDisabled = !FCDUTF16CollationIterator.class.desiredAssertionStatus();

    public FCDUTF16CollationIterator(CollationData collationData) {
        super(collationData);
        this.nfcImpl = collationData.nfcImpl;
    }

    public FCDUTF16CollationIterator(CollationData collationData, boolean bl, CharSequence charSequence, int n) {
        super(collationData, bl, charSequence, n);
        this.rawSeq = charSequence;
        this.segmentStart = n;
        this.rawLimit = charSequence.length();
        this.nfcImpl = collationData.nfcImpl;
        this.checkDir = 1;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CollationIterator && ((CollationIterator)this).equals(object) && object instanceof FCDUTF16CollationIterator)) {
            return true;
        }
        FCDUTF16CollationIterator fCDUTF16CollationIterator = (FCDUTF16CollationIterator)object;
        if (this.checkDir != fCDUTF16CollationIterator.checkDir) {
            return true;
        }
        if (this.checkDir == 0 && this.seq == this.rawSeq != (fCDUTF16CollationIterator.seq == fCDUTF16CollationIterator.rawSeq)) {
            return true;
        }
        if (this.checkDir != 0 || this.seq == this.rawSeq) {
            return this.pos - 0 == fCDUTF16CollationIterator.pos - 0;
        }
        return this.segmentStart - 0 == fCDUTF16CollationIterator.segmentStart - 0 && this.pos - this.start == fCDUTF16CollationIterator.pos - fCDUTF16CollationIterator.start;
    }

    @Override
    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    @Override
    public void resetToOffset(int n) {
        this.reset();
        this.seq = this.rawSeq;
        this.segmentStart = this.pos = 0 + n;
        this.start = this.pos;
        this.limit = this.rawLimit;
        this.checkDir = 1;
    }

    @Override
    public int getOffset() {
        if (this.checkDir != 0 || this.seq == this.rawSeq) {
            return this.pos - 0;
        }
        if (this.pos == this.start) {
            return this.segmentStart - 0;
        }
        return this.segmentLimit - 0;
    }

    @Override
    public void setText(boolean bl, CharSequence charSequence, int n) {
        super.setText(bl, charSequence, n);
        this.rawSeq = charSequence;
        this.segmentStart = n;
        this.rawLimit = this.limit = charSequence.length();
        this.checkDir = 1;
    }

    @Override
    public int nextCodePoint() {
        char c;
        char c2;
        while (true) {
            if (this.checkDir > 0) {
                if (this.pos == this.limit) {
                    return 1;
                }
                if (!CollationFCD.hasTccc(c2 = this.seq.charAt(this.pos++)) || !CollationFCD.maybeTibetanCompositeVowel(c2) && (this.pos == this.limit || !CollationFCD.hasLccc(this.seq.charAt(this.pos)))) break;
                --this.pos;
                this.nextSegment();
                c2 = this.seq.charAt(this.pos++);
                break;
            }
            if (this.checkDir == 0 && this.pos != this.limit) {
                c2 = this.seq.charAt(this.pos++);
                break;
            }
            this.switchToForward();
        }
        if (Character.isHighSurrogate(c2) && this.pos != this.limit && Character.isLowSurrogate(c = this.seq.charAt(this.pos))) {
            ++this.pos;
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    @Override
    public int previousCodePoint() {
        char c;
        char c2;
        while (true) {
            if (this.checkDir < 0) {
                if (this.pos == this.start) {
                    return 1;
                }
                if (!CollationFCD.hasLccc(c2 = this.seq.charAt(--this.pos)) || !CollationFCD.maybeTibetanCompositeVowel(c2) && (this.pos == this.start || !CollationFCD.hasTccc(this.seq.charAt(this.pos - 1)))) break;
                ++this.pos;
                this.previousSegment();
                c2 = this.seq.charAt(--this.pos);
                break;
            }
            if (this.checkDir == 0 && this.pos != this.start) {
                c2 = this.seq.charAt(--this.pos);
                break;
            }
            this.switchToBackward();
        }
        if (Character.isLowSurrogate(c2) && this.pos != this.start && Character.isHighSurrogate(c = this.seq.charAt(this.pos - 1))) {
            --this.pos;
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    @Override
    protected long handleNextCE32() {
        char c;
        while (true) {
            if (this.checkDir > 0) {
                if (this.pos == this.limit) {
                    return -4294967104L;
                }
                if (!CollationFCD.hasTccc(c = this.seq.charAt(this.pos++)) || !CollationFCD.maybeTibetanCompositeVowel(c) && (this.pos == this.limit || !CollationFCD.hasLccc(this.seq.charAt(this.pos)))) break;
                --this.pos;
                this.nextSegment();
                c = this.seq.charAt(this.pos++);
                break;
            }
            if (this.checkDir == 0 && this.pos != this.limit) {
                c = this.seq.charAt(this.pos++);
                break;
            }
            this.switchToForward();
        }
        return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        while (n > 0 && this.nextCodePoint() >= 0) {
            --n;
        }
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        while (n > 0 && this.previousCodePoint() >= 0) {
            --n;
        }
    }

    private void switchToForward() {
        if (!($assertionsDisabled || this.checkDir < 0 && this.seq == this.rawSeq || this.checkDir == 0 && this.pos == this.limit)) {
            throw new AssertionError();
        }
        if (this.checkDir < 0) {
            this.start = this.segmentStart = this.pos;
            if (this.pos == this.segmentLimit) {
                this.limit = this.rawLimit;
                this.checkDir = 1;
            } else {
                this.checkDir = 0;
            }
        } else {
            if (this.seq != this.rawSeq) {
                this.seq = this.rawSeq;
                this.start = this.segmentStart = this.segmentLimit;
                this.pos = this.segmentStart;
            }
            this.limit = this.rawLimit;
            this.checkDir = 1;
        }
    }

    private void nextSegment() {
        block6: {
            if (!($assertionsDisabled || this.checkDir > 0 && this.seq == this.rawSeq && this.pos != this.limit)) {
                throw new AssertionError();
            }
            int n = this.pos;
            int n2 = 0;
            do {
                int n3 = n;
                int n4 = Character.codePointAt(this.seq, n);
                n += Character.charCount(n4);
                int n5 = this.nfcImpl.getFCD16(n4);
                int n6 = n5 >> 8;
                if (n6 == 0 && n3 != this.pos) {
                    this.limit = this.segmentLimit = n3;
                    break block6;
                }
                if (n6 != 0 && (n2 > n6 || CollationFCD.isFCD16OfTibetanCompositeVowel(n5))) {
                    do {
                        n3 = n;
                        if (n == this.rawLimit) break;
                        n4 = Character.codePointAt(this.seq, n);
                        n += Character.charCount(n4);
                    } while (this.nfcImpl.getFCD16(n4) > 255);
                    this.normalize(this.pos, n3);
                    this.pos = this.start;
                    break block6;
                }
                n2 = n5 & 0xFF;
            } while (n != this.rawLimit && n2 != 0);
            this.limit = this.segmentLimit = n;
        }
        if (!$assertionsDisabled && this.pos == this.limit) {
            throw new AssertionError();
        }
        this.checkDir = 0;
    }

    private void switchToBackward() {
        if (!($assertionsDisabled || this.checkDir > 0 && this.seq == this.rawSeq || this.checkDir == 0 && this.pos == this.start)) {
            throw new AssertionError();
        }
        if (this.checkDir > 0) {
            this.limit = this.segmentLimit = this.pos;
            if (this.pos == this.segmentStart) {
                this.start = 0;
                this.checkDir = -1;
            } else {
                this.checkDir = 0;
            }
        } else {
            if (this.seq != this.rawSeq) {
                this.seq = this.rawSeq;
                this.limit = this.segmentLimit = this.segmentStart;
                this.pos = this.segmentLimit;
            }
            this.start = 0;
            this.checkDir = -1;
        }
    }

    private void previousSegment() {
        block6: {
            if (!($assertionsDisabled || this.checkDir < 0 && this.seq == this.rawSeq && this.pos != this.start)) {
                throw new AssertionError();
            }
            int n = this.pos;
            int n2 = 0;
            do {
                int n3 = n;
                int n4 = Character.codePointBefore(this.seq, n);
                n -= Character.charCount(n4);
                int n5 = this.nfcImpl.getFCD16(n4);
                int n6 = n5 & 0xFF;
                if (n6 == 0 && n3 != this.pos) {
                    this.start = this.segmentStart = n3;
                    break block6;
                }
                if (n6 != 0 && (n2 != 0 && n6 > n2 || CollationFCD.isFCD16OfTibetanCompositeVowel(n5))) {
                    do {
                        n3 = n;
                        if (n5 <= 255 || n == 0) break;
                        n4 = Character.codePointBefore(this.seq, n);
                        n -= Character.charCount(n4);
                    } while ((n5 = this.nfcImpl.getFCD16(n4)) != 0);
                    this.normalize(n3, this.pos);
                    this.pos = this.limit;
                    break block6;
                }
                n2 = n5 >> 8;
            } while (n != 0 && n2 != 0);
            this.start = this.segmentStart = n;
        }
        if (!$assertionsDisabled && this.pos == this.start) {
            throw new AssertionError();
        }
        this.checkDir = 0;
    }

    private void normalize(int n, int n2) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(this.rawSeq, n, n2, this.normalized, n2 - n);
        this.segmentStart = n;
        this.segmentLimit = n2;
        this.seq = this.normalized;
        this.start = 0;
        this.limit = this.start + this.normalized.length();
    }
}

