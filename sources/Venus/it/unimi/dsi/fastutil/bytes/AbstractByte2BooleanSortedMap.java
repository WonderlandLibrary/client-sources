/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.bytes.AbstractByte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.AbstractByteSortedSet;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanSortedMap;
import it.unimi.dsi.fastutil.bytes.Byte2BooleanSortedMaps;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteSet;
import it.unimi.dsi.fastutil.bytes.ByteSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractByte2BooleanSortedMap
extends AbstractByte2BooleanMap
implements Byte2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractByte2BooleanSortedMap() {
    }

    @Override
    public ByteSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public BooleanCollection values() {
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
    implements BooleanIterator {
        protected final ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Byte2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean nextBoolean() {
            return ((Byte2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
    extends AbstractBooleanCollection {
        final AbstractByte2BooleanSortedMap this$0;

        protected ValuesCollection(AbstractByte2BooleanSortedMap abstractByte2BooleanSortedMap) {
            this.this$0 = abstractByte2BooleanSortedMap;
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(Byte2BooleanSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(boolean bl) {
            return this.this$0.containsValue(bl);
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
        protected final ObjectBidirectionalIterator<Byte2BooleanMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Byte2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public byte nextByte() {
            return ((Byte2BooleanMap.Entry)this.i.next()).getByteKey();
        }

        @Override
        public byte previousByte() {
            return ((Byte2BooleanMap.Entry)this.i.previous()).getByteKey();
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
        final AbstractByte2BooleanSortedMap this$0;

        protected KeySet(AbstractByte2BooleanSortedMap abstractByte2BooleanSortedMap) {
            this.this$0 = abstractByte2BooleanSortedMap;
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
            return new KeySetIterator(this.this$0.byte2BooleanEntrySet().iterator(new AbstractByte2BooleanMap.BasicEntry(by, false)));
        }

        @Override
        public ByteBidirectionalIterator iterator() {
            return new KeySetIterator(Byte2BooleanSortedMaps.fastIterator(this.this$0));
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

