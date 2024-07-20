/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ShortMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2ShortSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Map;

public abstract class AbstractInt2ShortSortedMap
extends AbstractInt2ShortMap
implements Int2ShortSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2ShortSortedMap() {
    }

    @Override
    @Deprecated
    public Int2ShortSortedMap headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2ShortSortedMap tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2ShortSortedMap subMap(Integer from, Integer to) {
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
    public ShortCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, Short>> entrySet() {
        return this.int2ShortEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractShortIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Short>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Short>> i) {
            this.i = i;
        }

        @Override
        public short nextShort() {
            return (Short)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractShortCollection {
        protected ValuesCollection() {
        }

        @Override
        public ShortIterator iterator() {
            return new ValuesIterator(AbstractInt2ShortSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(short k) {
            return AbstractInt2ShortSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2ShortSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Short>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Short>> i) {
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
            return AbstractInt2ShortSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2ShortSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2ShortSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2ShortSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2ShortSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2ShortSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2ShortSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2ShortSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2ShortSortedMap.this.entrySet().iterator(new AbstractInt2ShortMap.BasicEntry(from, 0)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2ShortSortedMap.this.entrySet().iterator());
        }
    }
}

