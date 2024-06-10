/*  1:   */ package org.newdawn.slick.geom;
/*  2:   */ 
/*  3:   */ public class Point
/*  4:   */   extends Shape
/*  5:   */ {
/*  6:   */   public Point(float x, float y)
/*  7:   */   {
/*  8:21 */     this.x = x;
/*  9:22 */     this.y = y;
/* 10:23 */     checkPoints();
/* 11:   */   }
/* 12:   */   
/* 13:   */   public Shape transform(Transform transform)
/* 14:   */   {
/* 15:31 */     float[] result = new float[this.points.length];
/* 16:32 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 17:   */     
/* 18:34 */     return new Point(this.points[0], this.points[1]);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected void createPoints()
/* 22:   */   {
/* 23:42 */     this.points = new float[2];
/* 24:43 */     this.points[0] = getX();
/* 25:44 */     this.points[1] = getY();
/* 26:   */     
/* 27:46 */     this.maxX = this.x;
/* 28:47 */     this.maxY = this.y;
/* 29:48 */     this.minX = this.x;
/* 30:49 */     this.minY = this.y;
/* 31:   */     
/* 32:51 */     findCenter();
/* 33:52 */     calculateRadius();
/* 34:   */   }
/* 35:   */   
/* 36:   */   protected void findCenter()
/* 37:   */   {
/* 38:60 */     this.center = new float[2];
/* 39:61 */     this.center[0] = this.points[0];
/* 40:62 */     this.center[1] = this.points[1];
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected void calculateRadius()
/* 44:   */   {
/* 45:70 */     this.boundingCircleRadius = 0.0F;
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Point
 * JD-Core Version:    0.7.0.1
 */