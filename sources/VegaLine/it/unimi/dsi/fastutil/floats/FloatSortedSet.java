/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import java.util.SortedSet;

public interface FloatSortedSet
extends FloatSet,
SortedSet<Float> {
    public FloatBidirectionalIterator iterator(float var1);

    @Override
    @Deprecated
    public FloatBidirectionalIterator floatIterator();

    @Override
    public FloatBidirectionalIterator iterator();

    public FloatSortedSet subSet(Float var1, Float var2);

    public FloatSortedSet headSet(Float var1);

    public FloatSortedSet tailSet(Float var1);

    public FloatComparator comparator();

    public FloatSortedSet subSet(float var1, float var2);

    public FloatSortedSet headSet(float var1);

    public FloatSortedSet tailSet(float var1);

    public float firstFloat();

    public float lastFloat();
}

