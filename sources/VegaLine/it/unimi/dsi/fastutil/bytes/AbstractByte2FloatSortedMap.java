/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2FloatMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2FloatSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractByte2FloatSortedMap
extends AbstractByte2FloatMap
implements Byte2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2FloatSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2FloatSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2FloatSortedMap subMap(Byte from, Byte to) {
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
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Float>> entrySet() {
        return this.byte2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Float>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getValue()).floatValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractFloatCollection {
        protected ValuesCollection() {
        }

        @Override
        public FloatIterator iterator() {
            return new ValuesIterator(AbstractByte2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractByte2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Float>> i) {
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
            return AbstractByte2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2FloatSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2FloatSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2FloatSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2FloatSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2FloatSortedMap.this.entrySet().iterator(new AbstractByte2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

