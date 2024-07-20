/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanSortedMap;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractByte2BooleanSortedMap
extends AbstractByte2BooleanMap
implements Byte2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2BooleanSortedMap() {
    }

    @Override
    @Deprecated
    public Byte2BooleanSortedMap headMap(Byte to) {
        return this.headMap((byte)to);
    }

    @Override
    @Deprecated
    public Byte2BooleanSortedMap tailMap(Byte from) {
        return this.tailMap((byte)from);
    }

    @Override
    @Deprecated
    public Byte2BooleanSortedMap subMap(Byte from, Byte to) {
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
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Byte, Boolean>> entrySet() {
        return this.byte2BooleanEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Boolean>> i) {
            this.i = i;
        }

        @Override
        public boolean nextBoolean() {
            return (Boolean)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractBooleanCollection {
        protected ValuesCollection() {
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(AbstractByte2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractByte2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractByte2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Byte, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Byte, Boolean>> i) {
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
            return AbstractByte2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractByte2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractByte2BooleanSortedMap.this.clear();
        }

        @Override
        public ByteComparator comparator() {
            return AbstractByte2BooleanSortedMap.this.comparator();
        }

        @Override
        public byte firstByte() {
            return AbstractByte2BooleanSortedMap.this.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return AbstractByte2BooleanSortedMap.this.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte to) {
            return AbstractByte2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte from) {
            return AbstractByte2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte from, byte to) {
            return AbstractByte2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte from) {
            return new KeySetIterator(AbstractByte2BooleanSortedMap.this.entrySet().iterator(new AbstractByte2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractByte2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

