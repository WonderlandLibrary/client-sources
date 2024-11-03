package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.bytes.Byte2IntFunction;
import com.viaversion.viaversion.libs.fastutil.chars.Char2IntFunction;
import com.viaversion.viaversion.libs.fastutil.doubles.Double2IntFunction;
import com.viaversion.viaversion.libs.fastutil.floats.Float2IntFunction;
import com.viaversion.viaversion.libs.fastutil.longs.Long2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Reference2IntFunction;
import com.viaversion.viaversion.libs.fastutil.shorts.Short2IntFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Int2IntFunction extends com.viaversion.viaversion.libs.fastutil.Function<Integer, Integer>, java.util.function.IntUnaryOperator {
   @Override
   default int applyAsInt(int operand) {
      return this.get(operand);
   }

   default int put(int key, int value) {
      throw new UnsupportedOperationException();
   }

   int get(int var1);

   default int getOrDefault(int key, int defaultValue) {
      int v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   default int remove(int key) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   default Integer put(Integer key, Integer value) {
      int k = key;
      boolean containsKey = this.containsKey(k);
      int v = this.put(k, value.intValue());
      return containsKey ? v : null;
   }

   @Deprecated
   default Integer get(Object key) {
      if (key == null) {
         return null;
      } else {
         int k = (Integer)key;
         int v;
         return (v = this.get(k)) == this.defaultReturnValue() && !this.containsKey(k) ? null : v;
      }
   }

   @Deprecated
   default Integer getOrDefault(Object key, Integer defaultValue) {
      if (key == null) {
         return defaultValue;
      } else {
         int k = (Integer)key;
         int v = this.get(k);
         return v == this.defaultReturnValue() && !this.containsKey(k) ? defaultValue : v;
      }
   }

   @Deprecated
   default Integer remove(Object key) {
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

   default void defaultReturnValue(int rv) {
      throw new UnsupportedOperationException();
   }

   default int defaultReturnValue() {
      return 0;
   }

   static Int2IntFunction identity() {
      return k -> k;
   }

   @Deprecated
   @Override
   default <T> Function<T, Integer> compose(Function<? super T, ? extends Integer> before) {
      return com.viaversion.viaversion.libs.fastutil.Function.super.compose(before);
   }

   @Deprecated
   @Override
   default <T> Function<Integer, T> andThen(Function<? super Integer, ? extends T> after) {
      return com.viaversion.viaversion.libs.fastutil.Function.super.andThen(after);
   }

   default Int2ByteFunction andThenByte(Int2ByteFunction after) {
      return k -> after.get(this.get(k));
   }

   default Byte2IntFunction composeByte(Byte2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2ShortFunction andThenShort(Int2ShortFunction after) {
      return k -> after.get(this.get(k));
   }

   default Short2IntFunction composeShort(Short2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2IntFunction andThenInt(Int2IntFunction after) {
      return k -> after.get(this.get(k));
   }

   default Int2IntFunction composeInt(Int2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2LongFunction andThenLong(Int2LongFunction after) {
      return k -> after.get(this.get(k));
   }

   default Long2IntFunction composeLong(Long2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2CharFunction andThenChar(Int2CharFunction after) {
      return k -> after.get(this.get(k));
   }

   default Char2IntFunction composeChar(Char2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2FloatFunction andThenFloat(Int2FloatFunction after) {
      return k -> after.get(this.get(k));
   }

   default Float2IntFunction composeFloat(Float2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default Int2DoubleFunction andThenDouble(Int2DoubleFunction after) {
      return k -> after.get(this.get(k));
   }

   default Double2IntFunction composeDouble(Double2IntFunction before) {
      return k -> this.get(before.get(k));
   }

   default <T> Int2ObjectFunction<T> andThenObject(Int2ObjectFunction<? extends T> after) {
      return k -> (T)after.get(this.get(k));
   }

   default <T> Object2IntFunction<T> composeObject(Object2IntFunction<? super T> before) {
      return k -> this.get(before.getInt(k));
   }

   default <T> Int2ReferenceFunction<T> andThenReference(Int2ReferenceFunction<? extends T> after) {
      return k -> after.get(this.get(k));
   }

   default <T> Reference2IntFunction<T> composeReference(Reference2IntFunction<? super T> before) {
      return k -> this.get(before.getInt(k));
   }
}
