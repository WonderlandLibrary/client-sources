/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2FloatMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2FloatSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractDouble2FloatSortedMap
extends AbstractDouble2FloatMap
implements Double2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Double2FloatSortedMap headMap(Double to) {
        return this.headMap((double)to);
    }

    @Override
    @Deprecated
    public Double2FloatSortedMap tailMap(Double from) {
        return this.tailMap((double)from);
    }

    @Override
    @Deprecated
    public Double2FloatSortedMap subMap(Double from, Double to) {
        return this.subMap((double)from, (double)to);
    }

    @Override
    @Deprecated
    public Double firstKey() {
        return this.firstDoubleKey();
    }

    @Override
    @Deprecated
    public Double lastKey() {
        return this.lastDoubleKey();
    }

    @Override
    public DoubleSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Double, Float>> entrySet() {
        return this.double2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Float>> i) {
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
            return new ValuesIterator(AbstractDouble2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractDouble2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractDouble2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractDoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Float>> i) {
            this.i = i;
        }

        @Override
        public double nextDouble() {
            return (Double)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public double previousDouble() {
            return (Double)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractDoubleSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(double k) {
            return AbstractDouble2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractDouble2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2FloatSortedMap.this.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return AbstractDouble2FloatSortedMap.this.comparator();
        }

        @Override
        public double firstDouble() {
            return AbstractDouble2FloatSortedMap.this.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return AbstractDouble2FloatSortedMap.this.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double to) {
            return AbstractDouble2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double from) {
            return AbstractDouble2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double from, double to) {
            return AbstractDouble2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double from) {
            return new KeySetIterator(AbstractDouble2FloatSortedMap.this.entrySet().iterator(new AbstractDouble2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractDouble2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

