/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.ints.IntBigListIterator;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class IntBigListIterators {
    public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();

    private IntBigListIterators() {
    }

    public static IntBigListIterator singleton(int n) {
        return new SingletonBigListIterator(n);
    }

    public static IntBigListIterator unmodifiable(IntBigListIterator intBigListIterator) {
        return new UnmodifiableBigListIterator(intBigListIterator);
    }

    public static IntBigListIterator asBigListIterator(IntListIterator intListIterator) {
        return new BigListIteratorListIterator(intListIterator);
    }

    public static class BigListIteratorListIterator
    implements IntBigListIterator {
        protected final IntListIterator i;

        protected BigListIteratorListIterator(IntListIterator intListIterator) {
            this.i = intListIterator;
        }

        private int intDisplacement(long l) {
            if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
            }
            return (int)l;
        }

        @Override
        public void set(int n) {
            this.i.set(n);
        }

        @Override
        public void add(int n) {
            this.i.add(n);
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
        public int nextInt() {
            return this.i.nextInt();
        }

        @Override
        public int previousInt() {
            return this.i.previousInt();
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
    implements IntBigListIterator {
        protected final IntBigListIterator i;

        public UnmodifiableBigListIterator(IntBigListIterator intBigListIterator) {
            this.i = intBigListIterator;
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
        public int nextInt() {
            return this.i.nextInt();
        }

        @Override
        public int previousInt() {
            return this.i.previousInt();
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
    implements IntBigListIterator {
        private final int element;
        private int curr;

        public SingletonBigListIterator(int n) {
            this.element = n;
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public int previousInt() {
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
    implements IntBigListIterator,
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
        public int nextInt() {
            throw new NoSuchElementException();
        }

        @Override
        public int previousInt() {
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

