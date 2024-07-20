/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractIntSet;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;

public abstract class AbstractIntSortedSet
extends AbstractIntSet
implements IntSortedSet {
    protected AbstractIntSortedSet() {
    }

    @Override
    @Deprecated
    public IntSortedSet headSet(Integer to) {
        return this.headSet((int)to);
    }

    @Override
    @Deprecated
    public IntSortedSet tailSet(Integer from) {
        return this.tailSet((int)from);
    }

    @Override
    @Deprecated
    public IntSortedSet subSet(Integer from, Integer to) {
        return this.subSet((int)from, (int)to);
    }

    @Override
    @Deprecated
    public Integer first() {
        return this.firstInt();
    }

    @Override
    @Deprecated
    public Integer last() {
        return this.lastInt();
    }

    @Override
    @Deprecated
    public IntBidirectionalIterator intIterator() {
        return this.iterator();
    }

    @Override
    public abstract IntBidirectionalIterator iterator();
}

