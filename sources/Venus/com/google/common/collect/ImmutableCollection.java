/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableAsList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public abstract class ImmutableCollection<E>
extends AbstractCollection<E>
implements Serializable {
    static final int SPLITERATOR_CHARACTERISTICS = 1296;

    ImmutableCollection() {
    }

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 1296);
    }

    @Override
    public final Object[] toArray() {
        int n = this.size();
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        Object[] objectArray = new Object[n];
        this.copyIntoArray(objectArray, 0);
        return objectArray;
    }

    @Override
    @CanIgnoreReturnValue
    public final <T> T[] toArray(T[] TArray) {
        Preconditions.checkNotNull(TArray);
        int n = this.size();
        if (TArray.length < n) {
            TArray = ObjectArrays.newArray(TArray, n);
        } else if (TArray.length > n) {
            TArray[n] = null;
        }
        this.copyIntoArray(TArray, 0);
        return TArray;
    }

    @Override
    public abstract boolean contains(@Nullable Object var1);

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean removeIf(Predicate<? super E> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        switch (this.size()) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return ImmutableList.of(this.iterator().next());
            }
        }
        return new RegularImmutableAsList(this, this.toArray());
    }

    abstract boolean isPartialView();

    @CanIgnoreReturnValue
    int copyIntoArray(Object[] objectArray, int n) {
        for (Object e : this) {
            objectArray[n++] = e;
        }
        return n;
    }

    Object writeReplace() {
        return new ImmutableList.SerializedForm(this.toArray());
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    static abstract class ArrayBasedBuilder<E>
    extends Builder<E> {
        Object[] contents;
        int size;

        ArrayBasedBuilder(int n) {
            CollectPreconditions.checkNonnegative(n, "initialCapacity");
            this.contents = new Object[n];
            this.size = 0;
        }

        private void ensureCapacity(int n) {
            if (this.contents.length < n) {
                this.contents = Arrays.copyOf(this.contents, ArrayBasedBuilder.expandedCapacity(this.contents.length, n));
            }
        }

        @Override
        @CanIgnoreReturnValue
        public ArrayBasedBuilder<E> add(E e) {
            Preconditions.checkNotNull(e);
            this.ensureCapacity(this.size + 1);
            this.contents[this.size++] = e;
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E ... EArray) {
            ObjectArrays.checkElementsNotNull((Object[])EArray);
            this.ensureCapacity(this.size + EArray.length);
            System.arraycopy(EArray, 0, this.contents, this.size, EArray.length);
            this.size += EArray.length;
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Collection) {
                Collection collection = (Collection)iterable;
                this.ensureCapacity(this.size + collection.size());
            }
            super.addAll(iterable);
            return this;
        }

        @CanIgnoreReturnValue
        ArrayBasedBuilder<E> combine(ArrayBasedBuilder<E> arrayBasedBuilder) {
            Preconditions.checkNotNull(arrayBasedBuilder);
            this.ensureCapacity(this.size + arrayBasedBuilder.size);
            System.arraycopy(arrayBasedBuilder.contents, 0, this.contents, this.size, arrayBasedBuilder.size);
            this.size += arrayBasedBuilder.size;
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder add(Object object) {
            return this.add(object);
        }
    }

    public static abstract class Builder<E> {
        static final int DEFAULT_INITIAL_CAPACITY = 4;

        static int expandedCapacity(int n, int n2) {
            if (n2 < 0) {
                throw new AssertionError((Object)"cannot store more than MAX_VALUE elements");
            }
            int n3 = n + (n >> 1) + 1;
            if (n3 < n2) {
                n3 = Integer.highestOneBit(n2 - 1) << 1;
            }
            if (n3 < 0) {
                n3 = Integer.MAX_VALUE;
            }
            return n3;
        }

        Builder() {
        }

        @CanIgnoreReturnValue
        public abstract Builder<E> add(E var1);

        @CanIgnoreReturnValue
        public Builder<E> add(E ... EArray) {
            for (E e : EArray) {
                this.add(e);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            for (E e : iterable) {
                this.add(e);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            while (iterator2.hasNext()) {
                this.add(iterator2.next());
            }
            return this;
        }

        public abstract ImmutableCollection<E> build();
    }
}

