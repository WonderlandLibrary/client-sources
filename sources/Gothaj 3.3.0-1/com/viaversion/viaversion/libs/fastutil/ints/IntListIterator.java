package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.ListIterator;

public interface IntListIterator extends IntBidirectionalIterator, ListIterator<Integer> {
   default void set(int k) {
      throw new UnsupportedOperationException();
   }

   default void add(int k) {
      throw new UnsupportedOperationException();
   }

   @Override
   default void remove() {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   default void set(Integer k) {
      this.set(k.intValue());
   }

   @Deprecated
   default void add(Integer k) {
      this.add(k.intValue());
   }

   @Deprecated
   @Override
   default Integer next() {
      return IntBidirectionalIterator.super.next();
   }

   @Deprecated
   @Override
   default Integer previous() {
      return IntBidirectionalIterator.super.previous();
   }
}
