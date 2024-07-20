/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2DoubleMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2DoubleSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2DoubleSortedMap
extends AbstractFloat2DoubleMap
implements Float2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Float2DoubleSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2DoubleSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2DoubleSortedMap subMap(Float from, Float to) {
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
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, Double>> entrySet() {
        return this.float2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, Double>> i) {
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
            return new ValuesIterator(AbstractFloat2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractFloat2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, Double>> i) {
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
            return AbstractFloat2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2DoubleSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2DoubleSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2DoubleSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2DoubleSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2DoubleSortedMap.this.entrySet().iterator(new AbstractFloat2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

