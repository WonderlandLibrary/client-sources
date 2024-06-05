package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.AStarCustomPathfinder;
import cc.novoline.utils.pathfinding.Vec3;
import java.util.ArrayList;

class AStarCustomPathfinder$Hub {
   private Vec3 loc;
   private AStarCustomPathfinder$Hub parent;
   private ArrayList path;
   private double squareDistanceToFromTarget;
   private double cost;
   private double totalCost;
   final AStarCustomPathfinder this$0;

   public AStarCustomPathfinder$Hub(AStarCustomPathfinder var1, Vec3 var2, AStarCustomPathfinder$Hub var3, ArrayList var4, double var5, double var7, double var9) {
      this.this$0 = var1;
      this.loc = null;
      this.parent = null;
      this.loc = var2;
      this.parent = var3;
      this.path = var4;
      this.squareDistanceToFromTarget = var5;
      this.cost = var7;
      this.totalCost = var9;
   }

   public Vec3 getLoc() {
      return this.loc;
   }

   public AStarCustomPathfinder$Hub getParent() {
      return this.parent;
   }

   public ArrayList getPath() {
      return this.path;
   }

   public double getSquareDistanceToFromTarget() {
      return this.squareDistanceToFromTarget;
   }

   public double getCost() {
      return this.cost;
   }

   public void setLoc(Vec3 var1) {
      this.loc = var1;
   }

   public void setParent(AStarCustomPathfinder$Hub var1) {
      this.parent = var1;
   }

   public void setPath(ArrayList var1) {
      this.path = var1;
   }

   public void setSquareDistanceToFromTarget(double var1) {
      this.squareDistanceToFromTarget = var1;
   }

   public void setCost(double var1) {
      this.cost = var1;
   }

   public double getTotalCost() {
      return this.totalCost;
   }

   public void setTotalCost(double var1) {
      this.totalCost = var1;
   }
}
