/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractReference2ByteMap;
import it.unimi.dsi.fastutil.objects.AbstractReferenceSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Reference2ByteSortedMap;
import it.unimi.dsi.fastutil.objects.ReferenceSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractReference2ByteSortedMap<K>
extends AbstractReference2ByteMap<K>
implements Reference2ByteSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractReference2ByteSortedMap() {
    }

    @Override
    public ReferenceSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public ByteCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
        return this.reference2ByteEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractByteIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Byte>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Byte>> i) {
            this.i = i;
        }

        @Override
        public byte nextByte() {
            return (Byte)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractByteCollection {
        protected ValuesCollection() {
        }

        @Override
        public ByteIterator iterator() {
            return new ValuesIterator(AbstractReference2ByteSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(byte k) {
            return AbstractReference2ByteSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractReference2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2ByteSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Byte>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Byte>> i) {
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
            return AbstractReference2ByteSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractReference2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractReference2ByteSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractReference2ByteSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractReference2ByteSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractReference2ByteSortedMap.this.lastKey();
        }

        @Override
        public ReferenceSortedSet<K> headSet(K to) {
            return AbstractReference2ByteSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ReferenceSortedSet<K> tailSet(K from) {
            return AbstractReference2ByteSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ReferenceSortedSet<K> subSet(K from, K to) {
            return AbstractReference2ByteSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractReference2ByteSortedMap.this.entrySet().iterator(new AbstractReference2ByteMap.BasicEntry(from, 0)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractReference2ByteSortedMap.this.entrySet().iterator());
        }
    }
}

