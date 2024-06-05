package net;

import kotlin.jvm.internal.Intrinsics;

public class a0n {
   private static String[] b;

   public static void a(Object var0, String var1) {
      Intrinsics.checkParameterIsNotNull(var0, var1);
   }

   public static void b(Object var0, String var1) {
      Intrinsics.checkExpressionValueIsNotNull(var0, var1);
   }

   public static boolean a(Object var0, Object var1) {
      return Intrinsics.areEqual(var0, var1);
   }

   public static void c() {
      Intrinsics.throwNpe();
   }

   public static void a(int var0, String var1) {
      Intrinsics.reifiedOperationMarker(var0, var1);
   }

   public static void a(String var0) {
      Intrinsics.throwUninitializedPropertyAccessException(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[1]);
      }

   }
}
