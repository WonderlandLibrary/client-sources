package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class IntObjectMutablePair<V> implements IntObjectPair<V>, Serializable {
   private static final long serialVersionUID = 0L;
   protected int left;
   protected V right;

   public IntObjectMutablePair(int left, V right) {
      this.left = left;
      this.right = right;
   }

   public static <V> IntObjectMutablePair<V> of(int left, V right) {
      return new IntObjectMutablePair<>(left, right);
   }

   @Override
   public int leftInt() {
      return this.left;
   }

   public IntObjectMutablePair<V> left(int l) {
      this.left = l;
      return this;
   }

   @Override
   public V right() {
      return this.right;
   }

   public IntObjectMutablePair<V> right(V r) {
      this.right = r;
      return this;
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else if (other instanceof IntObjectPair) {
         return this.left == ((IntObjectPair)other).leftInt() && Objects.equals(this.right, ((IntObjectPair)other).right());
      } else {
         return !(other instanceof Pair) ? false : Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   @Override
   public int hashCode() {
      return this.left * 19 + (this.right == null ? 0 : this.right.hashCode());
   }

   @Override
   public String toString() {
      return "<" + this.leftInt() + "," + this.right() + ">";
   }
}
