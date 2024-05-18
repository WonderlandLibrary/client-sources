/*    */ package org.newdawn.slick.geom;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Point
/*    */   extends Shape
/*    */ {
/*    */   public Point(float x, float y) {
/* 21 */     this.x = x;
/* 22 */     this.y = y;
/* 23 */     checkPoints();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Shape transform(Transform transform) {
/* 31 */     float[] result = new float[this.points.length];
/* 32 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/*    */     
/* 34 */     return new Point(this.points[0], this.points[1]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createPoints() {
/* 42 */     this.points = new float[2];
/* 43 */     this.points[0] = getX();
/* 44 */     this.points[1] = getY();
/*    */     
/* 46 */     this.maxX = this.x;
/* 47 */     this.maxY = this.y;
/* 48 */     this.minX = this.x;
/* 49 */     this.minY = this.y;
/*    */     
/* 51 */     findCenter();
/* 52 */     calculateRadius();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void findCenter() {
/* 60 */     this.center = new float[2];
/* 61 */     this.center[0] = this.points[0];
/* 62 */     this.center[1] = this.points[1];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void calculateRadius() {
/* 70 */     this.boundingCircleRadius = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\geom\Point.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */