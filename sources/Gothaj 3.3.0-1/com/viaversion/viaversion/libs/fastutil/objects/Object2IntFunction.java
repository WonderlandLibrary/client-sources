package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.bytes.Byte2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ByteFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2CharFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2DoubleFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2FloatFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2LongFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ReferenceFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ShortFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2ObjectFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface Object2IntFunction<K> extends com.viaversion.viaversion.libs.fastutil.Function<K, Integer>, ToIntFunction<K> {
   @Override
   default int applyAsInt(K operand) {
      return this.getInt(operand);
   }

   default int put(K key, int value) {
      throw new UnsupportedOperationException();
   }

   int getInt(Object var1);

   default int getOrDefault(Object key, int defaultValue) {
      int v;
      return (v = this.getInt(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default int removeInt(Object key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   default Integer put(K key, Integer value) {
      boolean containsKey = this.containsKey(key);
      int v = this.put(key, value.intValue());
      return containsKey ? v : null;
   }

   @Deprecated
   default Integer get(Object key) {
      int v;
      return (v = this.getInt(key)) == this.defaultReturnValue() && !this.containsKey(key) ? null : v;
   }

   @Deprecated
   default Integer getOrDefault(Object key, Integer defaultValue) {
      int v = this.getInt(key);
      return v == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   @Deprecated
   default Integer remove(Object key) {
      return this.containsKey(key) ? this.removeInt(key) : null;
   }

   default void defaultReturnValue(int rv) {
      throw new UnsupportedOperationException();
   }

   default int defaultReturnValue() {
      return 0;
   }

   @Deprecated
   @Override
   default <T> Function<K, T> andThen(Function<? super Integer, ? extends T> after) {
      return com.viaversion.viaversion.libs.fastutil.Function.super.andThen(after);
   }

   default Object2ByteFunction<K> andThenByte(Int2ByteFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Byte2IntFunction composeByte(Byte2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2ShortFunction<K> andThenShort(Int2ShortFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Short2IntFunction composeShort(Short2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2IntFunction<K> andThenInt(Int2IntFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Int2IntFunction composeInt(Int2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2LongFunction<K> andThenLong(Int2LongFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Long2IntFunction composeLong(Long2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2CharFunction<K> andThenChar(Int2CharFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Char2IntFunction composeChar(Char2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2FloatFunction<K> andThenFloat(Int2FloatFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Float2IntFunction composeFloat(Float2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default Object2DoubleFunction<K> andThenDouble(Int2DoubleFunction after) {
      return k -> after.get(this.getInt(k));
   }

   default Double2IntFunction composeDouble(Double2ObjectFunction<K> before) {
      return k -> this.getInt(before.get(k));
   }

   default <T> Object2ObjectFunction<K, T> andThenObject(Int2ObjectFunction<? extends T> after) {
      return k -> (T)after.get(this.getInt(k));
   }

   default <T> Object2IntFunction<T> composeObject(Object2ObjectFunction<? super T, ? extends K> before) {
      return k -> this.getInt(before.get(k));
   }

   default <T> Object2ReferenceFunction<K, T> andThenReference(Int2ReferenceFunction<? extends T> after) {
      return k -> after.get(this.getInt(k));
   }

   default <T> Reference2IntFunction<T> composeReference(Reference2ObjectFunction<? super T, ? extends K> before) {
      return k -> this.getInt(before.get(k));
   }
}
