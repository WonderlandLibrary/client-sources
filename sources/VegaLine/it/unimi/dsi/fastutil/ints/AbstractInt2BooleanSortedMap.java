/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2BooleanMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2BooleanSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractInt2BooleanSortedMap
extends AbstractInt2BooleanMap
implements Int2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2BooleanSortedMap() {
    }

    @Override
    @Deprecated
    public Int2BooleanSortedMap headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2BooleanSortedMap tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2BooleanSortedMap subMap(Integer from, Integer to) {
        return this.subMap((int)from, (int)to);
    }

    @Override
    @Deprecated
    public Integer firstKey() {
        return this.firstIntKey();
    }

    @Override
    @Deprecated
    public Integer lastKey() {
        return this.lastIntKey();
    }

    @Override
    public IntSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, Boolean>> entrySet() {
        return this.int2BooleanEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Boolean>> i) {
            this.i = i;
        }

        @Override
        public boolean nextBoolean() {
            return (Boolean)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractBooleanCollection {
        protected ValuesCollection() {
        }

        @Override
        public BooleanIterator iterator() {
            return new ValuesIterator(AbstractInt2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractInt2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Boolean>> i) {
            this.i = i;
        }

        @Override
        public int nextInt() {
            return (Integer)((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public int previousInt() {
            return (Integer)((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractIntSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(int k) {
            return AbstractInt2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2BooleanSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2BooleanSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2BooleanSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2BooleanSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2BooleanSortedMap.this.entrySet().iterator(new AbstractInt2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

