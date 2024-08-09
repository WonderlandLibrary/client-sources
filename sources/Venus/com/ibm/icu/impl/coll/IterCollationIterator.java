/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.text.UCharacterIterator;

public class IterCollationIterator
extends CollationIterator {
    protected UCharacterIterator iter;

    public IterCollationIterator(CollationData collationData, boolean bl, UCharacterIterator uCharacterIterator) {
        super(collationData, bl);
        this.iter = uCharacterIterator;
    }

    @Override
    public void resetToOffset(int n) {
        this.reset();
        this.iter.setIndex(n);
    }

    @Override
    public int getOffset() {
        return this.iter.getIndex();
    }

    @Override
    public int nextCodePoint() {
        return this.iter.nextCodePoint();
    }

    @Override
    public int previousCodePoint() {
        return this.iter.previousCodePoint();
    }

    @Override
    protected long handleNextCE32() {
        int n = this.iter.next();
        if (n < 0) {
            return -4294967104L;
        }
        return this.makeCodePointAndCE32Pair(n, this.trie.getFromU16SingleLead((char)n));
    }

    @Override
    protected char handleGetTrailSurrogate() {
        int n = this.iter.next();
        if (!IterCollationIterator.isTrailSurrogate(n) && n >= 0) {
            this.iter.previous();
        }
        return (char)n;
    }

    @Override
    protected void forwardNumCodePoints(int n) {
        this.iter.moveCodePointIndex(n);
    }

    @Override
    protected void backwardNumCodePoints(int n) {
        this.iter.moveCodePointIndex(-n);
    }
}

