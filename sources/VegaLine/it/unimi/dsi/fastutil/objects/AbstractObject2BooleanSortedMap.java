/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2BooleanMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2BooleanSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2BooleanSortedMap<K>
extends AbstractObject2BooleanMap<K>
implements Object2BooleanSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2BooleanSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
        return this.object2BooleanEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Boolean>> i) {
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
            return new ValuesIterator(AbstractObject2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractObject2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Boolean>> i) {
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
            return AbstractObject2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2BooleanSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2BooleanSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2BooleanSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2BooleanSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2BooleanSortedMap.this.entrySet().iterator(new AbstractObject2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

