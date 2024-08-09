/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.viaversion.viaversion.libs.fastutil.ints.IntLists$SynchronizedList
 *  com.viaversion.viaversion.libs.fastutil.ints.IntLists$SynchronizedRandomAccessList
 *  com.viaversion.viaversion.libs.fastutil.ints.IntLists$UnmodifiableList
 *  com.viaversion.viaversion.libs.fastutil.ints.IntLists$UnmodifiableRandomAccessList
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.AbstractIntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntBidirectionalIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import com.viaversion.viaversion.libs.fastutil.ints.IntCollections;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntIterators;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import com.viaversion.viaversion.libs.fastutil.ints.IntListIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntSpliterators;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class IntLists {
    public static final EmptyList EMPTY_LIST = new EmptyList();

    private IntLists() {
    }

    public static IntList shuffle(IntList intList, Random random2) {
        int n = intList.size();
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            int n3 = intList.getInt(n);
            intList.set(n, intList.getInt(n2));
            intList.set(n2, n3);
        }
        return intList;
    }

    public static IntList emptyList() {
        return EMPTY_LIST;
    }

    public static IntList singleton(int n) {
        return new Singleton(n);
    }

    public static IntList singleton(Object object) {
        return new Singleton((Integer)object);
    }

    public static IntList synchronize(IntList intList) {
        return intList instanceof RandomAccess ? new SynchronizedRandomAccessList(intList) : new SynchronizedList(intList);
    }

    public static IntList synchronize(IntList intList, Object object) {
        return intList instanceof RandomAccess ? new SynchronizedRandomAccessList(intList, object) : new SynchronizedList(intList, object);
    }

    public static IntList unmodifiable(IntList intList) {
        return intList instanceof RandomAccess ? new UnmodifiableRandomAccessList(intList) : new UnmodifiableList(intList);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class EmptyList
    extends IntCollections.EmptyCollection
    implements IntList,
    RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptyList() {
        }

        @Override
        public int getInt(int n) {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int removeInt(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int set(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int indexOf(int n) {
            return 1;
        }

        @Override
        public int lastIndexOf(int n) {
            return 1;
        }

        @Override
        public boolean addAll(int n, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void replaceAll(UnaryOperator<Integer> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public void add(int n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer get(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public boolean add(Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer set(int n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Integer remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public int indexOf(Object object) {
            return 1;
        }

        @Override
        @Deprecated
        public int lastIndexOf(Object object) {
            return 1;
        }

        @Override
        public void sort(IntComparator intComparator) {
        }

        @Override
        public void unstableSort(IntComparator intComparator) {
        }

        @Override
        @Deprecated
        public void sort(Comparator<? super Integer> comparator) {
        }

        @Override
        @Deprecated
        public void unstableSort(Comparator<? super Integer> comparator) {
        }

        @Override
        public IntListIterator listIterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override
        public IntListIterator iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override
        public IntListIterator listIterator(int n) {
            if (n == 0) {
                return IntIterators.EMPTY_ITERATOR;
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public IntList subList(int n, int n2) {
            if (n == 0 && n2 == 0) {
                return this;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void getElements(int n, int[] nArray, int n2, int n3) {
            if (n == 0 && n3 == 0 && n2 >= 0 && n2 <= nArray.length) {
                return;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(List<? extends Integer> list) {
            if (list == this) {
                return 1;
            }
            return list.isEmpty() ? 0 : -1;
        }

        public Object clone() {
            return EMPTY_LIST;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object object) {
            return object instanceof List && ((List)object).isEmpty();
        }

        @Override
        public String toString() {
            return "[]";
        }

        private Object readResolve() {
            return EMPTY_LIST;
        }

        @Override
        public IntBidirectionalIterator iterator() {
            return this.iterator();
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Integer)object);
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

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Integer)object);
        }

        @Override
        @Deprecated
        public Object get(int n) {
            return this.get(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Singleton
    extends AbstractIntList
    implements RandomAccess,
    Serializable,
    Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;
        private final int element;

        protected Singleton(int n) {
            this.element = n;
        }

        @Override
        public int getInt(int n) {
            if (n == 0) {
                return this.element;
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        public boolean rem(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int removeInt(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean contains(int n) {
            return n == this.element;
        }

        @Override
        public int indexOf(int n) {
            return n == this.element ? 0 : -1;
        }

        @Override
        public int[] toIntArray() {
            return new int[]{this.element};
        }

        @Override
        public IntListIterator listIterator() {
            return IntIterators.singleton(this.element);
        }

        @Override
        public IntListIterator iterator() {
            return this.listIterator();
        }

        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.singleton(this.element);
        }

        @Override
        public IntListIterator listIterator(int n) {
            if (n > 1 || n < 0) {
                throw new IndexOutOfBoundsException();
            }
            IntListIterator intListIterator = this.listIterator();
            if (n == 1) {
                intListIterator.nextInt();
            }
            return intListIterator;
        }

        @Override
        public IntList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            if (n != 0 || n2 != 1) {
                return EMPTY_LIST;
            }
            return this;
        }

        @Override
        @Deprecated
        public void forEach(Consumer<? super Integer> consumer) {
            consumer.accept((Integer)this.element);
        }

        @Override
        public boolean addAll(int n, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
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
        @Deprecated
        public void replaceAll(UnaryOperator<Integer> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void replaceAll(IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            intConsumer.accept(this.element);
        }

        @Override
        public boolean addAll(IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(int n, IntCollection intCollection) {
            throw new UnsupportedOperationException();
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

        @Override
        public void sort(IntComparator intComparator) {
        }

        @Override
        public void unstableSort(IntComparator intComparator) {
        }

        @Override
        @Deprecated
        public void sort(Comparator<? super Integer> comparator) {
        }

        @Override
        @Deprecated
        public void unstableSort(Comparator<? super Integer> comparator) {
        }

        @Override
        public void getElements(int n, int[] nArray, int n2, int n3) {
            if (n2 < 0) {
                throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
            }
            if (n2 + n3 > nArray.length) {
                throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + nArray.length + ")");
            }
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
            }
            if (n3 <= 0) {
                return;
            }
            nArray[n2] = this.element;
        }

        @Override
        public void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, int[] nArray) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return this;
        }

        @Override
        public Spliterator spliterator() {
            return this.spliterator();
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

        @Override
        public Iterator iterator() {
            return this.iterator();
        }

        @Override
        public IntIterator iterator() {
            return this.iterator();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static abstract class ImmutableListBase
    extends AbstractIntList
    implements IntList {
        ImmutableListBase() {
        }

        @Override
        @Deprecated
        public final void add(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean add(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(int n, Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final int removeInt(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean rem(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeIf(Predicate<? super Integer> predicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeIf(IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void replaceAll(UnaryOperator<Integer> unaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void replaceAll(IntUnaryOperator intUnaryOperator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void add(int n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean add(Integer n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final Integer remove(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final Integer set(int n, Integer n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(int n, IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean addAll(int n, IntList intList) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean removeAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final boolean retainAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final int set(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void size(int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void removeElements(int n, int n2) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void addElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void setElements(int n, int[] nArray, int n2, int n3) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void sort(IntComparator intComparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void unstableSort(IntComparator intComparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void sort(Comparator<? super Integer> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public final void unstableSort(Comparator<? super Integer> comparator) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Object remove(int n) {
            return this.remove(n);
        }

        @Override
        @Deprecated
        public void add(int n, Object object) {
            this.add(n, (Integer)object);
        }

        @Override
        @Deprecated
        public Object set(int n, Object object) {
            return this.set(n, (Integer)object);
        }

        @Override
        @Deprecated
        public boolean add(Object object) {
            return this.add((Integer)object);
        }
    }
}

