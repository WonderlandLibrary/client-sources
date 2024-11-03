package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Pair;
import java.io.Serializable;
import java.util.Objects;

public class ObjectObjectMutablePair<K, V> implements Pair<K, V>, Serializable {
   private static final long serialVersionUID = 0L;
   protected K left;
   protected V right;

   public ObjectObjectMutablePair(K left, V right) {
      this.left = left;
      this.right = right;
   }

   public static <K, V> ObjectObjectMutablePair<K, V> of(K left, V right) {
      return new ObjectObjectMutablePair<>(left, right);
   }

   @Override
   public K left() {
      return this.left;
   }

   public ObjectObjectMutablePair<K, V> left(K l) {
      this.left = l;
      return this;
   }

   @Override
   public V right() {
      return this.right;
   }

   public ObjectObjectMutablePair<K, V> right(V r) {
      this.right = r;
      return this;
   }

   @Override
   public boolean equals(Object other) {
      if (other == null) {
         return false;
      } else {
         return !(other instanceof Pair) ? false : Objects.equals(this.left, ((Pair)other).left()) && Objects.equals(this.right, ((Pair)other).right());
      }
   }

   @Override
   public int hashCode() {
      return (this.left == null ? 0 : this.left.hashCode()) * 19 + (this.right == null ? 0 : this.right.hashCode());
   }

   @Override
   public String toString() {
      return "<" + this.left() + "," + this.right() + ">";
   }
}
