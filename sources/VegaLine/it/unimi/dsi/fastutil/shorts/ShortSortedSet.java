/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.SortedSet;

public interface ShortSortedSet
extends ShortSet,
SortedSet<Short> {
    public ShortBidirectionalIterator iterator(short var1);

    @Override
    @Deprecated
    public ShortBidirectionalIterator shortIterator();

    @Override
    public ShortBidirectionalIterator iterator();

    public ShortSortedSet subSet(Short var1, Short var2);

    public ShortSortedSet headSet(Short var1);

    public ShortSortedSet tailSet(Short var1);

    public ShortComparator comparator();

    public ShortSortedSet subSet(short var1, short var2);

    public ShortSortedSet headSet(short var1);

    public ShortSortedSet tailSet(short var1);

    public short firstShort();

    public short lastShort();
}

