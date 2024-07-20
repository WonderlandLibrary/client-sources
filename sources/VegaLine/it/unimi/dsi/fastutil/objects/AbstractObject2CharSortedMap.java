/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.objects.AbstractObject2CharMap;
import it.unimi.dsi.fastutil.objects.AbstractObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
import it.unimi.dsi.fastutil.objects.Object2CharSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Comparator;
import java.util.Map;

public abstract class AbstractObject2CharSortedMap<K>
extends AbstractObject2CharMap<K>
implements Object2CharSortedMap<K> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractObject2CharSortedMap() {
    }

    @Override
    public ObjectSortedSet<K> keySet() {
        return new KeySet();
    }

    @Override
    public CharCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<K, Character>> entrySet() {
        return this.object2CharEntrySet();
    }

    protected static class ValuesIterator<K>
    extends AbstractCharIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Character>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<K, Character>> i) {
            this.i = i;
        }

        @Override
        public char nextChar() {
            return ((Character)((Map.Entry)this.i.next()).getValue()).charValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractCharCollection {
        protected ValuesCollection() {
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(AbstractObject2CharSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(char k) {
            return AbstractObject2CharSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractObject2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2CharSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<K>
    extends AbstractObjectBidirectionalIterator<K> {
        protected final ObjectBidirectionalIterator<Map.Entry<K, Character>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<K, Character>> i) {
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
            return AbstractObject2CharSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractObject2CharSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractObject2CharSortedMap.this.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return AbstractObject2CharSortedMap.this.comparator();
        }

        @Override
        public K first() {
            return AbstractObject2CharSortedMap.this.firstKey();
        }

        @Override
        public K last() {
            return AbstractObject2CharSortedMap.this.lastKey();
        }

        @Override
        public ObjectSortedSet<K> headSet(K to) {
            return AbstractObject2CharSortedMap.this.headMap(to).keySet();
        }

        @Override
        public ObjectSortedSet<K> tailSet(K from) {
            return AbstractObject2CharSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public ObjectSortedSet<K> subSet(K from, K to) {
            return AbstractObject2CharSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator(K from) {
            return new KeySetIterator(AbstractObject2CharSortedMap.this.entrySet().iterator(new AbstractObject2CharMap.BasicEntry(from, '\u0000')));
        }

        @Override
        public ObjectBidirectionalIterator<K> iterator() {
            return new KeySetIterator(AbstractObject2CharSortedMap.this.entrySet().iterator());
        }
    }
}

