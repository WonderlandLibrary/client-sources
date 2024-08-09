/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public final class CharSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private CharSortedSets() {
    }

    public static CharSortedSet singleton(char c) {
        return new Singleton(c, null);
    }

    public static CharSortedSet singleton(char c, CharComparator charComparator) {
        return new Singleton(c, charComparator);
    }

    public static CharSortedSet singleton(Object object) {
        return new Singleton(((Character)object).charValue(), null);
    }

    public static CharSortedSet singleton(Object object, CharComparator charComparator) {
        return new Singleton(((Character)object).charValue(), charComparator);
    }

    public static CharSortedSet synchronize(CharSortedSet charSortedSet) {
        return new SynchronizedSortedSet(charSortedSet);
    }

    public static CharSortedSet synchronize(CharSortedSet charSortedSet, Object object) {
        return new SynchronizedSortedSet(charSortedSet, object);
    }

    public static CharSortedSet unmodifiable(CharSortedSet charSortedSet) {
        return new UnmodifiableSortedSet(charSortedSet);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class UnmodifiableSortedSet
    extends CharSets.UnmodifiableSet
    implements CharSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharSortedSet sortedSet;

        protected UnmodifiableSortedSet(CharSortedSet charSortedSet) {
            super(charSortedSet);
            this.sortedSet = charSortedSet;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(c, c2));
        }

        @Override
        public CharSortedSet headSet(char c) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(c));
        }

        @Override
        public CharSortedSet tailSet(char c) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(c));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return CharIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            return CharIterators.unmodifiable(this.sortedSet.iterator(c));
        }

        @Override
        public char firstChar() {
            return this.sortedSet.firstChar();
        }

        @Override
        public char lastChar() {
            return this.sortedSet.lastChar();
        }

        @Override
        @Deprecated
        public Character first() {
            return this.sortedSet.first();
        }

        @Override
        @Deprecated
        public Character last() {
            return this.sortedSet.last();
        }

        @Override
        @Deprecated
        public CharSortedSet subSet(Character c, Character c2) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(c, c2));
        }

        @Override
        @Deprecated
        public CharSortedSet headSet(Character c) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(c));
        }

        @Override
        @Deprecated
        public CharSortedSet tailSet(Character c) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(c));
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
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SynchronizedSortedSet
    extends CharSets.SynchronizedSet
    implements CharSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharSortedSet sortedSet;

        protected SynchronizedSortedSet(CharSortedSet charSortedSet, Object object) {
            super(charSortedSet, object);
            this.sortedSet = charSortedSet;
        }

        protected SynchronizedSortedSet(CharSortedSet charSortedSet) {
            super(charSortedSet);
            this.sortedSet = charSortedSet;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public CharComparator comparator() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.comparator();
            }
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(c, c2), this.sync);
        }

        @Override
        public CharSortedSet headSet(char c) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(c), this.sync);
        }

        @Override
        public CharSortedSet tailSet(char c) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(c), this.sync);
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            return this.sortedSet.iterator(c);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char firstChar() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.firstChar();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public char lastChar() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.lastChar();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character first() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Deprecated
        public Character last() {
            Object object = this.sync;
            synchronized (object) {
                return this.sortedSet.last();
            }
        }

        @Override
        @Deprecated
        public CharSortedSet subSet(Character c, Character c2) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(c, c2), this.sync);
        }

        @Override
        @Deprecated
        public CharSortedSet headSet(Character c) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(c), this.sync);
        }

        @Override
        @Deprecated
        public CharSortedSet tailSet(Character c) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(c), this.sync);
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
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends CharSets.Singleton
    implements CharSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final CharComparator comparator;

        protected Singleton(char c, CharComparator charComparator) {
            super(c);
            this.comparator = charComparator;
        }

        private Singleton(char c) {
            this(c, (CharComparator)null);
        }

        final int compare(char c, char c2) {
            return this.comparator == null ? Character.compare(c, c2) : this.comparator.compare(c, c2);
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            CharBidirectionalIterator charBidirectionalIterator = this.iterator();
            if (this.compare(this.element, c) <= 0) {
                charBidirectionalIterator.nextChar();
            }
            return charBidirectionalIterator;
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            if (this.compare(c, this.element) <= 0 && this.compare(this.element, c2) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet headSet(char c) {
            if (this.compare(this.element, c) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet tailSet(char c) {
            if (this.compare(c, this.element) <= 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public char firstChar() {
            return this.element;
        }

        @Override
        public char lastChar() {
            return this.element;
        }

        @Override
        @Deprecated
        public CharSortedSet subSet(Character c, Character c2) {
            return this.subSet(c.charValue(), c2.charValue());
        }

        @Override
        @Deprecated
        public CharSortedSet headSet(Character c) {
            return this.headSet(c.charValue());
        }

        @Override
        @Deprecated
        public CharSortedSet tailSet(Character c) {
            return this.tailSet(c.charValue());
        }

        @Override
        @Deprecated
        public Character first() {
            return Character.valueOf(this.element);
        }

        @Override
        @Deprecated
        public Character last() {
            return Character.valueOf(this.element);
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return super.iterator();
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }

        Singleton(char c, 1 var2_2) {
            this(c);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptySet
    extends CharSets.EmptySet
    implements CharSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public CharBidirectionalIterator iterator(char c) {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public CharSortedSet subSet(char c, char c2) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet headSet(char c) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet tailSet(char c) {
            return EMPTY_SET;
        }

        @Override
        public char firstChar() {
            throw new NoSuchElementException();
        }

        @Override
        public char lastChar() {
            throw new NoSuchElementException();
        }

        @Override
        public CharComparator comparator() {
            return null;
        }

        @Override
        @Deprecated
        public CharSortedSet subSet(Character c, Character c2) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public CharSortedSet headSet(Character c) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public CharSortedSet tailSet(Character c) {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Character first() {
            throw new NoSuchElementException();
        }

        @Override
        @Deprecated
        public Character last() {
            throw new NoSuchElementException();
        }

        @Override
        public Object clone() {
            return EMPTY_SET;
        }

        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        @Deprecated
        public Object last() {
            return this.last();
        }

        @Override
        @Deprecated
        public Object first() {
            return this.first();
        }

        @Override
        @Deprecated
        public SortedSet tailSet(Object object) {
            return this.tailSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet headSet(Object object) {
            return this.headSet((Character)object);
        }

        @Override
        @Deprecated
        public SortedSet subSet(Object object, Object object2) {
            return this.subSet((Character)object, (Character)object2);
        }

        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}

