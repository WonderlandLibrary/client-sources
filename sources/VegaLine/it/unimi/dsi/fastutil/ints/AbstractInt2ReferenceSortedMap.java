/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.AbstractInt2ReferenceMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2ReferenceSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public abstract class AbstractInt2ReferenceSortedMap<V>
extends AbstractInt2ReferenceMap<V>
implements Int2ReferenceSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2ReferenceSortedMap() {
    }

    @Override
    @Deprecated
    public Int2ReferenceSortedMap<V> headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2ReferenceSortedMap<V> tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
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
    public ReferenceCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
        return this.int2ReferenceEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, V>> i) {
            this.i = i;
        }

        @Override
        public V next() {
            return ((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractReferenceCollection<V> {
        protected ValuesCollection() {
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(AbstractInt2ReferenceSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractInt2ReferenceSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2ReferenceSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, V>> i) {
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
            return AbstractInt2ReferenceSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2ReferenceSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2ReferenceSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2ReferenceSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2ReferenceSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2ReferenceSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2ReferenceSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2ReferenceSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2ReferenceSortedMap.this.entrySet().iterator(new AbstractInt2ReferenceMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2ReferenceSortedMap.this.entrySet().iterator());
        }
    }
}

