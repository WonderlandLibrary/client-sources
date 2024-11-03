package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface IntConsumer extends Consumer<Integer>, java.util.function.IntConsumer {
   @Deprecated
   default void accept(Integer t) {
      this.accept(t.intValue());
   }

   default IntConsumer andThen(java.util.function.IntConsumer after) {
      Objects.requireNonNull(after);
      return t -> {
         this.accept(t);
         after.accept(t);
      };
   }

   default IntConsumer andThen(IntConsumer after) {
      return this.andThen((java.util.function.IntConsumer)after);
   }

   @Deprecated
   @Override
   default Consumer<Integer> andThen(Consumer<? super Integer> after) {
      return Consumer.super.andThen(after);
   }
}
