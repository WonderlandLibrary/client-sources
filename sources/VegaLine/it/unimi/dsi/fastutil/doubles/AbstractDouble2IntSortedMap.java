/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2IntMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2IntSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractDouble2IntSortedMap
extends AbstractDouble2IntMap
implements Double2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Double2IntSortedMap headMap(Double to) {
        return this.headMap((double)to);
    }

    @Override
    @Deprecated
    public Double2IntSortedMap tailMap(Double from) {
        return this.tailMap((double)from);
    }

    @Override
    @Deprecated
    public Double2IntSortedMap subMap(Double from, Double to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
        return this.double2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Integer>> i) {
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
            return new ValuesIterator(AbstractDouble2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractDouble2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractDouble2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractDoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Integer>> i) {
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
            return AbstractDouble2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractDouble2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2IntSortedMap.this.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return AbstractDouble2IntSortedMap.this.comparator();
        }

        @Override
        public double firstDouble() {
            return AbstractDouble2IntSortedMap.this.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return AbstractDouble2IntSortedMap.this.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double to) {
            return AbstractDouble2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double from) {
            return AbstractDouble2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double from, double to) {
            return AbstractDouble2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double from) {
            return new KeySetIterator(AbstractDouble2IntSortedMap.this.entrySet().iterator(new AbstractDouble2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractDouble2IntSortedMap.this.entrySet().iterator());
        }
    }
}

