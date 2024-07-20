/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2LongMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2LongSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2LongSortedMap
extends AbstractFloat2LongMap
implements Float2LongSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2LongSortedMap() {
    }

    @Override
    @Deprecated
    public Float2LongSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2LongSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2LongSortedMap subMap(Float from, Float to) {
        return this.subMap(from.floatValue(), to.floatValue());
    }

    @Override
    @Deprecated
    public Float firstKey() {
        return Float.valueOf(this.firstFloatKey());
    }

    @Override
    @Deprecated
    public Float lastKey() {
        return Float.valueOf(this.lastFloatKey());
    }

    @Override
    public FloatSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public LongCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, Long>> entrySet() {
        return this.float2LongEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractLongIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Long>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, Long>> i) {
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
            return new ValuesIterator(AbstractFloat2LongSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(long k) {
            return AbstractFloat2LongSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2LongSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Long>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, Long>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getKey()).floatValue();
        }

        @Override
        public float previousFloat() {
            return ((Float)((Map.Entry)this.i.previous()).getKey()).floatValue();
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
    extends AbstractFloatSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(float k) {
            return AbstractFloat2LongSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2LongSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2LongSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2LongSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2LongSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2LongSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2LongSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2LongSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2LongSortedMap.this.entrySet().iterator(new AbstractFloat2LongMap.BasicEntry(from, 0L)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2LongSortedMap.this.entrySet().iterator());
        }
    }
}

