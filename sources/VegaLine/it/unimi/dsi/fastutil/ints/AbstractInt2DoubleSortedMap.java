/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2DoubleMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2DoubleSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractInt2DoubleSortedMap
extends AbstractInt2DoubleMap
implements Int2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Int2DoubleSortedMap headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2DoubleSortedMap tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2DoubleSortedMap subMap(Integer from, Integer to) {
        return this.subMap((int)from, (int)to);
    }

    @Override
    @Deprecated
    public Integer firstKey() {
        return this.firstIntKey();
    }

    @Override
    @Deprecated
    public Integer lastKey() {
        return this.lastIntKey();
    }

    @Override
    public IntSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, Double>> entrySet() {
        return this.int2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Double>> i) {
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
            return new ValuesIterator(AbstractInt2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractInt2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Double>> i) {
            this.i = i;
        }

        @Override
        public int nextInt() {
            return (Integer)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public int previousInt() {
            return (Integer)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractIntSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(int k) {
            return AbstractInt2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2DoubleSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2DoubleSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2DoubleSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2DoubleSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2DoubleSortedMap.this.entrySet().iterator(new AbstractInt2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

