package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2CharFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2LongFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

@FunctionalInterface
public interface Int2ObjectFunction<V> extends com.viaversion.viaversion.libs.fastutil.Function<Integer, V>, IntFunction<V> {
   @Override
   default V apply(int operand) {
      return this.get(operand);
   }

   default V put(int key, V value) {
      throw new UnsupportedOperationException();
   }

   V get(int var1);

   default V getOrDefault(int key, V defaultValue) {
      V v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default V remove(int key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   default V put(Integer key, V value) {
      int k = key;
      boolean containsKey = this.containsKey(k);
      V v = this.put(k, value);
      return containsKey ? v : null;
   }

   @Deprecated
   @Override
   default V get(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         V v;
         return (v = this.get(k)) == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
      }
   }

   @Deprecated
   @Override
   default V getOrDefault(Object key, V defaultValue) {
      if (key == null) {
         return defaultValue;
      } else {
         int k = (Integer)key;
         V v = this.get(k);
         return v == this.defaultReturnValue() && !this.containsKey(k) ? defaultValue : v;
      }
   }

   @Deprecated
   @Override
   default V remove(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         return this.containsKey(k) ? this.remove(k) : null;
      }
   }

   default boolean containsKey(int key) {
      return true;
   }

   @Deprecated
   @Override
   default boolean containsKey(Object key) {
      return key == null ? false : this.containsKey(((Integer)key).intValue());
   }

   default void defaultReturnValue(V rv) {
      throw new UnsupportedOperationException();
   }

   default V defaultReturnValue() {
      return null;
   }

   @Deprecated
   @Override
   default <T> Function<T, V> compose(Function<? super T, ? extends Integer> before) {
      return (Function<T, V>)com.viaversion.viaversion.libs.fastutil.Function.super.compose(before);
   }

   default Int2ByteFunction andThenByte(Object2ByteFunction<V> after) {
      return k -> after.getByte(this.get(k));
   }

   default Byte2ObjectFunction<V> composeByte(Byte2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2ShortFunction andThenShort(Object2ShortFunction<V> after) {
      return k -> after.getShort(this.get(k));
   }

   default Short2ObjectFunction<V> composeShort(Short2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2IntFunction andThenInt(Object2IntFunction<V> after) {
      return k -> after.getInt(this.get(k));
   }

   default Int2ObjectFunction<V> composeInt(Int2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2LongFunction andThenLong(Object2LongFunction<V> after) {
      return k -> after.getLong(this.get(k));
   }

   default Long2ObjectFunction<V> composeLong(Long2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2CharFunction andThenChar(Object2CharFunction<V> after) {
      return k -> after.getChar(this.get(k));
   }

   default Char2ObjectFunction<V> composeChar(Char2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2FloatFunction andThenFloat(Object2FloatFunction<V> after) {
      return k -> after.getFloat(this.get(k));
   }

   default Float2ObjectFunction<V> composeFloat(Float2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2DoubleFunction andThenDouble(Object2DoubleFunction<V> after) {
      return k -> after.getDouble(this.get(k));
   }

   default Double2ObjectFunction<V> composeDouble(Double2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default <T> Int2ObjectFunction<T> andThenObject(Object2ObjectFunction<? super V, ? extends T> after) {
      return k -> (T)after.get(this.get(k));
   }

   default <T> Object2ObjectFunction<T, V> composeObject(Object2IntFunction<? super T> before) {
      return k -> this.get(before.getInt(k));
   }

   default <T> Int2ReferenceFunction<T> andThenReference(Object2ReferenceFunction<? super V, ? extends T> after) {
      return k -> after.get(this.get(k));
   }

   default <T> Reference2ObjectFunction<T, V> composeReference(Reference2IntFunction<? super T> before) {
      return k -> this.get(before.getInt(k));
   }
}
