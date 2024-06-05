package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;

public class PathEntity {
   private final PathPoint[] points;
   private int currentPathIndex;
   private int pathLength;

   public PathEntity(PathPoint[] var1) {
      this.points = var1;
      this.pathLength = var1.length;
   }

   public void incrementPathIndex() {
      ++this.currentPathIndex;
   }

   public boolean isFinished() {
      return this.currentPathIndex >= this.pathLength;
   }

   public PathPoint getFinalPathPoint() {
      return this.pathLength > 0?this.points[this.pathLength - 1]:null;
   }

   public PathPoint getPathPointFromIndex(int var1) {
      return this.points[var1];
   }

   public int getCurrentPathLength() {
      return this.pathLength;
   }

   public void setCurrentPathLength(int var1) {
      this.pathLength = var1;
   }

   public int getCurrentPathIndex() {
      return this.currentPathIndex;
   }

   public void setCurrentPathIndex(int var1) {
      this.currentPathIndex = var1;
   }

   public Vec3 getVectorFromIndex(Entity var1, int var2) {
      double var3 = (double)this.points[var2].xCoord + (double)((int)(var1.width + 1.0F)) * 0.5D;
      double var5 = (double)this.points[var2].yCoord;
      double var7 = (double)this.points[var2].zCoord + (double)((int)(var1.width + 1.0F)) * 0.5D;
      return new Vec3(var3, var5, var7);
   }

   public Vec3 getPosition(Entity var1) {
      return this.getVectorFromIndex(var1, this.currentPathIndex);
   }

   public boolean a(PathEntity var1) {
      return false;
   }

   public boolean isDestinationSame(Vec3 var1) {
      PathPoint var2 = this.getFinalPathPoint();
      return var2.xCoord == (int)var1.xCoord && var2.zCoord == (int)var1.zCoord;
   }
}
