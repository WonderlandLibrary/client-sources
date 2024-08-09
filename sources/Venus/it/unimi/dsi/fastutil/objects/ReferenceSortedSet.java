/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import java.util.Iterator;
import java.util.SortedSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ReferenceSortedSet<K>
extends ReferenceSet<K>,
SortedSet<K>,
ObjectBidirectionalIterable<K> {
    public ObjectBidirectionalIterator<K> iterator(K var1);

    @Override
    public ObjectBidirectionalIterator<K> iterator();

    @Override
    public ReferenceSortedSet<K> subSet(K var1, K var2);

    @Override
    public ReferenceSortedSet<K> headSet(K var1);

    @Override
    public ReferenceSortedSet<K> tailSet(K var1);

    @Override
    default public ObjectIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public SortedSet tailSet(Object object) {
        return this.tailSet(object);
    }

    @Override
    default public SortedSet headSet(Object object) {
        return this.headSet(object);
    }

    @Override
    default public SortedSet subSet(Object object, Object object2) {
        return this.subSet(object, object2);
    }
}

