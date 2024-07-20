/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2LongSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2LongSortedMap<K>
extends AbstractObject2LongMap<K>
implements Object2LongSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2LongSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public LongCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Long>> entrySet() {
        return this.object2LongEntrySet();
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
            return new ValuesIterator(AbstractObject2LongSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(long k) {
            return AbstractObject2LongSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2LongSortedMap.this.clear();
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
    extends AbstractObjectSortedSet<K> {
        protected KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return AbstractObject2LongSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2LongSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2LongSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2LongSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2LongSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2LongSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2LongSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2LongSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2LongSortedMap.this.entrySet().iterator(new AbstractObject2LongMap.BasicEntry(from, 0L)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2LongSortedMap.this.entrySet().iterator());
        }
    }
}

