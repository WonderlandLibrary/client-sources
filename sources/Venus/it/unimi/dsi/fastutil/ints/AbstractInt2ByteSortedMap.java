/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2ByteMap;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2ByteMap;
import it.unimi.dsi.fastutil.ints.Int2ByteSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ByteSortedMaps;
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
public abstract class AbstractInt2ByteSortedMap
extends AbstractInt2ByteMap
implements Int2ByteSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2ByteSortedMap() {
    }

    @Override
    public IntSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public ByteCollection values() {
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
    implements ByteIterator {
        protected final ObjectBidirectionalIterator<Int2ByteMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Int2ByteMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public byte nextByte() {
            return ((Int2ByteMap.Entry)this.i.next()).getByteValue();
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
    extends AbstractByteCollection {
        final AbstractInt2ByteSortedMap this$0;

        protected ValuesCollection(AbstractInt2ByteSortedMap abstractInt2ByteSortedMap) {
            this.this$0 = abstractInt2ByteSortedMap;
        }

        @Override
        public ByteIterator iterator() {
            return new ValuesIterator(Int2ByteSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsValue(by);
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
        protected final ObjectBidirectionalIterator<Int2ByteMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Int2ByteMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Int2ByteMap.Entry)this.i.next()).getIntKey();
        }

        @Override
        public int previousInt() {
            return ((Int2ByteMap.Entry)this.i.previous()).getIntKey();
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
        final AbstractInt2ByteSortedMap this$0;

        protected KeySet(AbstractInt2ByteSortedMap abstractInt2ByteSortedMap) {
            this.this$0 = abstractInt2ByteSortedMap;
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
            return new KeySetIterator(this.this$0.int2ByteEntrySet().iterator(new AbstractInt2ByteMap.BasicEntry(n, 0)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(Int2ByteSortedMaps.fastIterator(this.this$0));
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

