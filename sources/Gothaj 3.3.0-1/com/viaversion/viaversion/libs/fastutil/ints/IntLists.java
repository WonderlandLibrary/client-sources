package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntLists.SynchronizedList;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists.SynchronizedRandomAccessList;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists.UnmodifiableList;
import com.viaversion.viaversion.libs.fastutil.ints.IntLists.UnmodifiableRandomAccessList;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class IntLists {
   public static final IntLists.EmptyList EMPTY_LIST = new IntLists.EmptyList();

   private IntLists() {
   }

   public static IntList shuffle(IntList l, Random random) {
      int i = l.size();

      while (i-- != 0) {
         int p = random.nextInt(i + 1);
         int t = l.getInt(i);
         l.set(i, l.getInt(p));
         l.set(p, t);
      }

      return l;
   }

   public static IntList emptyList() {
      return EMPTY_LIST;
   }

   public static IntList singleton(int element) {
      return new IntLists.Singleton(element);
   }

   public static IntList singleton(Object element) {
      return new IntLists.Singleton((Integer)element);
   }

   public static IntList synchronize(IntList l) {
      return (IntList)(l instanceof RandomAccess ? new SynchronizedRandomAccessList(l) : new SynchronizedList(l));
   }

   public static IntList synchronize(IntList l, Object sync) {
      return (IntList)(l instanceof RandomAccess ? new SynchronizedRandomAccessList(l, sync) : new SynchronizedList(l, sync));
   }

   public static IntList unmodifiable(IntList l) {
      return (IntList)(l instanceof RandomAccess ? new UnmodifiableRandomAccessList(l) : new UnmodifiableList(l));
   }

   public static class EmptyList extends IntCollections.EmptyCollection implements IntList, RandomAccess, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyList() {
      }

      @Override
      public int getInt(int i) {
         throw new IndexOutOfBoundsException();
      }

      @Override
      public boolean rem(int k) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int removeInt(int i) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void add(int index, int k) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int set(int index, int k) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int indexOf(int k) {
         return -1;
      }

      @Override
      public int lastIndexOf(int k) {
         return -1;
      }

      @Override
      public boolean addAll(int i, Collection<? extends Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public void replaceAll(UnaryOperator<Integer> operator) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void replaceAll(java.util.function.IntUnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(IntList c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(int i, IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(int i, IntList c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public void add(int index, Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public Integer get(int index) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public boolean add(Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public Integer set(int index, Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public Integer remove(int k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public int indexOf(Object k) {
         return -1;
      }

      @Deprecated
      @Override
      public int lastIndexOf(Object k) {
         return -1;
      }

      @Override
      public void sort(IntComparator comparator) {
      }

      @Override
      public void unstableSort(IntComparator comparator) {
      }

      @Deprecated
      @Override
      public void sort(Comparator<? super Integer> comparator) {
      }

      @Deprecated
      @Override
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
      public IntListIterator listIterator(int i) {
         if (i == 0) {
            return IntIterators.EMPTY_ITERATOR;
         } else {
            throw new IndexOutOfBoundsException(String.valueOf(i));
         }
      }

      @Override
      public IntList subList(int from, int to) {
         if (from == 0 && to == 0) {
            return this;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public void getElements(int from, int[] a, int offset, int length) {
         if (from != 0 || length != 0 || offset < 0 || offset > a.length) {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void addElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void addElements(int index, int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int index, int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void size(int s) {
         throw new UnsupportedOperationException();
      }

      public int compareTo(List<? extends Integer> o) {
         if (o == this) {
            return 0;
         } else {
            return o.isEmpty() ? 0 : -1;
         }
      }

      @Override
      public Object clone() {
         return IntLists.EMPTY_LIST;
      }

      @Override
      public int hashCode() {
         return 1;
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof List && ((List)o).isEmpty();
      }

      @Override
      public String toString() {
         return "[]";
      }

      private Object readResolve() {
         return IntLists.EMPTY_LIST;
      }
   }

   abstract static class ImmutableListBase extends AbstractIntList implements IntList {
      @Deprecated
      @Override
      public final void add(int index, int k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean add(int k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(Collection<? extends Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(int index, Collection<? extends Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final int removeInt(int index) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean rem(int k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean removeAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean retainAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean removeIf(Predicate<? super Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean removeIf(java.util.function.IntPredicate c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void replaceAll(UnaryOperator<Integer> operator) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void replaceAll(java.util.function.IntUnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void add(int index, Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean add(Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final Integer remove(int index) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean remove(Object k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final Integer set(int index, Integer k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(IntList c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(int index, IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean addAll(int index, IntList c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean removeAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final boolean retainAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final int set(int index, int k) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void clear() {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void size(int size) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void addElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void setElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void sort(IntComparator comp) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void unstableSort(IntComparator comp) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void sort(Comparator<? super Integer> comparator) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public final void unstableSort(Comparator<? super Integer> comparator) {
         throw new UnsupportedOperationException();
      }
   }

   public static class Singleton extends AbstractIntList implements RandomAccess, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      private final int element;

      protected Singleton(int element) {
         this.element = element;
      }

      @Override
      public int getInt(int i) {
         if (i == 0) {
            return this.element;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public boolean rem(int k) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int removeInt(int i) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean contains(int k) {
         return k == this.element;
      }

      @Override
      public int indexOf(int k) {
         return k == this.element ? 0 : -1;
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
      public IntListIterator listIterator(int i) {
         if (i <= 1 && i >= 0) {
            IntListIterator l = this.listIterator();
            if (i == 1) {
               l.nextInt();
            }

            return l;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public IntList subList(int from, int to) {
         this.ensureIndex(from);
         this.ensureIndex(to);
         if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
         } else {
            return (IntList)(from == 0 && to == 1 ? this : IntLists.EMPTY_LIST);
         }
      }

      @Deprecated
      @Override
      public void forEach(Consumer<? super Integer> action) {
         action.accept(this.element);
      }

      @Override
      public boolean addAll(int i, Collection<? extends Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(Collection<? extends Integer> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean retainAll(Collection<?> c) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public boolean removeIf(Predicate<? super Integer> filter) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public void replaceAll(UnaryOperator<Integer> operator) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void replaceAll(java.util.function.IntUnaryOperator operator) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void forEach(java.util.function.IntConsumer action) {
         action.accept(this.element);
      }

      @Override
      public boolean addAll(IntList c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(int i, IntList c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(int i, IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean retainAll(IntCollection c) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeIf(java.util.function.IntPredicate filter) {
         throw new UnsupportedOperationException();
      }

      @Deprecated
      @Override
      public Object[] toArray() {
         return new Object[]{this.element};
      }

      @Override
      public void sort(IntComparator comparator) {
      }

      @Override
      public void unstableSort(IntComparator comparator) {
      }

      @Deprecated
      @Override
      public void sort(Comparator<? super Integer> comparator) {
      }

      @Deprecated
      @Override
      public void unstableSort(Comparator<? super Integer> comparator) {
      }

      @Override
      public void getElements(int from, int[] a, int offset, int length) {
         if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
         } else if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
         } else if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
         } else if (length > 0) {
            a[offset] = this.element;
         }
      }

      @Override
      public void removeElements(int from, int to) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void addElements(int index, int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void addElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int index, int[] a) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setElements(int index, int[] a, int offset, int length) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int size() {
         return 1;
      }

      @Override
      public void size(int size) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void clear() {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object clone() {
         return this;
      }
   }
}
