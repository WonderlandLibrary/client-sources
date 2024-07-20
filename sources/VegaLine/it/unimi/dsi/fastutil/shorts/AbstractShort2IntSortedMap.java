/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2IntSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2IntSortedMap
extends AbstractShort2IntMap
implements Short2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Short2IntSortedMap headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2IntSortedMap tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2IntSortedMap subMap(Short from, Short to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, Integer>> entrySet() {
        return this.short2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Integer>> i) {
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
            return new ValuesIterator(AbstractShort2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractShort2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Integer>> i) {
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
            return AbstractShort2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2IntSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2IntSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2IntSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2IntSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2IntSortedMap.this.entrySet().iterator(new AbstractShort2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2IntSortedMap.this.entrySet().iterator());
        }
    }
}

