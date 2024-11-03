package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.ints.IntCollection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntFunction;

public interface Object2IntMap<K> extends Object2IntFunction<K>, Map<K, Integer> {
   @Override
   int size();

   @Override
   default void clear() {
      throw new UnsupportedOperationException();
   }

   @Override
   void defaultReturnValue(int var1);

   @Override
   int defaultReturnValue();

   ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet();

   @Deprecated
   default ObjectSet<java.util.Map.Entry<K, Integer>> entrySet() {
      return this.object2IntEntrySet();
   }

   @Deprecated
   @Override
   default Integer put(K key, Integer value) {
      return Object2IntFunction.super.put(key, value);
   }

   @Deprecated
   @Override
   default Integer get(Object key) {
      return Object2IntFunction.super.get(key);
   }

   @Deprecated
   @Override
   default Integer remove(Object key) {
      return Object2IntFunction.super.remove(key);
   }

   ObjectSet<K> keySet();

   IntCollection values();

   @Override
   boolean containsKey(Object var1);

   boolean containsValue(int var1);

   @Deprecated
   @Override
   default boolean containsValue(Object value) {
      return value == null ? false : this.containsValue(((Integer)value).intValue());
   }

   @Override
   default void forEach(BiConsumer<? super K, ? super Integer> consumer) {
      ObjectSet<Object2IntMap.Entry<K>> entrySet = this.object2IntEntrySet();
      Consumer<Object2IntMap.Entry<K>> wrappingConsumer = entry -> consumer.accept(entry.getKey(), entry.getIntValue());
      if (entrySet instanceof Object2IntMap.FastEntrySet) {
         ((Object2IntMap.FastEntrySet)entrySet).fastForEach(wrappingConsumer);
      } else {
         entrySet.forEach(wrappingConsumer);
      }
   }

   @Override
   default int getOrDefault(Object key, int defaultValue) {
      int v;
      return (v = this.getInt(key)) == this.defaultReturnValue() && !this.containsKey(key) ? defaultValue : v;
   }

   @Deprecated
   @Override
   default Integer getOrDefault(Object key, Integer defaultValue) {
      return Map.super.getOrDefault(key, defaultValue);
   }

   default int putIfAbsent(K key, int value) {
      int v = this.getInt(key);
      int drv = this.defaultReturnValue();
      if (v == drv && !this.containsKey(key)) {
         this.put(key, value);
         return drv;
      } else {
         return v;
      }
   }

   default boolean remove(Object key, int value) {
      int curValue = this.getInt(key);
      if (curValue == value && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.removeInt(key);
         return true;
      } else {
         return false;
      }
   }

   default boolean replace(K key, int oldValue, int newValue) {
      int curValue = this.getInt(key);
      if (curValue == oldValue && (curValue != this.defaultReturnValue() || this.containsKey(key))) {
         this.put(key, newValue);
         return true;
      } else {
         return false;
      }
   }

   default int replace(K key, int value) {
      return this.containsKey(key) ? this.put(key, value) : this.defaultReturnValue();
   }

   default int computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int v = this.getInt(key);
      if (v == this.defaultReturnValue() && !this.containsKey(key)) {
         int newValue = mappingFunction.applyAsInt(key);
         this.put(key, newValue);
         return newValue;
      } else {
         return v;
      }
   }

   @Deprecated
   default int computeIntIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   default int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
      Objects.requireNonNull(mappingFunction);
      int v = this.getInt(key);
      int drv = this.defaultReturnValue();
      if (v != drv || this.containsKey(key)) {
         return v;
      } else if (!mappingFunction.containsKey(key)) {
         return drv;
      } else {
         int newValue = mappingFunction.getInt(key);
         this.put(key, newValue);
         return newValue;
      }
   }

   @Deprecated
   default int computeIntIfAbsentPartial(K key, Object2IntFunction<? super K> mappingFunction) {
      return this.computeIfAbsent(key, mappingFunction);
   }

   default int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.getInt(key);
      int drv = this.defaultReturnValue();
      if (oldValue == drv && !this.containsKey(key)) {
         return drv;
      } else {
         Integer newValue = remappingFunction.apply(key, oldValue);
         if (newValue == null) {
            this.removeInt(key);
            return drv;
         } else {
            int newVal = newValue;
            this.put(key, newVal);
            return newVal;
         }
      }
   }

   default int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.getInt(key);
      int drv = this.defaultReturnValue();
      boolean contained = oldValue != drv || this.containsKey(key);
      Integer newValue = remappingFunction.apply(key, contained ? oldValue : null);
      if (newValue == null) {
         if (contained) {
            this.removeInt(key);
         }

         return drv;
      } else {
         int newVal = newValue;
         this.put(key, newVal);
         return newVal;
      }
   }

   default int merge(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.getInt(key);
      int drv = this.defaultReturnValue();
      int newValue;
      if (oldValue == drv && !this.containsKey(key)) {
         newValue = value;
      } else {
         Integer mergedValue = remappingFunction.apply(oldValue, value);
         if (mergedValue == null) {
            this.removeInt(key);
            return drv;
         }

         newValue = mergedValue;
      }

      this.put(key, newValue);
      return newValue;
   }

   default int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
      Objects.requireNonNull(remappingFunction);
      int oldValue = this.getInt(key);
      int drv = this.defaultReturnValue();
      int newValue = oldValue == drv && !this.containsKey(key) ? value : remappingFunction.applyAsInt(oldValue, value);
      this.put(key, newValue);
      return newValue;
   }

   default int mergeInt(K key, int value, com.viaversion.viaversion.libs.fastutil.ints.IntBinaryOperator remappingFunction) {
      return this.mergeInt(key, value, (IntBinaryOperator)remappingFunction);
   }

   @Deprecated
   default int mergeInt(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      return this.merge(key, value, remappingFunction);
   }

   @Deprecated
   default Integer putIfAbsent(K key, Integer value) {
      return Map.super.putIfAbsent(key, value);
   }

   @Deprecated
   @Override
   default boolean remove(Object key, Object value) {
      return Map.super.remove(key, value);
   }

   @Deprecated
   default boolean replace(K key, Integer oldValue, Integer newValue) {
      return Map.super.replace(key, oldValue, newValue);
   }

   @Deprecated
   default Integer replace(K key, Integer value) {
      return Map.super.replace(key, value);
   }

   @Deprecated
   default Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
      return Map.super.merge(key, value, remappingFunction);
   }

   public interface Entry<K> extends java.util.Map.Entry<K, Integer> {
      int getIntValue();

      int setValue(int var1);

      @Deprecated
      default Integer getValue() {
         return this.getIntValue();
      }

      @Deprecated
      default Integer setValue(Integer value) {
         return this.setValue(value.intValue());
      }
   }

   public interface FastEntrySet<K> extends ObjectSet<Object2IntMap.Entry<K>> {
      ObjectIterator<Object2IntMap.Entry<K>> fastIterator();

      default void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
         this.forEach(consumer);
      }
   }
}
