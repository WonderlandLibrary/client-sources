/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface DoubleListIterator
extends DoubleBidirectionalIterator,
ListIterator<Double> {
    @Override
    default public void set(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void add(double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    default public void set(Double d) {
        this.set((double)d);
    }

    @Override
    @Deprecated
    default public void add(Double d) {
        this.add((double)d);
    }

    @Override
    @Deprecated
    default public Double next() {
        return DoubleBidirectionalIterator.super.next();
    }

    @Override
    @Deprecated
    default public Double previous() {
        return DoubleBidirectionalIterator.super.previous();
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
        this.add((Double)object);
    }

    @Override
    @Deprecated
    default public void set(Object object) {
        this.set((Double)object);
    }
}

