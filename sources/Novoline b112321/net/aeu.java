package net;

import cc.novoline.modules.visual.Waypoints$Waypoint;

public class aeu {
   public static Waypoints$Waypoint a(String var0, int var1, int var2, int var3) {
      return Waypoints$Waypoint.of(var0, var1, var2, var3);
   }

   public static String b(Waypoints$Waypoint var0) {
      return var0.getName();
   }

   public static int a(Waypoints$Waypoint var0) {
      return var0.getX();
   }

   public static int d(Waypoints$Waypoint var0) {
      return var0.getY();
   }

   public static int c(Waypoints$Waypoint var0) {
      return var0.getZ();
   }

   public static double[] a(Waypoints$Waypoint var0, double var1, double var3, double var5) {
      return var0.convertTo2D(var1, var3, var5);
   }

   public static void a(Waypoints$Waypoint var0, double[] var1) {
      var0.setPositions(var1);
   }
}
