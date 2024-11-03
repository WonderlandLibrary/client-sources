package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectObjectImmutablePair;
import java.util.Comparator;

public interface Pair<L, R> {
   L left();

   R right();

   default Pair<L, R> left(L l) {
      throw new UnsupportedOperationException();
   }

   default Pair<L, R> right(R r) {
      throw new UnsupportedOperationException();
   }

   default L first() {
      return this.left();
   }

   default R second() {
      return this.right();
   }

   default Pair<L, R> first(L l) {
      return this.left(l);
   }

   default Pair<L, R> second(R r) {
      return this.right(r);
   }

   default Pair<L, R> key(L l) {
      return this.left(l);
   }

   default Pair<L, R> value(R r) {
      return this.right(r);
   }

   default L key() {
      return this.left();
   }

   default R value() {
      return this.right();
   }

   static <L, R> Pair<L, R> of(L l, R r) {
      return new ObjectObjectImmutablePair<>(l, r);
   }

   static <L, R> Comparator<Pair<L, R>> lexComparator() {
      return (x, y) -> {
         int t = ((Comparable)x.left()).compareTo(y.left());
         return t != 0 ? t : ((Comparable)x.right()).compareTo(y.right());
      };
   }
}
