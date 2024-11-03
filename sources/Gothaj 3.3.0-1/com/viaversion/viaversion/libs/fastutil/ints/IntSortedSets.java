package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets.SynchronizedSortedSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSets.UnmodifiableSortedSet;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class IntSortedSets {
   public static final IntSortedSets.EmptySet EMPTY_SET = new IntSortedSets.EmptySet();

   private IntSortedSets() {
   }

   public static IntSortedSet singleton(int element) {
      return new IntSortedSets.Singleton(element);
   }

   public static IntSortedSet singleton(int element, IntComparator comparator) {
      return new IntSortedSets.Singleton(element, comparator);
   }

   public static IntSortedSet singleton(Object element) {
      return new IntSortedSets.Singleton((Integer)element);
   }

   public static IntSortedSet singleton(Object element, IntComparator comparator) {
      return new IntSortedSets.Singleton((Integer)element, comparator);
   }

   public static IntSortedSet synchronize(IntSortedSet s) {
      return new SynchronizedSortedSet(s);
   }

   public static IntSortedSet synchronize(IntSortedSet s, Object sync) {
      return new SynchronizedSortedSet(s, sync);
   }

   public static IntSortedSet unmodifiable(IntSortedSet s) {
      return new UnmodifiableSortedSet(s);
   }

   public static class EmptySet extends IntSets.EmptySet implements IntSortedSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      @Override
      public IntBidirectionalIterator iterator(int from) {
         return IntIterators.EMPTY_ITERATOR;
      }

      @Override
      public IntSortedSet subSet(int from, int to) {
         return IntSortedSets.EMPTY_SET;
      }

      @Override
      public IntSortedSet headSet(int from) {
         return IntSortedSets.EMPTY_SET;
      }

      @Override
      public IntSortedSet tailSet(int to) {
         return IntSortedSets.EMPTY_SET;
      }

      @Override
      public int firstInt() {
         throw new NoSuchElementException();
      }

      @Override
      public int lastInt() {
         throw new NoSuchElementException();
      }

      @Override
      public IntComparator comparator() {
         return null;
      }

      @Deprecated
      @Override
      public IntSortedSet subSet(Integer from, Integer to) {
         return IntSortedSets.EMPTY_SET;
      }

      @Deprecated
      @Override
      public IntSortedSet headSet(Integer from) {
         return IntSortedSets.EMPTY_SET;
      }

      @Deprecated
      @Override
      public IntSortedSet tailSet(Integer to) {
         return IntSortedSets.EMPTY_SET;
      }

      @Deprecated
      @Override
      public Integer first() {
         throw new NoSuchElementException();
      }

      @Deprecated
      @Override
      public Integer last() {
         throw new NoSuchElementException();
      }

      @Override
      public Object clone() {
         return IntSortedSets.EMPTY_SET;
      }

      private Object readResolve() {
         return IntSortedSets.EMPTY_SET;
      }
   }

   public static class Singleton extends IntSets.Singleton implements IntSortedSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      final IntComparator comparator;

      protected Singleton(int element, IntComparator comparator) {
         super(element);
         this.comparator = comparator;
      }

      Singleton(int element) {
         this(element, null);
      }

      final int compare(int k1, int k2) {
         return this.comparator == null ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
      }

      @Override
      public IntBidirectionalIterator iterator(int from) {
         IntBidirectionalIterator i = this.iterator();
         if (this.compare(this.element, from) <= 0) {
            i.nextInt();
         }

         return i;
      }

      @Override
      public IntComparator comparator() {
         return this.comparator;
      }

      @Override
      public IntSpliterator spliterator() {
         return IntSpliterators.singleton(this.element, this.comparator);
      }

      @Override
      public IntSortedSet subSet(int from, int to) {
         return (IntSortedSet)(this.compare(from, this.element) <= 0 && this.compare(this.element, to) < 0 ? this : IntSortedSets.EMPTY_SET);
      }

      @Override
      public IntSortedSet headSet(int to) {
         return (IntSortedSet)(this.compare(this.element, to) < 0 ? this : IntSortedSets.EMPTY_SET);
      }

      @Override
      public IntSortedSet tailSet(int from) {
         return (IntSortedSet)(this.compare(from, this.element) <= 0 ? this : IntSortedSets.EMPTY_SET);
      }

      @Override
      public int firstInt() {
         return this.element;
      }

      @Override
      public int lastInt() {
         return this.element;
      }

      @Deprecated
      @Override
      public IntSortedSet subSet(Integer from, Integer to) {
         return this.subSet(from.intValue(), to.intValue());
      }

      @Deprecated
      @Override
      public IntSortedSet headSet(Integer to) {
         return this.headSet(to.intValue());
      }

      @Deprecated
      @Override
      public IntSortedSet tailSet(Integer from) {
         return this.tailSet(from.intValue());
      }

      @Deprecated
      @Override
      public Integer first() {
         return this.element;
      }

      @Deprecated
      @Override
      public Integer last() {
         return this.element;
      }
   }
}
