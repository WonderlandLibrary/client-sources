/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2IntMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2IntSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractByte2IntSortedMap
extends AbstractByte2IntMap
implements Byte2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2IntSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2IntSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2IntSortedMap subMap(Byte from, Byte to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Integer>> entrySet() {
        return this.byte2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Integer>> i) {
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
            return new ValuesIterator(AbstractByte2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractByte2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Integer>> i) {
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
            return AbstractByte2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2IntSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2IntSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2IntSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2IntSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2IntSortedMap.this.entrySet().iterator(new AbstractByte2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2IntSortedMap.this.entrySet().iterator());
        }
    }
}

