/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2CharSortedMap;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractDouble2CharSortedMap
extends AbstractDouble2CharMap
implements Double2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2CharSortedMap() {
    }

    @Override
    @Deprecated
    public Double2CharSortedMap headMap(Double to) {
        return this.headMap((double)to);
    }

    @Override
    @Deprecated
    public Double2CharSortedMap tailMap(Double from) {
        return this.tailMap((double)from);
    }

    @Override
    @Deprecated
    public Double2CharSortedMap subMap(Double from, Double to) {
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
    public CharCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Double, Character>> entrySet() {
        return this.double2CharEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractCharIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Character>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Double, Character>> i) {
            this.i = i;
        }

        @Override
        public char nextChar() {
            return ((Character)((Map.Entry)this.i.next()).getValue()).charValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractCharCollection {
        protected ValuesCollection() {
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(AbstractDouble2CharSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(char k) {
            return AbstractDouble2CharSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractDouble2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2CharSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractDoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Double, Character>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Double, Character>> i) {
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
            return AbstractDouble2CharSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractDouble2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractDouble2CharSortedMap.this.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return AbstractDouble2CharSortedMap.this.comparator();
        }

        @Override
        public double firstDouble() {
            return AbstractDouble2CharSortedMap.this.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return AbstractDouble2CharSortedMap.this.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double to) {
            return AbstractDouble2CharSortedMap.this.headMap(to).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double from) {
            return AbstractDouble2CharSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double from, double to) {
            return AbstractDouble2CharSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double from) {
            return new KeySetIterator(AbstractDouble2CharSortedMap.this.entrySet().iterator(new AbstractDouble2CharMap.BasicEntry(from, '\u0000')));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractDouble2CharSortedMap.this.entrySet().iterator());
        }
    }
}

