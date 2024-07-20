/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.longs.AbstractLong2ShortMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ShortSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Map;

public abstract class AbstractLong2ShortSortedMap
extends AbstractLong2ShortMap
implements Long2ShortSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2ShortSortedMap() {
    }

    @Override
    @Deprecated
    public Long2ShortSortedMap headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2ShortSortedMap tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2ShortSortedMap subMap(Long from, Long to) {
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
    public ShortCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, Short>> entrySet() {
        return this.long2ShortEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractShortIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Short>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, Short>> i) {
            this.i = i;
        }

        @Override
        public short nextShort() {
            return (Short)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractShortCollection {
        protected ValuesCollection() {
        }

        @Override
        public ShortIterator iterator() {
            return new ValuesIterator(AbstractLong2ShortSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(short k) {
            return AbstractLong2ShortSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2ShortSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Short>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, Short>> i) {
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
            return AbstractLong2ShortSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2ShortSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2ShortSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2ShortSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2ShortSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2ShortSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2ShortSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2ShortSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2ShortSortedMap.this.entrySet().iterator(new AbstractLong2ShortMap.BasicEntry(from, 0)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2ShortSortedMap.this.entrySet().iterator());
        }
    }
}

