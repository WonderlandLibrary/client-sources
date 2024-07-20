/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2DoubleMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2DoubleSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractLong2DoubleSortedMap
extends AbstractLong2DoubleMap
implements Long2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Long2DoubleSortedMap headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2DoubleSortedMap tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2DoubleSortedMap subMap(Long from, Long to) {
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
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, Double>> entrySet() {
        return this.long2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, Double>> i) {
            this.i = i;
        }

        @Override
        public double nextDouble() {
            return (Double)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractDoubleCollection {
        protected ValuesCollection() {
        }

        @Override
        public DoubleIterator iterator() {
            return new ValuesIterator(AbstractLong2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractLong2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, Double>> i) {
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
            return AbstractLong2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2DoubleSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2DoubleSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2DoubleSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2DoubleSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2DoubleSortedMap.this.entrySet().iterator(new AbstractLong2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

