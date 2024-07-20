/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2FloatMap;
import it.unimi.dsi.fastutil.longs.AbstractLongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2FloatSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractLong2FloatSortedMap
extends AbstractLong2FloatMap
implements Long2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Long2FloatSortedMap headMap(Long to) {
        return this.headMap((long)to);
    }

    @Override
    @Deprecated
    public Long2FloatSortedMap tailMap(Long from) {
        return this.tailMap((long)from);
    }

    @Override
    @Deprecated
    public Long2FloatSortedMap subMap(Long from, Long to) {
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
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Long, Float>> entrySet() {
        return this.long2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Long, Float>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getValue()).floatValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractFloatCollection {
        protected ValuesCollection() {
        }

        @Override
        public FloatIterator iterator() {
            return new ValuesIterator(AbstractLong2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractLong2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractLong2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractLongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Long, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Long, Float>> i) {
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
            return AbstractLong2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractLong2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractLong2FloatSortedMap.this.clear();
        }

        @Override
        public LongComparator comparator() {
            return AbstractLong2FloatSortedMap.this.comparator();
        }

        @Override
        public long firstLong() {
            return AbstractLong2FloatSortedMap.this.firstLongKey();
        }

        @Override
        public long lastLong() {
            return AbstractLong2FloatSortedMap.this.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long to) {
            return AbstractLong2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public LongSortedSet tailSet(long from) {
            return AbstractLong2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public LongSortedSet subSet(long from, long to) {
            return AbstractLong2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long from) {
            return new KeySetIterator(AbstractLong2FloatSortedMap.this.entrySet().iterator(new AbstractLong2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractLong2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

