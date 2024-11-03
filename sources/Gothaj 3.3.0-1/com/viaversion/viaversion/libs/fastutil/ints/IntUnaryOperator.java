package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface IntUnaryOperator extends UnaryOperator<Integer>, java.util.function.IntUnaryOperator {
   int apply(int var1);

   static IntUnaryOperator identity() {
      return i -> i;
   }

   static IntUnaryOperator negation() {
      return i -> -i;
   }

   @Deprecated
   @Override
   default int applyAsInt(int x) {
      return this.apply(x);
   }

   @Deprecated
   default Integer apply(Integer x) {
      return this.apply(x.intValue());
   }
}
