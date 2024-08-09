/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface BooleanListIterator
extends BooleanBidirectionalIterator,
ListIterator<Boolean> {
    @Override
    default public void set(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(boolean bl) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Boolean bl) {
        this.set((boolean)bl);
    }

    @Override
    @Deprecated
    default public void add(Boolean bl) {
        this.add((boolean)bl);
    }

    @Override
    @Deprecated
    default public Boolean next() {
        return BooleanBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Boolean previous() {
        return BooleanBidirectionalIterator.super.previous();
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
        this.add((Boolean)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Boolean)object);
    }
}

