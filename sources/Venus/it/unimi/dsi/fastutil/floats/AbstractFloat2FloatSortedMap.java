/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2FloatMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMap;
import it.unimi.dsi.fastutil.floats.Float2FloatSortedMaps;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatSet;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloat2FloatSortedMap
extends AbstractFloat2FloatMap
implements Float2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2FloatSortedMap() {
    }

    @Override
    public FloatSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public FloatCollection values() {
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
    implements FloatIterator {
        protected final ObjectBidirectionalIterator<Float2FloatMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Float2FloatMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public float nextFloat() {
            return ((Float2FloatMap.Entry)this.i.next()).getFloatValue();
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
    extends AbstractFloatCollection {
        final AbstractFloat2FloatSortedMap this$0;

        protected ValuesCollection(AbstractFloat2FloatSortedMap abstractFloat2FloatSortedMap) {
            this.this$0 = abstractFloat2FloatSortedMap;
        }

        @Override
        public FloatIterator iterator() {
            return new ValuesIterator(Float2FloatSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(float f) {
            return this.this$0.containsValue(f);
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
        protected final ObjectBidirectionalIterator<Float2FloatMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Float2FloatMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public float nextFloat() {
            return ((Float2FloatMap.Entry)this.i.next()).getFloatKey();
        }

        @Override
        public float previousFloat() {
            return ((Float2FloatMap.Entry)this.i.previous()).getFloatKey();
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
        final AbstractFloat2FloatSortedMap this$0;

        protected KeySet(AbstractFloat2FloatSortedMap abstractFloat2FloatSortedMap) {
            this.this$0 = abstractFloat2FloatSortedMap;
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
            return new KeySetIterator(this.this$0.float2FloatEntrySet().iterator(new AbstractFloat2FloatMap.BasicEntry(f, 0.0f)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(Float2FloatSortedMaps.fastIterator(this.this$0));
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

