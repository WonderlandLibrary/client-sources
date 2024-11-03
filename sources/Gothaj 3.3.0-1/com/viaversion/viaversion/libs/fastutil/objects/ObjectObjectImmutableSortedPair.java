package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SortedPair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectObjectImmutableSortedPair<K extends Comparable<K>> extends ObjectObjectImmutablePair<K, K> implements SortedPair<K>, Serializable {
   private static final long serialVersionUID = 0L;

   private ObjectObjectImmutableSortedPair(K left, K right) {
      super(left, right);
   }

   public static <K extends Comparable<K>> ObjectObjectImmutableSortedPair<K> of(K left, K right) {
      return left.compareTo(right) <= 0 ? new ObjectObjectImmutableSortedPair<>(left, right) : new ObjectObjectImmutableSortedPair<>(right, left);
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else {
         return !(other instanceof SortedPair)
            ? false
            : Objects.equals(this.left, ((SortedPair)other).left()) && Objects.equals(this.right, ((SortedPair)other).right());
      }
   }

   @Override
   public String toString() {
      return "{" + this.left() + "," + this.right() + "}";
   }
}
