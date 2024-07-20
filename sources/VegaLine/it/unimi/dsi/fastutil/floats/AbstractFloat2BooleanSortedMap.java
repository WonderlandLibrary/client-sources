/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloat2BooleanMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2BooleanSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2BooleanSortedMap
extends AbstractFloat2BooleanMap
implements Float2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2BooleanSortedMap() {
    }

    @Override
    @Deprecated
    public Float2BooleanSortedMap headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2BooleanSortedMap tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2BooleanSortedMap subMap(Float from, Float to) {
        return this.subMap(from.floatValue(), to.floatValue());
    }

    @Override
    @Deprecated
    public Float firstKey() {
        return Float.valueOf(this.firstFloatKey());
    }

    @Override
    @Deprecated
    public Float lastKey() {
        return Float.valueOf(this.lastFloatKey());
    }

    @Override
    public FloatSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, Boolean>> entrySet() {
        return this.float2BooleanEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, Boolean>> i) {
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
            return new ValuesIterator(AbstractFloat2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractFloat2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, Boolean>> i) {
            this.i = i;
        }

        @Override
        public float nextFloat() {
            return ((Float)((Map.Entry)this.i.next()).getKey()).floatValue();
        }

        @Override
        public float previousFloat() {
            return ((Float)((Map.Entry)this.i.previous()).getKey()).floatValue();
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
    extends AbstractFloatSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(float k) {
            return AbstractFloat2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2BooleanSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2BooleanSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2BooleanSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2BooleanSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2BooleanSortedMap.this.entrySet().iterator(new AbstractFloat2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

