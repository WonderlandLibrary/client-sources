package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;

public final class FCDIterCollationIterator extends IterCollationIterator
{
    private State state;
    private int start;
    private int pos;
    private int limit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder s;
    private StringBuilder normalized;
    
    public FCDIterCollationIterator(final CollationData data, final boolean numeric, final UCharacterIterator ui, final int startIndex) {
        super(data, numeric, ui);
        this.state = State.ITER_CHECK_FWD;
        this.start = startIndex;
        this.nfcImpl = data.nfcImpl;
    }
    
    @Override
    public void resetToOffset(final int newOffset) {
        super.resetToOffset(newOffset);
        this.start = newOffset;
        this.state = State.ITER_CHECK_FWD;
    }
    
    @Override
    public int getOffset() {
        if (this.state.compareTo(State.ITER_CHECK_BWD) <= 0) {
            return this.iter.getIndex();
        }
        if (this.state == State.ITER_IN_FCD_SEGMENT) {
            return this.pos;
        }
        if (this.pos == 0) {
            return this.start;
        }
        return this.limit;
    }
    
    @Override
    public int nextCodePoint() {
        while (true) {
            if (this.state == State.ITER_CHECK_FWD) {
                final int c = this.iter.next();
                if (c < 0) {
                    return c;
                }
                if (!CollationFCD.hasTccc(c) || (!CollationFCD.maybeTibetanCompositeVowel(c) && !CollationFCD.hasLccc(this.iter.current()))) {
                    if (CollationIterator.isLeadSurrogate(c)) {
                        final int trail = this.iter.next();
                        if (CollationIterator.isTrailSurrogate(trail)) {
                            return Character.toCodePoint((char)c, (char)trail);
                        }
                        if (trail >= 0) {
                            this.iter.previous();
                        }
                    }
                    return c;
                }
                this.iter.previous();
                if (!this.nextSegment()) {
                    return -1;
                }
                continue;
            }
            else if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                final int c = this.iter.nextCodePoint();
                this.pos += Character.charCount(c);
                assert c >= 0;
                return c;
            }
            else {
                if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                    final int c = this.normalized.codePointAt(this.pos);
                    this.pos += Character.charCount(c);
                    return c;
                }
                this.switchToForward();
            }
        }
    }
    
    @Override
    public int previousCodePoint() {
        Block_15: {
            Block_11: {
                int c;
                while (true) {
                    if (this.state == State.ITER_CHECK_BWD) {
                        c = this.iter.previous();
                        if (c < 0) {
                            final int n = 0;
                            this.pos = n;
                            this.start = n;
                            this.state = State.ITER_IN_FCD_SEGMENT;
                            return -1;
                        }
                        if (!CollationFCD.hasLccc(c)) {
                            break;
                        }
                        int prev = -1;
                        if (CollationFCD.maybeTibetanCompositeVowel(c) || CollationFCD.hasTccc(prev = this.iter.previous())) {
                            this.iter.next();
                            if (prev >= 0) {
                                this.iter.next();
                            }
                            if (!this.previousSegment()) {
                                return -1;
                            }
                            continue;
                        }
                        else {
                            if (CollationIterator.isTrailSurrogate(c)) {
                                if (prev < 0) {
                                    prev = this.iter.previous();
                                }
                                if (CollationIterator.isLeadSurrogate(prev)) {
                                    return Character.toCodePoint((char)prev, (char)c);
                                }
                            }
                            if (prev >= 0) {
                                this.iter.next();
                                break;
                            }
                            break;
                        }
                    }
                    else {
                        if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.start) {
                            break Block_11;
                        }
                        if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != 0) {
                            break Block_15;
                        }
                        this.switchToBackward();
                    }
                }
                return c;
            }
            int c = this.iter.previousCodePoint();
            this.pos -= Character.charCount(c);
            assert c >= 0;
            return c;
        }
        int c = this.normalized.codePointBefore(this.pos);
        this.pos -= Character.charCount(c);
        return c;
    }
    
    @Override
    protected long handleNextCE32() {
        int c;
        while (true) {
            if (this.state == State.ITER_CHECK_FWD) {
                c = this.iter.next();
                if (c < 0) {
                    return -4294967104L;
                }
                if (!CollationFCD.hasTccc(c) || (!CollationFCD.maybeTibetanCompositeVowel(c) && !CollationFCD.hasLccc(this.iter.current()))) {
                    break;
                }
                this.iter.previous();
                if (!this.nextSegment()) {
                    c = -1;
                    return 192L;
                }
                continue;
            }
            else if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                c = this.iter.next();
                ++this.pos;
                assert c >= 0;
                break;
            }
            else {
                if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                    c = this.normalized.charAt(this.pos++);
                    break;
                }
                this.switchToForward();
            }
        }
        return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead((char)c));
    }
    
    @Override
    protected char handleGetTrailSurrogate() {
        if (this.state.compareTo(State.ITER_IN_FCD_SEGMENT) <= 0) {
            final int trail = this.iter.next();
            if (CollationIterator.isTrailSurrogate(trail)) {
                if (this.state == State.ITER_IN_FCD_SEGMENT) {
                    ++this.pos;
                }
            }
            else if (trail >= 0) {
                this.iter.previous();
            }
            return (char)trail;
        }
        assert this.pos < this.normalized.length();
        final char trail2;
        if (Character.isLowSurrogate(trail2 = this.normalized.charAt(this.pos))) {
            ++this.pos;
        }
        return trail2;
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
        assert this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == this.normalized.length();
        if (this.state == State.ITER_CHECK_BWD) {
            final int index = this.iter.getIndex();
            this.pos = index;
            this.start = index;
            if (this.pos == this.limit) {
                this.state = State.ITER_CHECK_FWD;
            }
            else {
                this.state = State.ITER_IN_FCD_SEGMENT;
            }
        }
        else {
            if (this.state != State.ITER_IN_FCD_SEGMENT) {
                if (this.state == State.IN_NORM_ITER_AT_START) {
                    this.iter.moveIndex(this.limit - this.start);
                }
                this.start = this.limit;
            }
            this.state = State.ITER_CHECK_FWD;
        }
    }
    
    private boolean nextSegment() {
        assert this.state == State.ITER_CHECK_FWD;
        this.pos = this.iter.getIndex();
        if (this.s == null) {
            this.s = new StringBuilder();
        }
        else {
            this.s.setLength(0);
        }
        int prevCC = 0;
        Block_11: {
            while (true) {
                final int c = this.iter.nextCodePoint();
                if (c < 0) {
                    break Block_11;
                }
                final int fcd16 = this.nfcImpl.getFCD16(c);
                final int leadCC = fcd16 >> 8;
                if (leadCC == 0 && this.s.length() != 0) {
                    this.iter.previousCodePoint();
                    break Block_11;
                }
                this.s.appendCodePoint(c);
                if (leadCC != 0 && (prevCC > leadCC || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
                    break;
                }
                prevCC = (fcd16 & 0xFF);
                if (prevCC == 0) {
                    break Block_11;
                }
            }
            while (true) {
                final int c = this.iter.nextCodePoint();
                if (c < 0) {
                    break;
                }
                if (this.nfcImpl.getFCD16(c) <= 255) {
                    this.iter.previousCodePoint();
                    break;
                }
                this.s.appendCodePoint(c);
            }
            this.normalize(this.s);
            this.start = this.pos;
            this.limit = this.pos + this.s.length();
            this.state = State.IN_NORM_ITER_AT_LIMIT;
            this.pos = 0;
            return true;
        }
        this.limit = this.pos + this.s.length();
        assert this.pos != this.limit;
        this.iter.moveIndex(-this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return true;
    }
    
    private void switchToBackward() {
        assert this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == 0;
        if (this.state == State.ITER_CHECK_FWD) {
            final int index = this.iter.getIndex();
            this.pos = index;
            this.limit = index;
            if (this.pos == this.start) {
                this.state = State.ITER_CHECK_BWD;
            }
            else {
                this.state = State.ITER_IN_FCD_SEGMENT;
            }
        }
        else {
            if (this.state != State.ITER_IN_FCD_SEGMENT) {
                if (this.state == State.IN_NORM_ITER_AT_LIMIT) {
                    this.iter.moveIndex(this.start - this.limit);
                }
                this.limit = this.start;
            }
            this.state = State.ITER_CHECK_BWD;
        }
    }
    
    private boolean previousSegment() {
        assert this.state == State.ITER_CHECK_BWD;
        this.pos = this.iter.getIndex();
        if (this.s == null) {
            this.s = new StringBuilder();
        }
        else {
            this.s.setLength(0);
        }
        int nextCC = 0;
        Block_12: {
            int fcd16;
            while (true) {
                final int c = this.iter.previousCodePoint();
                if (c < 0) {
                    break Block_12;
                }
                fcd16 = this.nfcImpl.getFCD16(c);
                final int trailCC = fcd16 & 0xFF;
                if (trailCC == 0 && this.s.length() != 0) {
                    this.iter.nextCodePoint();
                    break Block_12;
                }
                this.s.appendCodePoint(c);
                if (trailCC != 0 && ((nextCC != 0 && trailCC > nextCC) || CollationFCD.isFCD16OfTibetanCompositeVowel(fcd16))) {
                    break;
                }
                nextCC = fcd16 >> 8;
                if (nextCC == 0) {
                    break Block_12;
                }
            }
            while (fcd16 > 255) {
                final int c = this.iter.previousCodePoint();
                if (c < 0) {
                    break;
                }
                fcd16 = this.nfcImpl.getFCD16(c);
                if (fcd16 == 0) {
                    this.iter.nextCodePoint();
                    break;
                }
                this.s.appendCodePoint(c);
            }
            this.s.reverse();
            this.normalize(this.s);
            this.limit = this.pos;
            this.start = this.pos - this.s.length();
            this.state = State.IN_NORM_ITER_AT_START;
            this.pos = this.normalized.length();
            return true;
        }
        this.start = this.pos - this.s.length();
        assert this.pos != this.start;
        this.iter.moveIndex(this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return true;
    }
    
    private void normalize(final CharSequence s) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(s, this.normalized);
    }
    
    private enum State
    {
        ITER_CHECK_FWD, 
        ITER_CHECK_BWD, 
        ITER_IN_FCD_SEGMENT, 
        IN_NORM_ITER_AT_LIMIT, 
        IN_NORM_ITER_AT_START;
    }
}
