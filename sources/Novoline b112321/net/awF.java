package net;

import net.cN;
import viaversion.viaversion.api.entities.EntityType;

public class awF {
   private static boolean b;

   public static void b(cN var0, int var1, boolean var2) {
      var0.b(var1, var2);
   }

   public static int b(cN var0) {
      return var0.getClientEntityId();
   }

   public static void a(cN var0, int var1, EntityType var2) {
      var0.addEntity(var1, var2);
   }

   public static void a(cN var0, int var1) {
      var0.setClientEntityId(var1);
   }

   public static int a(cN var0) {
      return var0.c();
   }

   public static void a(cN var0, boolean var1) {
      var0.a(var1);
   }

   public static byte b(cN var0, int var1) {
      return var0.g(var1);
   }

   public static void a(cN var0, int var1, byte var2) {
      var0.b(var1, var2);
   }

   public static void b(cN var0, int var1, byte var2) {
      var0.a(var1, var2);
   }

   public static void a(cN var0, int var1, boolean var2) {
      var0.a(var1, var2);
   }

   public static byte f(cN var0, int var1) {
      return var0.f(var1);
   }

   public static boolean e(cN var0, int var1) {
      return var0.i(var1);
   }

   public static boolean d(cN var0, int var1) {
      return var0.j(var1);
   }

   public static void g(cN var0, int var1) {
      var0.h(var1);
   }

   public static int c(cN var0) {
      return var0.a();
   }

   public static int e(cN var0) {
      return var0.e();
   }

   public static boolean d(cN var0) {
      return var0.d();
   }

   public static void h(cN var0, int var1) {
      var0.c(var1);
   }

   public static void c(cN var0, int var1) {
      var0.e(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
