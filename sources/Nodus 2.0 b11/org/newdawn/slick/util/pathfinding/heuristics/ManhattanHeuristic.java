/*  1:   */ package org.newdawn.slick.util.pathfinding.heuristics;
/*  2:   */ 
/*  3:   */ import org.newdawn.slick.util.pathfinding.AStarHeuristic;
/*  4:   */ import org.newdawn.slick.util.pathfinding.Mover;
/*  5:   */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*  6:   */ 
/*  7:   */ public class ManhattanHeuristic
/*  8:   */   implements AStarHeuristic
/*  9:   */ {
/* 10:   */   private int minimumCost;
/* 11:   */   
/* 12:   */   public ManhattanHeuristic(int minimumCost)
/* 13:   */   {
/* 14:23 */     this.minimumCost = minimumCost;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public float getCost(TileBasedMap map, Mover mover, int x, int y, int tx, int ty)
/* 18:   */   {
/* 19:31 */     return this.minimumCost * (Math.abs(x - tx) + Math.abs(y - ty));
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.heuristics.ManhattanHeuristic
 * JD-Core Version:    0.7.0.1
 */