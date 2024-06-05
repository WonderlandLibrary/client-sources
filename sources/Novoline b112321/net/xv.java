package net;

import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;

public class xv {
   public static void e(PathNavigateGround var0, boolean var1) {
      var0.setAvoidsWater(var1);
   }

   public static void d(PathNavigateGround var0, boolean var1) {
      var0.setBreakDoors(var1);
   }

   public static boolean b(PathNavigateGround var0) {
      return var0.getAvoidsWater();
   }

   public static PathEntity c(PathNavigateGround var0) {
      return var0.getPath();
   }

   public static boolean a(PathNavigateGround var0) {
      return var0.getEnterDoors();
   }

   public static void c(PathNavigateGround var0, boolean var1) {
      var0.setCanSwim(var1);
   }

   public static void b(PathNavigateGround var0, boolean var1) {
      var0.setEnterDoors(var1);
   }

   public static PathEntity a(PathNavigateGround var0, BlockPos var1) {
      return var0.getPathToPos(var1);
   }

   public static void a(PathNavigateGround var0, boolean var1) {
      var0.setAvoidSun(var1);
   }
}
