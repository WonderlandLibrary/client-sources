/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharListIterator
extends CharBidirectionalIterator,
ListIterator<Character> {
    @Override
    default public void set(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
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

    @Override
    @Deprecated
    default public Character next() {
        return CharBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Character previous() {
        return CharBidirectionalIterator.super.previous();
    }

    @Override
    @Deprecated
    default public Object next() {
        return this.next();
    }

    @Override
    @Deprecated
    default public Object previous() {
        return this.previous();
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

