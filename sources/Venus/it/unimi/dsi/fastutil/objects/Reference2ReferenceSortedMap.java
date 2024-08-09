/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceMap;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface Reference2ReferenceSortedMap<K, V>
extends Reference2ReferenceMap<K, V>,
SortedMap<K, V> {
    @Override
    public Reference2ReferenceSortedMap<K, V> subMap(K var1, K var2);

    @Override
    public Reference2ReferenceSortedMap<K, V> headMap(K var1);

    @Override
    public Reference2ReferenceSortedMap<K, V> tailMap(K var1);

    @Override
    default public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
        return this.reference2ReferenceEntrySet();
    }

    @Override
    public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> reference2ReferenceEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public ReferenceCollection<V> values();

    @Override
    public Comparator<? super K> comparator();

    @Override
    default public ReferenceSet keySet() {
        return this.keySet();
    }

    @Override
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    @Override
    default public ObjectSet reference2ReferenceEntrySet() {
        return this.reference2ReferenceEntrySet();
    }

    @Override
    default public Set entrySet() {
        return this.entrySet();
    }

    @Override
    default public Collection values() {
        return this.values();
    }

    @Override
    default public Set keySet() {
        return this.keySet();
    }

    @Override
    default public SortedMap tailMap(Object object) {
        return this.tailMap(object);
    }

    @Override
    default public SortedMap headMap(Object object) {
        return this.headMap(object);
    }

    @Override
    default public SortedMap subMap(Object object, Object object2) {
        return this.subMap(object, object2);
    }

    public static interface FastSortedEntrySet<K, V>
    extends ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>>,
    Reference2ReferenceMap.FastEntrySet<K, V> {
        @Override
        public ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator();

        public ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceMap.Entry<K, V> var1);

        @Override
        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

