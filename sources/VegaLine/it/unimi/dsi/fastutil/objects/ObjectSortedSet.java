/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.SortedSet;

public interface ObjectSortedSet<K>
extends ObjectSet<K>,
SortedSet<K> {
    public ObjectBidirectionalIterator<K> iterator(K var1);

    @Override
    @Deprecated
    public ObjectBidirectionalIterator<K> objectIterator();

    @Override
    public ObjectBidirectionalIterator<K> iterator();

    @Override
    public ObjectSortedSet<K> subSet(K var1, K var2);

    @Override
    public ObjectSortedSet<K> headSet(K var1);

    @Override
    public ObjectSortedSet<K> tailSet(K var1);
}

