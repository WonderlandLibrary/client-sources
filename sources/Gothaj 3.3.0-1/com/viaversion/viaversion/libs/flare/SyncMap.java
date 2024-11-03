package com.viaversion.viaversion.libs.flare;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface SyncMap<K, V> extends ConcurrentMap<K, V> {
   @NonNull
   static <K, V> SyncMap<K, V> hashmap() {
      return of(HashMap::new, 16);
   }

   @NonNull
   static <K, V> SyncMap<K, V> hashmap(final int initialCapacity) {
      return of(HashMap::new, initialCapacity);
   }

   @NonNull
   static <K> Set<K> hashset() {
      return setOf(HashMap::new, 16);
   }

   @NonNull
   static <K> Set<K> hashset(final int initialCapacity) {
      return setOf(HashMap::new, initialCapacity);
   }

   @NonNull
   static <K, V> SyncMap<K, V> of(@NonNull final IntFunction<Map<K, SyncMap.ExpungingEntry<V>>> function, final int initialCapacity) {
      return new SyncMapImpl<>(function, initialCapacity);
   }

   @NonNull
   static <K> Set<K> setOf(@NonNull final IntFunction<Map<K, SyncMap.ExpungingEntry<Boolean>>> function, final int initialCapacity) {
      return Collections.newSetFromMap(new SyncMapImpl<>(function, initialCapacity));
   }

   @NonNull
   @Override
   Set<Entry<K, V>> entrySet();

   @Override
   int size();

   @Override
   void clear();

   public interface ExpungingEntry<V> {
      boolean exists();

      @Nullable
      V get();

      @NonNull
      V getOr(@NonNull final V other);

      @NonNull
      SyncMap.InsertionResult<V> setIfAbsent(@NonNull final V value);

      @NonNull
      <K> SyncMap.InsertionResult<V> computeIfAbsent(@Nullable final K key, @NonNull final Function<? super K, ? extends V> function);

      @NonNull
      <K> SyncMap.InsertionResult<V> computeIfPresent(@Nullable final K key, @NonNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction);

      @NonNull
      <K> SyncMap.InsertionResult<V> compute(@Nullable final K key, @NonNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction);

      void set(@NonNull final V value);

      boolean replace(@NonNull final Object compare, @Nullable final V value);

      @Nullable
      V clear();

      boolean trySet(@NonNull final V value);

      @Nullable
      V tryReplace(@NonNull final V value);

      boolean tryExpunge();

      boolean tryUnexpungeAndSet(@NonNull final V value);

      <K> boolean tryUnexpungeAndCompute(@Nullable final K key, @NonNull final Function<? super K, ? extends V> function);

      <K> boolean tryUnexpungeAndCompute(@Nullable final K key, @NonNull final BiFunction<? super K, ? super V, ? extends V> remappingFunction);
   }

   public interface InsertionResult<V> {
      byte operation();

      @Nullable
      V previous();

      @Nullable
      V current();
   }
}
