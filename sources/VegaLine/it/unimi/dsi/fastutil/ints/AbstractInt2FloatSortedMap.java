/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.ints.AbstractInt2FloatMap;
import it.unimi.dsi.fastutil.ints.AbstractIntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.AbstractIntSortedSet;
import it.unimi.dsi.fastutil.ints.Int2FloatSortedMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractInt2FloatSortedMap
extends AbstractInt2FloatMap
implements Int2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractInt2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Int2FloatSortedMap headMap(Integer to) {
        return this.headMap((int)to);
    }

    @Override
    @Deprecated
    public Int2FloatSortedMap tailMap(Integer from) {
        return this.tailMap((int)from);
    }

    @Override
    @Deprecated
    public Int2FloatSortedMap subMap(Integer from, Integer to) {
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
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Integer, Float>> entrySet() {
        return this.int2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Float>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getValue()).floatValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractFloatCollection {
        protected ValuesCollection() {
        }

        @Override
        public FloatIterator iterator() {
            return new ValuesIterator(AbstractInt2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractInt2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractInt2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractIntBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Integer, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Integer, Float>> i) {
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
            return AbstractInt2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractInt2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractInt2FloatSortedMap.this.clear();
        }

        @Override
        public IntComparator comparator() {
            return AbstractInt2FloatSortedMap.this.comparator();
        }

        @Override
        public int firstInt() {
            return AbstractInt2FloatSortedMap.this.firstIntKey();
        }

        @Override
        public int lastInt() {
            return AbstractInt2FloatSortedMap.this.lastIntKey();
        }

        @Override
        public IntSortedSet headSet(int to) {
            return AbstractInt2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public IntSortedSet tailSet(int from) {
            return AbstractInt2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public IntSortedSet subSet(int from, int to) {
            return AbstractInt2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public IntBidirectionalIterator iterator(int from) {
            return new KeySetIterator(AbstractInt2FloatSortedMap.this.entrySet().iterator(new AbstractInt2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractInt2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

