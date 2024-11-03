package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSet;
import java.util.function.BiFunction;
import java.util.function.IntFunction;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Int2ObjectSyncMap<V> extends Int2ObjectMap<V> {
   @NonNull
   static <V> Int2ObjectSyncMap<V> hashmap() {
      return of(Int2ObjectOpenHashMap::new, 16);
   }

   @NonNull
   static <V> Int2ObjectSyncMap<V> hashmap(final int initialCapacity) {
      return of(Int2ObjectOpenHashMap::new, initialCapacity);
   }

   @NonNull
   static IntSet hashset() {
      return setOf(Int2ObjectOpenHashMap::new, 16);
   }

   @NonNull
   static IntSet hashset(final int initialCapacity) {
      return setOf(Int2ObjectOpenHashMap::new, initialCapacity);
   }

   @NonNull
   static <V> Int2ObjectSyncMap<V> of(@NonNull final IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<V>>> function, final int initialCapacity) {
      return new Int2ObjectSyncMapImpl<>(function, initialCapacity);
   }

   @NonNull
   static IntSet setOf(@NonNull final IntFunction<Int2ObjectMap<Int2ObjectSyncMap.ExpungingEntry<Boolean>>> function, final int initialCapacity) {
      return new Int2ObjectSyncMapSet(new Int2ObjectSyncMapImpl<>(function, initialCapacity));
   }

   @NonNull
   @Override
   ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet();

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
      Int2ObjectSyncMap.InsertionResult<V> setIfAbsent(@NonNull final V value);

      @NonNull
      Int2ObjectSyncMap.InsertionResult<V> computeIfAbsent(final int key, @NonNull final IntFunction<? extends V> function);

      @NonNull
      Int2ObjectSyncMap.InsertionResult<V> computeIfAbsentPrimitive(final int key, @NonNull final Int2ObjectFunction<? extends V> function);

      @NonNull
      Int2ObjectSyncMap.InsertionResult<V> computeIfPresent(final int key, @NonNull final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);

      @NonNull
      Int2ObjectSyncMap.InsertionResult<V> compute(final int key, @NonNull final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);

      void set(@NonNull final V value);

      boolean replace(@NonNull final Object compare, @Nullable final V value);

      @Nullable
      V clear();

      boolean trySet(@NonNull final V value);

      @Nullable
      V tryReplace(@NonNull final V value);

      boolean tryExpunge();

      boolean tryUnexpungeAndSet(@NonNull final V value);

      boolean tryUnexpungeAndCompute(final int key, @NonNull final IntFunction<? extends V> function);

      boolean tryUnexpungeAndComputePrimitive(final int key, @NonNull final Int2ObjectFunction<? extends V> function);

      boolean tryUnexpungeAndCompute(final int key, @NonNull final BiFunction<? super Integer, ? super V, ? extends V> remappingFunction);
   }

   public interface InsertionResult<V> {
      byte operation();

      @Nullable
      V previous();

      @Nullable
      V current();
   }
}
