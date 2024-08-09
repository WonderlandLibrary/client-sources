/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSets$SynchronizedSet
 *  com.viaversion.viaversion.libs.fastutil.ints.IntSets$UnmodifiableSet
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntArraySet;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public final class IntSets {
    static final int ARRAY_SET_CUTOFF = 4;
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final IntSet UNMODIFIABLE_EMPTY_SET = IntSets.unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));

    private IntSets() {
    }

    public static IntSet emptySet() {
        return EMPTY_SET;
    }

    public static IntSet singleton(int n) {
        return new Singleton(n);
    }

    public static IntSet singleton(Integer n) {
        return new Singleton(n);
    }

    public static IntSet synchronize(IntSet intSet) {
        return new SynchronizedSet(intSet);
    }

    public static IntSet synchronize(IntSet intSet, Object object) {
        return new SynchronizedSet(intSet, object);
    }

    public static IntSet unmodifiable(IntSet intSet) {
        return new UnmodifiableSet(intSet);
    }

    public static IntSet fromTo(int n, int n2) {
        return new AbstractIntSet(n, n2){
            final int val$from;
            final int val$to;
            {
                this.val$from = n;
                this.val$to = n2;
            }

            @Override
            public boolean contains(int n) {
                return n >= this.val$from && n < this.val$to;
            }

            @Override
            public IntIterator iterator() {
                return IntIterators.fromTo(this.val$from, this.val$to);
            }

            @Override
            public int size() {
                long l = (long)this.val$to - (long)this.val$from;
                return l >= 0L && l <= Integer.MAX_VALUE ? (int)l : Integer.MAX_VALUE;
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static IntSet from(int n) {
        return new AbstractIntSet(n){
            final int val$from;
            {
                this.val$from = n;
            }

            @Override
            public boolean contains(int n) {
                return n >= this.val$from;
            }

            @Override
            public IntIterator iterator() {
                return IntIterators.concat(IntIterators.fromTo(this.val$from, Integer.MAX_VALUE), IntSets.singleton(Integer.MAX_VALUE).iterator());
            }

            @Override
            public int size() {
                long l = Integer.MAX_VALUE - (long)this.val$from + 1L;
                return l >= 0L && l <= Integer.MAX_VALUE ? (int)l : Integer.MAX_VALUE;
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static IntSet to(int n) {
        return new AbstractIntSet(n){
            final int val$to;
            {
                this.val$to = n;
            }

            @Override
            public boolean contains(int n) {
                return n < this.val$to;
            }

            @Override
            public IntIterator iterator() {
                return IntIterators.fromTo(Integer.MIN_VALUE, this.val$to);
            }

            @Override
            public int size() {
                long l = (long)this.val$to - Integer.MIN_VALUE;
                return l >= 0L && l <= Integer.MAX_VALUE ? (int)l : Integer.MAX_VALUE;
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    public static class EmptySet
    extends IntCollections.EmptyCollection
    implements IntSet,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override
        public boolean remove(int n) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return EMPTY_SET;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof Set && ((Set)object).isEmpty();
        }

        @Override
        @Deprecated
        public boolean rem(int n) {
            return super.rem(n);
        }

        private Object readResolve() {
            return EMPTY_SET;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractIntSet
    implements Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int element;

        protected Singleton(int n) {
            this.element = n;
        }

        @Override
        public boolean contains(int n) {
            return n == this.element;
        }

        @Override
        public boolean remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public IntListIterator iterator() {
            return IntIterators.singleton(this.element);
        }

        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element);
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public int[] toIntArray() {
            return new int[]{this.element};
        }

        @Override
        @Deprecated
        public void forEach(Consumer<? super Integer> consumer) {
            consumer.accept((Integer)this.element);
        }

        @Override
        public boolean addAll(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean removeIf(Predicate<? super Integer> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            intConsumer.accept(this.element);
        }

        @Override
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeIf(IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object[] toArray() {
            return new Object[]{this.element};
        }

        public Object clone() {
            return this;
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public Iterator iterator() {
            return this.iterator();
        }
    }
}

