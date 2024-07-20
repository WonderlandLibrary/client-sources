/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ByteMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2ByteSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractByte2ByteSortedMap
extends AbstractByte2ByteMap
implements Byte2ByteSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2ByteSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2ByteSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2ByteSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2ByteSortedMap subMap(Byte from, Byte to) {
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
    public ByteCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Byte>> entrySet() {
        return this.byte2ByteEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractByteIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Byte>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Byte>> i) {
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
            return new ValuesIterator(AbstractByte2ByteSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(byte k) {
            return AbstractByte2ByteSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ByteSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Byte>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Byte>> i) {
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
            return AbstractByte2ByteSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ByteSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2ByteSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2ByteSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2ByteSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2ByteSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2ByteSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2ByteSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2ByteSortedMap.this.entrySet().iterator(new AbstractByte2ByteMap.BasicEntry(from, 0)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2ByteSortedMap.this.entrySet().iterator());
        }
    }
}

