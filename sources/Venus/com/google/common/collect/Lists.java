/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.CartesianList;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.TransformedListIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class Lists {
    private Lists() {
    }

    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    @SafeVarargs
    @CanIgnoreReturnValue
    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayList(E ... EArray) {
        Preconditions.checkNotNull(EArray);
        int n = Lists.computeArrayListCapacity(EArray.length);
        ArrayList arrayList = new ArrayList(n);
        Collections.addAll(arrayList, EArray);
        return arrayList;
    }

    @VisibleForTesting
    static int computeArrayListCapacity(int n) {
        CollectPreconditions.checkNonnegative(n, "arraySize");
        return Ints.saturatedCast(5L + (long)n + (long)(n / 10));
    }

    @CanIgnoreReturnValue
    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        return iterable instanceof Collection ? new ArrayList<E>(Collections2.cast(iterable)) : Lists.newArrayList(iterable.iterator());
    }

    @CanIgnoreReturnValue
    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> iterator2) {
        ArrayList<E> arrayList = Lists.newArrayList();
        Iterators.addAll(arrayList, iterator2);
        return arrayList;
    }

    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayListWithCapacity(int n) {
        CollectPreconditions.checkNonnegative(n, "initialArraySize");
        return new ArrayList(n);
    }

    @GwtCompatible(serializable=true)
    public static <E> ArrayList<E> newArrayListWithExpectedSize(int n) {
        return new ArrayList(Lists.computeArrayListCapacity(n));
    }

    @GwtCompatible(serializable=true)
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    @GwtCompatible(serializable=true)
    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> linkedList = Lists.newLinkedList();
        Iterables.addAll(linkedList, iterable);
        return linkedList;
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }

    @GwtIncompatible
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> iterable) {
        Collection<? extends E> collection = iterable instanceof Collection ? Collections2.cast(iterable) : Lists.newArrayList(iterable);
        return new CopyOnWriteArrayList<E>(collection);
    }

    public static <E> List<E> asList(@Nullable E e, E[] EArray) {
        return new OnePlusArrayList<E>(e, EArray);
    }

    public static <E> List<E> asList(@Nullable E e, @Nullable E e2, E[] EArray) {
        return new TwoPlusArrayList<E>(e, e2, EArray);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        return CartesianList.create(list);
    }

    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B> ... listArray) {
        return Lists.cartesianProduct(Arrays.asList(listArray));
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list instanceof RandomAccess ? new TransformingRandomAccessList<F, T>(list, function) : new TransformingSequentialList<F, T>(list, function);
    }

    public static <T> List<List<T>> partition(List<T> list, int n) {
        Preconditions.checkNotNull(list);
        Preconditions.checkArgument(n > 0);
        return list instanceof RandomAccess ? new RandomAccessPartition<T>(list, n) : new Partition<T>(list, n);
    }

    public static ImmutableList<Character> charactersOf(String string) {
        return new StringAsImmutableList(Preconditions.checkNotNull(string));
    }

    @Beta
    public static List<Character> charactersOf(CharSequence charSequence) {
        return new CharSequenceAsList(Preconditions.checkNotNull(charSequence));
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList)list).reverse();
        }
        if (list instanceof ReverseList) {
            return ((ReverseList)list).getForwardList();
        }
        if (list instanceof RandomAccess) {
            return new RandomAccessReverseList<T>(list);
        }
        return new ReverseList<T>(list);
    }

    static int hashCodeImpl(List<?> list) {
        int n = 1;
        for (Object obj : list) {
            n = 31 * n + (obj == null ? 0 : obj.hashCode());
            n = ~(~n);
        }
        return n;
    }

    static boolean equalsImpl(List<?> list, @Nullable Object object) {
        if (object == Preconditions.checkNotNull(list)) {
            return false;
        }
        if (!(object instanceof List)) {
            return true;
        }
        List list2 = (List)object;
        int n = list.size();
        if (n != list2.size()) {
            return true;
        }
        if (list instanceof RandomAccess && list2 instanceof RandomAccess) {
            for (int i = 0; i < n; ++i) {
                if (Objects.equal(list.get(i), list2.get(i))) continue;
                return true;
            }
            return false;
        }
        return Iterators.elementsEqual(list.iterator(), list2.iterator());
    }

    static <E> boolean addAllImpl(List<E> list, int n, Iterable<? extends E> iterable) {
        boolean bl = false;
        ListIterator<E> listIterator2 = list.listIterator(n);
        for (E e : iterable) {
            listIterator2.add(e);
            bl = true;
        }
        return bl;
    }

    static int indexOfImpl(List<?> list, @Nullable Object object) {
        if (list instanceof RandomAccess) {
            return Lists.indexOfRandomAccess(list, object);
        }
        ListIterator<?> listIterator2 = list.listIterator();
        while (listIterator2.hasNext()) {
            if (!Objects.equal(object, listIterator2.next())) continue;
            return listIterator2.previousIndex();
        }
        return 1;
    }

    private static int indexOfRandomAccess(List<?> list, @Nullable Object object) {
        int n = list.size();
        if (object == null) {
            for (int i = 0; i < n; ++i) {
                if (list.get(i) != null) continue;
                return i;
            }
        } else {
            for (int i = 0; i < n; ++i) {
                if (!object.equals(list.get(i))) continue;
                return i;
            }
        }
        return 1;
    }

    static int lastIndexOfImpl(List<?> list, @Nullable Object object) {
        if (list instanceof RandomAccess) {
            return Lists.lastIndexOfRandomAccess(list, object);
        }
        ListIterator<?> listIterator2 = list.listIterator(list.size());
        while (listIterator2.hasPrevious()) {
            if (!Objects.equal(object, listIterator2.previous())) continue;
            return listIterator2.nextIndex();
        }
        return 1;
    }

    private static int lastIndexOfRandomAccess(List<?> list, @Nullable Object object) {
        if (object == null) {
            for (int i = list.size() - 1; i >= 0; --i) {
                if (list.get(i) != null) continue;
                return i;
            }
        } else {
            for (int i = list.size() - 1; i >= 0; --i) {
                if (!object.equals(list.get(i))) continue;
                return i;
            }
        }
        return 1;
    }

    static <E> ListIterator<E> listIteratorImpl(List<E> list, int n) {
        return new AbstractListWrapper<E>(list).listIterator(n);
    }

    static <E> List<E> subListImpl(List<E> list, int n, int n2) {
        AbstractListWrapper abstractListWrapper = list instanceof RandomAccess ? new RandomAccessListWrapper<E>((List)list){
            private static final long serialVersionUID = 0L;

            @Override
            public ListIterator<E> listIterator(int n) {
                return this.backingList.listIterator(n);
            }
        } : new AbstractListWrapper<E>((List)list){
            private static final long serialVersionUID = 0L;

            @Override
            public ListIterator<E> listIterator(int n) {
                return this.backingList.listIterator(n);
            }
        };
        return abstractListWrapper.subList(n, n2);
    }

    static <T> List<T> cast(Iterable<T> iterable) {
        return (List)iterable;
    }

    private static class RandomAccessListWrapper<E>
    extends AbstractListWrapper<E>
    implements RandomAccess {
        RandomAccessListWrapper(List<E> list) {
            super(list);
        }
    }

    private static class AbstractListWrapper<E>
    extends AbstractList<E> {
        final List<E> backingList;

        AbstractListWrapper(List<E> list) {
            this.backingList = Preconditions.checkNotNull(list);
        }

        @Override
        public void add(int n, E e) {
            this.backingList.add(n, e);
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            return this.backingList.addAll(n, collection);
        }

        @Override
        public E get(int n) {
            return this.backingList.get(n);
        }

        @Override
        public E remove(int n) {
            return this.backingList.remove(n);
        }

        @Override
        public E set(int n, E e) {
            return this.backingList.set(n, e);
        }

        @Override
        public boolean contains(Object object) {
            return this.backingList.contains(object);
        }

        @Override
        public int size() {
            return this.backingList.size();
        }
    }

    private static class RandomAccessReverseList<T>
    extends ReverseList<T>
    implements RandomAccess {
        RandomAccessReverseList(List<T> list) {
            super(list);
        }
    }

    private static class ReverseList<T>
    extends AbstractList<T> {
        private final List<T> forwardList;

        ReverseList(List<T> list) {
            this.forwardList = Preconditions.checkNotNull(list);
        }

        List<T> getForwardList() {
            return this.forwardList;
        }

        private int reverseIndex(int n) {
            int n2 = this.size();
            Preconditions.checkElementIndex(n, n2);
            return n2 - 1 - n;
        }

        private int reversePosition(int n) {
            int n2 = this.size();
            Preconditions.checkPositionIndex(n, n2);
            return n2 - n;
        }

        @Override
        public void add(int n, @Nullable T t) {
            this.forwardList.add(this.reversePosition(n), t);
        }

        @Override
        public void clear() {
            this.forwardList.clear();
        }

        @Override
        public T remove(int n) {
            return this.forwardList.remove(this.reverseIndex(n));
        }

        @Override
        protected void removeRange(int n, int n2) {
            this.subList(n, n2).clear();
        }

        @Override
        public T set(int n, @Nullable T t) {
            return this.forwardList.set(this.reverseIndex(n), t);
        }

        @Override
        public T get(int n) {
            return this.forwardList.get(this.reverseIndex(n));
        }

        @Override
        public int size() {
            return this.forwardList.size();
        }

        @Override
        public List<T> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.reverse(this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n)));
        }

        @Override
        public Iterator<T> iterator() {
            return this.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            int n2 = this.reversePosition(n);
            ListIterator<T> listIterator2 = this.forwardList.listIterator(n2);
            return new ListIterator<T>(this, listIterator2){
                boolean canRemoveOrSet;
                final ListIterator val$forwardIterator;
                final ReverseList this$0;
                {
                    this.this$0 = reverseList;
                    this.val$forwardIterator = listIterator2;
                }

                @Override
                public void add(T t) {
                    this.val$forwardIterator.add(t);
                    this.val$forwardIterator.previous();
                    this.canRemoveOrSet = false;
                }

                @Override
                public boolean hasNext() {
                    return this.val$forwardIterator.hasPrevious();
                }

                @Override
                public boolean hasPrevious() {
                    return this.val$forwardIterator.hasNext();
                }

                @Override
                public T next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.canRemoveOrSet = true;
                    return this.val$forwardIterator.previous();
                }

                @Override
                public int nextIndex() {
                    return ReverseList.access$000(this.this$0, this.val$forwardIterator.nextIndex());
                }

                @Override
                public T previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.canRemoveOrSet = true;
                    return this.val$forwardIterator.next();
                }

                @Override
                public int previousIndex() {
                    return this.nextIndex() - 1;
                }

                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.canRemoveOrSet);
                    this.val$forwardIterator.remove();
                    this.canRemoveOrSet = false;
                }

                @Override
                public void set(T t) {
                    Preconditions.checkState(this.canRemoveOrSet);
                    this.val$forwardIterator.set(t);
                }
            };
        }

        static int access$000(ReverseList reverseList, int n) {
            return reverseList.reversePosition(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class CharSequenceAsList
    extends AbstractList<Character> {
        private final CharSequence sequence;

        CharSequenceAsList(CharSequence charSequence) {
            this.sequence = charSequence;
        }

        @Override
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.sequence.charAt(n));
        }

        @Override
        public int size() {
            return this.sequence.length();
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class StringAsImmutableList
    extends ImmutableList<Character> {
        private final String string;

        StringAsImmutableList(String string) {
            this.string = string;
        }

        @Override
        public int indexOf(@Nullable Object object) {
            return object instanceof Character ? this.string.indexOf(((Character)object).charValue()) : -1;
        }

        @Override
        public int lastIndexOf(@Nullable Object object) {
            return object instanceof Character ? this.string.lastIndexOf(((Character)object).charValue()) : -1;
        }

        @Override
        public ImmutableList<Character> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.charactersOf(this.string.substring(n, n2));
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.string.charAt(n));
        }

        @Override
        public int size() {
            return this.string.length();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static class RandomAccessPartition<T>
    extends Partition<T>
    implements RandomAccess {
        RandomAccessPartition(List<T> list, int n) {
            super(list, n);
        }
    }

    private static class Partition<T>
    extends AbstractList<List<T>> {
        final List<T> list;
        final int size;

        Partition(List<T> list, int n) {
            this.list = list;
            this.size = n;
        }

        @Override
        public List<T> get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            int n2 = n * this.size;
            int n3 = Math.min(n2 + this.size, this.list.size());
            return this.list.subList(n2, n3);
        }

        @Override
        public int size() {
            return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public Object get(int n) {
            return this.get(n);
        }
    }

    private static class TransformingRandomAccessList<F, T>
    extends AbstractList<T>
    implements RandomAccess,
    Serializable {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;
        private static final long serialVersionUID = 0L;

        TransformingRandomAccessList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(list);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromList.clear();
        }

        @Override
        public T get(int n) {
            return this.function.apply(this.fromList.get(n));
        }

        @Override
        public Iterator<T> iterator() {
            return this.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            return new TransformedListIterator<F, T>(this, this.fromList.listIterator(n)){
                final TransformingRandomAccessList this$0;
                {
                    this.this$0 = transformingRandomAccessList;
                    super(listIterator2);
                }

                @Override
                T transform(F f) {
                    return this.this$0.function.apply(f);
                }
            };
        }

        @Override
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }

        @Override
        public boolean removeIf(Predicate<? super T> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.fromList.removeIf(arg_0 -> this.lambda$removeIf$0(predicate, arg_0));
        }

        @Override
        public T remove(int n) {
            return this.function.apply(this.fromList.remove(n));
        }

        @Override
        public int size() {
            return this.fromList.size();
        }

        private boolean lambda$removeIf$0(Predicate predicate, Object object) {
            return predicate.test(this.function.apply(object));
        }
    }

    private static class TransformingSequentialList<F, T>
    extends AbstractSequentialList<T>
    implements Serializable {
        final List<F> fromList;
        final Function<? super F, ? extends T> function;
        private static final long serialVersionUID = 0L;

        TransformingSequentialList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(list);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromList.clear();
        }

        @Override
        public int size() {
            return this.fromList.size();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            return new TransformedListIterator<F, T>(this, this.fromList.listIterator(n)){
                final TransformingSequentialList this$0;
                {
                    this.this$0 = transformingSequentialList;
                    super(listIterator2);
                }

                @Override
                T transform(F f) {
                    return this.this$0.function.apply(f);
                }
            };
        }

        @Override
        public boolean removeIf(Predicate<? super T> predicate) {
            Preconditions.checkNotNull(predicate);
            return this.fromList.removeIf(arg_0 -> this.lambda$removeIf$0(predicate, arg_0));
        }

        private boolean lambda$removeIf$0(Predicate predicate, Object object) {
            return predicate.test(this.function.apply(object));
        }
    }

    private static class TwoPlusArrayList<E>
    extends AbstractList<E>
    implements Serializable,
    RandomAccess {
        final E first;
        final E second;
        final E[] rest;
        private static final long serialVersionUID = 0L;

        TwoPlusArrayList(@Nullable E e, @Nullable E e2, E[] EArray) {
            this.first = e;
            this.second = e2;
            this.rest = Preconditions.checkNotNull(EArray);
        }

        @Override
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 2);
        }

        @Override
        public E get(int n) {
            switch (n) {
                case 0: {
                    return this.first;
                }
                case 1: {
                    return this.second;
                }
            }
            Preconditions.checkElementIndex(n, this.size());
            return this.rest[n - 2];
        }
    }

    private static class OnePlusArrayList<E>
    extends AbstractList<E>
    implements Serializable,
    RandomAccess {
        final E first;
        final E[] rest;
        private static final long serialVersionUID = 0L;

        OnePlusArrayList(@Nullable E e, E[] EArray) {
            this.first = e;
            this.rest = Preconditions.checkNotNull(EArray);
        }

        @Override
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 1);
        }

        @Override
        public E get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return n == 0 ? this.first : this.rest[n - 1];
        }
    }
}

