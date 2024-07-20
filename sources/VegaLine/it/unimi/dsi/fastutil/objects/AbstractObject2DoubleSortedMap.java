/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2DoubleSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2DoubleSortedMap<K>
extends AbstractObject2DoubleMap<K>
implements Object2DoubleSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2DoubleSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public DoubleCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Double>> entrySet() {
        return this.object2DoubleEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractDoubleIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Double>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Double>> i) {
            this.i = i;
        }

        @Override
        public double nextDouble() {
            return (Double)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractDoubleCollection {
        protected ValuesCollection() {
        }

        @Override
        public DoubleIterator iterator() {
            return new ValuesIterator(AbstractObject2DoubleSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(double k) {
            return AbstractObject2DoubleSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2DoubleSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Double>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Double>> i) {
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
            return AbstractObject2DoubleSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2DoubleSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2DoubleSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2DoubleSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2DoubleSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2DoubleSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2DoubleSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2DoubleSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2DoubleSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2DoubleSortedMap.this.entrySet().iterator(new AbstractObject2DoubleMap.BasicEntry(from, 0.0)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2DoubleSortedMap.this.entrySet().iterator());
        }
    }
}

