/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2ByteMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteSortedMap;
import it.unimi.dsi.fastutil.longs.Long2ByteSortedMaps;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongComparator;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractLong2ByteSortedMap
extends AbstractLong2ByteMap
implements Long2ByteSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2ByteSortedMap() {
    }

    @Override
    public LongSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public ByteCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public LongSet keySet() {
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
    implements ByteIterator {
        protected final ObjectBidirectionalIterator<Long2ByteMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Long2ByteMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public byte nextByte() {
            return ((Long2ByteMap.Entry)this.i.next()).getByteValue();
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
    extends AbstractByteCollection {
        final AbstractLong2ByteSortedMap this$0;

        protected ValuesCollection(AbstractLong2ByteSortedMap abstractLong2ByteSortedMap) {
            this.this$0 = abstractLong2ByteSortedMap;
        }

        @Override
        public ByteIterator iterator() {
            return new ValuesIterator(Long2ByteSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(byte by) {
            return this.this$0.containsValue(by);
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
    implements LongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Long2ByteMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Long2ByteMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public long nextLong() {
            return ((Long2ByteMap.Entry)this.i.next()).getLongKey();
        }

        @Override
        public long previousLong() {
            return ((Long2ByteMap.Entry)this.i.previous()).getLongKey();
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
    extends AbstractLongSortedSet {
        final AbstractLong2ByteSortedMap this$0;

        protected KeySet(AbstractLong2ByteSortedMap abstractLong2ByteSortedMap) {
            this.this$0 = abstractLong2ByteSortedMap;
        }

        @Override
        public boolean contains(long l) {
            return this.this$0.containsKey(l);
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
        public LongComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public long firstLong() {
            return this.this$0.firstLongKey();
        }

        @Override
        public long lastLong() {
            return this.this$0.lastLongKey();
        }

        @Override
        public LongSortedSet headSet(long l) {
            return this.this$0.headMap(l).keySet();
        }

        @Override
        public LongSortedSet tailSet(long l) {
            return this.this$0.tailMap(l).keySet();
        }

        @Override
        public LongSortedSet subSet(long l, long l2) {
            return this.this$0.subMap(l, l2).keySet();
        }

        @Override
        public LongBidirectionalIterator iterator(long l) {
            return new KeySetIterator(this.this$0.long2ByteEntrySet().iterator(new AbstractLong2ByteMap.BasicEntry(l, 0)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(Long2ByteSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public LongIterator iterator() {
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

