/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2IntMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2IntSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractInt2IntSortedMap
extends AbstractInt2IntMap
implements Int2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Int2IntSortedMap headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2IntSortedMap tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2IntSortedMap subMap(Integer from, Integer to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, Integer>> entrySet() {
        return this.int2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Integer>> i) {
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
            return new ValuesIterator(AbstractInt2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractInt2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Integer>> i) {
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
            return AbstractInt2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2IntSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2IntSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2IntSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2IntSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2IntSortedMap.this.entrySet().iterator(new AbstractInt2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2IntSortedMap.this.entrySet().iterator());
        }
    }
}

