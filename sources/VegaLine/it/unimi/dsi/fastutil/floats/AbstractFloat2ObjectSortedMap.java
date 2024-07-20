/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloat2ObjectMap;
import it.unimi.dsi.fastutil.floats.AbstractFloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.AbstractFloatSortedSet;
import it.unimi.dsi.fastutil.floats.Float2ObjectSortedMap;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import it.unimi.dsi.fastutil.floats.FloatSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractFloat2ObjectSortedMap<V>
extends AbstractFloat2ObjectMap<V>
implements Float2ObjectSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractFloat2ObjectSortedMap() {
    }

    @Override
    @Deprecated
    public Float2ObjectSortedMap<V> headMap(Float to) {
        return this.headMap(to.floatValue());
    }

    @Override
    @Deprecated
    public Float2ObjectSortedMap<V> tailMap(Float from) {
        return this.tailMap(from.floatValue());
    }

    @Override
    @Deprecated
    public Float2ObjectSortedMap<V> subMap(Float from, Float to) {
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
    public ObjectCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Float, V>> entrySet() {
        return this.float2ObjectEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Float, V>> i) {
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
            return new ValuesIterator(AbstractFloat2ObjectSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractFloat2ObjectSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractFloat2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2ObjectSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractFloatBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Float, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Float, V>> i) {
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
            return AbstractFloat2ObjectSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractFloat2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractFloat2ObjectSortedMap.this.clear();
        }

        @Override
        public FloatComparator comparator() {
            return AbstractFloat2ObjectSortedMap.this.comparator();
        }

        @Override
        public float firstFloat() {
            return AbstractFloat2ObjectSortedMap.this.firstFloatKey();
        }

        @Override
        public float lastFloat() {
            return AbstractFloat2ObjectSortedMap.this.lastFloatKey();
        }

        @Override
        public FloatSortedSet headSet(float to) {
            return AbstractFloat2ObjectSortedMap.this.headMap(to).keySet();
        }

        @Override
        public FloatSortedSet tailSet(float from) {
            return AbstractFloat2ObjectSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public FloatSortedSet subSet(float from, float to) {
            return AbstractFloat2ObjectSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public FloatBidirectionalIterator iterator(float from) {
            return new KeySetIterator(AbstractFloat2ObjectSortedMap.this.entrySet().iterator(new AbstractFloat2ObjectMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public FloatBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractFloat2ObjectSortedMap.this.entrySet().iterator());
        }
    }
}

