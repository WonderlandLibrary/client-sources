/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.CollectCollectors;
import com.google.common.collect.CollectSpliterators;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.SingletonImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import javax.annotation.Nullable;

@GwtCompatible(serializable=true, emulated=true)
public abstract class ImmutableList<E>
extends ImmutableCollection<E>
implements List<E>,
RandomAccess {
    @Beta
    public static <E> Collector<E, ?, ImmutableList<E>> toImmutableList() {
        return CollectCollectors.toImmutableList();
    }

    public static <E> ImmutableList<E> of() {
        return RegularImmutableList.EMPTY;
    }

    public static <E> ImmutableList<E> of(E e) {
        return new SingletonImmutableList<E>(e);
    }

    public static <E> ImmutableList<E> of(E e, E e2) {
        return ImmutableList.construct(e, e2);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3) {
        return ImmutableList.construct(e, e2, e3);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4) {
        return ImmutableList.construct(e, e2, e3, e4);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableList.construct(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
    }

    @SafeVarargs
    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E ... EArray) {
        Object[] objectArray = new Object[12 + EArray.length];
        objectArray[0] = e;
        objectArray[1] = e2;
        objectArray[2] = e3;
        objectArray[3] = e4;
        objectArray[4] = e5;
        objectArray[5] = e6;
        objectArray[6] = e7;
        objectArray[7] = e8;
        objectArray[8] = e9;
        objectArray[9] = e10;
        objectArray[10] = e11;
        objectArray[11] = e12;
        System.arraycopy(EArray, 0, objectArray, 12, EArray.length);
        return ImmutableList.construct(objectArray);
    }

    public static <E> ImmutableList<E> copyOf(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        return iterable instanceof Collection ? ImmutableList.copyOf((Collection)iterable) : ImmutableList.copyOf(iterable.iterator());
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> collection) {
        if (collection instanceof ImmutableCollection) {
            ImmutableList immutableList = ((ImmutableCollection)collection).asList();
            return immutableList.isPartialView() ? ImmutableList.asImmutableList(immutableList.toArray()) : immutableList;
        }
        return ImmutableList.construct(collection.toArray());
    }

    public static <E> ImmutableList<E> copyOf(Iterator<? extends E> iterator2) {
        if (!iterator2.hasNext()) {
            return ImmutableList.of();
        }
        E e = iterator2.next();
        if (!iterator2.hasNext()) {
            return ImmutableList.of(e);
        }
        return ((Builder)((Builder)new Builder().add((Object)e)).addAll(iterator2)).build();
    }

    public static <E> ImmutableList<E> copyOf(E[] EArray) {
        switch (EArray.length) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return new SingletonImmutableList<E>(EArray[0]);
            }
        }
        return new RegularImmutableList(ObjectArrays.checkElementsNotNull((Object[])EArray.clone()));
    }

    public static <E extends Comparable<? super E>> ImmutableList<E> sortedCopyOf(Iterable<? extends E> iterable) {
        Object[] objectArray = Iterables.toArray(iterable, new Comparable[0]);
        ObjectArrays.checkElementsNotNull(objectArray);
        Arrays.sort(objectArray);
        return ImmutableList.asImmutableList(objectArray);
    }

    public static <E> ImmutableList<E> sortedCopyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(comparator);
        Object[] objectArray = Iterables.toArray(iterable);
        ObjectArrays.checkElementsNotNull(objectArray);
        Arrays.sort(objectArray, comparator);
        return ImmutableList.asImmutableList(objectArray);
    }

    private static <E> ImmutableList<E> construct(Object ... objectArray) {
        return ImmutableList.asImmutableList(ObjectArrays.checkElementsNotNull(objectArray));
    }

    static <E> ImmutableList<E> asImmutableList(Object[] objectArray) {
        return ImmutableList.asImmutableList(objectArray, objectArray.length);
    }

    static <E> ImmutableList<E> asImmutableList(Object[] objectArray, int n) {
        switch (n) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                SingletonImmutableList<Object> singletonImmutableList = new SingletonImmutableList<Object>(objectArray[0]);
                return singletonImmutableList;
            }
        }
        if (n < objectArray.length) {
            objectArray = Arrays.copyOf(objectArray, n);
        }
        return new RegularImmutableList(objectArray);
    }

    ImmutableList() {
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public UnmodifiableListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int n) {
        return new AbstractIndexedListIterator<E>(this, this.size(), n){
            final ImmutableList this$0;
            {
                this.this$0 = immutableList;
                super(n, n2);
            }

            @Override
            protected E get(int n) {
                return this.this$0.get(n);
            }
        };
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        Preconditions.checkNotNull(consumer);
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            consumer.accept(this.get(i));
        }
    }

    @Override
    public int indexOf(@Nullable Object object) {
        return object == null ? -1 : Lists.indexOfImpl(this, object);
    }

    @Override
    public int lastIndexOf(@Nullable Object object) {
        return object == null ? -1 : Lists.lastIndexOfImpl(this, object);
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.indexOf(object) >= 0;
    }

    @Override
    public ImmutableList<E> subList(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.size());
        int n3 = n2 - n;
        if (n3 == this.size()) {
            return this;
        }
        switch (n3) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return ImmutableList.of(this.get(n));
            }
        }
        return this.subListUnchecked(n, n2);
    }

    ImmutableList<E> subListUnchecked(int n, int n2) {
        return new SubList(this, n, n2 - n);
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final boolean addAll(int n, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final E set(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void add(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    @CanIgnoreReturnValue
    public final E remove(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void replaceAll(UnaryOperator<E> unaryOperator) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public final void sort(Comparator<? super E> comparator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final ImmutableList<E> asList() {
        return this;
    }

    @Override
    public Spliterator<E> spliterator() {
        return CollectSpliterators.indexed(this.size(), 1296, this::get);
    }

    @Override
    int copyIntoArray(Object[] objectArray, int n) {
        int n2 = this.size();
        for (int i = 0; i < n2; ++i) {
            objectArray[n + i] = this.get(i);
        }
        return n + n2;
    }

    public ImmutableList<E> reverse() {
        return this.size() <= 1 ? this : new ReverseImmutableList(this);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return Lists.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        int n = 1;
        int n2 = this.size();
        for (int i = 0; i < n2; ++i) {
            n = 31 * n + this.get(i).hashCode();
            n = ~(~n);
        }
        return n;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public ListIterator listIterator() {
        return this.listIterator();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Builder<E>
    extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        Builder(int n) {
            super(n);
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E e) {
            super.add((Object)e);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll(iterable);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> add(E ... EArray) {
            super.add(EArray);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll(iterator2);
            return this;
        }

        @Override
        @CanIgnoreReturnValue
        Builder<E> combine(ImmutableCollection.ArrayBasedBuilder<E> arrayBasedBuilder) {
            super.combine(arrayBasedBuilder);
            return this;
        }

        @Override
        public ImmutableList<E> build() {
            return ImmutableList.asImmutableList(this.contents, this.size);
        }

        @Override
        @CanIgnoreReturnValue
        ImmutableCollection.ArrayBasedBuilder combine(ImmutableCollection.ArrayBasedBuilder arrayBasedBuilder) {
            return this.combine(arrayBasedBuilder);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterable iterable) {
            return this.addAll(iterable);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object[] objectArray) {
            return this.add(objectArray);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.ArrayBasedBuilder add(Object object) {
            return this.add(object);
        }

        @Override
        public ImmutableCollection build() {
            return this.build();
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder addAll(Iterator iterator2) {
            return this.addAll(iterator2);
        }

        @Override
        @CanIgnoreReturnValue
        public ImmutableCollection.Builder add(Object object) {
            return this.add(object);
        }
    }

    static class SerializedForm
    implements Serializable {
        final Object[] elements;
        private static final long serialVersionUID = 0L;

        SerializedForm(Object[] objectArray) {
            this.elements = objectArray;
        }

        Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }

    private static class ReverseImmutableList<E>
    extends ImmutableList<E> {
        private final transient ImmutableList<E> forwardList;

        ReverseImmutableList(ImmutableList<E> immutableList) {
            this.forwardList = immutableList;
        }

        private int reverseIndex(int n) {
            return this.size() - 1 - n;
        }

        private int reversePosition(int n) {
            return this.size() - n;
        }

        @Override
        public ImmutableList<E> reverse() {
            return this.forwardList;
        }

        @Override
        public boolean contains(@Nullable Object object) {
            return this.forwardList.contains(object);
        }

        @Override
        public int indexOf(@Nullable Object object) {
            int n = this.forwardList.lastIndexOf(object);
            return n >= 0 ? this.reverseIndex(n) : -1;
        }

        @Override
        public int lastIndexOf(@Nullable Object object) {
            int n = this.forwardList.indexOf(object);
            return n >= 0 ? this.reverseIndex(n) : -1;
        }

        @Override
        public ImmutableList<E> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return ((ImmutableList)this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n))).reverse();
        }

        @Override
        public E get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.forwardList.get(this.reverseIndex(n));
        }

        @Override
        public int size() {
            return this.forwardList.size();
        }

        @Override
        boolean isPartialView() {
            return this.forwardList.isPartialView();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return super.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }
    }

    class SubList
    extends ImmutableList<E> {
        final transient int offset;
        final transient int length;
        final ImmutableList this$0;

        SubList(ImmutableList immutableList, int n, int n2) {
            this.this$0 = immutableList;
            this.offset = n;
            this.length = n2;
        }

        @Override
        public int size() {
            return this.length;
        }

        @Override
        public E get(int n) {
            Preconditions.checkElementIndex(n, this.length);
            return this.this$0.get(n + this.offset);
        }

        @Override
        public ImmutableList<E> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.length);
            return this.this$0.subList(n + this.offset, n2 + this.offset);
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return super.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }
    }
}

