package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.SynchronizedCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections.UnmodifiableCollection;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ObjectCollections {
   private ObjectCollections() {
   }

   public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c) {
      return new SynchronizedCollection(c);
   }

   public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> c, Object sync) {
      return new SynchronizedCollection(c, sync);
   }

   public static <K> ObjectCollection<K> unmodifiable(ObjectCollection<? extends K> c) {
      return new UnmodifiableCollection(c);
   }

   public static <K> ObjectCollection<K> asCollection(ObjectIterable<K> iterable) {
      return (ObjectCollection<K>)(iterable instanceof ObjectCollection ? (ObjectCollection)iterable : new ObjectCollections.IterableCollection<>(iterable));
   }

   public abstract static class EmptyCollection<K> extends AbstractObjectCollection<K> {
      protected EmptyCollection() {
      }

      @Override
      public boolean contains(Object k) {
         return false;
      }

      @Override
      public Object[] toArray() {
         return ObjectArrays.EMPTY_ARRAY;
      }

      @Override
      public <T> T[] toArray(T[] array) {
         if (array.length > 0) {
            array[0] = null;
         }

         return array;
      }

      public ObjectBidirectionalIterator<K> iterator() {
         return ObjectIterators.EMPTY_ITERATOR;
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return ObjectSpliterators.EMPTY_SPLITERATOR;
      }

      @Override
      public int size() {
         return 0;
      }

      @Override
      public void clear() {
      }

      @Override
      public int hashCode() {
         return 0;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return !(o instanceof Collection) ? false : ((Collection)o).isEmpty();
         }
      }

      @Override
      public void forEach(Consumer<? super K> action) {
      }

      @Override
      public boolean containsAll(Collection<?> c) {
         return c.isEmpty();
      }

      @Override
      public boolean addAll(Collection<? extends K> c) {
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

      @Override
      public boolean removeIf(Predicate<? super K> filter) {
         return false;
      }
   }

   public static class IterableCollection<K> extends AbstractObjectCollection<K> implements Serializable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final ObjectIterable<K> iterable;

      protected IterableCollection(ObjectIterable<K> iterable) {
         this.iterable = Objects.requireNonNull(iterable);
      }

      @Override
      public int size() {
         long size = this.iterable.spliterator().getExactSizeIfKnown();
         if (size >= 0L) {
            return (int)Math.min(2147483647L, size);
         } else {
            int c = 0;

            for (ObjectIterator<K> iterator = this.iterator(); iterator.hasNext(); c++) {
               iterator.next();
            }

            return c;
         }
      }

      @Override
      public boolean isEmpty() {
         return !this.iterable.iterator().hasNext();
      }

      @Override
      public ObjectIterator<K> iterator() {
         return this.iterable.iterator();
      }

      @Override
      public ObjectSpliterator<K> spliterator() {
         return this.iterable.spliterator();
      }
   }

   static class SizeDecreasingSupplier<K, C extends ObjectCollection<K>> implements Supplier<C> {
      static final int RECOMMENDED_MIN_SIZE = 8;
      final AtomicInteger suppliedCount = new AtomicInteger(0);
      final int expectedFinalSize;
      final IntFunction<C> builder;

      SizeDecreasingSupplier(int expectedFinalSize, IntFunction<C> builder) {
         this.expectedFinalSize = expectedFinalSize;
         this.builder = builder;
      }

      public C get() {
         int expectedNeededNextSize = 1 + (this.expectedFinalSize - 1) / this.suppliedCount.incrementAndGet();
         if (expectedNeededNextSize < 0) {
            expectedNeededNextSize = 8;
         }

         return this.builder.apply(expectedNeededNextSize);
      }
   }
}
