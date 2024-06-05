package net;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Iterator;

public class aik {
   public static Iterator a(Iterator var0, int var1) {
      return Iterators.limit(var0, var1);
   }

   public static UnmodifiableIterator a(Iterator var0, Predicate var1) {
      return Iterators.filter(var0, var1);
   }

   public static UnmodifiableIterator a() {
      return Iterators.emptyIterator();
   }

   public static Iterator a(Iterator var0) {
      return Iterators.concat(var0);
   }

   public static Object[] b(Iterator var0, Class var1) {
      return Iterators.toArray(var0, var1);
   }

   public static UnmodifiableIterator a(Object[] var0) {
      return Iterators.forArray(var0);
   }

   public static Iterator a(Iterator var0, Iterator var1) {
      return Iterators.concat(var0, var1);
   }

   public static boolean a(Iterator var0, Object var1) {
      return Iterators.contains(var0, var1);
   }

   public static UnmodifiableIterator b(Iterator var0) {
      return Iterators.unmodifiableIterator(var0);
   }

   public static UnmodifiableIterator a(Iterator var0, Class var1) {
      return Iterators.filter(var0, var1);
   }

   public static boolean a(Collection var0, Iterator var1) {
      return Iterators.addAll(var0, var1);
   }

   public static Iterator a(Iterator var0, Function var1) {
      return Iterators.transform(var0, var1);
   }

   public static UnmodifiableIterator a(Object var0) {
      return Iterators.singletonIterator(var0);
   }
}
