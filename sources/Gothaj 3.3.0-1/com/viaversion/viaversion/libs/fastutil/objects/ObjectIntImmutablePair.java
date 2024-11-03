package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectIntImmutablePair<K> implements ObjectIntPair<K>, Serializable {
   private static final long serialVersionUID = 0L;
   protected final K left;
   protected final int right;

   public ObjectIntImmutablePair(K left, int right) {
      this.left = left;
      this.right = right;
   }

   public static <K> ObjectIntImmutablePair<K> of(K left, int right) {
      return new ObjectIntImmutablePair<>(left, right);
   }

   @Override
   public K left() {
      return this.left;
   }

   @Override
   public int rightInt() {
      return this.right;
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof ObjectIntPair) {
         return Objects.equals(this.left, ((ObjectIntPair)other).left()) && this.right == ((ObjectIntPair)other).rightInt();
      } else {
         return !(other instanceof Pair) ? false : Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   @Override
   public int hashCode() {
      return (this.left == null ? 0 : this.left.hashCode()) * 19 + this.right;
   }

   @Override
   public String toString() {
      return "<" + this.left() + "," + this.rightInt() + ">";
   }
}
