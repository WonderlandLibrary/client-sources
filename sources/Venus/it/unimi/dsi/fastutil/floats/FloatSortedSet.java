/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterable;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface FloatSortedSet
extends FloatSet,
SortedSet<Float>,
FloatBidirectionalIterable {
    public FloatBidirectionalIterator iterator(float var1);

    @Override
    public FloatBidirectionalIterator iterator();

    public FloatSortedSet subSet(float var1, float var2);

    public FloatSortedSet headSet(float var1);

    public FloatSortedSet tailSet(float var1);

    public FloatComparator comparator();

    public float firstFloat();

    public float lastFloat();

    @Deprecated
    default public FloatSortedSet subSet(Float f, Float f2) {
        return this.subSet(f.floatValue(), f2.floatValue());
    }

    @Deprecated
    default public FloatSortedSet headSet(Float f) {
        return this.headSet(f.floatValue());
    }

    @Deprecated
    default public FloatSortedSet tailSet(Float f) {
        return this.tailSet(f.floatValue());
    }

    @Override
    @Deprecated
    default public Float first() {
        return Float.valueOf(this.firstFloat());
    }

    @Override
    @Deprecated
    default public Float last() {
        return Float.valueOf(this.lastFloat());
    }

    @Override
    default public FloatIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    @Deprecated
    default public Object last() {
        return this.last();
    }

    @Override
    @Deprecated
    default public Object first() {
        return this.first();
    }

    @Override
    @Deprecated
    default public SortedSet tailSet(Object object) {
        return this.tailSet((Float)object);
    }

    @Override
    @Deprecated
    default public SortedSet headSet(Object object) {
        return this.headSet((Float)object);
    }

    @Override
    @Deprecated
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet((Float)object, (Float)object2);
    }

    @Override
    default public Comparator comparator() {
        return this.comparator();
    }
}

