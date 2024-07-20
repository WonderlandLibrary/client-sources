/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ReferenceMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2ReferenceSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.objects.ReferenceCollection;
import java.util.Map;

public abstract class AbstractByte2ReferenceSortedMap<V>
extends AbstractByte2ReferenceMap<V>
implements Byte2ReferenceSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2ReferenceSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2ReferenceSortedMap<V> headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2ReferenceSortedMap<V> tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2ReferenceSortedMap<V> subMap(Byte from, Byte to) {
        return this.subMap((byte)from, (byte)to);
    }

    @Override
    @Deprecated
    public Byte firstKey() {
        return this.firstByteKey();
    }

    @Override
    @Deprecated
    public Byte lastKey() {
        return this.lastByteKey();
    }

    @Override
    public ByteSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public ReferenceCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, V>> entrySet() {
        return this.byte2ReferenceEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, V>> i) {
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
            return new ValuesIterator(AbstractByte2ReferenceSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractByte2ReferenceSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ReferenceSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, V>> i) {
            this.i = i;
        }

        @Override
        public byte nextByte() {
            return (Byte)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public byte previousByte() {
            return (Byte)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractByteSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(byte k) {
            return AbstractByte2ReferenceSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2ReferenceSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ReferenceSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2ReferenceSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2ReferenceSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2ReferenceSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2ReferenceSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2ReferenceSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2ReferenceSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2ReferenceSortedMap.this.entrySet().iterator(new AbstractByte2ReferenceMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2ReferenceSortedMap.this.entrySet().iterator());
        }
    }
}

