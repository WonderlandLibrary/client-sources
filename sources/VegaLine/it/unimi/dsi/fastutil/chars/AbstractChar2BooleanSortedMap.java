/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
import it.unimi.dsi.fastutil.booleans.AbstractBooleanIterator;
import it.unimi.dsi.fastutil.booleans.BooleanCollection;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.chars.AbstractChar2BooleanMap;
import it.unimi.dsi.fastutil.chars.AbstractCharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2BooleanSortedMap;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;

public abstract class AbstractChar2BooleanSortedMap
extends AbstractChar2BooleanMap
implements Char2BooleanSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2BooleanSortedMap() {
    }

    @Override
    @Deprecated
    public Char2BooleanSortedMap headMap(Character to) {
        return this.headMap(to.charValue());
    }

    @Override
    @Deprecated
    public Char2BooleanSortedMap tailMap(Character from) {
        return this.tailMap(from.charValue());
    }

    @Override
    @Deprecated
    public Char2BooleanSortedMap subMap(Character from, Character to) {
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
    public BooleanCollection values() {
        return new ValuesCollection();
    }

    @Override
    public ObjectSortedSet<Map.Entry<Character, Boolean>> entrySet() {
        return this.char2BooleanEntrySet();
    }

    protected static class ValuesIterator
    extends AbstractBooleanIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Boolean>> i;

        public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Character, Boolean>> i) {
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
            return new ValuesIterator(AbstractChar2BooleanSortedMap.this.entrySet().iterator());
        }

        @Override
        public boolean contains(boolean k) {
            return AbstractChar2BooleanSortedMap.this.containsValue(k);
        }

        @Override
        public int size() {
            return AbstractChar2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2BooleanSortedMap.this.clear();
        }
    }

    protected static class KeySetIterator
    extends AbstractCharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Map.Entry<Character, Boolean>> i;

        public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Character, Boolean>> i) {
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
            return AbstractChar2BooleanSortedMap.this.containsKey(k);
        }

        @Override
        public int size() {
            return AbstractChar2BooleanSortedMap.this.size();
        }

        @Override
        public void clear() {
            AbstractChar2BooleanSortedMap.this.clear();
        }

        @Override
        public CharComparator comparator() {
            return AbstractChar2BooleanSortedMap.this.comparator();
        }

        @Override
        public char firstChar() {
            return AbstractChar2BooleanSortedMap.this.firstCharKey();
        }

        @Override
        public char lastChar() {
            return AbstractChar2BooleanSortedMap.this.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char to) {
            return AbstractChar2BooleanSortedMap.this.headMap(to).keySet();
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return AbstractChar2BooleanSortedMap.this.tailMap(from).keySet();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return AbstractChar2BooleanSortedMap.this.subMap(from, to).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return new KeySetIterator(AbstractChar2BooleanSortedMap.this.entrySet().iterator(new AbstractChar2BooleanMap.BasicEntry(from, false)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(AbstractChar2BooleanSortedMap.this.entrySet().iterator());
        }
    }
}

