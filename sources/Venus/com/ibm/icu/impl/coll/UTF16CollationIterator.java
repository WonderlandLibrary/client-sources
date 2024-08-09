/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationIterator;

public class UTF16CollationIterator
extends CollationIterator {
    protected CharSequence seq;
    protected int start;
    protected int pos;
    protected int limit;
    static final boolean $assertionsDisabled = !UTF16CollationIterator.class.desiredAssertionStatus();

    public UTF16CollationIterator(CollationData collationData) {
        super(collationData);
    }

    public UTF16CollationIterator(CollationData collationData, boolean bl, CharSequence charSequence, int n) {
        super(collationData, bl);
        this.seq = charSequence;
        this.start = 0;
        this.pos = n;
        this.limit = charSequence.length();
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return true;
        }
        UTF16CollationIterator uTF16CollationIterator = (UTF16CollationIterator)object;
        return this.pos - this.start == uTF16CollationIterator.pos - uTF16CollationIterator.start;
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
        this.pos = this.start + n;
    }

    @Override
    public int getOffset() {
        return this.pos - this.start;
    }

    public void setText(boolean bl, CharSequence charSequence, int n) {
        this.reset(bl);
        this.seq = charSequence;
        this.start = 0;
        this.pos = n;
        this.limit = charSequence.length();
    }

    @Override
    public int nextCodePoint() {
        char c;
        char c2;
        if (this.pos == this.limit) {
            return 1;
        }
        if (Character.isHighSurrogate(c2 = this.seq.charAt(this.pos++)) && this.pos != this.limit && Character.isLowSurrogate(c = this.seq.charAt(this.pos))) {
            ++this.pos;
            return Character.toCodePoint(c2, c);
        }
        return c2;
    }

    @Override
    public int previousCodePoint() {
        char c;
        char c2;
        if (this.pos == this.start) {
            return 1;
        }
        if (Character.isLowSurrogate(c2 = this.seq.charAt(--this.pos)) && this.pos != this.start && Character.isHighSurrogate(c = this.seq.charAt(this.pos - 1))) {
            --this.pos;
            return Character.toCodePoint(c, c2);
        }
        return c2;
    }

    @Override
    protected long handleNextCE32() {
        if (this.pos == this.limit) {
            return -4294967104L;
        }
        char c = this.seq.charAt(this.pos++);
        return this.makeCodePointAndCE32Pair(c, this.trie.getFromU16SingleLead(c));
    }

    @Override
    protected char handleGetTrailSurrogate() {
        if (this.pos == this.limit) {
            return '\u0001';
        }
        char c = this.seq.charAt(this.pos);
        if (Character.isLowSurrogate(c)) {
            ++this.pos;
        }
        return c;
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        while (n > 0 && this.pos != this.limit) {
            char c = this.seq.charAt(this.pos++);
            --n;
            if (!Character.isHighSurrogate(c) || this.pos == this.limit || !Character.isLowSurrogate(this.seq.charAt(this.pos))) continue;
            ++this.pos;
        }
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        while (n > 0 && this.pos != this.start) {
            char c = this.seq.charAt(--this.pos);
            --n;
            if (!Character.isLowSurrogate(c) || this.pos == this.start || !Character.isHighSurrogate(this.seq.charAt(this.pos - 1))) continue;
            --this.pos;
        }
    }
}

