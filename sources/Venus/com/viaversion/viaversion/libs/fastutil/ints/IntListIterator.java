/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface IntListIterator
extends IntBidirectionalIterator,
ListIterator<Integer> {
    @Override
    default public void set(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Integer n) {
        this.set((int)n);
    }

    @Override
    @Deprecated
    default public void add(Integer n) {
        this.add((int)n);
    }

    @Override
    @Deprecated
    default public Integer next() {
        return IntBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Integer previous() {
        return IntBidirectionalIterator.super.previous();
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
        this.add((Integer)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Integer)object);
    }
}

