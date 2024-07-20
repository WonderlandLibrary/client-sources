/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2LongMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2LongSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractReference2LongSortedMap<K>
extends AbstractReference2LongMap<K>
implements Reference2LongSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractReference2LongSortedMap() {
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public LongCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
        return this.reference2LongEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractLongIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Long>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Long>> i) {
            this.i = i;
        }

        @Override
        public long nextLong() {
            return (Long)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractLongCollection {
        protected ValuesCollection() {
        }

        @Override
        public LongIterator iterator() {
            return new ValuesIterator(AbstractReference2LongSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(long k) {
            return AbstractReference2LongSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractReference2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2LongSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Long>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Long>> i) {
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
    extends AbstractReferenceSortedSet<K> {
        protected KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return AbstractReference2LongSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractReference2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2LongSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractReference2LongSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractReference2LongSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractReference2LongSortedMap.this.lastKey();
        }

        @Override
        public ReferenceSortedSet<K> headSet(K to) {
            return AbstractReference2LongSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K from) {
            return AbstractReference2LongSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K from, K to) {
            return AbstractReference2LongSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractReference2LongSortedMap.this.entrySet().iterator(new AbstractReference2LongMap.BasicEntry(from, 0L)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractReference2LongSortedMap.this.entrySet().iterator());
        }
    }
}

