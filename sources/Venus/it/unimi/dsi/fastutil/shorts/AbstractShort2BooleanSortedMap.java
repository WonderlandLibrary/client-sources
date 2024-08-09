/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.AbstractShort2BooleanMap;
import it.unimi.dsi.fastutil.shorts.AbstractShortSortedSet;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanSortedMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanSortedMaps;
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
public abstract class AbstractShort2BooleanSortedMap
extends AbstractShort2BooleanMap
implements Short2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractShort2BooleanSortedMap() {
    }

    @Override
    public ShortSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public BooleanCollection values() {
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
    implements BooleanIterator {
        protected final ObjectBidirectionalIterator<Short2BooleanMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Short2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public boolean nextBoolean() {
            return ((Short2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
        final AbstractShort2BooleanSortedMap this$0;

        protected ValuesCollection(AbstractShort2BooleanSortedMap abstractShort2BooleanSortedMap) {
            this.this$0 = abstractShort2BooleanSortedMap;
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(Short2BooleanSortedMaps.fastIterator(this.this$0));
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
    implements ShortBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Short2BooleanMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Short2BooleanMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public short nextShort() {
            return ((Short2BooleanMap.Entry)this.i.next()).getShortKey();
        }

        @Override
        public short previousShort() {
            return ((Short2BooleanMap.Entry)this.i.previous()).getShortKey();
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
        final AbstractShort2BooleanSortedMap this$0;

        protected KeySet(AbstractShort2BooleanSortedMap abstractShort2BooleanSortedMap) {
            this.this$0 = abstractShort2BooleanSortedMap;
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
            return new KeySetIterator(this.this$0.short2BooleanEntrySet().iterator(new AbstractShort2BooleanMap.BasicEntry(s, false)));
        }

        @Override
        public ShortBidirectionalIterator iterator() {
            return new KeySetIterator(Short2BooleanSortedMaps.fastIterator(this.this$0));
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

