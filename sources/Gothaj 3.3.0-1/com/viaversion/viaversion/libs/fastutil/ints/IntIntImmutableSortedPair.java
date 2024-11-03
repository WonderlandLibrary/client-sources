package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;
import java.util.Objects;

public class IntIntImmutableSortedPair extends IntIntImmutablePair implements IntIntSortedPair, Serializable {
   private static final long serialVersionUID = 0L;

   private IntIntImmutableSortedPair(int left, int right) {
      super(left, right);
   }

   public static IntIntImmutableSortedPair of(int left, int right) {
      return left <= right ? new IntIntImmutableSortedPair(left, right) : new IntIntImmutableSortedPair(right, left);
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof IntIntSortedPair) {
         return this.left == ((IntIntSortedPair)other).leftInt() && this.right == ((IntIntSortedPair)other).rightInt();
      } else {
         return !(other instanceof SortedPair)
            ? false
            : Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right());
      }
   }

   @Override
   public String toString() {
      return "{" + this.leftInt() + "," + this.rightInt() + "}";
   }
}
