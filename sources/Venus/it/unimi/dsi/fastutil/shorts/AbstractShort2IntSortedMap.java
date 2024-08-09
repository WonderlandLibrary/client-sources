/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2IntMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2IntMap;
import it.unimi.dsi.fastutil.shorts.Short2IntSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2IntSortedMaps;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import it.unimi.dsi.fastutil.shorts.ShortIterator;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import it.unimi.dsi.fastutil.shorts.ShortSortedSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractShort2IntSortedMap
extends AbstractShort2IntMap
implements Short2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2IntSortedMap() {
    }

    @Override
    public ShortSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public IntCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public ShortSet keySet() {
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
    implements IntIterator {
        protected final ObjectBidirectionalIterator<Short2IntMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Short2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Short2IntMap.Entry)this.i.next()).getIntValue();
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
    extends AbstractIntCollection {
        final AbstractShort2IntSortedMap this$0;

        protected ValuesCollection(AbstractShort2IntSortedMap abstractShort2IntSortedMap) {
            this.this$0 = abstractShort2IntSortedMap;
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(Short2IntSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(int n) {
            return this.this$0.containsValue(n);
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
    implements ShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Short2IntMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Short2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public short nextShort() {
            return ((Short2IntMap.Entry)this.i.next()).getShortKey();
        }

        @Override
        public short previousShort() {
            return ((Short2IntMap.Entry)this.i.previous()).getShortKey();
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
    extends AbstractShortSortedSet {
        final AbstractShort2IntSortedMap this$0;

        protected KeySet(AbstractShort2IntSortedMap abstractShort2IntSortedMap) {
            this.this$0 = abstractShort2IntSortedMap;
        }

        @Override
        public boolean contains(short s) {
            return this.this$0.containsKey(s);
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
        public ShortComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public short firstShort() {
            return this.this$0.firstShortKey();
        }

        @Override
        public short lastShort() {
            return this.this$0.lastShortKey();
        }

        @Override
        public ShortSortedSet headSet(short s) {
            return this.this$0.headMap(s).keySet();
        }

        @Override
        public ShortSortedSet tailSet(short s) {
            return this.this$0.tailMap(s).keySet();
        }

        @Override
        public ShortSortedSet subSet(short s, short s2) {
            return this.this$0.subMap(s, s2).keySet();
        }

        @Override
        public ShortBidirectionalIterator iterator(short s) {
            return new KeySetIterator(this.this$0.short2IntEntrySet().iterator(new AbstractShort2IntMap.BasicEntry(s, 0)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(Short2IntSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public ShortIterator iterator() {
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

