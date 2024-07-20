/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharComparator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.chars.CharSets;
import it.unimi.dsi.fastutil.chars.CharSortedSet;
import java.io.Serializable;
import java.util.NoSuchElementException;

public class CharSortedSets {
    public static final EmptySet EMPTY_SET = new EmptySet();

    private CharSortedSets() {
    }

    public static CharSortedSet singleton(char element) {
        return new Singleton(element);
    }

    public static CharSortedSet singleton(char element, CharComparator comparator) {
        return new Singleton(element, comparator);
    }

    public static CharSortedSet singleton(Object element) {
        return new Singleton(((Character)element).charValue());
    }

    public static CharSortedSet singleton(Object element, CharComparator comparator) {
        return new Singleton(((Character)element).charValue(), comparator);
    }

    public static CharSortedSet synchronize(CharSortedSet s) {
        return new SynchronizedSortedSet(s);
    }

    public static CharSortedSet synchronize(CharSortedSet s, Object sync) {
        return new SynchronizedSortedSet(s, sync);
    }

    public static CharSortedSet unmodifiable(CharSortedSet s) {
        return new UnmodifiableSortedSet(s);
    }

    public static class UnmodifiableSortedSet
    extends CharSets.UnmodifiableSet
    implements CharSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharSortedSet sortedSet;

        protected UnmodifiableSortedSet(CharSortedSet s) {
            super(s);
            this.sortedSet = s;
        }

        @Override
        public CharComparator comparator() {
            return this.sortedSet.comparator();
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
        }

        @Override
        public CharSortedSet headSet(char to) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return CharIterators.unmodifiable(this.sortedSet.iterator());
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return CharIterators.unmodifiable(this.sortedSet.iterator(from));
        }

        @Override
        @Deprecated
        public CharBidirectionalIterator charIterator() {
            return this.iterator();
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
        public Character first() {
            return (Character)this.sortedSet.first();
        }

        @Override
        public Character last() {
            return (Character)this.sortedSet.last();
        }

        @Override
        public CharSortedSet subSet(Character from, Character to) {
            return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
        }

        @Override
        public CharSortedSet headSet(Character to) {
            return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
        }

        @Override
        public CharSortedSet tailSet(Character from) {
            return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
        }
    }

    public static class SynchronizedSortedSet
    extends CharSets.SynchronizedSet
    implements CharSortedSet,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharSortedSet sortedSet;

        protected SynchronizedSortedSet(CharSortedSet s, Object sync) {
            super(s, sync);
            this.sortedSet = s;
        }

        protected SynchronizedSortedSet(CharSortedSet s) {
            super(s);
            this.sortedSet = s;
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
        public CharSortedSet subSet(char from, char to) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
        }

        @Override
        public CharSortedSet headSet(char to) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
        }

        @Override
        public CharSortedSet tailSet(char from) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
        }

        @Override
        public CharBidirectionalIterator iterator() {
            return this.sortedSet.iterator();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return this.sortedSet.iterator(from);
        }

        @Override
        @Deprecated
        public CharBidirectionalIterator charIterator() {
            return this.sortedSet.iterator();
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
        public Character first() {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.sortedSet.first();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Character last() {
            Object object = this.sync;
            synchronized (object) {
                return (Character)this.sortedSet.last();
            }
        }

        @Override
        public CharSortedSet subSet(Character from, Character to) {
            return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
        }

        @Override
        public CharSortedSet headSet(Character to) {
            return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
        }

        @Override
        public CharSortedSet tailSet(Character from) {
            return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
        }
    }

    public static class Singleton
    extends CharSets.Singleton
    implements CharSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        final CharComparator comparator;

        private Singleton(char element, CharComparator comparator) {
            super(element);
            this.comparator = comparator;
        }

        private Singleton(char element) {
            this(element, (CharComparator)null);
        }

        final int compare(char k1, char k2) {
            return this.comparator == null ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
        }

        @Override
        @Deprecated
        public CharBidirectionalIterator charIterator() {
            return this.iterator();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            CharBidirectionalIterator i = this.iterator();
            if (this.compare(this.element, from) <= 0) {
                i.next();
            }
            return i;
        }

        @Override
        public CharComparator comparator() {
            return this.comparator;
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            if (this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet headSet(char to) {
            if (this.compare(this.element, to) < 0) {
                return this;
            }
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet tailSet(char from) {
            if (this.compare(from, this.element) <= 0) {
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
        public Character first() {
            return Character.valueOf(this.element);
        }

        @Override
        @Deprecated
        public Character last() {
            return Character.valueOf(this.element);
        }

        @Override
        @Deprecated
        public CharSortedSet subSet(Character from, Character to) {
            return this.subSet(from.charValue(), to.charValue());
        }

        @Override
        @Deprecated
        public CharSortedSet headSet(Character to) {
            return this.headSet(to.charValue());
        }

        @Override
        @Deprecated
        public CharSortedSet tailSet(Character from) {
            return this.tailSet(from.charValue());
        }
    }

    public static class EmptySet
    extends CharSets.EmptySet
    implements CharSortedSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean rem(char ok) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public CharBidirectionalIterator charIterator() {
            return this.iterator();
        }

        @Override
        public CharBidirectionalIterator iterator(char from) {
            return CharIterators.EMPTY_ITERATOR;
        }

        @Override
        public CharSortedSet subSet(char from, char to) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet headSet(char from) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet tailSet(char to) {
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
        public CharSortedSet subSet(Character from, Character to) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet headSet(Character from) {
            return EMPTY_SET;
        }

        @Override
        public CharSortedSet tailSet(Character to) {
            return EMPTY_SET;
        }

        @Override
        public Character first() {
            throw new NoSuchElementException();
        }

        @Override
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
    }
}

