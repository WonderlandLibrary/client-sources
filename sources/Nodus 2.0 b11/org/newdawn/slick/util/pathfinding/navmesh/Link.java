/*  1:   */ package org.newdawn.slick.util.pathfinding.navmesh;
/*  2:   */ 
/*  3:   */ public class Link
/*  4:   */ {
/*  5:   */   private float px;
/*  6:   */   private float py;
/*  7:   */   private Space target;
/*  8:   */   
/*  9:   */   public Link(float px, float py, Space target)
/* 10:   */   {
/* 11:24 */     this.px = px;
/* 12:25 */     this.py = py;
/* 13:26 */     this.target = target;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public float distance2(float tx, float ty)
/* 17:   */   {
/* 18:37 */     float dx = tx - this.px;
/* 19:38 */     float dy = ty - this.py;
/* 20:   */     
/* 21:40 */     return dx * dx + dy * dy;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public float getX()
/* 25:   */   {
/* 26:49 */     return this.px;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public float getY()
/* 30:   */   {
/* 31:58 */     return this.py;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Space getTarget()
/* 35:   */   {
/* 36:67 */     return this.target;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.navmesh.Link
 * JD-Core Version:    0.7.0.1
 */