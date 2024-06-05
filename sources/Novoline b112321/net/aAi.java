package net;

import net.a10;
import net.a87;
import org.lwjgl.util.vector.Vector3f;

public class aAi {
   private static String[] b;

   public static void a(a87 var0, a87 var1) {
      var0.a(var1);
   }

   public static void a(a87 var0, Vector3f var1, float var2) {
      var0.a(var1, var2);
   }

   public static void e(a87 var0, float var1) {
      var0.a(var1);
   }

   public static float b(a87 var0) {
      return var0.c();
   }

   public static float c(a87 var0) {
      return var0.d();
   }

   public static float a(a87 var0) {
      return var0.e();
   }

   public static void c(a87 var0, float var1, float var2) {
      var0.a(var1, var2);
   }

   public static void a(a87 var0, float var1, float var2) {
      var0.c(var1, var2);
   }

   public static void g(a87 var0, float var1) {
      var0.h(var1);
   }

   public static void a(a87 var0, float var1) {
      var0.c(var1);
   }

   public static void b(a87 var0, float var1) {
      var0.e(var1);
   }

   public static void d(a87 var0, float var1) {
      var0.g(var1);
   }

   public static void f(a87 var0, float var1) {
      var0.f(var1);
   }

   public static void b(a87 var0, float var1, float var2) {
      var0.b(var1, var2);
   }

   public static void c(a87 var0, float var1) {
      var0.d(var1);
   }

   public static void a(a87 var0, a10 var1, float var2, float var3) {
      var0.a(var1, var2, var3);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[4]);
      }

   }
}
