/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObject2ReferenceMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.Object2ReferenceSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2ReferenceSortedMap<K, V>
extends AbstractObject2ReferenceMap<K, V>
implements Object2ReferenceSortedMap<K, V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2ReferenceSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public ReferenceCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
        return this.object2ReferenceEntrySet();
    }

    protected static class ValuesIterator<K, V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, V>> i) {
            this.i = i;
        }

        @Override
        public V next() {
            return ((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractReferenceCollection<V> {
        protected ValuesCollection() {
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(AbstractObject2ReferenceSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractObject2ReferenceSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2ReferenceSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K, V>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, V>> i) {
            this.i = i;
        }

        @Override
        public K next() {
            return ((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Map.Entry)this.i.previous()).getKey();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
    }

    protected class KeySet
    extends AbstractObjectSortedSet<K> {
        protected KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return AbstractObject2ReferenceSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2ReferenceSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2ReferenceSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2ReferenceSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2ReferenceSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2ReferenceSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2ReferenceSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2ReferenceSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2ReferenceSortedMap.this.entrySet().iterator(new AbstractObject2ReferenceMap.BasicEntry(from, null)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2ReferenceSortedMap.this.entrySet().iterator());
        }
    }
}

