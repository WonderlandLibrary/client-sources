package com.viaversion.viaversion.libs.fastutil;

@FunctionalInterface
public interface Function<K, V> extends java.util.function.Function<K, V> {
   @Override
   default V apply(K key) {
      return this.get(key);
   }

   default V put(K key, V value) {
      throw new UnsupportedOperationException();
   }

   V get(Object var1);

   default V getOrDefault(Object key, V defaultValue) {
      V value = this.get(key);
      return value == null && !this.containsKey(key) ? defaultValue : value;
   }

   default boolean containsKey(Object key) {
      return true;
   }

   default V remove(Object key) {
      throw new UnsupportedOperationException();
   }

   default int size() {
      return -1;
   }

   default void clear() {
      throw new UnsupportedOperationException();
   }
}
