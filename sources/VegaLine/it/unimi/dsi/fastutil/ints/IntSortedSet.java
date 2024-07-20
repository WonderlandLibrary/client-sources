/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.SortedSet;

public interface IntSortedSet
extends IntSet,
SortedSet<Integer> {
    public IntBidirectionalIterator iterator(int var1);

    @Override
    @Deprecated
    public IntBidirectionalIterator intIterator();

    @Override
    public IntBidirectionalIterator iterator();

    public IntSortedSet subSet(Integer var1, Integer var2);

    public IntSortedSet headSet(Integer var1);

    public IntSortedSet tailSet(Integer var1);

    public IntComparator comparator();

    public IntSortedSet subSet(int var1, int var2);

    public IntSortedSet headSet(int var1);

    public IntSortedSet tailSet(int var1);

    public int firstInt();

    public int lastInt();
}

