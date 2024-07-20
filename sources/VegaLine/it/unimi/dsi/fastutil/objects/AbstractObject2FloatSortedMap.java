/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2FloatMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2FloatSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2FloatSortedMap<K>
extends AbstractObject2FloatMap<K>
implements Object2FloatSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2FloatSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Float>> entrySet() {
        return this.object2FloatEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Float>> i) {
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
            return new ValuesIterator(AbstractObject2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractObject2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Float>> i) {
            this.i = i;
        }

        @Override
        public K next() {
            return ((Map.Entry)this.i.next()).getKey();
        }

        @Override
        public K previous() {
            return ((Map.Entry)this.i.previous()).getKey();
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
    extends AbstractObjectSortedSet<K> {
        protected KeySet() {
        }

        @Override
        public boolean contains(Object k) {
            return AbstractObject2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2FloatSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2FloatSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2FloatSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2FloatSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2FloatSortedMap.this.entrySet().iterator(new AbstractObject2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

