/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;

public interface CharBigListIterator
extends CharBidirectionalIterator,
BigListIterator<Character> {
    @Override
    default public void set(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Character c) {
        this.set(c.charValue());
    }

    @Override
    @Deprecated
    default public void add(Character c) {
        this.add(c.charValue());
    }

    default public long skip(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasNext()) {
            this.nextChar();
        }
        return l - l2 - 1L;
    }

    default public long back(long l) {
        long l2 = l;
        while (l2-- != 0L && this.hasPrevious()) {
            this.previousChar();
        }
        return l - l2 - 1L;
    }

    @Override
    default public int skip(int n) {
        return SafeMath.safeLongToInt(this.skip((long)n));
    }

    @Override
    @Deprecated
    default public void add(Object object) {
        this.add((Character)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Character)object);
    }
}

