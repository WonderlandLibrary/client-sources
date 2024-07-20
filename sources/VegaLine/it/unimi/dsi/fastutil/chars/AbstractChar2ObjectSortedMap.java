/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2ObjectMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2ObjectSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
import it.unimi.dsi.fastutil.objects.AbstractObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2ObjectSortedMap<V>
extends AbstractChar2ObjectMap<V>
implements Char2ObjectSortedMap<V> {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2ObjectSortedMap() {
    }

    @Override
    @Deprecated
    public Char2ObjectSortedMap<V> headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2ObjectSortedMap<V> tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2ObjectSortedMap<V> subMap(Character from, Character to) {
        return this.subMap(from.charValue(), to.charValue());
    }

    @Override
    @Deprecated
    public Character firstKey() {
        return Character.valueOf(this.firstCharKey());
    }

    @Override
    @Deprecated
    public Character lastKey() {
        return Character.valueOf(this.lastCharKey());
    }

    @Override
    public CharSortedSet keySet() {
        return new KeySet();
    }

    @Override
    public ObjectCollection<V> values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
        return this.char2ObjectEntrySet();
    }

    protected static class ValuesIterator<V>
    extends AbstractObjectIterator<V> {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, V>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, V>> i) {
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
            return new ValuesIterator(AbstractChar2ObjectSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(Object k) {
            return AbstractChar2ObjectSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2ObjectSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator<V>
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, V>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, V>> i) {
            this.i = i;
        }

        @Override
        public char nextChar() {
            return ((Character)((Map.Entry)this.i.next()).getKey()).charValue();
        }

        @Override
        public char previousChar() {
            return ((Character)((Map.Entry)this.i.previous()).getKey()).charValue();
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
    extends AbstractCharSortedSet {
        protected KeySet() {
        }

        @Override
        public boolean contains(char k) {
            return AbstractChar2ObjectSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2ObjectSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2ObjectSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2ObjectSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2ObjectSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2ObjectSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2ObjectSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2ObjectSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2ObjectSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2ObjectSortedMap.this.entrySet().iterator(new AbstractChar2ObjectMap.BasicEntry<Object>(from, null)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2ObjectSortedMap.this.entrySet().iterator());
        }
    }
}

