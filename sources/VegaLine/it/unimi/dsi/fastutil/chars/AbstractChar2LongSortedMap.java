/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2LongMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2LongSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
import it.unimi.dsi.fastutil.longs.AbstractLongIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2LongSortedMap
extends AbstractChar2LongMap
implements Char2LongSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2LongSortedMap() {
    }

    @Override
    @Deprecated
    public Char2LongSortedMap headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2LongSortedMap tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2LongSortedMap subMap(Character from, Character to) {
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
    public LongCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, Long>> entrySet() {
        return this.char2LongEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractLongIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Long>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, Long>> i) {
            this.i = i;
        }

        @Override
        public long nextLong() {
            return (Long)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractLongCollection {
        protected ValuesCollection() {
        }

        @Override
        public LongIterator iterator() {
            return new ValuesIterator(AbstractChar2LongSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(long k) {
            return AbstractChar2LongSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2LongSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Long>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, Long>> i) {
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
            return AbstractChar2LongSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2LongSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2LongSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2LongSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2LongSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2LongSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2LongSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2LongSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2LongSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2LongSortedMap.this.entrySet().iterator(new AbstractChar2LongMap.BasicEntry(from, 0L)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2LongSortedMap.this.entrySet().iterator());
        }
    }
}

