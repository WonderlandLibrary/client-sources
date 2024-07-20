/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;

public abstract class AbstractDoubleSortedSet
extends AbstractDoubleSet
implements DoubleSortedSet {
    protected AbstractDoubleSortedSet() {
    }

    @Override
    @Deprecated
    public DoubleSortedSet headSet(Double to) {
        return this.headSet((double)to);
    }

    @Override
    @Deprecated
    public DoubleSortedSet tailSet(Double from) {
        return this.tailSet((double)from);
    }

    @Override
    @Deprecated
    public DoubleSortedSet subSet(Double from, Double to) {
        return this.subSet((double)from, (double)to);
    }

    @Override
    @Deprecated
    public Double first() {
        return this.firstDouble();
    }

    @Override
    @Deprecated
    public Double last() {
        return this.lastDouble();
    }

    @Override
    @Deprecated
    public DoubleBidirectionalIterator doubleIterator() {
        return this.iterator();
    }

    @Override
    public abstract DoubleBidirectionalIterator iterator();
}

