package net;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;

public class A8 {
   public static boolean e(PathEntity var0) {
      return var0.isFinished();
   }

   public static int a(PathEntity var0) {
      return var0.getCurrentPathIndex();
   }

   public static int d(PathEntity var0) {
      return var0.getCurrentPathLength();
   }

   public static PathPoint a(PathEntity var0, int var1) {
      return var0.getPathPointFromIndex(var1);
   }

   public static Vec3 a(PathEntity var0, Entity var1, int var2) {
      return var0.getVectorFromIndex(var1, var2);
   }

   public static void b(PathEntity var0) {
      var0.incrementPathIndex();
   }

   public static void c(PathEntity var0, int var1) {
      var0.setCurrentPathIndex(var1);
   }

   public static Vec3 a(PathEntity var0, Entity var1) {
      return var0.getPosition(var1);
   }

   public static PathPoint c(PathEntity var0) {
      return var0.getFinalPathPoint();
   }

   public static boolean a(PathEntity var0, PathEntity var1) {
      return var0.a(var1);
   }

   public static boolean a(PathEntity var0, Vec3 var1) {
      return var0.isDestinationSame(var1);
   }

   public static void b(PathEntity var0, int var1) {
      var0.setCurrentPathLength(var1);
   }
}
