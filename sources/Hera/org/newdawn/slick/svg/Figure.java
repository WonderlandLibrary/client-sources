/*    */ package org.newdawn.slick.svg;
/*    */ 
/*    */ import org.newdawn.slick.geom.Shape;
/*    */ import org.newdawn.slick.geom.Transform;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Figure
/*    */ {
/*    */   public static final int ELLIPSE = 1;
/*    */   public static final int LINE = 2;
/*    */   public static final int RECTANGLE = 3;
/*    */   public static final int PATH = 4;
/*    */   public static final int POLYGON = 5;
/*    */   private int type;
/*    */   private Shape shape;
/*    */   private NonGeometricData data;
/*    */   private Transform transform;
/*    */   
/*    */   public Figure(int type, Shape shape, NonGeometricData data, Transform transform) {
/* 42 */     this.shape = shape;
/* 43 */     this.data = data;
/* 44 */     this.type = type;
/* 45 */     this.transform = transform;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transform getTransform() {
/* 55 */     return this.transform;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getType() {
/* 64 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Shape getShape() {
/* 73 */     return this.shape;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NonGeometricData getData() {
/* 82 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\svg\Figure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */