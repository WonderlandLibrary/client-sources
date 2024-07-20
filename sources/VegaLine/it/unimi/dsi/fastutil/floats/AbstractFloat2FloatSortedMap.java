/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2FloatSortedMap
extends AbstractFloat2FloatMap
implements Float2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Float2FloatSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2FloatSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2FloatSortedMap subMap(Float from, Float to) {
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
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, Float>> entrySet() {
        return this.float2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, Float>> i) {
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
            return new ValuesIterator(AbstractFloat2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractFloat2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, Float>> i) {
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
            return AbstractFloat2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2FloatSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2FloatSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2FloatSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2FloatSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2FloatSortedMap.this.entrySet().iterator(new AbstractFloat2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

