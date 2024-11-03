package com.viaversion.viaversion.libs.fastutil;

import java.util.Comparator;

public interface IndirectPriorityQueue<K> {
   void enqueue(int var1);

   int dequeue();

   default boolean isEmpty() {
      return this.size() == 0;
   }

   int size();

   void clear();

   int first();

   default int last() {
      throw new UnsupportedOperationException();
   }

   default void changed() {
      this.changed(this.first());
   }

   Comparator<? super K> comparator();

   default void changed(int index) {
      throw new UnsupportedOperationException();
   }

   default void allChanged() {
      throw new UnsupportedOperationException();
   }

   default boolean contains(int index) {
      throw new UnsupportedOperationException();
   }

   default boolean remove(int index) {
      throw new UnsupportedOperationException();
   }

   default int front(int[] a) {
      throw new UnsupportedOperationException();
   }
}
