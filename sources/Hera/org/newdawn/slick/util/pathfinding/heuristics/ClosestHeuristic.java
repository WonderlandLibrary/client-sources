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
/*    */ public class ClosestHeuristic
/*    */   implements AStarHeuristic
/*    */ {
/*    */   public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty) {
/* 18 */     float dx = (tx - x);
/* 19 */     float dy = (ty - y);
/*    */     
/* 21 */     float result = (float)Math.sqrt((dx * dx + dy * dy));
/*    */     
/* 23 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\heuristics\ClosestHeuristic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */