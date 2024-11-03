package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.SynchronizedFunction;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntFunctions.UnmodifiableFunction;
import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public final class Int2IntFunctions {
   public static final Int2IntFunctions.EmptyFunction EMPTY_FUNCTION = new Int2IntFunctions.EmptyFunction();

   private Int2IntFunctions() {
   }

   public static Int2IntFunction singleton(int key, int value) {
      return new Int2IntFunctions.Singleton(key, value);
   }

   public static Int2IntFunction singleton(Integer key, Integer value) {
      return new Int2IntFunctions.Singleton(key, value);
   }

   public static Int2IntFunction synchronize(Int2IntFunction f) {
      return new SynchronizedFunction(f);
   }

   public static Int2IntFunction synchronize(Int2IntFunction f, Object sync) {
      return new SynchronizedFunction(f, sync);
   }

   public static Int2IntFunction unmodifiable(Int2IntFunction f) {
      return new UnmodifiableFunction(f);
   }

   public static Int2IntFunction primitive(Function<? super Integer, ? extends Integer> f) {
      Objects.requireNonNull(f);
      if (f instanceof Int2IntFunction) {
         return (Int2IntFunction)f;
      } else {
         return (Int2IntFunction)(f instanceof java.util.function.IntUnaryOperator
            ? ((java.util.function.IntUnaryOperator)f)::applyAsInt
            : new Int2IntFunctions.PrimitiveFunction(f));
      }
   }

   public static class EmptyFunction extends AbstractInt2IntFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;

      protected EmptyFunction() {
      }

      @Override
      public int get(int k) {
         return 0;
      }

      @Override
      public int getOrDefault(int k, int defaultValue) {
         return defaultValue;
      }

      @Override
      public boolean containsKey(int k) {
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
         return Int2IntFunctions.EMPTY_FUNCTION;
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
         return Int2IntFunctions.EMPTY_FUNCTION;
      }
   }

   public static class PrimitiveFunction implements Int2IntFunction {
      protected final Function<? super Integer, ? extends Integer> function;

      protected PrimitiveFunction(Function<? super Integer, ? extends Integer> function) {
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
      public int get(int key) {
         Integer v = this.function.apply(key);
         return v == null ? this.defaultReturnValue() : v;
      }

      @Override
      public int getOrDefault(int key, int defaultValue) {
         Integer v = this.function.apply(key);
         return v == null ? defaultValue : v;
      }

      @Deprecated
      @Override
      public Integer get(Object key) {
         return key == null ? null : this.function.apply((Integer)key);
      }

      @Deprecated
      @Override
      public Integer getOrDefault(Object key, Integer defaultValue) {
         if (key == null) {
            return defaultValue;
         } else {
            Integer v;
            return (v = this.function.apply((Integer)key)) == null ? defaultValue : v;
         }
      }

      @Deprecated
      @Override
      public Integer put(Integer key, Integer value) {
         throw new UnsupportedOperationException();
      }
   }

   public static class Singleton extends AbstractInt2IntFunction implements Serializable, Cloneable {
      private static final long serialVersionUID = -7046029254386353129L;
      protected final int key;
      protected final int value;

      protected Singleton(int key, int value) {
         this.key = key;
         this.value = value;
      }

      @Override
      public boolean containsKey(int k) {
         return this.key == k;
      }

      @Override
      public int get(int k) {
         return this.key == k ? this.value : this.defRetValue;
      }

      @Override
      public int getOrDefault(int k, int defaultValue) {
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
