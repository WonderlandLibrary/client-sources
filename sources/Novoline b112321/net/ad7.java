package net;

import cc.novoline.utils.pathfinding.Vec3;

public class ad7 {
   private static String b;

   public static Vec3 a(Vec3 var0, double var1, double var3, double var5) {
      return var0.addVector(var1, var3, var5);
   }

   public static Vec3 a(Vec3 var0) {
      return var0.floor();
   }

   public static double a(Vec3 var0, Vec3 var1) {
      return var0.squareDistanceTo(var1);
   }

   public static Vec3 b(Vec3 var0, Vec3 var1) {
      return var0.add(var1);
   }

   public static double c(Vec3 var0) {
      return var0.getX();
   }

   public static double b(Vec3 var0) {
      return var0.getY();
   }

   public static double e(Vec3 var0) {
      return var0.getZ();
   }

   public static net.minecraft.util.Vec3 d(Vec3 var0) {
      return var0.mc();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("KWHMI");
      }

   }
}
