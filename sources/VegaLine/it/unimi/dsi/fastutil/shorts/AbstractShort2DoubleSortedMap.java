/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2DoubleMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2DoubleSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2DoubleSortedMap
extends AbstractShort2DoubleMap
implements Short2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2DoubleSortedMap() {
    }

    @Override
    @Deprecated
    public Short2DoubleSortedMap headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2DoubleSortedMap tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2DoubleSortedMap subMap(Short from, Short to) {
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
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, Double>> entrySet() {
        return this.short2DoubleEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Double>> i) {
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
            return new ValuesIterator(AbstractShort2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractShort2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Double>> i) {
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
            return AbstractShort2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2DoubleSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2DoubleSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2DoubleSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2DoubleSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2DoubleSortedMap.this.entrySet().iterator(new AbstractShort2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

