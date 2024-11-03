package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.SynchronizedFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectFunctions.UnmodifiableFunction;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Int2ObjectFunctions {
   public static final Int2ObjectFunctions.EmptyFunction EMPTY_FUNCTION = new Int2ObjectFunctions.EmptyFunction();

   private Int2ObjectFunctions() {
   }

   public static <V> Int2ObjectFunction<V> singleton(int key, V value) {
      return new Int2ObjectFunctions.Singleton<>(key, value);
   }

   public static <V> Int2ObjectFunction<V> singleton(Integer key, V value) {
      return new Int2ObjectFunctions.Singleton<>(key, value);
   }

   public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f) {
      return new SynchronizedFunction(f);
   }

   public static <V> Int2ObjectFunction<V> synchronize(Int2ObjectFunction<V> f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static <V> Int2ObjectFunction<V> unmodifiable(Int2ObjectFunction<? extends V> f) {
      return new UnmodifiableFunction(f);
   }

   public static <V> Int2ObjectFunction<V> primitive(Function<? super Integer, ? extends V> f) {
      Objects.requireNonNull(f);
      if (f instanceof Int2ObjectFunction) {
         return (Int2ObjectFunction<V>)f;
      } else {
         return (Int2ObjectFunction<V>)(f instanceof IntFunction ? ((IntFunction)f)::apply : new Int2ObjectFunctions.PrimitiveFunction<>(f));
      }
   }

   public static class EmptyFunction<V> extends AbstractInt2ObjectFunction<V> implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      @Override
      public V get(int k) {
         return null;
      }

      @Override
      public V getOrDefault(int k, V defaultValue) {
         return defaultValue;
      }

      @Override
      public boolean containsKey(int k) {
         return false;
      }

      @Override
      public V defaultReturnValue() {
         return null;
      }

      @Override
      public void defaultReturnValue(V defRetValue) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int size() {
         return 0;
      }

      @Override
      public void clear() {
      }

      @Override
      public Object clone() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }

      @Override
      public int hashCode() {
         return 0;
      }

      @Override
      public boolean equals(Object o) {
         return !(o instanceof com.viaversion.viaversion.libs.fastutil.Function) ? false : ((com.viaversion.viaversion.libs.fastutil.Function)o).size() == 0;
      }

      @Override
      public String toString() {
         return "{}";
      }

      private Object readResolve() {
         return Int2ObjectFunctions.EMPTY_FUNCTION;
      }
   }

   public static class PrimitiveFunction<V> implements Int2ObjectFunction<V> {
      protected final Function<? super Integer, ? extends V> function;

      protected PrimitiveFunction(Function<? super Integer, ? extends V> function) {
         this.function = function;
      }

      @Override
      public boolean containsKey(int key) {
         return this.function.apply(key) != null;
      }

      @Deprecated
      @Override
      public boolean containsKey(Object key) {
         return key == null ? false : this.function.apply((Integer)key) != null;
      }

      @Override
      public V get(int key) {
         V v = (V)this.function.apply(key);
         return v == null ? null : v;
      }

      @Override
      public V getOrDefault(int key, V defaultValue) {
         V v = (V)this.function.apply(key);
         return v == null ? defaultValue : v;
      }

      @Deprecated
      @Override
      public V get(Object key) {
         return (V)(key == null ? null : this.function.apply((Integer)key));
      }

      @Deprecated
      @Override
      public V getOrDefault(Object key, V defaultValue) {
         if (key == null) {
            return defaultValue;
         } else {
            V v;
            return (v = (V)this.function.apply((Integer)key)) == null ? defaultValue : v;
         }
      }

      @Deprecated
      @Override
      public V put(Integer key, V value) {
         throw new UnsupportedOperationException();
      }
   }

   public static class Singleton<V> extends AbstractInt2ObjectFunction<V> implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int key;
      protected final V value;

      protected Singleton(int key, V value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public boolean containsKey(int k) {
         return this.key == k;
      }

      @Override
      public V get(int k) {
         return this.key == k ? this.value : this.defRetValue;
      }

      @Override
      public V getOrDefault(int k, V defaultValue) {
         return this.key == k ? this.value : defaultValue;
      }

      @Override
      public int size() {
         return 1;
      }

      @Override
      public Object clone() {
         return this;
      }
   }
}
