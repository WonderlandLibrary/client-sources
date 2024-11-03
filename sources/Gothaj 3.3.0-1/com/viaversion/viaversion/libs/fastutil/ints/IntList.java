package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;

public interface IntList extends List<Integer>, Comparable<List<? extends Integer>>, IntCollection {
   IntListIterator iterator();

   @Override
   default IntSpliterator spliterator() {
      return (IntSpliterator)(this instanceof RandomAccess
         ? new AbstractIntList.IndexBasedSpliterator(this, 0)
         : IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 16720));
   }

   IntListIterator listIterator();

   IntListIterator listIterator(int var1);

   IntList subList(int var1, int var2);

   void size(int var1);

   void getElements(int var1, int[] var2, int var3, int var4);

   void removeElements(int var1, int var2);

   void addElements(int var1, int[] var2);

   void addElements(int var1, int[] var2, int var3, int var4);

   default void setElements(int[] a) {
      this.setElements(0, a);
   }

   default void setElements(int index, int[] a) {
      this.setElements(index, a, 0, a.length);
   }

   default void setElements(int index, int[] a, int offset, int length) {
      if (index < 0) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
      } else if (index > this.size()) {
         throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
      } else {
         IntArrays.ensureOffsetLength(a, offset, length);
         if (index + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size() + ")");
         } else {
            IntListIterator iter = this.listIterator(index);
            int i = 0;

            while (i < length) {
               iter.nextInt();
               iter.set(a[offset + i++]);
            }
         }
      }
   }

   @Override
   boolean add(int var1);

   void add(int var1, int var2);

   @Deprecated
   default void add(int index, Integer key) {
      this.add(index, key.intValue());
   }

   boolean addAll(int var1, IntCollection var2);

   int set(int var1, int var2);

   default void replaceAll(java.util.function.IntUnaryOperator operator) {
      IntListIterator iter = this.listIterator();

      while (iter.hasNext()) {
         iter.set(operator.applyAsInt(iter.nextInt()));
      }
   }

   default void replaceAll(IntUnaryOperator operator) {
      this.replaceAll((java.util.function.IntUnaryOperator)operator);
   }

   @Deprecated
   @Override
   default void replaceAll(UnaryOperator<Integer> operator) {
      this.replaceAll(operator instanceof java.util.function.IntUnaryOperator ? (java.util.function.IntUnaryOperator)operator : operator::apply);
   }

   int getInt(int var1);

   int indexOf(int var1);

   int lastIndexOf(int var1);

   @Deprecated
   @Override
   default boolean contains(Object key) {
      return IntCollection.super.contains(key);
   }

   @Deprecated
   default Integer get(int index) {
      return this.getInt(index);
   }

   @Deprecated
   @Override
   default int indexOf(Object o) {
      return this.indexOf(((Integer)o).intValue());
   }

   @Deprecated
   @Override
   default int lastIndexOf(Object o) {
      return this.lastIndexOf(((Integer)o).intValue());
   }

   @Deprecated
   @Override
   default boolean add(Integer k) {
      return this.add(k.intValue());
   }

   int removeInt(int var1);

   @Deprecated
   @Override
   default boolean remove(Object key) {
      return IntCollection.super.remove(key);
   }

   @Deprecated
   default Integer remove(int index) {
      return this.removeInt(index);
   }

   @Deprecated
   default Integer set(int index, Integer k) {
      return this.set(index, k.intValue());
   }

   default boolean addAll(int index, IntList l) {
      return this.addAll(index, (IntCollection)l);
   }

   default boolean addAll(IntList l) {
      return this.addAll(this.size(), l);
   }

   static IntList of() {
      return IntImmutableList.of();
   }

   static IntList of(int e) {
      return IntLists.singleton(e);
   }

   static IntList of(int e0, int e1) {
      return IntImmutableList.of(e0, e1);
   }

   static IntList of(int e0, int e1, int e2) {
      return IntImmutableList.of(e0, e1, e2);
   }

   static IntList of(int... a) {
      switch (a.length) {
         case 0:
            return of();
         case 1:
            return of(a[0]);
         default:
            return IntImmutableList.of(a);
      }
   }

   @Deprecated
   @Override
   default void sort(Comparator<? super Integer> comparator) {
      this.sort(IntComparators.asIntComparator(comparator));
   }

   default void sort(IntComparator comparator) {
      if (comparator == null) {
         this.unstableSort(comparator);
      } else {
         int[] elements = this.toIntArray();
         IntArrays.stableSort(elements, comparator);
         this.setElements(elements);
      }
   }

   @Deprecated
   default void unstableSort(Comparator<? super Integer> comparator) {
      this.unstableSort(IntComparators.asIntComparator(comparator));
   }

   default void unstableSort(IntComparator comparator) {
      int[] elements = this.toIntArray();
      if (comparator == null) {
         IntArrays.unstableSort(elements);
      } else {
         IntArrays.unstableSort(elements, comparator);
      }

      this.setElements(elements);
   }
}
