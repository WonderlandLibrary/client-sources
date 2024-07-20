/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2ByteMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2ByteSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2ByteSortedMap
extends AbstractChar2ByteMap
implements Char2ByteSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2ByteSortedMap() {
    }

    @Override
    @Deprecated
    public Char2ByteSortedMap headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2ByteSortedMap tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2ByteSortedMap subMap(Character from, Character to) {
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
    public ByteCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
        return this.char2ByteEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractByteIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Byte>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, Byte>> i) {
            this.i = i;
        }

        @Override
        public byte nextByte() {
            return (Byte)((Map.Entry)this.i.next()).getValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    protected class ValuesCollection
    extends AbstractByteCollection {
        protected ValuesCollection() {
        }

        @Override
        public ByteIterator iterator() {
            return new ValuesIterator(AbstractChar2ByteSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(byte k) {
            return AbstractChar2ByteSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2ByteSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Byte>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, Byte>> i) {
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
            return AbstractChar2ByteSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2ByteSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2ByteSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2ByteSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2ByteSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2ByteSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2ByteSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2ByteSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2ByteSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2ByteSortedMap.this.entrySet().iterator(new AbstractChar2ByteMap.BasicEntry(from, 0)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2ByteSortedMap.this.entrySet().iterator());
        }
    }
}

