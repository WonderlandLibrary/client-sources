/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractChar2DoubleMap;
import it.unimi.dsi.fastutil.chars.AbstractCharSortedSet;
import it.unimi.dsi.fastutil.chars.Char2DoubleMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleSortedMap;
import it.unimi.dsi.fastutil.chars.Char2DoubleSortedMaps;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractChar2DoubleSortedMap
extends AbstractChar2DoubleMap
implements Char2DoubleSortedMap {
    private static final long serialVersionUID = -1773560792952436569L;

    protected AbstractChar2DoubleSortedMap() {
    }

    @Override
    public CharSortedSet keySet() {
        return new KeySet(this);
    }

    @Override
    public DoubleCollection values() {
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
    implements DoubleIterator {
        protected final ObjectBidirectionalIterator<Char2DoubleMap.Entry> i;

        public ValuesIterator(ObjectBidirectionalIterator<Char2DoubleMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public double nextDouble() {
            return ((Char2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
    extends AbstractDoubleCollection {
        final AbstractChar2DoubleSortedMap this$0;

        protected ValuesCollection(AbstractChar2DoubleSortedMap abstractChar2DoubleSortedMap) {
            this.this$0 = abstractChar2DoubleSortedMap;
        }

        @Override
        public DoubleIterator iterator() {
            return new ValuesIterator(Char2DoubleSortedMaps.fastIterator(this.this$0));
        }

        @Override
        public boolean contains(double d) {
            return this.this$0.containsValue(d);
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
        protected final ObjectBidirectionalIterator<Char2DoubleMap.Entry> i;

        public KeySetIterator(ObjectBidirectionalIterator<Char2DoubleMap.Entry> objectBidirectionalIterator) {
            this.i = objectBidirectionalIterator;
        }

        @Override
        public char nextChar() {
            return ((Char2DoubleMap.Entry)this.i.next()).getCharKey();
        }

        @Override
        public char previousChar() {
            return ((Char2DoubleMap.Entry)this.i.previous()).getCharKey();
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
        final AbstractChar2DoubleSortedMap this$0;

        protected KeySet(AbstractChar2DoubleSortedMap abstractChar2DoubleSortedMap) {
            this.this$0 = abstractChar2DoubleSortedMap;
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
            return new KeySetIterator(this.this$0.char2DoubleEntrySet().iterator(new AbstractChar2DoubleMap.BasicEntry(c, 0.0)));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return new KeySetIterator(Char2DoubleSortedMaps.fastIterator(this.this$0));
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

