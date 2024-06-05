package net;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;

public class afp {
   private static int[] b;

   public static boolean b(PathNavigate var0) {
      return var0.noPath();
   }

   public static boolean a(PathNavigate var0, Entity var1, double var2) {
      return var0.tryMoveToEntityLiving(var1, var2);
   }

   public static void a(PathNavigate var0) {
      var0.clearPathEntity();
   }

   public static boolean a(PathNavigate var0, double var1, double var3, double var5, double var7) {
      return var0.tryMoveToXYZ(var1, var3, var5, var7);
   }

   public static void a(PathNavigate var0, float var1) {
      var0.setHeightRequirement(var1);
   }

   public static void a(PathNavigate var0, double var1) {
      var0.setSpeed(var1);
   }

   public static PathEntity d(PathNavigate var0) {
      return var0.getPath();
   }

   public static PathEntity a(PathNavigate var0, double var1, double var3, double var5) {
      return var0.getPathToXYZ(var1, var3, var5);
   }

   public static boolean a(PathNavigate var0, PathEntity var1, double var2) {
      return var0.a(var1, var2);
   }

   public static void c(PathNavigate var0) {
      var0.onUpdateNavigation();
   }

   public static PathEntity a(PathNavigate var0, Entity var1) {
      return var0.getPathToEntityLiving(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[3]);
      }

   }
}
