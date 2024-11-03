package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;

public interface IntIntSortedPair extends IntIntPair, SortedPair<Integer>, Serializable {
   static IntIntSortedPair of(int left, int right) {
      return IntIntImmutableSortedPair.of(left, right);
   }

   default boolean contains(int e) {
      return e == this.leftInt() || e == this.rightInt();
   }

   @Deprecated
   @Override
   default boolean contains(Object o) {
      return o == null ? false : this.contains(((Integer)o).intValue());
   }
}
