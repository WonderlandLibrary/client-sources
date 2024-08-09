/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.objects.ObjectBigListIterator;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class ObjectBigListIterators {
    public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();

    private ObjectBigListIterators() {
    }

    public static <K> ObjectBigListIterator<K> singleton(K k) {
        return new SingletonBigListIterator<K>(k);
    }

    public static <K> ObjectBigListIterator<K> unmodifiable(ObjectBigListIterator<K> objectBigListIterator) {
        return new UnmodifiableBigListIterator<K>(objectBigListIterator);
    }

    public static <K> ObjectBigListIterator<K> asBigListIterator(ObjectListIterator<K> objectListIterator) {
        return new BigListIteratorListIterator<K>(objectListIterator);
    }

    public static class BigListIteratorListIterator<K>
    implements ObjectBigListIterator<K> {
        protected final ObjectListIterator<K> i;

        protected BigListIteratorListIterator(ObjectListIterator<K> objectListIterator) {
            this.i = objectListIterator;
        }

        private int intDisplacement(long l) {
            if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
            }
            return (int)l;
        }

        @Override
        public void set(K k) {
            this.i.set(k);
        }

        @Override
        public void add(K k) {
            this.i.add(k);
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
        public K next() {
            return (K)this.i.next();
        }

        @Override
        public K previous() {
            return this.i.previous();
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

    public static class UnmodifiableBigListIterator<K>
    implements ObjectBigListIterator<K> {
        protected final ObjectBigListIterator<K> i;

        public UnmodifiableBigListIterator(ObjectBigListIterator<K> objectBigListIterator) {
            this.i = objectBigListIterator;
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
        public K next() {
            return (K)this.i.next();
        }

        @Override
        public K previous() {
            return this.i.previous();
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

    private static class SingletonBigListIterator<K>
    implements ObjectBigListIterator<K> {
        private final K element;
        private int curr;

        public SingletonBigListIterator(K k) {
            this.element = k;
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
        public K next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }

        @Override
        public K previous() {
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

    public static class EmptyBigListIterator<K>
    implements ObjectBigListIterator<K>,
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
        public K next() {
            throw new NoSuchElementException();
        }

        @Override
        public K previous() {
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

