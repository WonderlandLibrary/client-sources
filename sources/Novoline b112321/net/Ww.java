package net;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class Ww {
   public static void a(NodeProcessor var0, IBlockAccess var1, Entity var2) {
      var0.initProcessor(var1, var2);
   }

   public static PathPoint a(NodeProcessor var0, Entity var1) {
      return var0.getPathPointTo(var1);
   }

   public static PathPoint a(NodeProcessor var0, Entity var1, double var2, double var4, double var6) {
      return var0.getPathPointToCoords(var1, var2, var4, var6);
   }

   public static void a(NodeProcessor var0) {
      var0.postProcess();
   }

   public static int a(NodeProcessor var0, PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5) {
      return var0.findPathOptions(var1, var2, var3, var4, var5);
   }
}
