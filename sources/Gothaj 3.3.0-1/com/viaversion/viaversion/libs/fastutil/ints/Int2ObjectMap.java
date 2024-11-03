package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public interface Int2ObjectMap<V> extends Int2ObjectFunction<V>, Map<Integer, V> {
   @Override
   int size();

   @Override
   default void clear() {
      throw new UnsupportedOperationException();
   }

   @Override
   void defaultReturnValue(V var1);

   @Override
   V defaultReturnValue();

   ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

   @Deprecated
   default ObjectSet<java.util.Map.Entry<Integer, V>> entrySet() {
      return this.int2ObjectEntrySet();
   }

   @Deprecated
   @Override
   default V put(Integer key, V value) {
      return Int2ObjectFunction.super.put(key, value);
   }

   @Deprecated
   @Override
   default V get(Object key) {
      return Int2ObjectFunction.super.get(key);
   }

   @Deprecated
   @Override
   default V remove(Object key) {
      return Int2ObjectFunction.super.remove(key);
   }

   IntSet keySet();

   ObjectCollection<V> values();

   @Override
   boolean containsKey(int var1);

   @Deprecated
   @Override
   default boolean containsKey(Object key) {
      return Int2ObjectFunction.super.containsKey(key);
   }

   @Override
   default void forEach(BiConsumer<? super Integer, ? super V> consumer) {
      ObjectSet<Int2ObjectMap.Entry<V>> entrySet = this.int2ObjectEntrySet();
      Consumer<Int2ObjectMap.Entry<V>> wrappingConsumer = entry -> consumer.accept(entry.getIntKey(), entry.getValue());
      if (entrySet instanceof Int2ObjectMap.FastEntrySet) {
         ((Int2ObjectMap.FastEntrySet)entrySet).fastForEach(wrappingConsumer);
      } else {
         entrySet.forEach(wrappingConsumer);
      }
   }

   @Override
   default V getOrDefault(int key, V defaultValue) {
      V v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   @Deprecated
   @Override
   default V getOrDefault(Object key, V defaultValue) {
      return Map.super.getOrDefault(key, defaultValue);
   }

   default V putIfAbsent(int key, V value) {
      V v = this.get(key);
      V drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(int key, Object value) {
      V curValue = this.get(key);
      if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(int key, V oldValue, V newValue) {
      V curValue = this.get(key);
      if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default V replace(int key, V value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      V v = this.get(key);
      if (v == this.defaultReturnValue() && !this.containsKey(key)) {
         V newValue = (V)mappingFunction.apply(key);
         this.put(key, newValue);
         return newValue;
      } else {
         return v;
      }
   }

   default V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      V v = this.get(key);
      V drv = this.defaultReturnValue();
      if (v != drv || this.containsKey(key)) {
         return v;
      } else if (!mappingFunction.containsKey(key)) {
         return drv;
      } else {
         V newValue = (V)mappingFunction.get(key);
         this.put(key, newValue);
         return newValue;
      }
   }

   @Deprecated
   default V computeIfAbsentPartial(int key, Int2ObjectFunction<? extends V> mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   default V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      V oldValue = this.get(key);
      V drv = this.defaultReturnValue();
      if (oldValue == drv && !this.containsKey(key)) {
         return drv;
      } else {
         V newValue = (V)remappingFunction.apply(key, oldValue);
         if (newValue == null) {
            this.remove(key);
            return drv;
         } else {
            this.put(key, newValue);
            return newValue;
         }
      }
   }

   default V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      V oldValue = this.get(key);
      V drv = this.defaultReturnValue();
      boolean contained = oldValue != drv || this.containsKey(key);
      V newValue = (V)remappingFunction.apply(key, contained ? oldValue : null);
      if (newValue == null) {
         if (contained) {
            this.remove(key);
         }

         return drv;
      } else {
         this.put(key, newValue);
         return newValue;
      }
   }

   default V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      Objects.requireNonNull(value);
      V oldValue = this.get(key);
      V drv = this.defaultReturnValue();
      V newValue;
      if (oldValue == drv && !this.containsKey(key)) {
         newValue = value;
      } else {
         V mergedValue = (V)remappingFunction.apply(oldValue, value);
         if (mergedValue == null) {
            this.remove(key);
            return drv;
         }

         newValue = mergedValue;
      }

      this.put(key, newValue);
      return newValue;
   }

   public interface Entry<V> extends java.util.Map.Entry<Integer, V> {
      int getIntKey();

      @Deprecated
      default Integer getKey() {
         return this.getIntKey();
      }
   }

   public interface FastEntrySet<V> extends ObjectSet<Int2ObjectMap.Entry<V>> {
      ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator();

      default void fastForEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
         this.forEach(consumer);
      }
   }
}
