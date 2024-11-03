package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.SynchronizedFunction;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntFunctions.UnmodifiableFunction;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class Object2IntFunctions {
   public static final Object2IntFunctions.EmptyFunction EMPTY_FUNCTION = new Object2IntFunctions.EmptyFunction();

   private Object2IntFunctions() {
   }

   public static <K> Object2IntFunction<K> singleton(K key, int value) {
      return new Object2IntFunctions.Singleton<>(key, value);
   }

   public static <K> Object2IntFunction<K> singleton(K key, Integer value) {
      return new Object2IntFunctions.Singleton<>(key, value);
   }

   public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f) {
      return new SynchronizedFunction(f);
   }

   public static <K> Object2IntFunction<K> synchronize(Object2IntFunction<K> f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static <K> Object2IntFunction<K> unmodifiable(Object2IntFunction<? extends K> f) {
      return new UnmodifiableFunction(f);
   }

   public static <K> Object2IntFunction<K> primitive(Function<? super K, ? extends Integer> f) {
      Objects.requireNonNull(f);
      if (f instanceof Object2IntFunction) {
         return (Object2IntFunction<K>)f;
      } else {
         return (Object2IntFunction<K>)(f instanceof ToIntFunction
            ? key -> ((ToIntFunction)f).applyAsInt((K)key)
            : new Object2IntFunctions.PrimitiveFunction<>(f));
      }
   }

   public static class EmptyFunction<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      @Override
      public int getInt(Object k) {
         return 0;
      }

      @Override
      public int getOrDefault(Object k, int defaultValue) {
         return defaultValue;
      }

      @Override
      public boolean containsKey(Object k) {
         return false;
      }

      @Override
      public int defaultReturnValue() {
         return 0;
      }

      @Override
      public void defaultReturnValue(int defRetValue) {
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
         return Object2IntFunctions.EMPTY_FUNCTION;
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
         return Object2IntFunctions.EMPTY_FUNCTION;
      }
   }

   public static class PrimitiveFunction<K> implements Object2IntFunction<K> {
      protected final Function<? super K, ? extends Integer> function;

      protected PrimitiveFunction(Function<? super K, ? extends Integer> function) {
         this.function = function;
      }

      @Override
      public boolean containsKey(Object key) {
         return this.function.apply((K)key) != null;
      }

      @Override
      public int getInt(Object key) {
         Integer v = this.function.apply((K)key);
         return v == null ? this.defaultReturnValue() : v;
      }

      @Override
      public int getOrDefault(Object key, int defaultValue) {
         Integer v = this.function.apply((K)key);
         return v == null ? defaultValue : v;
      }

      @Deprecated
      @Override
      public Integer get(Object key) {
         return this.function.apply((K)key);
      }

      @Deprecated
      @Override
      public Integer getOrDefault(Object key, Integer defaultValue) {
         Integer v;
         return (v = this.function.apply((K)key)) == null ? defaultValue : v;
      }

      @Deprecated
      @Override
      public Integer put(K key, Integer value) {
         throw new UnsupportedOperationException();
      }
   }

   public static class Singleton<K> extends AbstractObject2IntFunction<K> implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final K key;
      protected final int value;

      protected Singleton(K key, int value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public boolean containsKey(Object k) {
         return Objects.equals(this.key, k);
      }

      @Override
      public int getInt(Object k) {
         return Objects.equals(this.key, k) ? this.value : this.defRetValue;
      }

      @Override
      public int getOrDefault(Object k, int defaultValue) {
         return Objects.equals(this.key, k) ? this.value : defaultValue;
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
