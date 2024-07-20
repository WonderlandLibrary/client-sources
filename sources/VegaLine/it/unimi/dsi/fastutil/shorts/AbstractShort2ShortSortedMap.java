/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ShortMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.AbstractShortIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ShortSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2ShortSortedMap
extends AbstractShort2ShortMap
implements Short2ShortSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2ShortSortedMap() {
    }

    @Override
    @Deprecated
    public Short2ShortSortedMap headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2ShortSortedMap tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2ShortSortedMap subMap(Short from, Short to) {
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
    public ShortCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, Short>> entrySet() {
        return this.short2ShortEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractShortIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Short>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Short>> i) {
            this.i = i;
        }

        @Override
        public short nextShort() {
            return (Short)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractShortCollection {
        protected ValuesCollection() {
        }

        @Override
        public ShortIterator iterator() {
            return new ValuesIterator(AbstractShort2ShortSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(short k) {
            return AbstractShort2ShortSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ShortSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Short>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Short>> i) {
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
            return AbstractShort2ShortSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2ShortSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ShortSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2ShortSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2ShortSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2ShortSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2ShortSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2ShortSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2ShortSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2ShortSortedMap.this.entrySet().iterator(new AbstractShort2ShortMap.BasicEntry(from, 0)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2ShortSortedMap.this.entrySet().iterator());
        }
    }
}

