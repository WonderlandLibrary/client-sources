package net;

import viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;

public class hU {
   private static int b;

   public static void a(ShoulderTracker var0, int var1) {
      var0.setEntityId(var1);
   }

   public static String d(ShoulderTracker var0) {
      return var0.h();
   }

   public static void a(ShoulderTracker var0, String var1) {
      var0.a(var1);
   }

   public static void c(ShoulderTracker var0) {
      var0.update();
   }

   public static int b(ShoulderTracker var0) {
      return var0.getEntityId();
   }

   public static String a(ShoulderTracker var0) {
      return var0.g();
   }

   public static void b(ShoulderTracker var0, String var1) {
      var0.c(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 68;
   }

   static {
      if(c() == 0) {
         b(47);
      }

   }
}
