/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2DoubleMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2DoubleSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractDouble2DoubleSortedMap
extends AbstractDouble2DoubleMap
implements Double2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Double2DoubleSortedMap headMap(Double to) {
        return this.headMap((double)to);
    }

    @Override
    @Deprecated
    public Double2DoubleSortedMap tailMap(Double from) {
        return this.tailMap((double)from);
    }

    @Override
    @Deprecated
    public Double2DoubleSortedMap subMap(Double from, Double to) {
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
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Double, Double>> entrySet() {
        return this.double2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Double>> i) {
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
            return new ValuesIterator(AbstractDouble2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractDouble2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractDouble2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractDoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Double>> i) {
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
            return AbstractDouble2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractDouble2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2DoubleSortedMap.this.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return AbstractDouble2DoubleSortedMap.this.comparator();
        }

        @Override
        public double firstDouble() {
            return AbstractDouble2DoubleSortedMap.this.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return AbstractDouble2DoubleSortedMap.this.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double to) {
            return AbstractDouble2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double from) {
            return AbstractDouble2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double from, double to) {
            return AbstractDouble2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double from) {
            return new KeySetIterator(AbstractDouble2DoubleSortedMap.this.entrySet().iterator(new AbstractDouble2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractDouble2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

