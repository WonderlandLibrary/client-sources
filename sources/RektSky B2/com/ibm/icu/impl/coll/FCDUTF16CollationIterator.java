package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;

public final class FCDUTF16CollationIterator extends UTF16CollationIterator
{
    private CharSequence rawSeq;
    private static final int rawStart = 0;
    private int segmentStart;
    private int segmentLimit;
    private int rawLimit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder normalized;
    private int checkDir;
    
    public FCDUTF16CollationIterator(final CollationData d) {
        super(d);
        this.nfcImpl = d.nfcImpl;
    }
    
    public FCDUTF16CollationIterator(final CollationData data, final boolean numeric, final CharSequence s, final int p) {
        super(data, numeric, s, p);
        this.rawSeq = s;
        this.segmentStart = p;
        this.rawLimit = s.length();
        this.nfcImpl = data.nfcImpl;
        this.checkDir = 1;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CollationIterator) || !this.equals(other) || !(other instanceof FCDUTF16CollationIterator)) {
            return false;
        }
        final FCDUTF16CollationIterator o = (FCDUTF16CollationIterator)other;
        if (this.checkDir != o.checkDir) {
            return false;
        }
        if (this.checkDir == 0 && this.seq == this.rawSeq != (o.seq == o.rawSeq)) {
            return false;
        }
        if (this.checkDir != 0 || this.seq == this.rawSeq) {
            return this.pos - 0 == o.pos - 0;
        }
        return this.segmentStart - 0 == o.segmentStart - 0 && this.pos - this.start == o.pos - o.start;
    }
    
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
    
    @Override
    public void resetToOffset(final int newOffset) {
        this.reset();
        this.seq = this.rawSeq;
        final int start = 0 + newOffset;
        this.pos = start;
        this.segmentStart = start;
        this.start = start;
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
    public void setText(final boolean numeric, final CharSequence s, final int p) {
        super.setText(numeric, s, p);
        this.rawSeq = s;
        this.segmentStart = p;
        final int length = s.length();
        this.limit = length;
        this.rawLimit = length;
        this.checkDir = 1;
    }
    
    @Override
    public int nextCodePoint() {
        while (true) {
            while (this.checkDir <= 0) {
                if (this.checkDir == 0 && this.pos != this.limit) {
                    final char c = this.seq.charAt(this.pos++);
                    final char trail;
                    if (Character.isHighSurrogate(c) && this.pos != this.limit && Character.isLowSurrogate(trail = this.seq.charAt(this.pos))) {
                        ++this.pos;
                        return Character.toCodePoint(c, trail);
                    }
                    return c;
                }
                else {
                    this.switchToForward();
                }
            }
            if (this.pos == this.limit) {
                return -1;
            }
            char c = this.seq.charAt(this.pos++);
            if (CollationFCD.hasTccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || (this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos))))) {
                --this.pos;
                this.nextSegment();
                c = this.seq.charAt(this.pos++);
            }
            continue;
        }
    }
    
    @Override
    public int previousCodePoint() {
        while (true) {
            while (this.checkDir >= 0) {
                if (this.checkDir == 0 && this.pos != this.start) {
                    final CharSequence seq = this.seq;
                    final int pos = this.pos - 1;
                    this.pos = pos;
                    final char c = seq.charAt(pos);
                    final char lead;
                    if (Character.isLowSurrogate(c) && this.pos != this.start && Character.isHighSurrogate(lead = this.seq.charAt(this.pos - 1))) {
                        --this.pos;
                        return Character.toCodePoint(lead, c);
                    }
                    return c;
                }
                else {
                    this.switchToBackward();
                }
            }
            if (this.pos == this.start) {
                return -1;
            }
            final CharSequence seq2 = this.seq;
            final int pos2 = this.pos - 1;
            this.pos = pos2;
            char c = seq2.charAt(pos2);
            if (CollationFCD.hasLccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || (this.pos != this.start && CollationFCD.hasTccc(this.seq.charAt(this.pos - 1))))) {
                ++this.pos;
                this.previousSegment();
                final CharSequence seq3 = this.seq;
                final int pos3 = this.pos - 1;
                this.pos = pos3;
                c = seq3.charAt(pos3);
            }
            continue;
        }
    }
    
    @Override
    protected long handleNextCE32() {
        while (this.checkDir <= 0) {
            if (this.checkDir == 0 && this.pos != this.limit) {
                final char c = this.seq.charAt(this.pos++);
                return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
            }
            this.switchToForward();
        }
        if (this.pos == this.limit) {
            return -4294967104L;
        }
        char c = this.seq.charAt(this.pos++);
        if (CollationFCD.hasTccc(c) && (CollationFCD.maybeTibetanCompositeVowel(c) || (this.pos != this.limit && CollationFCD.hasLccc(this.seq.charAt(this.pos))))) {
            --this.pos;
            this.nextSegment();
            c = this.seq.charAt(this.pos++);
            return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
        }
        return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
    }
    
    @Override
    protected void forwardNumCodePoints(int num) {
        while (num > 0 && this.nextCodePoint() >= 0) {
            --num;
        }
    }
    
    @Override
    protected void backwardNumCodePoints(int num) {
        while (num > 0 && this.previousCodePoint() >= 0) {
            --num;
        }
    }
    
    private void switchToForward() {
        assert this.checkDir == 0 && this.pos == this.limit;
        if (this.checkDir < 0) {
            final int pos = this.pos;
            this.segmentStart = pos;
            this.start = pos;
            if (this.pos == this.segmentLimit) {
                this.limit = this.rawLimit;
                this.checkDir = 1;
            }
            else {
                this.checkDir = 0;
            }
        }
        else {
            if (this.seq != this.rawSeq) {
                this.seq = this.rawSeq;
                final int segmentLimit = this.segmentLimit;
                this.segmentStart = segmentLimit;
                this.start = segmentLimit;
                this.pos = segmentLimit;
            }
            this.limit = this.rawLimit;
            this.checkDir = 1;
        }
    }
    
    private void nextSegment() {
        assert this.checkDir > 0 && this.seq == this.rawSeq && this.pos != this.limit;
        int p = this.pos;
        int prevCC = 0;
        Label_0234: {
            Label_0218: {
                while (true) {
                    final int q = p;
                    final int c = Character.codePointAt(this.seq, p);
                    p += Character.charCount(c);
                    final int fcd16 = this.nfcImpl.getFCD16(c);
                    final int leadCC = fcd16 >> 8;
                    if (leadCC == 0 && q != this.pos) {
                        final int n = q;
                        this.segmentLimit = n;
                        this.limit = n;
                        break Label_0234;
                    }
                    if (leadCC != 0 && (prevCC > leadCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
                        break;
                    }
                    prevCC = (fcd16 & 0xFF);
                    if (p == this.rawLimit || prevCC == 0) {
                        break Label_0218;
                    }
                }
                while (true) {
                    int q;
                    while ((q = p) != this.rawLimit) {
                        final int c = Character.codePointAt(this.seq, p);
                        p += Character.charCount(c);
                        if (this.nfcImpl.getFCD16(c) <= 255) {
                            this.normalize(this.pos, q);
                            this.pos = this.start;
                            break Label_0234;
                        }
                    }
                    continue;
                }
            }
            final int n2 = p;
            this.segmentLimit = n2;
            this.limit = n2;
        }
        assert this.pos != this.limit;
        this.checkDir = 0;
    }
    
    private void switchToBackward() {
        assert this.checkDir == 0 && this.pos == this.start;
        if (this.checkDir > 0) {
            final int pos = this.pos;
            this.segmentLimit = pos;
            this.limit = pos;
            if (this.pos == this.segmentStart) {
                this.start = 0;
                this.checkDir = -1;
            }
            else {
                this.checkDir = 0;
            }
        }
        else {
            if (this.seq != this.rawSeq) {
                this.seq = this.rawSeq;
                final int segmentStart = this.segmentStart;
                this.segmentLimit = segmentStart;
                this.limit = segmentStart;
                this.pos = segmentStart;
            }
            this.start = 0;
            this.checkDir = -1;
        }
    }
    
    private void previousSegment() {
        assert this.checkDir < 0 && this.seq == this.rawSeq && this.pos != this.start;
        int p = this.pos;
        int nextCC = 0;
        Label_0238: {
            Label_0222: {
                int fcd16;
                while (true) {
                    final int q = p;
                    final int c = Character.codePointBefore(this.seq, p);
                    p -= Character.charCount(c);
                    fcd16 = this.nfcImpl.getFCD16(c);
                    final int trailCC = fcd16 & 0xFF;
                    if (trailCC == 0 && q != this.pos) {
                        final int n = q;
                        this.segmentStart = n;
                        this.start = n;
                        break Label_0238;
                    }
                    if (trailCC != 0 && ((nextCC != 0 && trailCC > nextCC) || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
                        break;
                    }
                    nextCC = fcd16 >> 8;
                    if (p == 0 || nextCC == 0) {
                        break Label_0222;
                    }
                }
                int q;
                int c;
                do {
                    q = p;
                    if (fcd16 <= 255) {
                        break;
                    }
                    if (p == 0) {
                        break;
                    }
                    c = Character.codePointBefore(this.seq, p);
                    p -= Character.charCount(c);
                } while ((fcd16 = this.nfcImpl.getFCD16(c)) != 0);
                this.normalize(q, this.pos);
                this.pos = this.limit;
                break Label_0238;
            }
            final int n2 = p;
            this.segmentStart = n2;
            this.start = n2;
        }
        assert this.pos != this.start;
        this.checkDir = 0;
    }
    
    private void normalize(final int from, final int to) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(this.rawSeq, from, to, this.normalized, to - from);
        this.segmentStart = from;
        this.segmentLimit = to;
        this.seq = this.normalized;
        this.start = 0;
        this.limit = this.start + this.normalized.length();
    }
}
