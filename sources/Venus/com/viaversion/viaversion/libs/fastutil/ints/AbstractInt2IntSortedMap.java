/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractInt2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSortedSet;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntSortedMaps;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractInt2IntSortedMap
extends AbstractInt2IntMap
implements Int2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2IntSortedMap() {
    }

    @Override
    public IntSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public IntCollection values() {
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class KeySet
    extends AbstractIntSortedSet {
        final AbstractInt2IntSortedMap this$0;

        protected KeySet(AbstractInt2IntSortedMap abstractInt2IntSortedMap) {
            this.this$0 = abstractInt2IntSortedMap;
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
            return new KeySetIterator(this.this$0.int2IntEntrySet().iterator(new AbstractInt2IntMap.BasicEntry(n, 0)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(Int2IntSortedMaps.fastIterator(this.this$0));
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class ValuesCollection
    extends AbstractIntCollection {
        final AbstractInt2IntSortedMap this$0;

        protected ValuesCollection(AbstractInt2IntSortedMap abstractInt2IntSortedMap) {
            this.this$0 = abstractInt2IntSortedMap;
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(Int2IntSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsValue(n);
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

    protected static class ValuesIterator
    implements IntIterator {
        protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Int2IntMap.Entry)this.i.next()).getIntValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected static class KeySetIterator
    implements IntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Int2IntMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Int2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Int2IntMap.Entry)this.i.next()).getIntKey();
        }

        @Override
        public int previousInt() {
            return ((Int2IntMap.Entry)this.i.previous()).getIntKey();
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
}

