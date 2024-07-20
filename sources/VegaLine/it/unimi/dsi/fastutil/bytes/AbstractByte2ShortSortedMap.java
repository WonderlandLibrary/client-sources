/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2ShortSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Map;

public abstract class AbstractByte2ShortSortedMap
extends AbstractByte2ShortMap
implements Byte2ShortSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2ShortSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2ShortSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2ShortSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2ShortSortedMap subMap(Byte from, Byte to) {
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
    public ShortCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Short>> entrySet() {
        return this.byte2ShortEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractShortIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Short>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Short>> i) {
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
            return new ValuesIterator(AbstractByte2ShortSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(short k) {
            return AbstractByte2ShortSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ShortSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Short>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Short>> i) {
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
            return AbstractByte2ShortSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2ShortSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2ShortSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2ShortSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2ShortSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2ShortSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2ShortSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2ShortSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2ShortSortedMap.this.entrySet().iterator(new AbstractByte2ShortMap.BasicEntry(from, 0)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2ShortSortedMap.this.entrySet().iterator());
        }
    }
}

