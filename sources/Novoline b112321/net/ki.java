package net;

import net.minecraft.pathfinding.PathPoint;

public class ki {
   public static float b(PathPoint var0, PathPoint var1) {
      return var0.distanceTo(var1);
   }

   public static int a(int var0, int var1, int var2) {
      return PathPoint.makeHash(var0, var1, var2);
   }

   public static float a(PathPoint var0, PathPoint var1) {
      return var0.distanceToSquared(var1);
   }

   public static boolean a(PathPoint var0) {
      return var0.isAssigned();
   }
}
