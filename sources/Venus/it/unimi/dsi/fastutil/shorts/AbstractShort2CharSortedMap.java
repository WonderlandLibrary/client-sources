/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2CharMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2CharMap;
import it.unimi.dsi.fastutil.shorts.Short2CharSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2CharSortedMaps;
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
public abstract class AbstractShort2CharSortedMap
extends AbstractShort2CharMap
implements Short2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2CharSortedMap() {
    }

    @Override
    public ShortSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public CharCollection values() {
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
    implements CharIterator {
        protected final ObjectBidirectionalIterator<Short2CharMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Short2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public char nextChar() {
            return ((Short2CharMap.Entry)this.i.next()).getCharValue();
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
    extends AbstractCharCollection {
        final AbstractShort2CharSortedMap this$0;

        protected ValuesCollection(AbstractShort2CharSortedMap abstractShort2CharSortedMap) {
            this.this$0 = abstractShort2CharSortedMap;
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(Short2CharSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(char c) {
            return this.this$0.containsValue(c);
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
        protected final ObjectBidirectionalIterator<Short2CharMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Short2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public short nextShort() {
            return ((Short2CharMap.Entry)this.i.next()).getShortKey();
        }

        @Override
        public short previousShort() {
            return ((Short2CharMap.Entry)this.i.previous()).getShortKey();
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
        final AbstractShort2CharSortedMap this$0;

        protected KeySet(AbstractShort2CharSortedMap abstractShort2CharSortedMap) {
            this.this$0 = abstractShort2CharSortedMap;
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
            return new KeySetIterator(this.this$0.short2CharEntrySet().iterator(new AbstractShort2CharMap.BasicEntry(s, '\u0000')));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(Short2CharSortedMaps.fastIterator(this.this$0));
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

