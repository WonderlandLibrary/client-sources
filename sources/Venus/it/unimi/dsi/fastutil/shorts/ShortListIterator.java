/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ShortListIterator
extends ShortBidirectionalIterator,
ListIterator<Short> {
    @Override
    default public void set(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(short s) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Short s) {
        this.set((short)s);
    }

    @Override
    @Deprecated
    default public void add(Short s) {
        this.add((short)s);
    }

    @Override
    @Deprecated
    default public Short next() {
        return ShortBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Short previous() {
        return ShortBidirectionalIterator.super.previous();
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
        this.add((Short)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Short)object);
    }
}

