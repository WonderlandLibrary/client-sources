/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharBigListIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class CharBigListIterators {
    public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();

    private CharBigListIterators() {
    }

    public static CharBigListIterator singleton(char c) {
        return new SingletonBigListIterator(c);
    }

    public static CharBigListIterator unmodifiable(CharBigListIterator charBigListIterator) {
        return new UnmodifiableBigListIterator(charBigListIterator);
    }

    public static CharBigListIterator asBigListIterator(CharListIterator charListIterator) {
        return new BigListIteratorListIterator(charListIterator);
    }

    public static class BigListIteratorListIterator
    implements CharBigListIterator {
        protected final CharListIterator i;

        protected BigListIteratorListIterator(CharListIterator charListIterator) {
            this.i = charListIterator;
        }

        private int intDisplacement(long l) {
            if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
            }
            return (int)l;
        }

        @Override
        public void set(char c) {
            this.i.set(c);
        }

        @Override
        public void add(char c) {
            this.i.add(c);
        }

        @Override
        public int back(int n) {
            return this.i.back(n);
        }

        @Override
        public long back(long l) {
            return this.i.back(this.intDisplacement(l));
        }

        @Override
        public void remove() {
            this.i.remove();
        }

        @Override
        public int skip(int n) {
            return this.i.skip(n);
        }

        @Override
        public long skip(long l) {
            return this.i.skip(this.intDisplacement(l));
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public char nextChar() {
            return this.i.nextChar();
        }

        @Override
        public char previousChar() {
            return this.i.previousChar();
        }

        @Override
        public long nextIndex() {
            return this.i.nextIndex();
        }

        @Override
        public long previousIndex() {
            return this.i.previousIndex();
        }
    }

    public static class UnmodifiableBigListIterator
    implements CharBigListIterator {
        protected final CharBigListIterator i;

        public UnmodifiableBigListIterator(CharBigListIterator charBigListIterator) {
            this.i = charBigListIterator;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }

        @Override
        public char nextChar() {
            return this.i.nextChar();
        }

        @Override
        public char previousChar() {
            return this.i.previousChar();
        }

        @Override
        public long nextIndex() {
            return this.i.nextIndex();
        }

        @Override
        public long previousIndex() {
            return this.i.previousIndex();
        }
    }

    private static class SingletonBigListIterator
    implements CharBigListIterator {
        private final char element;
        private int curr;

        public SingletonBigListIterator(char c) {
            this.element = c;
        }

        @Override
        public boolean hasNext() {
            return this.curr == 0;
        }

        @Override
        public boolean hasPrevious() {
            return this.curr == 1;
        }

        @Override
        public char nextChar() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public char previousChar() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = 0;
            return this.element;
        }

        @Override
        public long nextIndex() {
            return this.curr;
        }

        @Override
        public long previousIndex() {
            return this.curr - 1;
        }
    }

    public static class EmptyBigListIterator
    implements CharBigListIterator,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyBigListIterator() {
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public char nextChar() {
            throw new NoSuchElementException();
        }

        @Override
        public char previousChar() {
            throw new NoSuchElementException();
        }

        @Override
        public long nextIndex() {
            return 0L;
        }

        @Override
        public long previousIndex() {
            return -1L;
        }

        @Override
        public long skip(long l) {
            return 0L;
        }

        @Override
        public long back(long l) {
            return 0L;
        }

        public Object clone() {
            return EMPTY_BIG_LIST_ITERATOR;
        }

        private Object readResolve() {
            return EMPTY_BIG_LIST_ITERATOR;
        }
    }
}

