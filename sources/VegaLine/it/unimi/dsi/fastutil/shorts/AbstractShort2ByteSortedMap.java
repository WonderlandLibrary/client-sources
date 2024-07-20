/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ByteMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ByteSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2ByteSortedMap
extends AbstractShort2ByteMap
implements Short2ByteSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2ByteSortedMap() {
    }

    @Override
    @Deprecated
    public Short2ByteSortedMap headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2ByteSortedMap tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2ByteSortedMap subMap(Short from, Short to) {
        return this.subMap((short)from, (short)to);
    }

    @Override
    @Deprecated
    public Short firstKey() {
        return this.firstShortKey();
    }

    @Override
    @Deprecated
    public Short lastKey() {
        return this.lastShortKey();
    }

    @Override
    public ShortSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public ByteCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet() {
        return this.short2ByteEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractByteIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i) {
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
            return new ValuesIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(byte k) {
            return AbstractShort2ByteSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ByteSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i) {
            this.i = i;
        }

        @Override
        public short nextShort() {
            return (Short)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public short previousShort() {
            return (Short)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractShortSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(short k) {
            return AbstractShort2ByteSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ByteSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2ByteSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2ByteSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2ByteSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2ByteSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2ByteSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2ByteSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator(new AbstractShort2ByteMap.BasicEntry(from, 0)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator());
        }
    }
}

