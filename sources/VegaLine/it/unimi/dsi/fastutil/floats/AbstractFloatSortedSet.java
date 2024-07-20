/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;

public abstract class AbstractFloatSortedSet
extends AbstractFloatSet
implements FloatSortedSet {
    protected AbstractFloatSortedSet() {
    }

    @Override
    @Deprecated
    public FloatSortedSet headSet(Float to) {
        return this.headSet(to.floatValue());
    }

    @Override
    @Deprecated
    public FloatSortedSet tailSet(Float from) {
        return this.tailSet(from.floatValue());
    }

    @Override
    @Deprecated
    public FloatSortedSet subSet(Float from, Float to) {
        return this.subSet(from.floatValue(), to.floatValue());
    }

    @Override
    @Deprecated
    public Float first() {
        return Float.valueOf(this.firstFloat());
    }

    @Override
    @Deprecated
    public Float last() {
        return Float.valueOf(this.lastFloat());
    }

    @Override
    @Deprecated
    public FloatBidirectionalIterator floatIterator() {
        return this.iterator();
    }

    @Override
    public abstract FloatBidirectionalIterator iterator();
}

