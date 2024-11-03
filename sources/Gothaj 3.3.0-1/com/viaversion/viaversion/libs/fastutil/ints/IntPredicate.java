package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface IntPredicate extends Predicate<Integer>, java.util.function.IntPredicate {
   @Deprecated
   default boolean test(Integer t) {
      return this.test(t.intValue());
   }

   default IntPredicate and(java.util.function.IntPredicate other) {
      Objects.requireNonNull(other);
      return t -> this.test(t) && other.test(t);
   }

   default IntPredicate and(IntPredicate other) {
      return this.and((java.util.function.IntPredicate)other);
   }

   @Deprecated
   @Override
   default Predicate<Integer> and(Predicate<? super Integer> other) {
      return Predicate.super.and(other);
   }

   default IntPredicate negate() {
      return t -> !this.test(t);
   }

   default IntPredicate or(java.util.function.IntPredicate other) {
      Objects.requireNonNull(other);
      return t -> this.test(t) || other.test(t);
   }

   default IntPredicate or(IntPredicate other) {
      return this.or((java.util.function.IntPredicate)other);
   }

   @Deprecated
   @Override
   default Predicate<Integer> or(Predicate<? super Integer> other) {
      return Predicate.super.or(other);
   }
}
