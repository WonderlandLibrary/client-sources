/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractDouble2BooleanSortedMap
extends AbstractDouble2BooleanMap
implements Double2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2BooleanSortedMap() {
    }

    @Override
    @Deprecated
    public Double2BooleanSortedMap headMap(Double to) {
        return this.headMap((double)to);
    }

    @Override
    @Deprecated
    public Double2BooleanSortedMap tailMap(Double from) {
        return this.tailMap((double)from);
    }

    @Override
    @Deprecated
    public Double2BooleanSortedMap subMap(Double from, Double to) {
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
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Double, Boolean>> entrySet() {
        return this.double2BooleanEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Boolean>> i) {
            this.i = i;
        }

        @Override
        public boolean nextBoolean() {
            return (Boolean)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractBooleanCollection {
        protected ValuesCollection() {
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(AbstractDouble2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractDouble2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractDouble2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractDoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Boolean>> i) {
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
            return AbstractDouble2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractDouble2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2BooleanSortedMap.this.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return AbstractDouble2BooleanSortedMap.this.comparator();
        }

        @Override
        public double firstDouble() {
            return AbstractDouble2BooleanSortedMap.this.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return AbstractDouble2BooleanSortedMap.this.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double to) {
            return AbstractDouble2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double from) {
            return AbstractDouble2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double from, double to) {
            return AbstractDouble2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double from) {
            return new KeySetIterator(AbstractDouble2BooleanSortedMap.this.entrySet().iterator(new AbstractDouble2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractDouble2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

