/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.SortedSet;

public interface ReferenceSortedSet<K>
extends ReferenceSet<K>,
SortedSet<K> {
    public ObjectBidirectionalIterator<K> iterator(K var1);

    @Override
    @Deprecated
    public ObjectBidirectionalIterator<K> objectIterator();

    @Override
    public ObjectBidirectionalIterator<K> iterator();

    @Override
    public ReferenceSortedSet<K> subSet(K var1, K var2);

    @Override
    public ReferenceSortedSet<K> headSet(K var1);

    @Override
    public ReferenceSortedSet<K> tailSet(K var1);
}

