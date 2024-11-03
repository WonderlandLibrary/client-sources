package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

public interface IntIterable extends Iterable<Integer> {
   IntIterator iterator();

   default IntIterator intIterator() {
      return this.iterator();
   }

   default IntSpliterator spliterator() {
      return IntSpliterators.asSpliteratorUnknownSize(this.iterator(), 0);
   }

   default IntSpliterator intSpliterator() {
      return this.spliterator();
   }

   default void forEach(java.util.function.IntConsumer action) {
      Objects.requireNonNull(action);
      this.iterator().forEachRemaining(action);
   }

   default void forEach(IntConsumer action) {
      this.forEach((java.util.function.IntConsumer)action);
   }

   @Deprecated
   @Override
   default void forEach(Consumer<? super Integer> action) {
      this.forEach(action instanceof java.util.function.IntConsumer ? (java.util.function.IntConsumer)action : action::accept);
   }
}
