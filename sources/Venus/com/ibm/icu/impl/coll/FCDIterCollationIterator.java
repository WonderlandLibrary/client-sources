/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFCD;
import com.ibm.icu.impl.coll.IterCollationIterator;
import com.ibm.icu.text.UCharacterIterator;

public final class FCDIterCollationIterator
extends IterCollationIterator {
    private State state = State.ITER_CHECK_FWD;
    private int start;
    private int pos;
    private int limit;
    private final Normalizer2Impl nfcImpl;
    private StringBuilder s;
    private StringBuilder normalized;
    static final boolean $assertionsDisabled = !FCDIterCollationIterator.class.desiredAssertionStatus();

    public FCDIterCollationIterator(CollationData collationData, boolean bl, UCharacterIterator uCharacterIterator, int n) {
        super(collationData, bl, uCharacterIterator);
        this.start = n;
        this.nfcImpl = collationData.nfcImpl;
    }

    @Override
    public void resetToOffset(int n) {
        super.resetToOffset(n);
        this.start = n;
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
            int n;
            if (this.state == State.ITER_CHECK_FWD) {
                n = this.iter.next();
                if (n < 0) {
                    return n;
                }
                if (CollationFCD.hasTccc(n) && (CollationFCD.maybeTibetanCompositeVowel(n) || CollationFCD.hasLccc(this.iter.current()))) {
                    this.iter.previous();
                    if (this.nextSegment()) continue;
                    return 1;
                }
                if (FCDIterCollationIterator.isLeadSurrogate(n)) {
                    int n2 = this.iter.next();
                    if (FCDIterCollationIterator.isTrailSurrogate(n2)) {
                        return Character.toCodePoint((char)n, (char)n2);
                    }
                    if (n2 >= 0) {
                        this.iter.previous();
                    }
                }
                return n;
            }
            if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                n = this.iter.nextCodePoint();
                this.pos += Character.charCount(n);
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
                return n;
            }
            if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                n = this.normalized.codePointAt(this.pos);
                this.pos += Character.charCount(n);
                return n;
            }
            this.switchToForward();
        }
    }

    @Override
    public int previousCodePoint() {
        while (true) {
            int n;
            if (this.state == State.ITER_CHECK_BWD) {
                n = this.iter.previous();
                if (n < 0) {
                    this.pos = 0;
                    this.start = 0;
                    this.state = State.ITER_IN_FCD_SEGMENT;
                    return 1;
                }
                if (CollationFCD.hasLccc(n)) {
                    int n2 = -1;
                    if (CollationFCD.maybeTibetanCompositeVowel(n) || CollationFCD.hasTccc(n2 = this.iter.previous())) {
                        this.iter.next();
                        if (n2 >= 0) {
                            this.iter.next();
                        }
                        if (this.previousSegment()) continue;
                        return 1;
                    }
                    if (FCDIterCollationIterator.isTrailSurrogate(n)) {
                        if (n2 < 0) {
                            n2 = this.iter.previous();
                        }
                        if (FCDIterCollationIterator.isLeadSurrogate(n2)) {
                            return Character.toCodePoint((char)n2, (char)n);
                        }
                    }
                    if (n2 >= 0) {
                        this.iter.next();
                    }
                }
                return n;
            }
            if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.start) {
                n = this.iter.previousCodePoint();
                this.pos -= Character.charCount(n);
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
                return n;
            }
            if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != 0) {
                n = this.normalized.codePointBefore(this.pos);
                this.pos -= Character.charCount(n);
                return n;
            }
            this.switchToBackward();
        }
    }

    @Override
    protected long handleNextCE32() {
        int n;
        while (true) {
            if (this.state == State.ITER_CHECK_FWD) {
                n = this.iter.next();
                if (n < 0) {
                    return -4294967104L;
                }
                if (!CollationFCD.hasTccc(n) || !CollationFCD.maybeTibetanCompositeVowel(n) && !CollationFCD.hasLccc(this.iter.current())) break;
                this.iter.previous();
                if (this.nextSegment()) continue;
                n = -1;
                return 192L;
            }
            if (this.state == State.ITER_IN_FCD_SEGMENT && this.pos != this.limit) {
                n = this.iter.next();
                ++this.pos;
                if (!$assertionsDisabled && n < 0) {
                    throw new AssertionError();
                }
                break;
            }
            if (this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos != this.normalized.length()) {
                n = this.normalized.charAt(this.pos++);
                break;
            }
            this.switchToForward();
        }
        return this.makeCodePointAndCE32Pair(n, this.trie.getFromU16SingleLead((char)n));
    }

    @Override
    protected char handleGetTrailSurrogate() {
        if (this.state.compareTo(State.ITER_IN_FCD_SEGMENT) <= 0) {
            int n = this.iter.next();
            if (FCDIterCollationIterator.isTrailSurrogate(n)) {
                if (this.state == State.ITER_IN_FCD_SEGMENT) {
                    ++this.pos;
                }
            } else if (n >= 0) {
                this.iter.previous();
            }
            return (char)n;
        }
        if (!$assertionsDisabled && this.pos >= this.normalized.length()) {
            throw new AssertionError();
        }
        char c = this.normalized.charAt(this.pos);
        if (Character.isLowSurrogate(c)) {
            ++this.pos;
        }
        return c;
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
        if (!($assertionsDisabled || this.state == State.ITER_CHECK_BWD || this.state == State.ITER_IN_FCD_SEGMENT && this.pos == this.limit || this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == this.normalized.length())) {
            throw new AssertionError();
        }
        if (this.state == State.ITER_CHECK_BWD) {
            this.start = this.pos = this.iter.getIndex();
            this.state = this.pos == this.limit ? State.ITER_CHECK_FWD : State.ITER_IN_FCD_SEGMENT;
        } else {
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
        int n;
        if (!$assertionsDisabled && this.state != State.ITER_CHECK_FWD) {
            throw new AssertionError();
        }
        this.pos = this.iter.getIndex();
        if (this.s == null) {
            this.s = new StringBuilder();
        } else {
            this.s.setLength(0);
        }
        int n2 = 0;
        while ((n = this.iter.nextCodePoint()) >= 0) {
            int n3 = this.nfcImpl.getFCD16(n);
            int n4 = n3 >> 8;
            if (n4 == 0 && this.s.length() != 0) {
                this.iter.previousCodePoint();
                break;
            }
            this.s.appendCodePoint(n);
            if (n4 != 0 && (n2 > n4 || CollationFCD.isFCD16OfTibetanCompositeVowel(n3))) {
                while ((n = this.iter.nextCodePoint()) >= 0) {
                    if (this.nfcImpl.getFCD16(n) <= 255) {
                        this.iter.previousCodePoint();
                        break;
                    }
                    this.s.appendCodePoint(n);
                }
                this.normalize(this.s);
                this.start = this.pos;
                this.limit = this.pos + this.s.length();
                this.state = State.IN_NORM_ITER_AT_LIMIT;
                this.pos = 0;
                return false;
            }
            n2 = n3 & 0xFF;
            if (n2 != 0) continue;
            break;
        }
        this.limit = this.pos + this.s.length();
        if (!$assertionsDisabled && this.pos == this.limit) {
            throw new AssertionError();
        }
        this.iter.moveIndex(-this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return false;
    }

    private void switchToBackward() {
        if (!($assertionsDisabled || this.state == State.ITER_CHECK_FWD || this.state == State.ITER_IN_FCD_SEGMENT && this.pos == this.start || this.state.compareTo(State.IN_NORM_ITER_AT_LIMIT) >= 0 && this.pos == 0)) {
            throw new AssertionError();
        }
        if (this.state == State.ITER_CHECK_FWD) {
            this.limit = this.pos = this.iter.getIndex();
            this.state = this.pos == this.start ? State.ITER_CHECK_BWD : State.ITER_IN_FCD_SEGMENT;
        } else {
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
        int n;
        if (!$assertionsDisabled && this.state != State.ITER_CHECK_BWD) {
            throw new AssertionError();
        }
        this.pos = this.iter.getIndex();
        if (this.s == null) {
            this.s = new StringBuilder();
        } else {
            this.s.setLength(0);
        }
        int n2 = 0;
        while ((n = this.iter.previousCodePoint()) >= 0) {
            int n3 = this.nfcImpl.getFCD16(n);
            int n4 = n3 & 0xFF;
            if (n4 == 0 && this.s.length() != 0) {
                this.iter.nextCodePoint();
                break;
            }
            this.s.appendCodePoint(n);
            if (n4 != 0 && (n2 != 0 && n4 > n2 || CollationFCD.isFCD16OfTibetanCompositeVowel(n3))) {
                while (n3 > 255 && (n = this.iter.previousCodePoint()) >= 0) {
                    n3 = this.nfcImpl.getFCD16(n);
                    if (n3 == 0) {
                        this.iter.nextCodePoint();
                        break;
                    }
                    this.s.appendCodePoint(n);
                }
                this.s.reverse();
                this.normalize(this.s);
                this.limit = this.pos;
                this.start = this.pos - this.s.length();
                this.state = State.IN_NORM_ITER_AT_START;
                this.pos = this.normalized.length();
                return false;
            }
            n2 = n3 >> 8;
            if (n2 != 0) continue;
            break;
        }
        this.start = this.pos - this.s.length();
        if (!$assertionsDisabled && this.pos == this.start) {
            throw new AssertionError();
        }
        this.iter.moveIndex(this.s.length());
        this.state = State.ITER_IN_FCD_SEGMENT;
        return false;
    }

    private void normalize(CharSequence charSequence) {
        if (this.normalized == null) {
            this.normalized = new StringBuilder();
        }
        this.nfcImpl.decompose(charSequence, this.normalized);
    }

    private static enum State {
        ITER_CHECK_FWD,
        ITER_CHECK_BWD,
        ITER_IN_FCD_SEGMENT,
        IN_NORM_ITER_AT_LIMIT,
        IN_NORM_ITER_AT_START;

    }
}

