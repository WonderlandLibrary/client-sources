/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.doubles.AbstractDouble2BooleanMap;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleSortedSet;
import it.unimi.dsi.fastutil.doubles.Double2BooleanMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMap;
import it.unimi.dsi.fastutil.doubles.Double2BooleanSortedMaps;
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
public abstract class AbstractDouble2BooleanSortedMap
extends AbstractDouble2BooleanMap
implements Double2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractDouble2BooleanSortedMap() {
    }

    @Override
    public DoubleSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public BooleanCollection values() {
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
    implements BooleanIterator {
        protected final ObjectBidirectionalIterator<Double2BooleanMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Double2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean nextBoolean() {
            return ((Double2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    extends AbstractBooleanCollection {
        final AbstractDouble2BooleanSortedMap this$0;

        protected ValuesCollection(AbstractDouble2BooleanSortedMap abstractDouble2BooleanSortedMap) {
            this.this$0 = abstractDouble2BooleanSortedMap;
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(Double2BooleanSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(boolean bl) {
            return this.this$0.containsValue(bl);
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
        protected final ObjectBidirectionalIterator<Double2BooleanMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Double2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public double nextDouble() {
            return ((Double2BooleanMap.Entry)this.i.next()).getDoubleKey();
        }

        @Override
        public double previousDouble() {
            return ((Double2BooleanMap.Entry)this.i.previous()).getDoubleKey();
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
        final AbstractDouble2BooleanSortedMap this$0;

        protected KeySet(AbstractDouble2BooleanSortedMap abstractDouble2BooleanSortedMap) {
            this.this$0 = abstractDouble2BooleanSortedMap;
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
            return new KeySetIterator(this.this$0.double2BooleanEntrySet().iterator(new AbstractDouble2BooleanMap.BasicEntry(d, false)));
        }

        @Override
        public DoubleBidirectionalIterator iterator() {
            return new KeySetIterator(Double2BooleanSortedMaps.fastIterator(this.this$0));
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

