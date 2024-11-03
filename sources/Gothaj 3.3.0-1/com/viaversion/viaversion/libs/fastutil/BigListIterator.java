package com.viaversion.viaversion.libs.fastutil;

public interface BigListIterator<K> extends BidirectionalIterator<K> {
   long nextIndex();

   long previousIndex();

   default void set(K e) {
      throw new UnsupportedOperationException();
   }

   default void add(K e) {
      throw new UnsupportedOperationException();
   }
}
