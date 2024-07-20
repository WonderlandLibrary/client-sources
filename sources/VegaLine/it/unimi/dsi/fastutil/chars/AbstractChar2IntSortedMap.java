/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2IntMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2IntSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
import it.unimi.dsi.fastutil.ints.AbstractIntIterator;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2IntSortedMap
extends AbstractChar2IntMap
implements Char2IntSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2IntSortedMap() {
    }

    @Override
    @Deprecated
    public Char2IntSortedMap headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2IntSortedMap tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2IntSortedMap subMap(Character from, Character to) {
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
    public IntCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, Integer>> entrySet() {
        return this.char2IntEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractIntIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Integer>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, Integer>> i) {
            this.i = i;
        }

        @Override
        public int nextInt() {
            return (Integer)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractIntCollection {
        protected ValuesCollection() {
        }

        @Override
        public IntIterator iterator() {
            return new ValuesIterator(AbstractChar2IntSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(int k) {
            return AbstractChar2IntSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2IntSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Integer>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, Integer>> i) {
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
            return AbstractChar2IntSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2IntSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2IntSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2IntSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2IntSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2IntSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2IntSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2IntSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2IntSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2IntSortedMap.this.entrySet().iterator(new AbstractChar2IntMap.BasicEntry(from, 0)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2IntSortedMap.this.entrySet().iterator());
        }
    }
}

