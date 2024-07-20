/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2CharSortedMap;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Map;

public abstract class AbstractShort2CharSortedMap
extends AbstractShort2CharMap
implements Short2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2CharSortedMap() {
    }

    @Override
    @Deprecated
    public Short2CharSortedMap headMap(Short to) {
        return this.headMap((short)to);
    }

    @Override
    @Deprecated
    public Short2CharSortedMap tailMap(Short from) {
        return this.tailMap((short)from);
    }

    @Override
    @Deprecated
    public Short2CharSortedMap subMap(Short from, Short to) {
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
    public CharCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Short, Character>> entrySet() {
        return this.short2CharEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractCharIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Character>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Character>> i) {
            this.i = i;
        }

        @Override
        public char nextChar() {
            return ((Character)((Map.Entry)this.i.next()).getValue()).charValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractCharCollection {
        protected ValuesCollection() {
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(AbstractShort2CharSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(char k) {
            return AbstractShort2CharSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractShort2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2CharSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Short, Character>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Character>> i) {
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
            return AbstractShort2CharSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractShort2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractShort2CharSortedMap.this.clear();
        }

        @Override
        public ShortComparator comparator() {
            return AbstractShort2CharSortedMap.this.comparator();
        }

        @Override
        public short firstShort() {
            return AbstractShort2CharSortedMap.this.firstShortKey();
        }

        @Override
        public short lastShort() {
            return AbstractShort2CharSortedMap.this.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short to) {
            return AbstractShort2CharSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short from) {
            return AbstractShort2CharSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ShortSortedSet subSet(short from, short to) {
            return AbstractShort2CharSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short from) {
            return new KeySetIterator(AbstractShort2CharSortedMap.this.entrySet().iterator(new AbstractShort2CharMap.BasicEntry(from, '\u0000')));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractShort2CharSortedMap.this.entrySet().iterator());
        }
    }
}

