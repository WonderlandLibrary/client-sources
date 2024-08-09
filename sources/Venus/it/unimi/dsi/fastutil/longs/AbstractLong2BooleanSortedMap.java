/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.longs.AbstractLong2BooleanMap;
import it.unimi.dsi.fastutil.longs.AbstractLongSortedSet;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanSortedMaps;
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
public abstract class AbstractLong2BooleanSortedMap
extends AbstractLong2BooleanMap
implements Long2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractLong2BooleanSortedMap() {
    }

    @Override
    public LongSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public BooleanCollection values() {
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
    implements BooleanIterator {
        protected final ObjectBidirectionalIterator<Long2BooleanMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Long2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean nextBoolean() {
            return ((Long2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
        final AbstractLong2BooleanSortedMap this$0;

        protected ValuesCollection(AbstractLong2BooleanSortedMap abstractLong2BooleanSortedMap) {
            this.this$0 = abstractLong2BooleanSortedMap;
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(Long2BooleanSortedMaps.fastIterator(this.this$0));
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
    implements LongBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Long2BooleanMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Long2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public long nextLong() {
            return ((Long2BooleanMap.Entry)this.i.next()).getLongKey();
        }

        @Override
        public long previousLong() {
            return ((Long2BooleanMap.Entry)this.i.previous()).getLongKey();
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
        final AbstractLong2BooleanSortedMap this$0;

        protected KeySet(AbstractLong2BooleanSortedMap abstractLong2BooleanSortedMap) {
            this.this$0 = abstractLong2BooleanSortedMap;
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
            return new KeySetIterator(this.this$0.long2BooleanEntrySet().iterator(new AbstractLong2BooleanMap.BasicEntry(l, false)));
        }

        @Override
        public LongBidirectionalIterator iterator() {
            return new KeySetIterator(Long2BooleanSortedMaps.fastIterator(this.this$0));
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

