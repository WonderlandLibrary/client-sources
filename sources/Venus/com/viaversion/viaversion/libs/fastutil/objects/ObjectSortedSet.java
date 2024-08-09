/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Size64;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterable;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Spliterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface ObjectSortedSet<K>
extends ObjectSet<K>,
SortedSet<K>,
ObjectBidirectionalIterable<K> {
    public ObjectBidirectionalIterator<K> iterator(K var1);

    @Override
    public ObjectBidirectionalIterator<K> iterator();

    @Override
    default public ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorFromSorted(this.iterator(), Size64.sizeOf(this), 85, this.comparator());
    }

    @Override
    public ObjectSortedSet<K> subSet(K var1, K var2);

    @Override
    public ObjectSortedSet<K> headSet(K var1);

    @Override
    public ObjectSortedSet<K> tailSet(K var1);

    @Override
    default public ObjectIterator iterator() {
        return this.iterator();
    }

    @Override
    default public Spliterator spliterator() {
        return this.spliterator();
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

