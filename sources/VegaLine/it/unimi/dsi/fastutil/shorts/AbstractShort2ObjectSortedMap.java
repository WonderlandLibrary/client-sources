/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2ObjectMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2ObjectSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2ObjectSortedMap<V>
extends AbstractShort2ObjectMap<V>
implements Short2ObjectSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2ObjectSortedMap() {
    }

    @Override
    @Deprecated
    public Short2ObjectSortedMap<V> headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2ObjectSortedMap<V> tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2ObjectSortedMap<V> subMap(Short from, Short to) {
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
    public ObjectCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
        return this.short2ObjectEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, V>> i) {
            this.i = i;
        }

        @Override
        public V next() {
            return ((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractObjectCollection<V> {
        protected ValuesCollection() {
        }

        @Override
        public ObjectIterator<V> iterator() {
            return new ValuesIterator(AbstractShort2ObjectSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractShort2ObjectSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ObjectSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, V>> i) {
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
            return AbstractShort2ObjectSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2ObjectSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2ObjectSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2ObjectSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2ObjectSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2ObjectSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2ObjectSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2ObjectSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2ObjectSortedMap.this.entrySet().iterator(new AbstractShort2ObjectMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2ObjectSortedMap.this.entrySet().iterator());
        }
    }
}

