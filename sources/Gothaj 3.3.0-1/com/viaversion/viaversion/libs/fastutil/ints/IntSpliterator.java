package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Spliterator.OfInt;
import java.util.function.Consumer;

public interface IntSpliterator extends OfInt {
   @Deprecated
   @Override
   default boolean tryAdvance(Consumer<? super Integer> action) {
      return this.tryAdvance(action instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)action : action::accept);
   }

   default boolean tryAdvance(IntConsumer action) {
      return this.tryAdvance((java.util.function.IntConsumer)action);
   }

   @Deprecated
   @Override
   default void forEachRemaining(Consumer<? super Integer> action) {
      this.forEachRemaining(action instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)action : action::accept);
   }

   default void forEachRemaining(IntConsumer action) {
      this.forEachRemaining((java.util.function.IntConsumer)action);
   }

   default long skip(long n) {
      if (n < 0L) {
         throw new IllegalArgumentException("Argument must be nonnegative: " + n);
      } else {
         while (n-- != 0L && this.tryAdvance(unused -> {
         })) {
         }

         byte i;
         return n - i - 1L;
      }
   }

   IntSpliterator trySplit();

   default IntComparator getComparator() {
      throw new IllegalStateException();
   }
}
