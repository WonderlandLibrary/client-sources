package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface Object2ObjectMap<K, V> extends Object2ObjectFunction<K, V>, Map<K, V> {
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

   ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet();

   default ObjectSet<java.util.Map.Entry<K, V>> entrySet() {
      return this.object2ObjectEntrySet();
   }

   @Override
   default V put(K key, V value) {
      return Object2ObjectFunction.super.put(key, value);
   }

   @Override
   default V remove(Object key) {
      return Object2ObjectFunction.super.remove(key);
   }

   ObjectSet<K> keySet();

   ObjectCollection<V> values();

   @Override
   boolean containsKey(Object var1);

   @Override
   default void forEach(BiConsumer<? super K, ? super V> consumer) {
      ObjectSet<Object2ObjectMap.Entry<K, V>> entrySet = this.object2ObjectEntrySet();
      Consumer<Object2ObjectMap.Entry<K, V>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), entry.getValue());
      if (entrySet instanceof Object2ObjectMap.FastEntrySet) {
         ((Object2ObjectMap.FastEntrySet)entrySet).fastForEach(wrappingConsumer);
      } else {
         entrySet.forEach(wrappingConsumer);
      }
   }

   @Override
   default V getOrDefault(Object key, V defaultValue) {
      V v;
      return (v = this.get(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   @Override
   default V putIfAbsent(K key, V value) {
      V v = this.get(key);
      V drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   @Override
   default boolean remove(Object key, Object value) {
      V curValue = this.get(key);
      if (Objects.equals(curValue, value) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.remove(key);
         return true;
      } else {
         return false;
      }
   }

   @Override
   default boolean replace(K key, V oldValue, V newValue) {
      V curValue = this.get(key);
      if (Objects.equals(curValue, oldValue) && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   @Override
   default V replace(K key, V value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default V computeIfAbsent(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
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
   default V computeObjectIfAbsentPartial(K key, Object2ObjectFunction<? super K, ? extends V> mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   @Override
   default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
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

   @Override
   default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
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

   @Override
   default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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

   public interface Entry<K, V> extends java.util.Map.Entry<K, V> {
   }

   public interface FastEntrySet<K, V> extends ObjectSet<Object2ObjectMap.Entry<K, V>> {
      ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator();

      default void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
         this.forEach(consumer);
      }
   }
}
