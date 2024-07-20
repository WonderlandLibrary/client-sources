/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2ShortMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ShortSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractReference2ShortSortedMap<K>
extends AbstractReference2ShortMap<K>
implements Reference2ShortSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractReference2ShortSortedMap() {
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public ShortCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Short>> entrySet() {
        return this.reference2ShortEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractShortIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Short>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Short>> i) {
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
            return new ValuesIterator(AbstractReference2ShortSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(short k) {
            return AbstractReference2ShortSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractReference2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2ShortSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Short>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Short>> i) {
            this.i = i;
        }

        @Override
        public K next() {
            return ((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractReferenceSortedSet<K> {
        protected KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return AbstractReference2ShortSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractReference2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2ShortSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractReference2ShortSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractReference2ShortSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractReference2ShortSortedMap.this.lastKey();
        }

        @Override
        public ReferenceSortedSet<K> headSet(K to) {
            return AbstractReference2ShortSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K from) {
            return AbstractReference2ShortSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K from, K to) {
            return AbstractReference2ShortSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractReference2ShortSortedMap.this.entrySet().iterator(new AbstractReference2ShortMap.BasicEntry(from, 0)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractReference2ShortSortedMap.this.entrySet().iterator());
        }
    }
}

