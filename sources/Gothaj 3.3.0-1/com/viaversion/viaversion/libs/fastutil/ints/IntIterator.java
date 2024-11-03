package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.PrimitiveIterator.OfInt;
import java.util.function.Consumer;

public interface IntIterator extends OfInt {
   @Override
   int nextInt();

   @Deprecated
   @Override
   default Integer next() {
      return this.nextInt();
   }

   default void forEachRemaining(IntConsumer action) {
      this.forEachRemaining((java.util.function.IntConsumer)action);
   }

   @Deprecated
   @Override
   default void forEachRemaining(Consumer<? super Integer> action) {
      this.forEachRemaining(action instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)action : action::accept);
   }

   default int skip(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("Argument must be nonnegative: " + n);
      } else {
         int i = n;

         while (i-- != 0 && this.hasNext()) {
            this.nextInt();
         }

         return n - i - 1;
      }
   }
}
