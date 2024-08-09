/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatListIterator;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class FloatBigListIterators {
    public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();

    private FloatBigListIterators() {
    }

    public static FloatBigListIterator singleton(float f) {
        return new SingletonBigListIterator(f);
    }

    public static FloatBigListIterator unmodifiable(FloatBigListIterator floatBigListIterator) {
        return new UnmodifiableBigListIterator(floatBigListIterator);
    }

    public static FloatBigListIterator asBigListIterator(FloatListIterator floatListIterator) {
        return new BigListIteratorListIterator(floatListIterator);
    }

    public static class BigListIteratorListIterator
    implements FloatBigListIterator {
        protected final FloatListIterator i;

        protected BigListIteratorListIterator(FloatListIterator floatListIterator) {
            this.i = floatListIterator;
        }

        private int intDisplacement(long l) {
            if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
            }
            return (int)l;
        }

        @Override
        public void set(float f) {
            this.i.set(f);
        }

        @Override
        public void add(float f) {
            this.i.add(f);
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
        public float nextFloat() {
            return this.i.nextFloat();
        }

        @Override
        public float previousFloat() {
            return this.i.previousFloat();
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
    implements FloatBigListIterator {
        protected final FloatBigListIterator i;

        public UnmodifiableBigListIterator(FloatBigListIterator floatBigListIterator) {
            this.i = floatBigListIterator;
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
        public float nextFloat() {
            return this.i.nextFloat();
        }

        @Override
        public float previousFloat() {
            return this.i.previousFloat();
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
    implements FloatBigListIterator {
        private final float element;
        private int curr;

        public SingletonBigListIterator(float f) {
            this.element = f;
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
        public float nextFloat() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public float previousFloat() {
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
    implements FloatBigListIterator,
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
        public float nextFloat() {
            throw new NoSuchElementException();
        }

        @Override
        public float previousFloat() {
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

