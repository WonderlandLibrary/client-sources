package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Size64;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface IntCollection extends Collection<Integer>, IntIterable {
   @Override
   IntIterator iterator();

   @Override
   default IntIterator intIterator() {
      return this.iterator();
   }

   @Override
   default IntSpliterator spliterator() {
      return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 320);
   }

   @Override
   default IntSpliterator intSpliterator() {
      return this.spliterator();
   }

   boolean add(int var1);

   boolean contains(int var1);

   boolean rem(int var1);

   @Deprecated
   default boolean add(Integer key) {
      return this.add(key.intValue());
   }

   @Deprecated
   @Override
   default boolean contains(Object key) {
      return key == null ? false : this.contains(((Integer)key).intValue());
   }

   @Deprecated
   @Override
   default boolean remove(Object key) {
      return key == null ? false : this.rem((Integer)key);
   }

   int[] toIntArray();

   @Deprecated
   default int[] toIntArray(int[] a) {
      return this.toArray(a);
   }

   int[] toArray(int[] var1);

   boolean addAll(IntCollection var1);

   boolean containsAll(IntCollection var1);

   boolean removeAll(IntCollection var1);

   @Deprecated
   @Override
   default boolean removeIf(Predicate<? super Integer> filter) {
      return this.removeIf(filter instanceof java.util.function.IntPredicate ? (java.util.function.IntPredicate)filter : key -> filter.test(key));
   }

   default boolean removeIf(java.util.function.IntPredicate filter) {
      boolean removed = false;
      IntIterator each = this.iterator();

      while (each.hasNext()) {
         if (filter.test(each.nextInt())) {
            each.remove();
            removed = true;
         }
      }

      return removed;
   }

   default boolean removeIf(IntPredicate filter) {
      return this.removeIf(filter);
   }

   boolean retainAll(IntCollection var1);

   @Deprecated
   @Override
   default Stream<Integer> stream() {
      return Collection.super.stream();
   }

   default IntStream intStream() {
      return StreamSupport.intStream(this.intSpliterator(), false);
   }

   @Deprecated
   @Override
   default Stream<Integer> parallelStream() {
      return Collection.super.parallelStream();
   }

   default IntStream intParallelStream() {
      return StreamSupport.intStream(this.intSpliterator(), true);
   }
}
