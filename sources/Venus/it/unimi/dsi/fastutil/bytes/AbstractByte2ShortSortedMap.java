/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.AbstractByte2ShortMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2ShortMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2ShortSortedMaps;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByte2ShortSortedMap
extends AbstractByte2ShortMap
implements Byte2ShortSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2ShortSortedMap() {
    }

    @Override
    public ByteSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public ShortCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public ByteSet keySet() {
        return this.keySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    protected static class ValuesIterator
    implements ShortIterator {
        protected final ObjectBidirectionalIterator<Byte2ShortMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Byte2ShortMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public short nextShort() {
            return ((Byte2ShortMap.Entry)this.i.next()).getShortValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class ValuesCollection
    extends AbstractShortCollection {
        final AbstractByte2ShortSortedMap this$0;

        protected ValuesCollection(AbstractByte2ShortSortedMap abstractByte2ShortSortedMap) {
            this.this$0 = abstractByte2ShortSortedMap;
        }

        @Override
        public ShortIterator iterator() {
            return new ValuesIterator(Byte2ShortSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(short s) {
            return this.this$0.containsValue(s);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    protected static class KeySetIterator
    implements ByteBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Byte2ShortMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Byte2ShortMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public byte nextByte() {
            return ((Byte2ShortMap.Entry)this.i.next()).getByteKey();
        }

        @Override
        public byte previousByte() {
            return ((Byte2ShortMap.Entry)this.i.previous()).getByteKey();
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class KeySet
    extends AbstractByteSortedSet {
        final AbstractByte2ShortSortedMap this$0;

        protected KeySet(AbstractByte2ShortSortedMap abstractByte2ShortSortedMap) {
            this.this$0 = abstractByte2ShortSortedMap;
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsKey(by);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public ByteComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public byte firstByte() {
            return this.this$0.firstByteKey();
        }

        @Override
        public byte lastByte() {
            return this.this$0.lastByteKey();
        }

        @Override
        public ByteSortedSet headSet(byte by) {
            return this.this$0.headMap(by).keySet();
        }

        @Override
        public ByteSortedSet tailSet(byte by) {
            return this.this$0.tailMap(by).keySet();
        }

        @Override
        public ByteSortedSet subSet(byte by, byte by2) {
            return this.this$0.subMap(by, by2).keySet();
        }

        @Override
        public ByteBidirectionalIterator iterator(byte by) {
            return new KeySetIterator(this.this$0.byte2ShortEntrySet().iterator(new AbstractByte2ShortMap.BasicEntry(by, 0)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(Byte2ShortSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public ByteIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

