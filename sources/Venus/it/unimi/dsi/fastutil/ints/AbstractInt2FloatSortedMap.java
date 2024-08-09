/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2FloatMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2FloatMap;
import it.unimi.dsi.fastutil.ints.Int2FloatSortedMap;
import it.unimi.dsi.fastutil.ints.Int2FloatSortedMaps;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractInt2FloatSortedMap
extends AbstractInt2FloatMap
implements Int2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2FloatSortedMap() {
    }

    @Override
    public IntSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public FloatCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public IntSet keySet() {
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
    implements FloatIterator {
        protected final ObjectBidirectionalIterator<Int2FloatMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Int2FloatMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public float nextFloat() {
            return ((Int2FloatMap.Entry)this.i.next()).getFloatValue();
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
    extends AbstractFloatCollection {
        final AbstractInt2FloatSortedMap this$0;

        protected ValuesCollection(AbstractInt2FloatSortedMap abstractInt2FloatSortedMap) {
            this.this$0 = abstractInt2FloatSortedMap;
        }

        @Override
        public FloatIterator iterator() {
            return new ValuesIterator(Int2FloatSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(float f) {
            return this.this$0.containsValue(f);
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
    implements IntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Int2FloatMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Int2FloatMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Int2FloatMap.Entry)this.i.next()).getIntKey();
        }

        @Override
        public int previousInt() {
            return ((Int2FloatMap.Entry)this.i.previous()).getIntKey();
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
    extends AbstractIntSortedSet {
        final AbstractInt2FloatSortedMap this$0;

        protected KeySet(AbstractInt2FloatSortedMap abstractInt2FloatSortedMap) {
            this.this$0 = abstractInt2FloatSortedMap;
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsKey(n);
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
        public IntComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public int firstInt() {
            return this.this$0.firstIntKey();
        }

        @Override
        public int lastInt() {
            return this.this$0.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int n) {
            return this.this$0.headMap(n).keySet();
        }

        @Override
        public IntSortedSet tailSet(int n) {
            return this.this$0.tailMap(n).keySet();
        }

        @Override
        public IntSortedSet subSet(int n, int n2) {
            return this.this$0.subMap(n, n2).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int n) {
            return new KeySetIterator(this.this$0.int2FloatEntrySet().iterator(new AbstractInt2FloatMap.BasicEntry(n, 0.0f)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(Int2FloatSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public IntIterator iterator() {
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

