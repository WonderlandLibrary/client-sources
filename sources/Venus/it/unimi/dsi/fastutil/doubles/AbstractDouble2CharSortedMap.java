/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2CharMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2CharMap;
import it.unimi.dsi.fastutil.doubles.Double2CharSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2CharSortedMaps;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDouble2CharSortedMap
extends AbstractDouble2CharMap
implements Double2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2CharSortedMap() {
    }

    @Override
    public DoubleSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public CharCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public DoubleSet keySet() {
        return this.keySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    protected static class ValuesIterator
    implements CharIterator {
        protected final ObjectBidirectionalIterator<Double2CharMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Double2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public char nextChar() {
            return ((Double2CharMap.Entry)this.i.next()).getCharValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class ValuesCollection
    extends AbstractCharCollection {
        final AbstractDouble2CharSortedMap this$0;

        protected ValuesCollection(AbstractDouble2CharSortedMap abstractDouble2CharSortedMap) {
            this.this$0 = abstractDouble2CharSortedMap;
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(Double2CharSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(char c) {
            return this.this$0.containsValue(c);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    protected static class KeySetIterator
    implements DoubleBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Double2CharMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Double2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public double nextDouble() {
            return ((Double2CharMap.Entry)this.i.next()).getDoubleKey();
        }

        @Override
        public double previousDouble() {
            return ((Double2CharMap.Entry)this.i.previous()).getDoubleKey();
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class KeySet
    extends AbstractDoubleSortedSet {
        final AbstractDouble2CharSortedMap this$0;

        protected KeySet(AbstractDouble2CharSortedMap abstractDouble2CharSortedMap) {
            this.this$0 = abstractDouble2CharSortedMap;
        }

        @Override
        public boolean contains(double d) {
            return this.this$0.containsKey(d);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public DoubleComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public double firstDouble() {
            return this.this$0.firstDoubleKey();
        }

        @Override
        public double lastDouble() {
            return this.this$0.lastDoubleKey();
        }

        @Override
        public DoubleSortedSet headSet(double d) {
            return this.this$0.headMap(d).keySet();
        }

        @Override
        public DoubleSortedSet tailSet(double d) {
            return this.this$0.tailMap(d).keySet();
        }

        @Override
        public DoubleSortedSet subSet(double d, double d2) {
            return this.this$0.subMap(d, d2).keySet();
        }

        @Override
        public DoubleBidirectionalIterator iterator(double d) {
            return new KeySetIterator(this.this$0.double2CharEntrySet().iterator(new AbstractDouble2CharMap.BasicEntry(d, '\u0000')));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(Double2CharSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public DoubleIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

