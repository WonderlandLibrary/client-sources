package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.ListIterator;

public interface ObjectListIterator<K> extends ObjectBidirectionalIterator<K>, ListIterator<K> {
   @Override
   default void set(K k) {
      throw new UnsupportedOperationException();
   }

   @Override
   default void add(K k) {
      throw new UnsupportedOperationException();
   }

   @Override
   default void remove() {
      throw new UnsupportedOperationException();
   }
}
