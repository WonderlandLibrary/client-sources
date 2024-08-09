/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2IntMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2IntMap;
import it.unimi.dsi.fastutil.floats.Float2IntSortedMap;
import it.unimi.dsi.fastutil.floats.Float2IntSortedMaps;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloat2IntSortedMap
extends AbstractFloat2IntMap
implements Float2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2IntSortedMap() {
    }

    @Override
    public FloatSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public IntCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public FloatSet keySet() {
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
        protected final ObjectBidirectionalIterator<Float2IntMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Float2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public int nextInt() {
            return ((Float2IntMap.Entry)this.i.next()).getIntValue();
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
        final AbstractFloat2IntSortedMap this$0;

        protected ValuesCollection(AbstractFloat2IntSortedMap abstractFloat2IntSortedMap) {
            this.this$0 = abstractFloat2IntSortedMap;
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(Float2IntSortedMaps.fastIterator(this.this$0));
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
    implements FloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Float2IntMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Float2IntMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public float nextFloat() {
            return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
        }

        @Override
        public float previousFloat() {
            return ((Float2IntMap.Entry)this.i.previous()).getFloatKey();
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
    extends AbstractFloatSortedSet {
        final AbstractFloat2IntSortedMap this$0;

        protected KeySet(AbstractFloat2IntSortedMap abstractFloat2IntSortedMap) {
            this.this$0 = abstractFloat2IntSortedMap;
        }

        @Override
        public boolean contains(float f) {
            return this.this$0.containsKey(f);
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
        public FloatComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public float firstFloat() {
            return this.this$0.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return this.this$0.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float f) {
            return this.this$0.headMap(f).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float f) {
            return this.this$0.tailMap(f).keySet();
        }

        @Override
        public FloatSortedSet subSet(float f, float f2) {
            return this.this$0.subMap(f, f2).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float f) {
            return new KeySetIterator(this.this$0.float2IntEntrySet().iterator(new AbstractFloat2IntMap.BasicEntry(f, 0)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(Float2IntSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public FloatIterator iterator() {
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

