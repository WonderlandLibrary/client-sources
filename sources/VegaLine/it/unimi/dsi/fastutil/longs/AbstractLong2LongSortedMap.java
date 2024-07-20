/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2LongMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2LongSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractLong2LongSortedMap
extends AbstractLong2LongMap
implements Long2LongSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2LongSortedMap() {
    }

    @Override
    @Deprecated
    public Long2LongSortedMap headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2LongSortedMap tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2LongSortedMap subMap(Long from, Long to) {
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
    public LongCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, Long>> entrySet() {
        return this.long2LongEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractLongIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Long>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, Long>> i) {
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
            return new ValuesIterator(AbstractLong2LongSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(long k) {
            return AbstractLong2LongSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2LongSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Long>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, Long>> i) {
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
            return AbstractLong2LongSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2LongSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2LongSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2LongSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2LongSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2LongSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2LongSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2LongSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2LongSortedMap.this.entrySet().iterator(new AbstractLong2LongMap.BasicEntry(from, 0L)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2LongSortedMap.this.entrySet().iterator());
        }
    }
}

