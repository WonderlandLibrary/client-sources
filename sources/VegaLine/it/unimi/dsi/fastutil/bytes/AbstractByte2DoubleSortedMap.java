/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2DoubleMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2DoubleSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractByte2DoubleSortedMap
extends AbstractByte2DoubleMap
implements Byte2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2DoubleSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2DoubleSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2DoubleSortedMap subMap(Byte from, Byte to) {
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
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Double>> entrySet() {
        return this.byte2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Double>> i) {
            this.i = i;
        }

        @Override
        public double nextDouble() {
            return (Double)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractDoubleCollection {
        protected ValuesCollection() {
        }

        @Override
        public DoubleIterator iterator() {
            return new ValuesIterator(AbstractByte2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractByte2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Double>> i) {
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
            return AbstractByte2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2DoubleSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2DoubleSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2DoubleSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2DoubleSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2DoubleSortedMap.this.entrySet().iterator(new AbstractByte2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

