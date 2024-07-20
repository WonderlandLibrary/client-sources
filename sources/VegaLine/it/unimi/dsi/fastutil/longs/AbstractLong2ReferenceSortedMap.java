/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ReferenceMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ReferenceSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public abstract class AbstractLong2ReferenceSortedMap<V>
extends AbstractLong2ReferenceMap<V>
implements Long2ReferenceSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2ReferenceSortedMap() {
    }

    @Override
    @Deprecated
    public Long2ReferenceSortedMap<V> headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2ReferenceSortedMap<V> tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
        return this.subMap((long)from, (long)to);
    }

    @Override
    @Deprecated
    public Long firstKey() {
        return this.firstLongKey();
    }

    @Override
    @Deprecated
    public Long lastKey() {
        return this.lastLongKey();
    }

    @Override
    public LongSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public ReferenceCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
        return this.long2ReferenceEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, V>> i) {
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
            return new ValuesIterator(AbstractLong2ReferenceSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractLong2ReferenceSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2ReferenceSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, V>> i) {
            this.i = i;
        }

        @Override
        public long nextLong() {
            return (Long)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public long previousLong() {
            return (Long)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractLongSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(long k) {
            return AbstractLong2ReferenceSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2ReferenceSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2ReferenceSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2ReferenceSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2ReferenceSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2ReferenceSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2ReferenceSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2ReferenceSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2ReferenceSortedMap.this.entrySet().iterator(new AbstractLong2ReferenceMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2ReferenceSortedMap.this.entrySet().iterator());
        }
    }
}

