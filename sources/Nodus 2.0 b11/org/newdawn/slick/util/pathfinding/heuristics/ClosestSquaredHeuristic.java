/*  1:   */ package org.newdawn.slick.util.pathfinding.heuristics;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.util.pathfinding.AStarHeuristic;
/*  4:   */ import org.newdawn.slick.util.pathfinding.Mover;
/*  5:   */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*  6:   */ 
/*  7:   */ public class ClosestSquaredHeuristic
/*  8:   */   implements AStarHeuristic
/*  9:   */ {
/* 10:   */   public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty)
/* 11:   */   {
/* 12:20 */     float dx = tx - x;
/* 13:21 */     float dy = ty - y;
/* 14:   */     
/* 15:23 */     return dx * dx + dy * dy;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.heuristics.ClosestSquaredHeuristic
 * JD-Core Version:    0.7.0.1
 */