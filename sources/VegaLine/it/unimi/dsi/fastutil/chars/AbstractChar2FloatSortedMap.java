/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2FloatMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2FloatSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.AbstractFloatIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2FloatSortedMap
extends AbstractChar2FloatMap
implements Char2FloatSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2FloatSortedMap() {
    }

    @Override
    @Deprecated
    public Char2FloatSortedMap headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2FloatSortedMap tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2FloatSortedMap subMap(Character from, Character to) {
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
    public FloatCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, Float>> entrySet() {
        return this.char2FloatEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractFloatIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Float>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, Float>> i) {
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
            return new ValuesIterator(AbstractChar2FloatSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(float k) {
            return AbstractChar2FloatSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2FloatSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Float>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, Float>> i) {
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
            return AbstractChar2FloatSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2FloatSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2FloatSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2FloatSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2FloatSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2FloatSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2FloatSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2FloatSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2FloatSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2FloatSortedMap.this.entrySet().iterator(new AbstractChar2FloatMap.BasicEntry(from, 0.0f)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2FloatSortedMap.this.entrySet().iterator());
        }
    }
}

