package com.viaversion.viaversion.libs.fastutil.ints;

public abstract class AbstractIntSpliterator implements IntSpliterator {
   protected AbstractIntSpliterator() {
   }

   @Override
   public final boolean tryAdvance(IntConsumer action) {
      return this.tryAdvance(action);
   }

   @Override
   public final void forEachRemaining(IntConsumer action) {
      this.forEachRemaining(action);
   }
}
