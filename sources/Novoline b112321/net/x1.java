package net;

import org.apache.commons.lang3.ArrayUtils;

public class x1 {
   public static boolean a(Object[] var0) {
      return ArrayUtils.isEmpty(var0);
   }

   public static boolean c(Object[] var0, Object var1) {
      return ArrayUtils.contains(var0, var1);
   }

   public static Object[] a(Object[] var0, Object[] var1) {
      return ArrayUtils.addAll(var0, var1);
   }

   public static Object[] a(Object[] var0, Object var1) {
      return ArrayUtils.add(var0, var1);
   }

   public static Object[] a(Object[] var0, int var1, Object var2) {
      return ArrayUtils.add(var0, var1, var2);
   }

   public static Object[] b(Object[] var0) {
      return ArrayUtils.clone(var0);
   }

   public static boolean c(Object[] var0) {
      return ArrayUtils.isNotEmpty(var0);
   }

   public static Object[] a(Object[] var0, int var1, int var2) {
      return ArrayUtils.subarray(var0, var1, var2);
   }

   public static int b(Object[] var0, Object var1) {
      return ArrayUtils.indexOf(var0, var1);
   }
}
