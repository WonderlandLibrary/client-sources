/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatListIterator
extends FloatBidirectionalIterator,
ListIterator<Float> {
    @Override
    default public void set(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Float f) {
        this.set(f.floatValue());
    }

    @Override
    @Deprecated
    default public void add(Float f) {
        this.add(f.floatValue());
    }

    @Override
    @Deprecated
    default public Float next() {
        return FloatBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Float previous() {
        return FloatBidirectionalIterator.super.previous();
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
        this.add((Float)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Float)object);
    }
}

