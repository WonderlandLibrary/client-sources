/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2IntMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2IntSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractLong2IntSortedMap
extends AbstractLong2IntMap
implements Long2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Long2IntSortedMap headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2IntSortedMap tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2IntSortedMap subMap(Long from, Long to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, Integer>> entrySet() {
        return this.long2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, Integer>> i) {
            this.i = i;
        }

        @Override
        public int nextInt() {
            return (Integer)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractIntCollection {
        protected ValuesCollection() {
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(AbstractLong2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractLong2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, Integer>> i) {
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
            return AbstractLong2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2IntSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2IntSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2IntSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2IntSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2IntSortedMap.this.entrySet().iterator(new AbstractLong2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2IntSortedMap.this.entrySet().iterator());
        }
    }
}

