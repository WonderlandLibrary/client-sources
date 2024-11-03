package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Function;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;

@FunctionalInterface
public interface Object2ObjectFunction<K, V> extends Function<K, V> {
   @Override
   default V put(K key, V value) {
      throw new UnsupportedOperationException();
   }

   @Override
   V get(Object var1);

   @Override
   default V getOrDefault(Object key, V defaultValue) {
      V v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   @Override
   default V remove(Object key) {
      throw new UnsupportedOperationException();
   }

   default void defaultReturnValue(V rv) {
      throw new UnsupportedOperationException();
   }

   default V defaultReturnValue() {
      return null;
   }

   default Object2ByteFunction<K> andThenByte(Object2ByteFunction<V> after) {
      return k -> after.getByte(this.get(k));
   }

   default Byte2ObjectFunction<V> composeByte(Byte2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2ShortFunction<K> andThenShort(Object2ShortFunction<V> after) {
      return k -> after.getShort(this.get(k));
   }

   default Short2ObjectFunction<V> composeShort(Short2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2IntFunction<K> andThenInt(Object2IntFunction<V> after) {
      return k -> after.getInt(this.get(k));
   }

   default Int2ObjectFunction<V> composeInt(Int2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2LongFunction<K> andThenLong(Object2LongFunction<V> after) {
      return k -> after.getLong(this.get(k));
   }

   default Long2ObjectFunction<V> composeLong(Long2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2CharFunction<K> andThenChar(Object2CharFunction<V> after) {
      return k -> after.getChar(this.get(k));
   }

   default Char2ObjectFunction<V> composeChar(Char2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2FloatFunction<K> andThenFloat(Object2FloatFunction<V> after) {
      return k -> after.getFloat(this.get(k));
   }

   default Float2ObjectFunction<V> composeFloat(Float2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default Object2DoubleFunction<K> andThenDouble(Object2DoubleFunction<V> after) {
      return k -> after.getDouble(this.get(k));
   }

   default Double2ObjectFunction<V> composeDouble(Double2ObjectFunction<K> before) {
      return k -> this.get(before.get(k));
   }

   default <T> Object2ObjectFunction<K, T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {
      return k -> (T)after.get(this.get(k));
   }

   default <T> Object2ObjectFunction<T, V> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
      return k -> this.get(before.get(k));
   }

   default <T> Object2ReferenceFunction<K, T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {
      return k -> after.get(this.get(k));
   }

   default <T> Reference2ObjectFunction<T, V> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
      return k -> this.get(before.get(k));
   }
}
