/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
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
public interface Reference2FloatSortedMap<K>
extends Reference2FloatMap<K>,
SortedMap<K, Float> {
    public Reference2FloatSortedMap<K> subMap(K var1, K var2);

    public Reference2FloatSortedMap<K> headMap(K var1);

    public Reference2FloatSortedMap<K> tailMap(K var1);

    @Override
    @Deprecated
    default public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
        return this.reference2FloatEntrySet();
    }

    @Override
    public ObjectSortedSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet();

    @Override
    public ReferenceSortedSet<K> keySet();

    @Override
    public FloatCollection values();

    @Override
    public Comparator<? super K> comparator();

    @Override
    default public ReferenceSet keySet() {
        return this.keySet();
    }

    @Override
    @Deprecated
    default public ObjectSet entrySet() {
        return this.entrySet();
    }

    @Override
    default public ObjectSet reference2FloatEntrySet() {
        return this.reference2FloatEntrySet();
    }

    @Override
    @Deprecated
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

    public static interface FastSortedEntrySet<K>
    extends ObjectSortedSet<Reference2FloatMap.Entry<K>>,
    Reference2FloatMap.FastEntrySet<K> {
        @Override
        public ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> fastIterator();

        public ObjectBidirectionalIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap.Entry<K> var1);

        @Override
        default public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
    }
}

