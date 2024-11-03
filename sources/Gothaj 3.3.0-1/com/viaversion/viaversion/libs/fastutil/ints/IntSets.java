package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.IntSets.SynchronizedSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSets.UnmodifiableSet;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class IntSets {
   static final int ARRAY_SET_CUTOFF = 4;
   public static final IntSets.EmptySet EMPTY_SET = new IntSets.EmptySet();
   static final IntSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));

   private IntSets() {
   }

   public static IntSet emptySet() {
      return EMPTY_SET;
   }

   public static IntSet singleton(int element) {
      return new IntSets.Singleton(element);
   }

   public static IntSet singleton(Integer element) {
      return new IntSets.Singleton(element);
   }

   public static IntSet synchronize(IntSet s) {
      return new SynchronizedSet(s);
   }

   public static IntSet synchronize(IntSet s, Object sync) {
      return new SynchronizedSet(s, sync);
   }

   public static IntSet unmodifiable(IntSet s) {
      return new UnmodifiableSet(s);
   }

   public static IntSet fromTo(final int from, final int to) {
      return new AbstractIntSet() {
         @Override
         public boolean contains(int x) {
            return x >= from && x < to;
         }

         @Override
         public IntIterator iterator() {
            return IntIterators.fromTo(from, to);
         }

         @Override
         public int size() {
            long size = (long)to - (long)from;
            return size >= 0L && size <= 2147483647L ? (int)size : Integer.MAX_VALUE;
         }
      };
   }

   public static IntSet from(final int from) {
      return new AbstractIntSet() {
         @Override
         public boolean contains(int x) {
            return x >= from;
         }

         @Override
         public IntIterator iterator() {
            return IntIterators.concat(IntIterators.fromTo(from, Integer.MAX_VALUE), IntSets.singleton(Integer.MAX_VALUE).iterator());
         }

         @Override
         public int size() {
            long size = 2147483647L - (long)from + 1L;
            return size >= 0L && size <= 2147483647L ? (int)size : Integer.MAX_VALUE;
         }
      };
   }

   public static IntSet to(final int to) {
      return new AbstractIntSet() {
         @Override
         public boolean contains(int x) {
            return x < to;
         }

         @Override
         public IntIterator iterator() {
            return IntIterators.fromTo(Integer.MIN_VALUE, to);
         }

         @Override
         public int size() {
            long size = (long)to - -2147483648L;
            return size >= 0L && size <= 2147483647L ? (int)size : Integer.MAX_VALUE;
         }
      };
   }

   public static class EmptySet extends IntCollections.EmptyCollection implements IntSet, Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptySet() {
      }

      @Override
      public boolean remove(int ok) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object clone() {
         return IntSets.EMPTY_SET;
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof Set && ((Set)o).isEmpty();
      }

      @Deprecated
      @Override
      public boolean rem(int k) {
         return super.rem(k);
      }

      private Object readResolve() {
         return IntSets.EMPTY_SET;
      }
   }

   public static class Singleton extends AbstractIntSet implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int element;

      protected Singleton(int element) {
         this.element = element;
      }

      @Override
      public boolean contains(int k) {
         return k == this.element;
      }

      @Override
      public boolean remove(int k) {
         throw new UnsupportedOperationException();
      }

      public IntListIterator iterator() {
         return IntIterators.singleton(this.element);
      }

      @Override
      public IntSpliterator spliterator() {
         return IntSpliterators.singleton(this.element);
      }

      @Override
      public int size() {
         return 1;
      }

      @Override
      public int[] toIntArray() {
         return new int[]{this.element};
      }

      @Deprecated
      @Override
      public void forEach(Consumer<? super Integer> action) {
         action.accept(this.element);
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

      @Override
      public void forEach(java.util.function.IntConsumer action) {
         action.accept(this.element);
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
      public Object clone() {
         return this;
      }
   }
}
