/*    */ package org.newdawn.slick.util.pathfinding.heuristics;
/*    */ 
/*    */ import org.newdawn.slick.util.pathfinding.AStarHeuristic;
/*    */ import org.newdawn.slick.util.pathfinding.Mover;
/*    */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClosestSquaredHeuristic
/*    */   implements AStarHeuristic
/*    */ {
/*    */   public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
/* 20 */     float dx = (tx - x);
/* 21 */     float dy = (ty - y);
/*    */     
/* 23 */     return dx * dx + dy * dy;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\heuristics\ClosestSquaredHeuristic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */