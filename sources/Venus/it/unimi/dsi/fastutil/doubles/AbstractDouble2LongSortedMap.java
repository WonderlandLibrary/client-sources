/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.doubles.AbstractDouble2LongMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2LongMap;
import it.unimi.dsi.fastutil.doubles.Double2LongSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2LongSortedMaps;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.doubles.DoubleSortedSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractDouble2LongSortedMap
extends AbstractDouble2LongMap
implements Double2LongSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2LongSortedMap() {
    }

    @Override
    public DoubleSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public LongCollection values() {
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
    implements LongIterator {
        protected final ObjectBidirectionalIterator<Double2LongMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Double2LongMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public long nextLong() {
            return ((Double2LongMap.Entry)this.i.next()).getLongValue();
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
    extends AbstractLongCollection {
        final AbstractDouble2LongSortedMap this$0;

        protected ValuesCollection(AbstractDouble2LongSortedMap abstractDouble2LongSortedMap) {
            this.this$0 = abstractDouble2LongSortedMap;
        }

        @Override
        public LongIterator iterator() {
            return new ValuesIterator(Double2LongSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(long l) {
            return this.this$0.containsValue(l);
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
        protected final ObjectBidirectionalIterator<Double2LongMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Double2LongMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public double nextDouble() {
            return ((Double2LongMap.Entry)this.i.next()).getDoubleKey();
        }

        @Override
        public double previousDouble() {
            return ((Double2LongMap.Entry)this.i.previous()).getDoubleKey();
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
        final AbstractDouble2LongSortedMap this$0;

        protected KeySet(AbstractDouble2LongSortedMap abstractDouble2LongSortedMap) {
            this.this$0 = abstractDouble2LongSortedMap;
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
            return new KeySetIterator(this.this$0.double2LongEntrySet().iterator(new AbstractDouble2LongMap.BasicEntry(d, 0L)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(Double2LongSortedMaps.fastIterator(this.this$0));
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

