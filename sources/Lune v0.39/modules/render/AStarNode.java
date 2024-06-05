package me.superskidder.lune.modules.render;

public class AStarNode {
   private double x;
   private double z;
   private double heuristic;
   private AStarNode parent = null;

   public AStarNode(double x, double z) {
      this.x = x;
      this.z = z;
   }

   public AStarNode getParent() {
      return this.parent;
   }

   public void setParent(AStarNode parent) {
      this.parent = parent;
   }

   public double getX() {
      return this.x;
   }

   public void setX(int x) {
      this.x = (double)x;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(int y) {
      this.z = (double)y;
   }

   public double getHeuristic() {
      return this.heuristic;
   }

   public void setHeuristic(int heuristic) {
      this.heuristic = (double)heuristic;
   }
}
