/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharBidirectionalIterator
extends CharIterator,
ObjectBidirectionalIterator<Character> {
    public char previousChar();

    @Override
    @Deprecated
    default public Character previous() {
        return Character.valueOf(this.previousChar());
    }

    @Override
    default public int back(int n) {
        int n2 = n;
        while (n2-- != 0 && this.hasPrevious()) {
            this.previousChar();
        }
        return n - n2 - 1;
    }

    @Override
    default public int skip(int n) {
        return CharIterator.super.skip(n);
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
    }
}

