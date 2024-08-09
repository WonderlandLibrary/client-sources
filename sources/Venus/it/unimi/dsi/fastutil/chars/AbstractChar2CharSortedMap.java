/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2CharMap;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharSortedMap;
import it.unimi.dsi.fastutil.chars.Char2CharSortedMaps;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChar2CharSortedMap
extends AbstractChar2CharMap
implements Char2CharSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2CharSortedMap() {
    }

    @Override
    public CharSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public CharCollection values() {
        return new ValuesCollection(this);
    }

    @Override
    public CharSet keySet() {
        return this.keySet();
    }

    @Override
    public Collection values() {
        return this.values();
    }

    @Override
    public Set keySet() {
        return this.keySet();
    }

    protected static class ValuesIterator
    implements CharIterator {
        protected final ObjectBidirectionalIterator<Char2CharMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Char2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public char nextChar() {
            return ((Char2CharMap.Entry)this.i.next()).getCharValue();
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class ValuesCollection
    extends AbstractCharCollection {
        final AbstractChar2CharSortedMap this$0;

        protected ValuesCollection(AbstractChar2CharSortedMap abstractChar2CharSortedMap) {
            this.this$0 = abstractChar2CharSortedMap;
        }

        @Override
        public CharIterator iterator() {
            return new ValuesIterator(Char2CharSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(char c) {
            return this.this$0.containsValue(c);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }

    protected static class KeySetIterator
    implements CharBidirectionalIterator {
        protected final ObjectBidirectionalIterator<Char2CharMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Char2CharMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public char nextChar() {
            return ((Char2CharMap.Entry)this.i.next()).getCharKey();
        }

        @Override
        public char previousChar() {
            return ((Char2CharMap.Entry)this.i.previous()).getCharKey();
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

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    protected class KeySet
    extends AbstractCharSortedSet {
        final AbstractChar2CharSortedMap this$0;

        protected KeySet(AbstractChar2CharSortedMap abstractChar2CharSortedMap) {
            this.this$0 = abstractChar2CharSortedMap;
        }

        @Override
        public boolean contains(char c) {
            return this.this$0.containsKey(c);
        }

        @Override
        public int size() {
            return this.this$0.size();
        }

        @Override
        public void clear() {
            this.this$0.clear();
        }

        @Override
        public CharComparator comparator() {
            return this.this$0.comparator();
        }

        @Override
        public char firstChar() {
            return this.this$0.firstCharKey();
        }

        @Override
        public char lastChar() {
            return this.this$0.lastCharKey();
        }

        @Override
        public CharSortedSet headSet(char c) {
            return this.this$0.headMap(c).keySet();
        }

        @Override
        public CharSortedSet tailSet(char c) {
            return this.this$0.tailMap(c).keySet();
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            return this.this$0.subMap(c, c2).keySet();
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            return new KeySetIterator(this.this$0.char2CharEntrySet().iterator(new AbstractChar2CharMap.BasicEntry(c, '\u0000')));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(Char2CharSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public CharIterator iterator() {
            return this.iterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

